package org.semanticweb.ontop.pivotalrepr.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.semanticweb.ontop.executor.renaming.AlreadyExistingPredicateException;
import org.semanticweb.ontop.executor.renaming.PredicateRenamingChecker;
import org.semanticweb.ontop.model.*;
import org.semanticweb.ontop.model.impl.AtomPredicateImpl;
import org.semanticweb.ontop.model.impl.OBDADataFactoryImpl;
import org.semanticweb.ontop.owlrefplatform.core.basicoperations.NeutralSubstitution;
import org.semanticweb.ontop.owlrefplatform.core.basicoperations.VariableDispatcher;
import org.semanticweb.ontop.pivotalrepr.*;
import org.semanticweb.ontop.pivotalrepr.BinaryAsymmetricOperatorNode.ArgumentPosition;
import org.semanticweb.ontop.pivotalrepr.impl.tree.DefaultIntermediateQueryBuilder;
import org.semanticweb.ontop.pivotalrepr.proposal.InvalidQueryOptimizationProposalException;
import org.semanticweb.ontop.pivotalrepr.proposal.PredicateRenamingProposal;
import org.semanticweb.ontop.pivotalrepr.proposal.impl.PredicateRenamingProposalImpl;

import java.util.List;
import java.util.UUID;

/**
 * TODO: explain
 */
public class IntermediateQueryUtils {

    private static final OBDADataFactory DATA_FACTORY = OBDADataFactoryImpl.getInstance();
    private static final String SUB_QUERY_SUFFIX = "u";


    /**
     * TODO: describe
     */
    public static Optional<IntermediateQuery> mergeDefinitions(List<IntermediateQuery> predicateDefinitions)
            throws QueryMergingException {
        return mergeDefinitions(predicateDefinitions, Optional.<ImmutableQueryModifiers>absent());
    }


    /**
     * TODO: describe
     * The optional modifiers are for the top construction node above the UNION (if any).
     */
    public static Optional<IntermediateQuery> mergeDefinitions(List<IntermediateQuery> predicateDefinitions,
                                                               Optional<ImmutableQueryModifiers> optionalTopModifiers)
            throws QueryMergingException {
        if (predicateDefinitions.isEmpty())
            return Optional.absent();

        IntermediateQuery firstDefinition = predicateDefinitions.get(0);
        if (predicateDefinitions.size() == 1) {
            return Optional.of(firstDefinition);
        }

        DataAtom headAtom = createTopProjectionAtom(firstDefinition.getRootConstructionNode().getProjectionAtom());
        AtomPredicate normalPredicate = headAtom.getPredicate();
        AtomPredicate subQueryPredicate = createSubQueryPredicate(predicateDefinitions, normalPredicate);
        DataAtom subQueryAtom = DATA_FACTORY.getDataAtom(subQueryPredicate, headAtom.getVariablesOrGroundTerms());

        // Non final definition
        IntermediateQuery mergedDefinition = null;

        for (IntermediateQuery originalDefinition : predicateDefinitions) {
            if (mergedDefinition == null) {
                mergedDefinition = initMergedDefinition(originalDefinition.getMetadata(), headAtom, subQueryAtom,
                        optionalTopModifiers);
            } else {
                mergedDefinition = prepareForMergingNewDefinition(mergedDefinition, subQueryAtom);
            }

            checkDefinitionRootProjections(mergedDefinition, originalDefinition);

            PredicateRenamingProposal renamingProposal = new PredicateRenamingProposalImpl(normalPredicate,
                    subQueryPredicate);

            IntermediateQuery renamedDefinition;
            try {
                renamedDefinition = originalDefinition.applyProposal(renamingProposal).getResultingQuery();
            } catch (InvalidQueryOptimizationProposalException | EmptyQueryException e) {
                throw new RuntimeException("Internal error: bad renaming proposal: " + e.getMessage());
            }
            mergedDefinition.mergeSubQuery(renamedDefinition);
        }
        return Optional.of(mergedDefinition);
    }

    /**
     * TODO: explain
     */
    private static AtomPredicate createSubQueryPredicate(List<IntermediateQuery> predicateDefinitions,
                                                         AtomPredicate predicate) {
        AtomPredicate newPredicate = new AtomPredicateImpl(predicate.getName()+ SUB_QUERY_SUFFIX, predicate.getArity());

        for (IntermediateQuery definition : predicateDefinitions) {
            try {
                PredicateRenamingChecker.checkNonExistence(definition, newPredicate);
            }
            /**
             * If the proposed predicate is already used,
             * creates one by using UUID4
             */
            catch (AlreadyExistingPredicateException e) {
                newPredicate = new AtomPredicateImpl(predicate.getName()+ UUID.randomUUID(), predicate.getArity());
                break;
            }
        }
        return newPredicate;
    }

    /**
     * TODO: explain
     *
     */
    private static DataAtom createTopProjectionAtom(DataAtom firstRuleProjectionAtom) {
        ImmutableList.Builder<Variable> argBuilder = ImmutableList.builder();

        VariableDispatcher variableDispatcher = new VariableDispatcher();
        for (VariableOrGroundTerm argument : firstRuleProjectionAtom.getVariablesOrGroundTerms()) {
            /**
             * Variable: keeps it if not already used in the atom or rename it otherwise.
             */
            if (argument instanceof Variable) {
                argBuilder.add(variableDispatcher.renameDataAtomVariable((Variable) argument));
            }
            /**
             * Ground term: create a new variable.
             */
            else {
                argBuilder.add(variableDispatcher.generateNewVariable());
            }
        }

        return DATA_FACTORY.getDataAtom(firstRuleProjectionAtom.getPredicate(), argBuilder.build());
    }

    /**
     * TODO: explain
     */
    private static IntermediateQuery initMergedDefinition(MetadataForQueryOptimization metadata,
                                                          DataAtom headAtom, DataAtom subQueryAtom,
                                                          Optional<ImmutableQueryModifiers> optionalTopModifiers)
            throws QueryMergingException {
        ConstructionNode rootNode = new ConstructionNodeImpl(headAtom, new NeutralSubstitution(), optionalTopModifiers);
        UnionNode unionNode = new UnionNodeImpl();
        IntensionalDataNode dataNode = new IntensionalDataNodeImpl(subQueryAtom);

        IntermediateQueryBuilder queryBuilder = new DefaultIntermediateQueryBuilder(metadata);
        try {
            queryBuilder.init(rootNode);
            queryBuilder.addChild(rootNode, unionNode);
            queryBuilder.addChild(unionNode, dataNode);
            return queryBuilder.build();
        } catch (IntermediateQueryBuilderException e) {
            throw new QueryMergingException(e.getLocalizedMessage());
        }
    }

    /**
     * TODO: explain
     */
    private static IntermediateQuery prepareForMergingNewDefinition(IntermediateQuery mergedDefinition,
                                                                    DataAtom subQueryAtom)
            throws QueryMergingException {
        try {
            IntermediateQueryBuilder queryBuilder = convertToBuilder(mergedDefinition);
            ConstructionNode rootConstructionNode = queryBuilder.getRootConstructionNode();

            UnionNode unionNode = extractUnionNode(queryBuilder, rootConstructionNode);

            IntensionalDataNode dataNode = new IntensionalDataNodeImpl(subQueryAtom);
            queryBuilder.addChild(unionNode, dataNode);

            return queryBuilder.build();
        } catch (IntermediateQueryBuilderException e) {
            throw new QueryMergingException(e.getLocalizedMessage());
        }
    }

    private static UnionNode extractUnionNode(IntermediateQueryBuilder queryBuilder,
                                              ConstructionNode rootConstructionNode)
            throws IntermediateQueryBuilderException {
        ImmutableList<QueryNode> rootChildren = queryBuilder.getSubNodesOf(rootConstructionNode);
        if (rootChildren.size() != 1) {
            throw new RuntimeException("BUG: merged definition query without a unique UNION" +
                    " below the root projection node");
        }
        QueryNode rootChild = rootChildren.get(0);

        if (!(rootChild instanceof UnionNode)) {
            throw new RuntimeException("BUG: the root child of a merged definition is not a UNION");
        }
        return (UnionNode) rootChild;
    }

    /**
     * TODO: explain
     *
     */
    public static IntermediateQueryBuilder convertToBuilder(IntermediateQuery originalQuery)
            throws IntermediateQueryBuilderException {
        try {
            return convertToBuilderAndTransform(originalQuery, Optional.<HomogeneousQueryNodeTransformer>absent());
            /**
             * No transformer so should not be expected
             */
        } catch (QueryNodeTransformationException | NotNeededNodeException e) {
            throw new RuntimeException("Should not be thrown: " + e.getMessage());
        }
    }

    /**
     * TODO: explain
     *
     */
    public static IntermediateQueryBuilder convertToBuilderAndTransform(IntermediateQuery originalQuery,
                                                                        HomogeneousQueryNodeTransformer transformer)
            throws IntermediateQueryBuilderException, QueryNodeTransformationException, NotNeededNodeException {
        return convertToBuilderAndTransform(originalQuery, Optional.of(transformer));
    }

    /**
     * TODO: explain
     *
     * TODO: avoid the use of a recursive method. Use a stack instead.
     *
     */
    private static IntermediateQueryBuilder convertToBuilderAndTransform(IntermediateQuery originalQuery,
                                                                        Optional<HomogeneousQueryNodeTransformer> optionalTransformer)
            throws IntermediateQueryBuilderException, QueryNodeTransformationException, NotNeededNodeException {
        IntermediateQueryBuilder queryBuilder = new DefaultIntermediateQueryBuilder(originalQuery.getMetadata());

        // Clone of the original root node and apply the transformer if available.
        ConstructionNode originalRootNode = originalQuery.getRootConstructionNode();
        ConstructionNode newRootNode;
        if (optionalTransformer.isPresent()) {
            newRootNode =  originalRootNode.acceptNodeTransformer(optionalTransformer.get()).clone();
        }
        else {
            newRootNode = originalRootNode.clone();
        }

        queryBuilder.init(newRootNode);

        return copyChildrenNodesToBuilder(originalQuery, queryBuilder, originalRootNode, newRootNode, optionalTransformer);
    }

    /**
     * TODO: replace this implementation by a non-recursive one.
     */
    private static IntermediateQueryBuilder copyChildrenNodesToBuilder(final IntermediateQuery originalQuery,
                                                                       IntermediateQueryBuilder queryBuilder,
                                                                       final QueryNode originalParentNode,
                                                                       final QueryNode newParentNode,
                                                                       Optional<HomogeneousQueryNodeTransformer> optionalTransformer)
            throws IntermediateQueryBuilderException, QueryNodeTransformationException, NotNeededNodeException {
        for(QueryNode originalChildNode : originalQuery.getChildren(originalParentNode)) {

            // QueryNode are mutable
            QueryNode newChildNode;
            if (optionalTransformer.isPresent()) {
                newChildNode = originalChildNode.acceptNodeTransformer(optionalTransformer.get()).clone();
            } else {
                newChildNode = originalChildNode.clone();
            }

            Optional<ArgumentPosition> optionalPosition = originalQuery.getOptionalPosition(originalParentNode, originalChildNode);
            queryBuilder.addChild(newParentNode, newChildNode, optionalPosition);

            // Recursive call
            queryBuilder = copyChildrenNodesToBuilder(originalQuery, queryBuilder, originalChildNode, newChildNode, optionalTransformer);
        }

        return queryBuilder;
    }

    /**
     * TODO: explain
     *
     */
    private static void checkDefinitionRootProjections(IntermediateQuery definition1, IntermediateQuery definition2)
            throws QueryMergingException {
        ConstructionNode root1 = definition1.getRootConstructionNode();
        ConstructionNode root2 = definition2.getRootConstructionNode();

        DataAtom headAtom1 = root1.getProjectionAtom();
        DataAtom headAtom2 = root2.getProjectionAtom();

        if (!headAtom1.hasSamePredicateAndArity(headAtom2)) {
            throw new QueryMergingException("Two definitions of different things: " + headAtom1 + " != " + headAtom2);
        }

        /**
         * We do not check the query modifiers
         * TODO: should we?
         */
    }
}
package org.semanticweb.ontop.owlrefplatform.core.unfolding;


import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import fj.*;
import fj.data.*;
import org.semanticweb.ontop.model.*;
import org.semanticweb.ontop.model.Function;
import org.semanticweb.ontop.owlrefplatform.core.basicoperations.Unifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Map;

/**
 * TODO: describe it
 *
 * Here by Type we mean:
 *   - Integer, double, etc. as usual
 *   - URI templates
 *
 */
public class TypeLift {

    private static class MultiTypeException extends Exception {
    };

    private static class UnificationException extends Exception {
    };

    private static class TypeApplicationError extends RuntimeException {
    };

    private static Logger LOGGER = LoggerFactory.getLogger(TypeLift.class);

    /**
     * Type lifting implementation based on tree zippers (persistent data structures).
     *
     * @param inputRules
     * @param multiTypedFunctionSymbolIndex
     * @return
     */
    public static java.util.List<CQIE> liftTypes(java.util.List<CQIE> inputRules,
                                       Multimap<Predicate,Integer> multiTypedFunctionSymbolIndex) {
        /**
         * Yes, some **** tests try to lift types while there is no rule...
         */
        if (inputRules.isEmpty()) {
            return inputRules;
        }

        /**
         * Builds a tree zipper from the input Datalog rules.
         */
        TreeBasedDatalogProgram initialDatalogProgram = TreeBasedDatalogProgram.fromRules(inputRules);
        TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> initialRootZipper = TreeZipper.fromTree(
                initialDatalogProgram.getP3RuleTree());

        /**
         * Navigates into the tree until reaching the leftmost leaf.
         */
        TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> leftmostTreeZipper =
                navigateToLeftmostLeaf(initialRootZipper);

        /**
         * Computes a new Datalog program by applying type lifting
         */
        TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> newTreeZipper = liftTypesOnTreeZipper(
                leftmostTreeZipper, multiTypedFunctionSymbolIndex);
        TreeBasedDatalogProgram newDatalogProgram = TreeBasedDatalogProgram.fromP3RuleTree(newTreeZipper.toTree());

        LOGGER.debug(newDatalogProgram.toString());

        java.util.List<CQIE> newRules = new ArrayList<>(newDatalogProgram.getRules().toCollection());
        return newRules;
    }

    /**
     * Navigates into the zipper until reaching the leftmost leaf.
     *
     * Tail-recursive function
     *  (even if not optimized by the JVM, should not be too profound (tree depth)).
     *
     */
    private static TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> navigateToLeftmostLeaf(
            TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> currentZipper) {

        Option<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> optionalFirstChild = currentZipper.firstChild();
        /**
         * Goes to its left child
         */
        if (optionalFirstChild.isSome())
            return navigateToLeftmostLeaf(optionalFirstChild.some());
        /**
         * Otherwise, is the leftmost leaf.
         */
        return currentZipper;
    }

    /**
     * We use here an imperative loop instead of a function
     * because the tail-recursion optimization is apparently still not supported in Java 7.
     *
     */
    private static TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> liftTypesOnTreeZipper(
            TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> initialTreeZipper,
            final Multimap<Predicate,Integer> multiTypedFunctionSymbolIndex) {

        TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> currentZipper = initialTreeZipper;
        while (true) {
            currentZipper = updateSubTree(currentZipper, multiTypedFunctionSymbolIndex);

            /**
             * Moves to the leftmost leaf of the right sibling if possible.
             */
            Option<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> optionalRightSibling = currentZipper.right();
            if (optionalRightSibling.isSome()) {
                /**
                 * If the right sibling is not a leaf, reaches the leftmost leaf of its sub-tree.
                 */
                currentZipper = navigateToLeftmostLeaf(optionalRightSibling.some());
            }
            /**
             * Otherwise, tries to move to the parent.
             * If already at the root, terminates.
             */
            else {
                Option<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> optionalParent = currentZipper.parent();
                if (optionalParent.isSome()) {
                    currentZipper = currentZipper.parent().some();
                }
                /**
                 * The root has been reached. Applies its proposal and breaks
                 * the loop.
                 */
                else {
                    currentZipper = applyTypeFunction.f(currentZipper);
                    break;
                }
            }
        }
        return currentZipper;
    }

    /**
     * Updates the current and its children nodes.
     *
     * Type lifting is forbidden if the current predicate is
     * already multi-typed or if the child proposals would
     * make it multi-typed.
     *
     * Returns the updated treeZipper at the same position.
     */
    private static TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> updateSubTree(
            final TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> currentZipper,
            Multimap<Predicate, Integer> multiTypedFunctionSymbolIndex) {

        P3<Predicate, List<CQIE>, Option<Function>> currentLabel = currentZipper.getLabel();
        Predicate currentPredicate = currentLabel._1();

        boolean isMultiTyped = multiTypedFunctionSymbolIndex.containsKey(currentPredicate);

        if (!isMultiTyped) {
            try {
                return liftTypeFromChildrenToParent(currentZipper);
            }
            /**
             * Type lifting rejected because it would make the predicate
             * be multi-typed.
             */
            catch(MultiTypeException ex) {
            }
        }
        return applyToChildren(applyTypeFunction, currentZipper);
    }

    /**
     *
     * If the children proposals are compatible, throws a MultiTypeException
     *
     */
    private static TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> liftTypeFromChildrenToParent(
            final TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> parentZipper) throws MultiTypeException {

        Option<Function> parentProposal = buildProposal(parentZipper);
        /**
         * If no type has been proposed by the children nor the node itself,
         * no need to remove types from the children rules.
         */
        if (parentProposal.isNone()) {
            return parentZipper;
        }

        /**
         * Removes types from the children rules
         */
        TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> cleanedZipper = applyToChildren(removeTypeFunction, parentZipper);

        /**
         * Sets the proposal to the parent node
         */
        P3<Predicate, List<CQIE>, Option<Function>> parentLabel = cleanedZipper.getLabel();
        return cleanedZipper.setLabel(P.p(parentLabel._1(), parentLabel._2(), parentProposal));
    }


    /**
     * If the children proposals are compatible, throws a MultiTypeException
     */
    private static Option<Function> buildProposal(final TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> currentZipper)
            throws MultiTypeException {

        HashMap<Predicate, List<Function>> childProposalIndex = retrieveChildrenProposals(currentZipper);

        /**
         * If there is no child proposal, no need to unify.
         * Returns the local proposal.
         */
        if (childProposalIndex.isEmpty()) {
            return proposeTypeFromLocalRules(currentZipper);
        }

        List<CQIE> currentRules = currentZipper.getLabel()._2();

        /**
         * Use the head of the first rule.
         * Please note that parent node without rule does not make sense.
         *
         * TODO: will it make sense to use the proposal instead?
         */
        Function currentHead = currentRules.head().getHead();

        /**
         * TODO: explain
         */
        HashMap<Predicate, List<Function>> ruleBodyIndex = computeRuleBodyIndex(currentRules);

        /**
         * Unifies all these proposals.
         *
         * If such unification is not possible,  a MultiTypeException will be thrown.
         */
        Function newProposal = unifyProposals(currentHead, ruleBodyIndex, childProposalIndex,
                childProposalIndex.keys());

        return Option.some(newProposal);
    }

    /**
     * TODO: describe what we mean here by rule body index
     */
    private static HashMap<Predicate,List<Function>> computeRuleBodyIndex(List<CQIE> currentRules) {
        List<Function> bodies = List.join(currentRules.map(new F<CQIE, List<Function>>() {
            @Override
            public List<Function> f(CQIE rule) {
                return List.iterableList(rule.getBody());
            }
        }));
        return buildPredicateIndex(bodies);
    }

    /**
     * TODO: describe
     *
     * Tail-recursive
     *
     * Assumptions:
     *   - There are at least of child proposals
     *   - There are multiple current rules (tree consistency)
     *
     */
     private static Function unifyProposals(Function currentHead, HashMap<Predicate, List<Function>> ruleBodyIndex,
                                          HashMap<Predicate, List<Function>> childProposalIndex,
                                          List<Predicate> remainingPredicates) throws MultiTypeException {
        /**
         * Stop condition (no more atom to unify).
         */
        if (remainingPredicates.isEmpty())
            return currentHead;

         Predicate currentPredicate = remainingPredicates.head();

         Function newHead = unifyAtoms(currentHead, crossProduct(ruleBodyIndex.get(currentPredicate).some(),
                 childProposalIndex.get(currentPredicate).some()));

         /**
          * Tail recursion
          */
        return unifyProposals(newHead, ruleBodyIndex, childProposalIndex, remainingPredicates.tail());
    }

    /**
     * TODO: explain
     */
    private static Function unifyAtoms(Function currentHead, List<P2<Function, Function>> bodyAndProposalAtoms)
        throws MultiTypeException {

        if (bodyAndProposalAtoms.isEmpty())
            return currentHead;

        P2<Function, Function> bodyAndHeadPair = bodyAndProposalAtoms.head();
        Function bodyAtom = bodyAndHeadPair._1();
        Function proposalAtom = bodyAndHeadPair._2();

        try {
            Function newHead = unifyTypes(currentHead, bodyAtom, proposalAtom);

            /**
             * Tail recursion
             */
            return unifyAtoms(newHead, bodyAndProposalAtoms.tail());
        }
        /**
         * Impossible to unify.
         * This happens when multiple types are proposed for this predicate.
         */
        catch(UnificationException e) {
                throw new MultiTypeException();
        }
    }

    /**
     * Low-level function.
     *
     * The goal to transfer proposed types (given by the proposedAtom)
     * to the localHead.
     *
     * Like the localHead, the localAtom belongs to the local rule.
     * It should have the same predicate than the proposedAtom (which usually
     * differs from the one of the localHead).
     *
     * One sensitive constraint here is to propagate types without changing the
     * variable names.
     *
     * If the unification could not be achieved, throws a UnificationException.
     */
    private static Function unifyTypes(Function localHead, Function localAtom, Function proposedAtom)
            throws UnificationException{
        /**
         * Most General Unifier between the proposedAtom and the localAtom.
         */
        Map<Variable, Term> directMGU = Unifier.getMGU(proposedAtom, localAtom, true, ImmutableMultimap.<Predicate,Integer>of());

        /**
         * Impossible to unify the multiple types proposed for this predicate.
         */
        if (directMGU == null) {
            throw new UnificationException();
        }

        /**
         * The current MGU may change variable names because they were not the same in the two atoms.
         *
         * Here, we are just interested in the types but we do not want to change the variable names.
         * Thus, we force variable reuse.
         */
        //TODO: reimplement this method without side effect.
        Map<Variable, Term> typingMGU = DatalogUnfolder.forceVariableReuse(new ArrayList<Term>(), directMGU);

        //Mutable!!
        Function newHead = (Function)localHead.clone();
        // Side-effect (newHead is updated)
        Unifier.applySelectiveUnifier(newHead, typingMGU);

        return newHead;
    }

    /**
     * Applies the type proposal to the rule heads.
     */
    private static List<CQIE> applyTypeToRules(List<CQIE> initialRules, final Function typeProposal)
            throws TypeApplicationError{
        return initialRules.map(new F<CQIE, CQIE>() {
            @Override
            public CQIE f(CQIE initialRule) {
                CQIE newRule = initialRule.clone();
                Function currentHead = initialRule.getHead();
                try {
                    Function newHead = unifyTypes(currentHead, currentHead, typeProposal);
                    newRule.updateHead(newHead);
                    return newRule;
                    /**
                     * Unification exception should not appear at this level.
                     * There is an inconsistency somewhere.
                     *
                     * Throws a runtime exception (TypeApplicationError)
                     * that should not be expected.
                     */
                } catch(UnificationException e) {
                    throw new TypeApplicationError();
                }
            }
        });
    }

    private static List<CQIE> removeTypesFromRules(List<CQIE> initialRules) {
        return initialRules.map(new F<CQIE, CQIE>() {
            @Override
            public CQIE f(CQIE initialRule) {
                Function initialHead = initialRule.getHead();
                List<Term> initialHeadTerms =  List.iterableList(initialHead.getTerms());

                /**
                 * Computes untyped arguments for the head predicate.
                 */
                List<Term> newHeadTerms = initialHeadTerms.map(new F<Term, Term>() {
                    @Override
                    public Term f(Term term) {
                        // TODO: clean the called method
                        return DatalogUnfolder.getUntypedArgumentFromTerm(term, false, new ArrayList<Term>()).get(0);
                    }
                });

                /**
                 * Builds a new rule.
                 * TODO: modernize the CQIE API (make it immutable).
                 */
                CQIE newRule = initialRule.clone();
                Function newHead = (Function)initialHead.clone();
                newHead.updateTerms(new ArrayList<>(newHeadTerms.toCollection()));
                newRule.updateHead(newHead);
                return newRule;
            }
        });
    }

    /**
     * Returns the first head of its rules if types have been detected in it.
     *
     * TODO: consider case where there is multiple rules.
     */
    private static Option<Function> proposeTypeFromLocalRules(TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> currentZipper) {
        List<CQIE> currentRules = currentZipper.getLabel()._2();
        if (currentRules.isEmpty()) {
            return Option.none();
        }
        // TODO: detect types

        // Head of the first rule (cloned because mutable).
        return Option.some((Function)currentRules.head().getHead().clone());
    }

    private static HashMap<Predicate, List<Function>> retrieveChildrenProposals(final TreeZipper<P3<Predicate, List<CQIE>,
            Option<Function>>> parentZipper) {
        /**
         * Child forest
         */
        Stream<Tree<P3<Predicate, List<CQIE>, Option<Function>>>> subForest = parentZipper.focus().subForest()._1();
        if (subForest.isEmpty()) {
            return HashMap.from(Stream.<P2<Predicate, List<Function>>>nil());
        }

        /**
         * Children labels (roots of the child forest)
         */
        Stream<P3<Predicate, List<CQIE>, Option<Function>>> childrenLabels =  subForest.map(
                Tree.<P3<Predicate, List<CQIE>, Option<Function>>>root_());

        Stream<Option<Function>> proposals = childrenLabels.map(P3.<Predicate, List<CQIE>, Option<Function>>__3());

        /**
         * Only positive proposals
         */
        List<Function> proposedHeads = Option.somes(proposals).toList();

        /**
         * Computes and returns the equivalent predicate index
         */
        return buildPredicateIndex(proposedHeads);
    }

    /**
     * Applies a function to the children.
     *
     * Returns the updated tree zipper at the parent position.
     */
    private static TreeZipper<P3<Predicate,List<CQIE>,Option<Function>>> applyToChildren(
            F<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>, TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> f,
            TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> parentZipper) {
        Option<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> optionalFirstChild = parentZipper.firstChild();

        /**
         * No child, nothing to apply
         */
        if (optionalFirstChild.isNone()) {
            return parentZipper;
        }

        /**
         * Applies "applyTypeToRules" to the children.
         *
         * IMPROVEMENT: Find a way to replace this usage by a map only applied to the children of a given parent node.
         */
        TreeZipper<P3<Predicate,List<CQIE>,Option<Function>>> lastChildZipper = applyToNodeAndRightSiblings(f, optionalFirstChild.some());

        /**
         *  Move back to the parent node
         */
        return lastChildZipper.parent().some();
    }

    /**
     * Applies a function to the current zipper and its right siblings.
     *
     * Tail-recursive function.
     */
    private static TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> applyToNodeAndRightSiblings(
            F<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>, TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> f,
            TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> currentZipper) {
        /**
         * Applies f to the current node
         */
        TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> updatedCurrentZipper = f.f(currentZipper);

        /**
         * Looks for the right sibling
         */
        Option<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> optionalRightSibling = updatedCurrentZipper.right();
        if (optionalRightSibling.isSome()) {
            /**
             * Recursive call
             */
            return applyToNodeAndRightSiblings(f, optionalRightSibling.some());
        }
        /**
         * If if the rightmost sibling, stops recursion.
         */
        return updatedCurrentZipper;
    }


    /**
     * Low-level. Applies the type to the rules of the current predicate.
     *
     * Returns the updated zipper at the same location.
     */
    private static F<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>, TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> applyTypeFunction
            = new F<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>, TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>>() {
        @Override
        public TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> f(TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> treeZipper) {
            /**
             * Extracts values from the node
             */
            P3<Predicate, List<CQIE>, Option<Function>> label = treeZipper.getLabel();
            List<CQIE> initialRules = label._2();
            Option<Function> optionalNewTypeAtom = label._3();

            /**
             * No type atom proposed, nothing to change.
             */
            if (optionalNewTypeAtom.isNone())
                return treeZipper;
            /**
             * Otherwise, applies the proposed types
             * and returns the updated tree zipper.
             */
            else {
                List<CQIE> newRules = applyTypeToRules(initialRules, optionalNewTypeAtom.some());
                return treeZipper.setLabel(P.p(label._1(), newRules, Option.<Function>none()));
            }
        }
    };

    /**
     * Removes types from the rules of the current node.
     */
    private static F<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>, TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>> removeTypeFunction
            = new F<TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>, TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>>>() {
        @Override
        public TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> f(TreeZipper<P3<Predicate, List<CQIE>, Option<Function>>> treeZipper) {
            P3<Predicate, List<CQIE>, Option<Function>> label = treeZipper.getLabel();
            List<CQIE> initialRules = label._2();
            List<CQIE> updatedRules = removeTypesFromRules(initialRules);
            return treeZipper.setLabel(P.p(label._1(), updatedRules, Option.<Function>none()));
        }
    };

    /**
     * TODO: find the corresponding generic method
     */
    private static List<P2<Function, Function>> crossProduct(List<Function> l1, final List<Function> l2) {
        List<List<P2<Function, Function>>> intermediateList = l1.map(new F<Function, List<P2<Function, Function>>>() {
            @Override
            public List<P2<Function, Function>> f(final Function atom1) {
                return l2.map(new F<Function, P2<Function, Function>>() {
                    @Override
                    public P2<Function, Function> f(Function atom2) {
                        return P.p(atom1, atom2);
                    };
                });
            }
        });
        return List.join(intermediateList);
    }

    /**
     * TODO: explain
     */
    private static HashMap<Predicate, List<Function>> buildPredicateIndex(List<Function> atoms) {
        List<P2<Predicate, List<Function>>> predicateAtomList = atoms.group(
                /**
                 * Groups by predicate
                 */
                Equal.equal(new F<Function, F<Function, Boolean>>() {
                    @Override
                    public F<Function, Boolean> f(final Function atom) {
                        return new F<Function, Boolean>() {
                            @Override
                            public Boolean f(Function other) {
                                return other.getFunctionSymbol().equals(atom.getFunctionSymbol());
                            }
                        };
                    }
                })).map(
                /**
                 * Transforms it into a P2 list (predicate and list of functions).
                 */
                new F<List<Function>, P2<Predicate, List<Function>>>() {
                    @Override
                    public P2<Predicate, List<Function>> f(List<Function> atoms) {
                        return P.p(atoms.head().getFunctionSymbol(), atoms);
                    }
                });

        return HashMap.from(predicateAtomList);
    }

}

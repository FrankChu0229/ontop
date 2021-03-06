package it.unibz.inf.ontop.spec.mapping.parser.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import it.unibz.inf.ontop.model.term.Function;
import it.unibz.inf.ontop.model.term.Variable;
import it.unibz.inf.ontop.dbschema.QualifiedAttributeID;
import it.unibz.inf.ontop.dbschema.QuotedID;
import it.unibz.inf.ontop.dbschema.RelationID;
import it.unibz.inf.ontop.spec.mapping.parser.exception.IllegalJoinException;
import it.unibz.inf.ontop.utils.ImmutableCollectors;

import static it.unibz.inf.ontop.model.OntopModelSingletons.TERM_FACTORY;

/**
 * Created by Roman Kontchakov on 01/11/2016.
 *
 */
public class RAExpression {

    private ImmutableList<Function> dataAtoms;
    private ImmutableList<Function> filterAtoms;
    private RAExpressionAttributes attributes;

    /**
     * constructs a relation expression
     *
     * @param dataAtoms            an {@link ImmutableList}<{@link Function}>
     * @param filterAtoms          an {@link ImmutableList}<{@link Function}>
     * @param attributes           an {@link RAExpressionAttributes}
     */
    public RAExpression(ImmutableList<Function> dataAtoms,
                        ImmutableList<Function> filterAtoms,
                        RAExpressionAttributes attributes) {
        this.dataAtoms = dataAtoms;
        this.filterAtoms = filterAtoms;
        this.attributes = attributes;
    }


    public ImmutableList<Function> getDataAtoms() {
        return dataAtoms;
    }

    public ImmutableList<Function> getFilterAtoms() {
        return filterAtoms;
    }

    public ImmutableMap<QualifiedAttributeID, Variable> getAttributes() {
        return attributes.getAttributes();
    }

    /**
     * CROSS JOIN (also denoted by , in SQL)
     *
     * @param re1 a {@link RAExpression}
     * @param re2 a {@link RAExpression}
     * @return a {@link RAExpression}
     * @throws IllegalJoinException if the same alias occurs in both arguments
     */
    public static RAExpression crossJoin(RAExpression re1, RAExpression re2) throws IllegalJoinException {

        RAExpressionAttributes attributes =
                RAExpressionAttributes.crossJoin(re1.attributes, re2.attributes);

        return new RAExpression(union(re1.dataAtoms, re2.dataAtoms),
                union(re1.filterAtoms, re2.filterAtoms), attributes);
    }


    /**
     * JOIN ON
     *
     * @param re1 a {@link RAExpression}
     * @param re2 a {@link RAExpression}
     * @param getAtomOnExpression
     * @return a {@link RAExpression}
     * @throws IllegalJoinException if the same alias occurs in both arguments
     */
    public static RAExpression joinOn(RAExpression re1, RAExpression re2,
                                      java.util.function.Function<ImmutableMap<QualifiedAttributeID, Variable>, ImmutableList<Function>> getAtomOnExpression) throws IllegalJoinException {

        RAExpressionAttributes attributes =
                RAExpressionAttributes.crossJoin(re1.attributes, re2.attributes);

        return new RAExpression(union(re1.dataAtoms, re2.dataAtoms),
                union(re1.filterAtoms, re2.filterAtoms,
                        getAtomOnExpression.apply(attributes.getAttributes())),
                attributes);
    }

    /**
     * NATURAL JOIN
     *
     * @param re1 a {@link RAExpression}
     * @param re2 a {@link RAExpression}
     * @return a {@link RAExpression}
     * @throws IllegalJoinException if the same alias occurs in both arguments
     *          or one of the shared attributes is ambiguous
     */

    public static RAExpression naturalJoin(RAExpression re1, RAExpression re2) throws IllegalJoinException {

        ImmutableSet<QuotedID> shared =
                RAExpressionAttributes.getShared(re1.attributes, re2.attributes);

        RAExpressionAttributes attributes =
                RAExpressionAttributes.joinUsing(re1.attributes, re2.attributes, shared);

        return new RAExpression(union(re1.dataAtoms, re2.dataAtoms),
                union(re1.filterAtoms, re2.filterAtoms,
                        getJoinOnFilter(re1.attributes, re2.attributes, shared)),
                attributes);
    }

    /**
     * JOIN USING
     *
     * @param re1 a {@link RAExpression}
     * @param re2 a {@link RAExpression}
     * @param using a {@link ImmutableSet}<{@link QuotedID}>
     * @return a {@link RAExpression}
     * @throws IllegalJoinException if the same alias occurs in both arguments
     *          or one of the `using' attributes is ambiguous or absent
     */

    public static RAExpression joinUsing(RAExpression re1, RAExpression re2,
                                         ImmutableSet<QuotedID> using) throws IllegalJoinException {

        RAExpressionAttributes attributes =
                RAExpressionAttributes.joinUsing(re1.attributes, re2.attributes, using);

        return new RAExpression(union(re1.dataAtoms, re2.dataAtoms),
                union(re1.filterAtoms, re2.filterAtoms,
                        getJoinOnFilter(re1.attributes, re2.attributes, using)),
                attributes);
    }

    /**
     * internal implementation of JOIN USING and NATURAL JOIN
     *
     * @param re1 a {@link RAExpressionAttributes}
     * @param re2 a {@link RAExpressionAttributes}
     * @param using a {@link ImmutableSet}<{@link QuotedID}>
     * @return a {@Link ImmutableList}<{@link Function}>
     */
    private static ImmutableList<Function> getJoinOnFilter(RAExpressionAttributes re1,
                                                           RAExpressionAttributes re2,
                                                           ImmutableSet<QuotedID> using) {

        return using.stream()
                .map(id -> new QualifiedAttributeID(null, id))
                .map(id -> {
                    // TODO: this will be removed later, when OBDA factory will start checking non-nulls
                    Variable v1 = re1.getAttributes().get(id);
                    if (v1 == null)
                        throw new IllegalArgumentException("Variable " + id + " not found in " + re1);
                    Variable v2 = re2.getAttributes().get(id);
                    if (v2 == null)
                        throw new IllegalArgumentException("Variable " + id + " not found in " + re2);
                    return TERM_FACTORY.getFunctionEQ(v1, v2);
                })
                .collect(ImmutableCollectors.toList());
    }


    /**
     * (relational expression) AS A
     *
     * @param re a {@link RAExpression}
     * @param alias a {@link QuotedID}
     * @return a {@link RAExpression}
     */

    public static RAExpression alias(RAExpression re, RelationID alias) {
        return new RAExpression(re.dataAtoms, re.filterAtoms,
                RAExpressionAttributes.alias(re.attributes, alias));
    }



    private static ImmutableList<Function> union(ImmutableList<Function> atoms1, ImmutableList<Function> atoms2) {
        return ImmutableList.<Function>builder().addAll(atoms1).addAll(atoms2).build();
    }

    private static ImmutableList<Function> union(ImmutableList<Function> atoms1, ImmutableList<Function> atoms2, ImmutableList<Function> atoms3) {
        return ImmutableList.<Function>builder().addAll(atoms1).addAll(atoms2).addAll(atoms3).build();
    }


    @Override
    public String toString() {
        return "RAExpression : " + dataAtoms + " FILTER " + filterAtoms + " with " + attributes;
    }


}

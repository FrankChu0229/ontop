package it.unibz.inf.ontop.spec.mapping.pp.impl;


import it.unibz.inf.ontop.spec.mapping.pp.SQLPPTriplesMap;
import it.unibz.inf.ontop.model.term.ImmutableFunctionalTerm;
import it.unibz.inf.ontop.spec.mapping.pp.PPMappingAssertionProvenance;

public class OntopNativeSQLMappingAssertionProvenance implements PPMappingAssertionProvenance {

    private final ImmutableFunctionalTerm targetAtom;
    private final SQLPPTriplesMap triplesMap;

    OntopNativeSQLMappingAssertionProvenance(ImmutableFunctionalTerm targetAtom, SQLPPTriplesMap triplesMap) {
        this.targetAtom = targetAtom;
        this.triplesMap = triplesMap;
    }

    @Override
    public String getProvenanceInfo() {
        String info = "id: " + triplesMap.getId();
        info += "\ntarget atom: " + targetAtom.toString();
        info += "\nsource query: " + triplesMap.getSourceQuery();
        return info;
    }

    @Override
    public String toString() {
        return getProvenanceInfo();
    }
}

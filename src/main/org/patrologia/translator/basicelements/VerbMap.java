package org.patrologia.translator.basicelements;

import java.util.*;

public class VerbMap {

    private HashMap<String, Verb> internalMap = new HashMap<String, Verb>();
    private ConjugationMap conjugationMap;

    public VerbMap(ConjugationMap conjugationMap) {
        this.conjugationMap = conjugationMap;
    }

    public Collection<Verb> values() {
        return internalMap.values();
    }

    public void put(String infinitive, Verb verb) {
        internalMap.put(infinitive, verb);
    }

    public Collection<Verb> getAllVerbs(String initialValue) {
        Set<String> allInfinitives = conjugationMap.get(initialValue);
        if(allInfinitives == null) return Collections.EMPTY_SET;
        Collection<Verb> verbCollection = new ArrayList<>();
        for(String infinitive :allInfinitives) {
            verbCollection.add(new Verb(internalMap.get(infinitive)));
        }
        return verbCollection;
    }

    public Verb getVerb(String initialValue) {
        return internalMap.get(initialValue);
    }
}

package org.patrologia.translator.basicelements;

import java.util.*;

public class ConjugationMap {

    private HashMap<String, Set<String>> internalMap = new HashMap<>();
    private Accentuer accentuer;

    public ConjugationMap(Accentuer accentuer) {
        this.accentuer = accentuer;
    }

    public void put(String key, String value) {
        Set<String> values = null;
        if(internalMap.containsKey(key)) {
            values = internalMap.get(key);
        } else {
            values = new HashSet<>();
        }
        values.add(value);
        internalMap.put(key, values);
    }

    public boolean containsKey(String initialValue) {
        return internalMap.containsKey(initialValue) || internalMap.containsKey(accentuer.unaccentued(initialValue));
    }

    public Set<String> get(String key) {
        return internalMap.get(key) != null ? internalMap.get(key) : internalMap.get(accentuer.unaccentued(key));
    }

    public List<String> keySet() {
        List<String> allKeys = new ArrayList<>();
        for(String keySet : internalMap.keySet()) {
            allKeys.addAll(internalMap.get(keySet));
        }
        return allKeys;
    }

    public Set<String> allConjugations() {
        return internalMap.keySet();
    }

    public String getFirstKey(String initialValue) {
        if(!hasKey(initialValue)) {
            return null;
        }
        return (String)internalMap.get(initialValue).toArray()[0];
    }

    private boolean hasKey(String initialValue) {
        return internalMap.get(initialValue) != null;
    }
}

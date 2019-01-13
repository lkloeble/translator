package patrologia.translator.basicelements.verb;

import patrologia.translator.basicelements.Accentuer;

import java.util.*;

public class ConjugationMap {

    private HashMap<String, Set<String>> internalMap = new HashMap<>();
    private Accentuer accentuer;
    private InfinitiveBuilder infinitiveBuilder;

    public ConjugationMap(Accentuer accentuer, InfinitiveBuilder infinitiveBuilder) {
        this.infinitiveBuilder = infinitiveBuilder;
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
        Set<String> firstResults = internalMap.get(key) != null ? internalMap.get(key) : internalMap.get(accentuer.unaccentued(key));
        if(firstResults == null) {
            firstResults = internalMap.get(accentuer.unaccentuedWithSofit(key));
        }
        if(firstResults != null) return firstResults;
        return internalMap.get(infinitiveBuilder.getInfinitiveFromInitialValue(key));
    }

    public List<String> keySet() {
        Set<String> allKeys = new HashSet<>();
        for(String keySet : internalMap.keySet()) {
            allKeys.addAll(internalMap.get(keySet));
            allKeys.add(keySet);
        }
        return new ArrayList<>(allKeys);
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

    @Override
    public String toString() {
        return "ConjugationMap{" +
                "internalMap=" + internalMap.size() +
                " values, accentuer=" + accentuer +
                '}';
    }
}

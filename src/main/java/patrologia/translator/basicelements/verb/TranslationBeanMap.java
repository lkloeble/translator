package patrologia.translator.basicelements.verb;

import java.util.*;

public class TranslationBeanMap {

    private Map<String, List<String>> map = new HashMap<>();

    public void addGlobalVerbKey(String root) {
        map.put(root, new ArrayList<>());
    }

    public void addConjugationForGlobalKey(String root, String infinitive) {
        List<String> conjugations = map.get(root);
        conjugations.add(infinitive);
        map.put(root,conjugations);
    }

    public List<String> getAllTimes(String root) {
        return map.containsKey(root) ? map.get(root) : Collections.EMPTY_LIST;
    }

}

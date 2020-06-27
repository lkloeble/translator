package patrologia.translator.basicelements;

import patrologia.translator.conjugation.ConjugationPart2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TranslationRules {

    private Map<Integer, TranslationRule> translationRuleMap = new HashMap<>();

    public TranslationRules(String translationDefinition) {
        String[] split = translationDefinition.split("@");
        for(int ruleIndice = 0;ruleIndice<split.length;ruleIndice++) {
            translationRuleMap.put(ruleIndice,TranslationRuleFactory.getTranslationRule(split[ruleIndice]));
        }
    }

    public List<ConjugationPart2> transform(List<ConjugationPart2> conjugationPartList, String time) {
        Set<Integer> integers = translationRuleMap.keySet();
        for(Integer ruleIndice  : integers) {
            TranslationRule translationRule = translationRuleMap.get(ruleIndice);
            if(translationRule == null || !translationRule.conjugationName.equals(time)) continue;
            conjugationPartList = translationRule.transform(conjugationPartList);
        }
        return conjugationPartList;
    }

    public boolean hasTransformationForThisTime(String time) {
        for(TranslationRule translationRule : translationRuleMap.values()) {
            if(translationRule != null && translationRule.concernsThisTime(time)) return true;
        }
        return false;
    }
}

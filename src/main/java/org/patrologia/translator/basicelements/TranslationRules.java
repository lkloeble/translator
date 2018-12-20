package patrologia.translator.basicelements;

import patrologia.translator.conjugation.ConjugationPart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by lkloeble on 01/10/2017.
 */
public class TranslationRules {

    private Map<Integer, TranslationRule> translationRuleMap = new HashMap<>();

    public TranslationRules(String translationDefinition) {
        String[] split = translationDefinition.split("@");
        for(int ruleIndice = 0;ruleIndice<split.length;ruleIndice++) {
            translationRuleMap.put(ruleIndice,TranslationRuleFactory.getTranslationRule(split[ruleIndice]));
        }
    }

    public List<ConjugationPart> transform(List<ConjugationPart> conjugationPartList, String time) {
        Set<Integer> integers = translationRuleMap.keySet();
        for(Integer ruleIndice  : integers) {
            TranslationRule translationRule = translationRuleMap.get(ruleIndice);
            if(translationRule == null || !translationRule.conjugationName.equals(time)) continue;
            conjugationPartList = translationRule.transform(conjugationPartList);
        }
        return conjugationPartList;
    }
}

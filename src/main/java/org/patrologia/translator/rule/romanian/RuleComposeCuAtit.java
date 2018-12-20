package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 31/05/2017.
 */
public class RuleComposeCuAtit extends Rule {

    private RomanianRuleFactory romanianRuleFactory;
    private String[] rulesToAdd;

    public RuleComposeCuAtit(RomanianRuleFactory romanianRuleFactory, String[] rulesToAdd) {
        this.romanianRuleFactory = romanianRuleFactory;
        this.rulesToAdd = rulesToAdd;
    }

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("atit".equals(followingWord.getInitialValue())) {
            word.setInitialValue("cuatit");
            word.setRoot("cuatit");
            Preposition cuAtit = (Preposition)word;
            cuAtit.emptyRules();
            for(String ruleName : rulesToAdd) {
                cuAtit.addRule(romanianRuleFactory.getRuleByName(ruleName,"cuAtit"));
            }
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

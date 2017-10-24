package org.patrologia.translator.rule.hebrew;

import org.patrologia.translator.rule.Rule;
import org.patrologia.translator.rule.RuleFactory;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewRuleFactory extends RuleFactory {

    @Override
    public Rule getRuleByName(String ruleName, String preposition) {
        if(ruleName == null) {
            return null;
        }
        if("articleCopyFollowingNoun".equals(ruleName)) {
            return new ArticleCopyFollowingNoun();
        } else if("eraseFollowingMinus".equals(ruleName)) {
            return new EraseFollowingMinus();
        } else if("wawConversiveForFutureAndPastVerbs".equals(ruleName)) {
            return new RuleWawConversiveForVerbTime();
        }
        return null;
    }
}

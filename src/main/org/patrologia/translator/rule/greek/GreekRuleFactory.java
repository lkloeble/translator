package org.patrologia.translator.rule.greek;

import org.patrologia.translator.rule.Rule;
import org.patrologia.translator.rule.RuleFactory;

/**
 * Created by Laurent KLOEBLE on 08/10/2015.
 */
public class GreekRuleFactory  extends RuleFactory {

    @Override
    public Rule getRuleByName(String ruleName, String preposition) {
        if(ruleName == null) {
            return null;
        }
        String parameter = extractParameter(ruleName);
        ruleName = extractRuleNameWhenHasParameter(ruleName);
        if("electGenitiveForFollowingNoun".equals(ruleName)) {
            return new RuleElectGenitiveForFollowingNoun(parameter);
        } else if("electAccusativeForFollowingNoun".equals(ruleName)) {
            return new RuleElectAccusativeForFollowingNoun(parameter);
        } else if("electPlurialNeutralWithSingularVerbMustChangeTimeVerbToPlural".equals(ruleName)) {
            return new RulePlurialNeutralWithSingularVerbMustChangeTimeVerbToPlural();
        } else if("electDativeForFollowingNoun".equals(ruleName)) {
            return new RuleElectDativeForFollowingNoun(parameter);
        } else if("findFollowindEmerav".equals(ruleName)) {
            return new RuleKatEmeravMerge();
        } else if("findAvaMesov".equals(ruleName)) {
            return new RuleAvaMesovFinder();
        }
        return null;
    }
}

package patrologia.translator.rule.greek;

import patrologia.translator.rule.Rule;
import patrologia.translator.rule.RuleFactory;

public class GreekRuleFactory extends RuleFactory {

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

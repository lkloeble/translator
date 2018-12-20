package patrologia.translator.rule.latin;

import patrologia.translator.rule.Rule;
import patrologia.translator.rule.RuleFactory;

/**
 * Created by Laurent KLOEBLE on 25/08/2015.
 */
public class LatinRuleFactory extends RuleFactory {

    @Override
    public Rule getRuleByName(String ruleName, String preposition) {
        if(ruleName == null) {
            return null;
        }
        if("electAblativeForFollowingNoun".equals(ruleName)) {
            return new RuleElectAblativeForFollowingNoun();
        } else if("electAccusativeForFollowingNoun".equals(ruleName)) {
            return new RuleElectAccusativeForFollowingNoun();
        } else if("electDativeForFollowingNoun".equals(ruleName)) {
            return new RuleElectDativeForFollowingNoun();
        } else if("adjectiveSelectGenderRule".equals(ruleName)) {
            return new RuleAdjectiveElectGender();
        }
        return null;
    }
}

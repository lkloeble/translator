package patrologia.translator.rule.english;

import patrologia.translator.rule.Rule;
import patrologia.translator.rule.RuleFactory;

public class EnglishRuleFactory  extends RuleFactory {

    @Override
    public Rule getRuleByName(String ruleName, String preposition) {
        if (ruleName == null) {
            return null;
        }
        String ruleNameWithoutParameter = ruleName.indexOf("(") > 0 ? ruleName.substring(0, ruleName.indexOf("(")) : ruleName;
        String parameter = extractParameter(ruleName);
        if ("electPluralForFollowingPluralNoun".equals(ruleNameWithoutParameter)) {
            return new RuleElectPluralForFollowingPluralNoun();
        } else if("electFeminineForFollowingNoun".equals(ruleNameWithoutParameter)) {
            return new RuleElectFeminineForFollowingNoun();
        }  else if("electPluralForFollowingVerb".equals(ruleNameWithoutParameter)) {
            return new RuleElectCustomArePosition(ruleName);
        } else if("electRuleByAnyChance".equals(ruleNameWithoutParameter)) {
            return new RuleByAnyChanceExpression();
        } else if("electRuleByTheWay".equals(ruleNameWithoutParameter)) {
            return new RuleByTheWay();
        } else if("upthereFinder".equals(ruleName)) {
            return new RuleUpThereFinder();
        } else if("nexttoFinder".equals(ruleName)) {
            return new RuleNextToFinder();
        } else if("nooneFinder".equals(ruleName)) {
            return new RuleNoOneFinder();
        } else if("soManyFinder".equals(ruleName)) {
            return new SoManyFinder();
        } else if("searchIsAndReplaceByThereis".equals(ruleName)) {
            return new RuleThereIsFusion();
        } else if("searchIsBeforeReplaceByThereIs".equals(ruleName))  {
            return new RuleIsThereFusion();
        } else if("electImperativeForFollowingVerb".equals(ruleName)) {
            return new RuleElectImperativeConjugation();
        } else if("electInfinitiveForFollowingVerb".equals(ruleName)) {
            return new RuleElectInfinitiveForFollowingVerb();
        } else if("searchTooMany".equals(ruleName)) {
            return new RuleTooMany();
        } else if("searchTooMuch".equals(ruleName)) {
            return new RuleTooMuch();
        } else if("searchHowMany".equals(ruleName)) {
            return new RuleHowMany();
        } else if("searchHowMuch".equals(ruleName)) {
            return new RuleHowMuch();
        } else if("electPersonInFollowingVerb".equals(ruleNameWithoutParameter)) {
            return new RuleElectPersonInFollowingVerb(parameter);
        } else if("recomposeOneWould".equals(ruleName)) {
            return new RuleOneWould();
        } else if("ofcourseFinder".equals(ruleName)) {
            return new RuleOfCourse();
        } else if ("followUpVerbFinder".equals(ruleName)) {
            return new RuleVerbUpFinder();
        } else if ("rootOutVerbFinder".equals(ruleName)) {
            return new RootOutVerbFinder();
        } else if ("comeBackVerbFinder".equals(ruleName)) {
            return new RuleComeBackFinder();
        }
        return null;
    }
}

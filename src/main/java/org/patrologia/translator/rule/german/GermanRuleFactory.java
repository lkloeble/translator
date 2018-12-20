package patrologia.translator.rule.german;

import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.rule.Rule;
import patrologia.translator.rule.RuleElectPersonInFollowingVerb;
import patrologia.translator.rule.RuleFactory;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanRuleFactory extends RuleFactory {

    private VerbRepository verbRepository;

    public GermanRuleFactory(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    @Override
    public Rule getRuleByName(String ruleName, String preposition) {
        if(ruleName == null) {
            return null;
        }
        String ruleNameWithoutParameter = extractRuleNameWhenHasParameter(ruleName);
        String parameter = extractParameter(ruleName);
        if("searchPrecedingVerbsToSuffixThemAndTranslate".equals(ruleNameWithoutParameter)) {
            return new RuleSearchPrecedingVerbs(verbRepository, preposition);
        } else if("gutenTagReplace".equals(ruleNameWithoutParameter)) {
            return new RuleGutenTagReplace();
        } else if("gutenAbendReplace".equals(ruleNameWithoutParameter)) {
            return new RuleGutenAbendReplace();
        } else if("gutenMorgenReplace".equals(ruleNameWithoutParameter)) {
            return new RuleGutenMorgenReplace();
        } else if("verbLeadingPreposition".equals(ruleNameWithoutParameter)) {
            return new RuleVerbLeadingPreposition(verbRepository);
        } else if("esGibtReplace".equals(ruleName)) {
            return new RuleEsGibtFinder();
        } else if("gibtEsReplace".equals(ruleName)) {
            return new RuleGibtEsFinder();
        } else if("esBeforeVerb".equals(ruleName)) {
            return new RuleEsBeforeVerb();
        } else if("switchWithInfinitiveForm".equals(ruleName)) {
            return new RuleSwitchWithInfinitiveForm();
        } else if("electPersonInFollowingVerb".equals(ruleNameWithoutParameter)) {
            return new RuleElectPersonInFollowingVerb(parameter);
        } else if("recomposeAufGrund".equals(ruleName)) {
            return new RuleAufGrundReplace();
        } else if("electAccusativeForFollowingNoun".equals(ruleName)) {
            return new RuleElectAccusativeForFollowingNoun();
        } else if("esGabReplace".equals(ruleName)) {
            return new RuleEsGabFinder();
        } else if("gabEsReplace".equals(ruleName)) {
            return new RuleGabEsFinder();
        }
        return null;
    }
}

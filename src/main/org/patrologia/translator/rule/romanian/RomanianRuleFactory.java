package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.rule.Rule;
import org.patrologia.translator.rule.RuleFactory;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianRuleFactory extends RuleFactory {

    private VerbRepository verbRepository;

    public RomanianRuleFactory(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    @Override
    public Rule getRuleByName(String ruleName, String preposition) {
        if(ruleName == null) {
            return null;
        }
        if("electPluralForFollowingWord".equals(ruleName)) {
            return new RuleElectPluralForFollowingWord();
        }  else if("oArticleFinder".equals(ruleName)) {
            return new RuleOArticleFinder();
        } else if("electAsVreaFinder".equals(ruleName)) {
            return new RuleAsVreaFinder();
        } else if("varogFinder".equals(ruleName)) {
            return new RuleVaRogFinder();
        } else if("terogFinder".equals(ruleName)) {
            return new RuleTeRogFinder();
        } else if("dinpacateFinder".equals(ruleName)) {
            return new RuleDinPacateFinder();
        } else if("dinfatsaFinder".equals(ruleName)) {
            return new RuleDinFatsaFinder();
        } else if("recomposeCeFel".equals(ruleName)) {
            return new RuleRecomposeCeFel();
        } else if("recomposeDece".equals(ruleName)) {
            return new RuleComposeDece();
        } else if("findInfinitiveConstruction".equals(ruleName)) {
            return new RuleInfinitiveConstruction(verbRepository);
        } else if("findGenitive".equals(ruleName)) {
            return new RuleFindGenitive();
        } else if("maiPresusFinder".equals(ruleName)){
            return new RuleRecomposeMaiPresus();
        } else if("maiBineFinder".equals(ruleName))  {
            return new RuleRecomposeMaiBine();
        } else if("recomposeALui".equals(ruleName)) {
            return new RuleRecomposeALui();
        } else if("lookForPrecedingGenitive".equals(ruleName)) {
            return new RuleLookForPrecedingGenitive();
        } else if("spreCareFinder".equals(ruleName)) {
            return new RuleComposeSpreCare();
        } else if("recomposeAFost".equals(ruleName)) {
            return new RuleComposeAFost();
        } else if("dinVeciFinder".equals(ruleName)) {
            return new RuleComposeDinVeci();
        } else if("recomposeCuAtit".equals(ruleName)) {
            return new RuleComposeCuAtit(this, new String[]{"recomposeCuAtitMaiBine"});
        } else if("recomposeCuAtitMaiBine".equals(ruleName)) {
            return new RuleComposeCuAtitMaiBine();
        }  else if("recomposeArVerb".equals(ruleName)) {
            return new RuleComposeArVerb();
        } else if("recomposeVaVerb".equals(ruleName)) {
            return new RuleComposeVaVerb();
        } else if("electGenitiveForFollowingNoun".equals(ruleName)) {
            return new RuleElectGenitiveForFollowingNoun();
        }
        return null;
    }
}

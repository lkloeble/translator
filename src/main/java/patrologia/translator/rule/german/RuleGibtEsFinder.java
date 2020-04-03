package patrologia.translator.rule.german;

import patrologia.translator.rule.Rule;
import patrologia.translator.rule.english.TwoWordsFusionRule;

public class RuleGibtEsFinder extends TwoWordsFusionRule {

    public RuleGibtEsFinder() {
        super("es","gibt","esgibt", false);
    }

}

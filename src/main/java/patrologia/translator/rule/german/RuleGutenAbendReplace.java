package patrologia.translator.rule.german;

import patrologia.translator.rule.Rule;
import patrologia.translator.rule.english.TwoWordsFusionRule;

public class RuleGutenAbendReplace extends TwoWordsFusionRule {

    public RuleGutenAbendReplace() {
        super("guten","abend","gutenabend");
    }

}

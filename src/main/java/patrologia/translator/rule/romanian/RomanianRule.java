package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public abstract class RomanianRule extends Rule {

    protected String substitutionPreposition;

    protected void updatePreposition(Word word) {
        if (substitutionPreposition != null) {
            word.updateInitialValue(substitutionPreposition);
            word.updateRoot(substitutionPreposition);
        }
    }

}

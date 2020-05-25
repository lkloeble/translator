package patrologia.translator.rule.greek;

import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public abstract class GreekRule extends Rule {

    protected String substitutionPreposition;

    protected void updatePreposition(Word word) {
        if (substitutionPreposition != null) {
            word.updateInitialValue(substitutionPreposition);
            word.updateRoot(substitutionPreposition);
        }
    }
}

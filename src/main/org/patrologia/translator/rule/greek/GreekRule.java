package org.patrologia.translator.rule.greek;

import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 22/03/2016.
 */
public abstract class GreekRule extends Rule {

    protected String substitutionPreposition;

    protected void updatePreposition(Word word) {
        if (substitutionPreposition != null) {
            word.updateInitialValue(substitutionPreposition);
            word.updateRoot(substitutionPreposition);
        }
    }
}

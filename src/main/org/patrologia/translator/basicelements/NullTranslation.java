package org.patrologia.translator.basicelements;

import org.patrologia.translator.basicelements.modifier.FinalModifier;

/**
 * Created by Laurent KLOEBLE on 01/09/2015.
 */
public class NullTranslation extends Translation {

    public NullTranslation(FinalModifier finalModifier) {
        this.finalModifier = finalModifier;
    }

    @Override
    public String getPossibleTranslation() {
        return null;
    }
}

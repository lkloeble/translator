package org.patrologia.translator.basicelements;

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

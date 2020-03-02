package patrologia.translator.basicelements;

import patrologia.translator.basicelements.modifier.FinalModifier;

public class NullTranslation extends Translation {

    public NullTranslation(FinalModifier finalModifier) {
        this.finalModifier = finalModifier;
    }

    @Override
    public String getPossibleTranslation() {
        return null;
    }
}

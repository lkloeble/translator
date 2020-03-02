package patrologia.translator.casenumbergenre.hebrew;

import patrologia.translator.casenumbergenre.Case;

public class HebrewCase extends Case {

    public HebrewCase(HebrewCase toClone) {
        this.differentier = toClone.differentier;
    }

    protected String differentier;

    public HebrewCase() {
    }

    @Override
    protected String getDifferentier() {
        return differentier;
    }

    @Override
    public void updateDifferentier(String newDifferentier) {
        this.differentier = newDifferentier;
    }

    public static HebrewCase getClone(HebrewCase aCase) {
        if(aCase instanceof HebrewConstructedStateCase) {
            return new HebrewConstructedStateCase(aCase);
        } else if(aCase instanceof HebrewDeclinedNominativeCase) {
            return new HebrewDeclinedNominativeCase(aCase);
        } else if(aCase instanceof NominativeHebrewCase) {
            return new NominativeHebrewCase(aCase);
        } else if(aCase instanceof HebrewDirectionalCase) {
            return new HebrewDirectionalCase(aCase);
        }
        return new HebrewCase(aCase);
    }
}

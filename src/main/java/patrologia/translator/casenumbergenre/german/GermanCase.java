package patrologia.translator.casenumbergenre.german;

import patrologia.translator.casenumbergenre.Case;

public class GermanCase  extends Case {

    public GermanCase() {
    }

    protected String differentier;

    @Override
    public String getDifferentier() {
        return differentier;
    }
}

package org.patrologia.translator.casenumbergenre.german;

import org.patrologia.translator.casenumbergenre.Case;

/**
 * Created by lkloeble on 07/08/2017.
 */
public class GermanCase extends Case {

    public GermanCase() {
    }

    protected String differentier;

    @Override
    public String getDifferentier() {
        return differentier;
    }
}

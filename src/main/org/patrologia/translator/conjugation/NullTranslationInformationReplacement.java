package org.patrologia.translator.conjugation;

import org.patrologia.translator.basicelements.TranslationInformationReplacement;

/**
 * Created by Laurent KLOEBLE on 19/10/2015.
 */
public class NullTranslationInformationReplacement extends TranslationInformationReplacement {

    public NullTranslationInformationReplacement() {
        super(NULL_REPLACEMENT_STRING);
    }

    @Override
    public String getTimeException(int indice) {
        return "ZZZZZZ";
    }
}

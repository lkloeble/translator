package org.patrologia.translator.linguisticimplementations;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianLanguageSelector implements SpecificLanguageSelector {

    @Override
    public boolean electConstruction(String value, String toTranslate) {
        return value.contains(toTranslate);
    }
}

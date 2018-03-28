package org.patrologia.translator.basicelements;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public enum WordType {

    PREPOSITION, NOUN, VERB, NO_TRANSLATION, UNKNOWN;

    public String getDefinitionString() {
        return toString().substring(0,4);

    }
}

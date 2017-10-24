package org.patrologia.translator.basicelements;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public enum WordType {

    PREPOSITION, NOUN, VERB, DEMONSTRATIVE, NO_TRANSLATION, UNKNOWN;

    public char getCorrespondantTypeSymbol() {
        switch(this) {
            case PREPOSITION:return 'p';
            case NOUN:return 'n';
            case VERB:return 'v';
            case DEMONSTRATIVE:return 'd';
            case NO_TRANSLATION:return 'n';
            default: return 'u';
        }
    }
}

package patrologia.translator.basicelements;

import patrologia.translator.conjugation.ConjugationPosition;

public enum Language {

    LATIN, GREEK, HEBREW, FRENCH, UNKNOWN, ROMANIAN, GERMAN, ENGLISH;

    public ConjugationPosition getDefaultPositionInTranslationTableVerb() {
        switch(this) {
            case ENGLISH:return ConjugationPosition.SINGULAR_THIRD_PERSON;
            case GERMAN:return ConjugationPosition.SINGULAR_THIRD_PERSON;
            default:return ConjugationPosition.UNKNOWN;
        }
    }

}

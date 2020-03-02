package patrologia.translator.basicelements;

public enum Language {

    LATIN, GREEK, HEBREW, FRENCH, UNKNOWN, ROMANIAN, GERMAN, ENGLISH;

    public int getDefaultPositionInTranslationTableVerb() {
        switch(this) {
            case ENGLISH:return 2;
            case GERMAN:return 2;
            default:return -1;
        }
    }

}

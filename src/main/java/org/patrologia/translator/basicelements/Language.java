package patrologia.translator.basicelements;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
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

package patrologia.translator.casenumbergenre.german;

import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseFactory;
import patrologia.translator.casenumbergenre.NullCase;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanCaseFactory extends CaseFactory {

    private static final String EMPTY_DIFFERENTIER = "";

    @Override
    public Case getCaseByStringPattern(String pattern, String differentier) {
        switch(pattern) {
            case "nom":
                return new NominativeGermanCase(differentier);
            case "gen":
                return new GenitiveGermanCase(differentier);
            case "dat":
                return new DativeGermanCase(differentier);
            case "acc":
                return new AccusativeGermanCase(differentier);
        }
        return new NullCase();
    }

    public static GenitiveGermanCase getGenitive() {
        return new GenitiveGermanCase(EMPTY_DIFFERENTIER);
    }

    public static AccusativeGermanCase getAccusative() {
        return new AccusativeGermanCase(EMPTY_DIFFERENTIER);
    }
}

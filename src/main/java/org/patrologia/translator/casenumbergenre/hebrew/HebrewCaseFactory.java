package patrologia.translator.casenumbergenre.hebrew;

import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseFactory;
import patrologia.translator.casenumbergenre.NullCase;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewCaseFactory extends CaseFactory {

    @Override
    public Case getCaseByStringPattern(String pattern, String differencier) {
        switch(pattern) {
            case "nom":
                return new NominativeHebrewCase(differencier);
            case  "cst":
                return new HebrewConstructedStateCase(differencier);
            case "dec" :
                return new HebrewDeclinedNominativeCase(differencier);
            case "dir" :
                return new HebrewDirectionalCase(differencier);
        }
        return new NullCase();
    }
}

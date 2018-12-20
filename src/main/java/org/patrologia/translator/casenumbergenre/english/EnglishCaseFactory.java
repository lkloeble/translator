package patrologia.translator.casenumbergenre.english;

import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseFactory;
import patrologia.translator.casenumbergenre.NullCase;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class EnglishCaseFactory extends CaseFactory {

    @Override
    public Case getCaseByStringPattern(String pattern, String differentier) {
        if(pattern == null) {
            return null;
        }
        if("nom".equals(pattern.toLowerCase())) {
            return getNominative();
        }
        return null;

    }

    public static NominativeEnglishCase getNominative() {
        return NominativeEnglishCase.getInstance();
    }
}

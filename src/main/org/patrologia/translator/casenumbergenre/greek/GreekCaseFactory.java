package org.patrologia.translator.casenumbergenre.greek;

import org.patrologia.translator.casenumbergenre.Case;
import org.patrologia.translator.casenumbergenre.CaseFactory;
import org.patrologia.translator.casenumbergenre.NullCase;

/**
 * Created by Laurent KLOEBLE on 08/10/2015.
 */
public class GreekCaseFactory extends CaseFactory {

    private final static String EMPTY_DIFFERENTIER = "";

    @Override
    public Case getCaseByStringPattern(String pattern, String differentier) {
        if(pattern == null || pattern.length() != 3) {
            return new NullCase();
        }
        if("nom".equals(pattern.toLowerCase())) {
            return getNominative();
        }
        else if("acc".equals(pattern.toLowerCase())) {
            return getAccusative();
        }
        else if("gen".equals(pattern.toLowerCase())) {
            return getGenitive();
        }
        else if("dat".equals(pattern.toLowerCase())) {
            return getDative();
        }
        else if("voc".equals(pattern.toLowerCase())) {
            return getVocative();
        }
        return new NullCase();
    }

    public static NominativeGreekCase getNominative() {
        return new NominativeGreekCase(EMPTY_DIFFERENTIER);
    }

    public static GenitiveGreekCase getGenitive() {
        return new GenitiveGreekCase(EMPTY_DIFFERENTIER);
    }

    public static AccusativeGreekCase getAccusative() {
        return new AccusativeGreekCase(EMPTY_DIFFERENTIER);
    }

    public static DativeGreekCase getDative() {
        return new DativeGreekCase(EMPTY_DIFFERENTIER);
    }

    public static VocativeGreekCase getVocative() {
        return VocativeGreekCase.getInstance();
    }
}

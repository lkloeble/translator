package org.patrologia.translator.casenumbergenre.latin;

import org.patrologia.translator.casenumbergenre.CaseFactory;
import org.patrologia.translator.casenumbergenre.Case;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public class LatinCaseFactory extends CaseFactory {

    @Override
    public Case getCaseByStringPattern(String pattern, String differentier) {
        if(pattern == null) {
            return null;
        }
        if("abl".equals(pattern.toLowerCase())) {
            return getAblative();
        } else if("nom".equals(pattern.toLowerCase())) {
            return getNominative();
        } else if("gen".equals(pattern.toLowerCase())) {
            return getGenitive();
        } else if("acc".equals(pattern.toLowerCase())) {
            return getAccusative();
        } else if("dat".equals(pattern.toLowerCase())) {
            return getDative();
        } else if("voc".equals(pattern.toLowerCase())) {
            return getVocative();
        }
        return null;
    }

    public static NominativeLatinCase getNominative() {
        return NominativeLatinCase.getInstance();
    }

    public static AccusativeLatinCase getAccusative() {
        return AccusativeLatinCase.getInstance();
    }

    public static GenitiveLatinCase getGenitive() {
        return GenitiveLatinCase.getInstance();
    }

    public static DativeLatinCase getDative() {
        return DativeLatinCase.getInstance();
    }

    public static AblativeLatinCase getAblative() {
        return AblativeLatinCase.getInstance();
    }

    public static VocativeLatinCase getVocative() {
        return VocativeLatinCase.getInstance();
    }


}

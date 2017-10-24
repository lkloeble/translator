package org.patrologia.translator.casenumbergenre.latin;

import org.patrologia.translator.casenumbergenre.Case;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public abstract class LatinCase extends Case {

    public static Case getCaseByName(String caseName) {
        if("abl".equals(caseName)) {
            return AblativeLatinCase.getInstance();
        } else if("dat".equals(caseName)) {
            return DativeLatinCase.getInstance();
        } else if("acc".equals(caseName)) {
            return AccusativeLatinCase.getInstance();
        } else if("gen".equals(caseName)) {
            return GenitiveLatinCase.getInstance();
        } else if("nom".equals(caseName)) {
            return NominativeLatinCase.getInstance();
        }
        return null;
    }

}

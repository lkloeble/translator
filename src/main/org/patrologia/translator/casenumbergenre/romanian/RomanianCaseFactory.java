package org.patrologia.translator.casenumbergenre.romanian;

import org.patrologia.translator.casenumbergenre.Case;
import org.patrologia.translator.casenumbergenre.CaseFactory;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianCaseFactory extends CaseFactory {

    @Override
    public Case getCaseByStringPattern(String pattern, String differentier) {
        if(pattern == null) {
            return null;
        }
        if("nom".equals(pattern.toLowerCase())) {
            return new NominativeRomanianCase(differentier);
        } else if("gen".equals(pattern.toLowerCase())) {
            return new GenitiveRomanianCase(differentier);
        }
        return null;
    }


}

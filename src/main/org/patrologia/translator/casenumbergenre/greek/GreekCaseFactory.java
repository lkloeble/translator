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
            return new NominativeGreekCase(differentier);
        }
        else if("acc".equals(pattern.toLowerCase())) {
            return new AccusativeGreekCase(differentier);
        }
        else if("gen".equals(pattern.toLowerCase())) {
            return new GenitiveGreekCase(differentier);
        }
        else if("dat".equals(pattern.toLowerCase())) {
            return new DativeGreekCase(differentier);
        }
        else if("voc".equals(pattern.toLowerCase())) {
            return new VocativeGreekCase(differentier);
        }
        return new NullCase();
    }
}

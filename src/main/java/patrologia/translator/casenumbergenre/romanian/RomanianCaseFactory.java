package patrologia.translator.casenumbergenre.romanian;

import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseFactory;

public class RomanianCaseFactory  extends CaseFactory {

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

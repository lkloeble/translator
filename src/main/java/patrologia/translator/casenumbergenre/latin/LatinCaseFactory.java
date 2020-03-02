package patrologia.translator.casenumbergenre.latin;

import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseFactory;

public class LatinCaseFactory extends CaseFactory {

    @Override
    public Case getCaseByStringPattern(String pattern, String differentier) {
        if(pattern == null) {
            return null;
        }
        if("abl".equals(pattern.toLowerCase())) {
            return new AblativeLatinCase(differentier);
        } else if("nom".equals(pattern.toLowerCase())) {
            return new NominativeLatinCase(differentier);
        } else if("gen".equals(pattern.toLowerCase())) {
            return new GenitiveLatinCase(differentier);
        } else if("acc".equals(pattern.toLowerCase())) {
            return new AccusativeLatinCase(differentier);
        } else if("dat".equals(pattern.toLowerCase())) {
            return new DativeLatinCase(differentier);
        } else if("voc".equals(pattern.toLowerCase())) {
            return new VocativeLatinCase(differentier);
        }
        return null;
    }



}

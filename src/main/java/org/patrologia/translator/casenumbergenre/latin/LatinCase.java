package patrologia.translator.casenumbergenre.latin;

import patrologia.translator.casenumbergenre.Case;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public abstract class LatinCase extends Case {

    protected String differentier;


    public static Case getCaseByName(String caseName) {
        if("abl".equals(caseName)) {
            return new AblativeLatinCase(null);
        } else if("dat".equals(caseName)) {
            return new DativeLatinCase(null);
        } else if("acc".equals(caseName)) {
            return new AccusativeLatinCase(null);
        } else if("gen".equals(caseName)) {
            return new GenitiveLatinCase(null);
        } else if("nom".equals(caseName)) {
            return new NominativeLatinCase(null);
        }
        return null;
    }

}

package org.patrologia.translator.conjugation.latin;

import java.util.Collections;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 24/09/2015.
 */
public class GerondiveConjugation extends LatinConjugation {

    public static final String GERONDIVE_LATIN_NAME = "GER";
    public static final String GERONDIVE_SYNONYM_LATIN_NAME = "INFINITIVE";

    public GerondiveConjugation(List<String> conjugationElements) {
        super(conjugationElements, null);
    }


    @Override
    public List<String> getCongujationByTimePattern(String timePattern) {
        if(timePattern != null && !timePattern.equals(GERONDIVE_LATIN_NAME)) {
            return Collections.EMPTY_LIST;
        }
        return allEndings.get(timePattern) != null ? allEndings.get(timePattern) : Collections.EMPTY_LIST;
    }



    @Override
    public String getTime() {
        return GERONDIVE_LATIN_NAME;
    }

    @Override
    public String getNewBase(String[] originBase) {
        return originBase[0] + originBase[3].charAt(0) + "nd" + "TOERASE" ;
    }

    @Override
    public String rootCorrectionByTimePattern(String root, String timePattern) {
        return root.replace("TOERASE","");
    }

    @Override
    public String getSynonym() {
        return GERONDIVE_SYNONYM_LATIN_NAME;
    }
}

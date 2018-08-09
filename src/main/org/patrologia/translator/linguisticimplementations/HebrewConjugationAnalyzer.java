package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.ConjugationGenderAnalyser;
import org.patrologia.translator.casenumbergenre.Gender;

public class HebrewConjugationAnalyzer  implements ConjugationGenderAnalyser {

    @Override
    public Gender getGenderByConjugationCode(String conjugationCode) {
        if("im".equals(conjugationCode)) {
            return Gender.getGenderByCode("masc");
        } else if("empty".equals(conjugationCode)) {
            return Gender.getGenderByCode("neut");
        }
        return null;
    }

}

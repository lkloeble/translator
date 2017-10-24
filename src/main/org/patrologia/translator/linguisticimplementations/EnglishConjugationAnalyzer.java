package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.ConjugationGenderAnalyser;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.casenumbergenre.english.EnglishGender;

/**
 * Created by lkloeble on 22/05/2017.
 */
public class EnglishConjugationAnalyzer implements ConjugationGenderAnalyser {

    @Override
    public Gender getGenderByConjugationCode(String conjugationCode) {
        if("normfem".equals(conjugationCode)) {
            return new EnglishGender(Gender.FEMININE);
        }
        return null;
    }
}

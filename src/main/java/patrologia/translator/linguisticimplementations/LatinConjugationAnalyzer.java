package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.ConjugationGenderAnalyser;
import patrologia.translator.casenumbergenre.Gender;

public class LatinConjugationAnalyzer implements ConjugationGenderAnalyser {

    @Override
    public Gender getGenderByConjugationCode(String conjugationCode) {
        if("us-i".equals(conjugationCode)) {
            return new Gender(Gender.MASCULINE);
        } else if ("a-ae".equals(conjugationCode)) {
            return new Gender(Gender.FEMININE);
        } else if("us-us".equals(conjugationCode)) {
            return new Gender(Gender.MASCULINE);
        }
        return new Gender(Gender.NEUTRAL);
    }


}

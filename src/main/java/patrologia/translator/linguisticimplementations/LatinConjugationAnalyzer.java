package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.ConjugationGenderAnalyser;
import patrologia.translator.casenumbergenre.Gender;

public class LatinConjugationAnalyzer implements ConjugationGenderAnalyser {

    @Override
    public Gender getGenderByConjugationCode(String conjugationCode) {
        if("us-i".equals(conjugationCode)) {
            return new Gender(Gender.MASCULINE);
        } else if("us-us".equals(conjugationCode)) {
            return new Gender(Gender.MASCULINE);
        } else if("or-oris".equals(conjugationCode)) {
            return new Gender(Gender.MASCULINE);
        } else if("x(us)-i".equals(conjugationCode)) {
            return new Gender(Gender.MASCULINE);
        } else if ("a-ae".equals(conjugationCode)) {
            return new Gender(Gender.FEMININE);
        } else if ("as-atis".equals(conjugationCode)) {
            return new Gender(Gender.FEMININE);
        } else if ("s-tis2".equals(conjugationCode)) {
            return new Gender(Gender.FEMININE);
        }
        return new Gender(Gender.NEUTRAL);
    }


}

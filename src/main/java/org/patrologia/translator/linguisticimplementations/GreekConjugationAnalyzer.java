package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.ConjugationGenderAnalyser;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.greek.GreekGender;

/**
 * Created by lkloeble on 29/07/2016.
 */
public class GreekConjugationAnalyzer implements ConjugationGenderAnalyser {

    @Override
    public GreekGender getGenderByConjugationCode(String conjugationCode) {
        if("ος-ου".equals(conjugationCode)) {
            return new GreekGender(Gender.MASCULINE);
        } else if ("η-ης".equals(conjugationCode)) {
            return new GreekGender(Gender.FEMININE);
        } else if ("α-ας".equals(conjugationCode)) {
            return new GreekGender(Gender.FEMININE);
        } else if("ηρ-ηρος".equals(conjugationCode)) {
            return new GreekGender(Gender.FEMININE);
        } else if("ας-ου".equals(conjugationCode)) {
            return new GreekGender(GreekGender.MASCULINE_FIRST_AS);
        }
        return new GreekGender(Gender.NEUTRAL);
    }
}

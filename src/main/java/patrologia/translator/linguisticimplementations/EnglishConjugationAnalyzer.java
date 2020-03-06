package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.ConjugationGenderAnalyser;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.english.EnglishGender;

public class EnglishConjugationAnalyzer implements ConjugationGenderAnalyser {

    @Override
    public Gender getGenderByConjugationCode(String conjugationCode) {
        if("normfem".equals(conjugationCode)) {
            return new EnglishGender(Gender.FEMININE);
        }
        return null;
    }
}

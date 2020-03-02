package patrologia.translator.basicelements;

import patrologia.translator.casenumbergenre.Gender;

public interface ConjugationGenderAnalyser {

    Gender getGenderByConjugationCode(String conjugationCode);
}

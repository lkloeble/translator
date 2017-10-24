package org.patrologia.translator.basicelements;

import org.patrologia.translator.casenumbergenre.Gender;

/**
 * Created by lkloeble on 23/06/2016.
 */
public interface ConjugationGenderAnalyser {

    Gender getGenderByConjugationCode(String conjugationCode);
}

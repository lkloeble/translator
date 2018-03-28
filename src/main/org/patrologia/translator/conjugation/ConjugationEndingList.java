package org.patrologia.translator.conjugation;

import java.util.ArrayList;
import java.util.List;

public class ConjugationEndingList {

    List<ConjugationEnding> conjugationEndings = new ArrayList<>();

    public List<String> getEndingsAgregate() {
        List<String> endingsValuesAgregate = new ArrayList<>();
        for(ConjugationEnding conjugationEnding : conjugationEndings) {
            endingsValuesAgregate.addAll(conjugationEnding.getEndings());
        }
        return endingsValuesAgregate;
    }

    public List<String> getPresentTimeInConjugation() {
        List<String> allEndingNames = new ArrayList<>();
        for(ConjugationEnding ending : conjugationEndings) {
            allEndingNames.add(ending.getEndingName());
        }
        return allEndingNames;//this returns only the times available in the conjugation file
    }

    public List<String> get(String time) {
        for(ConjugationEnding conjugationEnding : conjugationEndings) {
            if(conjugationEnding.getEndingName().equals(time)) return conjugationEnding.getEndings();
        }
        return null;
    }

    public void put(String conjugationName, boolean relatedToNoun, List<String> endings) {
        ConjugationEnding conjugationEnding = new ConjugationEnding(conjugationName, relatedToNoun, endings);
        conjugationEndings.add(conjugationEnding);
    }

    public boolean isRelatedToNoun(String conjugationName) {
        for(ConjugationEnding conjugationEnding : conjugationEndings) {
            if(conjugationEnding.getEndingName().equals(conjugationName)) {
                return conjugationEnding.isRelatedToNoun();
            }
        }
        return false;
    }
}

package org.patrologia.translator.translationrules.hebrew;

import org.patrologia.translator.basicelements.TranslationRule;
import org.patrologia.translator.conjugation.ConjugationPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 05/10/2017.
 */
public class SofitAccentuedLetter extends TranslationRule {

    private String letterToUpdate;
    private String updatedValue;

    public SofitAccentuedLetter(String conjugationName, String ruleParameters) {
        this.conjugationName = conjugationName;
        this.ruleParameters = ruleParameters;
        String[] parts = ruleParameters.split("\\*");
        letterToUpdate = parts[0];
        updatedValue = parts[1];
    }

    @Override
    public List<ConjugationPart> transform(List<ConjugationPart> conjugationPartList) {
        List<ConjugationPart> modifiedList = new ArrayList<>();
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(hasSofitCharacterToBeAccentued(conjugationPart.getValue())) {
                String modifiedValue = conjugationPart.getValue().replaceAll(letterToUpdate,updatedValue);
                ConjugationPart modifiedPart = new ConjugationPart(conjugationPart.getConjugationPosition(), modifiedValue, conjugationPart.getUnaccentuedValue(), conjugationPart.getPositionInDefinition());
                modifiedList.add(modifiedPart);
            } else {
                modifiedList.add(conjugationPart);
            }
        }
        return modifiedList;
    }

    private boolean hasSofitCharacterToBeAccentued(String value) {
        return value.contains(letterToUpdate);
    }

    @Override
    public String toString() {
        return "SofitAccentuedLetter{" + conjugationName + " " + ruleParameters + "}";
    }
}

package org.patrologia.translator.translationrules.hebrew;

import org.patrologia.translator.basicelements.TranslationRule;
import org.patrologia.translator.conjugation.ConjugationPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 01/10/2017.
 */
public class LeadingRootLetter extends TranslationRule {

    private String letterToUpdate;
    private String updatedValue;

    public LeadingRootLetter(String conjugationName, String ruleParameters) {
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
            String value = conjugationPart.getValue();
            if(value.startsWith(letterToUpdate)) {
                value = updatedValue + value.substring(1);
            }
            ConjugationPart modified = new ConjugationPart(conjugationPart.getConjugationPosition(),value,conjugationPart.getUnaccentuedValue(),conjugationPart.getPositionInDefinition());
            modifiedList.add(modified);
        }
        return modifiedList;
    }

    @Override
    public String toString() {
        return "LeadingRootLetter{" + conjugationName +  " " + ruleParameters + "}";
    }
}

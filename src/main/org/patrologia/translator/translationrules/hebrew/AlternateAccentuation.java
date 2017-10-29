package org.patrologia.translator.translationrules.hebrew;

import org.patrologia.translator.basicelements.TranslationRule;
import org.patrologia.translator.conjugation.ConjugationPart;
import org.patrologia.translator.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 13/10/2017.
 */
public class AlternateAccentuation extends TranslationRule {

    private String letterToUpdate;
    private String updatedValue;
    private List<Integer> indices;

    public AlternateAccentuation(String conjugationName, String ruleParameters, List<Integer> indices) {
        this.conjugationName = conjugationName;
        this.ruleParameters = ruleParameters;
        String[] parts = ruleParameters.split("\\*");
        letterToUpdate = parts[0];
        updatedValue = parts[1];
        this.indices = indices;
    }

    @Override
    public List<ConjugationPart> transform(List<ConjugationPart> conjugationPartList) {
        List<ConjugationPart> modifiedList = new ArrayList<>();
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(hasPatternToChange(conjugationPart) && isPositionAllowedForChange(conjugationPart)) {
                String alternateValue = modifyValue(conjugationPart.getValue());
                ConjugationPart alternateConjugationPart = new ConjugationPart(conjugationPart.getConjugationPosition(), alternateValue, unaccentued(alternateValue), conjugationPart.getPositionInDefinition());
                modifiedList.add(alternateConjugationPart);
                if(isAllowedForAnyPosition()) {
                    modifiedList.add(conjugationPart);
                }
            } else {
                modifiedList.add(conjugationPart);
            }
        }
        return modifiedList;
    }

    private boolean isAllowedForAnyPosition() {
        return indices.size() == 0;
    }

    private boolean isPositionAllowedForChange(ConjugationPart conjugationPart) {
        if(indices.size() == 0) return true;
        return indices.contains(conjugationPart.getPositionInDefinition());
    }

    private String modifyValue(String value) {
        return value.replace(letterToUpdate,updatedValue);
    }

    private boolean hasPatternToChange(ConjugationPart conjugationPart) {
        return conjugationPart.getValue().contains(letterToUpdate);
    }

    private String unaccentued(String value) {
        return StringUtils.unaccentuate(value);
    }

    @Override
    public String toString() {
        return "AlternateAccentuation{" + conjugationName +  " " + ruleParameters + " " + indices + "}";
    }

}

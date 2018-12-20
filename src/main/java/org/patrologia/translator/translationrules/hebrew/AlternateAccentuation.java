package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart;

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
            if(hasPatternToChange(conjugationPart) && isPositionAllowedForChange(conjugationPart, indices)) {
                String alternateValue = modifyValue(conjugationPart.getValue());
                ConjugationPart alternateConjugationPart = new ConjugationPart(conjugationPart.getConjugationPosition(), alternateValue, unaccentued(alternateValue), conjugationPart.getPositionInDefinition());
                modifiedList.add(alternateConjugationPart);
                if(isAllowedForAnyPosition(indices)) {
                    modifiedList.add(conjugationPart);
                }
            } else {
                modifiedList.add(conjugationPart);
            }
        }
        return modifiedList;
    }

    private String modifyValue(String value) {
        return value.replace(letterToUpdate,updatedValue);
    }

    private boolean hasPatternToChange(ConjugationPart conjugationPart) {
        return conjugationPart.getValue().contains(letterToUpdate);
    }

    @Override
    public String toString() {
        return "AlternateAccentuation{" + conjugationName +  " " + ruleParameters + " " + indices + "}";
    }

}

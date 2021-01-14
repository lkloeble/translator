package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart2;

import java.util.ArrayList;
import java.util.List;

public class AlternateAccentuation extends TranslationRule {

    private String letterToUpdate;
    private String updatedValue;
    private List<Integer> strictPositions;

    public AlternateAccentuation(String conjugationName, String ruleParameters, List<Integer> strictPositions) {
        this.conjugationName = conjugationName;
        this.ruleParameters = ruleParameters;
        String[] parts = ruleParameters.split("\\*");
        letterToUpdate = parts[0];
        updatedValue = parts[1];
        this.strictPositions = strictPositions;
    }

    @Override
    public List<ConjugationPart2> transform(List<ConjugationPart2> conjugationPartList) {
        List<ConjugationPart2> modifiedList = new ArrayList<>();
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(hasPatternToChange(conjugationPart) && isPositionAllowedForChange(conjugationPart, strictPositions)) {
                String alternateValue = modifyValue(conjugationPart.getValue());
                ConjugationPart2 alternateConjugationPart = new ConjugationPart2(conjugationPart.getConjugationPosition(), alternateValue, unaccentued(alternateValue), conjugationPart.getIndice(),conjugationPart.getStrictPosition());
                modifiedList.add(alternateConjugationPart);
                if(isAllowedForAnyPosition(strictPositions)) {
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

    private boolean hasPatternToChange(ConjugationPart2 conjugationPart) {
        return conjugationPart.getValue().contains(letterToUpdate);
    }

    @Override
    public String toString() {
        return "AlternateAccentuation{" + conjugationName +  " " + ruleParameters + " " + strictPositions + "}";
    }

}

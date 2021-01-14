package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart2;

import java.util.ArrayList;
import java.util.List;

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
    public List<ConjugationPart2> transform(List<ConjugationPart2> conjugationPartList) {
        List<ConjugationPart2> modifiedList = new ArrayList<>();
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(hasSofitCharacterToBeAccentued(conjugationPart.getValue())) {
                String modifiedValue = conjugationPart.getValue().replaceAll(letterToUpdate,updatedValue);
                ConjugationPart2 modifiedPart = new ConjugationPart2(conjugationPart.getConjugationPosition(), modifiedValue, conjugationPart.getUnaccentuedValue(), conjugationPart.getIndice(),conjugationPart.getStrictPosition());
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

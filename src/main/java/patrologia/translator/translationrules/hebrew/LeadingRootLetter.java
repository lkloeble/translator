package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart2;

import java.util.ArrayList;
import java.util.List;

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
    public List<ConjugationPart2> transform(List<ConjugationPart2> conjugationPartList) {
        List<ConjugationPart2> modifiedList = new ArrayList<>();
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            String value = conjugationPart.getValue();
            if(value.startsWith(letterToUpdate)) {
                value = updatedValue + value.substring(1);
            }
            ConjugationPart2 modified = new ConjugationPart2(conjugationPart.getConjugationPosition(),value,conjugationPart.getUnaccentuedValue(),conjugationPart.getIndice());
            modifiedList.add(modified);
        }
        return modifiedList;
    }

    @Override
    public String toString() {
        return "LeadingRootLetter{" + conjugationName +  " " + ruleParameters + "}";
    }
}

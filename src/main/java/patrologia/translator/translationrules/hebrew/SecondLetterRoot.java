package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart2;

import java.util.ArrayList;
import java.util.List;

public class SecondLetterRoot  extends TranslationRule {

    private String letterToUpdate;
    private String updatedValue;

    public SecondLetterRoot(String conjugationName, String ruleParameters) {
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
            if(IsSecondLetterOfRoot(value,letterToUpdate)) {
                int letterIndice = value.indexOf(letterToUpdate);
                value = value.substring(0,letterIndice) + updatedValue  + value.substring(letterIndice+letterToUpdate.length());
            }
            ConjugationPart2 modified = new ConjugationPart2(conjugationPart.getConjugationPosition(),value,conjugationPart.getUnaccentuedValue(), conjugationPart.getIndice());
            modifiedList.add(modified);
        }
        return modifiedList;
    }

    private boolean IsSecondLetterOfRoot(String value, String letterToUpdate) {
        String workValue = withoutNumbers(value);
        return workValue.substring(1).startsWith(letterToUpdate);
    }

    @Override
    public String toString() {
        return "SecondLetterRoot{" + conjugationName +  " " + ruleParameters + "}";
    }
}

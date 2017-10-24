package org.patrologia.translator.translationrules.hebrew;

import org.patrologia.translator.basicelements.TranslationRule;
import org.patrologia.translator.conjugation.ConjugationPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 01/10/2017.
 */
public class SecondLetterRoot extends TranslationRule {

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
    public List<ConjugationPart> transform(List<ConjugationPart> conjugationPartList) {
        List<ConjugationPart> modifiedList = new ArrayList<>();
        for(ConjugationPart conjugationPart : conjugationPartList) {
            String value = conjugationPart.getValue();
            if(IsSecondLetterOfRoot(value,letterToUpdate)) {
                int letterIndice = value.indexOf(letterToUpdate);
                value = value.substring(0,letterIndice) + updatedValue  + value.substring(letterIndice+1);
            }
            ConjugationPart modified = new ConjugationPart(conjugationPart.getConjugationPosition(),value,conjugationPart.getUnaccentuedValue(), conjugationPart.getPositionInDefinition());
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

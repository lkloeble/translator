package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 16/11/2017.
 */
public class DeleteLetter  extends TranslationRule {

    private String letterToDelete;
    private List<Integer> indices;

    public DeleteLetter(String conjugationName, String letterToDelete, List<Integer> indices) {
        this.conjugationName = conjugationName;
        this.letterToDelete = letterToDelete;
        this.indices = indices;
    }

    @Override
    public List<ConjugationPart> transform(List<ConjugationPart> conjugationPartList) {
        List<ConjugationPart> modifiedList = new ArrayList<>();
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(isPositionAllowedForChange(conjugationPart, indices)) {
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
        return value.replace(letterToDelete,"");
    }

    @Override
    public String toString() {
        return "DeleteLetter{" + conjugationName +  " " + letterToDelete + " " + indices + "}";
    }
}

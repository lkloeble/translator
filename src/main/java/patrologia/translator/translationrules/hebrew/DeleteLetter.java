package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart2;

import java.util.ArrayList;
import java.util.List;

public class DeleteLetter extends TranslationRule {

    private String letterToDelete;
    private List<Integer> indices;

    public DeleteLetter(String conjugationName, String letterToDelete, List<Integer> indices) {
        this.conjugationName = conjugationName;
        this.letterToDelete = letterToDelete;
        this.indices = indices;
    }

    @Override
    public List<ConjugationPart2> transform(List<ConjugationPart2> conjugationPartList) {
        List<ConjugationPart2> modifiedList = new ArrayList<>();
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(isPositionAllowedForChange(conjugationPart, indices)) {
                String alternateValue = modifyValue(conjugationPart.getValue());
                ConjugationPart2 alternateConjugationPart = new ConjugationPart2(conjugationPart.getConjugationPosition(), alternateValue, unaccentued(alternateValue), conjugationPart.getIndice(),conjugationPart.getStrictPosition());
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

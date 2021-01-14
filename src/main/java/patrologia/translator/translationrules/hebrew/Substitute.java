package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart2;

import java.util.ArrayList;
import java.util.List;

public class Substitute extends TranslationRule {

    private String patternToReplace;
    private String replacementPattern;
    private List<Integer> indices;

    public Substitute(String conjugationName, String ruleParameters, List<Integer> indices) {
        this.conjugationName = conjugationName;
        this.ruleParameters = ruleParameters;
        String[] patterns = ruleParameters.split("\\*");
        this.patternToReplace = patterns[0];
        this.replacementPattern = patterns[1];
        this.indices = indices;
    }

    @Override
    public List<ConjugationPart2> transform(List<ConjugationPart2> conjugationPartList) {
        List<ConjugationPart2> modifiedList = new ArrayList<>();
        for (ConjugationPart2 conjugationPart : conjugationPartList) {
            if(isPositionAllowedForChange(conjugationPart, indices)) {
                String alternateValue = modifyValue(conjugationPart.getValue());
                String alternateUnaccentuedValue = modifyValue(conjugationPart.getUnaccentuedValue());
                ConjugationPart2 alternateConjugationPart = new ConjugationPart2(conjugationPart.getConjugationPosition(), alternateValue, alternateUnaccentuedValue, conjugationPart.getIndice(),conjugationPart.getStrictPosition());
                modifiedList.add(alternateConjugationPart);
            } else {
                modifiedList.add(conjugationPart);
            }
        }
        return modifiedList;
    }

    private String modifyValue(String value) {
        if(value != null && !value.contains(patternToReplace)) return value;
        return value.replace(patternToReplace,replacementPattern);
    }

    @Override
    public String toString() {
        return "Substitute{" + conjugationName +  " " + ruleParameters + " " + indices + "}";
    }

}

package org.patrologia.translator.translationrules.hebrew;

import org.patrologia.translator.basicelements.TranslationRule;
import org.patrologia.translator.conjugation.ConjugationPart;

import java.util.ArrayList;
import java.util.List;

public class Substitute extends TranslationRule {

    private String patternToReplace;
    private String replacementPattern;

    public Substitute(String conjugationName, String ruleParameters) {
        this.conjugationName = conjugationName;
        this.ruleParameters = ruleParameters;
        String[] patterns = ruleParameters.split("\\*");
        this.patternToReplace = patterns[0];
        this.replacementPattern = patterns[1];
    }

    @Override
    public List<ConjugationPart> transform(List<ConjugationPart> conjugationPartList) {
        List<ConjugationPart> modifiedList = new ArrayList<>();
        for (ConjugationPart conjugationPart : conjugationPartList) {
            String alternateValue = modifyValue(conjugationPart.getValue());
            String alternateUnaccentuedValue = modifyValue(conjugationPart.getUnaccentuedValue());
            ConjugationPart alternateConjugationPart = new ConjugationPart(conjugationPart.getConjugationPosition(), alternateValue, alternateUnaccentuedValue, conjugationPart.getPositionInDefinition());
            modifiedList.add(alternateConjugationPart);
        }
        return modifiedList;
    }

    private String modifyValue(String value) {
        if(value != null && !value.contains(patternToReplace)) return value;
        return value.replace(patternToReplace,replacementPattern);
    }

    @Override
    public String toString() {
        return "Substitute{" + conjugationName +  " " + ruleParameters + "}";
    }

}

package org.patrologia.translator.basicelements;

import org.patrologia.translator.conjugation.ConjugationPart;
import org.patrologia.translator.utils.StringUtils;

import java.util.List;

/**
 * Created by lkloeble on 01/10/2017.
 */
public abstract class TranslationRule {

    protected String conjugationName;
    protected String ruleParameters;

    public abstract List<ConjugationPart> transform(List<ConjugationPart> conjugationPartList);

    protected String withoutNumbers(String value) {
        char[] letters = value.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : letters) {
            if(c >= 'a') sb.append(c);
        }
        return sb.toString();
    }

    protected boolean isPositionAllowedForChange(ConjugationPart conjugationPart, List<Integer> indices) {
        if(indices.size() == 0) return true;
        return indices.contains(conjugationPart.getPositionInDefinition());
    }

    protected boolean isAllowedForAnyPosition(List<Integer> indices) {
        return indices.size() == 0;
    }

    protected String unaccentued(String value) {
        return StringUtils.unaccentuate(value);
    }

}

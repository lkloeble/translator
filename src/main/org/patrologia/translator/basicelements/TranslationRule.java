package org.patrologia.translator.basicelements;

import org.patrologia.translator.conjugation.ConjugationPart;

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

}

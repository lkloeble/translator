package org.patrologia.translator.conjugation.greek;

import org.patrologia.translator.basicelements.TranslationInformationReplacement;
import org.patrologia.translator.conjugation.VerbDefinition;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 */
public class GreekVerbDefinition extends VerbDefinition {

    private String definition;

    public GreekVerbDefinition(String definition) {
        this.definition = definition;
        extractValuesFromDefinition();
    }

    private void extractValuesFromDefinition() {
        String[] nameForms = definition.split(",");
        if(nameForms == null || nameForms.length < 3) {
            System.out.println("verb definition incomplete " + definition);
            return;
        }
        root = nameForms[0];
        baseConjugationRoot = nameForms[0];
        conjugationPattern = removeBrackets(nameForms[2]);
        alternateRootByTime = nameForms.length == 4 ? createRootByTime(removeBrackets(nameForms[3])) : Collections.EMPTY_MAP;
        String translationDefinition = nameForms.length == 4 ? removeParenthesis(nameForms[3]) : null;
        if(translationDefinition != null && isNotARulePattern(translationDefinition)) {
            translationInformationReplacement = new TranslationInformationReplacement(translationDefinition);
        }
        infinitiveForm = baseConjugationRoot + nameForms[1];
    }

    private Map<String, String> createRootByTime(String s) {
        return Collections.EMPTY_MAP;
    }

    @Override
    public boolean hasCustomReplacements() {
        return true;
    }

}

package org.patrologia.translator.conjugation.romanian;

import org.patrologia.translator.basicelements.TranslationInformationReplacement;
import org.patrologia.translator.conjugation.NullTranslationInformationReplacement;
import org.patrologia.translator.conjugation.VerbDefinition;

import java.util.Collections;

/**
 * Created by Laurent KLOEBLE on 19/10/2015.
 */
public class RomanianVerbDefinition  extends VerbDefinition {

    private String definition;

    public RomanianVerbDefinition(String definition) {
        this.definition = definition;
        extractValuesFromDefinition();
    }

    private void extractValuesFromDefinition() {
        String[] nameForms = definition.split(",");
        if (nameForms == null || nameForms.length < 3) {
            System.out.println("verb definition incomplete " + definition);
            return;
        }
        root = nameForms[0] + nameForms[1];
        baseConjugationRoot = nameForms[0];
        conjugationPattern = removeBrackets(nameForms[2]);
        alternateRootByTime = Collections.EMPTY_MAP;
        String translationDefinition = nameForms.length == 4 ? removeParenthesis(nameForms[3]) : null;
        if(translationDefinition != null && isNotARulePattern(translationDefinition)) {
            translationInformationReplacement = new TranslationInformationReplacement(translationDefinition);
        }
        infinitiveForm = "a " + baseConjugationRoot + nameForms[1];
    }

    @Override
    public String[] getNameForms() {
        return definition.split(",");
    }

    @Override
    public boolean hasCustomReplacements() {
        return true;
    }

    @Override
    public TranslationInformationReplacement getTranslationInformationReplacement() {
        return translationInformationReplacement != null ? translationInformationReplacement : new NullTranslationInformationReplacement();
    }
}
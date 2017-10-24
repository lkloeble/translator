package org.patrologia.translator.conjugation.latin;

import org.patrologia.translator.basicelements.TranslationInformationReplacement;
import org.patrologia.translator.conjugation.VerbDefinition;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 */
public class LatinVerbDefinition extends VerbDefinition {

    private String definition;

    public LatinVerbDefinition(String definition) {
        this.definition = definition;
        extractValuesFromDefinition();
    }

    private void extractValuesFromDefinition() {
        String[] nameForms = definition.split(",");
        if(nameForms == null || nameForms.length < 6) {
            return;
        }
        root = nameForms[0] + nameForms[1];
        baseConjugationRoot = nameForms[0];
        conjugationPattern = removeBrackets(nameForms[6]);
        String translationDefinition = nameForms.length == 8 ? removeParenthesis(nameForms[7]) : null;
        if(translationDefinition != null && isNotARulePattern(translationDefinition)) {
            translationInformationReplacement = new TranslationInformationReplacement(translationDefinition);
        }
        infinitiveForm = baseConjugationRoot + nameForms[3];
    }

    @Override
    public String[] getNameForms() {
        return definition.split(",");
    }

    @Override
    public boolean hasCustomReplacements() {
        return true;
    }

}

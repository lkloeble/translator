package patrologia.translator.conjugation.hebrew;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.TranslationRules;
import patrologia.translator.conjugation.VerbDefinition;

import java.util.Collections;
import java.util.Map;

public class HebrewVerbDefinition extends VerbDefinition {

    private String definition;

    public HebrewVerbDefinition(String definition) {
        this.definition = definition;
        this.language = Language.HEBREW;
        extractValuesFromDefinition();
    }

    private void extractValuesFromDefinition() {
        String[] nameForms = definition.split(",");
        if(nameForms == null || nameForms.length < 2) {
            System.out.println("verb definition incomplete " + definition);
            return;
        }
        root = nameForms[0];
        baseConjugationRoot = nameForms[0];
        infinitiveForm = nameForms[1].length() > 1  ? nameForms[1]  : baseConjugationRoot;
        conjugationPattern = removeBrackets(nameForms[2]);
        alternateRootByTime = nameForms.length == 4 ? createAlternateRoot(removeBrackets(nameForms[3])) : null;
        String translationDefinition = nameForms.length == 4 ? removeParenthesis(nameForms[3]) : null;
        if(translationDefinition != null && isNotARulePattern(translationDefinition)) {
            //translationInformationReplacement = new TranslationInformationReplacement(translationDefinition);
            translationRules = new TranslationRules(translationDefinition);
        }
    }

    @Override
    public String getInfinitiveForm() {
        return "l" + super.getInfinitiveForm();
    }

    private Map<String, String> createAlternateRoot(String s) {
        return Collections.EMPTY_MAP;
    }

    @Override
    public boolean hasCustomReplacements() {
        return true;
    }

    @Override
    public boolean hasCustomRules() {
        return true;
    }

}

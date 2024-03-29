package patrologia.translator.conjugation.english;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.TranslationInformationReplacement;
import patrologia.translator.basicelements.verb.TranslationInformationReplacement2;
import patrologia.translator.conjugation.VerbDefinition;

import java.util.Collections;
import java.util.Map;

public class EnglishVerbDefinition extends VerbDefinition {

    private String definition;

    public EnglishVerbDefinition(String definition) {
        this.definition = definition;
        this.language = Language.ENGLISH;
        extractValuesFromDefinition();
    }

    private void extractValuesFromDefinition() {
        String[] nameForms = definition.split(",");
        if(nameForms == null || nameForms.length < 2) {
            System.out.println("verb definition incomplete " + definition);
            return;
        }
        root = nameForms[0];
        String supplementToRoot = nameForms[1];
        baseConjugationRoot = root;
        conjugationPattern = removeBrackets(nameForms[2]);
        alternateRootByTime = nameForms.length == 4 ? createAlternateRoot(removeBrackets(nameForms[3])) : null;
        String translationDefinition = nameForms.length == 4 ? removeParenthesis(nameForms[3]) : null;
        if(translationDefinition != null && isNotARulePattern(translationDefinition)) {
            translationInformationReplacement = new TranslationInformationReplacement2(translationDefinition);
        }
        infinitiveForm = "to " + baseConjugationRoot + supplementToRoot;
    }

    @Override
    public boolean hasCustomReplacements() {
        return true;
    }

    private Map<String, String> createAlternateRoot(String s) {
        return Collections.EMPTY_MAP;
    }
}

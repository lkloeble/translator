package patrologia.translator.conjugation.german;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.TranslationInformationReplacement;
import patrologia.translator.basicelements.verb.TranslationInformationReplacement2;
import patrologia.translator.conjugation.VerbDefinition;

import java.util.Collections;
import java.util.Map;

public class GermanVerbDefinition extends VerbDefinition {

    private String definition;

    public GermanVerbDefinition(String definition) {
        this.definition = definition;
        this.language = Language.GERMAN;
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
        alternateRootByTime = nameForms.length == 4 ? createAlternateRoot(removeBrackets(nameForms[3])) : null;
        String translationDefinition = nameForms.length == 4 ? removeParenthesis(nameForms[3]) : null;
        if(translationDefinition != null && isNotARulePattern(translationDefinition)) {
            translationInformationReplacement = new TranslationInformationReplacement2(translationDefinition);
        }
        infinitiveForm = baseConjugationRoot + nameForms[1];
    }

    private Map<String, String> createAlternateRoot(String s) {
        return Collections.EMPTY_MAP;
    }

    @Override
    public boolean hasCustomReplacements() {
        return true;
    }

}

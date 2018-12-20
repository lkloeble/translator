package patrologia.translator.conjugation;

import patrologia.translator.basicelements.TranslationInformationReplacement;
import patrologia.translator.basicelements.TranslationRules;

import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 */
public abstract class VerbDefinition {

    protected String root;
    protected String baseConjugationRoot;
    protected String conjugationPattern;
    protected Map<String,String> alternateRootByTime;
    protected String infinitiveForm;

    protected TranslationInformationReplacement translationInformationReplacement;
    protected TranslationRules translationRules;

    public String getRoot() {
        return root;
    }

    public String getBaseConjugationRootByTime(String time) {
        if(alternateRootByTime == null) return baseConjugationRoot;
        if(alternateRootByTime.containsKey(time)) return alternateRootByTime.get(time);
        return baseConjugationRoot;
    }

    public String getConjugationPattern() {
        return conjugationPattern;
    }

    public String getInfinitiveForm() {
        return infinitiveForm;
    }

    protected String removeBrackets(String nameAndConjugation) {
        return nameAndConjugation.replace("[", "").replace("]", "");
    }

    public String[] getNameForms() {
        return new String[0];
    }

    public boolean hasCustomReplacements() {
        return false;
    }

    public boolean hasCustomRules() {
        return false;
    }

    public TranslationInformationReplacement getTranslationInformationReplacement() {
        return translationInformationReplacement != null ? translationInformationReplacement : new NullTranslationInformationReplacement();
    }

    protected String removeParenthesis(String definitionDescription) {
        return definitionDescription.replace("(", "").replace(")", "");
    }

    protected boolean isNotARulePattern(String translationDefinition) {
        return translationDefinition.indexOf('*') > 0;
    }


    public TranslationRules getTranslationRules() {
        return translationRules;
    }
}

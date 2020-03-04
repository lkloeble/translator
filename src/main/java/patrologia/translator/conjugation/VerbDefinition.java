package patrologia.translator.conjugation;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.TranslationInformationReplacement;
import patrologia.translator.basicelements.TranslationRules;
import patrologia.translator.basicelements.verb.NullTranslationInformationReplacement2;
import patrologia.translator.basicelements.verb.TranslationInformationReplacement2;

import java.util.Map;

public abstract class VerbDefinition {

    protected String root;
    protected String baseConjugationRoot;
    protected String conjugationPattern;
    protected Map<String,String> alternateRootByTime;
    protected String infinitiveForm;

    protected TranslationInformationReplacement2 translationInformationReplacement;
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

    public TranslationInformationReplacement2 getTranslationInformationReplacement2() {
        return translationInformationReplacement != null ? translationInformationReplacement : new NullTranslationInformationReplacement2();
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

    public String getBaseConjugationRoot() {
        return baseConjugationRoot;
    }

    public boolean hasTranslationRules() {
        //TODO
        return translationRules != null;
    }

    public Language getLanguage() {
        return null;
    }
}

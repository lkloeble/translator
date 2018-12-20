package patrologia.translator.basicelements;

import patrologia.translator.conjugation.RootedConjugation;

import java.util.List;

/**
 * Created by Laurent KLOEBLE on 19/10/2015.
 */
public class TranslationReplacements {

    private List<TranslationInformationReplacement> replacements;

    public TranslationReplacements(List<TranslationInformationReplacement> replacements) {
        this.replacements = replacements;
    }

    public boolean hasThisNameAsReplacement(String name) {
        for(TranslationInformationReplacement translationInformationReplacement : replacements)  {
            if(translationInformationReplacement.hasThisName(name)) return true;
        }
        return false;
    }

    public String correctFormName(String name, RootedConjugation  rootedConjugation) {
        for(TranslationInformationReplacement replacement : replacements) {
            rootedConjugation = replacement.performReplacement(name, rootedConjugation);
        }
        return rootedConjugation.allFormsInOneString();
    }
}

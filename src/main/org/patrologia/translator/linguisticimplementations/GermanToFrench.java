package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.modifier.FinalModifier;
import org.patrologia.translator.declension.DeclensionFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanToFrench extends LanguageToFrench {

    public GermanToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions, VerbRepository verbRepository, NounRepository nounRepository, DeclensionFactory declensionFactory, LanguageDecorator languageDecorator, FinalModifier finalModifier) {
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.nounRepository = nounRepository;
        this.finalModifier  = finalModifier;
        this.languageDecorator = languageDecorator;
        populateAllForms();
    }

    @Override
    protected SpecificLanguageSelector getLanguageSelector() {
        return new DefaultLanguageSelector();
    }

    @Override
    protected String translateWordUsingCustomMap(Word word, Map<String, String> customTranslationMap) {
        String defaultTranslation = super.translateWordUsingCustomMap(word, customTranslationMap);
        if(word.isPreposition()) {
            Preposition preposition = (Preposition)word;
            if(preposition.isDoubleNumberTranslation()) {
                return customTranslationMap.get(word.getRoot() + "SECONDNUMBER");
            }
        }
        return defaultTranslation;
    }
}

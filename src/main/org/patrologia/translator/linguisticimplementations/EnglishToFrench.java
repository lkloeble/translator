package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.modifier.FinalModifier;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.declension.DeclensionFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class EnglishToFrench extends LanguageToFrench {


    public EnglishToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions, VerbRepository verbRepository, NounRepository nounRepository, DeclensionFactory declensionFactory, FinalModifier finalModifier) {
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.conjugationGenderAnalyser = new EnglishConjugationAnalyzer();
        this.nounRepository = nounRepository;
        this.finalModifier = finalModifier;
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
            } else if(preposition.isDoubleGenreTranslation()) {
                return customTranslationMap.get(word.getRoot() + "SECONDGENDER");
            }
        }
        return defaultTranslation;
    }
}

package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.modifier.FinalModifier;
import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.casenumbergenre.*;
import org.patrologia.translator.casenumbergenre.Number;
import org.patrologia.translator.declension.DeclensionFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewToFrench extends LanguageToFrench {

    public HebrewToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions, VerbRepository verbRepository, NounRepository nounRepository, DeclensionFactory declensionFactory, FinalModifier finalModifier) {
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.nounRepository = nounRepository;
        this.finalModifier = finalModifier;
        populateAllForms();
    }

    public String numberCaseDecorate(String translationRoot, Word word) {
        String result = super.numberCaseDecorate(translationRoot, word);
        Noun article = (Noun) word;
        if (result.equals("le")) {
            Number numberElected = article.getNumberElected();
            Gender gender = article.getGender();
            if (numberElected != null && numberElected.equals(Number.PLURAL)) {
                return "les";
            } else if (gender != null && gender.equals(new Gender(Gender.FEMININE))) {
                return "la";
            }
            return "le";
        }
        return result;
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


    @Override
    protected SpecificLanguageSelector getLanguageSelector() {
        return new HebrewLanguageSelector();
    }
}

package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.declension.DeclensionFactory;

import java.util.List;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianToFrench extends LanguageToFrench {

    public RomanianToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions, VerbRepository verbRepository, NounRepository nounRepository, DeclensionFactory declensionFactory, FinalModifier finalModifier) {
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.nounRepository = nounRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.finalModifier = finalModifier;
        populateAllForms();
    }

    @Override
    protected SpecificLanguageSelector getLanguageSelector() {
        return new RomanianLanguageSelector();
    }

    protected String numberCaseDecorate(String translationRoot, Word word) {
        String basicTranslation = super.numberCaseDecorate(translationRoot, word);
        if(!word.isNoun() || !((Noun)word).isNotAnAdjective()) {
            return basicTranslation;
        }
        Noun noun = (Noun)word;
        if(noun.getGender().equals(Gender.FEMININE) && !noun.isWithoutArticle() && noun.getInitialValue().endsWith("a")) {
            return "la " + basicTranslation;
        }
        if(noun.getGender().equals(Gender.MASCULINE) && !noun.isWithoutArticle() && noun.getInitialValue().endsWith("ul")) {
            return "le " + basicTranslation;
        }
        return basicTranslation;
    }
}

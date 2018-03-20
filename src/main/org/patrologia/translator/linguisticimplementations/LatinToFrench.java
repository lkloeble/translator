package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.modifier.FinalModifier;
import org.patrologia.translator.declension.DeclensionFactory;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 01/09/2015.
 */
public class LatinToFrench extends LanguageToFrench {

    public LatinToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions, VerbRepository verbRepository, NounRepository nounRepository, DeclensionFactory declensionFactory, LanguageDecorator languageDecorator, FinalModifier finalModifier) {
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.conjugationGenderAnalyser = new LatinConjugationAnalyzer();
        this.nounRepository = nounRepository;
        this.languageDecorator = languageDecorator;
        this.finalModifier = finalModifier;
        populateAllForms();
    }

    @Override
    protected SpecificLanguageSelector getLanguageSelector() {
        return new DefaultLanguageSelector();
    }
}

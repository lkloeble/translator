package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.modifier.FinalModifier;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.declension.DeclensionFactory;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 08/10/2015.
 */
public class GreekToFrench extends LanguageToFrench {

    public GreekToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions, VerbRepository verbRepository, NounRepository nounRepository, DeclensionFactory declensionFactory, LanguageDecorator languageDecorator, FinalModifier finalModifier) {
        super(verbRepository);
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.conjugationGenderAnalyser = new GreekConjugationAnalyzer();
        this.nounRepository = nounRepository;
        this.languageDecorator = languageDecorator;
        this.finalModifier = finalModifier;
        populateAllForms();
    }

    @Override
    protected SpecificLanguageSelector getLanguageSelector() {
        return new DefaultLanguageSelector();
    }

    protected String specificPonctuationReplace(String initialValue) {
        if(";".equals(initialValue)) return "?";
        return initialValue;
    }
}
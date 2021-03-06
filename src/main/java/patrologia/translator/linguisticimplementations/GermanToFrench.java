package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.modifier.FinalModifier;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;
import java.util.Map;

public class GermanToFrench  extends LanguageToFrench {

    public GermanToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions,
                          VerbRepository2 verbRepository, NounRepository nounRepository,
                          DeclensionFactory declensionFactory, LanguageDecorator languageDecorator, FinalModifier finalModifier) {
        super(verbRepository);
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.nounRepository = nounRepository;
        this.finalModifier = finalModifier;
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
        if (word.isPreposition()) {
            Preposition preposition = (Preposition) word;
            if (preposition.isDoubleNumberTranslation()) {
                return customTranslationMap.get(word.getRoot() + "SECONDNUMBER");
            }
        }
        return defaultTranslation;
    }
}

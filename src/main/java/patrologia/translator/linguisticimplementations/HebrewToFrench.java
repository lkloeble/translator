package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.modifier.FinalModifier;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.Number;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;
import java.util.Map;

public class HebrewToFrench extends LanguageToFrench {

    public HebrewToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions,
                          VerbRepository2 verbRepository, NounRepository nounRepository,
                          DeclensionFactory declensionFactory, FinalModifier finalModifier) {
        super(verbRepository);
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.nounRepository = nounRepository;
        this.finalModifier = finalModifier;
        this.conjugationGenderAnalyser = new HebrewConjugationAnalyzer();
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

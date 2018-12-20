package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.modifier.FinalModifier;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianToFrench extends LanguageToFrench {

    public RomanianToFrench(List<String> dictionarydefinitions, List<String> frenchVerbsDefinitions, VerbRepository verbRepository, NounRepository nounRepository, DeclensionFactory declensionFactory, FinalModifier finalModifier) {
        super(verbRepository);
        this.dictionaryDefinitions = dictionarydefinitions;
        this.frenchVerbsDefinitions = frenchVerbsDefinitions;
        this.verbRepository = verbRepository;
        this.nounRepository = nounRepository;
        this.originLanguageDeclensionFactory = declensionFactory;
        this.conjugationGenderAnalyser = new DefaultConjugationAnalyzer();
        this.finalModifier = finalModifier;
        populateAllForms();
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

    @Override
    protected SpecificLanguageSelector getLanguageSelector() {
        return new RomanianLanguageSelector();
    }

}

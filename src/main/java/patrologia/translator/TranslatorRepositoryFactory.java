package patrologia.translator;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.modifier.DefaultFinalModifier;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.declension.DeclensionFactory;
import patrologia.translator.linguisticimplementations.RomanianToFrench;

import java.util.List;

public class TranslatorRepositoryFactory {

    private VerbRepository2 verbRepository;
    private NounRepository nounRepository;

    public TranslatorRepositoryFactory(VerbRepository2 verbRepository, NounRepository nounRepository) {
        this.verbRepository = verbRepository;
        this.nounRepository = nounRepository;
    }

    public TranslatorRepository getTranslator(Language sourceLanguage, Language destinationLanguage, List<String> dictionaryReferenceData, List<String> destinationVerbFile, String declensionPathFile, String declensionsAndFiles, DeclensionFactory declensionFactory) {
        if(sourceLanguage == null || destinationLanguage == null || dictionaryReferenceData == null) {
            return new NullTranslatorRepository();
        }
        if(sourceLanguage.equals(Language.LATIN) && destinationLanguage.equals(Language.FRENCH)) {
            //return new LatinToFrench(dictionaryReferenceData, destinationVerbFile, verbRepository, nounRepository, declensionFactory, new LatinLanguageDecorator(), new DefaultFinalModifier());
        } else if(sourceLanguage.equals(Language.GREEK) && destinationLanguage.equals(Language.FRENCH)) {
            //return new GreekToFrench(dictionaryReferenceData, destinationVerbFile, verbRepository, nounRepository, declensionFactory, new GreekLanguageDecorator(), new GreekFinalModifier());
        } else if(sourceLanguage.equals(Language.HEBREW) && destinationLanguage.equals(Language.FRENCH)) {
            //return new HebrewToFrench(dictionaryReferenceData, destinationVerbFile, verbRepository, nounRepository, declensionFactory, new DefaultFinalModifier());
        } else if(sourceLanguage.equals(Language.ROMANIAN) && destinationLanguage.equals(Language.FRENCH)) {
            return new RomanianToFrench(dictionaryReferenceData, destinationVerbFile, verbRepository, nounRepository, declensionFactory, new DefaultFinalModifier());
        } else if(sourceLanguage.equals(Language.GERMAN) && destinationLanguage.equals(Language.FRENCH)) {
            //return new GermanToFrench(dictionaryReferenceData, destinationVerbFile, verbRepository, nounRepository, declensionFactory, new GermanLanguageDecorator(), new DefaultFinalModifier());
        } else if(sourceLanguage.equals(Language.ENGLISH) && destinationLanguage.equals(Language.FRENCH)) {
            //return new EnglishToFrench(dictionaryReferenceData, destinationVerbFile, verbRepository, nounRepository, declensionFactory, new DefaultFinalModifier());
        }
        return new NullTranslatorRepository();
    }

}

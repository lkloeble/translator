package patrologia.translator.linguisticimplementations;

import patrologia.translator.NullTranslatorRepository;
import patrologia.translator.TranslatorRepository;
import patrologia.translator.TranslatorRepositoryFactory;
import patrologia.translator.basicelements.Analysis;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.Translation;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

public class FrenchTranslator  implements Translator {

    private Language destinationLanguage = Language.FRENCH;

    private List<String> dictionaryReferenceDefinitions;
    private List<String> verbReferenceData;
    private VerbRepository2 verbRepository;
    private NounRepository nounRepository;
    private String declensionData;
    private String declensionsAndFiles;
    private DeclensionFactory declensionFactory;

    public FrenchTranslator(List<String> dictionaryReferenceDefinitions, List<String> verbReferenceDefinitions, VerbRepository2 verbRepository, NounRepository nounRepository, String declensionPath, String declensionsAndFiles, DeclensionFactory declensionFactory) {
        this.verbRepository = verbRepository;
        this.dictionaryReferenceDefinitions = dictionaryReferenceDefinitions;
        this.verbReferenceData = verbReferenceDefinitions;
        this.declensionData = declensionPath;
        this.declensionsAndFiles = declensionsAndFiles;
        this.nounRepository = nounRepository;
        this.declensionFactory = declensionFactory;
    }

    public String translateToRead(Analysis analysis) {
        Translation result = getTranslatorRepository(analysis).computeReaderTranslation(analysis);
        return result.getPossibleTranslation();
    }

    private TranslatorRepository getTranslatorRepository(Analysis analysis) {
        if(analysis == null) {
            return new NullTranslatorRepository();
        }
        TranslatorRepositoryFactory translatorRepositoryFactory = new TranslatorRepositoryFactory(verbRepository, nounRepository);
        TranslatorRepository originToFrench = translatorRepositoryFactory.getTranslator(analysis.getLanguage(), destinationLanguage, dictionaryReferenceDefinitions, verbReferenceData, declensionData, declensionsAndFiles,declensionFactory);
        return originToFrench != null ? originToFrench : new NullTranslatorRepository();
    }
}

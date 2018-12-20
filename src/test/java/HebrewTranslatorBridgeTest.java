import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import patrologia.translator.conjugation.hebrew.HebrewConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.hebrew.HebrewDeclension;
import patrologia.translator.declension.hebrew.HebrewDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.HebrewAnalyzer;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.hebrew.HebrewRuleFactory;
import patrologia.translator.utils.Analizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    @Before
    public void init() {
        String prepositionFileDescription = "C:\\translator\\src\\main\\resources\\hebrew\\prepositions.txt";
        String nounFileDescription = "C:\\translator\\src\\main\\resources\\hebrew\\nouns.txt";
        String verbFileDescription = "C:\\translator\\src\\main\\resources\\hebrew\\verbs.txt";
        String hebrewFrenchDataFile = "C:\\translator\\src\\main\\resources\\hebrew\\cohn_hebrew_to_french.txt";
        String frenchVerbsDataFile = "C:\\translator\\src\\main\\resources\\french_verbs.txt";
        String declensionPath = "C:\\translator\\src\\main\\resources\\hebrew\\declensions";
        String declensionsAndFiles = "C:\\translator\\src\\main\\resources\\hebrew\\declensionsAndFiles.txt";
        String conjugationPath = "C:\\translator\\src\\main\\resources\\hebrew\\conjugations";
        String conjugationsAndFiles = "C:\\translator\\src\\main\\resources\\hebrew\\conjugationsAndFiles.txt";
        String hebrewPathFile = "C:\\translator\\src\\test\\resources\\hebrew_content.txt";
        String hebrewResultFile = "C:\\translator\\src\\test\\resources\\hebrew_expected_results.txt";
        HebrewDeclensionFactory hebrewDeclensionFactory = new HebrewDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        NounRepository nounRepository = new NounRepository(Language.HEBREW, hebrewDeclensionFactory, new Accentuer(),getFileContentForRepository(nounFileDescription));
        VerbRepository verbRepository = new VerbRepository(new HebrewConjugationFactory(getHebrewConjugations(conjugationsAndFiles), getHebrewConjugationDefinitions(conjugationsAndFiles, conjugationPath), nounRepository), Language.HEBREW, new DummyAccentuer(),getVerbs(verbFileDescription));
        HebrewRuleFactory ruleFactory = new HebrewRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.HEBREW, new HebrewCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        Analizer hebrewAnalyzer = new HebrewAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getFileContentForRepository(hebrewFrenchDataFile), getFileContentForRepository(frenchVerbsDataFile), verbRepository, nounRepository, declensionPath, declensionsAndFiles, hebrewDeclensionFactory);
        translatorBridge = new TranslatorBridge(hebrewAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(hebrewPathFile);
        mapValuesForResult = loadMapFromFiles(hebrewResultFile);
    }

    private List<Declension> getDeclensionList(String file, String directory) {
        List<String> declensionNameList = getFileContentForRepository(file);
        List<Declension> declensionList = new ArrayList<>();
        for (String declensionName : declensionNameList) {
            String parts[] = declensionName.split("%");
            String fileName = parts[1];
            declensionList.add(new HebrewDeclension(fileName, getDeclensionElements(fileName, directory)));
        }
        return declensionList;
    }

    private List<String> getVerbs(String verbFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "sum@IRREGULAR%[IPR]=[sum,es,est,sumus,estis,sunt]%[AII]=[eram,eras,erat,eramus,eratis,erant]%[AIF]=[ero,eris,erit,erimus,eritis,erunt]%[INFINITIVE]=[esse]%[ASP]=[sim,sis,sit,simus,sitis,sint]%[ASI]=[essem,esses,esset,essemus,essetis,essent]%[AIP]=[fui,fuisti,fuit,fuimus,fuistis,fuerunt]%[AIPP]=[fueram,fueras,fuerat,fueramus,fueratis,fuerant]%[IAP]=[fuisse]%[AIFP]=[fuero,fueris,fuerit,fuerimus,fueritis,fuerint]",
                "sum,o,is,ere,,,[o-is]"
        });
        */

        return getFileContentForRepository(verbFileDescription);
    }

    private Map<String, List<String>> getHebrewConjugationDefinitions(String file, String directory) {
        /*
        Map<String, List<String>> latinConjugationDefinitionsMap = new HashMap<>();
        latinConjugationDefinitionsMap.put("o-is", getOIslDefinition());
        return latinConjugationDefinitionsMap;
        */


        List<String> conjugationNameList = getFileContentForRepository(file);
        Map<String, List<String>> hebrewConjugationDefinitionsMap = new HashMap<>();
        for(String conjugationName : conjugationNameList) {
            String parts[] = conjugationName.split("%");
            String fileName = parts[1];
            String nameOnly = parts[0];
            hebrewConjugationDefinitionsMap.put(nameOnly, getConjugationElements(directory,fileName));
        }
        return hebrewConjugationDefinitionsMap;
    }

    private List<String> getHebrewConjugations(String conjugationsAndFiles) {
        /*
        return Arrays.asList(new String[]{
                "o-is%permittere.txt"
        });
        */

        return getFileContentForRepository(conjugationsAndFiles);
    }


}

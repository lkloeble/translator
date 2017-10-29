import org.junit.Before;
import org.junit.Test;
import org.patrologia.translator.TranslatorBridge;
import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.basicelements.NounRepository;
import org.patrologia.translator.basicelements.PrepositionRepository;
import org.patrologia.translator.basicelements.VerbRepository;
import org.patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import org.patrologia.translator.conjugation.hebrew.HebrewConjugationFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.hebrew.HebrewDeclension;
import org.patrologia.translator.declension.hebrew.HebrewDeclensionFactory;
import org.patrologia.translator.linguisticimplementations.FrenchTranslator;
import org.patrologia.translator.linguisticimplementations.HebrewAnalyzer;
import org.patrologia.translator.linguisticimplementations.Translator;
import org.patrologia.translator.rule.hebrew.HebrewRuleFactory;
import org.patrologia.translator.utils.Analizer;

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
        String prepositionFileDescription = "E:\\translator\\src\\main\\resources\\hebrew\\prepositions.txt";
        String nounFileDescription = "E:\\translator\\src\\main\\resources\\hebrew\\nouns.txt";
        String verbFileDescription = "E:\\translator\\src\\main\\resources\\hebrew\\verbs.txt";
        String hebrewFrenchDataFile = "E:\\translator\\src\\main\\resources\\hebrew\\cohn_hebrew_to_french.txt";
        String frenchVerbsDataFile = "E:\\translator\\src\\main\\resources\\french_verbs.txt";
        String declensionPath = "E:\\translator\\src\\main\\resources\\hebrew\\declensions";
        String declensionsAndFiles = "E:\\translator\\src\\main\\resources\\hebrew\\declensionsAndFiles.txt";
        String conjugationPath = "E:\\translator\\src\\main\\resources\\hebrew\\conjugations";
        String conjugationsAndFiles = "E:\\translator\\src\\main\\resources\\hebrew\\conjugationsAndFiles.txt";
        String hebrewPathFile = "E:\\translator\\src\\test\\resources\\hebrew_content.txt";
        String hebrewResultFile = "E:\\translator\\src\\test\\resources\\hebrew_expected_results.txt";
        HebrewDeclensionFactory hebrewDeclensionFactory = new HebrewDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        VerbRepository verbRepository = new VerbRepository(new HebrewConjugationFactory(getHebrewConjugations(conjugationsAndFiles), getHebrewConjugationDefinitions(conjugationsAndFiles, conjugationPath)), Language.HEBREW, getVerbs(verbFileDescription));
        HebrewRuleFactory ruleFactory = new HebrewRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.HEBREW, new HebrewCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        NounRepository nounRepository = new NounRepository(Language.HEBREW, hebrewDeclensionFactory, getFileContentForRepository(nounFileDescription));
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


    @Test
    public void test_weingreen_chapter_23() {
        assertEquals(2, 2);
    }

    @Test
    public void test_weingreen_chapter_24() {
        checkInMaps("wein24A", translatorBridge);
        checkInMaps("wein24B", translatorBridge);
        checkInMaps("wein24C", translatorBridge);
        checkInMaps("wein24D", translatorBridge);
        checkInMaps("wein24E", translatorBridge);
        checkInMaps("wein24F", translatorBridge);
        checkInMaps("wein24G", translatorBridge);
        checkInMaps("wein24H", translatorBridge);
        checkInMaps("wein24I", translatorBridge);
        checkInMaps("wein24J", translatorBridge);
        checkInMaps("wein24K", translatorBridge);
        checkInMaps("wein24L", translatorBridge);
        checkInMaps("wein24M", translatorBridge);
        checkInMaps("wein24N", translatorBridge);
        checkInMaps("wein24O", translatorBridge);
        checkInMaps("wein24P", translatorBridge);
        checkInMaps("wein24Q", translatorBridge);
        checkInMaps("wein24R", translatorBridge);
        checkInMaps("wein24S", translatorBridge);
        checkInMaps("wein24T", translatorBridge);
        checkInMaps("wein24U", translatorBridge);
        checkInMaps("wein24V", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_25() {
        assertEquals(2, 2);
    }


    @Test
    public void test_weingreen_chapter_26() {
        assertEquals(2, 2);
    }

    @Test
    public void test_weingreen_chapter_27() {
        checkInMaps("wein27A", translatorBridge);
        checkInMaps("wein27B", translatorBridge);
        checkInMaps("wein27C1", translatorBridge);
        checkInMaps("wein27C2", translatorBridge);
        checkInMaps("wein27D", translatorBridge);
        checkInMaps("wein27E", translatorBridge);
        checkInMaps("wein27F", translatorBridge);
        checkInMaps("wein27G", translatorBridge);
        checkInMaps("wein27H", translatorBridge);
        checkInMaps("wein27I", translatorBridge);
        checkInMaps("wein27J", translatorBridge);
        checkInMaps("wein27K", translatorBridge);
        checkInMaps("wein27L", translatorBridge);
        checkInMaps("wein27M", translatorBridge);
        checkInMaps("wein27N", translatorBridge);
        checkInMaps("wein27O", translatorBridge);
        checkInMaps("wein27P", translatorBridge);
        checkInMaps("wein27Q", translatorBridge);
        checkInMaps("wein27R", translatorBridge);
        checkInMaps("wein27S", translatorBridge);
        checkInMaps("wein27T", translatorBridge);
        checkInMaps("wein27U", translatorBridge);
        checkInMaps("wein27V", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_28() {
        assertEquals(2, 2);
    }

    @Test
    public void test_weingreen_chapter_29() {
        assertEquals(2, 2);
    }

    @Test
    public void test_weingreen_chapter_30() {
        checkInMaps("wein30A", translatorBridge);
        checkInMaps("wein30B", translatorBridge);
        checkInMaps("wein30C", translatorBridge);
        checkInMaps("wein30D", translatorBridge);
        checkInMaps("wein30E", translatorBridge);
        checkInMaps("wein30F", translatorBridge);
        checkInMaps("wein30G", translatorBridge);
        checkInMaps("wein30H", translatorBridge);
        checkInMaps("wein30I", translatorBridge);
        checkInMaps("wein30J", translatorBridge);
        checkInMaps("wein30K", translatorBridge);
        checkInMaps("wein30L", translatorBridge);
        checkInMaps("wein30M", translatorBridge);
        checkInMaps("wein30N", translatorBridge);
        checkInMaps("wein30O", translatorBridge);
        checkInMaps("wein30P", translatorBridge);
        checkInMaps("wein30Q", translatorBridge);
        checkInMaps("wein30R", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_33() {
        checkInMaps("wein33A", translatorBridge);
        checkInMaps("wein33B", translatorBridge);
        checkInMaps("wein33C", translatorBridge);
        checkInMaps("wein33D", translatorBridge);
        checkInMaps("wein33E", translatorBridge);
        checkInMaps("wein33F", translatorBridge);
        checkInMaps("wein33G", translatorBridge);
        checkInMaps("wein33H", translatorBridge);
        checkInMaps("wein33I", translatorBridge);
        checkInMaps("wein33J", translatorBridge);
        checkInMaps("wein33K", translatorBridge);
        checkInMaps("wein33L", translatorBridge);
        checkInMaps("wein33M", translatorBridge);
        checkInMaps("wein33N", translatorBridge);
        checkInMaps("wein33O", translatorBridge);
        checkInMaps("wein33P", translatorBridge);
        checkInMaps("wein33Q", translatorBridge);
        checkInMaps("wein33R", translatorBridge);
        checkInMaps("wein33S", translatorBridge);
    }


    @Test
    public void test_weingreen_chapter_35() {
        checkInMaps("wein35A", translatorBridge);
        checkInMaps("wein35B", translatorBridge);
        checkInMaps("wein35C", translatorBridge);
        checkInMaps("wein35D", translatorBridge);
        checkInMaps("wein35E", translatorBridge);
        checkInMaps("wein35F", translatorBridge);
        checkInMaps("wein35G", translatorBridge);
        checkInMaps("wein35H", translatorBridge);
        checkInMaps("wein35I", translatorBridge);
        checkInMaps("wein35J", translatorBridge);
        checkInMaps("wein35K", translatorBridge);
        checkInMaps("wein35L", translatorBridge);
        checkInMaps("wein35M", translatorBridge);
        checkInMaps("wein35N", translatorBridge);
        checkInMaps("wein35O", translatorBridge);
        checkInMaps("wein35P", translatorBridge);
        checkInMaps("wein35Q", translatorBridge);
        checkInMaps("wein35R", translatorBridge);
        checkInMaps("wein35S", translatorBridge);
        checkInMaps("wein35T", translatorBridge);
        checkInMaps("wein35U", translatorBridge);
        checkInMaps("wein35V", translatorBridge);
        checkInMaps("wein35W", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_38() {
        checkInMaps("wein38A", translatorBridge);
        checkInMaps("wein38B", translatorBridge);
        checkInMaps("wein38C", translatorBridge);
        checkInMaps("wein38D", translatorBridge);
        checkInMaps("wein38E", translatorBridge);
        checkInMaps("wein38F", translatorBridge);
        checkInMaps("wein38G", translatorBridge);
        checkInMaps("wein38H", translatorBridge);
        checkInMaps("wein38I", translatorBridge);
        checkInMaps("wein38J", translatorBridge);
        checkInMaps("wein38K", translatorBridge);
        checkInMaps("wein38L", translatorBridge);
        checkInMaps("wein38M", translatorBridge);
        checkInMaps("wein38N", translatorBridge);
        checkInMaps("wein38O", translatorBridge);
        checkInMaps("wein38P", translatorBridge);
        checkInMaps("wein38Q", translatorBridge);
        checkInMaps("wein38R", translatorBridge);
        checkInMaps("wein38S", translatorBridge);
        checkInMaps("wein38T", translatorBridge);
        checkInMaps("wein38U", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_41() {
        checkInMaps("wein41A", translatorBridge);
        checkInMaps("wein41B", translatorBridge);
        checkInMaps("wein41C", translatorBridge);
        checkInMaps("wein41D", translatorBridge);
        checkInMaps("wein41E", translatorBridge);
        checkInMaps("wein41F", translatorBridge);
        checkInMaps("wein41G", translatorBridge);
        checkInMaps("wein41H", translatorBridge);
        checkInMaps("wein41I", translatorBridge);
        checkInMaps("wein41J", translatorBridge);
        checkInMaps("wein41K", translatorBridge);
        checkInMaps("wein41L", translatorBridge);
        checkInMaps("wein41M", translatorBridge);
        checkInMaps("wein41N", translatorBridge);
        checkInMaps("wein41O", translatorBridge);
        checkInMaps("wein41P", translatorBridge);
        checkInMaps("wein41Q", translatorBridge);
        checkInMaps("wein41R", translatorBridge);
        checkInMaps("wein41S", translatorBridge);
        checkInMaps("wein41T", translatorBridge);
        checkInMaps("wein41U", translatorBridge);
        checkInMaps("wein41V", translatorBridge);
    }



}

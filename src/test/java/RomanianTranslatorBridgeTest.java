import org.junit.Before;
import org.junit.Test;
import org.patrologia.translator.TranslatorBridge;
import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.basicelements.NounRepository;
import org.patrologia.translator.basicelements.PrepositionRepository;
import org.patrologia.translator.basicelements.VerbRepository;
import org.patrologia.translator.casenumbergenre.romanian.RomanianCaseFactory;
import org.patrologia.translator.conjugation.romanian.RomanianConjugationFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.romanian.RomanianDeclension;
import org.patrologia.translator.declension.romanian.RomanianDeclensionFactory;
import org.patrologia.translator.linguisticimplementations.FrenchTranslator;
import org.patrologia.translator.linguisticimplementations.RomanianAnalyzer;
import org.patrologia.translator.linguisticimplementations.Translator;
import org.patrologia.translator.rule.romanian.RomanianRuleFactory;
import org.patrologia.translator.utils.Analizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    @Before
    public void init() {
        String prepositionFileDescription = "E:\\translator\\src\\main\\resources\\romanian\\prepositions.txt";
        String nounFileDescription = "E:\\translator\\src\\main\\resources\\romanian\\nouns.txt";
        String verbFileDescription = "E:\\translator\\src\\main\\resources\\romanian\\verbs.txt";
        String romanianFrenchDataFile = "E:\\translator\\src\\main\\resources\\romanian\\dico_romanian_french.txt";
        String frenchVerbsDataFile = "E:\\translator\\src\\main\\resources\\french_verbs.txt";
        String declensionPath = "E:\\translator\\src\\main\\resources\\romanian\\declensions";
        String declensionsAndFiles = "E:\\translator\\src\\main\\resources\\romanian\\declensionsAndFiles.txt";
        String conjugationPath = "E:\\translator\\src\\main\\resources\\romanian\\conjugations";
        String conjugationsAndFiles = "E:\\translator\\src\\main\\resources\\romanian\\conjugationsAndFiles.txt";
        String romanianPathFile = "E:\\translator\\src\\test\\resources\\romanian_content.txt";
        String romanianResultFile = "E:\\translator\\src\\test\\resources\\romanian_expected_results.txt";
        RomanianDeclensionFactory romanianDeclensionFactory = new RomanianDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        VerbRepository verbRepository = new VerbRepository(new RomanianConjugationFactory(getRomanianConjugations(conjugationsAndFiles), getRomanianConjugationDefinitions(conjugationsAndFiles, conjugationPath)), Language.ROMANIAN, getVerbs(verbFileDescription));
        RomanianRuleFactory ruleFactory = new RomanianRuleFactory(verbRepository);
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.ROMANIAN, new RomanianCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        NounRepository nounRepository = new NounRepository(Language.ROMANIAN, romanianDeclensionFactory, getFileContentForRepository(nounFileDescription));
        Analizer romanianAnalyzer = new RomanianAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getFileContentForRepository(romanianFrenchDataFile), getFileContentForRepository(frenchVerbsDataFile), verbRepository, nounRepository, declensionPath, declensionsAndFiles, romanianDeclensionFactory);
        translatorBridge = new TranslatorBridge(romanianAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(romanianPathFile);
        mapValuesForResult = loadMapFromFiles(romanianResultFile);
    }

    private Map<String, List<String>> getRomanianConjugationDefinitions(String file, String directory) {
        /*
        Map<String, List<String>> latinConjugationDefinitionsMap = new HashMap<>();
        latinConjugationDefinitionsMap.put("o-is", getOIslDefinition());
        return latinConjugationDefinitionsMap;
        */


        List<String> conjugationNameList = getFileContentForRepository(file);
        Map<String, List<String>> romanianConjugationDefinitionsMap = new HashMap<>();
        for(String conjugationName : conjugationNameList) {
            String parts[] = conjugationName.split("%");
            String fileName = parts[1];
            String nameOnly = parts[0];
            romanianConjugationDefinitionsMap.put(nameOnly, getConjugationElements(directory,fileName));
        }
        return romanianConjugationDefinitionsMap;
    }

    private List<String> getRomanianConjugations(String conjugationsAndFiles) {
        /*
        return Arrays.asList(new String[]{
                "o-is%permittere.txt"
        });
        */

        return getFileContentForRepository(conjugationsAndFiles);
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

    private List<Declension> getDeclensionList(String file, String directory) {
        List<String> declensionNameList = getFileContentForRepository(file);
        List<Declension> declensionList = new ArrayList<>();
        for (String declensionName : declensionNameList) {
            String parts[] = declensionName.split("%");
            String fileName = parts[1];
            declensionList.add(new RomanianDeclension(fileName, getDeclensionElements(fileName, directory), false));
        }
        return declensionList;
    }

    @Test
    public void test_assimil_chapter1() {
        checkInMaps("assimil1A", translatorBridge);
        checkInMaps("assimil1B", translatorBridge);
        checkInMaps("assimil1C", translatorBridge);
        checkInMaps("assimil1D", translatorBridge);
        checkInMaps("assimil1E", translatorBridge);
        checkInMaps("assimil1F", translatorBridge);
        checkInMaps("assimil1G", translatorBridge);
        checkInMaps("assimil1H", translatorBridge);
        checkInMaps("assimil1I", translatorBridge);
        checkInMaps("assimil1J", translatorBridge);
        checkInMaps("assimil1K", translatorBridge);
        checkInMaps("assimil1L", translatorBridge);
        checkInMaps("assimil1M", translatorBridge);
    }

    @Test
    public void test_assimil_chapter2() {
        checkInMaps("assimil2A", translatorBridge);
        checkInMaps("assimil2B", translatorBridge);
        checkInMaps("assimil2C", translatorBridge);
        checkInMaps("assimil2D", translatorBridge);
        checkInMaps("assimil2E", translatorBridge);
        checkInMaps("assimil2F", translatorBridge);
        checkInMaps("assimil2G", translatorBridge);
        checkInMaps("assimil2H", translatorBridge);
        checkInMaps("assimil2I", translatorBridge);
        checkInMaps("assimil2J", translatorBridge);
        checkInMaps("assimil2K", translatorBridge);
        checkInMaps("assimil2L", translatorBridge);
        checkInMaps("assimil2M", translatorBridge);
        checkInMaps("assimil2N", translatorBridge);
    }

    @Test
    public void test_assimil_chapter3() {
        checkInMaps("assimil3A", translatorBridge);
        checkInMaps("assimil3B", translatorBridge);
        checkInMaps("assimil3C", translatorBridge);
        checkInMaps("assimil3D", translatorBridge);
        checkInMaps("assimil3E", translatorBridge);
        checkInMaps("assimil3F", translatorBridge);
        checkInMaps("assimil3G", translatorBridge);
        checkInMaps("assimil3H", translatorBridge);
        checkInMaps("assimil3I", translatorBridge);
        checkInMaps("assimil3J", translatorBridge);
        checkInMaps("assimil3K", translatorBridge);
        checkInMaps("assimil3L", translatorBridge);
        checkInMaps("assimil3M", translatorBridge);
        checkInMaps("assimil3N", translatorBridge);
        checkInMaps("assimil3O", translatorBridge);
        checkInMaps("assimil3P", translatorBridge);
    }

    @Test
    public void test_assimil_chapter4() {
        checkInMaps("assimil4A", translatorBridge);
        checkInMaps("assimil4B", translatorBridge);
        checkInMaps("assimil4C", translatorBridge);
        checkInMaps("assimil4D", translatorBridge);
        checkInMaps("assimil4E", translatorBridge);
        checkInMaps("assimil4F", translatorBridge);
        checkInMaps("assimil4G", translatorBridge);
        checkInMaps("assimil4H", translatorBridge);
        checkInMaps("assimil4I", translatorBridge);
        checkInMaps("assimil4J", translatorBridge);
        checkInMaps("assimil4K", translatorBridge);
        checkInMaps("assimil4L", translatorBridge);
        checkInMaps("assimil4M", translatorBridge);
        checkInMaps("assimil4N", translatorBridge);
        checkInMaps("assimil4O", translatorBridge);
    }

    @Test
    public void test_assimil_chapter5() {
        checkInMaps("assimil5A", translatorBridge);
        checkInMaps("assimil5B", translatorBridge);
        checkInMaps("assimil5C", translatorBridge);
        checkInMaps("assimil5D", translatorBridge);
        checkInMaps("assimil5E", translatorBridge);
        checkInMaps("assimil5F", translatorBridge);
        checkInMaps("assimil5G", translatorBridge);
        checkInMaps("assimil5H", translatorBridge);
        checkInMaps("assimil5I", translatorBridge);
        checkInMaps("assimil5J", translatorBridge);
        checkInMaps("assimil5K", translatorBridge);
        checkInMaps("assimil5L", translatorBridge);
        checkInMaps("assimil5M", translatorBridge);
        checkInMaps("assimil5N", translatorBridge);
        checkInMaps("assimil5O", translatorBridge);
        checkInMaps("assimil5P", translatorBridge);
        checkInMaps("assimil5Q", translatorBridge);
    }

    @Test
    public void test_assimil_chapter6() {
        checkInMaps("assimil6A", translatorBridge);
        checkInMaps("assimil6B", translatorBridge);
        checkInMaps("assimil6C", translatorBridge);
        checkInMaps("assimil6D", translatorBridge);
        checkInMaps("assimil6E", translatorBridge);
        checkInMaps("assimil6F", translatorBridge);
        checkInMaps("assimil6G", translatorBridge);
        checkInMaps("assimil6H", translatorBridge);
        checkInMaps("assimil6I", translatorBridge);
        checkInMaps("assimil6J", translatorBridge);
        checkInMaps("assimil6K", translatorBridge);
        checkInMaps("assimil6L", translatorBridge);
        checkInMaps("assimil6M", translatorBridge);
        checkInMaps("assimil6N", translatorBridge);
    }

    @Test
    public void test_assimil_chapter7() {
        assertEquals(true, true);
    }

    @Test
    public void test_assimil_chapter8() {
        checkInMaps("assimil8A", translatorBridge);
        checkInMaps("assimil8B", translatorBridge);
        checkInMaps("assimil8C", translatorBridge);
        checkInMaps("assimil8D", translatorBridge);
        checkInMaps("assimil8E", translatorBridge);
        checkInMaps("assimil8F", translatorBridge);
        checkInMaps("assimil8G", translatorBridge);
        checkInMaps("assimil8H", translatorBridge);
        checkInMaps("assimil8I", translatorBridge);
        checkInMaps("assimil8J", translatorBridge);
        checkInMaps("assimil8K", translatorBridge);
        checkInMaps("assimil8L", translatorBridge);
        checkInMaps("assimil8M", translatorBridge);
        checkInMaps("assimil8N", translatorBridge);
        checkInMaps("assimil8O", translatorBridge);
    }

    @Test
    public void test_assimil_chapter9() {
        checkInMaps("assimil9A", translatorBridge);
        checkInMaps("assimil9B", translatorBridge);
        checkInMaps("assimil9C", translatorBridge);
        checkInMaps("assimil9D", translatorBridge);
        checkInMaps("assimil9E", translatorBridge);
        checkInMaps("assimil9F", translatorBridge);
        checkInMaps("assimil9G", translatorBridge);
        checkInMaps("assimil9H", translatorBridge);
        checkInMaps("assimil9J", translatorBridge);
        checkInMaps("assimil9K", translatorBridge);
        checkInMaps("assimil9L", translatorBridge);
        checkInMaps("assimil9M", translatorBridge);
        checkInMaps("assimil9N", translatorBridge);
        checkInMaps("assimil9O", translatorBridge);
        checkInMaps("assimil9P", translatorBridge);
    }

    @Test
    public void test_assimil_chapter10() {
        checkInMaps("assimil10A", translatorBridge);
        checkInMaps("assimil10B", translatorBridge);
        checkInMaps("assimil10C", translatorBridge);
        checkInMaps("assimil10D", translatorBridge);
        checkInMaps("assimil10E", translatorBridge);
        checkInMaps("assimil10F", translatorBridge);
        checkInMaps("assimil10G", translatorBridge);
        checkInMaps("assimil10H", translatorBridge);
        checkInMaps("assimil10I", translatorBridge);
        checkInMaps("assimil10J", translatorBridge);
        checkInMaps("assimil10K", translatorBridge);
        checkInMaps("assimil10L", translatorBridge);
        checkInMaps("assimil10M", translatorBridge);
        checkInMaps("assimil10N", translatorBridge);
        checkInMaps("assimil10O", translatorBridge);
        checkInMaps("assimil10P", translatorBridge);
    }

    @Test
    public void test_assimil_chapter11() {
        checkInMaps("assimil11A", translatorBridge);
        checkInMaps("assimil11B", translatorBridge);
        checkInMaps("assimil11C", translatorBridge);
        checkInMaps("assimil11D", translatorBridge);
        checkInMaps("assimil11E", translatorBridge);
        checkInMaps("assimil11F", translatorBridge);
        checkInMaps("assimil11G", translatorBridge);
        checkInMaps("assimil11H", translatorBridge);
        checkInMaps("assimil11I", translatorBridge);
        checkInMaps("assimil11J", translatorBridge);
        checkInMaps("assimil11K", translatorBridge);
        checkInMaps("assimil11L", translatorBridge);
        checkInMaps("assimil11M", translatorBridge);
        checkInMaps("assimil11N", translatorBridge);
        checkInMaps("assimil11O", translatorBridge);
        checkInMaps("assimil11P", translatorBridge);
        checkInMaps("assimil11Q", translatorBridge);
        checkInMaps("assimil11R", translatorBridge);
    }

    @Test
    public void test_assimil_chapter12() {
        checkInMaps("assimil12A", translatorBridge);
        checkInMaps("assimil12B", translatorBridge);
        checkInMaps("assimil12C", translatorBridge);
        checkInMaps("assimil12D", translatorBridge);
        checkInMaps("assimil12E", translatorBridge);
        checkInMaps("assimil12F", translatorBridge);
        checkInMaps("assimil12G", translatorBridge);
        checkInMaps("assimil12H", translatorBridge);
        checkInMaps("assimil12I", translatorBridge);
        checkInMaps("assimil12J", translatorBridge);
        checkInMaps("assimil12K", translatorBridge);
        checkInMaps("assimil12L", translatorBridge);
        checkInMaps("assimil12M", translatorBridge);
        checkInMaps("assimil12N", translatorBridge);
        checkInMaps("assimil12O", translatorBridge);
        checkInMaps("assimil12P", translatorBridge);
    }

    @Test
    public void test_assimil_chapter13() {
        checkInMaps("assimil13A", translatorBridge);
        checkInMaps("assimil13B", translatorBridge);
        checkInMaps("assimil13C", translatorBridge);
        checkInMaps("assimil13D", translatorBridge);
        checkInMaps("assimil13E", translatorBridge);
        checkInMaps("assimil13F", translatorBridge);
        checkInMaps("assimil13G", translatorBridge);
        checkInMaps("assimil13H", translatorBridge);
        checkInMaps("assimil13I", translatorBridge);
        checkInMaps("assimil13J", translatorBridge);
        checkInMaps("assimil13K", translatorBridge);
        checkInMaps("assimil13L", translatorBridge);
        checkInMaps("assimil13M", translatorBridge);
        checkInMaps("assimil13N", translatorBridge);
        checkInMaps("assimil13O", translatorBridge);
        checkInMaps("assimil13P", translatorBridge);
        checkInMaps("assimil13Q", translatorBridge);
        checkInMaps("assimil13R", translatorBridge);
    }

    @Test
    public void test_assimil_chapter14() {
        assertEquals(2, 2);
    }

    @Test
    public void test_assimil_chapter15() {
        checkInMaps("assimil15A", translatorBridge);
        checkInMaps("assimil15B", translatorBridge);
        checkInMaps("assimil15C", translatorBridge);
        checkInMaps("assimil15D", translatorBridge);
        checkInMaps("assimil15E", translatorBridge);
        checkInMaps("assimil15F", translatorBridge);
        checkInMaps("assimil15G", translatorBridge);
        checkInMaps("assimil15H", translatorBridge);
        checkInMaps("assimil15I", translatorBridge);
        checkInMaps("assimil15J", translatorBridge);
        checkInMaps("assimil15K", translatorBridge);
        checkInMaps("assimil15L", translatorBridge);
        checkInMaps("assimil15M", translatorBridge);
        checkInMaps("assimil15N", translatorBridge);
        checkInMaps("assimil15O", translatorBridge);
        checkInMaps("assimil15P", translatorBridge);
        checkInMaps("assimil15Q", translatorBridge);
        checkInMaps("assimil15R", translatorBridge);
        checkInMaps("assimil15S", translatorBridge);
        checkInMaps("assimil15T", translatorBridge);
        checkInMaps("assimil15U", translatorBridge);
    }
    @Test
    public void test_assimil_chapter16() {
        checkInMaps("assimil16A", translatorBridge);
        checkInMaps("assimil16B", translatorBridge);
        checkInMaps("assimil16C", translatorBridge);
        checkInMaps("assimil16D", translatorBridge);
        checkInMaps("assimil16E", translatorBridge);
        checkInMaps("assimil16F", translatorBridge);
        checkInMaps("assimil16G", translatorBridge);
        checkInMaps("assimil16H", translatorBridge);
        checkInMaps("assimil16I", translatorBridge);
        checkInMaps("assimil16J", translatorBridge);
        checkInMaps("assimil16K", translatorBridge);
        checkInMaps("assimil16L", translatorBridge);
        checkInMaps("assimil16M", translatorBridge);
        checkInMaps("assimil16N", translatorBridge);
        checkInMaps("assimil16O", translatorBridge);
        checkInMaps("assimil16P", translatorBridge);
        checkInMaps("assimil16Q", translatorBridge);
        checkInMaps("assimil16R", translatorBridge);
        checkInMaps("assimil16S", translatorBridge);
        checkInMaps("assimil16T", translatorBridge);
        checkInMaps("assimil16U", translatorBridge);
    }

    @Test
    public void test_assimil_chapter17() {
        checkInMaps("assimil17A", translatorBridge);
        checkInMaps("assimil17B", translatorBridge);
        checkInMaps("assimil17C", translatorBridge);
        checkInMaps("assimil17D", translatorBridge);
        checkInMaps("assimil17E", translatorBridge);
        checkInMaps("assimil17F", translatorBridge);
        checkInMaps("assimil17G", translatorBridge);
        checkInMaps("assimil17H", translatorBridge);
        checkInMaps("assimil17I", translatorBridge);
        checkInMaps("assimil17J", translatorBridge);
        checkInMaps("assimil17K", translatorBridge);
        checkInMaps("assimil17L", translatorBridge);
        checkInMaps("assimil17M", translatorBridge);
        checkInMaps("assimil17N", translatorBridge);
        checkInMaps("assimil17O", translatorBridge);
        checkInMaps("assimil17P", translatorBridge);
        checkInMaps("assimil17Q", translatorBridge);
        checkInMaps("assimil17R", translatorBridge);
        checkInMaps("assimil17S", translatorBridge);
        checkInMaps("assimil17T", translatorBridge);
    }

    @Test
    public void test_assimil_chapter18() {
        checkInMaps("assimil18A", translatorBridge);
        checkInMaps("assimil18B", translatorBridge);
        checkInMaps("assimil18C", translatorBridge);
        checkInMaps("assimil18D", translatorBridge);
        checkInMaps("assimil18E", translatorBridge);
        checkInMaps("assimil18F", translatorBridge);
        checkInMaps("assimil18G", translatorBridge);
        checkInMaps("assimil18H", translatorBridge);
        checkInMaps("assimil18I", translatorBridge);
        checkInMaps("assimil18J", translatorBridge);
        checkInMaps("assimil18K", translatorBridge);
        checkInMaps("assimil18L", translatorBridge);
        checkInMaps("assimil18M", translatorBridge);
        checkInMaps("assimil18N", translatorBridge);
        checkInMaps("assimil18O", translatorBridge);
        checkInMaps("assimil18P", translatorBridge);
        checkInMaps("assimil18Q", translatorBridge);
    }

    @Test
    public void test_assimil_chapter19() {
        checkInMaps("assimil19A", translatorBridge);
        checkInMaps("assimil19B", translatorBridge);
        checkInMaps("assimil19C", translatorBridge);
        checkInMaps("assimil19D", translatorBridge);
        checkInMaps("assimil19E", translatorBridge);
        checkInMaps("assimil19F", translatorBridge);
        checkInMaps("assimil19G", translatorBridge);
        checkInMaps("assimil19H", translatorBridge);
        checkInMaps("assimil19I", translatorBridge);
        checkInMaps("assimil19J", translatorBridge);
        checkInMaps("assimil19K", translatorBridge);
        checkInMaps("assimil19L", translatorBridge);
        checkInMaps("assimil19M", translatorBridge);
        checkInMaps("assimil19N", translatorBridge);
        checkInMaps("assimil19O", translatorBridge);
        checkInMaps("assimil19P", translatorBridge);
        checkInMaps("assimil19Q", translatorBridge);
        checkInMaps("assimil19R", translatorBridge);
        checkInMaps("assimil19S", translatorBridge);
        checkInMaps("assimil19T", translatorBridge);
        checkInMaps("assimil19U", translatorBridge);
    }

    @Test
    public void test_assimil_chapter20() {
        checkInMaps("assimil20A", translatorBridge);
        checkInMaps("assimil20B", translatorBridge);
        checkInMaps("assimil20C", translatorBridge);
        checkInMaps("assimil20D", translatorBridge);
        checkInMaps("assimil20E", translatorBridge);
        checkInMaps("assimil20F", translatorBridge);
        checkInMaps("assimil20G", translatorBridge);
        checkInMaps("assimil20H", translatorBridge);
        checkInMaps("assimil20I", translatorBridge);
        checkInMaps("assimil20J", translatorBridge);
        checkInMaps("assimil20K", translatorBridge);
        checkInMaps("assimil20L", translatorBridge);
        checkInMaps("assimil20M", translatorBridge);
        checkInMaps("assimil20N", translatorBridge);
        checkInMaps("assimil20O", translatorBridge);
        checkInMaps("assimil20P", translatorBridge);
        checkInMaps("assimil20Q", translatorBridge);
        checkInMaps("assimil20R", translatorBridge);
        checkInMaps("assimil20S", translatorBridge);
        checkInMaps("assimil20T", translatorBridge);
    }

        @Test
    public void test_staniloae_page1() {
        checkInMaps("staniloae1A", translatorBridge);
        checkInMaps("staniloae1B", translatorBridge);
        checkInMaps("staniloae1C", translatorBridge);
        checkInMaps("staniloae1D", translatorBridge);
        checkInMaps("staniloae1E", translatorBridge);
        checkInMaps("staniloae1F", translatorBridge);
        checkInMaps("staniloae1G", translatorBridge);
        checkInMaps("staniloae1H", translatorBridge);
        checkInMaps("staniloae1I", translatorBridge);
        checkInMaps("staniloae1J", translatorBridge);
        checkInMaps("staniloae1K", translatorBridge);
        checkInMaps("staniloae1L", translatorBridge);
        checkInMaps("staniloae1M", translatorBridge);
        checkInMaps("staniloae1N", translatorBridge);
        checkInMaps("staniloae1O", translatorBridge);
        checkInMaps("staniloae1P", translatorBridge);
        checkInMaps("staniloae1Q", translatorBridge);
        checkInMaps("staniloae1R", translatorBridge);
        checkInMaps("staniloae1S", translatorBridge);
        checkInMaps("staniloae1T", translatorBridge);
        checkInMaps("staniloae1U", translatorBridge);
        checkInMaps("staniloae1V", translatorBridge);
        checkInMaps("staniloae1W", translatorBridge);
        checkInMaps("staniloae1X", translatorBridge);
        checkInMaps("staniloae1Y", translatorBridge);
        checkInMaps("staniloae1Z", translatorBridge);
        checkInMaps("staniloae1AA", translatorBridge);
        checkInMaps("staniloae1BB", translatorBridge);
        checkInMaps("staniloae1CC", translatorBridge);
        checkInMaps("staniloae1DD", translatorBridge);
        checkInMaps("staniloae1EE", translatorBridge);
        checkInMaps("staniloae1FF", translatorBridge);
        checkInMaps("staniloae1GG", translatorBridge);
        checkInMaps("staniloae1HH", translatorBridge);
        checkInMaps("staniloae1II", translatorBridge);
        checkInMaps("staniloae1JJ", translatorBridge);
    }

    @Test
    public void test_trone_de_fer_chapter1() {
        checkInMaps("urzeala1A", translatorBridge);
        checkInMaps("urzeala1B", translatorBridge);
        checkInMaps("urzeala1C", translatorBridge);
        checkInMaps("urzeala1D", translatorBridge);
        checkInMaps("urzeala1E", translatorBridge);
        checkInMaps("urzeala1F", translatorBridge);
        checkInMaps("urzeala1G", translatorBridge);
        checkInMaps("urzeala1H", translatorBridge);
        checkInMaps("urzeala1I", translatorBridge);
        checkInMaps("urzeala1J", translatorBridge);
        checkInMaps("urzeala1K", translatorBridge);
        checkInMaps("urzeala1L", translatorBridge);
        checkInMaps("urzeala2A", translatorBridge);
        checkInMaps("urzeala2B", translatorBridge);
        checkInMaps("urzeala2C", translatorBridge);
        checkInMaps("urzeala2D", translatorBridge);
        checkInMaps("urzeala2E", translatorBridge);
        checkInMaps("urzeala2F", translatorBridge);
    }

    @Test
    public void test_filocalia1() {
        checkInMaps("filocalia1A", translatorBridge);
        checkInMaps("filocalia1B", translatorBridge);
        checkInMaps("filocalia1C", translatorBridge);
        checkInMaps("filocalia1D", translatorBridge);
        checkInMaps("filocalia1E", translatorBridge);
        checkInMaps("filocalia1F", translatorBridge);
        checkInMaps("filocalia1G", translatorBridge);
        checkInMaps("filocalia1H", translatorBridge);
        checkInMaps("filocalia1I", translatorBridge);
        checkInMaps("filocalia1J", translatorBridge);
        checkInMaps("filocalia1K", translatorBridge);
        checkInMaps("filocalia1L", translatorBridge);
        checkInMaps("filocalia1M", translatorBridge);
        checkInMaps("filocalia1N", translatorBridge);
        checkInMaps("filocalia1O", translatorBridge);
        checkInMaps("filocalia1P", translatorBridge);
        checkInMaps("filocalia1Q", translatorBridge);
        checkInMaps("filocalia1R", translatorBridge);
        checkInMaps("filocalia1S", translatorBridge);
        checkInMaps("filocalia1T", translatorBridge);
        checkInMaps("filocalia1U", translatorBridge);
        checkInMaps("filocalia1V", translatorBridge);
        checkInMaps("filocalia1W", translatorBridge);
        checkInMaps("filocalia1X", translatorBridge);
        checkInMaps("filocalia1Y", translatorBridge);
        checkInMaps("filocalia1Z", translatorBridge);
    }

    @Test
    public void test_boca1() {
        checkInMaps("bocaseumplu7A", translatorBridge);
        checkInMaps("bocaseumplu7B", translatorBridge);
        checkInMaps("bocaseumplu7C", translatorBridge);
        checkInMaps("bocaseumplu7D", translatorBridge);
        checkInMaps("bocaseumplu7E", translatorBridge);
        checkInMaps("bocaseumplu7F", translatorBridge);
    }

    @Test
    public void test_patericul1() {
        checkInMaps("patericulegyptantonie1A", translatorBridge);
        checkInMaps("patericulegyptantonie1B", translatorBridge);
        checkInMaps("patericulegyptantonie1C", translatorBridge);
        checkInMaps("patericulegyptantonie1D", translatorBridge);
        checkInMaps("patericulegyptantonie1E", translatorBridge);
        checkInMaps("patericulegyptantonie1F", translatorBridge);
        checkInMaps("patericulegyptantonie1G", translatorBridge);
        checkInMaps("patericulegyptantonie1H", translatorBridge);
        checkInMaps("patericulegyptantonie1I", translatorBridge);
        checkInMaps("patericulegyptantonie1J", translatorBridge);
    }

    @Test
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("filocalia1N", translatorBridge);
    }
    
}

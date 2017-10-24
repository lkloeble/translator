import org.junit.Before;
import org.junit.Test;
import org.patrologia.translator.TranslatorBridge;

import static org.junit.Assert.assertTrue;

/**
 * Created by Laurent KLOEBLE on 08/10/2015.
 */
public class GreekTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    @Before
    public void init() {
        String prepositionFileDescription = "C:\\java_projects\\src\\main\\resources\\greek\\prepositions.txt";
        String nounFileDescription = "C:\\java_projects\\src\\main\\resources\\greek\\nouns.txt";
        String verbFileDescription = "C:\\java_projects\\src\\main\\resources\\greek\\verbs.txt";
        String greekFrenchDataFile = "C:\\java_projects\\src\\main\\resources\\greek\\bailly_greek_to_french.txt";
        String frenchVerbsDataFile = "C:\\java_projects\\src\\main\\resources\\french_verbs.txt";
        String demonstrativeDataFile = "C:\\java_projects\\src\\main\\resources\\greek\\pronoms.txt";
        String declensionPath = "C:\\java_projects\\src\\main\\resources\\greek\\declensions";
        String declensionAndFiles = "C:\\java_projects\\src\\main\\resources\\greek\\declensionsAndFiles.txt";
        String conjugationPath = "C:\\java_projects\\src\\main\\resources\\greek\\conjugations";
        String conjugationsAndFiles = "C:\\java_projects\\src\\main\\resources\\greek\\conjugationsAndFiles.txt";
        String greekPathFile = "C:\\java_projects\\src\\test\\resources\\greek_content.txt";
        String greekResultFile = "C:\\java_projects\\src\\test\\resources\\greek_expected_result.txt";
        /*
        GreekRuleFactory ruleFactory = new GreekRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.GREEK, new GreekCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        NounRepository nounRepository = new NounRepository(Language.GREEK, new GreekDeclensionFactory(declensionPath, declensionAndFiles), getFileContentForRepository(nounFileDescription));
        VerbRepository verbRepository = new VerbRepository(new GreekConjugationFactory(conjugationPath, conjugationsAndFiles), Language.GREEK, getFileContentForRepository(verbFileDescription));
        DemonstrativeRepository demonstrativeRepository = new DemonstrativeRepository(Language.GREEK, getFileContentForRepository(demonstrativeDataFile));
        Analizer greekAnalyzer = new GreekAnalyzer(prepositionRepository, nounRepository, verbRepository, demonstrativeRepository);
        Translator frenchTranslator = new FrenchTranslator(getFileContentForRepository(greekFrenchDataFile), getFileContentForRepository(frenchVerbsDataFile), verbRepository, demonstrativeRepository, nounRepository, declensionPath, declensionAndFiles);
        translatorBridge = new TranslatorBridge(greekAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(greekPathFile);
        mapValuesForResult = loadMapFromFiles(greekResultFile);
        */
    }

    @Test
    public void test_bridge_on_assimil_lesson1() {
        checkInMaps("assimil1A", translatorBridge);
        checkInMaps("assimil1B", translatorBridge);
        checkInMaps("assimil1C", translatorBridge);
        checkInMaps("assimil1D", translatorBridge);
        checkInMaps("assimil1E", translatorBridge);
    }

    @Test
    public void test_bridge_on_assimil_lesson2() {
        checkInMaps("assimil2A", translatorBridge);
        checkInMaps("assimil2B", translatorBridge);
        checkInMaps("assimil2C", translatorBridge);
        checkInMaps("assimil2D", translatorBridge);
        checkInMaps("assimil2E", translatorBridge);
    }

    @Test
    public void test_bridge_on_assimil_lesson3() {
        checkInMaps("assimil3A", translatorBridge);
        checkInMaps("assimil3B", translatorBridge);
        checkInMaps("assimil3C", translatorBridge);
        checkInMaps("assimil3D", translatorBridge);
    }

    @Test
    public void test_bridge_on_assimil_lesson4() {
        checkInMaps("assimil4A", translatorBridge);
        checkInMaps("assimil4B", translatorBridge);
        checkInMaps("assimil4C", translatorBridge);
        checkInMaps("assimil4D", translatorBridge);
        checkInMaps("assimil4E", translatorBridge);
    }

    @Test
    public void test_bridge_on_assimil_lesson5() {
        checkInMaps("assimil5A", translatorBridge);
        checkInMaps("assimil5C", translatorBridge);
    }

    @Test
    public void test_bridge_on_assimil_lesson6() {
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
    }

    @Test
    public void test_bridge_on_assimil_lesson7() {
        checkInMaps("assimil7A", translatorBridge);
        checkInMaps("assimil7B", translatorBridge);
        checkInMaps("assimil7C", translatorBridge);
        checkInMaps("assimil7D", translatorBridge);
        checkInMaps("assimil7E", translatorBridge);
    }

    @Test
    public void test_bridge_on_assimil_lesson_8() {
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
    }

    @Test
    public void test_bridge_on_assimil_lesson_9() {
        checkInMaps("assimil9A", translatorBridge);
        checkInMaps("assimil9B", translatorBridge);
        checkInMaps("assimil9C", translatorBridge);
        checkInMaps("assimil9D", translatorBridge);
        checkInMaps("assimil9E", translatorBridge);
        checkInMaps("assimil9F", translatorBridge);
        checkInMaps("assimil9G", translatorBridge);
        checkInMaps("assimil9H", translatorBridge);
        checkInMaps("assimil9I", translatorBridge);
        checkInMaps("assimil9J", translatorBridge);
        checkInMaps("assimil9K", translatorBridge);
    }

    @Test
    public void test_bridge_on_assimil_lesson_10() {
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
    }

    @Test
    public void test_bridge_on_wenham_lesson3() {
        checkInMaps("wenham3A", translatorBridge);
        checkInMaps("wenham3B", translatorBridge);
    }


    @Test
    public void test_bridge_on_wenham_lesson4() {
        checkInMaps("wenham4A", translatorBridge);
        checkInMaps("wenham4B", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson5() {
        checkInMaps("wenham5A", translatorBridge);
        checkInMaps("wenham5B", translatorBridge);
        checkInMaps("wenham5C", translatorBridge);
        checkInMaps("wenham5D", translatorBridge);
        checkInMaps("wenham5E", translatorBridge);
        checkInMaps("wenham5F", translatorBridge);
        checkInMaps("wenham5G", translatorBridge);
        checkInMaps("wenham5H", translatorBridge);
        checkInMaps("wenham5I", translatorBridge);
        checkInMaps("wenham5J", translatorBridge);
        checkInMaps("wenham5K", translatorBridge);
        checkInMaps("wenham5L", translatorBridge);
        checkInMaps("wenham5M", translatorBridge);
        checkInMaps("wenham5N", translatorBridge);
        checkInMaps("wenham5O", translatorBridge);
        checkInMaps("wenham5P", translatorBridge);
        checkInMaps("wenham5Q", translatorBridge);
        checkInMaps("wenham5R", translatorBridge);
        checkInMaps("wenham5S", translatorBridge);
        checkInMaps("wenham5T", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson6() {
        checkInMaps("wenham6A", translatorBridge);
        checkInMaps("wenham6B", translatorBridge);
        checkInMaps("wenham6C", translatorBridge);
        checkInMaps("wenham6D", translatorBridge);
        checkInMaps("wenham6E", translatorBridge);
        checkInMaps("wenham6F", translatorBridge);
        checkInMaps("wenham6G", translatorBridge);
        checkInMaps("wenham6H", translatorBridge);
        checkInMaps("wenham6I", translatorBridge);
        checkInMaps("wenham6J", translatorBridge);
        checkInMaps("wenham6K", translatorBridge);
        checkInMaps("wenham6L", translatorBridge);
        checkInMaps("wenham6M", translatorBridge);
        checkInMaps("wenham6N", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson7() {
        checkInMaps("wenham7A", translatorBridge);
        checkInMaps("wenham7B", translatorBridge);
        checkInMaps("wenham7C", translatorBridge);
        checkInMaps("wenham7D", translatorBridge);
        checkInMaps("wenham7E", translatorBridge);
        checkInMaps("wenham7F", translatorBridge);
        checkInMaps("wenham7G", translatorBridge);
        checkInMaps("wenham7H", translatorBridge);
        checkInMaps("wenham7I", translatorBridge);
        checkInMaps("wenham7J", translatorBridge);
        checkInMaps("wenham7K", translatorBridge);
        checkInMaps("wenham7L", translatorBridge);
        checkInMaps("wenham7M", translatorBridge);
        checkInMaps("wenham7N", translatorBridge);
        checkInMaps("wenham7O", translatorBridge);
        checkInMaps("wenham7P", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson8() {
        checkInMaps("wenham8A", translatorBridge);
        checkInMaps("wenham8B", translatorBridge);
        checkInMaps("wenham8C", translatorBridge);
        checkInMaps("wenham8D", translatorBridge);
        checkInMaps("wenham8E", translatorBridge);
        checkInMaps("wenham8F", translatorBridge);
        checkInMaps("wenham8G", translatorBridge);
        checkInMaps("wenham8H", translatorBridge);
        checkInMaps("wenham8I", translatorBridge);
        checkInMaps("wenham8J", translatorBridge);
        checkInMaps("wenham8K", translatorBridge);
        checkInMaps("wenham8L", translatorBridge);
        checkInMaps("wenham8M", translatorBridge);
        checkInMaps("wenham8N", translatorBridge);
        checkInMaps("wenham8O", translatorBridge);
        checkInMaps("wenham8P", translatorBridge);
        checkInMaps("wenham8Q", translatorBridge);
        checkInMaps("wenham8R", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson9() {
        checkInMaps("wenham9A", translatorBridge);
        checkInMaps("wenham9B", translatorBridge);
        checkInMaps("wenham9C", translatorBridge);
        checkInMaps("wenham9D", translatorBridge);
        checkInMaps("wenham9E", translatorBridge);
        checkInMaps("wenham9F", translatorBridge);
        checkInMaps("wenham9G", translatorBridge);
        checkInMaps("wenham9H", translatorBridge);
        checkInMaps("wenham9I", translatorBridge);
        checkInMaps("wenham9J", translatorBridge);
        checkInMaps("wenham9K", translatorBridge);
        checkInMaps("wenham9L", translatorBridge);
        checkInMaps("wenham9M", translatorBridge);
        checkInMaps("wenham9N", translatorBridge);
        checkInMaps("wenham9O", translatorBridge);
        checkInMaps("wenham9P", translatorBridge);
        checkInMaps("wenham9Q", translatorBridge);
        checkInMaps("wenham9R", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson10() {
        checkInMaps("wenham10A", translatorBridge);
        checkInMaps("wenham10B", translatorBridge);
        checkInMaps("wenham10C", translatorBridge);
        checkInMaps("wenham10D", translatorBridge);
        checkInMaps("wenham10E", translatorBridge);
        checkInMaps("wenham10F", translatorBridge);
        checkInMaps("wenham10G", translatorBridge);
        checkInMaps("wenham10H", translatorBridge);
        checkInMaps("wenham10I", translatorBridge);
        checkInMaps("wenham10J", translatorBridge);
        checkInMaps("wenham10K", translatorBridge);
        checkInMaps("wenham10L", translatorBridge);
        checkInMaps("wenham10M", translatorBridge);
        checkInMaps("wenham10N", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson11() {
        checkInMaps("wenham11A", translatorBridge);
        checkInMaps("wenham11B", translatorBridge);
        checkInMaps("wenham11C", translatorBridge);
        checkInMaps("wenham11D", translatorBridge);
        checkInMaps("wenham11E", translatorBridge);
        checkInMaps("wenham11F", translatorBridge);
        checkInMaps("wenham11G", translatorBridge);
        checkInMaps("wenham11H", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson12() {
        checkInMaps("wenham12A", translatorBridge);
        checkInMaps("wenham12B", translatorBridge);
        checkInMaps("wenham12C", translatorBridge);
        checkInMaps("wenham12D", translatorBridge);
        checkInMaps("wenham12E", translatorBridge);
        checkInMaps("wenham12F", translatorBridge);
        checkInMaps("wenham12G", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson13() {
        checkInMaps("wenham13A", translatorBridge);
        checkInMaps("wenham13B", translatorBridge);
        checkInMaps("wenham13C", translatorBridge);
        checkInMaps("wenham13D", translatorBridge);
        checkInMaps("wenham13E", translatorBridge);
        checkInMaps("wenham13F", translatorBridge);
        checkInMaps("wenham13G", translatorBridge);
        checkInMaps("wenham13H", translatorBridge);
        checkInMaps("wenham13I", translatorBridge);
        checkInMaps("wenham13J", translatorBridge);
        checkInMaps("wenham13K", translatorBridge);
        checkInMaps("wenham13L", translatorBridge);
        checkInMaps("wenham13M", translatorBridge);
        checkInMaps("wenham13N", translatorBridge);
        checkInMaps("wenham13O", translatorBridge);
        checkInMaps("wenham13P", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson14() {
        checkInMaps("wenham14A", translatorBridge);
        checkInMaps("wenham14B", translatorBridge);
        checkInMaps("wenham14C", translatorBridge);
        checkInMaps("wenham14D", translatorBridge);
        checkInMaps("wenham14E", translatorBridge);
        checkInMaps("wenham14F", translatorBridge);
        checkInMaps("wenham14G", translatorBridge);
        checkInMaps("wenham14H", translatorBridge);
        checkInMaps("wenham14I", translatorBridge);
        checkInMaps("wenham14J", translatorBridge);
        checkInMaps("wenham14K", translatorBridge);
        checkInMaps("wenham14L", translatorBridge);
        checkInMaps("wenham14M", translatorBridge);
        checkInMaps("wenham14N", translatorBridge);
        checkInMaps("wenham14O", translatorBridge);
        checkInMaps("wenham14P", translatorBridge);
        checkInMaps("wenham14Q", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson15() {
        checkInMaps("wenham15A", translatorBridge);
        checkInMaps("wenham15B", translatorBridge);
        checkInMaps("wenham15C", translatorBridge);
        checkInMaps("wenham15D", translatorBridge);
        checkInMaps("wenham15E", translatorBridge);
        checkInMaps("wenham15F", translatorBridge);
        checkInMaps("wenham15G", translatorBridge);
        checkInMaps("wenham15H", translatorBridge);
        checkInMaps("wenham15I", translatorBridge);
        checkInMaps("wenham15J", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson16() {
        checkInMaps("wenham16A", translatorBridge);
        checkInMaps("wenham16B", translatorBridge);
        checkInMaps("wenham16C", translatorBridge);
        checkInMaps("wenham16D", translatorBridge);
        checkInMaps("wenham16E", translatorBridge);
        checkInMaps("wenham16F", translatorBridge);
        checkInMaps("wenham16G", translatorBridge);
        checkInMaps("wenham16H", translatorBridge);
        checkInMaps("wenham16I", translatorBridge);
        checkInMaps("wenham16J", translatorBridge);
        checkInMaps("wenham16K", translatorBridge);
        checkInMaps("wenham16L", translatorBridge);
        checkInMaps("wenham16M", translatorBridge);
        checkInMaps("wenham16N", translatorBridge);
        checkInMaps("wenham16O", translatorBridge);
        checkInMaps("wenham16P", translatorBridge);
        checkInMaps("wenham16Q", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson17() {
        checkInMaps("wenham17A", translatorBridge);
        checkInMaps("wenham17B", translatorBridge);
        checkInMaps("wenham17C", translatorBridge);
        checkInMaps("wenham17D", translatorBridge);
        checkInMaps("wenham17E", translatorBridge);
        checkInMaps("wenham17F", translatorBridge);
        checkInMaps("wenham17G", translatorBridge);
        checkInMaps("wenham17H", translatorBridge);
        checkInMaps("wenham17I", translatorBridge);
        checkInMaps("wenham17J", translatorBridge);
        checkInMaps("wenham17K", translatorBridge);
        checkInMaps("wenham17L", translatorBridge);
        checkInMaps("wenham17M", translatorBridge);
        checkInMaps("wenham17N", translatorBridge);
        checkInMaps("wenham17O", translatorBridge);
        checkInMaps("wenham17P", translatorBridge);
    }

    @Test
    public void test_bridge_on_wenham_lesson18() {
        assertTrue(false);
    }

    @Test
    public void test_bridge_on_matthew_1() {
        checkInMaps("matthew1A", translatorBridge);
        checkInMaps("matthew1B", translatorBridge);
        checkInMaps("matthew1C", translatorBridge);
        checkInMaps("matthew1D", translatorBridge);
        checkInMaps("matthew1E", translatorBridge);
        checkInMaps("matthew1F", translatorBridge);
        checkInMaps("matthew1G", translatorBridge);
        checkInMaps("matthew1H", translatorBridge);
        checkInMaps("matthew1I", translatorBridge);
        checkInMaps("matthew1J", translatorBridge);
        checkInMaps("matthew1K", translatorBridge);
        checkInMaps("matthew1L", translatorBridge);
        checkInMaps("matthew1M", translatorBridge);
        checkInMaps("matthew1N", translatorBridge);
        checkInMaps("matthew1O", translatorBridge);
        checkInMaps("matthew1P", translatorBridge);
        checkInMaps("matthew1Q", translatorBridge);
        checkInMaps("matthew1R", translatorBridge);
        checkInMaps("matthew1S", translatorBridge);
        checkInMaps("matthew1T", translatorBridge);
        checkInMaps("matthew1U", translatorBridge);
        checkInMaps("matthew1V", translatorBridge);
        checkInMaps("matthew1W", translatorBridge);
        checkInMaps("matthew1X", translatorBridge);
        checkInMaps("matthew1Y", translatorBridge);
    }

    @Test
    public void test_anaximandre_fragments() {
        checkInMaps("anaxA", translatorBridge);
        checkInMaps("anaxB1", translatorBridge);
        checkInMaps("anaxB2", translatorBridge);
        checkInMaps("anaxC1", translatorBridge);
        checkInMaps("anaxC2", translatorBridge);
        checkInMaps("anaxC3", translatorBridge);
        checkInMaps("anaxC4", translatorBridge);
        checkInMaps("anaxD", translatorBridge);
        checkInMaps("anaxE1", translatorBridge);
        checkInMaps("anaxE2", translatorBridge);
        checkInMaps("anaxE3", translatorBridge);
        checkInMaps("anaxE4A", translatorBridge);
        checkInMaps("anaxE4B", translatorBridge);
        checkInMaps("anaxE5", translatorBridge);
        checkInMaps("anaxE6", translatorBridge);
        checkInMaps("anaxF", translatorBridge);
        checkInMaps("anaxG1", translatorBridge);
        checkInMaps("anaxG2", translatorBridge);
        checkInMaps("anaxG3", translatorBridge);
        checkInMaps("anaxH", translatorBridge);
        checkInMaps("anaxI1", translatorBridge);
        checkInMaps("anaxI2", translatorBridge);
        checkInMaps("anaxI3", translatorBridge);
        checkInMaps("anaxJ", translatorBridge);
        checkInMaps("anaxK", translatorBridge);
        checkInMaps("anaxL", translatorBridge);
        checkInMaps("anaxM1", translatorBridge);
        checkInMaps("anaxM2", translatorBridge);
        checkInMaps("anaxO", translatorBridge);
        checkInMaps("anaxP", translatorBridge);
        checkInMaps("anaxQ", translatorBridge);
        checkInMaps("anaxR", translatorBridge);

    }

    @Test
    public void test_lxx_genesis_chap01() {
        checkInMaps("lxxgen1A", translatorBridge);
        checkInMaps("lxxgen1B", translatorBridge);
        checkInMaps("lxxgen1C", translatorBridge);
        checkInMaps("lxxgen1D", translatorBridge);
        checkInMaps("lxxgen1E", translatorBridge);
        checkInMaps("lxxgen1F", translatorBridge);
        checkInMaps("lxxgen1G", translatorBridge);
        checkInMaps("lxxgen1H", translatorBridge);
        checkInMaps("lxxgen1I", translatorBridge);
        checkInMaps("lxxgen1J", translatorBridge);
        checkInMaps("lxxgen1K", translatorBridge);
        checkInMaps("lxxgen1L", translatorBridge);
        checkInMaps("lxxgen1M", translatorBridge);
        checkInMaps("lxxgen1N", translatorBridge);
        checkInMaps("lxxgen1O", translatorBridge);
        checkInMaps("lxxgen1P", translatorBridge);
        checkInMaps("lxxgen1Q", translatorBridge);
        checkInMaps("lxxgen1R", translatorBridge);
        checkInMaps("lxxgen1S", translatorBridge);
        checkInMaps("lxxgen1T", translatorBridge);
        checkInMaps("lxxgen1U", translatorBridge);
        checkInMaps("lxxgen1V", translatorBridge);
        checkInMaps("lxxgen1W", translatorBridge);
        checkInMaps("lxxgen1X", translatorBridge);
        checkInMaps("lxxgen1Y", translatorBridge);
        checkInMaps("lxxgen1Z", translatorBridge);
        checkInMaps("lxxgen1AA", translatorBridge);
        checkInMaps("lxxgen1BB", translatorBridge);
        checkInMaps("lxxgen1CC", translatorBridge);
        checkInMaps("lxxgen1DD", translatorBridge);
        checkInMaps("lxxgen1EE", translatorBridge);
    }

    @Test
    public void test_clement() {
        checkInMaps("clement1A", translatorBridge);
        checkInMaps("clement1B", translatorBridge);
        checkInMaps("clement1C", translatorBridge);
        checkInMaps("clement1D", translatorBridge);
        checkInMaps("clement1E", translatorBridge);
        checkInMaps("clement1F", translatorBridge);
        checkInMaps("clement1G", translatorBridge);
        checkInMaps("clement1H", translatorBridge);
        checkInMaps("clement1I", translatorBridge);
        checkInMaps("clement1J", translatorBridge);
        checkInMaps("clement1K", translatorBridge);
        checkInMaps("clement1L", translatorBridge);
        checkInMaps("clement1M", translatorBridge);
        checkInMaps("clement1N", translatorBridge);
        checkInMaps("clement1O", translatorBridge);
    }

    @Test
    public void test_failed_ones() {
        assertTrue(true);
        checkInMaps("assimil9D", translatorBridge);
    }
}

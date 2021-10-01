import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.DummyAccentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.english.EnglishCaseFactory;
import patrologia.translator.conjugation.english.EnglishConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.english.EnglishDeclension;
import patrologia.translator.declension.english.EnglishDeclensionFactory;
import patrologia.translator.linguisticimplementations.EnglishAnalyzer;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.english.EnglishRuleFactory;
import patrologia.translator.utils.Analyzer;

import java.util.*;

import static junit.framework.Assert.assertTrue;

public class EnglishLightfootTest  extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    private String localTestPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\english\\";
    private String localCommonPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\";

    @Before
    public void init() {
        String prepositionFileDescription = localResourcesPath + "prepositions.txt";
        String nounFileDescription = localResourcesPath + "nouns.txt";
        String verbFileDescription = localResourcesPath + "verbs.txt";
        String englishFrenchDataFile = localResourcesPath + "harraps_shorter_to_french.txt";
        String frenchVerbsDataFile = localCommonPath + "french_verbs.txt";
        String declensionPath = localResourcesPath + "declensions";
        String declensionsAndFiles = localResourcesPath + "declensionsAndFiles.txt";
        String conjugationPath = localResourcesPath + "conjugations";
        String conjugationsAndFiles = localResourcesPath + "conjugationsAndFiles.txt";
        String englishPathFile = localTestPath + "lightfoot_content.txt";
        String englishResultFile = localTestPath + "lightfoot_expected_result.txt";
        EnglishDeclensionFactory englishDeclensionFactory = new EnglishDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        NounRepository nounRepository = new NounRepository(Language.ENGLISH, englishDeclensionFactory, new DummyAccentuer(), getNouns(nounFileDescription));
        VerbRepository2 verbRepository = new VerbRepository2(new EnglishConjugationFactory(getEnglishConjugations(conjugationsAndFiles), getEnglishConjugationDefinitions(conjugationsAndFiles, conjugationPath), englishDeclensionFactory), Language.ENGLISH, new DummyAccentuer(), getVerbs(verbFileDescription));
        EnglishRuleFactory ruleFactory = new EnglishRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.ENGLISH, new EnglishCaseFactory(), ruleFactory, getPrepositions(prepositionFileDescription));
        Analyzer englishAnalyzer = new EnglishAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getDictionaryData(englishFrenchDataFile), getFileContentForRepository(frenchVerbsDataFile), verbRepository, nounRepository, declensionPath, declensionsAndFiles, englishDeclensionFactory);
        translatorBridge = new TranslatorBridge(englishAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(englishPathFile);
        mapValuesForResult = loadMapFromFiles(englishResultFile);
    }

    private List<String> getNouns(String nounFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "abel@masc%invmasc"
        });
        */
        return getFileContentForRepository(nounFileDescription);
    }

    private List<String> getPrepositions(String prepositionFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "above@prep()"
        });
        */
        return getFileContentForRepository(prepositionFileDescription);
    }


    private List<String> getDictionaryData(String englishFrenchDataFile) {
        /*
        return Arrays.asList(new String[]{
                "sit@verb!norm%1(verb)=s'asseoir%2(verb)=sieger"
        });
        */
        return getFileContentForRepository(englishFrenchDataFile);

    }

    private List<Declension> getDeclensionList(String file, String directory) {
        List<String> declensionNameList = getFileContentForRepository(file);
        List<Declension> declensionList = new ArrayList<>();
        for (String declensionName : declensionNameList) {
            String parts[] = declensionName.split("%");
            String fileName = parts[1];
            declensionList.add(new EnglishDeclension(fileName, getDeclensionElements(fileName, directory)));
        }
        return declensionList;
    }

    private List<String> getEnglishConjugations(String conjugationsAndFiles) {
        /*
        return Arrays.asList(new String[]{
                "see%see.txt"
        });
        */
        return getFileContentForRepository(conjugationsAndFiles);
    }

    private Map<String, List<String>> getEnglishConjugationDefinitions(String file, String directory) {
        /*
        Map<String, List<String>> englishConjugationDefinitionsMap = new HashMap<>();
        englishConjugationDefinitionsMap.put("live", getLiveDefinition());
        return englishConjugationDefinitionsMap;
        */

        List<String> conjugationNameList = getFileContentForRepository(file);
        Map<String, List<String>> englishConjugationDefinitionsMap = new HashMap<>();
        for (String conjugationName : conjugationNameList) {
            String parts[] = conjugationName.split("%");
            String fileName = parts[1];
            String nameOnly = parts[0];
            englishConjugationDefinitionsMap.put(nameOnly, getConjugationElements(directory, fileName));
        }
        return englishConjugationDefinitionsMap;
    }

    private List<String> getVerbs(String verbFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "signif,y,[imply]"
        });
         */
        return getFileContentForRepository(verbFileDescription);
    }

    @Test
    public void lightfoot_page1() {
        checkInMaps("lightfoot_page1A", translatorBridge);
        checkInMaps("lightfoot_page1B", translatorBridge);
        checkInMaps("lightfoot_page1C", translatorBridge);
        checkInMaps("lightfoot_page1D", translatorBridge);
        checkInMaps("lightfoot_page1E", translatorBridge);
    }

    @Test
    public void lightfoot_page2() {
        checkInMaps("lightfoot_page2A", translatorBridge);
        checkInMaps("lightfoot_page2B", translatorBridge);
        checkInMaps("lightfoot_page2C", translatorBridge);
        checkInMaps("lightfoot_page2D", translatorBridge);
        checkInMaps("lightfoot_page2E", translatorBridge);
        checkInMaps("lightfoot_page2F", translatorBridge);
        checkInMaps("lightfoot_page2G", translatorBridge);
        checkInMaps("lightfoot_page2H", translatorBridge);
        checkInMaps("lightfoot_page2I", translatorBridge);
        checkInMaps("lightfoot_page2J", translatorBridge);
    }

    @Test
    public void lightfoot_page3() {
        checkInMaps("lightfoot_page3A", translatorBridge);
        checkInMaps("lightfoot_page3B", translatorBridge);
        checkInMaps("lightfoot_page3C", translatorBridge);
        checkInMaps("lightfoot_page3D", translatorBridge);
        checkInMaps("lightfoot_page3E", translatorBridge);
        checkInMaps("lightfoot_page3F", translatorBridge);
        checkInMaps("lightfoot_page3G", translatorBridge);
        checkInMaps("lightfoot_page3H", translatorBridge);
        checkInMaps("lightfoot_page3I", translatorBridge);
        checkInMaps("lightfoot_page3J", translatorBridge);
        checkInMaps("lightfoot_page3K", translatorBridge);
        checkInMaps("lightfoot_page3L", translatorBridge);
        checkInMaps("lightfoot_page3M", translatorBridge);
        checkInMaps("lightfoot_page3N", translatorBridge);
        checkInMaps("lightfoot_page3O", translatorBridge);
        checkInMaps("lightfoot_page3P", translatorBridge);
        checkInMaps("lightfoot_page3Q", translatorBridge);
        checkInMaps("lightfoot_page3R", translatorBridge);
    }

    @Test
    public void lightfoot_page4() {
        checkInMaps("lightfoot_page4A", translatorBridge);
        checkInMaps("lightfoot_page4B", translatorBridge);
        checkInMaps("lightfoot_page4C", translatorBridge);
        checkInMaps("lightfoot_page4D", translatorBridge);
        checkInMaps("lightfoot_page4E", translatorBridge);
        checkInMaps("lightfoot_page4F", translatorBridge);
        checkInMaps("lightfoot_page4G", translatorBridge);
        checkInMaps("lightfoot_page4H", translatorBridge);
        checkInMaps("lightfoot_page4I", translatorBridge);
        checkInMaps("lightfoot_page4J", translatorBridge);
        checkInMaps("lightfoot_page4K", translatorBridge);
        checkInMaps("lightfoot_page4L", translatorBridge);
        checkInMaps("lightfoot_page4M", translatorBridge);
        checkInMaps("lightfoot_page4N", translatorBridge);
        checkInMaps("lightfoot_page4O", translatorBridge);
    }

    @Test
    public void lightfoot_page5() {
        checkInMaps("lightfoot_page5A", translatorBridge);
        checkInMaps("lightfoot_page5B", translatorBridge);
        checkInMaps("lightfoot_page5C", translatorBridge);
        checkInMaps("lightfoot_page5D", translatorBridge);
        checkInMaps("lightfoot_page5E", translatorBridge);
        checkInMaps("lightfoot_page5F", translatorBridge);
        checkInMaps("lightfoot_page5G", translatorBridge);
        checkInMaps("lightfoot_page5H", translatorBridge);
        checkInMaps("lightfoot_page5I", translatorBridge);
        checkInMaps("lightfoot_page5J", translatorBridge);
        //checkInMaps("lightfoot_page5K", translatorBridge);
        checkInMaps("lightfoot_page5L", translatorBridge);
        checkInMaps("lightfoot_page5M", translatorBridge);
        checkInMaps("lightfoot_page5N", translatorBridge);
        checkInMaps("lightfoot_page5O", translatorBridge);
        checkInMaps("lightfoot_page5P", translatorBridge);
        checkInMaps("lightfoot_page5Q", translatorBridge);
    }


    @Test
    public void test_lightfoot_chapter_1() {
        checkInMaps("lightfoot_ch1A", translatorBridge);
        checkInMaps("lightfoot_ch1B", translatorBridge);
        checkInMaps("lightfoot_ch1C", translatorBridge);
        checkInMaps("lightfoot_ch1D", translatorBridge);
        checkInMaps("lightfoot_ch1E", translatorBridge);
        checkInMaps("lightfoot_ch1F", translatorBridge);
        checkInMaps("lightfoot_ch1G", translatorBridge);
        checkInMaps("lightfoot_ch1H", translatorBridge);
        checkInMaps("lightfoot_ch1I", translatorBridge);
        checkInMaps("lightfoot_ch1J", translatorBridge);
        checkInMaps("lightfoot_ch1K", translatorBridge);
        checkInMaps("lightfoot_ch1L", translatorBridge);
        checkInMaps("lightfoot_ch1M", translatorBridge);
        checkInMaps("lightfoot_ch1N", translatorBridge);
        checkInMaps("lightfoot_ch1O", translatorBridge);
        checkInMaps("lightfoot_ch1P", translatorBridge);
        checkInMaps("lightfoot_ch1Q", translatorBridge);
        checkInMaps("lightfoot_ch1R", translatorBridge);
        checkInMaps("lightfoot_ch1S", translatorBridge);
        checkInMaps("lightfoot_ch1T", translatorBridge);
        checkInMaps("lightfoot_ch1U", translatorBridge);
        checkInMaps("lightfoot_ch1V", translatorBridge);
        checkInMaps("lightfoot_ch1W", translatorBridge);
        checkInMaps("lightfoot_ch1X", translatorBridge);
        checkInMaps("lightfoot_ch1Y", translatorBridge);
        checkInMaps("lightfoot_ch1Z", translatorBridge);
        //checkInMaps("lightfoot_ch1AA", translatorBridge);
        checkInMaps("lightfoot_ch1BB", translatorBridge);
        checkInMaps("lightfoot_ch1CC", translatorBridge);
        checkInMaps("lightfoot_ch1DD", translatorBridge);
        checkInMaps("lightfoot_ch1EE", translatorBridge);
        checkInMaps("lightfoot_ch1FF", translatorBridge);
        checkInMaps("lightfoot_ch1GG", translatorBridge);
        //checkInMaps("lightfoot_ch1HH", translatorBridge);
        checkInMaps("lightfoot_ch1II", translatorBridge);
        checkInMaps("lightfoot_ch1JJ", translatorBridge);
        checkInMaps("lightfoot_ch1KK", translatorBridge);
        checkInMaps("lightfoot_ch1LL", translatorBridge);
    }

    @Test
    public void test_lightfoot_chapter_2() {
        checkInMaps("lightfoot_ch2A", translatorBridge);
        checkInMaps("lightfoot_ch2B1", translatorBridge);
        checkInMaps("lightfoot_ch2B2", translatorBridge);
        checkInMaps("lightfoot_ch2B3", translatorBridge);
        checkInMaps("lightfoot_ch2B4", translatorBridge);
        checkInMaps("lightfoot_ch2B5", translatorBridge);
        checkInMaps("lightfoot_ch2B6", translatorBridge);
        checkInMaps("lightfoot_ch2C", translatorBridge);
        checkInMaps("lightfoot_ch2D", translatorBridge);
        checkInMaps("lightfoot_ch2E", translatorBridge);
        checkInMaps("lightfoot_ch2F", translatorBridge);
        checkInMaps("lightfoot_ch2G", translatorBridge);
        checkInMaps("lightfoot_ch2H", translatorBridge);
        checkInMaps("lightfoot_ch2I", translatorBridge);
        checkInMaps("lightfoot_ch2J", translatorBridge);
        checkInMaps("lightfoot_ch2K", translatorBridge);
        checkInMaps("lightfoot_ch2L", translatorBridge);
        checkInMaps("lightfoot_ch2M", translatorBridge);
        checkInMaps("lightfoot_ch2N", translatorBridge);
    }

    @Test
    public void test_lightfoot_chapter_3() {
        checkInMaps("lightfoot_ch3A", translatorBridge);
        checkInMaps("lightfoot_ch3B", translatorBridge);
        checkInMaps("lightfoot_ch3C", translatorBridge);
        checkInMaps("lightfoot_ch3D", translatorBridge);
        checkInMaps("lightfoot_ch3E", translatorBridge);
        checkInMaps("lightfoot_ch3F", translatorBridge);
        checkInMaps("lightfoot_ch3G", translatorBridge);
        checkInMaps("lightfoot_ch3H", translatorBridge);
        checkInMaps("lightfoot_ch3I", translatorBridge);
        checkInMaps("lightfoot_ch3J", translatorBridge);
        checkInMaps("lightfoot_ch3K", translatorBridge);
        checkInMaps("lightfoot_ch3L", translatorBridge);
        checkInMaps("lightfoot_ch3M", translatorBridge);
        checkInMaps("lightfoot_ch3N", translatorBridge);
        checkInMaps("lightfoot_ch3O", translatorBridge);
        checkInMaps("lightfoot_ch3P", translatorBridge);
        checkInMaps("lightfoot_ch3Q", translatorBridge);
        checkInMaps("lightfoot_ch3R", translatorBridge);
        checkInMaps("lightfoot_ch3S", translatorBridge);
        checkInMaps("lightfoot_ch3T", translatorBridge);
        checkInMaps("lightfoot_ch3U", translatorBridge);
        checkInMaps("lightfoot_ch3V", translatorBridge);
        checkInMaps("lightfoot_ch3W", translatorBridge);
        checkInMaps("lightfoot_ch3X", translatorBridge);
        checkInMaps("lightfoot_ch3Y", translatorBridge);
        checkInMaps("lightfoot_ch3Z", translatorBridge);
        checkInMaps("lightfoot_ch3AA", translatorBridge);
        checkInMaps("lightfoot_ch3BB", translatorBridge);
        checkInMaps("lightfoot_ch3CC", translatorBridge);
        checkInMaps("lightfoot_ch3DD", translatorBridge);
        checkInMaps("lightfoot_ch3EE", translatorBridge);
        checkInMaps("lightfoot_ch3FF", translatorBridge);
        checkInMaps("lightfoot_ch3GG", translatorBridge);
        checkInMaps("lightfoot_ch3HH", translatorBridge);
        checkInMaps("lightfoot_ch3II", translatorBridge);
    }

    @Test
    public void test_lightfoot_chap4() {
        checkInMaps("lightfoot_ch4A", translatorBridge);
        checkInMaps("lightfoot_ch4B", translatorBridge);
        checkInMaps("lightfoot_ch4C", translatorBridge);
        checkInMaps("lightfoot_ch4D", translatorBridge);
        checkInMaps("lightfoot_ch4E", translatorBridge);
        checkInMaps("lightfoot_ch4F", translatorBridge);
        checkInMaps("lightfoot_ch4G", translatorBridge);
        checkInMaps("lightfoot_ch4H", translatorBridge);
        checkInMaps("lightfoot_ch4I", translatorBridge);
        checkInMaps("lightfoot_ch4J", translatorBridge);
        checkInMaps("lightfoot_ch4K", translatorBridge);
        checkInMaps("lightfoot_ch4L", translatorBridge);
        checkInMaps("lightfoot_ch4M", translatorBridge);
        checkInMaps("lightfoot_ch4N", translatorBridge);
        checkInMaps("lightfoot_ch4O", translatorBridge);
        checkInMaps("lightfoot_ch4P", translatorBridge);
        checkInMaps("lightfoot_ch4Q", translatorBridge);
        checkInMaps("lightfoot_ch4R", translatorBridge);
        checkInMaps("lightfoot_ch4S", translatorBridge);
        checkInMaps("lightfoot_ch4T", translatorBridge);
        checkInMaps("lightfoot_ch4U", translatorBridge);
        checkInMaps("lightfoot_ch4V", translatorBridge);
        checkInMaps("lightfoot_ch4W", translatorBridge);
        checkInMaps("lightfoot_ch4X", translatorBridge);
        checkInMaps("lightfoot_ch4Y", translatorBridge);
        checkInMaps("lightfoot_ch4Z", translatorBridge);
        checkInMaps("lightfoot_ch4AA", translatorBridge);
        checkInMaps("lightfoot_ch4BB", translatorBridge);
        checkInMaps("lightfoot_ch4CC", translatorBridge);
        checkInMaps("lightfoot_ch4DD", translatorBridge);
        checkInMaps("lightfoot_ch4EE", translatorBridge);
        checkInMaps("lightfoot_ch4FF", translatorBridge);
        checkInMaps("lightfoot_ch4GG", translatorBridge);
        checkInMaps("lightfoot_ch4HH", translatorBridge);
        checkInMaps("lightfoot_ch4II", translatorBridge);
        checkInMaps("lightfoot_ch4JJ", translatorBridge);
        checkInMaps("lightfoot_ch4KK", translatorBridge);
        checkInMaps("lightfoot_ch4LL", translatorBridge);
        checkInMaps("lightfoot_ch4MM", translatorBridge);
        checkInMaps("lightfoot_ch4NN", translatorBridge);
        checkInMaps("lightfoot_ch4OO", translatorBridge);
        checkInMaps("lightfoot_ch4PP", translatorBridge);
        checkInMaps("lightfoot_ch4QQ", translatorBridge);
        checkInMaps("lightfoot_ch4RR", translatorBridge);
        checkInMaps("lightfoot_ch4SS", translatorBridge);
        checkInMaps("lightfoot_ch4TT", translatorBridge);
    }


    @Test
    public void test_lightfoot_chap5() {
        checkInMaps("lightfoot_ch5A01", translatorBridge);
        checkInMaps("lightfoot_ch5A02", translatorBridge);
        checkInMaps("lightfoot_ch5A03", translatorBridge);
        checkInMaps("lightfoot_ch5A04", translatorBridge);
        checkInMaps("lightfoot_ch5A05", translatorBridge);
        checkInMaps("lightfoot_ch5A06", translatorBridge);
        //checkInMaps("lightfoot_ch5A07", translatorBridge);
        checkInMaps("lightfoot_ch5A08", translatorBridge);
        checkInMaps("lightfoot_ch5A09", translatorBridge);
        checkInMaps("lightfoot_ch5A10", translatorBridge);
        checkInMaps("lightfoot_ch5A11", translatorBridge);
        checkInMaps("lightfoot_ch5A12", translatorBridge);
        checkInMaps("lightfoot_ch5A13", translatorBridge);
        checkInMaps("lightfoot_ch5A14", translatorBridge);
        checkInMaps("lightfoot_ch5A15", translatorBridge);
        checkInMaps("lightfoot_ch5A16", translatorBridge);
        checkInMaps("lightfoot_ch5A17", translatorBridge);
        checkInMaps("lightfoot_ch5A18", translatorBridge);
        checkInMaps("lightfoot_ch5A19", translatorBridge);
        checkInMaps("lightfoot_ch5A20", translatorBridge);
        checkInMaps("lightfoot_ch5A21", translatorBridge);
        checkInMaps("lightfoot_ch5A22", translatorBridge);
        checkInMaps("lightfoot_ch5A23", translatorBridge);
        checkInMaps("lightfoot_ch5A24", translatorBridge);
        checkInMaps("lightfoot_ch5A25", translatorBridge);
        checkInMaps("lightfoot_ch5A26", translatorBridge);
        checkInMaps("lightfoot_ch5A27", translatorBridge);
        checkInMaps("lightfoot_ch5A28", translatorBridge);
        checkInMaps("lightfoot_ch5A29", translatorBridge);
        checkInMaps("lightfoot_ch5A30", translatorBridge);
        checkInMaps("lightfoot_ch5A31", translatorBridge);
        checkInMaps("lightfoot_ch5A32", translatorBridge);
        checkInMaps("lightfoot_ch5A33", translatorBridge);
        checkInMaps("lightfoot_ch5A34", translatorBridge);
        checkInMaps("lightfoot_ch5A35", translatorBridge);
        checkInMaps("lightfoot_ch5A36", translatorBridge);
        checkInMaps("lightfoot_ch5A37", translatorBridge);
        checkInMaps("lightfoot_ch5A38", translatorBridge);
        checkInMaps("lightfoot_ch5A39", translatorBridge);
        checkInMaps("lightfoot_ch5A40", translatorBridge);
        checkInMaps("lightfoot_ch5A41", translatorBridge);
    }

    @Test
    public void test_lightfoot_chap6() {
        checkInMaps("lightfoot_ch601", translatorBridge);
        checkInMaps("lightfoot_ch602", translatorBridge);
        checkInMaps("lightfoot_ch603", translatorBridge);
        checkInMaps("lightfoot_ch604", translatorBridge);
        checkInMaps("lightfoot_ch605", translatorBridge);
        checkInMaps("lightfoot_ch606", translatorBridge);
        checkInMaps("lightfoot_ch607", translatorBridge);
        checkInMaps("lightfoot_ch608", translatorBridge);
        checkInMaps("lightfoot_ch609", translatorBridge);
        checkInMaps("lightfoot_ch610", translatorBridge);
        checkInMaps("lightfoot_ch611", translatorBridge);
        checkInMaps("lightfoot_ch612", translatorBridge);
        checkInMaps("lightfoot_ch613", translatorBridge);
        checkInMaps("lightfoot_ch614", translatorBridge);
        checkInMaps("lightfoot_ch615", translatorBridge);
        checkInMaps("lightfoot_ch616", translatorBridge);
        checkInMaps("lightfoot_ch617", translatorBridge);
        checkInMaps("lightfoot_ch618", translatorBridge);
        checkInMaps("lightfoot_ch619", translatorBridge);
        checkInMaps("lightfoot_ch620", translatorBridge);
        checkInMaps("lightfoot_ch621", translatorBridge);
        checkInMaps("lightfoot_ch622", translatorBridge);
        checkInMaps("lightfoot_ch623", translatorBridge);
        checkInMaps("lightfoot_ch624", translatorBridge);
        checkInMaps("lightfoot_ch625", translatorBridge);
        checkInMaps("lightfoot_ch626", translatorBridge);
        checkInMaps("lightfoot_ch627", translatorBridge);
        checkInMaps("lightfoot_ch628", translatorBridge);
        checkInMaps("lightfoot_ch629", translatorBridge);
        checkInMaps("lightfoot_ch630", translatorBridge);
        checkInMaps("lightfoot_ch631", translatorBridge);
        checkInMaps("lightfoot_ch632", translatorBridge);
        checkInMaps("lightfoot_ch633", translatorBridge);
        checkInMaps("lightfoot_ch634", translatorBridge);
        checkInMaps("lightfoot_ch635", translatorBridge);
        checkInMaps("lightfoot_ch636", translatorBridge);
        checkInMaps("lightfoot_ch637", translatorBridge);
        checkInMaps("lightfoot_ch638", translatorBridge);
        checkInMaps("lightfoot_ch639", translatorBridge);
        checkInMaps("lightfoot_ch640", translatorBridge);
        checkInMaps("lightfoot_ch641", translatorBridge);
        checkInMaps("lightfoot_ch642", translatorBridge);
        checkInMaps("lightfoot_ch643", translatorBridge);
        checkInMaps("lightfoot_ch644", translatorBridge);
        checkInMaps("lightfoot_ch645", translatorBridge);
        checkInMaps("lightfoot_ch646", translatorBridge);
        checkInMaps("lightfoot_ch647", translatorBridge);
        checkInMaps("lightfoot_ch648", translatorBridge);
        checkInMaps("lightfoot_ch649", translatorBridge);
        checkInMaps("lightfoot_ch650", translatorBridge);
        checkInMaps("lightfoot_ch651", translatorBridge);
        checkInMaps("lightfoot_ch652", translatorBridge);
    }

    @Test
    public void test_lightfoot_chap7() {
        checkInMaps("lightfoot_ch701", translatorBridge);
        checkInMaps("lightfoot_ch702", translatorBridge);
        checkInMaps("lightfoot_ch703", translatorBridge);
        checkInMaps("lightfoot_ch704", translatorBridge);
        checkInMaps("lightfoot_ch705", translatorBridge);
        checkInMaps("lightfoot_ch706", translatorBridge);
        checkInMaps("lightfoot_ch707", translatorBridge);
        checkInMaps("lightfoot_ch708", translatorBridge);
        checkInMaps("lightfoot_ch709", translatorBridge);
        checkInMaps("lightfoot_ch710", translatorBridge);
        checkInMaps("lightfoot_ch711", translatorBridge);
        checkInMaps("lightfoot_ch712", translatorBridge);
        checkInMaps("lightfoot_ch713", translatorBridge);
        checkInMaps("lightfoot_ch714", translatorBridge);
        checkInMaps("lightfoot_ch715", translatorBridge);
        checkInMaps("lightfoot_ch716", translatorBridge);
        checkInMaps("lightfoot_ch717", translatorBridge);
        checkInMaps("lightfoot_ch718", translatorBridge);
        checkInMaps("lightfoot_ch719", translatorBridge);
        checkInMaps("lightfoot_ch720", translatorBridge);
        checkInMaps("lightfoot_ch721", translatorBridge);
        checkInMaps("lightfoot_ch722", translatorBridge);
        checkInMaps("lightfoot_ch723", translatorBridge);
        checkInMaps("lightfoot_ch724", translatorBridge);
        checkInMaps("lightfoot_ch725", translatorBridge);
        checkInMaps("lightfoot_ch726", translatorBridge);
        checkInMaps("lightfoot_ch727", translatorBridge);
        checkInMaps("lightfoot_ch728", translatorBridge);
        checkInMaps("lightfoot_ch729", translatorBridge);
        checkInMaps("lightfoot_ch730", translatorBridge);
        checkInMaps("lightfoot_ch731", translatorBridge);
        checkInMaps("lightfoot_ch732", translatorBridge);
        checkInMaps("lightfoot_ch733", translatorBridge);
        checkInMaps("lightfoot_ch734", translatorBridge);
        checkInMaps("lightfoot_ch735", translatorBridge);
        checkInMaps("lightfoot_ch736", translatorBridge);
        checkInMaps("lightfoot_ch737", translatorBridge);
        checkInMaps("lightfoot_ch738", translatorBridge);
        checkInMaps("lightfoot_ch739", translatorBridge);
        checkInMaps("lightfoot_ch740", translatorBridge);
        checkInMaps("lightfoot_ch741", translatorBridge);
        checkInMaps("lightfoot_ch742", translatorBridge);
        checkInMaps("lightfoot_ch743", translatorBridge);
        checkInMaps("lightfoot_ch744", translatorBridge);
        checkInMaps("lightfoot_ch745", translatorBridge);
        checkInMaps("lightfoot_ch746", translatorBridge);
        checkInMaps("lightfoot_ch747", translatorBridge);
        checkInMaps("lightfoot_ch748", translatorBridge);
        checkInMaps("lightfoot_ch749", translatorBridge);
        checkInMaps("lightfoot_ch750", translatorBridge);
        checkInMaps("lightfoot_ch751", translatorBridge);
        checkInMaps("lightfoot_ch752", translatorBridge);
        checkInMaps("lightfoot_ch753", translatorBridge);
    }

    @Test
    public void test_lightfoot_chap8() {
        checkInMaps("lightfoot_ch801", translatorBridge);
        checkInMaps("lightfoot_ch802", translatorBridge);
        checkInMaps("lightfoot_ch803", translatorBridge);
        checkInMaps("lightfoot_ch804", translatorBridge);
        checkInMaps("lightfoot_ch805", translatorBridge);
        checkInMaps("lightfoot_ch806", translatorBridge);
        checkInMaps("lightfoot_ch807", translatorBridge);
        checkInMaps("lightfoot_ch808", translatorBridge);
        checkInMaps("lightfoot_ch809", translatorBridge);
        checkInMaps("lightfoot_ch810", translatorBridge);
        checkInMaps("lightfoot_ch811", translatorBridge);
        checkInMaps("lightfoot_ch812", translatorBridge);
        checkInMaps("lightfoot_ch813", translatorBridge);
        checkInMaps("lightfoot_ch814", translatorBridge);
        checkInMaps("lightfoot_ch815", translatorBridge);
        checkInMaps("lightfoot_ch816", translatorBridge);
        checkInMaps("lightfoot_ch817", translatorBridge);
        checkInMaps("lightfoot_ch818", translatorBridge);
    }

    @Test
    public void test_lightfoot_chap9() {
        checkInMaps("lightfoot_ch900", translatorBridge);
        checkInMaps("lightfoot_ch901", translatorBridge);
        checkInMaps("lightfoot_ch902", translatorBridge);
        checkInMaps("lightfoot_ch903", translatorBridge);
        checkInMaps("lightfoot_ch904", translatorBridge);
        checkInMaps("lightfoot_ch905", translatorBridge);
        checkInMaps("lightfoot_ch906", translatorBridge);
        checkInMaps("lightfoot_ch907", translatorBridge);
        checkInMaps("lightfoot_ch908", translatorBridge);
        checkInMaps("lightfoot_ch909", translatorBridge);
        checkInMaps("lightfoot_ch910", translatorBridge);
        checkInMaps("lightfoot_ch911", translatorBridge);
        checkInMaps("lightfoot_ch912", translatorBridge);
        checkInMaps("lightfoot_ch913", translatorBridge);
        checkInMaps("lightfoot_ch914", translatorBridge);
        checkInMaps("lightfoot_ch915", translatorBridge);
        checkInMaps("lightfoot_ch916", translatorBridge);
        checkInMaps("lightfoot_ch917", translatorBridge);
        checkInMaps("lightfoot_ch918", translatorBridge);
        checkInMaps("lightfoot_ch919", translatorBridge);
        checkInMaps("lightfoot_ch920", translatorBridge);
        checkInMaps("lightfoot_ch921", translatorBridge);
    }

    @Test
    public void test_lightfoot_chap10() {
        checkInMaps("lightfoot_ch1001", translatorBridge);
        checkInMaps("lightfoot_ch1002", translatorBridge);
        checkInMaps("lightfoot_ch1003", translatorBridge);
        checkInMaps("lightfoot_ch1004", translatorBridge);
        checkInMaps("lightfoot_ch1005", translatorBridge);
        checkInMaps("lightfoot_ch1006", translatorBridge);
        checkInMaps("lightfoot_ch1007", translatorBridge);
        checkInMaps("lightfoot_ch1008", translatorBridge);
        checkInMaps("lightfoot_ch1009", translatorBridge);
        checkInMaps("lightfoot_ch1010", translatorBridge);
        checkInMaps("lightfoot_ch1011", translatorBridge);
        checkInMaps("lightfoot_ch1012", translatorBridge);
        checkInMaps("lightfoot_ch1013", translatorBridge);
        checkInMaps("lightfoot_ch1014", translatorBridge);
        checkInMaps("lightfoot_ch1015", translatorBridge);
        checkInMaps("lightfoot_ch1016", translatorBridge);
        checkInMaps("lightfoot_ch1017", translatorBridge);
        checkInMaps("lightfoot_ch1018", translatorBridge);
        checkInMaps("lightfoot_ch1019", translatorBridge);
        checkInMaps("lightfoot_ch1020", translatorBridge);
        checkInMaps("lightfoot_ch1021", translatorBridge);
        checkInMaps("lightfoot_ch1022", translatorBridge);
        checkInMaps("lightfoot_ch1023", translatorBridge);
        checkInMaps("lightfoot_ch1024", translatorBridge);
        checkInMaps("lightfoot_ch1025", translatorBridge);
        checkInMaps("lightfoot_ch1026", translatorBridge);
        checkInMaps("lightfoot_ch1027", translatorBridge);
        checkInMaps("lightfoot_ch1028", translatorBridge);
        checkInMaps("lightfoot_ch1029", translatorBridge);
        checkInMaps("lightfoot_ch1030", translatorBridge);
        checkInMaps("lightfoot_ch1031", translatorBridge);
        checkInMaps("lightfoot_ch1032", translatorBridge);
        checkInMaps("lightfoot_ch1033", translatorBridge);
        checkInMaps("lightfoot_ch1034", translatorBridge);
        checkInMaps("lightfoot_ch1035", translatorBridge);
        checkInMaps("lightfoot_ch1036", translatorBridge);
        checkInMaps("lightfoot_ch1037", translatorBridge);
        checkInMaps("lightfoot_ch1038", translatorBridge);
        checkInMaps("lightfoot_ch1039", translatorBridge);
        checkInMaps("lightfoot_ch1040", translatorBridge);
        checkInMaps("lightfoot_ch1041", translatorBridge);
        checkInMaps("lightfoot_ch1042", translatorBridge);
        checkInMaps("lightfoot_ch1043", translatorBridge);
        checkInMaps("lightfoot_ch1044", translatorBridge);
        checkInMaps("lightfoot_ch1045", translatorBridge);
        checkInMaps("lightfoot_ch1046", translatorBridge);
        checkInMaps("lightfoot_ch1047", translatorBridge);
        checkInMaps("lightfoot_ch1048", translatorBridge);
        checkInMaps("lightfoot_ch1049", translatorBridge);
        checkInMaps("lightfoot_ch1050", translatorBridge);
        checkInMaps("lightfoot_ch1051", translatorBridge);
        checkInMaps("lightfoot_ch1052", translatorBridge);
        checkInMaps("lightfoot_ch1053", translatorBridge);
        checkInMaps("lightfoot_ch1054", translatorBridge);
        checkInMaps("lightfoot_ch1055", translatorBridge);
        checkInMaps("lightfoot_ch1056", translatorBridge);
        checkInMaps("lightfoot_ch1057", translatorBridge);
        checkInMaps("lightfoot_ch1058", translatorBridge);
        checkInMaps("lightfoot_ch1059", translatorBridge);
    }

    @Test
    public void test_lightfoot_chap11() {
        checkInMaps("lightfoot_ch1100", translatorBridge);
        checkInMaps("lightfoot_ch1101", translatorBridge);
        checkInMaps("lightfoot_ch1102", translatorBridge);
        checkInMaps("lightfoot_ch1103", translatorBridge);
        checkInMaps("lightfoot_ch1104", translatorBridge);
        checkInMaps("lightfoot_ch1105", translatorBridge);
        checkInMaps("lightfoot_ch1106", translatorBridge);
        checkInMaps("lightfoot_ch1107", translatorBridge);
        checkInMaps("lightfoot_ch1108", translatorBridge);
        checkInMaps("lightfoot_ch1109", translatorBridge);
        checkInMaps("lightfoot_ch1110", translatorBridge);
        checkInMaps("lightfoot_ch1111", translatorBridge);
        checkInMaps("lightfoot_ch1112", translatorBridge);
        checkInMaps("lightfoot_ch1113", translatorBridge);
        checkInMaps("lightfoot_ch1114", translatorBridge);
        checkInMaps("lightfoot_ch1115", translatorBridge);
        checkInMaps("lightfoot_ch1116", translatorBridge);
        checkInMaps("lightfoot_ch1117", translatorBridge);
        checkInMaps("lightfoot_ch1118", translatorBridge);
        checkInMaps("lightfoot_ch1119", translatorBridge);
        checkInMaps("lightfoot_ch1120", translatorBridge);
        checkInMaps("lightfoot_ch1121", translatorBridge);
        checkInMaps("lightfoot_ch1122", translatorBridge);
        checkInMaps("lightfoot_ch1123", translatorBridge);
        checkInMaps("lightfoot_ch1124", translatorBridge);
        checkInMaps("lightfoot_ch1125", translatorBridge);
        checkInMaps("lightfoot_ch1126", translatorBridge);
        checkInMaps("lightfoot_ch1127", translatorBridge);
        checkInMaps("lightfoot_ch1128", translatorBridge);
        checkInMaps("lightfoot_ch1129", translatorBridge);
        checkInMaps("lightfoot_ch1130", translatorBridge);
        checkInMaps("lightfoot_ch1131", translatorBridge);
        checkInMaps("lightfoot_ch1132", translatorBridge);
        checkInMaps("lightfoot_ch1133", translatorBridge);
        checkInMaps("lightfoot_ch1134", translatorBridge);
        checkInMaps("lightfoot_ch1135", translatorBridge);
        checkInMaps("lightfoot_ch1136", translatorBridge);
        checkInMaps("lightfoot_ch1137", translatorBridge);
        checkInMaps("lightfoot_ch1138", translatorBridge);
        checkInMaps("lightfoot_ch1139", translatorBridge);
        checkInMaps("lightfoot_ch1140", translatorBridge);
        checkInMaps("lightfoot_ch1141", translatorBridge);
        checkInMaps("lightfoot_ch1142", translatorBridge);
        checkInMaps("lightfoot_ch1143", translatorBridge);
        checkInMaps("lightfoot_ch1144", translatorBridge);
        checkInMaps("lightfoot_ch1145", translatorBridge);
        checkInMaps("lightfoot_ch1146", translatorBridge);
        checkInMaps("lightfoot_ch1147", translatorBridge);
        checkInMaps("lightfoot_ch1148", translatorBridge);
    }

    @Test
    public void test_lightfoot_chap12() {
        checkInMaps("lightfoot_ch1200", translatorBridge);
        checkInMaps("lightfoot_ch1201", translatorBridge);
        checkInMaps("lightfoot_ch1202", translatorBridge);
        checkInMaps("lightfoot_ch1203", translatorBridge);
        checkInMaps("lightfoot_ch1204", translatorBridge);
        checkInMaps("lightfoot_ch1205", translatorBridge);
        checkInMaps("lightfoot_ch1206", translatorBridge);
        checkInMaps("lightfoot_ch1207", translatorBridge);
        checkInMaps("lightfoot_ch1208", translatorBridge);
        checkInMaps("lightfoot_ch1209", translatorBridge);
        checkInMaps("lightfoot_ch1210", translatorBridge);
        checkInMaps("lightfoot_ch1211", translatorBridge);
        checkInMaps("lightfoot_ch1212", translatorBridge);
        checkInMaps("lightfoot_ch1213", translatorBridge);
        checkInMaps("lightfoot_ch1214", translatorBridge);
        checkInMaps("lightfoot_ch1215", translatorBridge);
        checkInMaps("lightfoot_ch1216", translatorBridge);
        checkInMaps("lightfoot_ch1217", translatorBridge);
        checkInMaps("lightfoot_ch1218", translatorBridge);
        checkInMaps("lightfoot_ch1219", translatorBridge);
        checkInMaps("lightfoot_ch1220", translatorBridge);
        checkInMaps("lightfoot_ch1221", translatorBridge);
        //checkInMaps("lightfoot_ch1222", translatorBridge);
        checkInMaps("lightfoot_ch1223", translatorBridge);
        checkInMaps("lightfoot_ch1224", translatorBridge);
        checkInMaps("lightfoot_ch1225", translatorBridge);

    }

    @Test
    public void test_lightfoot_chap13() {
        checkInMaps("lightfoot_ch1301", translatorBridge);
        checkInMaps("lightfoot_ch1302", translatorBridge);
        checkInMaps("lightfoot_ch1303", translatorBridge);
        checkInMaps("lightfoot_ch1304", translatorBridge);
        checkInMaps("lightfoot_ch1305", translatorBridge);
        checkInMaps("lightfoot_ch1306", translatorBridge);
        checkInMaps("lightfoot_ch1307", translatorBridge);
        checkInMaps("lightfoot_ch1308", translatorBridge);
        checkInMaps("lightfoot_ch1309", translatorBridge);
        checkInMaps("lightfoot_ch1310", translatorBridge);
        checkInMaps("lightfoot_ch1311", translatorBridge);
        checkInMaps("lightfoot_ch1312", translatorBridge);
        checkInMaps("lightfoot_ch1313", translatorBridge);

    }

    @Test
    public void test_lightfoot_chap14() {
        checkInMaps("lightfoot_ch1401", translatorBridge);
        checkInMaps("lightfoot_ch1402", translatorBridge);
        checkInMaps("lightfoot_ch1403", translatorBridge);
        checkInMaps("lightfoot_ch1404", translatorBridge);
        checkInMaps("lightfoot_ch1405", translatorBridge);
        checkInMaps("lightfoot_ch1406", translatorBridge);
        checkInMaps("lightfoot_ch1407", translatorBridge);
        checkInMaps("lightfoot_ch1408", translatorBridge);
        checkInMaps("lightfoot_ch1409", translatorBridge);
        checkInMaps("lightfoot_ch1410", translatorBridge);
        checkInMaps("lightfoot_ch1411", translatorBridge);
        checkInMaps("lightfoot_ch1412", translatorBridge);
        checkInMaps("lightfoot_ch1413", translatorBridge);
        checkInMaps("lightfoot_ch1414", translatorBridge);
        checkInMaps("lightfoot_ch1415", translatorBridge);
        checkInMaps("lightfoot_ch1416", translatorBridge);
        checkInMaps("lightfoot_ch1417", translatorBridge);
        checkInMaps("lightfoot_ch1418", translatorBridge);
        checkInMaps("lightfoot_ch1419", translatorBridge);
        checkInMaps("lightfoot_ch1420", translatorBridge);
        checkInMaps("lightfoot_ch1421", translatorBridge);
        checkInMaps("lightfoot_ch1422", translatorBridge);
        checkInMaps("lightfoot_ch1423", translatorBridge);
        checkInMaps("lightfoot_ch1424", translatorBridge);
        checkInMaps("lightfoot_ch1425", translatorBridge);
        checkInMaps("lightfoot_ch1426", translatorBridge);
        checkInMaps("lightfoot_ch1427", translatorBridge);
        checkInMaps("lightfoot_ch1428", translatorBridge);
        checkInMaps("lightfoot_ch1429", translatorBridge);
        checkInMaps("lightfoot_ch1430", translatorBridge);
        checkInMaps("lightfoot_ch1431", translatorBridge);
        checkInMaps("lightfoot_ch1432", translatorBridge);
        checkInMaps("lightfoot_ch1433", translatorBridge);
        checkInMaps("lightfoot_ch1434", translatorBridge);
        checkInMaps("lightfoot_ch1435", translatorBridge);
        checkInMaps("lightfoot_ch1436", translatorBridge);
        checkInMaps("lightfoot_ch1437", translatorBridge);
        checkInMaps("lightfoot_ch1438", translatorBridge);
        checkInMaps("lightfoot_ch1439", translatorBridge);
        checkInMaps("lightfoot_ch1440", translatorBridge);
        checkInMaps("lightfoot_ch1441", translatorBridge);
        checkInMaps("lightfoot_ch1442", translatorBridge);
        checkInMaps("lightfoot_ch1443", translatorBridge);
        checkInMaps("lightfoot_ch1444", translatorBridge);
        checkInMaps("lightfoot_ch1445", translatorBridge);
        checkInMaps("lightfoot_ch1446", translatorBridge);
        checkInMaps("lightfoot_ch1447", translatorBridge);
        checkInMaps("lightfoot_ch1448", translatorBridge);
        checkInMaps("lightfoot_ch1449", translatorBridge);
        checkInMaps("lightfoot_ch1450", translatorBridge);
        checkInMaps("lightfoot_ch1451", translatorBridge);
        checkInMaps("lightfoot_ch1452", translatorBridge);
        checkInMaps("lightfoot_ch1453", translatorBridge);
        checkInMaps("lightfoot_ch1454", translatorBridge);
        checkInMaps("lightfoot_ch1455", translatorBridge);
        checkInMaps("lightfoot_ch1456", translatorBridge);
        checkInMaps("lightfoot_ch1457", translatorBridge);
        checkInMaps("lightfoot_ch1458", translatorBridge);
        checkInMaps("lightfoot_ch1459", translatorBridge);
        checkInMaps("lightfoot_ch1460", translatorBridge);
        checkInMaps("lightfoot_ch1461", translatorBridge);
        checkInMaps("lightfoot_ch1462", translatorBridge);
        checkInMaps("lightfoot_ch1463", translatorBridge);
        checkInMaps("lightfoot_ch1464", translatorBridge);
        checkInMaps("lightfoot_ch1465", translatorBridge);
        checkInMaps("lightfoot_ch1466", translatorBridge);
        checkInMaps("lightfoot_ch1467", translatorBridge);
        checkInMaps("lightfoot_ch1468", translatorBridge);
        checkInMaps("lightfoot_ch1469", translatorBridge);
        checkInMaps("lightfoot_ch1470", translatorBridge);
        checkInMaps("lightfoot_ch1471", translatorBridge);
        checkInMaps("lightfoot_ch1472", translatorBridge);
        checkInMaps("lightfoot_ch1473", translatorBridge);
        checkInMaps("lightfoot_ch1474", translatorBridge);
        checkInMaps("lightfoot_ch1475", translatorBridge);
    }


    @Test
    public void test_lightfoot_chap15() {
        checkInMaps("lightfoot_ch1501", translatorBridge);
        checkInMaps("lightfoot_ch1502", translatorBridge);
        checkInMaps("lightfoot_ch1503", translatorBridge);
        checkInMaps("lightfoot_ch1504", translatorBridge);
        checkInMaps("lightfoot_ch1505", translatorBridge);
        checkInMaps("lightfoot_ch1506", translatorBridge);
        checkInMaps("lightfoot_ch1507", translatorBridge);
        checkInMaps("lightfoot_ch1508", translatorBridge);
        checkInMaps("lightfoot_ch1509", translatorBridge);
        checkInMaps("lightfoot_ch1510", translatorBridge);
        checkInMaps("lightfoot_ch1511", translatorBridge);
        checkInMaps("lightfoot_ch1512", translatorBridge);
        checkInMaps("lightfoot_ch1513", translatorBridge);
        checkInMaps("lightfoot_ch1514", translatorBridge);
        checkInMaps("lightfoot_ch1515", translatorBridge);
        checkInMaps("lightfoot_ch1516", translatorBridge);
        checkInMaps("lightfoot_ch1517", translatorBridge);
        checkInMaps("lightfoot_ch1518", translatorBridge);
        checkInMaps("lightfoot_ch1519", translatorBridge);
        checkInMaps("lightfoot_ch1520", translatorBridge);
        checkInMaps("lightfoot_ch1521", translatorBridge);
        checkInMaps("lightfoot_ch1522", translatorBridge);
        checkInMaps("lightfoot_ch1523", translatorBridge);
        checkInMaps("lightfoot_ch1524", translatorBridge);
        checkInMaps("lightfoot_ch1525", translatorBridge);
        checkInMaps("lightfoot_ch1526", translatorBridge);
        checkInMaps("lightfoot_ch1527", translatorBridge);
        checkInMaps("lightfoot_ch1528", translatorBridge);
        checkInMaps("lightfoot_ch1529", translatorBridge);
        checkInMaps("lightfoot_ch1530", translatorBridge);
        checkInMaps("lightfoot_ch1531", translatorBridge);
        checkInMaps("lightfoot_ch1532", translatorBridge);
        checkInMaps("lightfoot_ch1533", translatorBridge);
        checkInMaps("lightfoot_ch1534", translatorBridge);
        checkInMaps("lightfoot_ch1535", translatorBridge);
        checkInMaps("lightfoot_ch1536", translatorBridge);
        checkInMaps("lightfoot_ch1537", translatorBridge);
        checkInMaps("lightfoot_ch1538", translatorBridge);
        checkInMaps("lightfoot_ch1539", translatorBridge);
        checkInMaps("lightfoot_ch1540", translatorBridge);
        checkInMaps("lightfoot_ch1541", translatorBridge);
        checkInMaps("lightfoot_ch1542", translatorBridge);
        checkInMaps("lightfoot_ch1543", translatorBridge);
        checkInMaps("lightfoot_ch1544", translatorBridge);
        checkInMaps("lightfoot_ch1545", translatorBridge);
        checkInMaps("lightfoot_ch1546", translatorBridge);
        checkInMaps("lightfoot_ch1547", translatorBridge);
        checkInMaps("lightfoot_ch1548", translatorBridge);
        checkInMaps("lightfoot_ch1549", translatorBridge);
    }

    @Test
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
    }
}

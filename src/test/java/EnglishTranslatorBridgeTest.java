import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository;
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
import patrologia.translator.utils.Analizer;
import patrologia.translator.basicelements.Language;


import java.util.*;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class EnglishTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    private String localTestPath="C:\\Users\\kloeblel\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath="C:\\Users\\kloeblel\\IdeaProjects\\translator\\src\\main\\resources\\english\\";
    private String localCommonPath="C:\\Users\\kloeblel\\IdeaProjects\\translator\\src\\main\\resources\\";

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
        String englishPathFile = localTestPath + "english_content.txt";
        String englishResultFile = localTestPath + "english_expected_results.txt";
        EnglishDeclensionFactory englishDeclensionFactory = new EnglishDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        NounRepository nounRepository = new NounRepository(Language.ENGLISH, englishDeclensionFactory, new DummyAccentuer(), getFileContentForRepository(nounFileDescription));
        VerbRepository2 verbRepository = new VerbRepository2(new EnglishConjugationFactory(getEnglishConjugations(conjugationsAndFiles), getEnglishConjugationDefinitions(conjugationsAndFiles, conjugationPath), englishDeclensionFactory), Language.ENGLISH, new DummyAccentuer(), getVerbs(verbFileDescription));
        EnglishRuleFactory ruleFactory = new EnglishRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.ENGLISH, new EnglishCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        Analizer englishAnalyzer = new EnglishAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getFileContentForRepository(englishFrenchDataFile), getFileContentForRepository(frenchVerbsDataFile), verbRepository, nounRepository, declensionPath, declensionsAndFiles, englishDeclensionFactory);
        translatorBridge = new TranslatorBridge(englishAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(englishPathFile);
        mapValuesForResult = loadMapFromFiles(englishResultFile);
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
                "live%live.txt"
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
                "be@IRREGULAR%[INFINITIVE]=[to be]%[IPR]=[am,are,is,are,are,are]%[AII]=[was,was,was,were,were,were]%[PAPR]=[being]%[AIF]=[bewill,bewill,bewill,bewill,bewill,bewill]%[ACP]=[bewould,bewould,bewould,bewould,bewould,bewould]%[ASP]=[ambe,arebe,be,ambe,arebe,bebe]%[AIMP]=[letbe,letbe,letbe,letbe,letbe,letbe]%[PAP]=[been]",
                "hang,[live],(AIP*hangd*hung*0)",
                "would,[live],(IPR*woulds*would*0)"
        });
        */

        return getFileContentForRepository(verbFileDescription);
    }

    private List<String> getLiveDefinition() {
        return Arrays.asList(new String[]{
                "IPR=>,,s,,, ,",
                "PAP=>d",
                "AII=>d,d,d,d,d,d",
                "AIMP=>xxx,,xxx,xxx,,xxx",
                "PAPR=>ing",
                "AIP=>d,d,d,d,d,d",
                "ACP=>would,would,swould,would,would,would",
                "AIF=>will,will,will,will,will,will",
                "ASP=>am,are,is,are,are,are,are"
        });

    }

    @Test
    public void test_assimil_chapter_1() {
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
    }

    @Test
    public void test_assimil_chapter_2() {
        checkInMaps("assimil2A", translatorBridge);
        checkInMaps("assimil2B", translatorBridge);
        checkInMaps("assimil2C", translatorBridge);
        checkInMaps("assimil2D", translatorBridge);
        checkInMaps("assimil2E", translatorBridge);
        checkInMaps("assimil2F", translatorBridge);
        checkInMaps("assimil2G", translatorBridge);
        checkInMaps("assimil2H", translatorBridge);
        checkInMaps("assimil2I", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_3() {
        checkInMaps("assimil3A", translatorBridge);
        checkInMaps("assimil3B", translatorBridge);
        checkInMaps("assimil3C", translatorBridge);
        checkInMaps("assimil3D", translatorBridge);
        checkInMaps("assimil3E", translatorBridge);
        checkInMaps("assimil3F", translatorBridge);
        checkInMaps("assimil3G", translatorBridge);
        checkInMaps("assimil3H", translatorBridge);
        checkInMaps("assimil3I", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_4() {
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
    }

    @Test
    public void test_assimil_chapter_5() {
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
    }


    @Test
    public void test_assimil_chapter_6() {
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
    public void test_assimil_chapter_7() {
        checkInMaps("assimil7A", translatorBridge);
        checkInMaps("assimil7B", translatorBridge);
        checkInMaps("assimil7C", translatorBridge);
        checkInMaps("assimil7D", translatorBridge);
        checkInMaps("assimil7E", translatorBridge);
        checkInMaps("assimil7F", translatorBridge);
        checkInMaps("assimil7G", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_8() {
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
    }

    @Test
    public void test_assimil_chapter_9() {
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
    public void test_assimil_chapter_10() {
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
    }

    @Test
    public void test_assimil_chapter_11() {
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
    }


    @Test
    public void test_assimil_chapter_12() {
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
    }

    @Test
    public void test_assimil_chapter_13() {
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
    }

    @Test
    public void test_assimil_chapter_14() {
        checkInMaps("assimil14A", translatorBridge);
        checkInMaps("assimil14B", translatorBridge);
        checkInMaps("assimil14C", translatorBridge);
        checkInMaps("assimil14D", translatorBridge);
        checkInMaps("assimil14E", translatorBridge);
        checkInMaps("assimil14F", translatorBridge);
        checkInMaps("assimil14G", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_15() {
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
    }

    @Test
    public void test_assimil_chapter_16() {
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
    }

    @Test
    public void test_assimil_chapter_17() {
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
    }

    @Test
    public void test_assimil_chapter_18() {
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
    }

    @Test
    public void test_assimil_chapter_19() {
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
    }

    @Test
    public void test_assimil_chapter_20() {
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
    }

    @Test
    public void test_assimil_chapter_21() {
        checkInMaps("assimil21A", translatorBridge);
        checkInMaps("assimil21B", translatorBridge);
        checkInMaps("assimil21C", translatorBridge);
        checkInMaps("assimil21D", translatorBridge);
        checkInMaps("assimil21E", translatorBridge);
        checkInMaps("assimil21F", translatorBridge);
        checkInMaps("assimil21G", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_22() {
        checkInMaps("assimil22A", translatorBridge);
        checkInMaps("assimil22B", translatorBridge);
        checkInMaps("assimil22C", translatorBridge);
        checkInMaps("assimil22D", translatorBridge);
        checkInMaps("assimil22E", translatorBridge);
        checkInMaps("assimil22F", translatorBridge);
        checkInMaps("assimil22G", translatorBridge);
        checkInMaps("assimil22H", translatorBridge);
        checkInMaps("assimil22I", translatorBridge);
        checkInMaps("assimil22J", translatorBridge);
        checkInMaps("assimil22K", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_23() {
        checkInMaps("assimil23A", translatorBridge);
        checkInMaps("assimil23B", translatorBridge);
        checkInMaps("assimil23C", translatorBridge);
        checkInMaps("assimil23D", translatorBridge);
        checkInMaps("assimil23E", translatorBridge);
        checkInMaps("assimil23F", translatorBridge);
        checkInMaps("assimil23G", translatorBridge);
        checkInMaps("assimil23H", translatorBridge);
        checkInMaps("assimil23I", translatorBridge);
        checkInMaps("assimil23J", translatorBridge);
        checkInMaps("assimil23K", translatorBridge);
        checkInMaps("assimil23L", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_24() {
        checkInMaps("assimil24A", translatorBridge);
        checkInMaps("assimil24B", translatorBridge);
        checkInMaps("assimil24C", translatorBridge);
        checkInMaps("assimil24D", translatorBridge);
        checkInMaps("assimil24E", translatorBridge);
        checkInMaps("assimil24F", translatorBridge);
        checkInMaps("assimil24G", translatorBridge);
        checkInMaps("assimil24H", translatorBridge);
        checkInMaps("assimil24I", translatorBridge);
        checkInMaps("assimil24J", translatorBridge);
        checkInMaps("assimil24K", translatorBridge);
        checkInMaps("assimil24L", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_25() {
        checkInMaps("assimil25A", translatorBridge);
        checkInMaps("assimil25B", translatorBridge);
        checkInMaps("assimil25C", translatorBridge);
        checkInMaps("assimil25D", translatorBridge);
        checkInMaps("assimil25E", translatorBridge);
        checkInMaps("assimil25F", translatorBridge);
        checkInMaps("assimil25G", translatorBridge);
        checkInMaps("assimil25H", translatorBridge);
        checkInMaps("assimil25I", translatorBridge);
        checkInMaps("assimil25J", translatorBridge);
        checkInMaps("assimil25K", translatorBridge);
        checkInMaps("assimil25L", translatorBridge);
        checkInMaps("assimil25M", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_26() {
        checkInMaps("assimil26A", translatorBridge);
        checkInMaps("assimil26B", translatorBridge);
        checkInMaps("assimil26C", translatorBridge);
        checkInMaps("assimil26D", translatorBridge);
        checkInMaps("assimil26E", translatorBridge);
        checkInMaps("assimil26F", translatorBridge);
        checkInMaps("assimil26G", translatorBridge);
        checkInMaps("assimil26H", translatorBridge);
        checkInMaps("assimil26I", translatorBridge);
        checkInMaps("assimil26J", translatorBridge);
        checkInMaps("assimil26K", translatorBridge);
        checkInMaps("assimil26L", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_27() {
        checkInMaps("assimil27A", translatorBridge);
        checkInMaps("assimil27B", translatorBridge);
        checkInMaps("assimil27C", translatorBridge);
        checkInMaps("assimil27D", translatorBridge);
        checkInMaps("assimil27E", translatorBridge);
        checkInMaps("assimil27F", translatorBridge);
        checkInMaps("assimil27G", translatorBridge);
        checkInMaps("assimil27H", translatorBridge);
        checkInMaps("assimil27I", translatorBridge);
        checkInMaps("assimil27J", translatorBridge);
        checkInMaps("assimil27K", translatorBridge);
        checkInMaps("assimil27L", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_28() {
        checkInMaps("assimil28A", translatorBridge);
        checkInMaps("assimil28B", translatorBridge);
        checkInMaps("assimil28C", translatorBridge);
        checkInMaps("assimil28D", translatorBridge);
        checkInMaps("assimil28E", translatorBridge);
        checkInMaps("assimil28F", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_29() {
        checkInMaps("assimil29A", translatorBridge);
        checkInMaps("assimil29B", translatorBridge);
        checkInMaps("assimil29C", translatorBridge);
        checkInMaps("assimil29D", translatorBridge);
        checkInMaps("assimil29E", translatorBridge);
        checkInMaps("assimil29F", translatorBridge);
        checkInMaps("assimil29G", translatorBridge);
        checkInMaps("assimil29H", translatorBridge);
        checkInMaps("assimil29I", translatorBridge);
        checkInMaps("assimil29J", translatorBridge);
        checkInMaps("assimil29K", translatorBridge);
        checkInMaps("assimil29L", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_30() {
        checkInMaps("assimil30A", translatorBridge);
        checkInMaps("assimil30B", translatorBridge);
        checkInMaps("assimil30C", translatorBridge);
        checkInMaps("assimil30D", translatorBridge);
        checkInMaps("assimil30E", translatorBridge);
        checkInMaps("assimil30F", translatorBridge);
        checkInMaps("assimil30G", translatorBridge);
        checkInMaps("assimil30H", translatorBridge);
        checkInMaps("assimil30I", translatorBridge);
        checkInMaps("assimil30J", translatorBridge);
        checkInMaps("assimil30K", translatorBridge);
        checkInMaps("assimil30L", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_31() {
        checkInMaps("assimil31A", translatorBridge);
        checkInMaps("assimil31B", translatorBridge);
        checkInMaps("assimil31C", translatorBridge);
        checkInMaps("assimil31D", translatorBridge);
        checkInMaps("assimil31E", translatorBridge);
        checkInMaps("assimil31F", translatorBridge);
        checkInMaps("assimil31G", translatorBridge);
        checkInMaps("assimil31H", translatorBridge);
        checkInMaps("assimil31I", translatorBridge);
        checkInMaps("assimil31J", translatorBridge);
        checkInMaps("assimil31K", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_32() {
        checkInMaps("assimil32A", translatorBridge);
        checkInMaps("assimil32B", translatorBridge);
        checkInMaps("assimil32C", translatorBridge);
        checkInMaps("assimil32D", translatorBridge);
        checkInMaps("assimil32E", translatorBridge);
        checkInMaps("assimil32F", translatorBridge);
        checkInMaps("assimil32G", translatorBridge);
        checkInMaps("assimil32H", translatorBridge);
        checkInMaps("assimil32I", translatorBridge);
        checkInMaps("assimil32J", translatorBridge);
        checkInMaps("assimil32K", translatorBridge);
        checkInMaps("assimil32L", translatorBridge);
        checkInMaps("assimil32M", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_33() {
        checkInMaps("assimil33A", translatorBridge);
        checkInMaps("assimil33B", translatorBridge);
        checkInMaps("assimil33C", translatorBridge);
        checkInMaps("assimil33D", translatorBridge);
        checkInMaps("assimil33E", translatorBridge);
        checkInMaps("assimil33F", translatorBridge);
        checkInMaps("assimil33G", translatorBridge);
        checkInMaps("assimil33H", translatorBridge);
        checkInMaps("assimil33I", translatorBridge);
        checkInMaps("assimil33J", translatorBridge);
        checkInMaps("assimil33K", translatorBridge);
        checkInMaps("assimil33L", translatorBridge);
        checkInMaps("assimil33M", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_34() {
        checkInMaps("assimil34A", translatorBridge);
        checkInMaps("assimil34B", translatorBridge);
        checkInMaps("assimil34C", translatorBridge);
        checkInMaps("assimil34D", translatorBridge);
        checkInMaps("assimil34E", translatorBridge);
        checkInMaps("assimil34F", translatorBridge);
        checkInMaps("assimil34G", translatorBridge);
        checkInMaps("assimil34H", translatorBridge);
        checkInMaps("assimil34I", translatorBridge);
        checkInMaps("assimil34J", translatorBridge);
        checkInMaps("assimil34K", translatorBridge);
        checkInMaps("assimil34L", translatorBridge);
        checkInMaps("assimil34M", translatorBridge);
    }

    @Test
    public void test_assimil_chapter_35() {
        checkInMaps("assimil35A", translatorBridge);
        checkInMaps("assimil35B", translatorBridge);
        checkInMaps("assimil35C", translatorBridge);
        checkInMaps("assimil35D", translatorBridge);
        checkInMaps("assimil35E", translatorBridge);
        checkInMaps("assimil35F", translatorBridge);
    }

    @Test
    public void nkjv_genesis_chapter1() {
        checkInMaps("nkjv_genesis_chapter1A", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1B", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1C", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1D", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1E", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1F", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1G", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1H", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1I", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1J", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1K", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1L", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1M", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1N", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1O", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1P", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1Q", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1R", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1S", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1T", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1U", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1V", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1W", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1X", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1Y", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1Z", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1AA", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1BB", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1CC", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1DD", translatorBridge);
        checkInMaps("nkjv_genesis_chapter1EE", translatorBridge);
    }

    @Test
    public void nkjv_genesis_chapter2() {
        checkInMaps("nkjv_genesis_chapter2A", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2B", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2C", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2D", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2E", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2F", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2G", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2H", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2I", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2J", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2K", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2L", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2M", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2N", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2O", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2P", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2Q", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2R", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2S", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2T", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2U", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2V", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2W", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2X", translatorBridge);
        checkInMaps("nkjv_genesis_chapter2Y", translatorBridge);
    }

    @Test
    public void nkjv_genesis_chapter3() {
        checkInMaps("nkjv_genesis_chapter3A", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3B", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3C", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3D", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3E", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3F", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3G", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3H", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3I", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3J", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3K", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3L", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3M", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3N", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3O", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3P", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3Q", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3R", translatorBridge);
        //checkInMaps("nkjv_genesis_chapter3S", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3T", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3U", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3V", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3W", translatorBridge);
        checkInMaps("nkjv_genesis_chapter3X", translatorBridge);
    }

    @Test
    public void nkjv_genesis_chapter4() {
        checkInMaps("nkjv_genesis_chapter4A", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4B", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4C", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4D", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4E", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4F", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4G", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4H", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4I", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4J", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4K", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4L", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4M", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4N", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4O", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4P", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4Q", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4R", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4S", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4T", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4U", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4V", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4W", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4X", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4Y", translatorBridge);
        checkInMaps("nkjv_genesis_chapter4Z", translatorBridge);
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
    public void test_leviathan_introduction() {
        checkInMaps("leviathan_prefaceA", translatorBridge);
        checkInMaps("leviathan_prefaceB", translatorBridge);
        checkInMaps("leviathan_prefaceC", translatorBridge);
        checkInMaps("leviathan_prefaceD", translatorBridge);
        checkInMaps("leviathan_prefaceE", translatorBridge);
        checkInMaps("leviathan_prefaceF", translatorBridge);
        checkInMaps("leviathan_prefaceG", translatorBridge);
        checkInMaps("leviathan_prefaceH", translatorBridge);
        checkInMaps("leviathan_prefaceI", translatorBridge);
        checkInMaps("leviathan_prefaceJ", translatorBridge);
        checkInMaps("leviathan_prefaceK", translatorBridge);
        checkInMaps("leviathan_prefaceL", translatorBridge);
        checkInMaps("leviathan_prefaceM", translatorBridge);
        checkInMaps("leviathan_prefaceN", translatorBridge);
        checkInMaps("leviathan_prefaceO", translatorBridge);
        checkInMaps("leviathan_prefaceP", translatorBridge);
        checkInMaps("leviathan_prefaceQ", translatorBridge);
        checkInMaps("leviathan_prefaceR", translatorBridge);
        checkInMaps("leviathan_prefaceS", translatorBridge);
        checkInMaps("leviathan_prefaceT", translatorBridge);
        //checkInMaps("leviathan_prefaceU", translatorBridge);
        checkInMaps("leviathan_prefaceV1", translatorBridge);
        checkInMaps("leviathan_prefaceV2", translatorBridge);
        checkInMaps("leviathan_prefaceW1", translatorBridge);
        /*
        checkInMaps("leviathan_prefaceW2", translatorBridge);
        checkInMaps("leviathan_prefaceX", translatorBridge);
        checkInMaps("leviathan_prefaceY", translatorBridge);
        checkInMaps("leviathan_prefaceZ", translatorBridge);
        checkInMaps("leviathan_prefaceAA", translatorBridge);
        checkInMaps("leviathan_prefaceBB", translatorBridge);
        */
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
        checkInMaps("lightfoot_ch5A07", translatorBridge);
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
        checkInMaps("lightfoot_ch1222", translatorBridge);
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
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("lightfoot_ch1472", translatorBridge);
    }
}

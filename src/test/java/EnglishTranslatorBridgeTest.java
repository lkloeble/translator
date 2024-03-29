import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.*;
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
import patrologia.translator.basicelements.Language;
import patrologia.translator.rule.english.EnglishRuleFactory;
import patrologia.translator.utils.Analyzer;


import java.util.*;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class EnglishTranslatorBridgeTest extends TranslatorBridgeTest {

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
        String englishPathFile = localTestPath + "english_content.txt";
        String englishResultFile = localTestPath + "english_expected_results.txt";
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
                "be@IRREGULAR%[INFINITIVE]=[to be]%[IPR]=[am,are,is,are,are,are]%[AII]=[was,was,was,were,were,were]%[PAPR]=[being]%[AIF]=[bewill,bewill,bewill,bewill,bewill,bewill]%[ACP]=[bewould,bewould,bewould,bewould,bewould,bewould]%[ASP]=[ambe,arebe,be,ambe,arebe,bebe]%[AIMP]=[letbe,letbe,letbe,letbe,letbe,letbe]%[PAP]=[been]"
        });
        */
        return getFileContentForRepository(verbFileDescription);
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
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
    }
}

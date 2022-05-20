import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.DummyAccentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.greek.GreekCaseFactory;
import patrologia.translator.conjugation.greek.GreekConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.greek.GreekDeclension;
import patrologia.translator.declension.greek.GreekDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.GreekAnalyzer;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.greek.GreekRuleFactory;
import patrologia.translator.utils.Analyzer;

import java.util.*;

import static org.junit.Assert.assertTrue;

public class HerodoteBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    private String localTestPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\greek\\";
    private String localCommonPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\";

    @Before
    public void init() {
        String prepositionFileDescription = localResourcesPath + "prepositions.txt";
        String nounFileDescription = localResourcesPath + "nouns.txt";
        String verbFileDescription = localResourcesPath + "verbs.txt";
        String greekFrenchDataFile = localResourcesPath + "bailly_greek_to_french.txt";
        String frenchVerbsDataFile = localCommonPath + "french_verbs.txt";
        String declensionPath = localResourcesPath + "declensions";
        String declensionsAndFiles = localResourcesPath + "declensionsAndFiles.txt";
        String conjugationPath = localResourcesPath + "conjugations";
        String conjugationsAndFiles = localResourcesPath + "conjugationsAndFiles.txt";
        String greekPathFile = localTestPath + "herodote_content.txt";
        String greekResultFile = localTestPath + "herodote_result.txt";
        GreekRuleFactory ruleFactory = new GreekRuleFactory();
        GreekDeclensionFactory greekDeclensionFactory = new GreekDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.GREEK, new GreekCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        NounRepository nounRepository = new NounRepository(Language.GREEK, greekDeclensionFactory, new DummyAccentuer(), getNouns(nounFileDescription));
        VerbRepository2 verbRepository = new VerbRepository2(new GreekConjugationFactory(getGreekConjugations(conjugationsAndFiles), getGreekConjugationDefinitions(conjugationsAndFiles, conjugationPath), greekDeclensionFactory), Language.GREEK, new DummyAccentuer(), getVerbs(verbFileDescription));
        Analyzer greekAnalyzer = new GreekAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getGreekDico(greekFrenchDataFile), getFrenchVerbs(frenchVerbsDataFile), verbRepository, nounRepository, declensionPath, declensionsAndFiles, greekDeclensionFactory);
        translatorBridge = new TranslatorBridge(greekAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(greekPathFile);
        mapValuesForResult = loadMapFromFiles(greekResultFile);
    }

    private List<String> getFrenchVerbs(String frenchVerbsDataFile) {
        /*
        return Arrays.asList(new String[]{
                "ecrire@NORM%[INFINITIVE]=[écrire]%[IPR]=[écris,écris,écrit,écrivons,écrivez,écrivent]%[PAP]=[écrit]%[AIP]=[écrivis,écrivis,écrivit,écrivîmes,écrivîtes,écrivirent]%[AII]=[écrivais,écrivais,écrivait,écrivions,écriviez,écrivaient]"
        });
        */
        return getFileContentForRepository(frenchVerbsDataFile);
    }

    private List<String> getGreekDico(String greekFrenchDataFile) {
        /*
        return Arrays.asList(new String[]{
                "πανυ@prep%1(prep)=entièrement",
                "σωφρον@verb!norm%1(verb)=etresaindesprit"
        });

         */
        return getFileContentForRepository(greekFrenchDataFile);
    }

    private List<String> getPrepositions(String prepositionFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "επανω@prep()"
        });
        */
        return getFileContentForRepository(prepositionFileDescription);
    }

    private List<String> getNouns(String nounFileDescription) {
        /*
            return Arrays.asList(new String[]{
                    "ιω@neut%inv[accsg:ιουν]"
            });

         */
        return getFileContentForRepository(nounFileDescription);
    }

    private List<Declension> getDeclensionList(String file, String directory) {
        List<String> declensionNameList = getFileContentForRepository(file);
        List<Declension> declensionList = new ArrayList<>();
        for (String declensionName : declensionNameList) {
            String parts[] = declensionName.split("%");
            String fileName = parts[1];
            declensionList.add(new GreekDeclension(fileName, getDeclensionElements(fileName, directory)));
        }
        return declensionList;
    }

    private List<String> getVerbs(String verbFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "πεμπ,ειν,[ω-εις],(AORACTPARTB*πεμπ*πεμψ)"
        });
         */
        return getFileContentForRepository(verbFileDescription);
    }

    private List<String> getCustomVerbDefinitions() {
        return Arrays.asList(new String[]{
                "IPR=>ω,εις,ει,ουμεν,ειτε,ουσιν|ουσι",
                "AORACTPARTB=>[ας-ασα-αν]"
        });
    }

    private Map<String, List<String>> getGreekConjugationDefinitions(String file, String directory) {
        /*
        Map<String, List<String>> greekConjugationDefinitionsMap = new HashMap<>();
        greekConjugationDefinitionsMap.put("ω-εις", getCustomVerbDefinitions());
        return greekConjugationDefinitionsMap;
         */
        List<String> conjugationNameList = getFileContentForRepository(file);
        Map<String, List<String>> greekConjugationDefinitionsMap = new HashMap<>();
        for (String conjugationName : conjugationNameList) {
            String parts[] = conjugationName.split("%");
            String fileName = parts[1];
            String nameOnly = parts[0];
            greekConjugationDefinitionsMap.put(nameOnly, getConjugationElements(directory, fileName));
        }
        return greekConjugationDefinitionsMap;
    }

    private List<String> getGreekConjugations(String conjugationsAndFiles) {
        /*
        return Arrays.asList(new String[]{
                "o-is%permittere.txt"
        });
        */

        return getFileContentForRepository(conjugationsAndFiles);
    }





    @Test
    public void test_herodote_book1_chapter1() {
        checkInMaps("herodote110A", translatorBridge);
        //checkInMaps("herodote110B", translatorBridge);
        checkInMaps("herodote110C", translatorBridge);
        checkInMaps("herodote110D", translatorBridge);
        checkInMaps("herodote110E", translatorBridge);
    }

    @Test
    public void test_herodote_book1_chapter1_section2() {
        //checkInMaps("herodote111A", translatorBridge);
        checkInMaps("herodote111B", translatorBridge);
        checkInMaps("herodote111C", translatorBridge);
        checkInMaps("herodote111D", translatorBridge);
        checkInMaps("herodote111E", translatorBridge);
    }

    @Test
    public void test_herodote_book1_chapter1_section3() {
        checkInMaps("herodote112A", translatorBridge);
        checkInMaps("herodote112B", translatorBridge);
        checkInMaps("herodote113A", translatorBridge);
        checkInMaps("herodote113B", translatorBridge);
        checkInMaps("herodote113C", translatorBridge);
        checkInMaps("herodote114A", translatorBridge);
        checkInMaps("herodote114B", translatorBridge);
        checkInMaps("herodote114C", translatorBridge);
        checkInMaps("herodote114D", translatorBridge);
    }

    @Test
    public void test_herodote_book1_chapter2_section1() {
        checkInMaps("herodote121A", translatorBridge);
        checkInMaps("herodote121B", translatorBridge);
        checkInMaps("herodote121C", translatorBridge);
        checkInMaps("herodote121D", translatorBridge);
        checkInMaps("herodote121E", translatorBridge);
        checkInMaps("herodote121F", translatorBridge);
        checkInMaps("herodote121G", translatorBridge);
    }

    @Test
    public void test_herodote_book1_chapter2_section2() {
        checkInMaps("herodote122A", translatorBridge);
        checkInMaps("herodote122B", translatorBridge);
        checkInMaps("herodote122C", translatorBridge);
        checkInMaps("herodote122D", translatorBridge);
    }

    @Test
    public void test_herodote_book1_chapter2_section3() {
        checkInMaps("herodote123A", translatorBridge);
        //checkInMaps("herodote123B", translatorBridge);
        checkInMaps("herodote123C", translatorBridge);
        checkInMaps("herodote123D", translatorBridge);
    }


    @Test
    public void test_failed_ones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
        //checkInMaps("herodote123B", translatorBridge);
    }

}

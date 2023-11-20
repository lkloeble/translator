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

public class GreekClementRomeFirstCorinthiansTest extends TranslatorBridgeTest {

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
        String greekPathFile = localTestPath + "clement_content.txt";
        String greekResultFile = localTestPath + "clement_expected_result.txt";
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
                "αποπλ,ειν,[εω-εις],(PRESACTPARTB*αποπλ*αποπλε)"
        });
         */
        return getFileContentForRepository(verbFileDescription);
    }

    private List<String> getCustomVerbDefinitions() {
        return Arrays.asList(new String[]{
                //"IPR=>ω,εις,ει,ουμεν,ειτε,ουσιν|ουσι",
                "PRESACTPARTB=>[ους-ουσα-ον]"
        });
    }

    private Map<String, List<String>> getGreekConjugationDefinitions(String file, String directory) {
        /*
        Map<String, List<String>> greekConjugationDefinitionsMap = new HashMap<>();
        greekConjugationDefinitionsMap.put("εω-εις", getCustomVerbDefinitions());
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
    public void test_clement1() {
        checkInMaps("clement1A", translatorBridge);
        checkInMaps("clement1B", translatorBridge);
        checkInMaps("clement1C", translatorBridge);
        checkInMaps("clement1D", translatorBridge);
        checkInMaps("clement1E", translatorBridge);
        //checkInMaps("clement1F", translatorBridge);
        checkInMaps("clement1G", translatorBridge);
        checkInMaps("clement1H", translatorBridge);
        //checkInMaps("clement1I", translatorBridge);
        checkInMaps("clement1J", translatorBridge);
        //checkInMaps("clement1K", translatorBridge);
        //checkInMaps("clement1L", translatorBridge);
        //checkInMaps("clement1M", translatorBridge);
        checkInMaps("clement1N", translatorBridge);
        //checkInMaps("clement1O", translatorBridge);
    }

    @Test
    public void test_clement2() {
        //checkInMaps("clement2A1", translatorBridge);
        checkInMaps("clement2A2", translatorBridge);
        //checkInMaps("clement2B1", translatorBridge);
        checkInMaps("clement2B2", translatorBridge);
        checkInMaps("clement2C1", translatorBridge);
        checkInMaps("clement2C2", translatorBridge);
        checkInMaps("clement2C3", translatorBridge);
        //checkInMaps("clement2C4", translatorBridge);
        checkInMaps("clement2D1", translatorBridge);
        checkInMaps("clement2D2", translatorBridge);
        checkInMaps("clement2E", translatorBridge);
        checkInMaps("clement2F1", translatorBridge);
        checkInMaps("clement2F2", translatorBridge);
        //checkInMaps("clement2F3", translatorBridge);
        checkInMaps("clement2G", translatorBridge);
        checkInMaps("clement2H", translatorBridge);
        checkInMaps("clement2I", translatorBridge);
    }

    @Test
    public void test_clement3() {
        checkInMaps("clement3A", translatorBridge);
        checkInMaps("clement3B", translatorBridge);
        checkInMaps("clement3C", translatorBridge);
        checkInMaps("clement3D", translatorBridge);
        checkInMaps("clement3E", translatorBridge);
        checkInMaps("clement3F", translatorBridge);
        checkInMaps("clement3G", translatorBridge);
        checkInMaps("clement3H", translatorBridge);
        checkInMaps("clement3I", translatorBridge);
        //checkInMaps("clement3J", translatorBridge);
    }

    @Test
    public void test_clement4() {
        checkInMaps("clement4A1", translatorBridge);
        //checkInMaps("clement4A2", translatorBridge);
        checkInMaps("clement4B", translatorBridge);
        checkInMaps("clement4C", translatorBridge);
        checkInMaps("clement4D1", translatorBridge);
        checkInMaps("clement4D2", translatorBridge);
        checkInMaps("clement4E", translatorBridge);
        checkInMaps("clement4F1", translatorBridge);
        checkInMaps("clement4F2", translatorBridge);
        checkInMaps("clement4F3", translatorBridge);
        checkInMaps("clement4G", translatorBridge);
        checkInMaps("clement4H", translatorBridge);
        checkInMaps("clement4I", translatorBridge);
        checkInMaps("clement4J1", translatorBridge);
        checkInMaps("clement4J2", translatorBridge);
        checkInMaps("clement4J3", translatorBridge);
        checkInMaps("clement4K", translatorBridge);
        checkInMaps("clement4L", translatorBridge);
        checkInMaps("clement4M1", translatorBridge);
        checkInMaps("clement4M2", translatorBridge);
    }

    @Test
    public void test_clement5() {
        checkInMaps("clement5A1", translatorBridge);
        checkInMaps("clement5A2", translatorBridge);
        //checkInMaps("clement5A3", translatorBridge);
        checkInMaps("clement5B1", translatorBridge);
        checkInMaps("clement5B2", translatorBridge);
        checkInMaps("clement5C1", translatorBridge);
        checkInMaps("clement5D1", translatorBridge);
        checkInMaps("clement5D2", translatorBridge);
        checkInMaps("clement5D3", translatorBridge);
        checkInMaps("clement5E1", translatorBridge);
        checkInMaps("clement5F1", translatorBridge);
        checkInMaps("clement5F2", translatorBridge);
        checkInMaps("clement5F3", translatorBridge);
        checkInMaps("clement5G1", translatorBridge);
        checkInMaps("clement5G2", translatorBridge);
        checkInMaps("clement5G3", translatorBridge);
        checkInMaps("clement5G4", translatorBridge);
    }

    @Test
    public void test_clement6() {
        checkInMaps("clement6A1", translatorBridge);
        checkInMaps("clement6A2", translatorBridge);
        checkInMaps("clement6A3", translatorBridge);
        checkInMaps("clement6A4", translatorBridge);
        checkInMaps("clement6B1", translatorBridge);
        //checkInMaps("clement6B2", translatorBridge);
        checkInMaps("clement6B3", translatorBridge);
        checkInMaps("clement6B4", translatorBridge);
        checkInMaps("clement6C1", translatorBridge);
        checkInMaps("clement6C2", translatorBridge);
        checkInMaps("clement6C3", translatorBridge);
        checkInMaps("clement6D1", translatorBridge);
        checkInMaps("clement6D2", translatorBridge);
    }

    @Test
    public void test_clement7() {
        checkInMaps("clement7A1", translatorBridge);
        checkInMaps("clement7A2", translatorBridge);
        //checkInMaps("clement7B1", translatorBridge);
        checkInMaps("clement7C1", translatorBridge);
        checkInMaps("clement7D1", translatorBridge);
        checkInMaps("clement7D2", translatorBridge);
        checkInMaps("clement7E1", translatorBridge);
        checkInMaps("clement7E2", translatorBridge);
        //checkInMaps("clement7F1", translatorBridge);
        //checkInMaps("clement7G1", translatorBridge);
        //checkInMaps("clement7G2", translatorBridge);
        checkInMaps("clement7G3", translatorBridge);
    }


    @Test
    public void test_clement8() {
        checkInMaps("clement8A", translatorBridge);
        checkInMaps("clement8B1", translatorBridge);
        checkInMaps("clement8B2", translatorBridge);
        checkInMaps("clement8C1", translatorBridge);
        checkInMaps("clement8C2", translatorBridge);
        checkInMaps("clement8C3", translatorBridge);
        checkInMaps("clement8D1", translatorBridge);
        checkInMaps("clement8D2", translatorBridge);
        checkInMaps("clement8D3", translatorBridge);
        checkInMaps("clement8D4", translatorBridge);
        checkInMaps("clement8D5", translatorBridge);
        checkInMaps("clement8D6", translatorBridge);
        checkInMaps("clement8D7", translatorBridge);
        checkInMaps("clement8E", translatorBridge);
    }

    @Test
    public void test_clement9() {
        checkInMaps("clement9A1", translatorBridge);
        checkInMaps("clement9A2", translatorBridge);
        checkInMaps("clement9A3", translatorBridge);
        checkInMaps("clement9A4", translatorBridge);
        checkInMaps("clement9B", translatorBridge);
        checkInMaps("clement9C", translatorBridge);
        checkInMaps("clement9D1", translatorBridge);
        checkInMaps("clement9D2", translatorBridge);
    }

    @Test
    public void test_failed_ones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
    }
}

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

public class GreekHesiodTheogonyTest extends TranslatorBridgeTest {

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
        String greekPathFile = localTestPath + "hesiod_theogony_content.txt";
        String greekResultFile = localTestPath + "hesiod_theogony_result.txt";
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
                    "καρ@neut%η-ητος"
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
                "τερπ,ειν,[ω-εις]"
        });

         */
        return getFileContentForRepository(verbFileDescription);
    }

    private List<String> getCustomVerbDefinitions() {
        return Arrays.asList(new String[]{
                "IPR=>ω,εις,ει,ουμεν,ειτε,ουσιν|ουσι"
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
    public void test_hesiode_theogony1to100() {
        checkInMaps("hesiodtheogony001", translatorBridge);
        checkInMaps("hesiodtheogony002", translatorBridge);
        checkInMaps("hesiodtheogony003", translatorBridge);
        checkInMaps("hesiodtheogony004", translatorBridge);
        //checkInMaps("hesiodtheogony005", translatorBridge);
        checkInMaps("hesiodtheogony006", translatorBridge);
        checkInMaps("hesiodtheogony007", translatorBridge);
        checkInMaps("hesiodtheogony008", translatorBridge);
        checkInMaps("hesiodtheogony009", translatorBridge);
        checkInMaps("hesiodtheogony010", translatorBridge);
        checkInMaps("hesiodtheogony011", translatorBridge);
        checkInMaps("hesiodtheogony012", translatorBridge);
        checkInMaps("hesiodtheogony013", translatorBridge);
        checkInMaps("hesiodtheogony014", translatorBridge);
        checkInMaps("hesiodtheogony015", translatorBridge);
        checkInMaps("hesiodtheogony016", translatorBridge);
        checkInMaps("hesiodtheogony017", translatorBridge);
        checkInMaps("hesiodtheogony018", translatorBridge);
        checkInMaps("hesiodtheogony019", translatorBridge);
        checkInMaps("hesiodtheogony020", translatorBridge);
        checkInMaps("hesiodtheogony021", translatorBridge);
        checkInMaps("hesiodtheogony022", translatorBridge);
        checkInMaps("hesiodtheogony023", translatorBridge);
        //checkInMaps("hesiodtheogony024", translatorBridge);
        checkInMaps("hesiodtheogony025", translatorBridge);
        checkInMaps("hesiodtheogony026", translatorBridge);
        checkInMaps("hesiodtheogony027", translatorBridge);
        checkInMaps("hesiodtheogony028", translatorBridge);
        checkInMaps("hesiodtheogony029", translatorBridge);
        checkInMaps("hesiodtheogony030", translatorBridge);
        checkInMaps("hesiodtheogony031", translatorBridge);
        checkInMaps("hesiodtheogony032", translatorBridge);
        checkInMaps("hesiodtheogony033", translatorBridge);
        checkInMaps("hesiodtheogony034", translatorBridge);
        checkInMaps("hesiodtheogony035", translatorBridge);
        checkInMaps("hesiodtheogony036", translatorBridge);
        checkInMaps("hesiodtheogony037", translatorBridge);
        checkInMaps("hesiodtheogony038", translatorBridge);
        checkInMaps("hesiodtheogony039", translatorBridge);
        checkInMaps("hesiodtheogony040", translatorBridge);
        checkInMaps("hesiodtheogony041", translatorBridge);
        checkInMaps("hesiodtheogony042", translatorBridge);
        checkInMaps("hesiodtheogony043", translatorBridge);
        checkInMaps("hesiodtheogony044", translatorBridge);
        checkInMaps("hesiodtheogony045", translatorBridge);
        checkInMaps("hesiodtheogony046", translatorBridge);
        checkInMaps("hesiodtheogony047", translatorBridge);
        checkInMaps("hesiodtheogony048", translatorBridge);
        checkInMaps("hesiodtheogony049", translatorBridge);
        checkInMaps("hesiodtheogony050", translatorBridge);
        checkInMaps("hesiodtheogony051", translatorBridge);
        checkInMaps("hesiodtheogony052", translatorBridge);
    }

    @Test
    public void test_failed_ones() {
        assertTrue(true);
        checkInMaps("foobar", translatorBridge);
        //checkInMaps("homereliv1lig004", translatorBridge);
    }
}

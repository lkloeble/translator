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

public class GreekHomereTest  extends TranslatorBridgeTest {

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
        String greekPathFile = localTestPath + "homere_content.txt";
        String greekResultFile = localTestPath + "homere_expected_result.txt";
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
                    "σμινθ@masc%υς-ως"
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
                "οιχ,ομαι,[ομαι-η],(MPII*οιχ*ωχ)"
        });
        µ:
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
    public void test_homere_yliad_book1_card1() {
        checkInMaps("homereliv1lig001", translatorBridge);
        //checkInMaps("homereliv1lig002", translatorBridge);
        //checkInMaps("homereliv1lig003", translatorBridge);
        checkInMaps("homereliv1lig004", translatorBridge);
        checkInMaps("homereliv1lig005", translatorBridge);
        //checkInMaps("homereliv1lig006", translatorBridge);
        checkInMaps("homereliv1lig007", translatorBridge);
        checkInMaps("homereliv1lig008", translatorBridge);
        checkInMaps("homereliv1lig009", translatorBridge);
        checkInMaps("homereliv1lig010", translatorBridge);
        checkInMaps("homereliv1lig011", translatorBridge);
        //checkInMaps("homereliv1lig012", translatorBridge);
        checkInMaps("homereliv1lig013", translatorBridge);
        checkInMaps("homereliv1lig014", translatorBridge);
        checkInMaps("homereliv1lig015", translatorBridge);
        checkInMaps("homereliv1lig016", translatorBridge);
        checkInMaps("homereliv1lig017", translatorBridge);
        checkInMaps("homereliv1lig018", translatorBridge);
        checkInMaps("homereliv1lig019", translatorBridge);
        checkInMaps("homereliv1lig020", translatorBridge);
        checkInMaps("homereliv1lig021", translatorBridge);
        checkInMaps("homereliv1lig022", translatorBridge);
        checkInMaps("homereliv1lig023", translatorBridge);
        checkInMaps("homereliv1lig024", translatorBridge);
        checkInMaps("homereliv1lig025", translatorBridge);
        checkInMaps("homereliv1lig026", translatorBridge);
        checkInMaps("homereliv1lig027", translatorBridge);
        checkInMaps("homereliv1lig028", translatorBridge);
        checkInMaps("homereliv1lig029", translatorBridge);
        checkInMaps("homereliv1lig030", translatorBridge);
        checkInMaps("homereliv1lig031", translatorBridge);
        checkInMaps("homereliv1lig032", translatorBridge);
    }


    @Test
    public void test_homere_yliad_book1_card2() {
        //checkInMaps("homereliv1lig033", translatorBridge);
        checkInMaps("homereliv1lig034", translatorBridge);
        checkInMaps("homereliv1lig035", translatorBridge);
        checkInMaps("homereliv1lig036", translatorBridge);
        checkInMaps("homereliv1lig037", translatorBridge);
        checkInMaps("homereliv1lig038", translatorBridge);
        checkInMaps("homereliv1lig039", translatorBridge);
        checkInMaps("homereliv1lig040", translatorBridge);
        checkInMaps("homereliv1lig041", translatorBridge);
        checkInMaps("homereliv1lig042", translatorBridge);
        checkInMaps("homereliv1lig043", translatorBridge);
        checkInMaps("homereliv1lig044", translatorBridge);
        checkInMaps("homereliv1lig045", translatorBridge);
        checkInMaps("homereliv1lig046", translatorBridge);
        checkInMaps("homereliv1lig047", translatorBridge);
        checkInMaps("homereliv1lig048", translatorBridge);
        checkInMaps("homereliv1lig049", translatorBridge);
        checkInMaps("homereliv1lig050", translatorBridge);
        checkInMaps("homereliv1lig051", translatorBridge);
        checkInMaps("homereliv1lig052", translatorBridge);
        checkInMaps("homereliv1lig053", translatorBridge);
        checkInMaps("homereliv1lig054", translatorBridge);
        checkInMaps("homereliv1lig055", translatorBridge);
        checkInMaps("homereliv1lig056", translatorBridge);
        checkInMaps("homereliv1lig057", translatorBridge);
        checkInMaps("homereliv1lig058", translatorBridge);
        checkInMaps("homereliv1lig059", translatorBridge);
        checkInMaps("homereliv1lig060", translatorBridge);
        checkInMaps("homereliv1lig061", translatorBridge);
        checkInMaps("homereliv1lig062", translatorBridge);
        checkInMaps("homereliv1lig063", translatorBridge);
        checkInMaps("homereliv1lig064", translatorBridge);
        checkInMaps("homereliv1lig065", translatorBridge);
        checkInMaps("homereliv1lig066", translatorBridge);
        checkInMaps("homereliv1lig067", translatorBridge);
    }


        @Test
    public void test_failed_ones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
        //checkInMaps("homereliv1lig004", translatorBridge);
    }

}

import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.DummyAccentuer;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.romanian.RomanianCaseFactory;
import patrologia.translator.conjugation.romanian.RomanianConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.romanian.RomanianDeclension;
import patrologia.translator.declension.romanian.RomanianDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.RomanianAnalyzer;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.romanian.RomanianRuleFactory;
import patrologia.translator.utils.Analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static patrologia.translator.basicelements.Language.ROMANIAN;

public class StaniloaePhilocaliaTest  extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    private String localTestPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\romanian\\";
    private String localCommonPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\";

    @Before
    public void init() {
        String prepositionFileDescription = localResourcesPath + "prepositions.txt";
        String nounFileDescription = localResourcesPath + "nouns.txt";
        String verbFileDescription = localResourcesPath + "verbs.txt";
        String romanianFrenchDataFile = localResourcesPath + "dico_romanian_french.txt";
        String frenchVerbsDataFile = localCommonPath + "french_verbs.txt";
        String declensionPath = localResourcesPath + "declensions";
        String declensionsAndFiles = localResourcesPath + "declensionsAndFiles.txt";
        String conjugationPath = localResourcesPath + "conjugations";
        String conjugationsAndFiles = localResourcesPath + "conjugationsAndFiles.txt";
        String romanianPathFile = localTestPath + "staniloae_philocalia_content.txt";
        String romanianResultFile = localTestPath + "staniloae_philocalia_expected_results.txt";
        RomanianDeclensionFactory romanianDeclensionFactory = new RomanianDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        NounRepository nounRepository = new NounRepository(ROMANIAN, romanianDeclensionFactory, new patrologia.translator.basicelements.DummyAccentuer(), getNouns(nounFileDescription));
        RomanianConjugationFactory romanianConjugationFactory = new RomanianConjugationFactory(getRomanianConjugations(conjugationsAndFiles), getRomanianConjugationDefinitions(conjugationsAndFiles, conjugationPath), romanianDeclensionFactory);
        VerbRepository2 verbRepository = new VerbRepository2(romanianConjugationFactory, ROMANIAN, new DummyAccentuer(), getVerbs(verbFileDescription));
        RomanianRuleFactory ruleFactory = new RomanianRuleFactory(verbRepository);
        PrepositionRepository prepositionRepository = new PrepositionRepository(ROMANIAN, new RomanianCaseFactory(), ruleFactory, getPrepositions(prepositionFileDescription));
        Analyzer romanianAnalyzer = new RomanianAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getRomanianDico(romanianFrenchDataFile), getFrenchVerbs(frenchVerbsDataFile), verbRepository, nounRepository, declensionPath, declensionsAndFiles, romanianDeclensionFactory);
        translatorBridge = new TranslatorBridge(romanianAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(romanianPathFile);
        mapValuesForResult = loadMapFromFiles(romanianResultFile);
    }

    private List<String> getRomanianDico(String romanianFrenchDataFile) {
        /*
        return Arrays.asList(new String[]{
                        "pot@verb!irrg%1(verb)=pouvoir",
                        "deveni@verb!norm%1(verb)=devenir"
                });
                */
        return getFileContentForRepository(romanianFrenchDataFile);
    }

    private List<String> getPrepositions(String prepositionFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "acea@prep()"
        });
        */
        return getFileContentForRepository(prepositionFileDescription);
    }

    private List<String> getNouns(String nounFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "alt@adj%adjts"
        });
         */
        return getFileContentForRepository(nounFileDescription);
    }

    private Map<String, List<String>> getRomanianConjugationDefinitions(String file, String directory) {
        /*
        Map<String, List<String>> conjugationDefinitionsMap = new HashMap<>();
        conjugationDefinitionsMap.put("face", Arrays.asList("IPR=>,i,e,em,etsi,,","PAP=>[adjts]","AIMP=> ,"));
        return conjugationDefinitionsMap;
        */

        List<String> conjugationNameList = getFileContentForRepository(file);
        Map<String, List<String>> romanianConjugationDefinitionsMap = new HashMap<>();
        for (String conjugationName : conjugationNameList) {
            String parts[] = conjugationName.split("%");
            String fileName = parts[1];
            String nameOnly = parts[0];
            romanianConjugationDefinitionsMap.put(nameOnly, getConjugationElements(directory, fileName));
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
                "bantu,i,[bantui],(PAP*bantu*bantuit)"
        });
        */
        return getFileContentForRepository(verbFileDescription);
    }

    private List<String> getFrenchVerbs(String frenchVerbFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "devenir@NORM%[INFINITIVE]=[devenir]%[IPR]=[deviens,deviens,devient,devenons,devenez,deviennent]%[PII]=[étais devenu,étais devenu,était devenu,étions devenus,étiez devenus,étaient devenus]%[PIP]=[suis devenu,es devenu,est devenu,sommes devenus,êtes devenus,sont devenus]%[AIP]=[devins,devins,devint,devînmes,devîntes,devinrent]%[PIF]=[serai devenu,seras devenu,sera devenu,serons devenus,serez devenus,seront devenus]%[PRPARPASS]=[être devenu,qui est devenu,qui est devenu,qui est devenu]%[PAAOIM]=[-,que sois,que soit,-,que soyez,que soient]%[MIAOIN]=[fus,fus,fut,fûmes,fûtes,furent]%[AORPASSIMP]=[sois,sois,soit,soyons,soyez,soient]%[PAP]=[devenu]%[PERACTPAR]=[devenu,-,-,-]"
        });
        */
        return getFileContentForRepository(frenchVerbFileDescription);
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
    public void test_filocalia1() {
        checkInMaps("filocalia1A", translatorBridge);
        checkInMaps("filocalia1B", translatorBridge);
        checkInMaps("filocalia1C", translatorBridge);
        checkInMaps("filocalia1D", translatorBridge);
        checkInMaps("filocalia1E", translatorBridge);
        checkInMaps("filocalia1F", translatorBridge);
        checkInMaps("filocalia1G", translatorBridge);
        checkInMaps("filocalia1H", translatorBridge);
        //checkInMaps("filocalia1I", translatorBridge);
        checkInMaps("filocalia1J", translatorBridge);
        checkInMaps("filocalia1K", translatorBridge);
        checkInMaps("filocalia1L", translatorBridge);
        //checkInMaps("filocalia1M", translatorBridge);
        //checkInMaps("filocalia1N", translatorBridge);
        checkInMaps("filocalia1O", translatorBridge);
        //checkInMaps("filocalia1P", translatorBridge);
        //checkInMaps("filocalia1Q", translatorBridge);
        checkInMaps("filocalia1R", translatorBridge);
        checkInMaps("filocalia1S", translatorBridge);
        checkInMaps("filocalia1T", translatorBridge);
        checkInMaps("filocalia1U", translatorBridge);
        checkInMaps("filocalia1V", translatorBridge);
        checkInMaps("filocalia1W1", translatorBridge);
        checkInMaps("filocalia1W2", translatorBridge);
        //checkInMaps("filocalia1X", translatorBridge);
        checkInMaps("filocalia1Y", translatorBridge);
        //checkInMaps("filocalia1Z", translatorBridge);
    }

    @Test
    public void test_filocalia1_antonie1() {
        checkInMaps("filocalia1antonie1", translatorBridge);
        checkInMaps("filocalia1antonie2", translatorBridge);
        checkInMaps("filocalia1antonie1A", translatorBridge);
        checkInMaps("filocalia1antonie1B", translatorBridge);
        checkInMaps("filocalia1antonie1C", translatorBridge);
        checkInMaps("filocalia1antonie1D", translatorBridge);
        checkInMaps("filocalia1antonie1E", translatorBridge);
        checkInMaps("filocalia1antonie1F", translatorBridge);
        checkInMaps("filocalia1antonie1G", translatorBridge);
    }

    @Test
    public void test_filocalia1_antonie2() {
        //checkInMaps("filocalia1antonie2A", translatorBridge);
        checkInMaps("filocalia1antonie2B", translatorBridge);
        checkInMaps("filocalia1antonie2C", translatorBridge);
        //checkInMaps("filocalia1antonie2D", translatorBridge);
        checkInMaps("filocalia1antonie2E", translatorBridge);
        checkInMaps("filocalia1antonie2F", translatorBridge);
        checkInMaps("filocalia1antonie2G", translatorBridge);
        //checkInMaps("filocalia1antonie2H", translatorBridge);
        checkInMaps("filocalia1antonie2I", translatorBridge);
    }

    @Test
    public void test_filocalia1_antonie3() {
        checkInMaps("filocaliaantonie3A", translatorBridge);
        checkInMaps("filocaliaantonie3B", translatorBridge);
        checkInMaps("filocaliaantonie3C", translatorBridge);
        checkInMaps("filocaliaantonie3D", translatorBridge);
        checkInMaps("filocaliaantonie3E", translatorBridge);
        checkInMaps("filocaliaantonie3F", translatorBridge);
        checkInMaps("filocaliaantonie3G", translatorBridge);
        checkInMaps("filocaliaantonie3H", translatorBridge);
    }

    @Test
    public void test_filocalia1_antonie4() {
        checkInMaps("filocaliaantonie4A", translatorBridge);
        //checkInMaps("filocaliaantonie4B", translatorBridge);
        checkInMaps("filocaliaantonie4C", translatorBridge);
        checkInMaps("filocaliaantonie4D", translatorBridge);
        checkInMaps("filocaliaantonie4E", translatorBridge);
        checkInMaps("filocaliaantonie4F", translatorBridge);
        checkInMaps("filocaliaantonie4G", translatorBridge);
    }

    @Test
    public void test_filocalia5() {
        //checkInMaps("filocaliaantonie5A", translatorBridge);
        checkInMaps("filocaliaantonie5B", translatorBridge);
        checkInMaps("filocaliaantonie5C", translatorBridge);
    }

    @Test
    public void test_filocalia6() {
        checkInMaps("filocaliaantonie6A", translatorBridge);
        checkInMaps("filocaliaantonie6B", translatorBridge);
        checkInMaps("filocaliaantonie6C", translatorBridge);
        checkInMaps("filocaliaantonie6D", translatorBridge);
        checkInMaps("filocaliaantonie6E", translatorBridge);
    }

    @Test
    public void test_filocalia7() {
        checkInMaps("filocaliaantonie7A", translatorBridge);
        checkInMaps("filocaliaantonie7B", translatorBridge);
        checkInMaps("filocaliaantonie7C", translatorBridge);
        checkInMaps("filocaliaantonie7D", translatorBridge);
        checkInMaps("filocaliaantonie7E", translatorBridge);
        checkInMaps("filocaliaantonie7F", translatorBridge);
        checkInMaps("filocaliaantonie7G", translatorBridge);
    }
}

import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.*;
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

import java.util.*;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static patrologia.translator.basicelements.Language.ROMANIAN;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianTranslatorBridgeTest extends TranslatorBridgeTest {

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
        String romanianPathFile = localTestPath + "romanian_content.txt";
        String romanianResultFile = localTestPath + "romanian_expected_results.txt";
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
        //checkInMaps("assimil6F", translatorBridge);
        checkInMaps("assimil6G", translatorBridge);
        //checkInMaps("assimil6H", translatorBridge);
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
        //checkInMaps("assimil15D", translatorBridge);
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
        //checkInMaps("assimil15Q", translatorBridge);
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
        //checkInMaps("assimil19M", translatorBridge);
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
        //checkInMaps("staniloae1D", translatorBridge);
        checkInMaps("staniloae1E", translatorBridge);
        checkInMaps("staniloae1F", translatorBridge);
        checkInMaps("staniloae1G", translatorBridge);
        //checkInMaps("staniloae1H", translatorBridge);
        checkInMaps("staniloae1I", translatorBridge);
        checkInMaps("staniloae1J", translatorBridge);
        checkInMaps("staniloae1K", translatorBridge);
        checkInMaps("staniloae1L", translatorBridge);
        checkInMaps("staniloae1M", translatorBridge);
        checkInMaps("staniloae1N", translatorBridge);
        checkInMaps("staniloae1O", translatorBridge);
        checkInMaps("staniloae1P", translatorBridge);
        checkInMaps("staniloae1Q", translatorBridge);
        //checkInMaps("staniloae1R", translatorBridge);
        checkInMaps("staniloae1S", translatorBridge);
        //checkInMaps("staniloae1T", translatorBridge);
        //checkInMaps("staniloae1U", translatorBridge);
        checkInMaps("staniloae1V", translatorBridge);
        checkInMaps("staniloae1W", translatorBridge);
        checkInMaps("staniloae1X", translatorBridge);
        checkInMaps("staniloae1Y", translatorBridge);
        checkInMaps("staniloae1Z", translatorBridge);
        checkInMaps("staniloae1AA", translatorBridge);
        //checkInMaps("staniloae1BB", translatorBridge);
        checkInMaps("staniloae1CC", translatorBridge);
        checkInMaps("staniloae1DD", translatorBridge);
        checkInMaps("staniloae1EE", translatorBridge);
        checkInMaps("staniloae1FF", translatorBridge);
        checkInMaps("staniloae1GG", translatorBridge);
        checkInMaps("staniloae1HH", translatorBridge);
        //checkInMaps("staniloae1II", translatorBridge);
        checkInMaps("staniloae1JJ", translatorBridge);
    }

    @Test
    public void test_staniloae_chap1_point4() {
        //checkInMaps("staniloae4A", translatorBridge);
        //checkInMaps("staniloae4B", translatorBridge);
        checkInMaps("staniloae4C", translatorBridge);
        checkInMaps("staniloae4D", translatorBridge);
        //checkInMaps("staniloae4E", translatorBridge);
        //checkInMaps("staniloae4F", translatorBridge);
        //checkInMaps("staniloae4G", translatorBridge);
        //checkInMaps("staniloae4H", translatorBridge);
        //checkInMaps("staniloae4I1", translatorBridge);
        //checkInMaps("staniloae4I2", translatorBridge);
        checkInMaps("staniloae4J1", translatorBridge);
        checkInMaps("staniloae4J2", translatorBridge);
        //checkInMaps("staniloae4J3", translatorBridge);
        checkInMaps("staniloae4K1", translatorBridge);
        checkInMaps("staniloae4K2", translatorBridge);
        checkInMaps("staniloae4K3", translatorBridge);
        checkInMaps("staniloae4L", translatorBridge);
    }

    @Test
    public void test_staniloae_ch1_point5() {
        checkInMaps("staniloae5A", translatorBridge);
        checkInMaps("staniloae5B", translatorBridge);
        checkInMaps("staniloae5C", translatorBridge);
        checkInMaps("staniloae5D", translatorBridge);
        checkInMaps("staniloae5E", translatorBridge);
        checkInMaps("staniloae5F", translatorBridge);
        //checkInMaps("staniloae5G", translatorBridge);
        //checkInMaps("staniloae5H", translatorBridge);
        checkInMaps("staniloae5I", translatorBridge);
        //checkInMaps("staniloae5J", translatorBridge);
        checkInMaps("staniloae5K", translatorBridge);
        //checkInMaps("staniloae5L", translatorBridge);
        checkInMaps("staniloae5M", translatorBridge);
        checkInMaps("staniloae5N", translatorBridge);
        checkInMaps("staniloae5O", translatorBridge);
        //checkInMaps("staniloae5P", translatorBridge);
        checkInMaps("staniloae5Q", translatorBridge);
    }

    @Test
    public void test_staniloae_ch1_point6() {
        checkInMaps("staniloae6A", translatorBridge);
        checkInMaps("staniloae6B", translatorBridge);
        checkInMaps("staniloae6C", translatorBridge);
        checkInMaps("staniloae6D", translatorBridge);
        checkInMaps("staniloae6E", translatorBridge);
        //checkInMaps("staniloae6F", translatorBridge);
        checkInMaps("staniloae6G", translatorBridge);
        //checkInMaps("staniloae6H", translatorBridge);
        checkInMaps("staniloae6I", translatorBridge);
        checkInMaps("staniloae6J", translatorBridge);
        checkInMaps("staniloae6K", translatorBridge);
        //checkInMaps("staniloae6L", translatorBridge);
        checkInMaps("staniloae6M", translatorBridge);
        //checkInMaps("staniloae6N", translatorBridge);
        checkInMaps("staniloae6O", translatorBridge);
        //checkInMaps("staniloae6P", translatorBridge);
        //checkInMaps("staniloae6Q", translatorBridge);
        checkInMaps("staniloae6R", translatorBridge);
        checkInMaps("staniloae6S", translatorBridge);
        checkInMaps("staniloae6T", translatorBridge);
        checkInMaps("staniloae6U", translatorBridge);
        checkInMaps("staniloae6V", translatorBridge);
        checkInMaps("staniloae6W", translatorBridge);
        //checkInMaps("staniloae6X1", translatorBridge);
        checkInMaps("staniloae6X2", translatorBridge);
        checkInMaps("staniloae6Y", translatorBridge);
        checkInMaps("staniloae6Z", translatorBridge);
    }

    @Test
    public void test_staniloae_ch1_point7() {
        //checkInMaps("staniloae7A", translatorBridge);
        checkInMaps("staniloae7B", translatorBridge);
        checkInMaps("staniloae7C", translatorBridge);
        checkInMaps("staniloae7D", translatorBridge);
        checkInMaps("staniloae7E", translatorBridge);
        checkInMaps("staniloae7F", translatorBridge);
        checkInMaps("staniloae7G", translatorBridge);
        checkInMaps("staniloae7H", translatorBridge);
        checkInMaps("staniloae7I", translatorBridge);
        checkInMaps("staniloae7J", translatorBridge);
        checkInMaps("staniloae7K", translatorBridge);
        checkInMaps("staniloae7L", translatorBridge);
        checkInMaps("staniloae7M", translatorBridge);
        checkInMaps("staniloae7N", translatorBridge);
        checkInMaps("staniloae7O", translatorBridge);
        checkInMaps("staniloae7P", translatorBridge);
        checkInMaps("staniloae7Q", translatorBridge);
        checkInMaps("staniloae7R", translatorBridge);
        //checkInMaps("staniloae7S", translatorBridge);
        checkInMaps("staniloae7T", translatorBridge);
        checkInMaps("staniloae7U", translatorBridge);
        checkInMaps("staniloae7V", translatorBridge);
        checkInMaps("staniloae7W", translatorBridge);
        checkInMaps("staniloae7X", translatorBridge);
        checkInMaps("staniloae7Y", translatorBridge);
        checkInMaps("staniloae7Z", translatorBridge);
        checkInMaps("staniloae7AA", translatorBridge);
        checkInMaps("staniloae7AB", translatorBridge);
        checkInMaps("staniloae7AC", translatorBridge);
        checkInMaps("staniloae7AD", translatorBridge);
        checkInMaps("staniloae7AE", translatorBridge);
        checkInMaps("staniloae7AF", translatorBridge);
        checkInMaps("staniloae7AG", translatorBridge);
        checkInMaps("staniloae7AH", translatorBridge);
        checkInMaps("staniloae7AI", translatorBridge);
        checkInMaps("staniloae7AJ", translatorBridge);
        //checkInMaps("staniloae7AK", translatorBridge);
    }

    @Test
    public void test_staniloae_ch1_point8() {
        checkInMaps("staniloae8A01", translatorBridge);
        checkInMaps("staniloae8A02", translatorBridge);
        checkInMaps("staniloae8A03", translatorBridge);
        checkInMaps("staniloae8A04", translatorBridge);
        checkInMaps("staniloae8A05", translatorBridge);
        checkInMaps("staniloae8A06", translatorBridge);
        checkInMaps("staniloae8A07", translatorBridge);
        checkInMaps("staniloae8A08", translatorBridge);
        checkInMaps("staniloae8A09", translatorBridge);
        checkInMaps("staniloae8A10", translatorBridge);
        checkInMaps("staniloae8A11", translatorBridge);
        checkInMaps("staniloae8A12", translatorBridge);
        checkInMaps("staniloae8A13", translatorBridge);
        checkInMaps("staniloae8A14", translatorBridge);
        //checkInMaps("staniloae8A15", translatorBridge);
        checkInMaps("staniloae8A16", translatorBridge);
        checkInMaps("staniloae8A17", translatorBridge);
        checkInMaps("staniloae8A18", translatorBridge);
        checkInMaps("staniloae8A19", translatorBridge);
        checkInMaps("staniloae8A20", translatorBridge);
        checkInMaps("staniloae8A21", translatorBridge);
        checkInMaps("staniloae8A22", translatorBridge);
        checkInMaps("staniloae8A23", translatorBridge);
        checkInMaps("staniloae8A24", translatorBridge);
        checkInMaps("staniloae8A25", translatorBridge);
        checkInMaps("staniloae8A26", translatorBridge);
        //checkInMaps("staniloae8A27", translatorBridge);
        checkInMaps("staniloae8A28", translatorBridge);
        //checkInMaps("staniloae8A29", translatorBridge);
        //checkInMaps("staniloae8A30", translatorBridge);
        //checkInMaps("staniloae8A31", translatorBridge);
    }

    @Test
    public void test_staniloae_ch1_point9() {
        checkInMaps("staniloae9A001", translatorBridge);
        checkInMaps("staniloae9A002", translatorBridge);
        checkInMaps("staniloae9A003", translatorBridge);
        checkInMaps("staniloae9A004", translatorBridge);
        checkInMaps("staniloae9A005", translatorBridge);
        checkInMaps("staniloae9A006", translatorBridge);
        checkInMaps("staniloae9A007", translatorBridge);
        checkInMaps("staniloae9A008", translatorBridge);
        checkInMaps("staniloae9A009", translatorBridge);
        checkInMaps("staniloae9A010", translatorBridge);
        checkInMaps("staniloae9A011", translatorBridge);
        checkInMaps("staniloae9A012", translatorBridge);
        checkInMaps("staniloae9A013", translatorBridge);
        checkInMaps("staniloae9A014", translatorBridge);
        checkInMaps("staniloae9A015", translatorBridge);
        checkInMaps("staniloae9A016", translatorBridge);
        checkInMaps("staniloae9A017", translatorBridge);
        checkInMaps("staniloae9A018", translatorBridge);
        checkInMaps("staniloae9A019", translatorBridge);
        checkInMaps("staniloae9A020", translatorBridge);
        checkInMaps("staniloae9A021", translatorBridge);
        checkInMaps("staniloae9A022", translatorBridge);
        checkInMaps("staniloae9A023", translatorBridge);
        checkInMaps("staniloae9A024", translatorBridge);
        checkInMaps("staniloae9A025", translatorBridge);
        checkInMaps("staniloae9A026", translatorBridge);
        checkInMaps("staniloae9A027", translatorBridge);
        checkInMaps("staniloae9A028", translatorBridge);
        checkInMaps("staniloae9A029", translatorBridge);
        checkInMaps("staniloae9A030", translatorBridge);
        checkInMaps("staniloae9A031", translatorBridge);
        checkInMaps("staniloae9A032", translatorBridge);
        checkInMaps("staniloae9A033", translatorBridge);
        checkInMaps("staniloae9A034", translatorBridge);
        //checkInMaps("staniloae9A035", translatorBridge);
        checkInMaps("staniloae9A036", translatorBridge);
        checkInMaps("staniloae9A037", translatorBridge);
        checkInMaps("staniloae9A038", translatorBridge);
    }

    @Test
    public void test_staniloae_chap1point10() {
        checkInMaps("staniloaeP19P1A", translatorBridge);
        checkInMaps("staniloaeP19P1B", translatorBridge);
        checkInMaps("staniloaeP19P1C", translatorBridge);
        checkInMaps("staniloaeP19P1D", translatorBridge);
        checkInMaps("staniloaeP19P1E", translatorBridge);
        checkInMaps("staniloaeP19P2A", translatorBridge);
        checkInMaps("staniloaeP19P2B", translatorBridge);
        checkInMaps("staniloaeP19P2C", translatorBridge);
        checkInMaps("staniloaeP19P2D", translatorBridge);
        checkInMaps("staniloaeP19P2E", translatorBridge);
        checkInMaps("staniloaeP19P2F", translatorBridge);
        checkInMaps("staniloaeP19P2G", translatorBridge);
        checkInMaps("staniloaeP19P2H", translatorBridge);
        checkInMaps("staniloaeP19P2I", translatorBridge);
        checkInMaps("staniloaeP19P2J", translatorBridge);
        checkInMaps("staniloaeP19P2K", translatorBridge);
        checkInMaps("staniloaeP19P2L", translatorBridge);
        checkInMaps("staniloaeP19P2M", translatorBridge);
        checkInMaps("staniloaeP19P2N", translatorBridge);
        checkInMaps("staniloaeP19P2O", translatorBridge);
        checkInMaps("staniloaeP19P3A", translatorBridge);
        checkInMaps("staniloaeP19P3B", translatorBridge);
        checkInMaps("staniloaeP19P3C", translatorBridge);
        //checkInMaps("staniloaeP19P3D", translatorBridge);
        checkInMaps("staniloaeP19P3E", translatorBridge);
        //checkInMaps("staniloaeP19P3F", translatorBridge);
        checkInMaps("staniloaeP19P3G", translatorBridge);
        checkInMaps("staniloaeP19P3H", translatorBridge);
        checkInMaps("staniloaeP19P3I", translatorBridge);
        checkInMaps("staniloaeP19P3J", translatorBridge);
        checkInMaps("staniloaeP19P3K", translatorBridge);
        checkInMaps("staniloaeP19P3L", translatorBridge);
        checkInMaps("staniloaeP19P3M", translatorBridge);
        checkInMaps("staniloaeP20P2A", translatorBridge);
        checkInMaps("staniloaeP20P2B", translatorBridge);
        checkInMaps("staniloaeP20P2C", translatorBridge);
    }

    @Test
    public void test_trone_de_fer_chapter1() {
        checkInMaps("urzeala1A", translatorBridge);
        checkInMaps("urzeala1B", translatorBridge);
        checkInMaps("urzeala1C", translatorBridge);
        checkInMaps("urzeala1D", translatorBridge);
        checkInMaps("urzeala1E", translatorBridge);
        checkInMaps("urzeala1F", translatorBridge);
        //checkInMaps("urzeala1G", translatorBridge);
        checkInMaps("urzeala1H", translatorBridge);
        checkInMaps("urzeala1I1", translatorBridge);
        checkInMaps("urzeala1I2", translatorBridge);
        //checkInMaps("urzeala1I3", translatorBridge);
        checkInMaps("urzeala1J", translatorBridge);
        checkInMaps("urzeala1K", translatorBridge);
        checkInMaps("urzeala1L", translatorBridge);
        checkInMaps("urzeala2A", translatorBridge);
        //checkInMaps("urzeala2B", translatorBridge);
        checkInMaps("urzeala2C", translatorBridge);
        //checkInMaps("urzeala2D", translatorBridge);
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
        checkInMaps("filocalia1antonie2A", translatorBridge);
        checkInMaps("filocalia1antonie2B", translatorBridge);
        checkInMaps("filocalia1antonie2C", translatorBridge);
        checkInMaps("filocalia1antonie2D", translatorBridge);
        checkInMaps("filocalia1antonie2E", translatorBridge);
        checkInMaps("filocalia1antonie2F", translatorBridge);
        checkInMaps("filocalia1antonie2G", translatorBridge);
        checkInMaps("filocalia1antonie2H", translatorBridge);
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
    public void test_boca1() {
        //checkInMaps("bocaseumplu7A", translatorBridge);
        //checkInMaps("bocaseumplu7B", translatorBridge);
        checkInMaps("bocaseumplu7C", translatorBridge);
        checkInMaps("bocaseumplu7D", translatorBridge);
        checkInMaps("bocaseumplu7E", translatorBridge);
        //checkInMaps("bocaseumplu7F", translatorBridge);
        //checkInMaps("bocaseumplu7G", translatorBridge);
        //checkInMaps("bocaseumplu7H", translatorBridge);
        checkInMaps("bocaseumplu7I", translatorBridge);
        checkInMaps("bocaseumplu7J", translatorBridge);
        checkInMaps("bocaseumplu7K", translatorBridge);
        checkInMaps("bocaseumplu7L", translatorBridge);
        checkInMaps("bocaseumplu7M", translatorBridge);
        //checkInMaps("bocaseumplu7N", translatorBridge);
        checkInMaps("bocaseumplu7O", translatorBridge);
        checkInMaps("bocaseumplu7P", translatorBridge);
        checkInMaps("bocaseumplu7Q", translatorBridge);
        checkInMaps("bocaseumplu7R", translatorBridge);
        checkInMaps("bocaseumplu7S", translatorBridge);
        checkInMaps("bocaseumplu7T", translatorBridge);
        //checkInMaps("bocaseumplu7U", translatorBridge);
        checkInMaps("bocaseumplu7V", translatorBridge);
        checkInMaps("bocaseumplu7W", translatorBridge);
        checkInMaps("bocaseumplu7X", translatorBridge);
        //checkInMaps("bocaseumplu7Y", translatorBridge);
        //checkInMaps("bocaseumplu7Z", translatorBridge);
        checkInMaps("bocaseumplu7AA", translatorBridge);
        checkInMaps("bocaseumplu7BB", translatorBridge);
        checkInMaps("bocaseumplu7CC", translatorBridge);
    }

    @Test
    public void test_boca2() {
        checkInMaps("bocaseumplu801", translatorBridge);
        checkInMaps("bocaseumplu802", translatorBridge);
        checkInMaps("bocaseumplu803", translatorBridge);
        checkInMaps("bocaseumplu804", translatorBridge);
        checkInMaps("bocaseumplu805", translatorBridge);
        checkInMaps("bocaseumplu806", translatorBridge);
        checkInMaps("bocaseumplu807", translatorBridge);
        checkInMaps("bocaseumplu808", translatorBridge);
        checkInMaps("bocaseumplu809", translatorBridge);
        checkInMaps("bocaseumplu810", translatorBridge);
        checkInMaps("bocaseumplu811", translatorBridge);
        checkInMaps("bocaseumplu812", translatorBridge);
        checkInMaps("bocaseumplu813", translatorBridge);
        checkInMaps("bocaseumplu814", translatorBridge);
        checkInMaps("bocaseumplu815", translatorBridge);
        checkInMaps("bocaseumplu816", translatorBridge);
        checkInMaps("bocaseumplu817", translatorBridge);
        checkInMaps("bocaseumplu818", translatorBridge);
        checkInMaps("bocaseumplu819", translatorBridge);
        checkInMaps("bocaseumplu820", translatorBridge);
        checkInMaps("bocaseumplu821", translatorBridge);
        checkInMaps("bocaseumplu822", translatorBridge);
        checkInMaps("bocaseumplu823", translatorBridge);
        checkInMaps("bocaseumplu824", translatorBridge);
        checkInMaps("bocaseumplu825", translatorBridge);
        checkInMaps("bocaseumplu826", translatorBridge);
        checkInMaps("bocaseumplu827", translatorBridge);
        checkInMaps("bocaseumplu828", translatorBridge);
        checkInMaps("bocaseumplu829", translatorBridge);
        checkInMaps("bocaseumplu830", translatorBridge);
        checkInMaps("bocaseumplu831", translatorBridge);
        checkInMaps("bocaseumplu832", translatorBridge);
        checkInMaps("bocaseumplu833", translatorBridge);
        checkInMaps("bocaseumplu834", translatorBridge);
        checkInMaps("bocaseumplu835", translatorBridge);
        //checkInMaps("bocaseumplu836", translatorBridge);
        checkInMaps("bocaseumplu837", translatorBridge);
        checkInMaps("bocaseumplu838", translatorBridge);
        checkInMaps("bocaseumplu839", translatorBridge);
        checkInMaps("bocaseumplu840", translatorBridge);
        checkInMaps("bocaseumplu841", translatorBridge);
        checkInMaps("bocaseumplu842", translatorBridge);
        checkInMaps("bocaseumplu843", translatorBridge);
        checkInMaps("bocaseumplu844", translatorBridge);
        checkInMaps("bocaseumplu845", translatorBridge);
        checkInMaps("bocaseumplu846", translatorBridge);
    }

    @Test
    public void test_boca3() {
        checkInMaps("bocaseumplu900", translatorBridge);
        checkInMaps("bocaseumplu901", translatorBridge);
        checkInMaps("bocaseumplu902", translatorBridge);
        checkInMaps("bocaseumplu903", translatorBridge);
        checkInMaps("bocaseumplu904", translatorBridge);
        checkInMaps("bocaseumplu905", translatorBridge);
        checkInMaps("bocaseumplu906", translatorBridge);
        //checkInMaps("bocaseumplu907", translatorBridge);
        checkInMaps("bocaseumplu908", translatorBridge);
        checkInMaps("bocaseumplu909", translatorBridge);
        //checkInMaps("bocaseumplu910", translatorBridge);
        checkInMaps("bocaseumplu911", translatorBridge);
        checkInMaps("bocaseumplu912", translatorBridge);
        checkInMaps("bocaseumplu913", translatorBridge);
        checkInMaps("bocaseumplu914", translatorBridge);
        checkInMaps("bocaseumplu915", translatorBridge);
        checkInMaps("bocaseumplu916", translatorBridge);
        checkInMaps("bocaseumplu917", translatorBridge);
        checkInMaps("bocaseumplu918", translatorBridge);
        checkInMaps("bocaseumplu919", translatorBridge);
        checkInMaps("bocaseumplu920", translatorBridge);
        checkInMaps("bocaseumplu921", translatorBridge);
        checkInMaps("bocaseumplu922", translatorBridge);
        checkInMaps("bocaseumplu923", translatorBridge);
        checkInMaps("bocaseumplu924", translatorBridge);
        checkInMaps("bocaseumplu925", translatorBridge);
        checkInMaps("bocaseumplu926", translatorBridge);
        checkInMaps("bocaseumplu927", translatorBridge);
        checkInMaps("bocaseumplu928", translatorBridge);
        checkInMaps("bocaseumplu929", translatorBridge);
        checkInMaps("bocaseumplu930", translatorBridge);
        checkInMaps("bocaseumplu931", translatorBridge);
        //checkInMaps("bocaseumplu932", translatorBridge);
        checkInMaps("bocaseumplu933", translatorBridge);
        checkInMaps("bocaseumplu934", translatorBridge);
        checkInMaps("bocaseumplu935", translatorBridge);
        checkInMaps("bocaseumplu936", translatorBridge);
        checkInMaps("bocaseumplu937", translatorBridge);
        checkInMaps("bocaseumplu938", translatorBridge);
        checkInMaps("bocaseumplu939", translatorBridge);
        checkInMaps("bocaseumplu940", translatorBridge);
        checkInMaps("bocaseumplu941", translatorBridge);
        checkInMaps("bocaseumplu942", translatorBridge);
        checkInMaps("bocaseumplu943", translatorBridge);
        checkInMaps("bocaseumplu944", translatorBridge);
        checkInMaps("bocaseumplu945", translatorBridge);
        checkInMaps("bocaseumplu946", translatorBridge);
        checkInMaps("bocaseumplu947", translatorBridge);
        checkInMaps("bocaseumplu948", translatorBridge);
        checkInMaps("bocaseumplu949", translatorBridge);
        checkInMaps("bocaseumplu950", translatorBridge);
        checkInMaps("bocaseumplu951", translatorBridge);
        //checkInMaps("bocaseumplu952", translatorBridge);
        checkInMaps("bocaseumplu953", translatorBridge);
        checkInMaps("bocaseumplu954", translatorBridge);
        checkInMaps("bocaseumplu955", translatorBridge);
        //checkInMaps("bocaseumplu956", translatorBridge);
        checkInMaps("bocaseumplu957", translatorBridge);
        checkInMaps("bocaseumplu958", translatorBridge);
        checkInMaps("bocaseumplu959", translatorBridge);
        checkInMaps("bocaseumplu960", translatorBridge);
        checkInMaps("bocaseumplu961", translatorBridge);
        checkInMaps("bocaseumplu962", translatorBridge);
        checkInMaps("bocaseumplu963", translatorBridge);
        checkInMaps("bocaseumplu964", translatorBridge);
        checkInMaps("bocaseumplu965", translatorBridge);
        checkInMaps("bocaseumplu966", translatorBridge);
        checkInMaps("bocaseumplu967", translatorBridge);
        checkInMaps("bocaseumplu968", translatorBridge);
        checkInMaps("bocaseumplu969", translatorBridge);
        checkInMaps("bocaseumplu970", translatorBridge);
        //checkInMaps("bocaseumplu971", translatorBridge);
        //checkInMaps("bocaseumplu972", translatorBridge);
        checkInMaps("bocaseumplu973", translatorBridge);
        checkInMaps("bocaseumplu974", translatorBridge);
        //checkInMaps("bocaseumplu975", translatorBridge);
        //checkInMaps("bocaseumplu976", translatorBridge);
        checkInMaps("bocaseumplu977", translatorBridge);
        checkInMaps("bocaseumplu978", translatorBridge);
        //checkInMaps("bocaseumplu979", translatorBridge);
        checkInMaps("bocaseumplu980", translatorBridge);
        //checkInMaps("bocaseumplu981", translatorBridge);
        checkInMaps("bocaseumplu982", translatorBridge);
        checkInMaps("bocaseumplu983", translatorBridge);
        checkInMaps("bocaseumplu984", translatorBridge);
        //checkInMaps("bocaseumplu985", translatorBridge);
        checkInMaps("bocaseumplu986", translatorBridge);
        checkInMaps("bocaseumplu987", translatorBridge);
        checkInMaps("bocaseumplu988", translatorBridge);
        checkInMaps("bocaseumplu989", translatorBridge);
        checkInMaps("bocaseumplu990", translatorBridge);
        checkInMaps("bocaseumplu991", translatorBridge);
        checkInMaps("bocaseumplu992", translatorBridge);
        checkInMaps("bocaseumplu993", translatorBridge);
        checkInMaps("bocaseumplu994", translatorBridge);
        checkInMaps("bocaseumplu995", translatorBridge);
        checkInMaps("bocaseumplu996", translatorBridge);
        checkInMaps("bocaseumplu997", translatorBridge);
        checkInMaps("bocaseumplu998", translatorBridge);
        checkInMaps("bocaseumplu999", translatorBridge);
        checkInMaps("bocaseumplu99A", translatorBridge);
        //checkInMaps("bocaseumplu99B", translatorBridge);
        checkInMaps("bocaseumplu99C", translatorBridge);
        checkInMaps("bocaseumplu99D", translatorBridge);
        checkInMaps("bocaseumplu99E", translatorBridge);
        //checkInMaps("bocaseumplu99F", translatorBridge);
        checkInMaps("bocaseumplu99G", translatorBridge);
        checkInMaps("bocaseumplu99H", translatorBridge);
        checkInMaps("bocaseumplu99I", translatorBridge);
        checkInMaps("bocaseumplu99J", translatorBridge);
        checkInMaps("bocaseumplu99K", translatorBridge);
        //checkInMaps("bocaseumplu99L", translatorBridge);
    }

    @Test
    public void test_boca4() {
        checkInMaps("bocaseumpluA001", translatorBridge);
        //checkInMaps("bocaseumpluA003", translatorBridge);
        checkInMaps("bocaseumpluA004", translatorBridge);
        checkInMaps("bocaseumpluA005", translatorBridge);
        checkInMaps("bocaseumpluA006", translatorBridge);
        checkInMaps("bocaseumpluA007", translatorBridge);
        checkInMaps("bocaseumpluA008", translatorBridge);
        checkInMaps("bocaseumpluA009", translatorBridge);
        checkInMaps("bocaseumpluA100", translatorBridge);
        checkInMaps("bocaseumpluA101", translatorBridge);
        checkInMaps("bocaseumpluA102", translatorBridge);
        checkInMaps("bocaseumpluA103", translatorBridge);
        checkInMaps("bocaseumpluA104", translatorBridge);
        checkInMaps("bocaseumpluA105", translatorBridge);
        checkInMaps("bocaseumpluA106", translatorBridge);
        checkInMaps("bocaseumpluA107", translatorBridge);
        checkInMaps("bocaseumpluA108", translatorBridge);
        checkInMaps("bocaseumpluA109", translatorBridge);
        checkInMaps("bocaseumpluA200", translatorBridge);
        checkInMaps("bocaseumpluA201", translatorBridge);
        checkInMaps("bocaseumpluA202", translatorBridge);
        checkInMaps("bocaseumpluA203", translatorBridge);
        //checkInMaps("bocaseumpluA204", translatorBridge);
        //checkInMaps("bocaseumpluA205", translatorBridge);
        checkInMaps("bocaseumpluA206", translatorBridge);
        checkInMaps("bocaseumpluA207", translatorBridge);
        checkInMaps("bocaseumpluA208", translatorBridge);
        checkInMaps("bocaseumpluA209", translatorBridge);
        checkInMaps("bocaseumpluA300", translatorBridge);
        checkInMaps("bocaseumpluA301", translatorBridge);
        checkInMaps("bocaseumpluA302", translatorBridge);
        checkInMaps("bocaseumpluA303", translatorBridge);
        checkInMaps("bocaseumpluA304", translatorBridge);
        checkInMaps("bocaseumpluA305", translatorBridge);
        //checkInMaps("bocaseumpluA306", translatorBridge);
        checkInMaps("bocaseumpluA307", translatorBridge);
        checkInMaps("bocaseumpluA308", translatorBridge);
        checkInMaps("bocaseumpluA309", translatorBridge);
        //checkInMaps("bocaseumpluA400", translatorBridge);
        checkInMaps("bocaseumpluA401", translatorBridge);
        checkInMaps("bocaseumpluA402", translatorBridge);
        //checkInMaps("bocaseumpluA403A", translatorBridge);
        checkInMaps("bocaseumpluA403B", translatorBridge);
        checkInMaps("bocaseumpluA404", translatorBridge);
        checkInMaps("bocaseumpluA405", translatorBridge);
        checkInMaps("bocaseumpluA406", translatorBridge);
        checkInMaps("bocaseumpluA407", translatorBridge);
        checkInMaps("bocaseumpluA408", translatorBridge);
        //checkInMaps("bocaseumpluA409", translatorBridge);
        checkInMaps("bocaseumpluA500", translatorBridge);
        //checkInMaps("bocaseumpluA501", translatorBridge);
        checkInMaps("bocaseumpluA502", translatorBridge);
        checkInMaps("bocaseumpluA503", translatorBridge);
        checkInMaps("bocaseumpluA504", translatorBridge);
        checkInMaps("bocaseumpluA505", translatorBridge);
        checkInMaps("bocaseumpluA506", translatorBridge);
        checkInMaps("bocaseumpluA507", translatorBridge);
        checkInMaps("bocaseumpluA508", translatorBridge);
        checkInMaps("bocaseumpluA509", translatorBridge);
        checkInMaps("bocaseumpluA600", translatorBridge);
        checkInMaps("bocaseumpluA601", translatorBridge);
        checkInMaps("bocaseumpluA602", translatorBridge);
        checkInMaps("bocaseumpluA603", translatorBridge);
        checkInMaps("bocaseumpluA604", translatorBridge);
        //checkInMaps("bocaseumpluA605", translatorBridge);
        checkInMaps("bocaseumpluA606", translatorBridge);
        checkInMaps("bocaseumpluA607", translatorBridge);
        checkInMaps("bocaseumpluA608", translatorBridge);
        //checkInMaps("bocaseumpluA609", translatorBridge);
        checkInMaps("bocaseumpluA700", translatorBridge);
        checkInMaps("bocaseumpluA701", translatorBridge);
        checkInMaps("bocaseumpluA702", translatorBridge);
        checkInMaps("bocaseumpluA703", translatorBridge);
        checkInMaps("bocaseumpluA704", translatorBridge);
        //checkInMaps("bocaseumpluA705", translatorBridge);
        checkInMaps("bocaseumpluA706", translatorBridge);
        checkInMaps("bocaseumpluA707", translatorBridge);
        checkInMaps("bocaseumpluA708", translatorBridge);
        checkInMaps("bocaseumpluA709", translatorBridge);
        checkInMaps("bocaseumpluA800", translatorBridge);
        checkInMaps("bocaseumpluA801", translatorBridge);
        checkInMaps("bocaseumpluA802", translatorBridge);
        checkInMaps("bocaseumpluA803", translatorBridge);
        checkInMaps("bocaseumpluA804", translatorBridge);
        checkInMaps("bocaseumpluA805", translatorBridge);
        checkInMaps("bocaseumpluA806", translatorBridge);
        checkInMaps("bocaseumpluA807", translatorBridge);
        checkInMaps("bocaseumpluA808", translatorBridge);
        checkInMaps("bocaseumpluA809", translatorBridge);
        checkInMaps("bocaseumpluA900", translatorBridge);
        checkInMaps("bocaseumpluA901", translatorBridge);
        checkInMaps("bocaseumpluA902", translatorBridge);
        checkInMaps("bocaseumpluA903", translatorBridge);
        checkInMaps("bocaseumpluA904", translatorBridge);
        checkInMaps("bocaseumpluA905", translatorBridge);
        checkInMaps("bocaseumpluA906", translatorBridge);
        checkInMaps("bocaseumpluA907", translatorBridge);
        checkInMaps("bocaseumpluA908", translatorBridge);
        checkInMaps("bocaseumpluA909", translatorBridge);
        checkInMaps("bocaseumpluA110", translatorBridge);
        checkInMaps("bocaseumpluA111", translatorBridge);
        checkInMaps("bocaseumpluA112", translatorBridge);
        checkInMaps("bocaseumpluA113", translatorBridge);
        checkInMaps("bocaseumpluA114", translatorBridge);
        checkInMaps("bocaseumpluA115", translatorBridge);
        checkInMaps("bocaseumpluA116", translatorBridge);
        checkInMaps("bocaseumpluA117", translatorBridge);
        checkInMaps("bocaseumpluA118", translatorBridge);
        checkInMaps("bocaseumpluA119", translatorBridge);
        checkInMaps("bocaseumpluA120", translatorBridge);
        checkInMaps("bocaseumpluA121", translatorBridge);
        checkInMaps("bocaseumpluA122", translatorBridge);
        checkInMaps("bocaseumpluA123", translatorBridge);
        checkInMaps("bocaseumpluA124", translatorBridge);
        checkInMaps("bocaseumpluA125", translatorBridge);
        checkInMaps("bocaseumpluA126", translatorBridge);
        checkInMaps("bocaseumpluA127", translatorBridge);
        checkInMaps("bocaseumpluA128", translatorBridge);
        checkInMaps("bocaseumpluA129", translatorBridge);
        checkInMaps("bocaseumpluA130", translatorBridge);
        checkInMaps("bocaseumpluA131", translatorBridge);
        checkInMaps("bocaseumpluA132", translatorBridge);
        checkInMaps("bocaseumpluA133", translatorBridge);
        checkInMaps("bocaseumpluA134", translatorBridge);
        checkInMaps("bocaseumpluA135", translatorBridge);
        checkInMaps("bocaseumpluA136", translatorBridge);
        checkInMaps("bocaseumpluA137", translatorBridge);
        checkInMaps("bocaseumpluA138", translatorBridge);
        checkInMaps("bocaseumpluA139", translatorBridge);
        checkInMaps("bocaseumpluA140", translatorBridge);
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
    public void test_patericul2() {
        //checkInMaps("patericulegyptantonia2A", translatorBridge);
        checkInMaps("patericulegyptantonia2B", translatorBridge);
        checkInMaps("patericulegyptantonia2C", translatorBridge);
        checkInMaps("patericulegyptantonia2D", translatorBridge);
        //checkInMaps("patericulegyptantonia2E", translatorBridge);
        checkInMaps("patericulegyptantonia2F", translatorBridge);
        //checkInMaps("patericulegyptantonia2G", translatorBridge);
        checkInMaps("patericulegyptantonia2H", translatorBridge);
        checkInMaps("patericulegyptantonia2I", translatorBridge);
        //checkInMaps("patericulegyptantonia2J", translatorBridge);
    }

    @Test
    public void test_patericul3() {
        checkInMaps("patericulegyptantonia3A", translatorBridge);
        checkInMaps("patericulegyptantonia3B", translatorBridge);
        checkInMaps("patericulegyptantonia3C", translatorBridge);
        checkInMaps("patericulegyptantonia3D", translatorBridge);
        checkInMaps("patericulegyptantonia3E", translatorBridge);
        checkInMaps("patericulegyptantonia3F", translatorBridge);
    }

    @Test
    public void test_patericul4() {
        checkInMaps("patericulegyptantonia4A", translatorBridge);
        checkInMaps("patericulegyptantonia4B", translatorBridge);
        checkInMaps("patericulegyptantonia4C", translatorBridge);
        checkInMaps("patericulegyptantonia4D", translatorBridge);
        checkInMaps("patericulegyptantonia4E", translatorBridge);
        //checkInMaps("patericulegyptantonia4F", translatorBridge);
        checkInMaps("patericulegyptantonia4G", translatorBridge);
    }

    @Test
    public void test_patericul5() {
        checkInMaps("patericulegyptantonia5A", translatorBridge);
        checkInMaps("patericulegyptantonia5B", translatorBridge);
        checkInMaps("patericulegyptantonia5C", translatorBridge);
        checkInMaps("patericulegyptantonia5D", translatorBridge);
        checkInMaps("patericulegyptantonia5E", translatorBridge);
        checkInMaps("patericulegyptantonia5F", translatorBridge);
        checkInMaps("patericulegyptantonia5G", translatorBridge);
        checkInMaps("patericulegyptantonia5H", translatorBridge);
    }

    @Test
    public void test_patericul6() {
        checkInMaps("patericulegyptantonia6A", translatorBridge);
        checkInMaps("patericulegyptantonia6B", translatorBridge);
        checkInMaps("patericulegyptantonia6C", translatorBridge);
        checkInMaps("patericulegyptantonia6D", translatorBridge);
    }

        @Test
    public void test_staniloae_despre_botezul() {
        checkInMaps("stanibotez01", translatorBridge);
        checkInMaps("stanibotez02", translatorBridge);
        checkInMaps("stanibotez03", translatorBridge);
        checkInMaps("stanibotez04", translatorBridge);
        checkInMaps("stanibotez05", translatorBridge);
        checkInMaps("stanibotez06", translatorBridge);
        checkInMaps("stanibotez07", translatorBridge);
        checkInMaps("stanibotez08", translatorBridge);
        checkInMaps("stanibotez09", translatorBridge);
        checkInMaps("stanibotez10", translatorBridge);
        checkInMaps("stanibotez11", translatorBridge);
        checkInMaps("stanibotez12", translatorBridge);
        checkInMaps("stanibotez13", translatorBridge);
        checkInMaps("stanibotez14", translatorBridge);
        checkInMaps("stanibotez15", translatorBridge);
        checkInMaps("stanibotez16", translatorBridge);
        checkInMaps("stanibotez17", translatorBridge);
        checkInMaps("stanibotez18", translatorBridge);
        checkInMaps("stanibotez19", translatorBridge);
        checkInMaps("stanibotez20", translatorBridge);
        checkInMaps("stanibotez21", translatorBridge);
        checkInMaps("stanibotez22", translatorBridge);
    }

    @Test
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
    }

}

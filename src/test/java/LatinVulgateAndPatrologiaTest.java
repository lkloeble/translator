import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.DummyAccentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.latin.LatinCaseFactory;
import patrologia.translator.conjugation.latin.LatinConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.latin.LatinDeclension;
import patrologia.translator.declension.latin.LatinDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.LatinAnalyzer;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.latin.LatinRuleFactory;
import patrologia.translator.utils.Analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lkloeble on 02/01/2017.
 */
public class LatinVulgateAndPatrologiaTest extends LatinTranslatorBridgeTest {

    protected TranslatorBridge translatorBridge;

    private String localTestPath="C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath="C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\latin\\";
    private String localCommonPath="C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\";

    @Before
    public void init() {
        String prepositionFileDescription = localResourcesPath + "prepositions.txt";
        String nounFileDescription = localResourcesPath + "nouns.txt";
        String verbFileDescription = localResourcesPath + "verbs.txt";
        String latinFrenchDataFile = localResourcesPath + "gaffiot_latin_to_french.txt";
        String frenchVerbsDataFile = localCommonPath + "french_verbs.txt";
        String declensionLatinFiles = localResourcesPath + "declensions";
        String conjugationLatinFiles = localResourcesPath + "conjugations";
        String declensionsAndFiles = localResourcesPath + "declensionsAndFiles.txt";
        String conjugationsAndFiles = localResourcesPath + "conjugationsAndFiles.txt";
        String latinPathFile = localTestPath + "vulgate_patrologia_content.txt";
        String latinResultFile = localTestPath + "vulgate_patrologia_expected_result.txt";
        LatinDeclensionFactory latinDeclensionFactory = new LatinDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionLatinFiles));
        LatinRuleFactory ruleFactory = new LatinRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.LATIN, new LatinCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        NounRepository nounRepository = new NounRepository(Language.LATIN, latinDeclensionFactory, new DummyAccentuer(),getNouns(nounFileDescription));
        VerbRepository2 verbRepository = new VerbRepository2(new LatinConjugationFactory(getLatinConjugations(conjugationsAndFiles), getLatinConjugationDefinitions(conjugationsAndFiles, conjugationLatinFiles),latinDeclensionFactory), Language.LATIN, new DummyAccentuer(),getVerbs(verbFileDescription));
        Analyzer latinAnalyzer = new LatinAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getFrenchDictionary(latinFrenchDataFile), getFileContentForRepository(frenchVerbsDataFile), verbRepository, nounRepository, declensionLatinFiles, declensionsAndFiles, latinDeclensionFactory);
        translatorBridge = new TranslatorBridge(latinAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(latinPathFile);
        mapValuesForResult = loadMapFromFiles(latinResultFile);
    }

    private List<String> getFrenchDictionary(String latinFrenchDataFile) {
        /*
        return Arrays.asList(new String[]{
                "vir@noun!is-is%1(noun)=forces",
                "vir@noun!x(us)-i%1(noun)=homme (oppose a femme)%2(noun)=homme, mari, epoux%3(noun)=soldat%4(noun)=personnage, individu%5(noun)=homme"
        });
         */
        return getFileContentForRepository(latinFrenchDataFile);
    }

    private List<String> getVerbs(String verbFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "scrib,o,is,ere,,,[o-is],(AIP*scrib*scrips@IAP*scrib*scrips)"
        });
        */
        return getFileContentForRepository(verbFileDescription);
    }

    private List<Declension> getDeclensionList(String file, String directory) {
        List<String> declensionNameList = getFileContentForRepository(file);
        List<Declension> declensionList = new ArrayList<>();
        for (String declensionName : declensionNameList) {
            String parts[] = declensionName.split("%");
            String fileName = parts[1];
            //declensionList.add(new LatinDeclension(fileName, getDeclensionElements(fileName, directory), false));
            declensionList.add(new LatinDeclension(fileName, getDeclensionElements(fileName, directory)));
        }
        return declensionList;
    }

    private List<String> getLatinConjugations(String conjugationsAndFiles) {
        /*
        return Arrays.asList(new String[]{
                "o-is%permittere.txt"
        });
        */
        return getFileContentForRepository(conjugationsAndFiles);
    }

    private Map<String, List<String>> getLatinConjugationDefinitions(String file, String directory) {
        /*
        Map<String, List<String>> latinConjugationDefinitionsMap = new HashMap<>();
        latinConjugationDefinitionsMap.put("io-is", Arrays.asList("IPR=>io,is,it,imus,itis,iunt","AIF=>iam,ies,iet,iemus,ietis,ient"));
        return latinConjugationDefinitionsMap;
        */
        List<String> conjugationNameList = getFileContentForRepository(file);
        Map<String, List<String>> latinConjugationDefinitionsMap = new HashMap<>();
        for(String conjugationName : conjugationNameList) {
            String parts[] = conjugationName.split("%");
            String fileName = parts[1];
            String nameOnly = parts[0];
            latinConjugationDefinitionsMap.put(nameOnly, getConjugationElements(directory,fileName));
        }
        return latinConjugationDefinitionsMap;
    }

    private List<String> getNouns(String nounFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "vir@fem%is-is",
                "vir@masc%x(us)-i"
        });
         */
        return getFileContentForRepository(nounFileDescription);
    }


    @Test
    public void test_vocabulary_vulgate_ch01() {
        checkInMaps("genese1A", translatorBridge);
        checkInMaps("genese1B", translatorBridge);
        checkInMaps("genese1C", translatorBridge);
        checkInMaps("genese1D", translatorBridge);
        checkInMaps("genese1E", translatorBridge);
        checkInMaps("genese1F", translatorBridge);
        checkInMaps("genese1G", translatorBridge);
        checkInMaps("genese1H", translatorBridge);
        checkInMaps("genese1I", translatorBridge);
        checkInMaps("genese1J", translatorBridge);
        checkInMaps("genese1K", translatorBridge);
        checkInMaps("genese1L", translatorBridge);
        checkInMaps("genese1M", translatorBridge);
        checkInMaps("genese1N", translatorBridge);
        checkInMaps("genese1O", translatorBridge);
        checkInMaps("genese1P", translatorBridge);
        checkInMaps("genese1Q", translatorBridge);
        checkInMaps("genese1R", translatorBridge);
        checkInMaps("genese1S", translatorBridge);
        checkInMaps("genese1T", translatorBridge);
        checkInMaps("genese1U", translatorBridge);
        checkInMaps("genese1V", translatorBridge);
        checkInMaps("genese1W", translatorBridge);
        checkInMaps("genese1X", translatorBridge);
        checkInMaps("genese1Y", translatorBridge);
        checkInMaps("genese1Z", translatorBridge);
        checkInMaps("genese1AA", translatorBridge);
        //checkInMaps("genese1BB", translatorBridge);
        //checkInMaps("genese1CC", translatorBridge);
        checkInMaps("genese1DD", translatorBridge);
        checkInMaps("genese1EE", translatorBridge);
    }

    @Test
    public void test_vocabulary_vulgate_ch02() {
        checkInMaps("genesis2A", translatorBridge);
        //checkInMaps("genesis2B", translatorBridge);
        checkInMaps("genesis2C", translatorBridge);
        checkInMaps("genesis2D", translatorBridge);
        checkInMaps("genesis2E", translatorBridge);
        checkInMaps("genesis2F", translatorBridge);
        checkInMaps("genesis2G", translatorBridge);
        checkInMaps("genesis2H", translatorBridge);
        checkInMaps("genesis2I", translatorBridge);
        checkInMaps("genesis2J", translatorBridge);
        checkInMaps("genesis2K", translatorBridge);
        checkInMaps("genesis2L", translatorBridge);
        checkInMaps("genesis2M", translatorBridge);
        checkInMaps("genesis2N", translatorBridge);
        checkInMaps("genesis2O", translatorBridge);
        checkInMaps("genesis2P", translatorBridge);
        checkInMaps("genesis2Q", translatorBridge);
        checkInMaps("genesis2R", translatorBridge);
        checkInMaps("genesis2S", translatorBridge);
        checkInMaps("genesis2T", translatorBridge);
        checkInMaps("genesis2U", translatorBridge);
        checkInMaps("genesis2V", translatorBridge);
        checkInMaps("genesis2W", translatorBridge);
        checkInMaps("genesis2X", translatorBridge);
        checkInMaps("genesis2Y", translatorBridge);
    }


    @Test
    public void patrologia_graeca_1_caput_primum() {
        checkInMaps("patrologiacaput1A", translatorBridge);
        checkInMaps("patrologiacaput1B", translatorBridge);
        checkInMaps("patrologiacaput1C", translatorBridge);
        checkInMaps("patrologiacaput1D", translatorBridge);
        checkInMaps("patrologiacaput1E", translatorBridge);
        checkInMaps("patrologiacaput1F", translatorBridge);
        checkInMaps("patrologiacaput1G", translatorBridge);
        checkInMaps("patrologiacaput1H", translatorBridge);
        checkInMaps("patrologiacaput1I", translatorBridge);
        checkInMaps("patrologiacaput1J", translatorBridge);
        checkInMaps("patrologiacaput1K", translatorBridge);
        checkInMaps("patrologiacaput1L", translatorBridge);
        //checkInMaps("patrologiacaput1M", translatorBridge);
        checkInMaps("patrologiacaput1N", translatorBridge);
        checkInMaps("patrologiacaput1O", translatorBridge);
        checkInMaps("patrologiacaput1P", translatorBridge);
        checkInMaps("patrologiacaput1Q", translatorBridge);
        checkInMaps("patrologiacaput1R", translatorBridge);
        checkInMaps("patrologiacaput1S", translatorBridge);
        checkInMaps("patrologiacaput1T", translatorBridge);
        //checkInMaps("patrologiacaput1U", translatorBridge);
        checkInMaps("patrologiacaput1V", translatorBridge);
        //checkInMaps("patrologiacaput1W", translatorBridge);
        checkInMaps("patrologiacaput1X", translatorBridge);
        //checkInMaps("patrologiacaput1Y", translatorBridge);
        //checkInMaps("patrologiacaput1Z", translatorBridge);
    }

    @Test
    public void patrologia_graeca_1_caput_2() {
        //checkInMaps("patrologiacaput2A", translatorBridge);
        //checkInMaps("patrologiacaput2B", translatorBridge);
        checkInMaps("patrologiacaput2C", translatorBridge);
        checkInMaps("patrologiacaput2D", translatorBridge);
        checkInMaps("patrologiacaput2E", translatorBridge);
        checkInMaps("patrologiacaput2F", translatorBridge);
        checkInMaps("patrologiacaput2G", translatorBridge);
        checkInMaps("patrologiacaput2H", translatorBridge);
        //checkInMaps("patrologiacaput2I", translatorBridge);
        checkInMaps("patrologiacaput2J", translatorBridge);
        checkInMaps("patrologiacaput2K", translatorBridge);
        checkInMaps("patrologiacaput2L", translatorBridge);
        checkInMaps("patrologiacaput2M", translatorBridge);
        checkInMaps("patrologiacaput2N", translatorBridge);
        checkInMaps("patrologiacaput2O", translatorBridge);
        checkInMaps("patrologiacaput2P", translatorBridge);
        checkInMaps("patrologiacaput2Q", translatorBridge);
        checkInMaps("patrologiacaput2R", translatorBridge);
        checkInMaps("patrologiacaput2S", translatorBridge);
        checkInMaps("patrologiacaput2T", translatorBridge);
        checkInMaps("patrologiacaput2U", translatorBridge);
        checkInMaps("patrologiacaput2V", translatorBridge);
        checkInMaps("patrologiacaput2W", translatorBridge);
        checkInMaps("patrologiacaput2X", translatorBridge);
        checkInMaps("patrologiacaput2Y", translatorBridge);
        checkInMaps("patrologiacaput2Z", translatorBridge);
    }

    @Test
    public void patrologia_graeca_1_caput_3() {
        checkInMaps("patrologiacaput3A01", translatorBridge);
        checkInMaps("patrologiacaput3A02", translatorBridge);
        checkInMaps("patrologiacaput3A03", translatorBridge);
        checkInMaps("patrologiacaput3A04", translatorBridge);
        checkInMaps("patrologiacaput3A05", translatorBridge);
        checkInMaps("patrologiacaput3A06", translatorBridge);
        checkInMaps("patrologiacaput3A07", translatorBridge);
        checkInMaps("patrologiacaput3A08", translatorBridge);
        checkInMaps("patrologiacaput3A09", translatorBridge);
        checkInMaps("patrologiacaput3A10", translatorBridge);
        checkInMaps("patrologiacaput3A11", translatorBridge);
        checkInMaps("patrologiacaput3A12", translatorBridge);
        checkInMaps("patrologiacaput3A13", translatorBridge);
        //checkInMaps("patrologiacaput3A14", translatorBridge);
        checkInMaps("patrologiacaput3A15", translatorBridge);
        checkInMaps("patrologiacaput3A16", translatorBridge);
        checkInMaps("patrologiacaput3A17", translatorBridge);
        checkInMaps("patrologiacaput3A18", translatorBridge);
        //checkInMaps("patrologiacaput3A19", translatorBridge);
        checkInMaps("patrologiacaput3A20", translatorBridge);
        checkInMaps("patrologiacaput3A21", translatorBridge);
        checkInMaps("patrologiacaput3A22", translatorBridge);
        checkInMaps("patrologiacaput3A23", translatorBridge);
        checkInMaps("patrologiacaput3A24", translatorBridge);
        checkInMaps("patrologiacaput3A25", translatorBridge);
        checkInMaps("patrologiacaput3A26", translatorBridge);
        checkInMaps("patrologiacaput3A27", translatorBridge);
        //checkInMaps("patrologiacaput3A28", translatorBridge);
        checkInMaps("patrologiacaput3A29", translatorBridge);
        checkInMaps("patrologiacaput3A30", translatorBridge);
        checkInMaps("patrologiacaput3A31", translatorBridge);
        checkInMaps("patrologiacaput3A32", translatorBridge);
        checkInMaps("patrologiacaput3A33", translatorBridge);
        checkInMaps("patrologiacaput3A34", translatorBridge);
        checkInMaps("patrologiacaput3A35", translatorBridge);
        checkInMaps("patrologiacaput3A36", translatorBridge);
        checkInMaps("patrologiacaput3A37", translatorBridge);
        checkInMaps("patrologiacaput3A38", translatorBridge);
        checkInMaps("patrologiacaput3A39", translatorBridge);
        checkInMaps("patrologiacaput3A40", translatorBridge);
        checkInMaps("patrologiacaput3A41", translatorBridge);
        checkInMaps("patrologiacaput3A42", translatorBridge);
        checkInMaps("patrologiacaput3A43", translatorBridge);
        checkInMaps("patrologiacaput3A44", translatorBridge);
        checkInMaps("patrologiacaput3A45", translatorBridge);
        checkInMaps("patrologiacaput3A46", translatorBridge);
        checkInMaps("patrologiacaput3A47", translatorBridge);
        checkInMaps("patrologiacaput3A48", translatorBridge);
        checkInMaps("patrologiacaput3A49", translatorBridge);
        checkInMaps("patrologiacaput3A50", translatorBridge);
        checkInMaps("patrologiacaput3B01", translatorBridge);
        checkInMaps("patrologiacaput3B02", translatorBridge);
        checkInMaps("patrologiacaput3B03", translatorBridge);
        checkInMaps("patrologiacaput3B04", translatorBridge);
        checkInMaps("patrologiacaput3B05", translatorBridge);
        checkInMaps("patrologiacaput3B06", translatorBridge);
        checkInMaps("patrologiacaput3B07", translatorBridge);
        checkInMaps("patrologiacaput3B08", translatorBridge);
        checkInMaps("patrologiacaput3B09", translatorBridge);
        checkInMaps("patrologiacaput3B10", translatorBridge);
        checkInMaps("patrologiacaput3B11", translatorBridge);
        checkInMaps("patrologiacaput3B12", translatorBridge);
        checkInMaps("patrologiacaput3B13", translatorBridge);
        checkInMaps("patrologiacaput3B14", translatorBridge);
        checkInMaps("patrologiacaput3B15", translatorBridge);
        checkInMaps("patrologiacaput3B16", translatorBridge);
        checkInMaps("patrologiacaput3B17", translatorBridge);
        checkInMaps("patrologiacaput3B18", translatorBridge);
        checkInMaps("patrologiacaput3B19", translatorBridge);
        checkInMaps("patrologiacaput3B20", translatorBridge);
        checkInMaps("patrologiacaput3B21", translatorBridge);
        checkInMaps("patrologiacaput3B22", translatorBridge);
        checkInMaps("patrologiacaput3B23", translatorBridge);
        checkInMaps("patrologiacaput3B24", translatorBridge);
        //checkInMaps("patrologiacaput3B25", translatorBridge);
        checkInMaps("patrologiacaput3B26", translatorBridge);
        checkInMaps("patrologiacaput3B27", translatorBridge);
        checkInMaps("patrologiacaput3B28", translatorBridge);
        checkInMaps("patrologiacaput3B29", translatorBridge);
        checkInMaps("patrologiacaput3B30", translatorBridge);
        checkInMaps("patrologiacaput3B31", translatorBridge);
        checkInMaps("patrologiacaput3B32", translatorBridge);
        checkInMaps("patrologiacaput3B33", translatorBridge);
        checkInMaps("patrologiacaput3B34", translatorBridge);
        checkInMaps("patrologiacaput3B35", translatorBridge);
        checkInMaps("patrologiacaput3B36", translatorBridge);
        checkInMaps("patrologiacaput3B37", translatorBridge);
        checkInMaps("patrologiacaput3B38", translatorBridge);
        checkInMaps("patrologiacaput3B39", translatorBridge);
        checkInMaps("patrologiacaput3B40", translatorBridge);
        checkInMaps("patrologiacaput3B41", translatorBridge);
        checkInMaps("patrologiacaput3B42", translatorBridge);
        checkInMaps("patrologiacaput3B43", translatorBridge);
        checkInMaps("patrologiacaput3B44", translatorBridge);
        checkInMaps("patrologiacaput3B45", translatorBridge);
        checkInMaps("patrologiacaput3B46", translatorBridge);
        checkInMaps("patrologiacaput3B47", translatorBridge);
        checkInMaps("patrologiacaput3B48", translatorBridge);
        checkInMaps("patrologiacaput3B49", translatorBridge);
        checkInMaps("patrologiacaput3B50", translatorBridge);
        checkInMaps("patrologiacaput3B51", translatorBridge);
        checkInMaps("patrologiacaput3B52", translatorBridge);
        checkInMaps("patrologiacaput3B53", translatorBridge);
        checkInMaps("patrologiacaput3B54", translatorBridge);
        checkInMaps("patrologiacaput3B55", translatorBridge);
        checkInMaps("patrologiacaput3B56", translatorBridge);
        checkInMaps("patrologiacaput3B57", translatorBridge);
        checkInMaps("patrologiacaput3B58", translatorBridge);
        checkInMaps("patrologiacaput3B59", translatorBridge);
        checkInMaps("patrologiacaput3B60", translatorBridge);
        checkInMaps("patrologiacaput3B61", translatorBridge);
        checkInMaps("patrologiacaput3B62", translatorBridge);
        checkInMaps("patrologiacaput3B63", translatorBridge);
        //checkInMaps("patrologiacaput3B64", translatorBridge);
        checkInMaps("patrologiacaput3B65", translatorBridge);
        checkInMaps("patrologiacaput3B66", translatorBridge);
        checkInMaps("patrologiacaput3B67", translatorBridge);
        checkInMaps("patrologiacaput3B68", translatorBridge);
        checkInMaps("patrologiacaput3B69", translatorBridge);
        checkInMaps("patrologiacaput3B70", translatorBridge);
        checkInMaps("patrologiacaput3B71", translatorBridge);
        checkInMaps("patrologiacaput3B72", translatorBridge);
        checkInMaps("patrologiacaput3B73", translatorBridge);
        checkInMaps("patrologiacaput3B74", translatorBridge);
    }

    @Test
    public void patrologia_graeca_1_caput_4() {
        checkInMaps("patrologiacaput401", translatorBridge);
        checkInMaps("patrologiacaput402", translatorBridge);
        checkInMaps("patrologiacaput403", translatorBridge);
        checkInMaps("patrologiacaput404", translatorBridge);
        checkInMaps("patrologiacaput405", translatorBridge);
        checkInMaps("patrologiacaput406", translatorBridge);
        checkInMaps("patrologiacaput407", translatorBridge);
        checkInMaps("patrologiacaput408", translatorBridge);
        checkInMaps("patrologiacaput409", translatorBridge);
        checkInMaps("patrologiacaput410", translatorBridge);
        checkInMaps("patrologiacaput411", translatorBridge);
        checkInMaps("patrologiacaput412", translatorBridge);
        checkInMaps("patrologiacaput413", translatorBridge);
        checkInMaps("patrologiacaput414", translatorBridge);
        //checkInMaps("patrologiacaput415", translatorBridge);
        checkInMaps("patrologiacaput416", translatorBridge);
        checkInMaps("patrologiacaput417", translatorBridge);
        checkInMaps("patrologiacaput418", translatorBridge);
        checkInMaps("patrologiacaput419", translatorBridge);
        checkInMaps("patrologiacaput420", translatorBridge);
        checkInMaps("patrologiacaput421", translatorBridge);
        checkInMaps("patrologiacaput422", translatorBridge);
        checkInMaps("patrologiacaput423", translatorBridge);
        checkInMaps("patrologiacaput424", translatorBridge);
        checkInMaps("patrologiacaput425", translatorBridge);
        checkInMaps("patrologiacaput426", translatorBridge);
        checkInMaps("patrologiacaput427", translatorBridge);
        checkInMaps("patrologiacaput428", translatorBridge);
        checkInMaps("patrologiacaput429", translatorBridge);
        checkInMaps("patrologiacaput430", translatorBridge);
        checkInMaps("patrologiacaput431", translatorBridge);
        checkInMaps("patrologiacaput432", translatorBridge);
        checkInMaps("patrologiacaput433", translatorBridge);
        checkInMaps("patrologiacaput434", translatorBridge);
        checkInMaps("patrologiacaput435", translatorBridge);
        checkInMaps("patrologiacaput436", translatorBridge);
        checkInMaps("patrologiacaput437", translatorBridge);
        checkInMaps("patrologiacaput438", translatorBridge);
        checkInMaps("patrologiacaput439", translatorBridge);
        //checkInMaps("patrologiacaput440", translatorBridge);
        checkInMaps("patrologiacaput441", translatorBridge);
        checkInMaps("patrologiacaput442", translatorBridge);
        checkInMaps("patrologiacaput443", translatorBridge);
        checkInMaps("patrologiacaput444", translatorBridge);
        checkInMaps("patrologiacaput445", translatorBridge);
        checkInMaps("patrologiacaput446", translatorBridge);
        checkInMaps("patrologiacaput447", translatorBridge);
        checkInMaps("patrologiacaput448", translatorBridge);
    }

    @Test
    public void test_patrologia_chapter5() {
        checkInMaps("patrologiacaput501", translatorBridge);
        checkInMaps("patrologiacaput502", translatorBridge);
        checkInMaps("patrologiacaput503", translatorBridge);
        checkInMaps("patrologiacaput504", translatorBridge);
        checkInMaps("patrologiacaput505", translatorBridge);
        checkInMaps("patrologiacaput506", translatorBridge);
        checkInMaps("patrologiacaput507", translatorBridge);
        checkInMaps("patrologiacaput508", translatorBridge);
        checkInMaps("patrologiacaput509", translatorBridge);
        checkInMaps("patrologiacaput510", translatorBridge);
        checkInMaps("patrologiacaput511", translatorBridge);
        checkInMaps("patrologiacaput512", translatorBridge);
        checkInMaps("patrologiacaput513", translatorBridge);
        checkInMaps("patrologiacaput514", translatorBridge);
        checkInMaps("patrologiacaput515", translatorBridge);
        checkInMaps("patrologiacaput516", translatorBridge);
        checkInMaps("patrologiacaput517", translatorBridge);
        checkInMaps("patrologiacaput518", translatorBridge);
        checkInMaps("patrologiacaput519", translatorBridge);
        checkInMaps("patrologiacaput520", translatorBridge);
        checkInMaps("patrologiacaput521", translatorBridge);
        checkInMaps("patrologiacaput522", translatorBridge);
        checkInMaps("patrologiacaput523", translatorBridge);
        checkInMaps("patrologiacaput524", translatorBridge);
        checkInMaps("patrologiacaput525", translatorBridge);
        checkInMaps("patrologiacaput526", translatorBridge);
        checkInMaps("patrologiacaput527", translatorBridge);
        checkInMaps("patrologiacaput528", translatorBridge);
        checkInMaps("patrologiacaput529", translatorBridge);
        checkInMaps("patrologiacaput530", translatorBridge);
        checkInMaps("patrologiacaput531", translatorBridge);
        checkInMaps("patrologiacaput532", translatorBridge);
        checkInMaps("patrologiacaput533", translatorBridge);
        checkInMaps("patrologiacaput534", translatorBridge);
        checkInMaps("patrologiacaput535", translatorBridge);
        checkInMaps("patrologiacaput536", translatorBridge);
        checkInMaps("patrologiacaput537", translatorBridge);
        checkInMaps("patrologiacaput538", translatorBridge);
        checkInMaps("patrologiacaput539", translatorBridge);
        checkInMaps("patrologiacaput540", translatorBridge);
        checkInMaps("patrologiacaput541", translatorBridge);
        checkInMaps("patrologiacaput542", translatorBridge);
        checkInMaps("patrologiacaput543", translatorBridge);
        checkInMaps("patrologiacaput544", translatorBridge);
        checkInMaps("patrologiacaput545", translatorBridge);
        checkInMaps("patrologiacaput546", translatorBridge);
        checkInMaps("patrologiacaput547", translatorBridge);
        checkInMaps("patrologiacaput548", translatorBridge);
        checkInMaps("patrologiacaput549", translatorBridge);
        checkInMaps("patrologiacaput550", translatorBridge);
        checkInMaps("patrologiacaput551", translatorBridge);
        checkInMaps("patrologiacaput552", translatorBridge);
        checkInMaps("patrologiacaput553", translatorBridge);
        checkInMaps("patrologiacaput554", translatorBridge);
        checkInMaps("patrologiacaput555", translatorBridge);
        checkInMaps("patrologiacaput556", translatorBridge);
        checkInMaps("patrologiacaput556", translatorBridge);
        checkInMaps("patrologiacaput557", translatorBridge);
        checkInMaps("patrologiacaput558", translatorBridge);
        checkInMaps("patrologiacaput559", translatorBridge);
        checkInMaps("patrologiacaput560", translatorBridge);
        checkInMaps("patrologiacaput561", translatorBridge);
        checkInMaps("patrologiacaput562", translatorBridge);
        checkInMaps("patrologiacaput563", translatorBridge);
        checkInMaps("patrologiacaput564", translatorBridge);
        checkInMaps("patrologiacaput565", translatorBridge);
        checkInMaps("patrologiacaput566", translatorBridge);
    }

    @Test
    public void test_patrologia_chapter6() {
        checkInMaps("patrologiacaput6A000", translatorBridge);
        checkInMaps("patrologiacaput6A001", translatorBridge);
        checkInMaps("patrologiacaput6A002", translatorBridge);
        checkInMaps("patrologiacaput6A003", translatorBridge);
        checkInMaps("patrologiacaput6A004", translatorBridge);
        checkInMaps("patrologiacaput6A005", translatorBridge);
        checkInMaps("patrologiacaput6A006", translatorBridge);
        checkInMaps("patrologiacaput6A007a", translatorBridge);
        checkInMaps("patrologiacaput6A007b", translatorBridge);
        checkInMaps("patrologiacaput6A008", translatorBridge);
        checkInMaps("patrologiacaput6A009", translatorBridge);
        checkInMaps("patrologiacaput6A010", translatorBridge);
        checkInMaps("patrologiacaput6A011", translatorBridge);
        checkInMaps("patrologiacaput6A012", translatorBridge);
        checkInMaps("patrologiacaput6A013", translatorBridge);
        checkInMaps("patrologiacaput6A014", translatorBridge);
        checkInMaps("patrologiacaput6A015", translatorBridge);
        checkInMaps("patrologiacaput6A016", translatorBridge);
        checkInMaps("patrologiacaput6A017", translatorBridge);
        checkInMaps("patrologiacaput6A018", translatorBridge);
        checkInMaps("patrologiacaput6A019", translatorBridge);
        checkInMaps("patrologiacaput6A020", translatorBridge);
        checkInMaps("patrologiacaput6A021", translatorBridge);
        checkInMaps("patrologiacaput6A022", translatorBridge);
        checkInMaps("patrologiacaput6A023", translatorBridge);
        checkInMaps("patrologiacaput6A024", translatorBridge);
        checkInMaps("patrologiacaput6A025", translatorBridge);
        checkInMaps("patrologiacaput6A026", translatorBridge);
        checkInMaps("patrologiacaput6A027", translatorBridge);
        checkInMaps("patrologiacaput6A028", translatorBridge);
        checkInMaps("patrologiacaput6A029", translatorBridge);
        checkInMaps("patrologiacaput6A030", translatorBridge);
        checkInMaps("patrologiacaput6A031", translatorBridge);
        checkInMaps("patrologiacaput6A032", translatorBridge);
        checkInMaps("patrologiacaput6A033", translatorBridge);
        checkInMaps("patrologiacaput6A034", translatorBridge);
        checkInMaps("patrologiacaput6A035", translatorBridge);
        checkInMaps("patrologiacaput6A036", translatorBridge);
        checkInMaps("patrologiacaput6A037", translatorBridge);
        checkInMaps("patrologiacaput6A038", translatorBridge);
        checkInMaps("patrologiacaput6A039", translatorBridge);
        checkInMaps("patrologiacaput6A040", translatorBridge);
        checkInMaps("patrologiacaput6A041", translatorBridge);
        checkInMaps("patrologiacaput6A042", translatorBridge);
        checkInMaps("patrologiacaput6A043", translatorBridge);
        checkInMaps("patrologiacaput6A044", translatorBridge);
        checkInMaps("patrologiacaput6A045", translatorBridge);
        checkInMaps("patrologiacaput6A046", translatorBridge);
        checkInMaps("patrologiacaput6A047", translatorBridge);
        checkInMaps("patrologiacaput6A048", translatorBridge);
        checkInMaps("patrologiacaput6A049", translatorBridge);
        checkInMaps("patrologiacaput6A050", translatorBridge);
        checkInMaps("patrologiacaput6A051", translatorBridge);
        checkInMaps("patrologiacaput6A052", translatorBridge);
        checkInMaps("patrologiacaput6A053", translatorBridge);
        checkInMaps("patrologiacaput6A054", translatorBridge);
        checkInMaps("patrologiacaput6A055", translatorBridge);
        checkInMaps("patrologiacaput6A056", translatorBridge);
        checkInMaps("patrologiacaput6A057", translatorBridge);
        checkInMaps("patrologiacaput6A058", translatorBridge);
        checkInMaps("patrologiacaput6A059", translatorBridge);
        checkInMaps("patrologiacaput6A060", translatorBridge);
        checkInMaps("patrologiacaput6A061", translatorBridge);
        checkInMaps("patrologiacaput6A062", translatorBridge);
        checkInMaps("patrologiacaput6A063", translatorBridge);
        checkInMaps("patrologiacaput6A064", translatorBridge);
        checkInMaps("patrologiacaput6A065", translatorBridge);
        checkInMaps("patrologiacaput6A066", translatorBridge);
        checkInMaps("patrologiacaput6A067", translatorBridge);
        checkInMaps("patrologiacaput6A068", translatorBridge);
        checkInMaps("patrologiacaput6A069", translatorBridge);
        checkInMaps("patrologiacaput6A070", translatorBridge);
        checkInMaps("patrologiacaput6A071", translatorBridge);
        checkInMaps("patrologiacaput6A072", translatorBridge);
        checkInMaps("patrologiacaput6A073", translatorBridge);
        checkInMaps("patrologiacaput6A074", translatorBridge);
        checkInMaps("patrologiacaput6A075", translatorBridge);
        checkInMaps("patrologiacaput6A076", translatorBridge);
        checkInMaps("patrologiacaput6A077", translatorBridge);
        checkInMaps("patrologiacaput6A078", translatorBridge);
        checkInMaps("patrologiacaput6A079", translatorBridge);
        checkInMaps("patrologiacaput6A080", translatorBridge);
        checkInMaps("patrologiacaput6A081", translatorBridge);
        checkInMaps("patrologiacaput6A082", translatorBridge);
        checkInMaps("patrologiacaput6A083", translatorBridge);
        checkInMaps("patrologiacaput6A084", translatorBridge);
        checkInMaps("patrologiacaput6A085", translatorBridge);
        checkInMaps("patrologiacaput6A086", translatorBridge);
        checkInMaps("patrologiacaput6A087", translatorBridge);
        checkInMaps("patrologiacaput6A088", translatorBridge);
        checkInMaps("patrologiacaput6A089", translatorBridge);
        checkInMaps("patrologiacaput6A090", translatorBridge);
        checkInMaps("patrologiacaput6A091", translatorBridge);
        checkInMaps("patrologiacaput6A092", translatorBridge);
        checkInMaps("patrologiacaput6A093", translatorBridge);
        checkInMaps("patrologiacaput6A094", translatorBridge);
        checkInMaps("patrologiacaput6A095", translatorBridge);
        checkInMaps("patrologiacaput6A096", translatorBridge);
        checkInMaps("patrologiacaput6A097", translatorBridge);
        checkInMaps("patrologiacaput6A098", translatorBridge);
        checkInMaps("patrologiacaput6A099", translatorBridge);
        checkInMaps("patrologiacaput6A100", translatorBridge);
        checkInMaps("patrologiacaput6A101", translatorBridge);
        checkInMaps("patrologiacaput6A102", translatorBridge);
        checkInMaps("patrologiacaput6A103", translatorBridge);
        checkInMaps("patrologiacaput6A104", translatorBridge);
        checkInMaps("patrologiacaput6A105", translatorBridge);
        checkInMaps("patrologiacaput6A106", translatorBridge);
        checkInMaps("patrologiacaput6A107", translatorBridge);
        checkInMaps("patrologiacaput6A108", translatorBridge);
        checkInMaps("patrologiacaput6A109", translatorBridge);
        checkInMaps("patrologiacaput6A110", translatorBridge);
        checkInMaps("patrologiacaput6A111", translatorBridge);
        checkInMaps("patrologiacaput6A112", translatorBridge);
        checkInMaps("patrologiacaput6A113", translatorBridge);
        checkInMaps("patrologiacaput6A114", translatorBridge);
        checkInMaps("patrologiacaput6A115", translatorBridge);
        checkInMaps("patrologiacaput6A116", translatorBridge);
        checkInMaps("patrologiacaput6A117", translatorBridge);
        checkInMaps("patrologiacaput6A118", translatorBridge);
        checkInMaps("patrologiacaput6A119", translatorBridge);
        checkInMaps("patrologiacaput6A120", translatorBridge);
    }

    @Test
    public void test_patrologia_chapter7() {
        checkInMaps("patrologiacaput701", translatorBridge);
        checkInMaps("patrologiacaput702", translatorBridge);
        checkInMaps("patrologiacaput703", translatorBridge);
        checkInMaps("patrologiacaput704", translatorBridge);
        checkInMaps("patrologiacaput705", translatorBridge);
        checkInMaps("patrologiacaput706", translatorBridge);
        checkInMaps("patrologiacaput707", translatorBridge);
        checkInMaps("patrologiacaput708", translatorBridge);
        checkInMaps("patrologiacaput709", translatorBridge);
        checkInMaps("patrologiacaput710", translatorBridge);
        checkInMaps("patrologiacaput711", translatorBridge);
        checkInMaps("patrologiacaput712", translatorBridge);
        checkInMaps("patrologiacaput713", translatorBridge);
        checkInMaps("patrologiacaput714", translatorBridge);
        checkInMaps("patrologiacaput715", translatorBridge);
        checkInMaps("patrologiacaput716", translatorBridge);
        checkInMaps("patrologiacaput717", translatorBridge);
        checkInMaps("patrologiacaput718", translatorBridge);
        checkInMaps("patrologiacaput719", translatorBridge);
        checkInMaps("patrologiacaput720", translatorBridge);
        checkInMaps("patrologiacaput721", translatorBridge);
        checkInMaps("patrologiacaput722", translatorBridge);
        checkInMaps("patrologiacaput723", translatorBridge);
        checkInMaps("patrologiacaput724", translatorBridge);
        checkInMaps("patrologiacaput725", translatorBridge);
        checkInMaps("patrologiacaput726", translatorBridge);
        checkInMaps("patrologiacaput727", translatorBridge);
        checkInMaps("patrologiacaput728", translatorBridge);
        checkInMaps("patrologiacaput729", translatorBridge);
        checkInMaps("patrologiacaput730", translatorBridge);
        checkInMaps("patrologiacaput731", translatorBridge);
        checkInMaps("patrologiacaput732", translatorBridge);
        checkInMaps("patrologiacaput733", translatorBridge);
        checkInMaps("patrologiacaput734", translatorBridge);
        checkInMaps("patrologiacaput735", translatorBridge);
        checkInMaps("patrologiacaput736", translatorBridge);
        checkInMaps("patrologiacaput737", translatorBridge);
        checkInMaps("patrologiacaput738", translatorBridge);
        checkInMaps("patrologiacaput739", translatorBridge);
        checkInMaps("patrologiacaput740", translatorBridge);
        checkInMaps("patrologiacaput741", translatorBridge);
        checkInMaps("patrologiacaput742", translatorBridge);
        checkInMaps("patrologiacaput743", translatorBridge);
    }


    @Test
    public void test_patrologia_chapter8() {
        checkInMaps("patrologiacaput800", translatorBridge);
        checkInMaps("patrologiacaput801", translatorBridge);
        checkInMaps("patrologiacaput802", translatorBridge);
        checkInMaps("patrologiacaput803", translatorBridge);
        checkInMaps("patrologiacaput804", translatorBridge);
        checkInMaps("patrologiacaput805", translatorBridge);
        checkInMaps("patrologiacaput806", translatorBridge);
        checkInMaps("patrologiacaput807", translatorBridge);
        checkInMaps("patrologiacaput808", translatorBridge);
        checkInMaps("patrologiacaput809A", translatorBridge);
        checkInMaps("patrologiacaput809B", translatorBridge);
        checkInMaps("patrologiacaput810", translatorBridge);
        checkInMaps("patrologiacaput811", translatorBridge);
        checkInMaps("patrologiacaput812", translatorBridge);
        checkInMaps("patrologiacaput813", translatorBridge);
        checkInMaps("patrologiacaput814", translatorBridge);
        checkInMaps("patrologiacaput815", translatorBridge);
        checkInMaps("patrologiacaput816", translatorBridge);
        checkInMaps("patrologiacaput817", translatorBridge);
        checkInMaps("patrologiacaput818", translatorBridge);
        checkInMaps("patrologiacaput819", translatorBridge);
        checkInMaps("patrologiacaput820", translatorBridge);
        checkInMaps("patrologiacaput821", translatorBridge);
        checkInMaps("patrologiacaput822", translatorBridge);
        checkInMaps("patrologiacaput823", translatorBridge);
        checkInMaps("patrologiacaput824", translatorBridge);
        checkInMaps("patrologiacaput825", translatorBridge);
        checkInMaps("patrologiacaput826", translatorBridge);
        checkInMaps("patrologiacaput827", translatorBridge);
        checkInMaps("patrologiacaput828", translatorBridge);
        checkInMaps("patrologiacaput829", translatorBridge);
        checkInMaps("patrologiacaput830", translatorBridge);
        checkInMaps("patrologiacaput831", translatorBridge);
        checkInMaps("patrologiacaput832", translatorBridge);
        checkInMaps("patrologiacaput833", translatorBridge);
        checkInMaps("patrologiacaput834", translatorBridge);
        checkInMaps("patrologiacaput835", translatorBridge);
        checkInMaps("patrologiacaput836", translatorBridge);
        checkInMaps("patrologiacaput837", translatorBridge);
        checkInMaps("patrologiacaput838", translatorBridge);
        checkInMaps("patrologiacaput839", translatorBridge);
        checkInMaps("patrologiacaput840", translatorBridge);
        checkInMaps("patrologiacaput841", translatorBridge);
        checkInMaps("patrologiacaput842", translatorBridge);
        checkInMaps("patrologiacaput843", translatorBridge);
        checkInMaps("patrologiacaput844", translatorBridge);
        checkInMaps("patrologiacaput845", translatorBridge);
        checkInMaps("patrologiacaput846", translatorBridge);
        checkInMaps("patrologiacaput847", translatorBridge);
        checkInMaps("patrologiacaput848", translatorBridge);
        checkInMaps("patrologiacaput849", translatorBridge);
        checkInMaps("patrologiacaput850", translatorBridge);
        checkInMaps("patrologiacaput851", translatorBridge);
        checkInMaps("patrologiacaput852", translatorBridge);
        checkInMaps("patrologiacaput853", translatorBridge);
        checkInMaps("patrologiacaput854", translatorBridge);
        checkInMaps("patrologiacaput855", translatorBridge);
        checkInMaps("patrologiacaput856", translatorBridge);
        checkInMaps("patrologiacaput857", translatorBridge);
        checkInMaps("patrologiacaput858", translatorBridge);
        checkInMaps("patrologiacaput859", translatorBridge);
        checkInMaps("patrologiacaput860", translatorBridge);
        checkInMaps("patrologiacaput861", translatorBridge);
        checkInMaps("patrologiacaput862", translatorBridge);
        checkInMaps("patrologiacaput863", translatorBridge);
        checkInMaps("patrologiacaput864", translatorBridge);
        checkInMaps("patrologiacaput865", translatorBridge);
        checkInMaps("patrologiacaput866", translatorBridge);
        checkInMaps("patrologiacaput867", translatorBridge);
        checkInMaps("patrologiacaput868", translatorBridge);
        checkInMaps("patrologiacaput869", translatorBridge);
        checkInMaps("patrologiacaput870", translatorBridge);
        checkInMaps("patrologiacaput871", translatorBridge);
        checkInMaps("patrologiacaput872", translatorBridge);
        checkInMaps("patrologiacaput873", translatorBridge);
        checkInMaps("patrologiacaput874", translatorBridge);
        checkInMaps("patrologiacaput875", translatorBridge);
        checkInMaps("patrologiacaput876", translatorBridge);
        checkInMaps("patrologiacaput877", translatorBridge);
        checkInMaps("patrologiacaput878", translatorBridge);
        checkInMaps("patrologiacaput879", translatorBridge);
        checkInMaps("patrologiacaput880", translatorBridge);
        checkInMaps("patrologiacaput881", translatorBridge);
    }

    @Test
    public void test_failing_one() {
        checkInMaps("toto", translatorBridge);
        //checkInMaps("patrologiacaput6A020", translatorBridge);
    }

}
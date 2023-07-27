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

import java.util.*;

/**
 * Created by lkloeble on 02/05/2017.
 */
public class LatinCaesarTest  extends LatinTranslatorBridgeTest  {

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
        String latinPathFile = localTestPath + "caesar_content.txt";
        String latinResultFile = localTestPath + "caesar_expected_result.txt";
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
                "traduc,o,is,ere,,,[o-is],(AIPP*traduc*tradux)"
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
    public void test_caeser_book1_chapter01() {
        checkInMaps("caesar1A1", translatorBridge);
        checkInMaps("caesar1A2", translatorBridge);
        checkInMaps("caesar1A3", translatorBridge);
        checkInMaps("caesar1A4", translatorBridge);
        checkInMaps("caesar1A5", translatorBridge);
        checkInMaps("caesar1A6", translatorBridge);
        checkInMaps("caesar1A7", translatorBridge);
        //checkInMaps("caesar1B1", translatorBridge);
        checkInMaps("caesar1B2", translatorBridge);
        checkInMaps("caesar1B3", translatorBridge);
        //checkInMaps("caesar1B4", translatorBridge);
        checkInMaps("caesar1B5", translatorBridge);
        checkInMaps("caesar1B6", translatorBridge);
        checkInMaps("caesar1C1", translatorBridge);
        checkInMaps("caesar1C2", translatorBridge);
        checkInMaps("caesar1C3", translatorBridge);
        checkInMaps("caesar1C4", translatorBridge);
        checkInMaps("caesar1C5", translatorBridge);
        checkInMaps("caesar1C6", translatorBridge);
        checkInMaps("caesar1C7", translatorBridge);
        checkInMaps("caesar1C8", translatorBridge);
        checkInMaps("caesar1D1A", translatorBridge);
        checkInMaps("caesar1D1B", translatorBridge);
        checkInMaps("caesar1D1C", translatorBridge);
        checkInMaps("caesar1D21", translatorBridge);
        checkInMaps("caesar1D22", translatorBridge);
        checkInMaps("caesar1D23", translatorBridge);
        checkInMaps("caesar1D24", translatorBridge);
        checkInMaps("caesar1D3", translatorBridge);
        //checkInMaps("caesar1D4", translatorBridge);
        //checkInMaps("caesar1E1", translatorBridge);
        checkInMaps("caesar1E21", translatorBridge);
        checkInMaps("caesar1E22", translatorBridge);
        checkInMaps("caesar1E31", translatorBridge);
        checkInMaps("caesar1E32", translatorBridge);
        checkInMaps("caesar1E33", translatorBridge);
        checkInMaps("caesar1E41", translatorBridge);
        checkInMaps("caesar1E42", translatorBridge);
        checkInMaps("caesar1E43", translatorBridge);
        checkInMaps("caesar1E44", translatorBridge);
        checkInMaps("caesar1F11", translatorBridge);
        checkInMaps("caesar1F12", translatorBridge);
        checkInMaps("caesar1F13", translatorBridge);
        checkInMaps("caesar1F21", translatorBridge);
        checkInMaps("caesar1F22", translatorBridge);
        checkInMaps("caesar1F31", translatorBridge);
        //checkInMaps("caesar1F32", translatorBridge);
        checkInMaps("caesar1F33", translatorBridge);
        //checkInMaps("caesar1F34", translatorBridge);
        checkInMaps("caesar1F35", translatorBridge);
        checkInMaps("caesar1G1", translatorBridge);
        //checkInMaps("caesar1G2", translatorBridge);
        checkInMaps("caesar1G3A", translatorBridge);
        checkInMaps("caesar1G3B", translatorBridge);
        checkInMaps("caesar1G3C", translatorBridge);
        checkInMaps("caesar1G3D", translatorBridge);
        checkInMaps("caesar1G3E", translatorBridge);
        checkInMaps("caesar1G3F", translatorBridge);
        checkInMaps("caesar1G4A", translatorBridge);
        checkInMaps("caesar1G4B", translatorBridge);
        checkInMaps("caesar1G5A", translatorBridge);
        checkInMaps("caesar1G5B", translatorBridge);
        checkInMaps("caesar1G5C", translatorBridge);
        checkInMaps("caesar1H1A", translatorBridge);
        checkInMaps("caesar1H1B", translatorBridge);
        checkInMaps("caesar1H1C", translatorBridge);
        checkInMaps("caesar1H2A", translatorBridge);
        checkInMaps("caesar1H2B", translatorBridge);
        checkInMaps("caesar1H3A", translatorBridge);
        checkInMaps("caesar1H3B", translatorBridge);
        checkInMaps("caesar1H3C", translatorBridge);
        checkInMaps("caesar1H4A", translatorBridge);
        checkInMaps("caesar1H4B", translatorBridge);
        checkInMaps("caesar1H4C", translatorBridge);
        checkInMaps("caesar1H4D", translatorBridge);
        checkInMaps("caesar1I1", translatorBridge);
        checkInMaps("caesar1I2", translatorBridge);
        checkInMaps("caesar1I3A", translatorBridge);
        checkInMaps("caesar1I3B", translatorBridge);
        checkInMaps("caesar1I4A", translatorBridge);
        checkInMaps("caesar1I4B", translatorBridge);
        checkInMaps("caesar1J1", translatorBridge);
        checkInMaps("caesar1J2", translatorBridge);
        checkInMaps("caesar1J3", translatorBridge);
        checkInMaps("caesar1J4", translatorBridge);
        checkInMaps("caesar1K1", translatorBridge);
        checkInMaps("caesar1K2", translatorBridge);
        checkInMaps("caesar1K3A", translatorBridge);
        checkInMaps("caesar1K3B", translatorBridge);
        checkInMaps("caesar1K3C", translatorBridge);
        checkInMaps("caesar1K4", translatorBridge);
        checkInMaps("caesar1K5A", translatorBridge);
        checkInMaps("caesar1K5B", translatorBridge);
        checkInMaps("caesar1L1A", translatorBridge);
        checkInMaps("caesar1L1B", translatorBridge);
        checkInMaps("caesar1L2A", translatorBridge);
        checkInMaps("caesar1L2B", translatorBridge);
        checkInMaps("caesar1L2C", translatorBridge);
        checkInMaps("caesar1L3A", translatorBridge);
        checkInMaps("caesar1L3B", translatorBridge);
        checkInMaps("caesar1L4", translatorBridge);
        checkInMaps("caesar1L5", translatorBridge);
        checkInMaps("caesar1L6", translatorBridge);
        checkInMaps("caesar1L7", translatorBridge);
        checkInMaps("caesar1M1", translatorBridge);
        checkInMaps("caesar1M2", translatorBridge);
        checkInMaps("caesar1M3", translatorBridge);
        checkInMaps("caesar1M4", translatorBridge);
        checkInMaps("caesar1M5", translatorBridge);
        checkInMaps("caesar1N1", translatorBridge);
        checkInMaps("caesar1N2", translatorBridge);
        checkInMaps("caesar1N3", translatorBridge);
        checkInMaps("caesar1N4", translatorBridge);
        checkInMaps("caesar1N5", translatorBridge);
        checkInMaps("caesar1N6", translatorBridge);
        checkInMaps("caesar1N7", translatorBridge);
        checkInMaps("caesar1O1", translatorBridge);
        checkInMaps("caesar1O2", translatorBridge);
        checkInMaps("caesar1O3", translatorBridge);
        checkInMaps("caesar1P1", translatorBridge);
        checkInMaps("caesar1P2", translatorBridge);
        checkInMaps("caesar1P3", translatorBridge);
        checkInMaps("caesar1P4", translatorBridge);
        checkInMaps("caesar1P5", translatorBridge);
        checkInMaps("caesar1P6", translatorBridge);
        checkInMaps("caesar1Q1", translatorBridge);
        checkInMaps("caesar1Q2", translatorBridge);
        checkInMaps("caesar1Q3", translatorBridge);
        checkInMaps("caesar1Q4", translatorBridge);
        checkInMaps("caesar1Q5", translatorBridge);
        checkInMaps("caesar1Q6", translatorBridge);
        checkInMaps("caesar1R1", translatorBridge);
        checkInMaps("caesar1R2", translatorBridge);
        checkInMaps("caesar1R3", translatorBridge);
        checkInMaps("caesar1R4", translatorBridge);
        checkInMaps("caesar1R5", translatorBridge);
        checkInMaps("caesar1R6", translatorBridge);
        checkInMaps("caesar1R7", translatorBridge);
        checkInMaps("caesar1R8", translatorBridge);
        checkInMaps("caesar1R9", translatorBridge);
        checkInMaps("caesar1R10", translatorBridge);
        checkInMaps("caesar1S1", translatorBridge);
        checkInMaps("caesar1S2", translatorBridge);
        checkInMaps("caesar1S3", translatorBridge);
        checkInMaps("caesar1S4", translatorBridge);
        checkInMaps("caesar1S5", translatorBridge);
        checkInMaps("caesar1T1", translatorBridge);
        checkInMaps("caesar1T2", translatorBridge);
        checkInMaps("caesar1T3", translatorBridge);
        checkInMaps("caesar1T4", translatorBridge);
        checkInMaps("caesar1T5", translatorBridge);
    }


    @Test
    public void test_failing_one() {
        checkInMaps("toto", translatorBridge);
        //checkInMaps("patrologiacaput6A020", translatorBridge);
    }


}

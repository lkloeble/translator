import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.german.GermanCaseFactory;
import patrologia.translator.conjugation.german.GermanConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.german.GermanDeclension;
import patrologia.translator.declension.german.GermanDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.GermanAnalyzer;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.german.GermanRuleFactory;
import patrologia.translator.utils.Analyzer;


import java.util.*;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    private String localTestPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\german\\";
    private String localCommonPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\";

    @Before
    public void init() {
        String prepositionFileDescription = localResourcesPath + "prepositions.txt";
        String nounFileDescription = localResourcesPath + "nouns.txt";
        String verbFileDescription = localResourcesPath + "verbs.txt";
        String germanFrenchDataFile = localResourcesPath + "robert_collins_german_to_french.txt";
        String frenchVerbsDataFile = localCommonPath + "french_verbs.txt";
        String declensionPath = localResourcesPath + "declensions";
        String declensionsAndFiles = localResourcesPath + "declensionsAndFiles.txt";
        String conjugationPath = localResourcesPath + "conjugations";
        String conjugationsAndFiles = localResourcesPath + "conjugationsAndFiles.txt";
        String germanPathFile = localTestPath + "german_content.txt";
        String germanResultFile = localTestPath + "german_expected_results.txt";
        GermanDeclensionFactory germanDeclensionFactory = new GermanDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        NounRepository nounRepository = new NounRepository(Language.GERMAN, germanDeclensionFactory, new DummyAccentuer(), getFileContentForRepository(nounFileDescription));
        VerbRepository2 verbRepository = new VerbRepository2(new GermanConjugationFactory(getGermanConjugations(conjugationsAndFiles), getGermanConjugationDefinitions(conjugationsAndFiles, conjugationPath), germanDeclensionFactory), Language.GERMAN, new DummyAccentuer(), getVerbs(verbFileDescription));
        GermanRuleFactory ruleFactory = new GermanRuleFactory(verbRepository);
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.GERMAN, new GermanCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        Analyzer germanAnalyzer = new GermanAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getDictionaryData(germanFrenchDataFile), getFrenchVerbs(frenchVerbsDataFile), verbRepository, nounRepository, declensionPath, declensionsAndFiles, germanDeclensionFactory);
        translatorBridge = new TranslatorBridge(germanAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(germanPathFile);
        mapValuesForResult = loadMapFromFiles(germanResultFile);
    }

    private List<String> getFrenchVerbs(String frenchVerbsDataFile) {
        /*
        return Arrays.asList(new String[]{
                "etre@IRREGULAR%[INFINITIVE]=[être]%[IPR]=[suis,es,est,sommes,êtes,sont]%[AII]=[étais,étais,était,étions,étiez,étaient]%[AIF]=[serai,seras,sera,serons,serez,seront]%[ASP]=[sois,sois,soit,soyons,soyez,soient]%[ASI]=[étais,étais,était,étions,étiez,étaient]%[AIP]=[fus,fus,fut,fûmes,fûtes,fûrent]%[AIMP]=[sois,soit,soyons,soyez,soient]%[AIPP]=[ai été,as été,a été,avons  été,avez été,ont été]%[PAPR]=[étant]%[ACP]=[serais,serais,serait,serions,seriez,seraient]%[PAP]=[été]%[IAP]=[avoir été]%[ACOPPR]=[serais,serais,serait,serions,seriez,seraient]%[AIFP]=[aurai été,auras été,aura été,aurons été,aurez été,auront été]%[ASPP]=[eusse été,eusses été,eut été,eussions été,eussiez été,eussent été]"
        });
        */
        return getFileContentForRepository(frenchVerbsDataFile);
    }

    private List<String> getDictionaryData(String germanFrenchDataFile) {
        /*
        return Arrays.asList(new String[]{
                "halt@prep%1(prep)=halte",
                "halt@verb!norm%1(verb)=arrêter%2(verb)=tenir"
        });
        */
        return getFileContentForRepository(germanFrenchDataFile);
    }

    private List<Declension> getDeclensionList(String file, String directory) {
        List<String> declensionNameList = getFileContentForRepository(file);
        List<Declension> declensionList = new ArrayList<>();
        for (String declensionName : declensionNameList) {
            String parts[] = declensionName.split("%");
            String fileName = parts[1];
            declensionList.add(new GermanDeclension(fileName, getDeclensionElements(fileName, directory)));
        }
        return declensionList;
    }

    private List<String> getVerbs(String verbFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "heirat,en,[mochten],(PAP*heirat*geheiratet*0@AIP*heirat*heiratet*0)"
        });
        */
        return getFileContentForRepository(verbFileDescription);
    }

    private Map<String, List<String>> getGermanConjugationDefinitions(String file, String directory) {
        /*
        Map<String, List<String>> latinConjugationDefinitionsMap = new HashMap<>();
        latinConjugationDefinitionsMap.put("o-is", getOIslDefinition());
        return latinConjugationDefinitionsMap;
        */


        List<String> conjugationNameList = getFileContentForRepository(file);
        Map<String, List<String>> germanConjugationDefinitionsMap = new HashMap<>();
        for (String conjugationName : conjugationNameList) {
            String parts[] = conjugationName.split("%");
            String fileName = parts[1];
            String nameOnly = parts[0];
            germanConjugationDefinitionsMap.put(nameOnly, getConjugationElements(directory, fileName));
        }
        return germanConjugationDefinitionsMap;
    }

    private List<String> getGermanConjugations(String conjugationsAndFiles) {
        /*
        return Arrays.asList(new String[]{
                "o-is%permittere.txt"
        });
        */
        return getFileContentForRepository(conjugationsAndFiles);
    }

    @Test
    public void test_allemand_debutant_chapter_01() {
        checkInMaps("lecon1A", translatorBridge);
        checkInMaps("lecon1B", translatorBridge);
        checkInMaps("lecon1C", translatorBridge);
        checkInMaps("lecon1D", translatorBridge);
        checkInMaps("lecon1E", translatorBridge);
        checkInMaps("lecon1F", translatorBridge);
        checkInMaps("lecon1G", translatorBridge);
        checkInMaps("lecon1H", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_02() {
        checkInMaps("lecon2A", translatorBridge);
        checkInMaps("lecon2B", translatorBridge);
        checkInMaps("lecon2C", translatorBridge);
        checkInMaps("lecon2D", translatorBridge);
        checkInMaps("lecon2E", translatorBridge);
        checkInMaps("lecon2F", translatorBridge);
        checkInMaps("lecon2G", translatorBridge);
        checkInMaps("lecon2H", translatorBridge);
        checkInMaps("lecon2I", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_03() {
        checkInMaps("lecon3A", translatorBridge);
        checkInMaps("lecon3B", translatorBridge);
        checkInMaps("lecon3C", translatorBridge);
        checkInMaps("lecon3D", translatorBridge);
        checkInMaps("lecon3E", translatorBridge);
        checkInMaps("lecon3F", translatorBridge);
        checkInMaps("lecon3G", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_04() {
        checkInMaps("lecon4A", translatorBridge);
        checkInMaps("lecon4B", translatorBridge);
        checkInMaps("lecon4C", translatorBridge);
        checkInMaps("lecon4D", translatorBridge);
        checkInMaps("lecon4E", translatorBridge);
        checkInMaps("lecon4F", translatorBridge);
        checkInMaps("lecon4G", translatorBridge);
        checkInMaps("lecon4H", translatorBridge);
        checkInMaps("lecon4I", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_05() {
        checkInMaps("lecon5A", translatorBridge);
        checkInMaps("lecon5B", translatorBridge);
        checkInMaps("lecon5C", translatorBridge);
        checkInMaps("lecon5D", translatorBridge);
        checkInMaps("lecon5E", translatorBridge);
        checkInMaps("lecon5F", translatorBridge);
        checkInMaps("lecon5G", translatorBridge);
        checkInMaps("lecon5H", translatorBridge);
        checkInMaps("lecon5I", translatorBridge);
        checkInMaps("lecon5J", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_06() {
        checkInMaps("lecon6A", translatorBridge);
        checkInMaps("lecon6B", translatorBridge);
        checkInMaps("lecon6C", translatorBridge);
        checkInMaps("lecon6D", translatorBridge);
        checkInMaps("lecon6E", translatorBridge);
        checkInMaps("lecon6F", translatorBridge);
        checkInMaps("lecon6G", translatorBridge);
        checkInMaps("lecon6H", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_07() {
        //checkInMaps("lecon7A", translatorBridge);
        checkInMaps("lecon7B", translatorBridge);
        checkInMaps("lecon7C", translatorBridge);
        checkInMaps("lecon7D", translatorBridge);
        checkInMaps("lecon7E", translatorBridge);
        checkInMaps("lecon7F", translatorBridge);
        checkInMaps("lecon7G", translatorBridge);
        checkInMaps("lecon7H", translatorBridge);
        checkInMaps("lecon7I", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_08() {
        checkInMaps("lecon8A", translatorBridge);
        checkInMaps("lecon8B", translatorBridge);
        checkInMaps("lecon8C", translatorBridge);
        checkInMaps("lecon8D", translatorBridge);
        checkInMaps("lecon8E", translatorBridge);
        checkInMaps("lecon8F", translatorBridge);
        checkInMaps("lecon8G", translatorBridge);
        checkInMaps("lecon8H", translatorBridge);
        checkInMaps("lecon8I", translatorBridge);
        checkInMaps("lecon8J", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_09() {
        checkInMaps("lecon9A", translatorBridge);
        checkInMaps("lecon9B", translatorBridge);
        checkInMaps("lecon9C", translatorBridge);
        checkInMaps("lecon9D", translatorBridge);
        //checkInMaps("lecon9E", translatorBridge);
        checkInMaps("lecon9F", translatorBridge);
        checkInMaps("lecon9G", translatorBridge);
        checkInMaps("lecon9H", translatorBridge);
        checkInMaps("lecon9I", translatorBridge);
        checkInMaps("lecon9J", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_10() {
        checkInMaps("lecon10A", translatorBridge);
        checkInMaps("lecon10B", translatorBridge);
        checkInMaps("lecon10C", translatorBridge);
        checkInMaps("lecon10D", translatorBridge);
        checkInMaps("lecon10E", translatorBridge);
        checkInMaps("lecon10F", translatorBridge);
        checkInMaps("lecon10G", translatorBridge);
        checkInMaps("lecon10H", translatorBridge);
        checkInMaps("lecon10I", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_11() {
        checkInMaps("lecon11A", translatorBridge);
        checkInMaps("lecon11B", translatorBridge);
        checkInMaps("lecon11C", translatorBridge);
        checkInMaps("lecon11D", translatorBridge);
        checkInMaps("lecon11E", translatorBridge);
        checkInMaps("lecon11F", translatorBridge);
        checkInMaps("lecon11G", translatorBridge);
        checkInMaps("lecon11H", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_12() {
        checkInMaps("lecon12A", translatorBridge);
        checkInMaps("lecon12B", translatorBridge);
        checkInMaps("lecon12C", translatorBridge);
        checkInMaps("lecon12D", translatorBridge);
        checkInMaps("lecon12E", translatorBridge);
        //checkInMaps("lecon12F", translatorBridge);
        checkInMaps("lecon12G", translatorBridge);
        checkInMaps("lecon12H", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_13() {
        checkInMaps("lecon13A", translatorBridge);
        checkInMaps("lecon13B", translatorBridge);
        checkInMaps("lecon13C", translatorBridge);
        checkInMaps("lecon13D", translatorBridge);
        checkInMaps("lecon13E", translatorBridge);
        checkInMaps("lecon13F", translatorBridge);
        checkInMaps("lecon13G", translatorBridge);
        checkInMaps("lecon13H", translatorBridge);
        checkInMaps("lecon13I", translatorBridge);
        checkInMaps("lecon13J", translatorBridge);
        checkInMaps("lecon13K", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_14() {
        checkInMaps("lecon14A", translatorBridge);
        checkInMaps("lecon14B", translatorBridge);
        checkInMaps("lecon14C", translatorBridge);
        checkInMaps("lecon14D", translatorBridge);
        checkInMaps("lecon14E", translatorBridge);
        checkInMaps("lecon14F", translatorBridge);
        checkInMaps("lecon14G", translatorBridge);
        checkInMaps("lecon14H", translatorBridge);
        checkInMaps("lecon14I", translatorBridge);
        checkInMaps("lecon14J", translatorBridge);
        checkInMaps("lecon14K", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_15() {
        checkInMaps("lecon15A", translatorBridge);
        checkInMaps("lecon15B", translatorBridge);
        checkInMaps("lecon15C", translatorBridge);
        //checkInMaps("lecon15D", translatorBridge);
        checkInMaps("lecon15E", translatorBridge);
        checkInMaps("lecon15F", translatorBridge);
        checkInMaps("lecon15G", translatorBridge);
        //checkInMaps("lecon15H", translatorBridge);
        checkInMaps("lecon15I", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_16() {
        checkInMaps("lecon16A", translatorBridge);
        //checkInMaps("lecon16B", translatorBridge);
        //checkInMaps("lecon16C", translatorBridge);
        checkInMaps("lecon16D", translatorBridge);
        checkInMaps("lecon16E", translatorBridge);
        checkInMaps("lecon16F", translatorBridge);
        //checkInMaps("lecon16G", translatorBridge);
        //checkInMaps("lecon16H", translatorBridge);
        //checkInMaps("lecon16I", translatorBridge);
        //checkInMaps("lecon16J", translatorBridge);
        checkInMaps("lecon16K", translatorBridge);
        checkInMaps("lecon16L", translatorBridge);

    }

    @Test
    public void test_allemand_debutant_chapter_17() {
        checkInMaps("lecon17A", translatorBridge);
        checkInMaps("lecon17B", translatorBridge);
        checkInMaps("lecon17C", translatorBridge);
        checkInMaps("lecon17D", translatorBridge);
        checkInMaps("lecon17E", translatorBridge);
        checkInMaps("lecon17F", translatorBridge);
        checkInMaps("lecon17G", translatorBridge);
        checkInMaps("lecon17H", translatorBridge);
        checkInMaps("lecon17I", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_18() {
        checkInMaps("lecon18A", translatorBridge);
        checkInMaps("lecon18B", translatorBridge);
        checkInMaps("lecon18C", translatorBridge);
        checkInMaps("lecon18D", translatorBridge);
        //checkInMaps("lecon18E", translatorBridge);
        checkInMaps("lecon18F", translatorBridge);
        checkInMaps("lecon18G", translatorBridge);
        checkInMaps("lecon18H", translatorBridge);
        checkInMaps("lecon18I", translatorBridge);
        checkInMaps("lecon18J", translatorBridge);
    }


    @Test
    public void test_genesis_chapter1() {
        checkInMaps("genesis1A", translatorBridge);
        //checkInMaps("genesis1B", translatorBridge);
        //checkInMaps("genesis1C", translatorBridge);
        checkInMaps("genesis1D", translatorBridge);
        checkInMaps("genesis1E", translatorBridge);
        //checkInMaps("genesis1F", translatorBridge);
        checkInMaps("genesis1G", translatorBridge);
        checkInMaps("genesis1H", translatorBridge);
        //checkInMaps("genesis1I", translatorBridge);
        checkInMaps("genesis1J", translatorBridge);
        checkInMaps("genesis1K", translatorBridge);
        checkInMaps("genesis1L", translatorBridge);
        checkInMaps("genesis1M", translatorBridge);
        //checkInMaps("genesis1N", translatorBridge);
        checkInMaps("genesis1O", translatorBridge);
        checkInMaps("genesis1P", translatorBridge);
        checkInMaps("genesis1Q", translatorBridge);
        checkInMaps("genesis1R", translatorBridge);
        checkInMaps("genesis1S", translatorBridge);
        //checkInMaps("genesis1T", translatorBridge);
        checkInMaps("genesis1U", translatorBridge);
        checkInMaps("genesis1V", translatorBridge);
        checkInMaps("genesis1W", translatorBridge);
        checkInMaps("genesis1X", translatorBridge);
        checkInMaps("genesis1Y", translatorBridge);
        //checkInMaps("genesis1Z", translatorBridge);
        //checkInMaps("genesis1AA", translatorBridge);
        checkInMaps("genesis1BB", translatorBridge);
        checkInMaps("genesis1CC", translatorBridge);
        checkInMaps("genesis1DD", translatorBridge);
        checkInMaps("genesis1EE", translatorBridge);
    }

    @Test
    public void test_genesis_chapter2() {
        checkInMaps("genesis2A", translatorBridge);
        checkInMaps("genesis2B", translatorBridge);
        checkInMaps("genesis2C", translatorBridge);
        //checkInMaps("genesis2D", translatorBridge);
        //checkInMaps("genesis2E", translatorBridge);
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
        //checkInMaps("genesis2S", translatorBridge);
        //checkInMaps("genesis2T", translatorBridge);
        checkInMaps("genesis2U", translatorBridge);
        checkInMaps("genesis2V", translatorBridge);
        //checkInMaps("genesis2W", translatorBridge);
        //checkInMaps("genesis2X", translatorBridge);
        checkInMaps("genesis2Y", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap01() {
        checkInMaps("nietzscheBook1", translatorBridge);
        checkInMaps("nietzscheBook2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1A1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1A2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1B", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap1C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1D", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1I", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap1J", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1K", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap1L", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap1M", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap1N", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1O", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap1P", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1Q", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1R", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1S", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap1T", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap02() {
        //checkInMaps("nietzscheBookGTversuchChap2A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2B", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap2C", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap2D", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap2E", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap2F", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap2G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2I", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap2J", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap03() {
        //checkInMaps("nietzscheBookGTversuchCha3A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3B1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3B2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3B3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3C1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha3C2", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha3D1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3D2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3D3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3E1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha3E2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3F1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3F2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3F3", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha3G1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3G2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3I1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3I2", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap04() {
        checkInMaps("nietzscheBookGTversuchCha4A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4D", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4E", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha4F1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha4F2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4F3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4G", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha4H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4I", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4J", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4K", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4L", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha4M", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4N1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha4N2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4O", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4P", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4Q", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4R1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4R2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4R3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4S1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4S2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4S3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4T", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha4U", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4V", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4W", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap05() {
        checkInMaps("nietzscheBookGTversuchCha5A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5B", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5D", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5E", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5G", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5H1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5H2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5I1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5I2", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5I3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5J1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5J2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5J3", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5K", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5L", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5M1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5M2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5M3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5N1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5N2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5O1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5O2", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5O3", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5P", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5Q1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5Q2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5R1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5R2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5R3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5S1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5S2", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5S3", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5T1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5T2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5T3", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5T4", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5U1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5U2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5V1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5V2", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha5W", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5X", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap06() {
        //checkInMaps("nietzscheBookGTversuchCha6A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6D", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha6E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6G", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha6H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6I", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6J", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6K", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6L", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6M", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha6N", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6O", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6P", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap07() {
        checkInMaps("nietzscheBookGTversuchCha7A", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7B", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7C", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7D", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7G", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7I1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7I2", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7I3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7J1", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7J2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7J3", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7J4", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7K", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7L1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7L2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7M1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7M2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7N1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7N2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7O", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7P1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7P2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7Q1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7Q2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7R1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7R2", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchCha7S1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7S2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7T1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7T2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7U", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_wagner() {
        checkInMaps("nietzscheBookGTWagner01", translatorBridge);
        checkInMaps("nietzscheBookGTWagner02", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner03", translatorBridge);
        checkInMaps("nietzscheBookGTWagner04", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner05", translatorBridge);
        checkInMaps("nietzscheBookGTWagner06", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner07", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner08", translatorBridge);
        checkInMaps("nietzscheBookGTWagner09", translatorBridge);
        checkInMaps("nietzscheBookGTWagner10", translatorBridge);
        checkInMaps("nietzscheBookGTWagner11", translatorBridge);
        checkInMaps("nietzscheBookGTWagner12", translatorBridge);
        checkInMaps("nietzscheBookGTWagner13", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner14", translatorBridge);
        checkInMaps("nietzscheBookGTWagner15", translatorBridge);
        checkInMaps("nietzscheBookGTWagner16", translatorBridge);
        checkInMaps("nietzscheBookGTWagner17", translatorBridge);
        checkInMaps("nietzscheBookGTWagner18", translatorBridge);
        checkInMaps("nietzscheBookGTWagner19", translatorBridge);
        checkInMaps("nietzscheBookGTWagner20", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner21", translatorBridge);
        checkInMaps("nietzscheBookGTWagner22", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner23", translatorBridge);
        checkInMaps("nietzscheBookGTWagner24", translatorBridge);
        checkInMaps("nietzscheBookGTWagner25", translatorBridge);
        checkInMaps("nietzscheBookGTWagner26", translatorBridge);
        checkInMaps("nietzscheBookGTWagner27", translatorBridge);
        checkInMaps("nietzscheBookGTWagner28", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner29", translatorBridge);
    }

    @Test
    public void test_nietzsche_naissance_tragedie_chap1() {
        //checkInMaps("nietzscheBookGTChap1001", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1002", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1003", translatorBridge);
        checkInMaps("nietzscheBookGTChap1004", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1005", translatorBridge);
        checkInMaps("nietzscheBookGTChap1006", translatorBridge);
        checkInMaps("nietzscheBookGTChap1007", translatorBridge);
        checkInMaps("nietzscheBookGTChap1008", translatorBridge);
        checkInMaps("nietzscheBookGTChap1009", translatorBridge);
        checkInMaps("nietzscheBookGTChap1010", translatorBridge);
        checkInMaps("nietzscheBookGTChap1011", translatorBridge);
        checkInMaps("nietzscheBookGTChap1012", translatorBridge);
        checkInMaps("nietzscheBookGTChap1013", translatorBridge);
        checkInMaps("nietzscheBookGTChap1014", translatorBridge);
        checkInMaps("nietzscheBookGTChap1015", translatorBridge);
        checkInMaps("nietzscheBookGTChap1016", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1017", translatorBridge);
        checkInMaps("nietzscheBookGTChap1018", translatorBridge);
        checkInMaps("nietzscheBookGTChap1019", translatorBridge);
        checkInMaps("nietzscheBookGTChap1020", translatorBridge);
        checkInMaps("nietzscheBookGTChap1021", translatorBridge);
        checkInMaps("nietzscheBookGTChap1022", translatorBridge);
        checkInMaps("nietzscheBookGTChap1023", translatorBridge);
        checkInMaps("nietzscheBookGTChap1024", translatorBridge);
        checkInMaps("nietzscheBookGTChap1025", translatorBridge);
        checkInMaps("nietzscheBookGTChap1026", translatorBridge);
        checkInMaps("nietzscheBookGTChap1027", translatorBridge);
        checkInMaps("nietzscheBookGTChap1028", translatorBridge);
        checkInMaps("nietzscheBookGTChap1029", translatorBridge);
        checkInMaps("nietzscheBookGTChap1030", translatorBridge);
        checkInMaps("nietzscheBookGTChap1031", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1032", translatorBridge);
        checkInMaps("nietzscheBookGTChap1033", translatorBridge);
        checkInMaps("nietzscheBookGTChap1034", translatorBridge);
        checkInMaps("nietzscheBookGTChap1035", translatorBridge);
        checkInMaps("nietzscheBookGTChap1036", translatorBridge);
        checkInMaps("nietzscheBookGTChap1037", translatorBridge);
        checkInMaps("nietzscheBookGTChap1038", translatorBridge);
        checkInMaps("nietzscheBookGTChap1039", translatorBridge);
        checkInMaps("nietzscheBookGTChap1040", translatorBridge);
        checkInMaps("nietzscheBookGTChap1041", translatorBridge);
        checkInMaps("nietzscheBookGTChap1042", translatorBridge);
        checkInMaps("nietzscheBookGTChap1043", translatorBridge);
        checkInMaps("nietzscheBookGTChap1044", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1045", translatorBridge);
        checkInMaps("nietzscheBookGTChap1046", translatorBridge);
        checkInMaps("nietzscheBookGTChap1047", translatorBridge);
        checkInMaps("nietzscheBookGTChap1048", translatorBridge);
        checkInMaps("nietzscheBookGTChap1049", translatorBridge);
        checkInMaps("nietzscheBookGTChap1050", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1051", translatorBridge);
        checkInMaps("nietzscheBookGTChap1052", translatorBridge);
        checkInMaps("nietzscheBookGTChap1053", translatorBridge);
        checkInMaps("nietzscheBookGTChap1054", translatorBridge);
        checkInMaps("nietzscheBookGTChap1055", translatorBridge);
        checkInMaps("nietzscheBookGTChap1056", translatorBridge);
        checkInMaps("nietzscheBookGTChap1057", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1058", translatorBridge);
        checkInMaps("nietzscheBookGTChap1059", translatorBridge);
        checkInMaps("nietzscheBookGTChap1060", translatorBridge);
        checkInMaps("nietzscheBookGTChap1061", translatorBridge);
        checkInMaps("nietzscheBookGTChap1062", translatorBridge);
        checkInMaps("nietzscheBookGTChap1063", translatorBridge);
        checkInMaps("nietzscheBookGTChap1064", translatorBridge);
        checkInMaps("nietzscheBookGTChap1065", translatorBridge);
        checkInMaps("nietzscheBookGTChap1066", translatorBridge);
        checkInMaps("nietzscheBookGTChap1067", translatorBridge);
        checkInMaps("nietzscheBookGTChap1068", translatorBridge);
        checkInMaps("nietzscheBookGTChap1069", translatorBridge);
        checkInMaps("nietzscheBookGTChap1070", translatorBridge);
        checkInMaps("nietzscheBookGTChap1071", translatorBridge);
        checkInMaps("nietzscheBookGTChap1072", translatorBridge);
        checkInMaps("nietzscheBookGTChap1073", translatorBridge);
        checkInMaps("nietzscheBookGTChap1074", translatorBridge);
        checkInMaps("nietzscheBookGTChap1075", translatorBridge);
        checkInMaps("nietzscheBookGTChap1076", translatorBridge);
        checkInMaps("nietzscheBookGTChap1077", translatorBridge);
        checkInMaps("nietzscheBookGTChap1078", translatorBridge);
        checkInMaps("nietzscheBookGTChap1079", translatorBridge);
        checkInMaps("nietzscheBookGTChap1080", translatorBridge);
        checkInMaps("nietzscheBookGTChap1081", translatorBridge);
        checkInMaps("nietzscheBookGTChap1082", translatorBridge);
        checkInMaps("nietzscheBookGTChap1083", translatorBridge);
        checkInMaps("nietzscheBookGTChap1084", translatorBridge);
        checkInMaps("nietzscheBookGTChap1085", translatorBridge);
        checkInMaps("nietzscheBookGTChap1086", translatorBridge);
        checkInMaps("nietzscheBookGTChap1087", translatorBridge);
        checkInMaps("nietzscheBookGTChap1088", translatorBridge);
        checkInMaps("nietzscheBookGTChap1089", translatorBridge);
        checkInMaps("nietzscheBookGTChap1090", translatorBridge);
        checkInMaps("nietzscheBookGTChap1091", translatorBridge);
        checkInMaps("nietzscheBookGTChap1092", translatorBridge);
        checkInMaps("nietzscheBookGTChap1093", translatorBridge);
        checkInMaps("nietzscheBookGTChap1094", translatorBridge);
        checkInMaps("nietzscheBookGTChap1095", translatorBridge);
        checkInMaps("nietzscheBookGTChap1096", translatorBridge);
        checkInMaps("nietzscheBookGTChap1097", translatorBridge);
        checkInMaps("nietzscheBookGTChap1098", translatorBridge);
        checkInMaps("nietzscheBookGTChap1099", translatorBridge);
        checkInMaps("nietzscheBookGTChap1100", translatorBridge);
        checkInMaps("nietzscheBookGTChap1101", translatorBridge);
        checkInMaps("nietzscheBookGTChap1102", translatorBridge);
        checkInMaps("nietzscheBookGTChap1103", translatorBridge);
        checkInMaps("nietzscheBookGTChap1104", translatorBridge);
        checkInMaps("nietzscheBookGTChap1105", translatorBridge);
        checkInMaps("nietzscheBookGTChap1106", translatorBridge);
        checkInMaps("nietzscheBookGTChap1107", translatorBridge);
        checkInMaps("nietzscheBookGTChap1108", translatorBridge);
        checkInMaps("nietzscheBookGTChap1109", translatorBridge);
        checkInMaps("nietzscheBookGTChap1110", translatorBridge);
        checkInMaps("nietzscheBookGTChap1111", translatorBridge);
        checkInMaps("nietzscheBookGTChap1112", translatorBridge);
    }
    
    @Test
    public void test_failedones() {
        assertTrue(true);
        //checkInMaps("toto", translatorBridge);
    }
}

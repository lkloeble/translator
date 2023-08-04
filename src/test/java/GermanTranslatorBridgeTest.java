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
        //checkInMaps("lecon15A", translatorBridge);
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

}

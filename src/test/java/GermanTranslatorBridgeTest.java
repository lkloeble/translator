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
        checkInMaps("lecon15H", translatorBridge);
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
    public void test_strack_preface() {
        checkInMaps("strack1", translatorBridge);
        checkInMaps("strack2", translatorBridge);
        //checkInMaps("strack3", translatorBridge);
        checkInMaps("strack4", translatorBridge);
        checkInMaps("strack5", translatorBridge);
        checkInMaps("strack6", translatorBridge);
        checkInMaps("strack7", translatorBridge);
        checkInMaps("strack8", translatorBridge);
        checkInMaps("strack9", translatorBridge);
        checkInMaps("strack10", translatorBridge);
        checkInMaps("strack11", translatorBridge);
        checkInMaps("strack12", translatorBridge);
        checkInMaps("strack13", translatorBridge);
        checkInMaps("strack14", translatorBridge);
        checkInMaps("strack15", translatorBridge);
        checkInMaps("strack16", translatorBridge);
        //checkInMaps("strack17", translatorBridge);
        //checkInMaps("strack18", translatorBridge);
        checkInMaps("strack19", translatorBridge);
        //checkInMaps("strack20", translatorBridge);
        checkInMaps("strack21", translatorBridge);
        //checkInMaps("strack22", translatorBridge);
        checkInMaps("strack23", translatorBridge);
        checkInMaps("strack24", translatorBridge);
        //checkInMaps("strack25", translatorBridge);
        checkInMaps("strack26", translatorBridge);
        checkInMaps("strack27", translatorBridge);
        checkInMaps("strack28", translatorBridge);
        checkInMaps("strack29", translatorBridge);
    }


    @Test
    public void test_strack_matthieuc1v1() {
        checkInMaps("strackmatc1v1t", translatorBridge);
        //checkInMaps("stracktoto", translatorBridge);
        checkInMaps("strackmatc1v1exp1A", translatorBridge);
        checkInMaps("strackmatc1v1exp1B", translatorBridge);
        checkInMaps("strackmatc1v1exp1C", translatorBridge);
        checkInMaps("strackmatc1v1exp1D", translatorBridge);
        checkInMaps("strackmatc1v1exp1E", translatorBridge);
        checkInMaps("strackmatc1v1exp1F", translatorBridge);
        checkInMaps("strackmatc1v1exp2A", translatorBridge);
        checkInMaps("strackmatc1v1exp2B", translatorBridge);
        checkInMaps("strackmatc1v1exp2C", translatorBridge);
        checkInMaps("strackmatc1v1exp2D", translatorBridge);
        checkInMaps("strackmatc1v1exp2E", translatorBridge);
        checkInMaps("strackmatc1v1exp2F", translatorBridge);
        checkInMaps("strackmatc1v1exp2G", translatorBridge);
        checkInMaps("strackmatc1v1exp2H", translatorBridge);
        checkInMaps("strackmatc1v1exp2I", translatorBridge);
        checkInMaps("strackmatc1v1exp2J", translatorBridge);
    }

    @Test
    public void test_talmud_help_strack() {
        //checkInMaps("strackp1notBA", translatorBridge);
        checkInMaps("strackp1notBB", translatorBridge);
        checkInMaps("strackp1notBC", translatorBridge);
        checkInMaps("strackp1notBD", translatorBridge);
        checkInMaps("strackp1notBE", translatorBridge);
        checkInMaps("strackp1notBF", translatorBridge);
        checkInMaps("strackp1notBG", translatorBridge);
        //checkInMaps("strackp1notBH", translatorBridge);
    }

    @Test
    public void test_talmud_help_strack2() {
        checkInMaps("strackp3A", translatorBridge);
        //checkInMaps("strackp3B", translatorBridge);
        //checkInMaps("strackp3C", translatorBridge);
        checkInMaps("strackp3D", translatorBridge);
        checkInMaps("strackp3E", translatorBridge);
        checkInMaps("strackp3F", translatorBridge);
        //checkInMaps("strackp3G", translatorBridge);
        checkInMaps("strackp3H", translatorBridge);
        checkInMaps("strackp3notA", translatorBridge);
        checkInMaps("strackp3notB", translatorBridge);
        checkInMaps("strackp3notC", translatorBridge);
        checkInMaps("strackp3notD", translatorBridge);
        checkInMaps("strackp3notE", translatorBridge);
        checkInMaps("strackp3notF", translatorBridge);
        checkInMaps("strackp3notG", translatorBridge);
        //checkInMaps("strackp3notH", translatorBridge);
        checkInMaps("strackp3notI", translatorBridge);
        //checkInMaps("strackp3notJ", translatorBridge);
    }

    @Test
    public void test_talmud_help_strackp3notB() {
        checkInMaps("strackp3notB1", translatorBridge);
        checkInMaps("strackp3notB2", translatorBridge);
        checkInMaps("strackp3notB3", translatorBridge);
        checkInMaps("strackp3notB4", translatorBridge);
        checkInMaps("strackp3notB5", translatorBridge);
        checkInMaps("strackp3notB6", translatorBridge);
        //checkInMaps("strackp3notB7", translatorBridge);
        //checkInMaps("strackp3notB8", translatorBridge);
        checkInMaps("strackp3notB9", translatorBridge);
        checkInMaps("strackp3notB10", translatorBridge);
        //checkInMaps("strackp3notB11", translatorBridge);
        checkInMaps("strackp3notB12", translatorBridge);
        checkInMaps("strackp3notB13", translatorBridge);
        checkInMaps("strackp3notB14", translatorBridge);
        checkInMaps("strackp3notB15", translatorBridge);
        checkInMaps("strackp3notB16", translatorBridge);
        checkInMaps("strackp3notB17", translatorBridge);
        checkInMaps("strackp3notB18", translatorBridge);
        checkInMaps("strackp3notB19", translatorBridge);
        checkInMaps("strackp3notB20", translatorBridge);
        checkInMaps("strackp3notB21", translatorBridge);
        checkInMaps("strackp3notB22", translatorBridge);
        //checkInMaps("strackp3notB23", translatorBridge);
        //checkInMaps("strackp3notB24", translatorBridge);
        //checkInMaps("strackp3notB25", translatorBridge);
        checkInMaps("strackp3notB26", translatorBridge);
        //checkInMaps("strackp3notB27", translatorBridge);
        checkInMaps("strackp3notB28", translatorBridge);
        checkInMaps("strackp3notB29", translatorBridge);
        //checkInMaps("strackp3notB30", translatorBridge);
        checkInMaps("strackp3notB31", translatorBridge);
        checkInMaps("strackp3notB32", translatorBridge);
        checkInMaps("strackp3notB33", translatorBridge);
        checkInMaps("strackp3notB34", translatorBridge);
        checkInMaps("strackp3notB35", translatorBridge);
        checkInMaps("strackp3notB36", translatorBridge);
        checkInMaps("strackp3notB37", translatorBridge);
        checkInMaps("strackp3notB38", translatorBridge);
    }

    @Test
    public void test_talmud_help_strackp3notC() {
        checkInMaps("strackp3notC1", translatorBridge);
        checkInMaps("strackp3notC2", translatorBridge);
        checkInMaps("strackp3notC3", translatorBridge);
        checkInMaps("strackp3notC4", translatorBridge);
    }

    @Test
    public void test_talmud_help_strackp3notD() {
        checkInMaps("strackp3notD1", translatorBridge);
        checkInMaps("strackp3notD2", translatorBridge);
        checkInMaps("strackp3notD3", translatorBridge);
        //checkInMaps("strackp3notD4", translatorBridge);
        checkInMaps("strackp3notD5", translatorBridge);
        checkInMaps("strackp3notD6", translatorBridge);
    }

    @Test
    public void test_talmud_help_strackp4andNot() {
        checkInMaps("strackp4not001", translatorBridge);
        //checkInMaps("strackp4not002", translatorBridge);
        //checkInMaps("strackp4not003", translatorBridge);
        checkInMaps("strackp4not004", translatorBridge);
        checkInMaps("strackp4not005", translatorBridge);
        checkInMaps("strackp4not006", translatorBridge);
        checkInMaps("strackp4not007", translatorBridge);
        checkInMaps("strackp4not008", translatorBridge);
        //checkInMaps("strackp4not009", translatorBridge);
        //checkInMaps("strackp4not010", translatorBridge);
        //checkInMaps("strackp4not011", translatorBridge);
        checkInMaps("strackp4not012", translatorBridge);
        checkInMaps("strackp4not013", translatorBridge);
        checkInMaps("strackp4not014", translatorBridge);
        checkInMaps("strackp4not015", translatorBridge);
        checkInMaps("strackp4not016", translatorBridge);
        checkInMaps("strackp4not017", translatorBridge);
        checkInMaps("strackp4not018", translatorBridge);
        checkInMaps("strackp4not019", translatorBridge);
        checkInMaps("strackp4not020", translatorBridge);
        checkInMaps("strackp4not021", translatorBridge);
        checkInMaps("strackp4not022", translatorBridge);
        checkInMaps("strackp4not023", translatorBridge);
        checkInMaps("strackp4not024", translatorBridge);
        checkInMaps("strackp4not025", translatorBridge);
        checkInMaps("strackp4not026", translatorBridge);
        checkInMaps("strackp4not027", translatorBridge);
        checkInMaps("strackp4not028", translatorBridge);
        checkInMaps("strackp4not029", translatorBridge);
        //checkInMaps("strackp4not030", translatorBridge);
        checkInMaps("strackp4not031", translatorBridge);
        checkInMaps("strackp4not032", translatorBridge);
        checkInMaps("strackp4not033", translatorBridge);
        //checkInMaps("strackp4not034", translatorBridge);
        checkInMaps("strackp4not035", translatorBridge);
        //checkInMaps("strackp4not036", translatorBridge);
        //checkInMaps("strackp4not037", translatorBridge);
        checkInMaps("strackp4not038", translatorBridge);
        checkInMaps("strackp4not039", translatorBridge);
        checkInMaps("strackp4not040", translatorBridge);
        checkInMaps("strackp4not041", translatorBridge);
        checkInMaps("strackp4not042", translatorBridge);
        checkInMaps("strackp4not043", translatorBridge);
        checkInMaps("strackp4not044", translatorBridge);
        checkInMaps("strackp4not045", translatorBridge);
        checkInMaps("strackp4not046", translatorBridge);
        checkInMaps("strackp4not047", translatorBridge);
        checkInMaps("strackp4not048", translatorBridge);
        checkInMaps("strackp4not049", translatorBridge);
        checkInMaps("strackp4not050", translatorBridge);
        checkInMaps("strackp4not051", translatorBridge);
        checkInMaps("strackp4not052", translatorBridge);
        checkInMaps("strackp4not053", translatorBridge);
        //checkInMaps("strackp4not054", translatorBridge);
        checkInMaps("strackp4not055", translatorBridge);
        //checkInMaps("strackp4not056", translatorBridge);
        checkInMaps("strackp4not057", translatorBridge);
        checkInMaps("strackp4not058", translatorBridge);
        checkInMaps("strackp4not059", translatorBridge);
        checkInMaps("strackp4not060", translatorBridge);
        //checkInMaps("strackp4not061", translatorBridge);
        checkInMaps("strackp4not062", translatorBridge);
        //checkInMaps("strackp4not063", translatorBridge);
        checkInMaps("strackp4not064", translatorBridge);
        checkInMaps("strackp4not065", translatorBridge);
        checkInMaps("strackp4not066", translatorBridge);
        checkInMaps("strackp4not067", translatorBridge);
        checkInMaps("strackp4not068", translatorBridge);
        checkInMaps("strackp4not069", translatorBridge);
        checkInMaps("strackp4not070", translatorBridge);
        checkInMaps("strackp4not071", translatorBridge);
        checkInMaps("strackp4not072", translatorBridge);
        checkInMaps("strackp4not073", translatorBridge);
        checkInMaps("strackp4not074", translatorBridge);
        checkInMaps("strackp4not075", translatorBridge);
        checkInMaps("strackp4not076", translatorBridge);
        //checkInMaps("strackp4not077", translatorBridge);
        //checkInMaps("strackp4not078", translatorBridge);
        //checkInMaps("strackp4not079", translatorBridge);
        //checkInMaps("strackp4not080", translatorBridge);
        //checkInMaps("strackp4not081", translatorBridge);
        //checkInMaps("strackp4not082", translatorBridge);
        //checkInMaps("strackp4not083", translatorBridge);
        checkInMaps("strackp4not084", translatorBridge);
        checkInMaps("strackp4not085", translatorBridge);
        checkInMaps("strackp4not086", translatorBridge);
        checkInMaps("strackp4not087", translatorBridge);
        checkInMaps("strackp4not088", translatorBridge);
        checkInMaps("strackp4not089", translatorBridge);
        //checkInMaps("strackp4not090", translatorBridge);
        checkInMaps("strackp4not091", translatorBridge);
        //checkInMaps("strackp4not092", translatorBridge);
        //checkInMaps("strackp4not093", translatorBridge);
        //checkInMaps("strackp4not094", translatorBridge);
        //checkInMaps("strackp4not095", translatorBridge);
        //checkInMaps("strackp4not096", translatorBridge);
        checkInMaps("strackp4not097", translatorBridge);
        //checkInMaps("strackp4not098", translatorBridge);
        checkInMaps("strackp4not099", translatorBridge);
        checkInMaps("strackp4not100", translatorBridge);
        //checkInMaps("strackp4not101", translatorBridge);
    }

    @Test
    public void test_strack_page6_par11() {
        checkInMaps("strackp6par11A", translatorBridge);
        checkInMaps("strackp6par11B", translatorBridge);
        //checkInMaps("strackp6par11C", translatorBridge);
        checkInMaps("strackp6par11D", translatorBridge);
        checkInMaps("strackp6par11E", translatorBridge);
        //checkInMaps("strackp6par11F1", translatorBridge);
        checkInMaps("strackp6par11F2", translatorBridge);
        checkInMaps("strackp6par11G", translatorBridge);
        checkInMaps("strackp6par11H", translatorBridge);
        checkInMaps("strackp6par11I", translatorBridge);
        checkInMaps("strackp6par11J", translatorBridge);
        checkInMaps("strackp6par11K", translatorBridge);
        checkInMaps("strackp6par11L", translatorBridge);
        checkInMaps("strackp6par11M", translatorBridge);
        checkInMaps("strackp6par11N", translatorBridge);
    }

    @Test
    public void test_strack_page7_par2() {
        //checkInMaps("strackp6par201", translatorBridge);
        checkInMaps("strackp6par202", translatorBridge);
        //checkInMaps("strackp6par203", translatorBridge);
        checkInMaps("strackp6par204", translatorBridge);
        checkInMaps("strackp6par205", translatorBridge);
        checkInMaps("strackp6par206", translatorBridge);
        //checkInMaps("strackp6par207", translatorBridge);
        checkInMaps("strackp6par208", translatorBridge);
        checkInMaps("strackp6par209", translatorBridge);
        checkInMaps("strackp6par210", translatorBridge);
        //checkInMaps("strackp6par211", translatorBridge);
        checkInMaps("strackp6par212", translatorBridge);
        checkInMaps("strackp6par213", translatorBridge);
        checkInMaps("strackp6par214", translatorBridge);
        //checkInMaps("strackp6par215", translatorBridge);
        checkInMaps("strackp6par216", translatorBridge);
        checkInMaps("strackp6par217", translatorBridge);
        //checkInMaps("strackp6par218", translatorBridge);
        //checkInMaps("strackp6par219", translatorBridge);
        //checkInMaps("strackp6par220", translatorBridge);
        //checkInMaps("strackp6par221", translatorBridge);
        checkInMaps("strackp6par222", translatorBridge);
        checkInMaps("strackp6par223", translatorBridge);
        //checkInMaps("strackp6par224", translatorBridge);
        checkInMaps("strackp6par225", translatorBridge);
        checkInMaps("strackp6par226", translatorBridge);
        //checkInMaps("strackp6par227", translatorBridge);
        checkInMaps("strackp6par228", translatorBridge);
        checkInMaps("strackp6par229", translatorBridge);
        checkInMaps("strackp6par230", translatorBridge);
        //checkInMaps("strackp6par231", translatorBridge);
        checkInMaps("strackp6par232", translatorBridge);
        checkInMaps("strackp6par233", translatorBridge);
        //checkInMaps("strackp6par234", translatorBridge);
        checkInMaps("strackp6par235", translatorBridge);
        checkInMaps("strackp6par236", translatorBridge);
        checkInMaps("strackp6par237", translatorBridge);
        //checkInMaps("strackp6par238", translatorBridge);
        //checkInMaps("strackp6par239", translatorBridge);
        //checkInMaps("strackp6par240", translatorBridge);
        checkInMaps("strackp6par241", translatorBridge);
        checkInMaps("strackp6par242", translatorBridge);
        checkInMaps("strackp6par243", translatorBridge);
        checkInMaps("strackp6par244", translatorBridge);
        checkInMaps("strackp6par245", translatorBridge);
        checkInMaps("strackp6par246", translatorBridge);
        //checkInMaps("strackp6par247", translatorBridge);
        checkInMaps("strackp6par248", translatorBridge);
        //checkInMaps("strackp6par249", translatorBridge);
        //checkInMaps("strackp6par250", translatorBridge);
        checkInMaps("strackp6par251", translatorBridge);
        checkInMaps("strackp6par252", translatorBridge);
        //checkInMaps("strackp6par253", translatorBridge);
        checkInMaps("strackp6par254", translatorBridge);
        //checkInMaps("strackp6par255", translatorBridge);
        //checkInMaps("strackp6par256", translatorBridge);
        checkInMaps("strackp6par257", translatorBridge);
        checkInMaps("strackp6par258", translatorBridge);
        //checkInMaps("strackp6par259", translatorBridge);
        //checkInMaps("strackp6par260", translatorBridge);
        //checkInMaps("strackp6par261", translatorBridge);
        //checkInMaps("strackp6par262", translatorBridge);
        checkInMaps("strackp6par263", translatorBridge);
        checkInMaps("strackp6par264", translatorBridge);
        //checkInMaps("strackp6par265", translatorBridge);
        checkInMaps("strackp6par266", translatorBridge);
        //checkInMaps("strackp6par267", translatorBridge);
        checkInMaps("strackp6par268", translatorBridge);
        checkInMaps("strackp6par269", translatorBridge);
        //checkInMaps("strackp6par270", translatorBridge);
        //checkInMaps("strackp6par271", translatorBridge);
        //checkInMaps("strackp6par272", translatorBridge);
        //checkInMaps("strackp6par273", translatorBridge);
        checkInMaps("strackp6par274", translatorBridge);
        //checkInMaps("strackp6par275", translatorBridge);
        //checkInMaps("strackp6par276", translatorBridge);
        checkInMaps("strackp6par277", translatorBridge);
        checkInMaps("strackp6par278", translatorBridge);
        checkInMaps("strackp6par279", translatorBridge);
        checkInMaps("strackp6par280", translatorBridge);
        checkInMaps("strackp6par281", translatorBridge);
        checkInMaps("strackp6par282", translatorBridge);
        checkInMaps("strackp6par283", translatorBridge);
        checkInMaps("strackp6par284", translatorBridge);
        checkInMaps("strackp6par285", translatorBridge);
        checkInMaps("strackp6par286", translatorBridge);
        checkInMaps("strackp6par287", translatorBridge);
        checkInMaps("strackp6par288", translatorBridge);
        checkInMaps("strackp6par289", translatorBridge);
        checkInMaps("strackp6par290", translatorBridge);
        checkInMaps("strackp6par291", translatorBridge);
        checkInMaps("strackp6par292", translatorBridge);
        //checkInMaps("strackp6par293", translatorBridge);
        //checkInMaps("strackp6par294", translatorBridge);
        //checkInMaps("strackp6par295", translatorBridge);
        checkInMaps("strackp6par296", translatorBridge);
        //checkInMaps("strackp6par297", translatorBridge);
        //checkInMaps("strackp6par298", translatorBridge);
        checkInMaps("strackp6par299", translatorBridge);
        checkInMaps("strackp6par301", translatorBridge);
        checkInMaps("strackp6par302", translatorBridge);
        //checkInMaps("strackp6par303", translatorBridge);
    }

    @Test
    public void test_strack_page10_par2() {
        //checkInMaps("strackp10par301", translatorBridge);
        checkInMaps("strackp10par302", translatorBridge);
        checkInMaps("strackp10par303", translatorBridge);
        checkInMaps("strackp10par304", translatorBridge);
        checkInMaps("strackp10par305", translatorBridge);
        checkInMaps("strackp10par306", translatorBridge);
        checkInMaps("strackp10par307", translatorBridge);
        checkInMaps("strackp10par308", translatorBridge);
        //checkInMaps("strackp10par309", translatorBridge);
        checkInMaps("strackp10par310", translatorBridge);
        checkInMaps("strackp10par311", translatorBridge);
        checkInMaps("strackp10par312", translatorBridge);
        //checkInMaps("strackp10par313", translatorBridge);
        checkInMaps("strackp10par314", translatorBridge);
        checkInMaps("strackp10par315", translatorBridge);
        checkInMaps("strackp10par316", translatorBridge);
        checkInMaps("strackp10par317", translatorBridge);
        checkInMaps("strackp10par318", translatorBridge);
        //checkInMaps("strackp10par319", translatorBridge);
        checkInMaps("strackp10par320", translatorBridge);
        checkInMaps("strackp10par321", translatorBridge);
        checkInMaps("strackp10par322", translatorBridge);
        checkInMaps("strackp10par323", translatorBridge);
        checkInMaps("strackp10par324", translatorBridge);
        checkInMaps("strackp10par325", translatorBridge);
        checkInMaps("strackp10par326", translatorBridge);
        checkInMaps("strackp10par327", translatorBridge);
        checkInMaps("strackp10par328", translatorBridge);
        checkInMaps("strackp10par329", translatorBridge);
        checkInMaps("strackp10par330", translatorBridge);
        checkInMaps("strackp10par331", translatorBridge);
        checkInMaps("strackp10par332", translatorBridge);
        checkInMaps("strackp10par333", translatorBridge);
        checkInMaps("strackp10par334", translatorBridge);
        //checkInMaps("strackp10par335", translatorBridge);
        //checkInMaps("strackp10par336", translatorBridge);
        //checkInMaps("strackp10par337", translatorBridge);
        //checkInMaps("strackp10par338", translatorBridge);
        //checkInMaps("strackp10par339", translatorBridge);
        checkInMaps("strackp10par340", translatorBridge);
        //checkInMaps("strackp10par341", translatorBridge);
        checkInMaps("strackp10par342", translatorBridge);
        checkInMaps("strackp10par343", translatorBridge);
        checkInMaps("strackp10par344", translatorBridge);
        checkInMaps("strackp10par345", translatorBridge);
        checkInMaps("strackp10par346", translatorBridge);
        //checkInMaps("strackp10par347", translatorBridge);
    }

    @Test
    public void test_strack_page11_par1() {
        checkInMaps("strackp11par001", translatorBridge);
        //checkInMaps("strackp11par002", translatorBridge);
        checkInMaps("strackp11par003", translatorBridge);
        checkInMaps("strackp11par004", translatorBridge);
        checkInMaps("strackp11par005", translatorBridge);
        checkInMaps("strackp11par006", translatorBridge);
        //checkInMaps("strackp11par007", translatorBridge);
        //checkInMaps("strackp11par008", translatorBridge);
        checkInMaps("strackp11par009", translatorBridge);
        checkInMaps("strackp11par010", translatorBridge);
        checkInMaps("strackp11par011", translatorBridge);
        checkInMaps("strackp11par012", translatorBridge);
        //checkInMaps("strackp11par013", translatorBridge);
        checkInMaps("strackp11par014", translatorBridge);
        checkInMaps("strackp11par015", translatorBridge);
        checkInMaps("strackp11par016", translatorBridge);
        checkInMaps("strackp11par017", translatorBridge);
        checkInMaps("strackp11par018", translatorBridge);
        //checkInMaps("strackp11par019", translatorBridge);
        checkInMaps("strackp11par020", translatorBridge);
        checkInMaps("strackp11par021", translatorBridge);
        checkInMaps("strackp11par022", translatorBridge);
        checkInMaps("strackp11par023", translatorBridge);
        checkInMaps("strackp11par024", translatorBridge);
        checkInMaps("strackp11par025", translatorBridge);
        checkInMaps("strackp11par026", translatorBridge);
        //checkInMaps("strackp11par027", translatorBridge);
        checkInMaps("strackp11par028", translatorBridge);
        checkInMaps("strackp11par029", translatorBridge);
        checkInMaps("strackp11par030", translatorBridge);
        checkInMaps("strackp11par031", translatorBridge);
        //checkInMaps("strackp11par032", translatorBridge);
        checkInMaps("strackp11par033", translatorBridge);
        checkInMaps("strackp11par034", translatorBridge);
        checkInMaps("strackp11par035", translatorBridge);
        checkInMaps("strackp11par036", translatorBridge);
        checkInMaps("strackp11par037", translatorBridge);
        checkInMaps("strackp11par038", translatorBridge);
        checkInMaps("strackp11par039", translatorBridge);
        checkInMaps("strackp11par040", translatorBridge);
        checkInMaps("strackp11par041", translatorBridge);
        checkInMaps("strackp11par042", translatorBridge);
        checkInMaps("strackp11par043", translatorBridge);
        //checkInMaps("strackp11par044", translatorBridge);
        checkInMaps("strackp11par045", translatorBridge);
        checkInMaps("strackp11par046", translatorBridge);
        checkInMaps("strackp11par047", translatorBridge);
        //checkInMaps("strackp11par048", translatorBridge);
        checkInMaps("strackp11par049", translatorBridge);
        checkInMaps("strackp11par050", translatorBridge);
        checkInMaps("strackp11par051", translatorBridge);
        checkInMaps("strackp11par052", translatorBridge);
        //checkInMaps("strackp11par053", translatorBridge);
        checkInMaps("strackp11par054", translatorBridge);
        //checkInMaps("strackp11par055", translatorBridge);
        checkInMaps("strackp11par056", translatorBridge);
        checkInMaps("strackp11par057", translatorBridge);
        //checkInMaps("strackp11par058", translatorBridge);
        checkInMaps("strackp11par059", translatorBridge);
        //checkInMaps("strackp11par060", translatorBridge);
        checkInMaps("strackp11par061", translatorBridge);
        checkInMaps("strackp11par062", translatorBridge);
        //checkInMaps("strackp11par063", translatorBridge);
        checkInMaps("strackp11par064", translatorBridge);
        checkInMaps("strackp11par065", translatorBridge);
        checkInMaps("strackp11par066", translatorBridge);
        checkInMaps("strackp11par067", translatorBridge);
        //checkInMaps("strackp11par068", translatorBridge);
        checkInMaps("strackp11par069", translatorBridge);
        checkInMaps("strackp11par070", translatorBridge);
        checkInMaps("strackp11par071", translatorBridge);
        checkInMaps("strackp11par072", translatorBridge);
        checkInMaps("strackp11par073", translatorBridge);
        checkInMaps("strackp11par074", translatorBridge);
        checkInMaps("strackp11par075", translatorBridge);
        checkInMaps("strackp11par076", translatorBridge);
        checkInMaps("strackp11par077", translatorBridge);
        checkInMaps("strackp11par078", translatorBridge);
        //checkInMaps("strackp11par079", translatorBridge);
        checkInMaps("strackp11par080", translatorBridge);
        checkInMaps("strackp11par081", translatorBridge);
        //checkInMaps("strackp11par082", translatorBridge);
        checkInMaps("strackp11par083", translatorBridge);
        checkInMaps("strackp11par084", translatorBridge);
        checkInMaps("strackp11par085", translatorBridge);
        checkInMaps("strackp11par086", translatorBridge);
        checkInMaps("strackp11par087", translatorBridge);
        checkInMaps("strackp11par088", translatorBridge);
        //checkInMaps("strackp11par089", translatorBridge);
        checkInMaps("strackp11par090", translatorBridge);
        checkInMaps("strackp11par091", translatorBridge);
        checkInMaps("strackp11par092", translatorBridge);
        checkInMaps("strackp11par093", translatorBridge);
        checkInMaps("strackp11par094", translatorBridge);
        checkInMaps("strackp11par095", translatorBridge);
        checkInMaps("strackp11par096", translatorBridge);
        checkInMaps("strackp11par097", translatorBridge);
        checkInMaps("strackp11par098", translatorBridge);
        checkInMaps("strackp11par099", translatorBridge);
        checkInMaps("strackp11par100", translatorBridge);
        checkInMaps("strackp11par101", translatorBridge);
    }

    @Test
    public void test_genesis_chapter1() {
        checkInMaps("genesis1A", translatorBridge);
        checkInMaps("genesis1B", translatorBridge);
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
        checkInMaps("nietzscheBookGTversuchChap1M", translatorBridge);
        //checkInMaps("nietzscheBookGTversuchChap1N", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1O", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1P", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1Q", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1R", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1S", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1T", translatorBridge);
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
        checkInMaps("nietzscheBookGTversuchCha4U", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4V", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4W", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap05() {
        checkInMaps("nietzscheBookGTversuchCha5A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5C", translatorBridge);
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
        checkInMaps("nietzscheBookGTversuchCha5U2", translatorBridge);
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
        checkInMaps("nietzscheBookGTversuchCha6N", translatorBridge);
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
        checkInMaps("nietzscheBookGTversuchCha7S1", translatorBridge);
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
        checkInMaps("nietzscheBookGTWagner05", translatorBridge);
        checkInMaps("nietzscheBookGTWagner06", translatorBridge);
        checkInMaps("nietzscheBookGTWagner07", translatorBridge);
        //checkInMaps("nietzscheBookGTWagner08", translatorBridge);
        checkInMaps("nietzscheBookGTWagner09", translatorBridge);
        checkInMaps("nietzscheBookGTWagner10", translatorBridge);
        checkInMaps("nietzscheBookGTWagner11", translatorBridge);
        checkInMaps("nietzscheBookGTWagner12", translatorBridge);
        checkInMaps("nietzscheBookGTWagner13", translatorBridge);
        checkInMaps("nietzscheBookGTWagner14", translatorBridge);
        checkInMaps("nietzscheBookGTWagner15", translatorBridge);
        checkInMaps("nietzscheBookGTWagner16", translatorBridge);
        checkInMaps("nietzscheBookGTWagner17", translatorBridge);
        checkInMaps("nietzscheBookGTWagner18", translatorBridge);
        checkInMaps("nietzscheBookGTWagner19", translatorBridge);
        checkInMaps("nietzscheBookGTWagner20", translatorBridge);
        checkInMaps("nietzscheBookGTWagner21", translatorBridge);
        checkInMaps("nietzscheBookGTWagner22", translatorBridge);
        checkInMaps("nietzscheBookGTWagner23", translatorBridge);
        checkInMaps("nietzscheBookGTWagner24", translatorBridge);
        checkInMaps("nietzscheBookGTWagner25", translatorBridge);
        checkInMaps("nietzscheBookGTWagner26", translatorBridge);
        checkInMaps("nietzscheBookGTWagner27", translatorBridge);
        checkInMaps("nietzscheBookGTWagner28", translatorBridge);
        checkInMaps("nietzscheBookGTWagner29", translatorBridge);
    }

    @Test
    public void test_nietzsche_naissance_tragedie_chap1() {
        //checkInMaps("nietzscheBookGTChap1001", translatorBridge);
        //checkInMaps("nietzscheBookGTChap1002", translatorBridge);
        checkInMaps("nietzscheBookGTChap1003", translatorBridge);
        checkInMaps("nietzscheBookGTChap1004", translatorBridge);
        checkInMaps("nietzscheBookGTChap1005", translatorBridge);
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
        checkInMaps("nietzscheBookGTChap1017", translatorBridge);
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
        checkInMaps("nietzscheBookGTChap1032", translatorBridge);
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
        checkInMaps("nietzscheBookGTChap1045", translatorBridge);
        checkInMaps("nietzscheBookGTChap1046", translatorBridge);
        checkInMaps("nietzscheBookGTChap1047", translatorBridge);
        checkInMaps("nietzscheBookGTChap1048", translatorBridge);
        checkInMaps("nietzscheBookGTChap1049", translatorBridge);
        checkInMaps("nietzscheBookGTChap1050", translatorBridge);
        checkInMaps("nietzscheBookGTChap1051", translatorBridge);
        checkInMaps("nietzscheBookGTChap1052", translatorBridge);
        checkInMaps("nietzscheBookGTChap1053", translatorBridge);
        checkInMaps("nietzscheBookGTChap1054", translatorBridge);
        checkInMaps("nietzscheBookGTChap1055", translatorBridge);
        checkInMaps("nietzscheBookGTChap1056", translatorBridge);
        checkInMaps("nietzscheBookGTChap1057", translatorBridge);
        checkInMaps("nietzscheBookGTChap1058", translatorBridge);
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
        checkInMaps("lecon1G", translatorBridge);
    }
}

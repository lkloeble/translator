import org.junit.Before;
import org.junit.Test;
import org.patrologia.translator.TranslatorBridge;
import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.casenumbergenre.german.GermanCaseFactory;
import org.patrologia.translator.conjugation.german.GermanConjugationFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.german.GermanDeclension;
import org.patrologia.translator.declension.german.GermanDeclensionFactory;
import org.patrologia.translator.linguisticimplementations.FrenchTranslator;
import org.patrologia.translator.linguisticimplementations.GermanAnalyzer;
import org.patrologia.translator.linguisticimplementations.Translator;
import org.patrologia.translator.rule.german.GermanRuleFactory;
import org.patrologia.translator.utils.Analizer;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertTrue;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    @Before
    public void init() {
        String prepositionFileDescription = "C:\\translator\\src\\main\\resources\\german\\prepositions.txt";
        String nounFileDescription = "C:\\translator\\src\\main\\resources\\german\\nouns.txt";
        String verbFileDescription = "C:\\translator\\src\\main\\resources\\german\\verbs.txt";
        String germanFrenchDataFile = "C:\\translator\\src\\main\\resources\\german\\robert_collins_german_to_french.txt";
        String frenchVerbsDataFile = "C:\\translator\\src\\main\\resources\\french_verbs.txt";
        String declensionPath = "C:\\translator\\src\\main\\resources\\german\\declensions";
        String declensionsAndFiles = "C:\\translator\\src\\main\\resources\\german\\declensionsAndFiles.txt";
        String conjugationPath = "C:\\translator\\src\\main\\resources\\german\\conjugations";
        String conjugationsAndFiles = "C:\\translator\\src\\main\\resources\\german\\conjugationsAndFiles.txt";
        String germanPathFile = "C:\\translator\\src\\test\\resources\\german_content.txt";
        String germanResultFile = "C:\\translator\\src\\test\\resources\\german_expected_results.txt";
        GermanDeclensionFactory germanDeclensionFactory = new GermanDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionPath));
        VerbRepository verbRepository = new VerbRepository(new GermanConjugationFactory(getGermanConjugations(conjugationsAndFiles), getGermanConjugationDefinitions(conjugationsAndFiles, conjugationPath)), Language.GERMAN, getVerbs(verbFileDescription));
        GermanRuleFactory ruleFactory = new GermanRuleFactory(verbRepository);
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.GERMAN, new GermanCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        NounRepository nounRepository = new NounRepository(Language.GERMAN, germanDeclensionFactory, getFileContentForRepository(nounFileDescription));
        Analizer germanAnalyzer = new GermanAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getFileContentForRepository(germanFrenchDataFile), getFileContentForRepository(frenchVerbsDataFile), verbRepository, nounRepository, declensionPath, declensionsAndFiles, germanDeclensionFactory);
        translatorBridge = new TranslatorBridge(germanAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(germanPathFile);
        mapValuesForResult = loadMapFromFiles(germanResultFile);
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
                "sum@IRREGULAR%[IPR]=[sum,es,est,sumus,estis,sunt]%[AII]=[eram,eras,erat,eramus,eratis,erant]%[AIF]=[ero,eris,erit,erimus,eritis,erunt]%[INFINITIVE]=[esse]%[ASP]=[sim,sis,sit,simus,sitis,sint]%[ASI]=[essem,esses,esset,essemus,essetis,essent]%[AIP]=[fui,fuisti,fuit,fuimus,fuistis,fuerunt]%[AIPP]=[fueram,fueras,fuerat,fueramus,fueratis,fuerant]%[IAP]=[fuisse]%[AIFP]=[fuero,fueris,fuerit,fuerimus,fueritis,fuerint]",
                "sum,o,is,ere,,,[o-is]"
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
        for(String conjugationName : conjugationNameList) {
            String parts[] = conjugationName.split("%");
            String fileName = parts[1];
            String nameOnly = parts[0];
            germanConjugationDefinitionsMap.put(nameOnly, getConjugationElements(directory,fileName));
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
        checkInMaps("lecon7A", translatorBridge);
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
        checkInMaps("lecon9E", translatorBridge);
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
        checkInMaps("lecon12F", translatorBridge);
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
        checkInMaps("lecon15D", translatorBridge);
        checkInMaps("lecon15E", translatorBridge);
        checkInMaps("lecon15F", translatorBridge);
        checkInMaps("lecon15G", translatorBridge);
        checkInMaps("lecon15H", translatorBridge);
        checkInMaps("lecon15I", translatorBridge);
    }

    @Test
    public void test_allemand_debutant_chapter_16() {
        checkInMaps("lecon16A", translatorBridge);
        checkInMaps("lecon16B", translatorBridge);
        checkInMaps("lecon16C", translatorBridge);
        checkInMaps("lecon16D", translatorBridge);
        checkInMaps("lecon16E", translatorBridge);
        checkInMaps("lecon16F", translatorBridge);
        checkInMaps("lecon16G", translatorBridge);
        checkInMaps("lecon16H", translatorBridge);
        checkInMaps("lecon16I", translatorBridge);
        checkInMaps("lecon16J", translatorBridge);
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
        checkInMaps("lecon18E", translatorBridge);
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
        checkInMaps("strack3", translatorBridge);
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
        checkInMaps("strack17", translatorBridge);
        checkInMaps("strack18", translatorBridge);
        checkInMaps("strack19", translatorBridge);
        checkInMaps("strack20", translatorBridge);
        checkInMaps("strack21", translatorBridge);
        checkInMaps("strack22", translatorBridge);
        checkInMaps("strack23", translatorBridge);
        checkInMaps("strack24", translatorBridge);
        checkInMaps("strack25", translatorBridge);
        checkInMaps("strack26", translatorBridge);
        checkInMaps("strack27", translatorBridge);
        checkInMaps("strack28", translatorBridge);
        checkInMaps("strack29", translatorBridge);
    }

    @Test
    public void test_strack_sources() {
        checkInMaps("stracksourceA", translatorBridge);
        checkInMaps("stracksourceB", translatorBridge);
        checkInMaps("stracksourceC", translatorBridge);
        checkInMaps("stracksourceD", translatorBridge);
        checkInMaps("stracksourceE", translatorBridge);
        checkInMaps("stracksourceF", translatorBridge);
        checkInMaps("stracksourceG", translatorBridge);
        checkInMaps("stracksourceH", translatorBridge);
        checkInMaps("stracksourceI", translatorBridge);
        checkInMaps("stracksourceJ", translatorBridge);
        checkInMaps("stracksourceK", translatorBridge);
        checkInMaps("stracksourceL", translatorBridge);
        checkInMaps("stracksourceM", translatorBridge);
        checkInMaps("stracksourceN", translatorBridge);
        checkInMaps("stracksourceO", translatorBridge);
        checkInMaps("stracksourceP", translatorBridge);
        checkInMaps("stracksourceQ", translatorBridge);
        checkInMaps("stracksourceR", translatorBridge);
        checkInMaps("stracksourceS", translatorBridge);
        checkInMaps("stracksourceT", translatorBridge);
        checkInMaps("stracksourceU", translatorBridge);
        checkInMaps("stracksourceV", translatorBridge);
        checkInMaps("stracksourceW", translatorBridge);
        checkInMaps("stracksourceX", translatorBridge);
        checkInMaps("stracksourceY", translatorBridge);
        checkInMaps("stracksourceZ", translatorBridge);
        checkInMaps("stracksourceAA", translatorBridge);
        checkInMaps("stracksourceBB", translatorBridge);
        checkInMaps("stracksourceCC", translatorBridge);
        checkInMaps("stracksourceDD", translatorBridge);
        checkInMaps("stracksourceEE", translatorBridge);
        checkInMaps("stracksourceFF", translatorBridge);
        checkInMaps("stracksourceGG", translatorBridge);
        checkInMaps("stracksourceHH", translatorBridge);
        checkInMaps("stracksourceII", translatorBridge);
        checkInMaps("stracksourceJJ", translatorBridge);
        checkInMaps("stracksourceKK", translatorBridge);
        checkInMaps("stracksourceLL", translatorBridge);
        checkInMaps("stracksourceMM", translatorBridge);
        checkInMaps("stracksourceNN", translatorBridge);
        checkInMaps("stracksourceOO", translatorBridge);
        checkInMaps("stracksourcePP", translatorBridge);
        checkInMaps("stracksourceQQ", translatorBridge);
        checkInMaps("stracksourceRR", translatorBridge);
        checkInMaps("stracksourceSS", translatorBridge);
        checkInMaps("stracksourceTT", translatorBridge);
        checkInMaps("stracksourceUU", translatorBridge);
        checkInMaps("stracksourceVV", translatorBridge);
        checkInMaps("stracksourceWW", translatorBridge);
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
    public void test_talmud_help_strack()  {
        checkInMaps("strackp1notBA", translatorBridge);
        checkInMaps("strackp1notBB", translatorBridge);
        checkInMaps("strackp1notBC", translatorBridge);
        checkInMaps("strackp1notBD", translatorBridge);
        checkInMaps("strackp1notBE", translatorBridge);
        checkInMaps("strackp1notBF", translatorBridge);
        checkInMaps("strackp1notBG", translatorBridge);
        checkInMaps("strackp1notBH", translatorBridge);
    }

    @Test
    public void test_talmud_help_strack2() {
        checkInMaps("strackp3A", translatorBridge);
        checkInMaps("strackp3B", translatorBridge);
        checkInMaps("strackp3C", translatorBridge);
        checkInMaps("strackp3D", translatorBridge);
        checkInMaps("strackp3E", translatorBridge);
        checkInMaps("strackp3F", translatorBridge);
        checkInMaps("strackp3G", translatorBridge);
        checkInMaps("strackp3H", translatorBridge);
        checkInMaps("strackp3notA", translatorBridge);
        checkInMaps("strackp3notB", translatorBridge);
        checkInMaps("strackp3notC", translatorBridge);
        checkInMaps("strackp3notD", translatorBridge);
        checkInMaps("strackp3notE", translatorBridge);
        checkInMaps("strackp3notF", translatorBridge);
        checkInMaps("strackp3notG", translatorBridge);
        checkInMaps("strackp3notH", translatorBridge);
        checkInMaps("strackp3notI", translatorBridge);
        checkInMaps("strackp3notJ", translatorBridge);
    }

        @Test
    public void test_genesis_chapter1() {
        checkInMaps("genesis1A", translatorBridge);
        checkInMaps("genesis1B", translatorBridge);
        checkInMaps("genesis1C", translatorBridge);
        checkInMaps("genesis1D", translatorBridge);
        checkInMaps("genesis1E", translatorBridge);
        //checkInMaps("genesis1F", translatorBridge);
        //checkInMaps("genesis1G", translatorBridge);
        //checkInMaps("genesis1H", translatorBridge);
        checkInMaps("genesis1I", translatorBridge);
        checkInMaps("genesis1J", translatorBridge);
        checkInMaps("genesis1K", translatorBridge);
        checkInMaps("genesis1L", translatorBridge);
        checkInMaps("genesis1M", translatorBridge);
        //checkInMaps("genesis1N", translatorBridge);
        //checkInMaps("genesis1O", translatorBridge);
        checkInMaps("genesis1P", translatorBridge);
        //checkInMaps("genesis1Q", translatorBridge);
        checkInMaps("genesis1R", translatorBridge);
        checkInMaps("genesis1S", translatorBridge);
        checkInMaps("genesis1T", translatorBridge);
        checkInMaps("genesis1U", translatorBridge);
        checkInMaps("genesis1V", translatorBridge);
        checkInMaps("genesis1W", translatorBridge);
        checkInMaps("genesis1X", translatorBridge);
        checkInMaps("genesis1Y", translatorBridge);
        checkInMaps("genesis1Z", translatorBridge);
        checkInMaps("genesis1AA", translatorBridge);
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
    public void test_nietzsche_birth_tragedy_firstbook_chap01() {
        checkInMaps("nietzscheBook1", translatorBridge);
        checkInMaps("nietzscheBook2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1A1", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1A2", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1D", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1I", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1J", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1K", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1L", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1M", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1N", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1O", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1P", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1Q", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1R", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1S", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap1T", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap02() {
        checkInMaps("nietzscheBookGTversuchChap2A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2D", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchChap2H", translatorBridge);
    }

    @Test
    public  void test_nietzsche_birth_tragedy_firstbook_chap03() {
        checkInMaps("nietzscheBookGTversuchCha3A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3D", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha3I", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap04() {
        checkInMaps("nietzscheBookGTversuchCha4A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4D", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4I", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4J", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4K", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4L", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4M", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4N", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4O", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4P", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4Q", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4R", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha4S", translatorBridge);
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
        checkInMaps("nietzscheBookGTversuchCha5E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5I", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5J", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5K", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5L", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5M", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5N", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5O", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5P", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5Q", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5R", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5S", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5T", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5U", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5V", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5W", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha5X", translatorBridge);
    }

    @Test
    public void test_nietzsche_birth_tragedy_firstbook_chap06() {
        checkInMaps("nietzscheBookGTversuchCha6A", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6D", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha6H", translatorBridge);
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
        checkInMaps("nietzscheBookGTversuchCha7B", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7C", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7D", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7E", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7F", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7G", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7H", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7I", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7J", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7K", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7L", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7M", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7N", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7O", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7P", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7Q", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7R", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7S", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7T", translatorBridge);
        checkInMaps("nietzscheBookGTversuchCha7U", translatorBridge);
    }
    
    @Test
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("strackp3B", translatorBridge);
    }
}

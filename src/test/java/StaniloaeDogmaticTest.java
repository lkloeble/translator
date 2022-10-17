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

import java.util.*;

import static junit.framework.Assert.assertTrue;
import static patrologia.translator.basicelements.Language.ROMANIAN;

public class StaniloaeDogmaticTest  extends TranslatorBridgeTest {

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
        String romanianPathFile = localTestPath + "staniloae_dogmatic_content.txt";
        String romanianResultFile = localTestPath + "staniloae_dogmatic_expected_results.txt";
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
                "par,ea,[face],(PAP*par*parut)"
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
        //checkInMaps("staniloae1W", translatorBridge);
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
        //checkInMaps("staniloae1HH", translatorBridge);
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
        //checkInMaps("staniloae5K", translatorBridge);
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
        //checkInMaps("staniloae7AC", translatorBridge);
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
        //checkInMaps("staniloae9A001", translatorBridge);
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
        //checkInMaps("staniloae9A031", translatorBridge);
        checkInMaps("staniloae9A032", translatorBridge);
        checkInMaps("staniloae9A033", translatorBridge);
        checkInMaps("staniloae9A034", translatorBridge);
        //checkInMaps("staniloae9A035", translatorBridge);
        checkInMaps("staniloae9A036", translatorBridge);
        checkInMaps("staniloae9A037", translatorBridge);
        checkInMaps("staniloae9A038", translatorBridge);
    }

    @Test
    public void test_staniloae_chap1end() {
        checkInMaps("staniloaeEndChap1_01", translatorBridge);
        checkInMaps("staniloaeEndChap1_02", translatorBridge);
        checkInMaps("staniloaeEndChap1_03", translatorBridge);
        checkInMaps("staniloaeEndChap1_04", translatorBridge);
        //checkInMaps("staniloaeEndChap1_05", translatorBridge);
        checkInMaps("staniloaeEndChap1_06", translatorBridge);
        checkInMaps("staniloaeEndChap1_07", translatorBridge);
        checkInMaps("staniloaeEndChap1_08", translatorBridge);
        checkInMaps("staniloaeEndChap1_09", translatorBridge);
        //checkInMaps("staniloaeEndChap1_10", translatorBridge);
        //checkInMaps("staniloaeEndChap1_11", translatorBridge);
        checkInMaps("staniloaeEndChap1_12", translatorBridge);
        checkInMaps("staniloaeEndChap1_13", translatorBridge);
        checkInMaps("staniloaeEndChap1_14", translatorBridge);
        checkInMaps("staniloaeEndChap1_15", translatorBridge);
        checkInMaps("staniloaeEndChap1_16", translatorBridge);
        checkInMaps("staniloaeEndChap1_17", translatorBridge);
        checkInMaps("staniloaeEndChap1_18", translatorBridge);
        checkInMaps("staniloaeEndChap1_19", translatorBridge);
        checkInMaps("staniloaeEndChap1_20", translatorBridge);
        checkInMaps("staniloaeEndChap1_21", translatorBridge);
        //checkInMaps("staniloaeEndChap1_22", translatorBridge);
        //checkInMaps("staniloaeEndChap1_23", translatorBridge);
        //checkInMaps("staniloaeEndChap1_24", translatorBridge);
        checkInMaps("staniloaeEndChap1_25", translatorBridge);
        checkInMaps("staniloaeEndChap1_26", translatorBridge);
        //checkInMaps("staniloaeEndChap1_27", translatorBridge);
        checkInMaps("staniloaeEndChap1_28", translatorBridge);
        checkInMaps("staniloaeEndChap1_29", translatorBridge);
        checkInMaps("staniloaeEndChap1_30", translatorBridge);
        checkInMaps("staniloaeEndChap1_31", translatorBridge);
        checkInMaps("staniloaeEndChap1_32", translatorBridge);
        //checkInMaps("staniloaeEndChap1_33", translatorBridge);
        checkInMaps("staniloaeEndChap1_34", translatorBridge);
        checkInMaps("staniloaeEndChap1_35", translatorBridge);
        //checkInMaps("staniloaeEndChap1_36", translatorBridge);
        checkInMaps("staniloaeEndChap1_37", translatorBridge);
        //checkInMaps("staniloaeEndChap1_38", translatorBridge);
        checkInMaps("staniloaeEndChap1_39", translatorBridge);
        checkInMaps("staniloaeEndChap1_40", translatorBridge);
        checkInMaps("staniloaeEndChap1_41", translatorBridge);
        checkInMaps("staniloaeEndChap1_42", translatorBridge);
        checkInMaps("staniloaeEndChap1_43", translatorBridge);
        //checkInMaps("staniloaeEndChap1_44", translatorBridge);
        checkInMaps("staniloaeEndChap1_45", translatorBridge);
    }

    @Test
    public void test_chapitre2() {
        checkInMaps("staniloaeChap2000", translatorBridge);
        checkInMaps("staniloaeChap2001", translatorBridge);
        checkInMaps("staniloaeChap2002", translatorBridge);
        checkInMaps("staniloaeChap2003", translatorBridge);
        //checkInMaps("staniloaeChap2004", translatorBridge);
        checkInMaps("staniloaeChap2005", translatorBridge);
        checkInMaps("staniloaeChap2006", translatorBridge);
        checkInMaps("staniloaeChap2007", translatorBridge);
        checkInMaps("staniloaeChap2008", translatorBridge);
        //checkInMaps("staniloaeChap2009", translatorBridge);
        checkInMaps("staniloaeChap2010", translatorBridge);
        //checkInMaps("staniloaeChap2011", translatorBridge);
        checkInMaps("staniloaeChap2012", translatorBridge);
        //checkInMaps("staniloaeChap2013", translatorBridge);
        checkInMaps("staniloaeChap2014", translatorBridge);
        checkInMaps("staniloaeChap2015", translatorBridge);
        checkInMaps("staniloaeChap2016", translatorBridge);
        //checkInMaps("staniloaeChap2017", translatorBridge);
        checkInMaps("staniloaeChap2018", translatorBridge);
        checkInMaps("staniloaeChap2019", translatorBridge);
        checkInMaps("staniloaeChap2020", translatorBridge);
        checkInMaps("staniloaeChap2021", translatorBridge);
        checkInMaps("staniloaeChap2022", translatorBridge);
        checkInMaps("staniloaeChap2023", translatorBridge);
        checkInMaps("staniloaeChap2024", translatorBridge);
        checkInMaps("staniloaeChap2025", translatorBridge);
        checkInMaps("staniloaeChap2026", translatorBridge);
        checkInMaps("staniloaeChap2027", translatorBridge);
        checkInMaps("staniloaeChap2028", translatorBridge);
        checkInMaps("staniloaeChap2029", translatorBridge);
        checkInMaps("staniloaeChap2030", translatorBridge);
        checkInMaps("staniloaeChap2031", translatorBridge);
        checkInMaps("staniloaeChap2032", translatorBridge);
        checkInMaps("staniloaeChap2033", translatorBridge);
        checkInMaps("staniloaeChap2034", translatorBridge);
        //checkInMaps("staniloaeChap2035", translatorBridge);
        checkInMaps("staniloaeChap2036", translatorBridge);
        checkInMaps("staniloaeChap2037", translatorBridge);
        checkInMaps("staniloaeChap2038", translatorBridge);
        checkInMaps("staniloaeChap2039", translatorBridge);
        checkInMaps("staniloaeChap2040", translatorBridge);
        checkInMaps("staniloaeChap2041", translatorBridge);
        checkInMaps("staniloaeChap2042", translatorBridge);
        checkInMaps("staniloaeChap2043", translatorBridge);
        checkInMaps("staniloaeChap2044", translatorBridge);
        //checkInMaps("staniloaeChap2045", translatorBridge);
        checkInMaps("staniloaeChap2046", translatorBridge);
        checkInMaps("staniloaeChap2047", translatorBridge);
        checkInMaps("staniloaeChap2048", translatorBridge);
        checkInMaps("staniloaeChap2049", translatorBridge);
        checkInMaps("staniloaeChap2050", translatorBridge);
        checkInMaps("staniloaeChap2051", translatorBridge);
        checkInMaps("staniloaeChap2052", translatorBridge);
        checkInMaps("staniloaeChap2053", translatorBridge);
        //checkInMaps("staniloaeChap2054", translatorBridge);
        checkInMaps("staniloaeChap2055", translatorBridge);
        //checkInMaps("staniloaeChap2056", translatorBridge);
        checkInMaps("staniloaeChap2057", translatorBridge);
        checkInMaps("staniloaeChap2058", translatorBridge);
        checkInMaps("staniloaeChap2059", translatorBridge);
        checkInMaps("staniloaeChap2060", translatorBridge);
        checkInMaps("staniloaeChap2061", translatorBridge);
        checkInMaps("staniloaeChap2062", translatorBridge);
        checkInMaps("staniloaeChap2063", translatorBridge);
        checkInMaps("staniloaeChap2064", translatorBridge);
        checkInMaps("staniloaeChap2065", translatorBridge);
        checkInMaps("staniloaeChap2066", translatorBridge);
        checkInMaps("staniloaeChap2067", translatorBridge);
        checkInMaps("staniloaeChap2068", translatorBridge);
        checkInMaps("staniloaeChap2069", translatorBridge);
        checkInMaps("staniloaeChap2070", translatorBridge);
        checkInMaps("staniloaeChap2071", translatorBridge);
        checkInMaps("staniloaeChap2072", translatorBridge);
        checkInMaps("staniloaeChap2073", translatorBridge);
        checkInMaps("staniloaeChap2074", translatorBridge);
        checkInMaps("staniloaeChap2075", translatorBridge);
        checkInMaps("staniloaeChap2076", translatorBridge);
        checkInMaps("staniloaeChap2077", translatorBridge);
        checkInMaps("staniloaeChap2078", translatorBridge);
        checkInMaps("staniloaeChap2079", translatorBridge);
        checkInMaps("staniloaeChap2080", translatorBridge);
        checkInMaps("staniloaeChap2081", translatorBridge);
        checkInMaps("staniloaeChap2082", translatorBridge);
        checkInMaps("staniloaeChap2083", translatorBridge);
        checkInMaps("staniloaeChap2084", translatorBridge);
        checkInMaps("staniloaeChap2085", translatorBridge);
        checkInMaps("staniloaeChap2086", translatorBridge);
        checkInMaps("staniloaeChap2087", translatorBridge);
        checkInMaps("staniloaeChap2088", translatorBridge);
        //checkInMaps("staniloaeChap2089", translatorBridge);
        checkInMaps("staniloaeChap2090", translatorBridge);
        checkInMaps("staniloaeChap2091", translatorBridge);
        checkInMaps("staniloaeChap2092", translatorBridge);
        checkInMaps("staniloaeChap2093", translatorBridge);
        checkInMaps("staniloaeChap2094", translatorBridge);
        checkInMaps("staniloaeChap2095", translatorBridge);
        //checkInMaps("staniloaeChap2096", translatorBridge);
        checkInMaps("staniloaeChap2097", translatorBridge);
        checkInMaps("staniloaeChap2098", translatorBridge);
        checkInMaps("staniloaeChap2099", translatorBridge);
        checkInMaps("staniloaeChap2100", translatorBridge);
        checkInMaps("staniloaeChap2101", translatorBridge);
        checkInMaps("staniloaeChap2102", translatorBridge);
        checkInMaps("staniloaeChap2103", translatorBridge);
        checkInMaps("staniloaeChap2104", translatorBridge);
        checkInMaps("staniloaeChap2105", translatorBridge);
        checkInMaps("staniloaeChap2106", translatorBridge);
        checkInMaps("staniloaeChap2107", translatorBridge);
        checkInMaps("staniloaeChap2108", translatorBridge);
        checkInMaps("staniloaeChap2109", translatorBridge);
        checkInMaps("staniloaeChap2110", translatorBridge);
        checkInMaps("staniloaeChap2111", translatorBridge);
        checkInMaps("staniloaeChap2112", translatorBridge);
        checkInMaps("staniloaeChap2113", translatorBridge);
        checkInMaps("staniloaeChap2114", translatorBridge);
        checkInMaps("staniloaeChap2115", translatorBridge);
        checkInMaps("staniloaeChap2116", translatorBridge);
        checkInMaps("staniloaeChap2117", translatorBridge);
        checkInMaps("staniloaeChap2118", translatorBridge);
        checkInMaps("staniloaeChap2119", translatorBridge);
        //checkInMaps("staniloaeChap2120", translatorBridge);
        checkInMaps("staniloaeChap2121", translatorBridge);
        checkInMaps("staniloaeChap2122", translatorBridge);
        checkInMaps("staniloaeChap2123", translatorBridge);
        checkInMaps("staniloaeChap2124", translatorBridge);
        //checkInMaps("staniloaeChap2125", translatorBridge);
        checkInMaps("staniloaeChap2126", translatorBridge);
        checkInMaps("staniloaeChap2127", translatorBridge);
        //checkInMaps("staniloaeChap2128", translatorBridge);
        checkInMaps("staniloaeChap2129", translatorBridge);
        checkInMaps("staniloaeChap2130", translatorBridge);
        checkInMaps("staniloaeChap2131", translatorBridge);
        checkInMaps("staniloaeChap2132", translatorBridge);
        checkInMaps("staniloaeChap2133", translatorBridge);
        checkInMaps("staniloaeChap2134", translatorBridge);
        checkInMaps("staniloaeChap2135", translatorBridge);
        checkInMaps("staniloaeChap2136", translatorBridge);
        checkInMaps("staniloaeChap2137", translatorBridge);
        //checkInMaps("staniloaeChap2138", translatorBridge);
        checkInMaps("staniloaeChap2139", translatorBridge);
        checkInMaps("staniloaeChap2140", translatorBridge);
        checkInMaps("staniloaeChap2141", translatorBridge);
        checkInMaps("staniloaeChap2142", translatorBridge);
        //checkInMaps("staniloaeChap2143", translatorBridge);
        checkInMaps("staniloaeChap2144", translatorBridge);
        checkInMaps("staniloaeChap2145", translatorBridge);
        checkInMaps("staniloaeChap2146", translatorBridge);
        checkInMaps("staniloaeChap2147", translatorBridge);
        checkInMaps("staniloaeChap2148", translatorBridge);
        checkInMaps("staniloaeChap2149", translatorBridge);
        checkInMaps("staniloaeChap2150", translatorBridge);
        checkInMaps("staniloaeChap2151", translatorBridge);
        checkInMaps("staniloaeChap2152", translatorBridge);

    }

    @Test
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
        //checkInMaps("bocaseumpluA112", translatorBridge);
    }

}

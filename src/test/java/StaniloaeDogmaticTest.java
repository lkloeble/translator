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
                        "pierde@verb!norm%1(verb)=perdre",
                        "se@prep%1(prep)=se"
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
                "instiintsar@fem%berea"
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
                "pier,de,[vinde],(AII*pie*pia@CONJ25*pier*piar@ACP*pier*arpier@ACPINF*pier*pierde)"
        });
         */
        return getFileContentForRepository(verbFileDescription);
    }

    private List<String> getFrenchVerbs(String frenchVerbFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "succeder@NORM%[INFINITIVE]=[succéder]%[IPR]=[succède,succèdes,succède,succèdons,succèdez,succèdent]%[ACP]=[succèderais,succèderais,succèderait,succèderions,succèderiez,succèderaient]"
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
    public void test_chapitre3() {
        checkInMaps("StaniloaeChap2501", translatorBridge);
        checkInMaps("StaniloaeChap2502", translatorBridge);
        checkInMaps("StaniloaeChap2503", translatorBridge);
        checkInMaps("StaniloaeChap2504", translatorBridge);
        checkInMaps("StaniloaeChap2505", translatorBridge);
        checkInMaps("StaniloaeChap2506", translatorBridge);
        checkInMaps("StaniloaeChap2507", translatorBridge);
        checkInMaps("StaniloaeChap2508", translatorBridge);
        checkInMaps("StaniloaeChap2509", translatorBridge);

        checkInMaps("StaniloaeChap2601", translatorBridge);
        checkInMaps("StaniloaeChap2602", translatorBridge);
        checkInMaps("StaniloaeChap2603", translatorBridge);
        checkInMaps("StaniloaeChap2604", translatorBridge);
        checkInMaps("StaniloaeChap2605", translatorBridge);
        checkInMaps("StaniloaeChap2606", translatorBridge);
        checkInMaps("StaniloaeChap2607", translatorBridge);
        checkInMaps("StaniloaeChap2608", translatorBridge);
        checkInMaps("StaniloaeChap2609", translatorBridge);
        checkInMaps("StaniloaeChap2610", translatorBridge);
        checkInMaps("StaniloaeChap2611", translatorBridge);
        checkInMaps("StaniloaeChap2612", translatorBridge);
        checkInMaps("StaniloaeChap2613", translatorBridge);
        checkInMaps("StaniloaeChap2614", translatorBridge);
        checkInMaps("StaniloaeChap2615", translatorBridge);
        checkInMaps("StaniloaeChap2616", translatorBridge);
        checkInMaps("StaniloaeChap2617", translatorBridge);
        checkInMaps("StaniloaeChap2618", translatorBridge);
        checkInMaps("StaniloaeChap2619", translatorBridge);
        checkInMaps("StaniloaeChap2620", translatorBridge);
        //checkInMaps("StaniloaeChap2621", translatorBridge);
        checkInMaps("StaniloaeChap2622", translatorBridge);
        checkInMaps("StaniloaeChap2623", translatorBridge);
        checkInMaps("StaniloaeChap2624", translatorBridge);
        checkInMaps("StaniloaeChap2625", translatorBridge);
        checkInMaps("StaniloaeChap2626", translatorBridge);
        checkInMaps("StaniloaeChap2627", translatorBridge);
        checkInMaps("StaniloaeChap2628", translatorBridge);
        checkInMaps("StaniloaeChap2629", translatorBridge);
        checkInMaps("StaniloaeChap2630", translatorBridge);
        checkInMaps("StaniloaeChap2631", translatorBridge);
        checkInMaps("StaniloaeChap2632", translatorBridge);
        checkInMaps("StaniloaeChap2633", translatorBridge);
        checkInMaps("StaniloaeChap2634", translatorBridge);
        checkInMaps("StaniloaeChap2635", translatorBridge);
        checkInMaps("StaniloaeChap2636", translatorBridge);
        checkInMaps("StaniloaeChap2637", translatorBridge);
        checkInMaps("StaniloaeChap2638", translatorBridge);
        checkInMaps("StaniloaeChap2639", translatorBridge);
        checkInMaps("StaniloaeChap2640", translatorBridge);
        checkInMaps("StaniloaeChap2641", translatorBridge);



        checkInMaps("StaniloaeChap2701", translatorBridge);
        checkInMaps("StaniloaeChap2702", translatorBridge);
        checkInMaps("StaniloaeChap2703", translatorBridge);
        checkInMaps("StaniloaeChap2704", translatorBridge);
        checkInMaps("StaniloaeChap2705", translatorBridge);
        checkInMaps("StaniloaeChap2706", translatorBridge);
        checkInMaps("StaniloaeChap2707", translatorBridge);
        checkInMaps("StaniloaeChap2708", translatorBridge);
        checkInMaps("StaniloaeChap2709", translatorBridge);
        checkInMaps("StaniloaeChap2710", translatorBridge);
        checkInMaps("StaniloaeChap2711", translatorBridge);
        checkInMaps("StaniloaeChap2712", translatorBridge);
        checkInMaps("StaniloaeChap2713", translatorBridge);
        checkInMaps("StaniloaeChap2714", translatorBridge);
        checkInMaps("StaniloaeChap2715", translatorBridge);
        checkInMaps("StaniloaeChap2716", translatorBridge);
        checkInMaps("StaniloaeChap2717", translatorBridge);
        checkInMaps("StaniloaeChap2718", translatorBridge);
        checkInMaps("StaniloaeChap2719", translatorBridge);
        checkInMaps("StaniloaeChap2720", translatorBridge);
        checkInMaps("StaniloaeChap2721", translatorBridge);
        checkInMaps("StaniloaeChap2722", translatorBridge);
        checkInMaps("StaniloaeChap2723", translatorBridge);
        checkInMaps("StaniloaeChap2724", translatorBridge);
        checkInMaps("StaniloaeChap2725", translatorBridge);
        checkInMaps("StaniloaeChap2726", translatorBridge);
        checkInMaps("StaniloaeChap2727", translatorBridge);
        checkInMaps("StaniloaeChap2728", translatorBridge);
        checkInMaps("StaniloaeChap2729", translatorBridge);
        checkInMaps("StaniloaeChap2730", translatorBridge);
        checkInMaps("StaniloaeChap2731", translatorBridge);
        checkInMaps("StaniloaeChap2732", translatorBridge);
        checkInMaps("StaniloaeChap2733", translatorBridge);
        checkInMaps("StaniloaeChap2734", translatorBridge);
        checkInMaps("StaniloaeChap2735", translatorBridge);
        checkInMaps("StaniloaeChap2736", translatorBridge);
        checkInMaps("StaniloaeChap2737", translatorBridge);
        checkInMaps("StaniloaeChap2738", translatorBridge);
        checkInMaps("StaniloaeChap2739", translatorBridge);
        checkInMaps("StaniloaeChap2740", translatorBridge);



        checkInMaps("StaniloaeChap2801", translatorBridge);
        checkInMaps("StaniloaeChap2802", translatorBridge);
        checkInMaps("StaniloaeChap2803", translatorBridge);
        checkInMaps("StaniloaeChap2804", translatorBridge);
        checkInMaps("StaniloaeChap2805", translatorBridge);
        checkInMaps("StaniloaeChap2806", translatorBridge);
        checkInMaps("StaniloaeChap2807", translatorBridge);
        checkInMaps("StaniloaeChap2808", translatorBridge);
        checkInMaps("StaniloaeChap2809", translatorBridge);
        checkInMaps("StaniloaeChap2810", translatorBridge);
        checkInMaps("StaniloaeChap2811", translatorBridge);
        checkInMaps("StaniloaeChap2812", translatorBridge);
        checkInMaps("StaniloaeChap2813", translatorBridge);
        checkInMaps("StaniloaeChap2814", translatorBridge);
        checkInMaps("StaniloaeChap2815", translatorBridge);
        checkInMaps("StaniloaeChap2816", translatorBridge);
        checkInMaps("StaniloaeChap2817", translatorBridge);
        checkInMaps("StaniloaeChap2818", translatorBridge);
        checkInMaps("StaniloaeChap2819", translatorBridge);
        checkInMaps("StaniloaeChap2820", translatorBridge);
        checkInMaps("StaniloaeChap2821", translatorBridge);
        checkInMaps("StaniloaeChap2822", translatorBridge);
        checkInMaps("StaniloaeChap2823", translatorBridge);
        checkInMaps("StaniloaeChap2824", translatorBridge);
        checkInMaps("StaniloaeChap2825", translatorBridge);
        checkInMaps("StaniloaeChap2826", translatorBridge);
        checkInMaps("StaniloaeChap2827", translatorBridge);
        checkInMaps("StaniloaeChap2828", translatorBridge);
        checkInMaps("StaniloaeChap2829", translatorBridge);
        checkInMaps("StaniloaeChap2830", translatorBridge);
        checkInMaps("StaniloaeChap2831", translatorBridge);
        checkInMaps("StaniloaeChap2832", translatorBridge);
        checkInMaps("StaniloaeChap2833", translatorBridge);
        checkInMaps("StaniloaeChap2834", translatorBridge);
        checkInMaps("StaniloaeChap2835", translatorBridge);
        checkInMaps("StaniloaeChap2836", translatorBridge);
        checkInMaps("StaniloaeChap2837", translatorBridge);
        checkInMaps("StaniloaeChap2838", translatorBridge);
        checkInMaps("StaniloaeChap2839", translatorBridge);
        checkInMaps("StaniloaeChap2840", translatorBridge);



        checkInMaps("StaniloaeChap2901", translatorBridge);
        checkInMaps("StaniloaeChap2902", translatorBridge);
        checkInMaps("StaniloaeChap2903", translatorBridge);
        checkInMaps("StaniloaeChap2904", translatorBridge);
        checkInMaps("StaniloaeChap2905", translatorBridge);
        checkInMaps("StaniloaeChap2906", translatorBridge);
        checkInMaps("StaniloaeChap2907", translatorBridge);
        checkInMaps("StaniloaeChap2908", translatorBridge);
        checkInMaps("StaniloaeChap2909", translatorBridge);
        checkInMaps("StaniloaeChap2910", translatorBridge);
        checkInMaps("StaniloaeChap2911", translatorBridge);
        checkInMaps("StaniloaeChap2912", translatorBridge);
        checkInMaps("StaniloaeChap2913", translatorBridge);
        checkInMaps("StaniloaeChap2914", translatorBridge);
        checkInMaps("StaniloaeChap2915", translatorBridge);
        checkInMaps("StaniloaeChap2916", translatorBridge);
        checkInMaps("StaniloaeChap2917", translatorBridge);
        checkInMaps("StaniloaeChap2918", translatorBridge);
        checkInMaps("StaniloaeChap2919", translatorBridge);
        checkInMaps("StaniloaeChap2920", translatorBridge);
        checkInMaps("StaniloaeChap2921", translatorBridge);
        checkInMaps("StaniloaeChap2922", translatorBridge);
        checkInMaps("StaniloaeChap2923", translatorBridge);
        checkInMaps("StaniloaeChap2924", translatorBridge);
        checkInMaps("StaniloaeChap2925", translatorBridge);
        checkInMaps("StaniloaeChap2926", translatorBridge);
        checkInMaps("StaniloaeChap2927", translatorBridge);
        checkInMaps("StaniloaeChap2928", translatorBridge);
        checkInMaps("StaniloaeChap2929", translatorBridge);
        checkInMaps("StaniloaeChap2930", translatorBridge);
        checkInMaps("StaniloaeChap2931", translatorBridge);
        checkInMaps("StaniloaeChap2932", translatorBridge);
        checkInMaps("StaniloaeChap2933", translatorBridge);
        checkInMaps("StaniloaeChap2934", translatorBridge);
        checkInMaps("StaniloaeChap2935", translatorBridge);
        checkInMaps("StaniloaeChap2936", translatorBridge);
        checkInMaps("StaniloaeChap2937", translatorBridge);
        checkInMaps("StaniloaeChap2938", translatorBridge);
        checkInMaps("StaniloaeChap2939", translatorBridge);
        checkInMaps("StaniloaeChap2940", translatorBridge);
        checkInMaps("StaniloaeChap2941", translatorBridge);


        checkInMaps("StaniloaeChap3001", translatorBridge);
        checkInMaps("StaniloaeChap3002", translatorBridge);
        checkInMaps("StaniloaeChap3003", translatorBridge);
        checkInMaps("StaniloaeChap3004", translatorBridge);
        checkInMaps("StaniloaeChap3005", translatorBridge);
        checkInMaps("StaniloaeChap3006", translatorBridge);
        checkInMaps("StaniloaeChap3007", translatorBridge);
        checkInMaps("StaniloaeChap3008", translatorBridge);
        checkInMaps("StaniloaeChap3009", translatorBridge);
        checkInMaps("StaniloaeChap3010", translatorBridge);
        checkInMaps("StaniloaeChap3011", translatorBridge);
        checkInMaps("StaniloaeChap3012", translatorBridge);
        checkInMaps("StaniloaeChap3013", translatorBridge);
        checkInMaps("StaniloaeChap3014", translatorBridge);
        checkInMaps("StaniloaeChap3015", translatorBridge);
        checkInMaps("StaniloaeChap3016", translatorBridge);
        checkInMaps("StaniloaeChap3017", translatorBridge);
        checkInMaps("StaniloaeChap3018", translatorBridge);
        checkInMaps("StaniloaeChap3019", translatorBridge);
        checkInMaps("StaniloaeChap3020", translatorBridge);
        checkInMaps("StaniloaeChap3021", translatorBridge);
        checkInMaps("StaniloaeChap3022", translatorBridge);
        checkInMaps("StaniloaeChap3023", translatorBridge);
        checkInMaps("StaniloaeChap3024", translatorBridge);
        checkInMaps("StaniloaeChap3025", translatorBridge);
        checkInMaps("StaniloaeChap3026", translatorBridge);
        checkInMaps("StaniloaeChap3027", translatorBridge);
        checkInMaps("StaniloaeChap3028", translatorBridge);
        checkInMaps("StaniloaeChap3029", translatorBridge);
        checkInMaps("StaniloaeChap3030", translatorBridge);
        checkInMaps("StaniloaeChap3031", translatorBridge);
        checkInMaps("StaniloaeChap3032", translatorBridge);
        checkInMaps("StaniloaeChap3033", translatorBridge);
        checkInMaps("StaniloaeChap3034", translatorBridge);
        checkInMaps("StaniloaeChap3035", translatorBridge);
        checkInMaps("StaniloaeChap3036", translatorBridge);
        checkInMaps("StaniloaeChap3037", translatorBridge);
        checkInMaps("StaniloaeChap3038", translatorBridge);
        checkInMaps("StaniloaeChap3039", translatorBridge);



        checkInMaps("StaniloaeChap3101", translatorBridge);
        checkInMaps("StaniloaeChap3102", translatorBridge);
        checkInMaps("StaniloaeChap3103", translatorBridge);
        checkInMaps("StaniloaeChap3104", translatorBridge);
        checkInMaps("StaniloaeChap3105", translatorBridge);
        checkInMaps("StaniloaeChap3106", translatorBridge);
        checkInMaps("StaniloaeChap3107", translatorBridge);
        checkInMaps("StaniloaeChap3108", translatorBridge);
        checkInMaps("StaniloaeChap3109", translatorBridge);
        checkInMaps("StaniloaeChap3110", translatorBridge);
        checkInMaps("StaniloaeChap3111", translatorBridge);
        checkInMaps("StaniloaeChap3112", translatorBridge);
        checkInMaps("StaniloaeChap3113", translatorBridge);
        checkInMaps("StaniloaeChap3114", translatorBridge);
        checkInMaps("StaniloaeChap3115", translatorBridge);
        checkInMaps("StaniloaeChap3116", translatorBridge);
        checkInMaps("StaniloaeChap3117", translatorBridge);
        checkInMaps("StaniloaeChap3118", translatorBridge);
        checkInMaps("StaniloaeChap3119", translatorBridge);
        checkInMaps("StaniloaeChap3120", translatorBridge);
        checkInMaps("StaniloaeChap3121", translatorBridge);
        checkInMaps("StaniloaeChap3122", translatorBridge);
        checkInMaps("StaniloaeChap3123", translatorBridge);
        checkInMaps("StaniloaeChap3124", translatorBridge);
        checkInMaps("StaniloaeChap3125", translatorBridge);
        checkInMaps("StaniloaeChap3126", translatorBridge);
        checkInMaps("StaniloaeChap3127", translatorBridge);
        checkInMaps("StaniloaeChap3128", translatorBridge);
        checkInMaps("StaniloaeChap3129", translatorBridge);


    }


    @Test
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
        //checkInMaps("bocaseumpluA112", translatorBridge);
    }

}

import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.DummyAccentuer;
import patrologia.translator.basicelements.Language;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StrackBillerbeckTranslatorBridgeTest extends TranslatorBridgeTest {


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
        String germanPathFile = localTestPath + "strackbillerbeck_content.txt";
        String germanResultFile = localTestPath + "strackbillerbeck_results.txt";
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
        //checkInMaps("strackp3notB4", translatorBridge);
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
        //checkInMaps("strackp3notD5", translatorBridge);
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
        //checkInMaps("strackp4not029", translatorBridge);
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
        //checkInMaps("strackp6par11B", translatorBridge);
        //checkInMaps("strackp6par11C", translatorBridge);
        //checkInMaps("strackp6par11D", translatorBridge);
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
        //checkInMaps("strackp6par202", translatorBridge);
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
        //checkInMaps("strackp6par285", translatorBridge);
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
        //checkInMaps("strackp10par311", translatorBridge);
        checkInMaps("strackp10par312", translatorBridge);
        //checkInMaps("strackp10par313", translatorBridge);
        checkInMaps("strackp10par314", translatorBridge);
        checkInMaps("strackp10par315", translatorBridge);
        checkInMaps("strackp10par316", translatorBridge);
        checkInMaps("strackp10par317", translatorBridge);
        //checkInMaps("strackp10par318", translatorBridge);
        //checkInMaps("strackp10par319", translatorBridge);
        //checkInMaps("strackp10par320", translatorBridge);
        //checkInMaps("strackp10par321", translatorBridge);
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
        //checkInMaps("strackp10par333", translatorBridge);
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
        //checkInMaps("strackp11par062", translatorBridge);
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
        //checkInMaps("strackp11par080", translatorBridge);
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
        //checkInMaps("strackp11par092", translatorBridge);
        checkInMaps("strackp11par093", translatorBridge);
        checkInMaps("strackp11par094", translatorBridge);
        checkInMaps("strackp11par095", translatorBridge);
        //checkInMaps("strackp11par096", translatorBridge);
        checkInMaps("strackp11par097", translatorBridge);
        checkInMaps("strackp11par098", translatorBridge);
        checkInMaps("strackp11par099", translatorBridge);
        checkInMaps("strackp11par100", translatorBridge);
        checkInMaps("strackp11par101", translatorBridge);
    }

    @Test
    public void test_strack_page14_par1() {
        checkInMaps("toto", translatorBridge);
    }

    }

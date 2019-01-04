import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.casenumbergenre.latin.LatinCaseFactory;
import patrologia.translator.conjugation.latin.LatinConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.latin.LatinDeclension;
import patrologia.translator.declension.latin.LatinDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.LatinAnalyzer;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.latin.LatinRuleFactory;
import patrologia.translator.utils.Analizer;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 08/09/2015.
 */
public class LatinTranslatorBridgeTest extends TranslatorBridgeTest {

    protected TranslatorBridge translatorBridge;

    private String localTestPath="C:\\Users\\laurent.kloeble\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath="C:\\Users\\laurent.kloeble\\IdeaProjects\\translator\\src\\main\\resources\\latin\\";
    private String localCommonPath="C:\\Users\\laurent.kloeble\\IdeaProjects\\translator\\src\\main\\resources\\";

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
        String latinPathFile = localTestPath + "latin_content.txt";
        String latinResultFile = localTestPath + "latin_expected_result.txt";
        LatinDeclensionFactory latinDeclensionFactory = new LatinDeclensionFactory(getDeclensions(declensionsAndFiles), getDeclensionList(declensionsAndFiles, declensionLatinFiles));
        LatinRuleFactory ruleFactory = new LatinRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.LATIN, new LatinCaseFactory(), ruleFactory, getFileContentForRepository(prepositionFileDescription));
        NounRepository nounRepository = new NounRepository(Language.LATIN, latinDeclensionFactory, new DummyAccentuer(),getNouns(nounFileDescription));
        VerbRepository verbRepository = new VerbRepository(new LatinConjugationFactory(getLatinConjugations(conjugationsAndFiles), getLatinConjugationDefinitions(conjugationsAndFiles, conjugationLatinFiles),nounRepository), Language.LATIN, new DummyAccentuer(),getVerbs(verbFileDescription));
        Analizer latinAnalyzer = new LatinAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getFileContentForRepository(latinFrenchDataFile), getFileContentForRepository(frenchVerbsDataFile), verbRepository, nounRepository, declensionLatinFiles, declensionsAndFiles, latinDeclensionFactory);
        translatorBridge = new TranslatorBridge(latinAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(latinPathFile);
        mapValuesForResult = loadMapFromFiles(latinResultFile);
    }

    private List<String> getVerbs(String verbFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "trad,o,is,ere,,,[o-is],(AIP*trad*tradid*0@AIFP*trads*tradid*0@AIPP*trad*tradid*0@IAPP*trad*tradid*0)"
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
        latinConjugationDefinitionsMap.put("o-is", getOIslDefinition());
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
                "sement@neut%is-is"
        });
        */
        return getFileContentForRepository(nounFileDescription);
    }

    @Test
    public void test_failing_one() {
        checkInMaps("patrologiacaput3B33", translatorBridge);
    }


}
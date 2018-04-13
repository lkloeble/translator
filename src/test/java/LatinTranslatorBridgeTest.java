import org.junit.Before;
import org.junit.Test;
import org.patrologia.translator.TranslatorBridge;
import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.preposition.PrepositionRepository;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.casenumbergenre.latin.LatinCaseFactory;
import org.patrologia.translator.conjugation.latin.LatinConjugationFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.latin.LatinDeclension;
import org.patrologia.translator.declension.latin.LatinDeclensionFactory;
import org.patrologia.translator.linguisticimplementations.FrenchTranslator;
import org.patrologia.translator.linguisticimplementations.LatinAnalyzer;
import org.patrologia.translator.linguisticimplementations.Translator;
import org.patrologia.translator.rule.latin.LatinRuleFactory;
import org.patrologia.translator.utils.Analizer;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 08/09/2015.
 */
public class LatinTranslatorBridgeTest extends TranslatorBridgeTest {

    protected TranslatorBridge translatorBridge;

    @Before
    public void init() {
        String prepositionFileDescription = "C:\\translator\\src\\main\\resources\\latin\\prepositions.txt";
        String nounFileDescription = "C:\\translator\\src\\main\\resources\\latin\\nouns.txt";
        String verbFileDescription = "C:\\translator\\src\\main\\resources\\latin\\verbs.txt";
        String latinFrenchDataFile = "C:\\translator\\src\\main\\resources\\latin\\gaffiot_latin_to_french.txt";
        String frenchVerbsDataFile = "C:\\translator\\src\\main\\resources\\french_verbs.txt";
        String declensionLatinFiles = "C:\\translator\\src\\main\\resources\\latin\\declensions";
        String conjugationLatinFiles = "C:\\translator\\src\\main\\resources\\latin\\conjugations";
        String declensionsAndFiles = "C:\\translator\\src\\main\\resources\\latin\\declensionsAndFiles.txt";
        String conjugationsAndFiles = "C:\\translator\\src\\main\\resources\\latin\\conjugationsAndFiles.txt";
        String latinPathFile = "C:\\translator\\src\\test\\resources\\latin_content.txt";
        String latinResultFile = "C:\\translator\\src\\test\\resources\\latin_expected_result.txt";
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
                "perfer,o,s,re,,,[o-s]"
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
                "orgetorix@neut%inv[gensing:orgetorigis,accsing:orgetoricem]"
        });
        */
        return getFileContentForRepository(nounFileDescription);
    }

    @Test
    public void test_failing_one() {
        checkInMaps("toto", translatorBridge);
    }


}
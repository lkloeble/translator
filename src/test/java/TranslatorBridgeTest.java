import org.patrologia.translator.TranslatorBridge;
import org.patrologia.translator.utils.DictionaryLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class TranslatorBridgeTest {

    protected Map<String, String> mapValuesForTest;
    protected Map<String, String> mapValuesForResult;

    protected List<String> getDeclensions(String file) {
        return getFileContentForRepository(file);
    }

    protected List<String> getDeclensionElements(String declensionFileName, String directory) {
        DictionaryLoader dictionaryLoader = new DictionaryLoader(directory + "\\" + declensionFileName);
        List<String> allLines = dictionaryLoader.getAllLines();
        return allLines;
    }

    protected List<String> getConjugationElements(String directory, String name) {
        return getDeclensionElements(name, directory);
    }

    protected Map<String, String> loadMapFromFiles(String greekPathFile) {
        Map<String, String> linesKeyValues = new HashMap<String, String>();
        List<String> lines = new DictionaryLoader(greekPathFile).getAllLines();
        //TODO lambda
        for (String line : lines) {
            if(line != null && !line.contains("=>")) continue;
            String[] keyValues = line.split("=>");
            linesKeyValues.put(keyValues[0], keyValues[1]);
        }
        return linesKeyValues;
    }

    protected List<String> getFileContentForRepository(String fileName) {
        return new DictionaryLoader(fileName).getAllLines();
    }

    protected void checkInMaps(String id, TranslatorBridge translatorBridge) {
        assertEquals(mapValuesForResult.get(id), translatorBridge.translate(mapValuesForTest.get(id)));
    }
}

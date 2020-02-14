package patrologia.panin;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class PaninGreekRepository {

    Map<Integer, String> dictionary = new HashMap<>();

    public String getWordByStrongID(String ID) {
        return dictionary.get(Integer.parseInt(ID));
    }

    public void addStrongReference(String reference) {
        String[] values = reference.split("@");
        dictionary.put(Integer.parseInt(values[1]),values[0]);
    }

    public void addAllStrongReferences(String allReferences) {
        StringTokenizer stringTokenizer = new StringTokenizer(allReferences);
        while(stringTokenizer.hasMoreTokens()) {
            addStrongReference(stringTokenizer.nextToken());
        }
    }
}

package patrologia.translator.linguisticimplementations;

public class FrenchVerbPattern {

    private String key;
    private String value;

    public FrenchVerbPattern(String pattern) {
        key = "";
        value = "";
        extractKeyValue(pattern);
    }

    private void extractKeyValue(String pattern) {
        if(pattern == null) return;
        String[] keyValueStart = pattern.split("=");
        if(keyValueStart == null || keyValueStart.length != 2)  return;
        key = keyValueStart[0].replace("]","").replace("[","");
        value = keyValueStart[1].replace("]","").replace("[","");
    }

    public boolean hasExactKey(String possibleKey) {
        return key.equals(possibleKey);
    }
}

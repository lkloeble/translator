package patrologia.translator.linguisticimplementations;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewLanguageSelector implements SpecificLanguageSelector {

    @Override
    public boolean electConstruction(String value, String toTranslate) {
        if(value.contains(",")) {
            String[] splitValues = value.split(",");
            for(String v : splitValues) {
                if(v.equals(toTranslate)) return true;
            }
        }
        return false;
    }
}

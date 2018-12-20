package patrologia.translator.linguisticimplementations;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class DefaultLanguageSelector implements SpecificLanguageSelector {

    @Override
    public boolean electConstruction(String value, String toTranslate) {
        return value.contains(toTranslate);
    }
}

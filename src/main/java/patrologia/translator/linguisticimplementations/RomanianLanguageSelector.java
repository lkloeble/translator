package patrologia.translator.linguisticimplementations;

public class RomanianLanguageSelector implements SpecificLanguageSelector {

    @Override
    public boolean electConstruction(String value, String toTranslate) {
        return value.contains(toTranslate);
    }

}

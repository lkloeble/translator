package patrologia.translator.basicelements.verb;

public class RomanianInfinitiveBuilder extends InfinitiveBuilder {

    @Override
    public String getInfinitiveFromInitialValue(String value) {
        return "a " + value;
    }

}

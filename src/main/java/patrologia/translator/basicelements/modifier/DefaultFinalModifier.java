package patrologia.translator.basicelements.modifier;

public class DefaultFinalModifier implements FinalModifier {

    @Override
    public String decorate(String translation) {
        return translation;
    }
}

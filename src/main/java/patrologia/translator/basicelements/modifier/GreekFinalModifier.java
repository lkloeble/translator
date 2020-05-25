package patrologia.translator.basicelements.modifier;

public class GreekFinalModifier implements FinalModifier {

    @Override
    public String decorate(String translation) {
        translation = translation.replace(" aux (des)", " des");
        translation = translation.replace(" de (du)", " du");
        translation = translation.replace(",de (du)", ", du");
        return translation;
    }
}
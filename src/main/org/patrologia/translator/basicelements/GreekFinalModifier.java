package org.patrologia.translator.basicelements;

/**
 * Created by lkloeble on 27/10/2016.
 */
public class GreekFinalModifier implements FinalModifier {

    @Override
    public String decorate(String translation) {
        translation = translation.replace(" aux (des)", " des");
        translation = translation.replace(" de (du)", " du");
        translation = translation.replace(",de (du)", ", du");
        return translation;
    }
}

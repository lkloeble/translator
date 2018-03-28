package org.patrologia.translator.basicelements.modifier;

import org.patrologia.translator.basicelements.modifier.FinalModifier;

/**
 * Created by lkloeble on 27/10/2016.
 */
public class DefaultFinalModifier implements FinalModifier {

    @Override
    public String decorate(String translation) {
        return translation;
    }
}

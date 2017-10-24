package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.Noun;

/**
 * Created by lkloeble on 26/10/2016.
 */
public class GreekLanguageDecorator implements LanguageDecorator {

    @Override
    public boolean dativeHandlerIsTrue(Noun noun) {
        return noun.isNotPrecededByAPrepositionWithRule();
    }

    @Override
    public boolean ablativeHandlerIsTrue(Noun noun) {
        return false;
    }
}

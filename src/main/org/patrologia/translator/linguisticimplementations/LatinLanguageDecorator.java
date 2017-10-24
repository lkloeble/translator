package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.Noun;

/**
 * Created by lkloeble on 26/10/2016.
 */
public class LatinLanguageDecorator implements LanguageDecorator {

    @Override
    public boolean dativeHandlerIsTrue(Noun noun) {
        return noun.isNotPrecededByAPrepositionWithRule() && noun.isNotPrecedByAnAdjective() && noun.IsNotPrecededByADativeNoun();
    }

    @Override
    public boolean ablativeHandlerIsTrue(Noun noun) {
        return noun.isNotPrecededByAPrepositionWithRule() && noun.isNotPrecedByAnAdjective() && noun.IsNotPrecededByAnAblativeNoun();
    }
}

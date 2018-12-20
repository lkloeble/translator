package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.noun.Noun;

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

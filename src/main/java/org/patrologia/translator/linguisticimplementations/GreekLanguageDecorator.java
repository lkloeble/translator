package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.noun.Noun;

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

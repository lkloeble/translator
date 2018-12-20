package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.noun.Noun;

/**
 * Created by lkloeble on 08/08/2017.
 */
public class GermanLanguageDecorator implements LanguageDecorator {

    @Override
    public boolean dativeHandlerIsTrue(Noun noun) {
        return false;
    }

    @Override
    public boolean ablativeHandlerIsTrue(Noun noun) {
        return false;
    }
}

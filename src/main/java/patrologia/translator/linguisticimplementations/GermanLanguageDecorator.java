package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.noun.Noun;

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

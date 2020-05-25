package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.noun.Noun;

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
package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.noun.Noun;

public interface LanguageDecorator {

    boolean dativeHandlerIsTrue(Noun noun);

    boolean ablativeHandlerIsTrue(Noun noun);

}

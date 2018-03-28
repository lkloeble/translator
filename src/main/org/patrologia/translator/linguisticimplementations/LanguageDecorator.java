package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.noun.Noun;

/**
 * Created by lkloeble on 26/10/2016.
 */
public interface LanguageDecorator {

    boolean dativeHandlerIsTrue(Noun noun);

    boolean ablativeHandlerIsTrue(Noun noun);
}

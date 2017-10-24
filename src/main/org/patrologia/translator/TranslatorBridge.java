package org.patrologia.translator;

import org.patrologia.translator.basicelements.Analysis;
import org.patrologia.translator.linguisticimplementations.Translator;
import org.patrologia.translator.utils.Analizer;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public class TranslatorBridge {

    private Analizer sourceAnalizer;
    private Translator destinationTranslator;

    public TranslatorBridge(Analizer sourceAnalizer, Translator destination) {
        this.sourceAnalizer = sourceAnalizer;
        this.destinationTranslator = destination;
    }

    public String translate(String sentence) {
        Analysis analysis = sourceAnalizer.analyze(sentence);
        return destinationTranslator.translateToRead(analysis);
    }
}

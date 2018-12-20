package patrologia.translator;

import patrologia.translator.basicelements.Analysis;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.utils.Analizer;

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

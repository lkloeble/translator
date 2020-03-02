package patrologia.translator;

import patrologia.translator.basicelements.Analysis;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.utils.Analyzer;

public class TranslatorBridge {

    private Analyzer sourceAnalizer;
    private Translator destinationTranslator;

    public TranslatorBridge(Analyzer sourceAnalizer, patrologia.translator.linguisticimplementations.Translator destination) {
        this.sourceAnalizer = sourceAnalizer;
        this.destinationTranslator = destination;
    }

    public String translate(String sentence) {
        Analysis analysis = sourceAnalizer.analyze(sentence);
        return destinationTranslator.translateToRead(analysis);
    }

}

package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.WordSplitterPattern;

public class HebrewWordSplitterPattern implements WordSplitterPattern {

    @Override
    public String getPattern() {
        return " -";
    }
}

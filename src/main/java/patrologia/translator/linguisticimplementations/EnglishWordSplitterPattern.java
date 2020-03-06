package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.WordSplitterPattern;

public class EnglishWordSplitterPattern implements WordSplitterPattern {

    @Override
    public String getPattern() {
        return " -\"";
    }

}

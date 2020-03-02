package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.WordSplitterPattern;

public class DefaultWordSplitterPattern implements WordSplitterPattern {

    @Override
    public String getPattern() {
        return  " -\"'";
    }

}

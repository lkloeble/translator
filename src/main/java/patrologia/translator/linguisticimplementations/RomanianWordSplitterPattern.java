package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.WordSplitterPattern;

public class RomanianWordSplitterPattern implements WordSplitterPattern {

    @Override
    public String getPattern() {
        return  " -\"'";
    }

}

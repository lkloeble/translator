package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.WordSplitterPattern;

public class LatinWordSplitterPattern implements WordSplitterPattern {

    @Override
    public String getPattern() {
        return  " -\"'";
    }

}

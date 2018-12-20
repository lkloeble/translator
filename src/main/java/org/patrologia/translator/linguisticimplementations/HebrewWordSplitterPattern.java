package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.WordSplitterPattern;

/**
 * Created by lkloeble on 09/11/2016.
 */
public class HebrewWordSplitterPattern implements WordSplitterPattern {

    @Override
    public String getPattern() {
        return " -";
    }
}

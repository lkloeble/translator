package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.utils.WordSplitterPattern;

/**
 * Created by lkloeble on 09/11/2016.
 */
public class DefaultWordSplitterPattern implements WordSplitterPattern {

    @Override
    public String getPattern() {
        return  " -\"'";
    }
}

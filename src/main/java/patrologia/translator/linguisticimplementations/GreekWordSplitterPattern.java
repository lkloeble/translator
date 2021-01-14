package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.WordSplitterPattern;

public class GreekWordSplitterPattern  implements WordSplitterPattern {

    @Override
    public String getPattern() {
        return  " -\"'";
    }


}

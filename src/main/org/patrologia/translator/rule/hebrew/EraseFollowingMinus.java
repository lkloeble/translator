package org.patrologia.translator.rule.hebrew;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 26/09/2016.
 */
public class EraseFollowingMinus extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getYetUnknownWordAtPosition(position+1);
        if("xxdexx".equals(followingWord.getInitialValue())) {
            followingWord.modifyContentByPatternReplacement("xxdexx","xxtoremovexx");
        }
    }
}

package patrologia.translator.rule.hebrew;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class EraseFollowingMinus extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getYetUnknownWordAtPosition(position+1);
        if("xxdexx".equals(followingWord.getInitialValue())) {
            followingWord.modifyContentByPatternReplacement("xxdexx","xxtoremovexx");
        }
    }

}

package org.patrologia.translator.rule.greek;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 05/06/2017.
 */
public class RuleKatEmeravMerge extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("ημεραν".equals(followingWord.getInitialValue())) {
            word.setInitialValue("καθημεραν");
            word.setRoot("καθημεραν");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }


}

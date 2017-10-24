package org.patrologia.translator.rule.greek;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 18/06/2017.
 */
public class RuleAvaMesovFinder extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("μεσον".equals(followingWord.getInitialValue())) {
            word.setInitialValue("αναμεσον");
            word.setRoot("αναμεσον");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

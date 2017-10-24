package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 19/04/2017.
 */
public class RuleOneWould extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("would".equals(followingWord.getInitialValue())) {
            word.setInitialValue("onexx");
            word.setRoot("onexx");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

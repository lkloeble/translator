package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 11/04/2017.
 */
public class RuleRecomposeMaiPresus extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("presus".equals(followingWord.getInitialValue())) {
            word.setInitialValue("maipresus");
            word.setRoot("maipresus");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

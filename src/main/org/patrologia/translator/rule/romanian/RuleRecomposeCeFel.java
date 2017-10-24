package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 03/06/2016.
 */
public class RuleRecomposeCeFel extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("fel".equals(followingWord.getInitialValue())) {
            word.setInitialValue("cefel");
            word.setRoot("cefel");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }
}

package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 31/05/2017.
 */
public class RuleRecomposeMaiBine extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("bine".equals(followingWord.getInitialValue())) {
            word.setInitialValue("maibine");
            word.setRoot("maibine");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

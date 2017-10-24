package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 31/07/2017.
 */
public class RuleComposeArVerb extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if(followingWord.isVerb()) {
            followingWord.setInitialValue("ar" + followingWord.getInitialValue());
            word.setInitialValue("xxtoremovexx");
        }
    }

}

package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 18/04/2017.
 */
public class RuleComposeDinVeci extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("veci".equals(followingWord.getInitialValue())) {
            word.setInitialValue("dinveci");
            word.setRoot("dinveci");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

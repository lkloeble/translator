package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 30/08/2016.
 */
public class RuleThereIsFusion extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("is".equals(followingWord.getInitialValue()) || "are".equals(followingWord.getInitialValue())) {
            followingWord.modifyContentByPatternReplacement("is","xxtoremovexx");
            followingWord.modifyContentByPatternReplacement("are","xxtoremovexx");
            word.modifyContentByPatternReplacement("there","thereis");
        }
    }
}

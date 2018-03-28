package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.WordContainer;
import org.patrologia.translator.basicelements.WordType;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 02/02/2017.
 */
public class RulePrepAndWordToAssemble extends Rule {

    protected String leadingPrepositionValue;
    protected String followingNounValue;
    protected String resultToSubstitue;

    public RulePrepAndWordToAssemble(String leadingPrepositionValue, String followingNounValue, String resultToSubstitue) {
        this.leadingPrepositionValue = leadingPrepositionValue;
        this.followingNounValue = followingNounValue;
        this.resultToSubstitue = resultToSubstitue;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        if (leadingPrepositionValue.equals(word.getInitialValue()) && followingNounValue.equals(followingWord.getInitialValue())) {
            word.setInitialValue(resultToSubstitue);
            word.setRoot(resultToSubstitue);
            followingWord.setInitialValue("xxtoremovexx");
            WordContainer wordContainer = phrase.getWordContainerAtPosition(position);
            wordContainer.deleteAllWordTypeExceptThisOne(WordType.PREPOSITION);
        }
    }
}
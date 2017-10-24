package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.WordContainer;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 19/01/2016.
 */
public class RuleOArticleFinder  extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        WordContainer nextWordContainer = phrase.getWordContainerAtPosition(position+1);
        if(nextWordContainer ==  null) {
            return;
        }
        Word nextWord = nextWordContainer.getUniqueWord();
        if(!nextWord.isNoun()) {
            return;
        }
        Noun nextNoun = (Noun)nextWord;
        nextNoun.setWithArticle();
    }
}

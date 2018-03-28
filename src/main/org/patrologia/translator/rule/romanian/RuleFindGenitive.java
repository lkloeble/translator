package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 07/04/2017.
 */
public class RuleFindGenitive extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getYetUnknownWordAtPosition(position+1);
        if(!nextWord.isNoun()) return;
        Noun nextNoun = (Noun)nextWord;
        if(nextNoun.isGenitive()) {
            word.updateRoot("aaxxaa");
            word.updateInitialValue("aaxxaa");
        }
    }
}

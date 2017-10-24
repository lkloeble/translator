package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.WordContainer;
import org.patrologia.translator.rule.Rule;

import java.util.Collections;

/**
 * Created by lkloeble on 06/02/2017.
 */
public class RuleByAndToWordsForFusion extends Rule {

    private String firstWord;
    private String secondWord;

    public RuleByAndToWordsForFusion(String firstWord, String secondWord) {
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        if(!word.getInitialValue().equals("by")) {
            return;
        }
        if(position + 2 > phrase.size()) {
            return;
        }
        Word next = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if(!next.getInitialValue().equals(firstWord)) {
            return;
        }
        Word afterNext = phrase.getWordContainerAtPosition(position+2).getUniqueWord();
        if(!afterNext.getInitialValue().equals(secondWord)) {
            return;
        }
        String result = "by" + firstWord + secondWord;
        phrase.addWordContainerAtPosition(position, new WordContainer(new Noun(language, result, result, Collections.EMPTY_LIST, null, null, null, Collections.EMPTY_LIST),position,language), phrase);
        phrase.updateWordContentAtPosition("xxtoremovexx", position + 1);
        phrase.updateWordContentAtPosition("xxtoremovexx", position+2);
    }

}

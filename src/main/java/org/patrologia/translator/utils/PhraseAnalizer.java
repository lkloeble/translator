package patrologia.translator.utils;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.rule.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 25/08/2015.
 */
public class PhraseAnalizer {

    public Analysis affectAllPossibleInformationsBetweenWords(Language language, Phrase phrase) {
        if(phrase == null || phrase.isEmpty()) {
            return new NullAnalysis(language);
        }
        for(Integer wordPosition : phrase.keySet()) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(wordPosition);
            for(Word word : wordContainer.getWordSetOrderedByPossibleCaseNumber()) {
                applyRules(word, phrase, wordPosition, wordContainer);
                //System.out.println("rule done");
            }
        }
        Phrase phraseWithoutErasedWords = new Phrase(1,language);
        int position = 1;
        for(Integer wordPosition : phrase.keySet()) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(wordPosition);
            if(!wordContainer.needsToBeErased()) {
                phraseWithoutErasedWords.addWordContainerAtPosition(position, wordContainer, phraseWithoutErasedWords);
                position++;
                continue;
            }
        }
        return new Analysis(language, phraseWithoutErasedWords);
    }

    public void applyRules(Word word, Phrase phrase, int wordPosition, WordContainer wordContainer) {
        if(word.hasRules()) {
            applyRules(word, phrase, wordPosition);
        }
        if (word.hasNewRules()) {
            applyRules(word, phrase, wordPosition);
        }
        if(!word.hasElected()) {
            word.selfElect();
        } else {
            if(word.isNoun()) {
                Noun noun = (Noun)word;
                CaseNumberGenre electedCaseNumber = noun.getElectedCaseNumber();
                wordContainer.eraseElectedCaseNumberInMultipleWordset(electedCaseNumber, word);
            }
        }

    }

    private void applyRules(Word word, Phrase phrase, int wordPosition) {
        List<Rule> rules = new ArrayList<>(word.getRules());
        Collections.sort(rules);
        for(Rule rule : rules) {
            rule.apply(word, phrase, wordPosition);
        }
    }
}

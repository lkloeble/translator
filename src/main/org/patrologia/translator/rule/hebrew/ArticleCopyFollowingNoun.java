package org.patrologia.translator.rule.hebrew;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.rule.Rule;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class ArticleCopyFollowingNoun extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getWordByType(WordType.NOUN);
        if(followingWord == null || !followingWord.isNoun()) return;
        Noun followingNoun = (Noun)followingWord;
        Preposition preposition = (Preposition)word;
        if(followingNoun.getGender().equals(new Gender(Gender.FEMININE))) {
            preposition.setDistinctiveTranslationByGenre();
        }
        if(followingNoun.isPlural()) {
            preposition.setDistinctiveTranslationByNumber();
        }
    }
}

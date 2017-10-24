package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Preposition;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 29/08/2016.
 */
public class RuleElectFeminineForFollowingNoun extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        //System.out.println(word.toString() + phrase + position + "");
        Word followingWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        if(followingWord == null) return;
        if(followingWord.isNoun() && ((Noun)followingWord).getGender().equals(new Gender(Gender.FEMININE))) {
            Preposition the = (Preposition)word;
            the.setDistinctiveTranslationByGenre();
        }
    }
}

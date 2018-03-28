package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 02/11/2015.
 */
public class RuleElectPluralForFollowingPluralNoun extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        //System.out.println(word.toString() + phrase + position + "");
        Word followingWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        if(followingWord == null) return;
        if(followingWord.isNoun() && ((Noun)followingWord).isPlural()) {
            Preposition the = (Preposition)word;
            the.setDistinctiveTranslationByNumber();
        }
    }
}

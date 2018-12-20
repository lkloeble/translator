package patrologia.translator.rule.english;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

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

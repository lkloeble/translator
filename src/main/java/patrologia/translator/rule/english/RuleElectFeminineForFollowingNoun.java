package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.rule.Rule;

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

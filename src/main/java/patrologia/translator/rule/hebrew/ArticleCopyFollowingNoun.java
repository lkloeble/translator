package patrologia.translator.rule.hebrew;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.rule.Rule;

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

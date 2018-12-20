package patrologia.translator.rule.latin;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 13/03/2017.
 */
public class RuleAdjectiveElectGender extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        WordContainer wcAtPosition = phrase.getWordContainerAtPosition(position);
        if(wcAtPosition.size() == 1) return;
        if(!wcAtPosition.hasOnlyOneType(WordType.NOUN)) return;
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getWordByType(WordType.NOUN);
        boolean hasSelected = true;
        if(followingWord.isNoun()) {
            Noun followingNoun = (Noun)followingWord;
            Gender followingGender = followingNoun.getGender();
            hasSelected = wcAtPosition.keepNounOfThisGender(followingGender);
        }
        if(!followingWord.isNoun() || !hasSelected) {
            followingWord = phrase.getWordContainerAtPosition(position - 1).getWordByType(WordType.NOUN);
        }
        if(!followingWord.isNoun()) return;
        if(followingWord.lastLetter() != word.lastLetter()) return;
        Noun followingNoun = (Noun)followingWord;
        Gender followingGender = followingNoun.getGender();
        wcAtPosition.keepNounOfThisGender(followingGender);
    }
}

package patrologia.translator.rule.greek;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;

/**
 * Created by lkloeble on 21/09/2016.
 */
public class RuleElectNounElectedForPrecedingAdjective extends GreekRule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getYetUnknownWordAtPosition(position+1);
        if(followingWord == null) return;
        Noun followingNoun = (Noun)followingWord;
        followingNoun.selfElect();
        Noun noun = (Noun)word;
        noun.setElectedCaseNumber(followingNoun.getElectedCaseNumber());
    }
}

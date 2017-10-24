package org.patrologia.translator.rule.greek;

import org.patrologia.translator.basicelements.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Verb;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.casenumbergenre.CaseNumberGenre;

/**
 * Created by lkloeble on 17/05/2016.
 */
public class RulePlurialNeutralWithSingularVerbMustChangeTimeVerbToPlural extends GreekRule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        //if(!previousWordIsTaArticle(phrase,position) && !currentWordIsPluralAndNeutral(word) && !followingWordIsVerb(phrase,position))  {
        if(currentWordIsPluralAndNeutral(word) && followingWordIsVerb(phrase,position)) {
            Verb verb = (Verb) (phrase.getWordContainerAtPosition(position + 1).getUniqueWord());
            verb.setPositionInTranslationTable(5);
            verb.setPluralKnown(true);
        }
    }

    private boolean previousWordIsTaArticle(Phrase phrase, int position) {
        Word previous = phrase.getWordContainerAtPosition(position-1).getUniqueWord();
        return (previous != null  && "τα".equals(previous.getInitialValue()));
    }

    private boolean currentWordIsPluralAndNeutral(Word word) {
        Noun noun = (Noun)word;
        CaseNumberGenre electedCaseNumber = noun.getElectedCaseNumber();
        return electedCaseNumber.isPlural();
    }

    private boolean followingWordIsVerb(Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        return followingWord.isVerb();

    }
}

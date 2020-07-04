package patrologia.translator.rule.greek;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.conjugation.ConjugationPosition;

public class RulePlurialNeutralWithSingularVerbMustChangeTimeVerbToPlural extends GreekRule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        //if(!previousWordIsTaArticle(phrase,position) && !currentWordIsPluralAndNeutral(word) && !followingWordIsVerb(phrase,position))  {
        if(currentWordIsPluralAndNeutral(word) && followingWordIsVerb(phrase,position)) {
            Verb verb = (Verb) (phrase.getWordContainerAtPosition(position + 1).getUniqueWord());
            verb.setPositionInTranslationTable(ConjugationPosition.PLURAL_THIRD_PERSON);
            verb.setPluralKnown(true);
        }
    }

    private boolean previousWordIsTaArticle(Phrase phrase, int position) {
        Word previous = phrase.getWordContainerAtPosition(position-1).getUniqueWord();
        return (previous != null  && "Ï„Î±".equals(previous.getInitialValue()));
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

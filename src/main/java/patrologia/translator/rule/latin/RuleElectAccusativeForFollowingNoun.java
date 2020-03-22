package patrologia.translator.rule.latin;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.german.AccusativeGermanCase;
import patrologia.translator.casenumbergenre.german.GermanCaseFactory;
import patrologia.translator.rule.Rule;

public class RuleElectAccusativeForFollowingNoun extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        WordContainer nextWordContainer = phrase.getWordContainerAtPosition(position + 1);
        AccusativeGermanCase accusative = GermanCaseFactory.getAccusative();
        boolean hasBeenElected = false;
        for(Word nextWord : nextWordContainer.getWordSet()) {
            if (checkByGender(new Gender(Gender.MASCULINE), nextWord, accusative)) hasBeenElected = true;
            if (checkByGender(new Gender(Gender.FEMININE), nextWord, accusative)) hasBeenElected  = true;
            if (checkByGender(new Gender(Gender.NEUTRAL), nextWord, accusative)) hasBeenElected  = true;
        }
        if(hasBeenElected) {
            nextWordContainer.eraseUnelectedCaseNumberInMultipleWordsetNoun();
        }
    }


}

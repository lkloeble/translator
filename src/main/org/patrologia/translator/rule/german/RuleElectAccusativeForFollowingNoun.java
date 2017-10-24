package org.patrologia.translator.rule.german;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.WordContainer;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.casenumbergenre.german.AccusativeGermanCase;
import org.patrologia.translator.casenumbergenre.german.GermanCaseFactory;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 08/08/2017.
 */
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

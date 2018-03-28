package org.patrologia.translator.rule.greek;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.casenumbergenre.*;
import org.patrologia.translator.casenumbergenre.greek.AccusativeGreekCase;
import org.patrologia.translator.casenumbergenre.greek.GreekCaseFactory;

/**
 * Created by lkloeble on 22/03/2016.
 */
public class RuleElectAccusativeForFollowingNoun extends GreekRule {

    public RuleElectAccusativeForFollowingNoun() {
        this.substitutionPreposition = null;
    }

    public RuleElectAccusativeForFollowingNoun(String substitutionPreposition) {
        this.substitutionPreposition = substitutionPreposition;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        AccusativeGreekCase accusative = new AccusativeGreekCase("");
        if(checkByGender(new Gender(Gender.MASCULINE), nextWord, accusative)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.FEMININE), nextWord, accusative)) updatePreposition(word);
        if (checkByGender(new Gender(Gender.NEUTRAL), nextWord, accusative)) updatePreposition(word);
    }
}

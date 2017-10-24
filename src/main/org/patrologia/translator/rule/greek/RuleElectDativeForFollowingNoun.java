package org.patrologia.translator.rule.greek;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.casenumbergenre.greek.DativeGreekCase;
import org.patrologia.translator.casenumbergenre.greek.GreekCaseFactory;

/**
 * Created by lkloeble on 22/03/2016.
 */
public class RuleElectDativeForFollowingNoun extends GreekRule {

    public RuleElectDativeForFollowingNoun() {
        this.substitutionPreposition = null;
    }

    public RuleElectDativeForFollowingNoun(String substitutionPreposition) {
        this.substitutionPreposition = substitutionPreposition;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        DativeGreekCase dative = GreekCaseFactory.getDative();
        if(checkByGender(new Gender(Gender.MASCULINE), nextWord, dative))  updatePreposition(word);
        if(checkByGender(new Gender(Gender.FEMININE), nextWord, dative)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.NEUTRAL), nextWord, dative)) updatePreposition(word);
    }
}

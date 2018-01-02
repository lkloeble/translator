package org.patrologia.translator.rule.greek;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.casenumbergenre.*;
import org.patrologia.translator.casenumbergenre.greek.GenitiveGreekCase;
import org.patrologia.translator.casenumbergenre.greek.GreekCaseFactory;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class RuleElectGenitiveForFollowingNoun extends GreekRule {

    public RuleElectGenitiveForFollowingNoun() {
        this.substitutionPreposition = null;
    }

    public RuleElectGenitiveForFollowingNoun(String substitutionPreposition) {
        this.substitutionPreposition = substitutionPreposition;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        GenitiveGreekCase genitive = new GenitiveGreekCase("");
        if(checkByGender(new Gender(Gender.MASCULINE), nextWord, genitive)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.FEMININE), nextWord, genitive)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.NEUTRAL), nextWord, genitive)) updatePreposition(word);
    }
}

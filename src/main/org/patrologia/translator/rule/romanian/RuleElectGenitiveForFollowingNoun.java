package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.casenumbergenre.romanian.GenitiveRomanianCase;

public class RuleElectGenitiveForFollowingNoun extends RomanianRule {

    public RuleElectGenitiveForFollowingNoun() {
        this.substitutionPreposition = null;
    }

    public RuleElectGenitiveForFollowingNoun(String substitutionPreposition) {
        this.substitutionPreposition = substitutionPreposition;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        GenitiveRomanianCase genitive = new GenitiveRomanianCase("");
        if(checkByGender(new Gender(Gender.MASCULINE), nextWord, genitive)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.FEMININE), nextWord, genitive)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.NEUTRAL), nextWord, genitive)) updatePreposition(word);
    }
}

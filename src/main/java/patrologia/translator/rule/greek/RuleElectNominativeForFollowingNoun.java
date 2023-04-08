package patrologia.translator.rule.greek;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.greek.AccusativeGreekCase;
import patrologia.translator.casenumbergenre.greek.NominativeGreekCase;

public class RuleElectNominativeForFollowingNoun extends GreekRule {

    public RuleElectNominativeForFollowingNoun() {
        this.substitutionPreposition = null;
    }

    public RuleElectNominativeForFollowingNoun(String substitutionPreposition) {
        this.substitutionPreposition = substitutionPreposition;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        NominativeGreekCase nominative = new NominativeGreekCase("");
        if(checkByGender(new Gender(Gender.MASCULINE), nextWord, nominative)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.FEMININE), nextWord, nominative)) updatePreposition(word);
        if (checkByGender(new Gender(Gender.NEUTRAL), nextWord, nominative)) updatePreposition(word);
    }
}

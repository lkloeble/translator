package patrologia.translator.rule.greek;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.greek.AccusativeGreekCase;

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
        accusative.setDifferentier(nextWord);
        if(checkByGender(new Gender(Gender.MASCULINE), nextWord, accusative)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.FEMININE), nextWord, accusative)) updatePreposition(word);
        if (checkByGender(new Gender(Gender.NEUTRAL), nextWord, accusative)) updatePreposition(word);
    }
}

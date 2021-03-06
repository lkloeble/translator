package patrologia.translator.rule.greek;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.greek.DativeGreekCase;

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
        DativeGreekCase dative = new DativeGreekCase("");
        if(checkByGender(new Gender(Gender.MASCULINE), nextWord, dative))  updatePreposition(word);
        if(checkByGender(new Gender(Gender.FEMININE), nextWord, dative)) updatePreposition(word);
        if(checkByGender(new Gender(Gender.NEUTRAL), nextWord, dative)) updatePreposition(word);
    }
}

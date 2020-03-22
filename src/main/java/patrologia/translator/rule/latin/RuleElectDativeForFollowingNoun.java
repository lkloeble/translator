package patrologia.translator.rule.latin;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.Number;
import patrologia.translator.casenumbergenre.latin.DativeLatinCase;
import patrologia.translator.rule.Rule;

public class RuleElectDativeForFollowingNoun extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        CaseNumberGenre dativeSingular = new CaseNumberGenre(new DativeLatinCase(null), Number.SINGULAR, new Gender(Gender.NEUTRAL));
        CaseNumberGenre dativePlural = new CaseNumberGenre(new DativeLatinCase(null), Number.PLURAL, new Gender(Gender.NEUTRAL));
        if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(dativeSingular)) {
            ((Noun) nextWord).setElectedCaseNumber(dativeSingular);
        } else if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(dativePlural)) {
            ((Noun) nextWord).setElectedCaseNumber(dativeSingular);
        }
    }

}

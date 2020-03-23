package patrologia.translator.rule.latin;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.Number;
import patrologia.translator.casenumbergenre.latin.AccusativeLatinCase;
import patrologia.translator.rule.Rule;

public class RuleElectAccusativeForFollowingNoun extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        if(nextWord.getInitialValue().equals("pulcherrima")) System.out.println("ruleElectAccusative");
        CaseNumberGenre accusativeSingular = new CaseNumberGenre(new AccusativeLatinCase(null), Number.SINGULAR, new Gender(Gender.NEUTRAL));
        CaseNumberGenre accusativePlural = new CaseNumberGenre(new AccusativeLatinCase(null), Number.PLURAL, new Gender(Gender.NEUTRAL));
        if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(accusativeSingular)) {
            ((Noun) nextWord).setElectedCaseNumber(accusativeSingular);
        } else if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(accusativePlural)) {
            ((Noun) nextWord).setElectedCaseNumber(accusativeSingular);
        }
    }


}

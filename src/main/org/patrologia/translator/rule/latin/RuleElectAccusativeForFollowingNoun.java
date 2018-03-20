package org.patrologia.translator.rule.latin;

import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.WordType;
import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.casenumbergenre.Number;
import org.patrologia.translator.casenumbergenre.latin.LatinCaseFactory;
import org.patrologia.translator.rule.Rule;

/**
 * Created by Laurent KLOEBLE on 10/09/2015.
 */
public class RuleElectAccusativeForFollowingNoun extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        if(nextWord.getInitialValue().equals("pulcherrima")) System.out.println("ruleElectAccusative");
        CaseNumberGenre accusativeSingular = new CaseNumberGenre(LatinCaseFactory.getAccusative(), Number.SINGULAR, new Gender(Gender.NEUTRAL));
        CaseNumberGenre accusativePlural = new CaseNumberGenre(LatinCaseFactory.getAccusative(), Number.PLURAL, new Gender(Gender.NEUTRAL));
        if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(accusativeSingular)) {
            ((Noun) nextWord).setElectedCaseNumber(accusativeSingular);
        } else if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(accusativePlural)) {
            ((Noun) nextWord).setElectedCaseNumber(accusativeSingular);
        }
    }
}

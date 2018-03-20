package org.patrologia.translator.rule.latin;

import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.WordType;
import org.patrologia.translator.casenumbergenre.*;
import org.patrologia.translator.casenumbergenre.Number;
import org.patrologia.translator.casenumbergenre.latin.LatinCaseFactory;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 14/11/2016.
 */
public class RuleElectDativeForFollowingNoun  extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        CaseNumberGenre dativeSingular = new CaseNumberGenre(LatinCaseFactory.getDative(), Number.SINGULAR, new Gender(Gender.NEUTRAL));
        CaseNumberGenre dativePlural = new CaseNumberGenre(LatinCaseFactory.getDative(), Number.PLURAL, new Gender(Gender.NEUTRAL));
        if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(dativeSingular)) {
            ((Noun) nextWord).setElectedCaseNumber(dativeSingular);
        } else if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(dativePlural)) {
            ((Noun) nextWord).setElectedCaseNumber(dativeSingular);
        }
    }

}

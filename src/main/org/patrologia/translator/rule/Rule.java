package org.patrologia.translator.rule;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.casenumbergenre.*;
import org.patrologia.translator.casenumbergenre.Number;

/**
 * Created by Laurent KLOEBLE on 25/08/2015.
 */
public abstract class Rule implements Comparable {

    protected Language language;

    public abstract void apply(Word word, Phrase phrase, int position);

    protected boolean checkByGender(Gender genre, Word nextWord, Case _case) {
        CaseNumberGenre caseSingular = new CaseNumberGenre(_case, org.patrologia.translator.casenumbergenre.Number.SINGULAR, genre);
        CaseNumberGenre casePlural = new CaseNumberGenre(_case, Number.PLURAL, genre);
        if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(caseSingular)) {
            ((Noun) nextWord).setElectedCaseNumber(caseSingular);
            return true;
        } else if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(casePlural)) {
            ((Noun) nextWord).setElectedCaseNumber(casePlural);
            return true;
        } else if(nextWord.hasType(WordType.PREPOSITION) && ((Preposition) nextWord).hasCase(_case)) {
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        Rule otherRule = (Rule)o;
        return this.getClass().toString().compareTo(otherRule.getClass().toString());
    }
}

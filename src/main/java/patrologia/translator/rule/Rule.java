package patrologia.translator.rule;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.Number;

public abstract class Rule implements Comparable {

    protected Language language;

    protected Integer precedenceOrder = 0;

    public abstract void apply(Word word, Phrase phrase, int position);

    protected boolean checkByGender(Gender genre, Word nextWord, Case _case) {
        CaseNumberGenre caseSingular = new CaseNumberGenre(_case, Number.SINGULAR, genre);
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

    protected String extractParameterInsideParenthesis(String parameterInside) {
        return parameterInside.split("\\(")[1].split("\\)")[0];
    }

    @Override
    public int compareTo(Object o) {
        Rule otherRule = (Rule)o;
        if(otherRule.precedenceOrder != this.precedenceOrder) return precedenceOrder.compareTo(otherRule.precedenceOrder);
        return this.getClass().toString().compareTo(otherRule.getClass().toString());
    }


}

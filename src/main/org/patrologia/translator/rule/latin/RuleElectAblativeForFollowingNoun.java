package org.patrologia.translator.rule.latin;

import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.WordType;
import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.casenumbergenre.Number;
import org.patrologia.translator.casenumbergenre.latin.AblativeLatinCase;
import org.patrologia.translator.casenumbergenre.latin.LatinCaseFactory;
import org.patrologia.translator.rule.Rule;

/**
 * Created by Laurent KLOEBLE on 25/08/2015.
 */
public class RuleElectAblativeForFollowingNoun extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        if(nextWord.getInitialValue().equals("pulcherrima")) System.out.println("ruleElectAblative");
        checkByGender(new Gender(Gender.MASCULINE), nextWord);
        checkByGender(new Gender(Gender.FEMININE), nextWord);
        checkByGender(new Gender(Gender.NEUTRAL), nextWord);
        checkByGender(new Gender(Gender.ADJECTIVE), nextWord);
    }

    private void checkByGender(Gender gender, Word nextWord) {
        CaseNumberGenre ablativeSingular = new CaseNumberGenre(new AblativeLatinCase(null), Number.SINGULAR, gender);
        CaseNumberGenre ablativePlural = new CaseNumberGenre(new AblativeLatinCase(null), Number.PLURAL, gender);
        if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(ablativeSingular)) {
            ((Noun) nextWord).setElectedCaseNumber(ablativeSingular);
        } else if (nextWord.hasType(WordType.NOUN) && ((Noun) nextWord).hasPossibleCaseNumber(ablativePlural)) {
            ((Noun) nextWord).setElectedCaseNumber(ablativePlural);
        }
    }
}

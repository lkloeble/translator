package patrologia.translator.rule.latin;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.Number;
import patrologia.translator.casenumbergenre.latin.AblativeLatinCase;
import patrologia.translator.rule.Rule;

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

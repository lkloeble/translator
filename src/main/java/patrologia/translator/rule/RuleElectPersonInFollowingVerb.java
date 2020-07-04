package patrologia.translator.rule;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.conjugation.ConjugationPosition;

public class RuleElectPersonInFollowingVerb extends Rule {

    private ConjugationPosition personIndex;

    public RuleElectPersonInFollowingVerb(String personIndexParameter) {
        personIndex = ConjugationPosition.valueOf(personIndexParameter);
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getWordByType(WordType.VERB);
        Word precedingWord = phrase.getWordContainerAtPosition(position-1).getWordByType(WordType.VERB);
        if(followingWord.isTypeUnknow() || !precedingWord.isTypeUnknow()) {
            applyInReverseOrder(word,phrase,position);
            return;
        }
        if(Word.WORD_TO_REMOVE.equals(followingWord.getInitialValue())) {
            followingWord = phrase.getWordContainerAtPosition(position+2).getWordByType(WordType.VERB);
        }
        if(followingWord.isTypeUnknow()) {
            applyInReverseOrder(word,phrase,position);
            return;
        }
        Verb followingVerb = (Verb)followingWord;
        followingVerb.setPositionInTranslationTable(personIndex);
        followingVerb.addForbiddenConjugation(Conjugation2.ACTIVE_IMPERATIVE_PRESENT);
        followingVerb.addForbiddenConjugation(Conjugation2.INFINITIVE);

    }

    private void applyInReverseOrder(Word word, Phrase phrase, int position) {
        Word precedingWord = phrase.getWordContainerAtPosition(position-1).getWordByType(WordType.VERB);
        if(precedingWord.isTypeUnknow()) return;
        Verb precedingVerb = (Verb)precedingWord;
        precedingVerb.setPositionInTranslationTable(personIndex);
        precedingVerb.addForbiddenConjugation(Conjugation2.ACTIVE_IMPERATIVE_PRESENT);
        precedingVerb.addForbiddenConjugation(Conjugation2.INFINITIVE);
    }
}

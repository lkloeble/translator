package org.patrologia.translator.rule;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Verb;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.WordType;
import org.patrologia.translator.conjugation.Conjugation;

/**
 * Created by lkloeble on 19/02/2017.
 */
public class RuleElectPersonInFollowingVerb extends Rule {

    private Integer personIndex;

    public RuleElectPersonInFollowingVerb(String personIndexParameter) {
        try {
            personIndex = Integer.parseInt(personIndexParameter);
        } catch (NumberFormatException nfe) {
            System.out.println("wrong indexParameter " +  personIndexParameter + "\n" + nfe);
        }
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
        followingVerb.addForbiddenConjugation(Conjugation.ACTIVE_IMPERATIVE_PRESENT);
        followingVerb.addForbiddenConjugation(Conjugation.INFINITIVE);

    }

    private void applyInReverseOrder(Word word, Phrase phrase, int position) {
        Word precedingWord = phrase.getWordContainerAtPosition(position-1).getWordByType(WordType.VERB);
        if(precedingWord.isTypeUnknow()) return;
        Verb precedingVerb = (Verb)precedingWord;
        precedingVerb.setPositionInTranslationTable(personIndex);
        precedingVerb.addForbiddenConjugation(Conjugation.ACTIVE_IMPERATIVE_PRESENT);
        precedingVerb.addForbiddenConjugation(Conjugation.INFINITIVE);
    }
}

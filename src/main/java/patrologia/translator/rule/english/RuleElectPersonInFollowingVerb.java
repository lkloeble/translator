package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.rule.Rule;

public class RuleElectPersonInFollowingVerb extends Rule {

    private Integer personIndex;

    public RuleElectPersonInFollowingVerb(String personIndexParameter) {
        try {
            personIndex = Integer.parseInt(personIndexParameter);
        } catch (NumberFormatException nfe) {
            System.out.println("wrong indexParameter " + personIndexParameter + "\n" + nfe);
        }
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position + 1).getWordByType(WordType.VERB);
        Word precedingWord = phrase.getWordContainerAtPosition(position - 1).getWordByType(WordType.VERB);
        if (followingWord.isTypeUnknow() || !precedingWord.isTypeUnknow()) {
            applyInReverseOrder(word, phrase, position);
            return;
        }
        if (Word.WORD_TO_REMOVE.equals(followingWord.getInitialValue())) {
            followingWord = phrase.getWordContainerAtPosition(position + 2).getWordByType(WordType.VERB);
        }
        if (followingWord.isTypeUnknow()) {
            applyInReverseOrder(word, phrase, position);
            return;
        }
        Verb followingVerb = (Verb) followingWord;
        followingVerb.setPositionInTranslationTable(personIndex);
        followingVerb.addForbiddenConjugation(Conjugation2.ACTIVE_IMPERATIVE_PRESENT);
        followingVerb.addForbiddenConjugation(Conjugation2.INFINITIVE);

    }

    private void applyInReverseOrder(Word word, Phrase phrase, int position) {
        Word precedingWord = phrase.getWordContainerAtPosition(position - 1).getWordByType(WordType.VERB);
        if (precedingWord.isTypeUnknow()) return;
        Verb precedingVerb = (Verb) precedingWord;
        precedingVerb.setPositionInTranslationTable(personIndex);
        precedingVerb.addForbiddenConjugation(Conjugation2.ACTIVE_IMPERATIVE_PRESENT);
        precedingVerb.addForbiddenConjugation(Conjugation2.INFINITIVE);
    }
}

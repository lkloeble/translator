package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.rule.Rule;

public class RuleElectInfinitiveForFollowingVerb extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getWordByType(WordType.VERB);
        if(followingWord == null || followingWord.isTypeUnknow()) return;
        Verb followingVerb = (Verb)followingWord;
        followingVerb.setConjugation(Conjugation2.INFINITIVE);
        followingVerb.updateInitialValue("to " + followingVerb.getInitialValue());
        word.updateInitialValue(Word.WORD_TO_REMOVE);
    }

}

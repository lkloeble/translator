package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.conjugation.Conjugation;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 10/10/2016.
 */
public class RuleElectInfinitiveForFollowingVerb extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getWordByType(WordType.VERB);
        if(followingWord == null || followingWord.isTypeUnknow()) return;
        Verb followingVerb = (Verb)followingWord;
        followingVerb.setConjugation(Conjugation.INFINITIVE);
        followingVerb.updateInitialValue("to " + followingVerb.getInitialValue());
        word.updateInitialValue(Word.WORD_TO_REMOVE);
    }
}

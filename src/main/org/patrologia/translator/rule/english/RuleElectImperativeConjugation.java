package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Verb;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.conjugation.Conjugation;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 10/10/2016.
 */
public class RuleElectImperativeConjugation extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getYetUnknownWordAtPosition(position+1);
        if(followingWord == null || !followingWord.isVerb()) {
            return;
        }
        Verb followingVerb = (Verb)followingWord;
        followingVerb.setConjugation(Conjugation.ACTIVE_IMPERATIVE_PRESENT);
        followingVerb.setPositionInTranslationTable(2);//position pour le let's buy => achetons
        followingVerb.addForbiddenConjugation(Conjugation.ACTIVE_INDICATIVE_PRESENT);
    }
}

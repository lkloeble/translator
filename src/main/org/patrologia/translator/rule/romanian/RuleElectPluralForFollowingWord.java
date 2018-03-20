package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.verb.Verb;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RuleElectPluralForFollowingWord extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if(followingWord.isVerb()) {
            Verb followingVerb = (Verb)followingWord;
            followingVerb.setPluralKnown(true);
        }
    }
}

package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;

/**
 * Created by lkloeble on 03/07/2017.
 */
public class RuleVerbUpFinder extends RuleVerbAndPrepositionAfter {

    public RuleVerbUpFinder()  {
        upVerbs.add("follow");
        upVerbs.add("lay");
        upVerbs.add("rise");
        this.preposition = "up";
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        apply(word, phrase, position, preposition);
    }

}

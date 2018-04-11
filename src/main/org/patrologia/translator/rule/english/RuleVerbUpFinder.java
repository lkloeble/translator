package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;

/**
 * Created by lkloeble on 03/07/2017.
 */
public class RuleVerbUpFinder extends RuleVerbAndPrepositionAfter {

    public RuleVerbUpFinder()  {
        prepositionVerbs.add("follow");
        prepositionVerbs.add("lay");
        prepositionVerbs.add("rise");
        this.preposition = "up";
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        apply(word, phrase, position, preposition);
    }

}

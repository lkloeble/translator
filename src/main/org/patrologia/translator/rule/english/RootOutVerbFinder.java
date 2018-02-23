package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;

public class RootOutVerbFinder extends RuleVerbAndPrepositionAfter {

    public RootOutVerbFinder()  {
        upVerbs.add("root");
        this.preposition = "out";
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        apply(word, phrase, position, preposition);
    }


}

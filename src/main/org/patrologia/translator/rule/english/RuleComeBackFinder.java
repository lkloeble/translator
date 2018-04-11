package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

public class RuleComeBackFinder extends RuleVerbAndPrepositionAfter {

    public RuleComeBackFinder()  {
        prepositionVerbs.add("come");
        this.preposition = "back";
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        apply(word, phrase, position, preposition);
    }

}

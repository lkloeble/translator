package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RootOutVerbFinder extends RuleVerbAndPrepositionAfter {

    public RootOutVerbFinder()  {
        prepositionVerbs.add("root");
        this.preposition = "out";
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        apply(word, phrase, position, preposition);
    }


}


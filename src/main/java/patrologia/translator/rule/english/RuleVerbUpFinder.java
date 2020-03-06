package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

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

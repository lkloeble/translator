package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

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

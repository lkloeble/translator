package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.rule.Rule;

public class RuleFindGenitive extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getYetUnknownWordAtPosition(position+1);
        if(!nextWord.isNoun()) return;
        Noun nextNoun = (Noun)nextWord;
        if(nextNoun.isGenitive()) {
            word.updateRoot("aaxxaa");
            word.updateInitialValue("aaxxaa");
        }
    }
}

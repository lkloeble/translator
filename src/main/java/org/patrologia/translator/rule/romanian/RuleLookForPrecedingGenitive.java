package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 18/04/2017.
 */
public class RuleLookForPrecedingGenitive extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word precedingWord = phrase.getWordContainerAtPosition(position-1).getUniqueWord();
        if(!precedingWord.isNoun()) return;
        Noun precedingNoun = (Noun)precedingWord;
        if(precedingNoun.isGenitive()) {
            word.updateInitialValue("luigenitive");
            word.updateRoot("luigenitive");
        }
    }
}

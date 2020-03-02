package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.rule.Rule;

public class RuleOArticleFinder extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        WordContainer nextWordContainer = phrase.getWordContainerAtPosition(position + 1);
        if (nextWordContainer == null) {
            return;
        }
        Word nextWord = nextWordContainer.getUniqueWord();
        if (!nextWord.isNoun()) {
            return;
        }
        Noun nextNoun = (Noun) nextWord;
        nextNoun.setWithArticle();
    }
}

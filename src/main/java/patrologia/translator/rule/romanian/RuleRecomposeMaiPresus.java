package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleRecomposeMaiPresus extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("presus".equals(followingWord.getInitialValue())) {
            word.setInitialValue("maipresus");
            word.setRoot("maipresus");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }
}

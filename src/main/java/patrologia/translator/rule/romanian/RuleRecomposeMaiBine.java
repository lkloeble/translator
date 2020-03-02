package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleRecomposeMaiBine extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("bine".equals(followingWord.getInitialValue())) {
            word.setInitialValue("maibine");
            word.setRoot("maibine");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }
}

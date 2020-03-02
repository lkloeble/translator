package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleRecomposeCeFel extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("fel".equals(followingWord.getInitialValue())) {
            word.setInitialValue("cefel");
            word.setRoot("cefel");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }
}

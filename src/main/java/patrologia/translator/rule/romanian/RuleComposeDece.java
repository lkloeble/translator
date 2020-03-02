package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleComposeDece extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("ce".equals(followingWord.getInitialValue())) {
            word.setInitialValue("dece");
            word.setRoot("dece");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }
}

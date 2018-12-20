package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 12/04/2017.
 */
public class RuleRecomposeALui extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("lui".equals(followingWord.getInitialValue())) {
            word.setInitialValue("alui");
            word.setRoot("alui");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

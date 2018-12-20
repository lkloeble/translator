package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 18/04/2017.
 */
public class RuleComposeSpreCare extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("care".equals(followingWord.getInitialValue())) {
            word.setInitialValue("sprecare");
            word.setRoot("sprecare");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

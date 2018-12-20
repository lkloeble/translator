package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 31/05/2017.
 */
public class RuleComposeCuAtitMaiBine extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("maibine".equals(followingWord.getInitialValue())) {
            word.setInitialValue("cuatitmaibine");
            word.setRoot("cuatitmaibine");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

}

package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 02/02/2017.
 */
public class RuleDinFatsaFinder extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        Word followingExtendedWord = phrase.getWordContainerAtPosition(position + 2).getUniqueWord();
        if ("din".equals(word.getInitialValue()) &&
                "femininearticle".equals(followingWord.getInitialValue()) &&
                "fatsa".equals(followingExtendedWord.getInitialValue())) {
            word.setInitialValue("dinfatsa");
            word.setRoot("dinfatsa");
            followingWord.setInitialValue("xxtoremovexx");
            followingExtendedWord.setInitialValue("xxtoremovexx");
        }
    }

}

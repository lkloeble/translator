package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 01/06/2016.
 */
public class RuleAsVreaFinder extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("as".equals(word.getInitialValue()) && "vrea".equals(followingWord.getInitialValue())) {
            word.setInitialValue("asvrea");
            word.setRoot("asvrea");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }
}

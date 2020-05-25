package patrologia.translator.rule.greek;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleAvaMesovFinder extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("μεσον".equals(followingWord.getInitialValue())) {
            word.setInitialValue("αναμεσον");
            word.setRoot("αναμεσον");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }


}

package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleComposeDinVeci extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("veci".equals(followingWord.getInitialValue())) {
            word.setInitialValue("dinveci");
            word.setRoot("dinveci");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }
}

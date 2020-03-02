package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleComposeArVerb extends Rule {

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if(followingWord.isVerb()) {
            followingWord.setInitialValue("ar" + followingWord.getInitialValue());
            word.setInitialValue("xxtoremovexx");
        }
    }

}

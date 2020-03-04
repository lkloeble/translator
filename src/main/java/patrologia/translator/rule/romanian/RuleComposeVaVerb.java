package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleComposeVaVerb extends Rule {

    public RuleComposeVaVerb(int precedenceOrder) {
        this.precedenceOrder = precedenceOrder;
    }

    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if(!word.isPreposition() && followingWord.isVerb()) {
            followingWord.setInitialValue(followingWord.getInitialValue() + "va");
            word.setInitialValue("xxtoremovexx");
        }
    }

}

package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.rule.Rule;

public class RulePrepAndWordToAssemble extends Rule {

    protected String leadingPrepositionValue;
    protected String followingNounValue;
    protected String resultToSubstitue;

    public RulePrepAndWordToAssemble(String leadingPrepositionValue, String followingNounValue, String resultToSubstitue, int precedenceOrder) {
        this.leadingPrepositionValue = leadingPrepositionValue;
        this.followingNounValue = followingNounValue;
        this.resultToSubstitue = resultToSubstitue;
        this.precedenceOrder = precedenceOrder;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position + 1).getUniqueWord();
        if (leadingPrepositionValue.equals(word.getInitialValue()) && followingNounValue.equals(followingWord.getInitialValue())) {
            word.setInitialValue(resultToSubstitue);
            word.setRoot(resultToSubstitue);
            followingWord.setInitialValue("xxtoremovexx");
            WordContainer wordContainer = phrase.getWordContainerAtPosition(position);
            wordContainer.deleteAllWordTypeExceptThisOne(WordType.PREPOSITION);
        }
    }
}

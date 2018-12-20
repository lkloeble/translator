package patrologia.translator.rule;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;

/**
 * Created by lkloeble on 06/06/2016.
 */
public class TwoWordsFusionRule extends Rule {

    private String firstWordValue;
    private String secondWordValue;
    private String fusionWordsResult;
    private boolean normalOrderFollowing = true;

    public TwoWordsFusionRule(String firstWordValue, String secondWordValue, String fusionWordsResult) {
        this.firstWordValue = firstWordValue;
        this.secondWordValue = secondWordValue;
        this.fusionWordsResult = fusionWordsResult;
    }

    public TwoWordsFusionRule(String firstWordValue, String secondWordValue, String fusionWordsResult, boolean followingOrder) {
        this.firstWordValue = firstWordValue;
        this.secondWordValue = secondWordValue;
        this.fusionWordsResult = fusionWordsResult;
        this.normalOrderFollowing = followingOrder;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        if(firstWordValue == null || secondWordValue == null) {
            return;
        }
        int offset =1;
        if(!normalOrderFollowing) offset = -1;
        Word followingWord = phrase.getWordContainerAtPosition(position+offset).getUniqueWord();
        if(firstWordValue.equals(word.getInitialValue()) && secondWordValue.equals(followingWord.getInitialValue())) {
            phrase.getWordContainerAtPosition(position).deleteWordByWordType(WordType.NOUN);
            word.setInitialValue(fusionWordsResult);
            word.setRoot(fusionWordsResult);
            followingWord.setInitialValue("xxtoremovexx");
            followingWord.setRoot("xxtoremovexx");
        }
    }

}

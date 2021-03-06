package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

public class RuleThereIsFusion extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if("is".equals(followingWord.getInitialValue()) || "are".equals(followingWord.getInitialValue())) {
            followingWord.modifyContentByPatternReplacement("is","xxtoremovexx");
            followingWord.modifyContentByPatternReplacement("are","xxtoremovexx");
            word.modifyContentByPatternReplacement("there","thereis");
        }
    }
}

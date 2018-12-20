package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 30/08/2016.
 */
public class RuleIsThereFusion extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word precedingWord = phrase.getWordContainerAtPosition(position-1).getUniqueWord();
        if("is".equals(precedingWord.getInitialValue()) || "are".equals(precedingWord.getInitialValue())) {
            precedingWord.modifyContentByPatternReplacement("is","xxtoremovexx");
            precedingWord.modifyContentByPatternReplacement("are","xxtoremovexx");
            word.modifyContentByPatternReplacement("there","isthere");
        }

    }
}

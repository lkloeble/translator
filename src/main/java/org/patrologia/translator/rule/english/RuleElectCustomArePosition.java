package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 25/01/2016.
 */
public class RuleElectCustomArePosition extends Rule {

    private int areVerbPosition;

    public RuleElectCustomArePosition(String ruleName) {
        areVerbPosition = extractCustomPosition(ruleName);
    }

    private int extractCustomPosition(String ruleName) {
        if(ruleName == null || ruleName.indexOf("(") == 0) return 6;
        String position = ruleName.substring(ruleName.indexOf("(")+1, ruleName.indexOf(")"));
        return Integer.parseInt(position);
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        WordContainer wordContainer = phrase.getWordContainerAtPosition(position+1);
        Word nextWord = wordContainer.getUniqueWord();
        if(!nextWord.isVerb()) {
            return;
        }
        Verb verb = (Verb)nextWord;
        verb.setPluralKnown(true);
        verb.setPositionInTranslationTable(areVerbPosition-1);
        //System.out.println(word);
    }

}

package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.conjugation.ConjugationPosition;
import patrologia.translator.rule.Rule;

public class RuleElectCustomArePosition extends Rule {

    private ConjugationPosition conjugationPosition;

    public RuleElectCustomArePosition(String ruleName) {
        conjugationPosition = extractCustomPosition(ruleName);
    }

    private ConjugationPosition extractCustomPosition(String ruleName) {
        if(ruleName == null || ruleName.indexOf("(") == 0) return ConjugationPosition.PLURAL_THIRD_PERSON;
        return ConjugationPosition.valueOf(extractParameterInsideParenthesis(ruleName));
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
        verb.setPositionInTranslationTable(conjugationPosition);
        //System.out.println(word);
    }


}

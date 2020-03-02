package patrologia.translator.rule.romanian;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.rule.Rule;

public class RuleElectPluralForFollowingWord extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        if(followingWord.isVerb()) {
            Verb followingVerb = (Verb)followingWord;
            followingVerb.setPluralKnown(true);
        }
    }
}

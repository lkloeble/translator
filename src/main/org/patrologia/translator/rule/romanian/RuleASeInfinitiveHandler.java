package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.basicelements.verb.Verb;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.rule.Rule;

public class RuleASeInfinitiveHandler extends Rule {

    private VerbRepository verbRepository;

    public RuleASeInfinitiveHandler(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getWordContainerAtPosition(position+1).getUniqueWord();
        Word followingNextWord = phrase.getWordContainerAtPosition(position+2).getUniqueWord();
        if("a".equals(word.getInitialValue()) && "se".equals(followingWord.getInitialValue()) && isInfinitive(followingNextWord)) {
            word.setInitialValue("se");
            word.setRoot("se");
            followingWord.setInitialValue("xxtoremovexx");
        }
    }

    private boolean isInfinitive(Word word) {
        if(verbRepository.hasVerb( word.getInitialValue())) {
            Verb verb = verbRepository.getVerb(word.getInitialValue());
            return verbRepository.isPossibleInfinitive(verb);
        }
        return false;
    }

}

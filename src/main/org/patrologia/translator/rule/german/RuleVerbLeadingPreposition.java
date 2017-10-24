package org.patrologia.translator.rule.german;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 13/06/2016.
 */
public class RuleVerbLeadingPreposition extends Rule {

    private VerbRepository verbRepository;

    public RuleVerbLeadingPreposition(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        if(!phraseContainsSomeComposedVerbsBeforePreposition(word, phrase, position)) {
            return;
        }
        int positionOfComposedVerbBeforePreposition = getPositionOfComposedVerbBeforePreposition(word, phrase, position);
        Verb toReplace = (Verb) phrase.getWordContainerAtPosition(positionOfComposedVerbBeforePreposition).getWordByType(WordType.VERB);
        toReplace.setRoot(word.getRoot() + toReplace.getRoot());
        toReplace.setInitialValue(word.getRoot() + toReplace.getInitialValue());
        word.setInitialValue("xxtoremovexx");
        word.setRoot("xxtoremovexx");
    }

    private boolean phraseContainsSomeComposedVerbsBeforePreposition(Word word, Phrase phrase, int position) {
        return getPositionOfComposedVerbBeforePreposition(word, phrase, position) > 0;
    }

    private int getPositionOfComposedVerbBeforePreposition(Word word, Phrase phrase, int position) {
        for(int wordPosition = 1;wordPosition<position;wordPosition++) {
            Word wordAtPosition = phrase.getWordContainerAtPosition(wordPosition).getUniqueWord();
            if(WordType.VERB != wordAtPosition.getWordType()) {
                continue;
            }
            if(verbRepository.hasVerb(word.getInitialValue() + wordAtPosition.getInitialValue())) {
                return wordPosition;
            }
        }
        return -1;
    }
}

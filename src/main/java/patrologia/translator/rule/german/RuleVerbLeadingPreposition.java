package patrologia.translator.rule.german;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.rule.Rule;

public class RuleVerbLeadingPreposition  extends Rule {

    private VerbRepository2 verbRepository;

    public RuleVerbLeadingPreposition(VerbRepository2 verbRepository) {
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


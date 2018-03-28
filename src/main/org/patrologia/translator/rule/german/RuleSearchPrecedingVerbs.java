package org.patrologia.translator.rule.german;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.verb.Verb;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 21/04/2016.
 */
public class RuleSearchPrecedingVerbs extends Rule {

    private VerbRepository verbRepository;
    private String preposition;

    public RuleSearchPrecedingVerbs(VerbRepository verbRepository, String preposition) {
        this.preposition = preposition;
        this.verbRepository = verbRepository;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        if(!isAPrecedingVerbForm(phrase, position)) {
            return;
        }
        Verb precedingVerb = getPrecedingVerb(phrase, position);
        int verbPosition = getVerbPosition(phrase, precedingVerb);
        contractVerbWithSuffix(phrase, word, precedingVerb, verbPosition, position);
    }

    private void contractVerbWithSuffix(Phrase phrase, Word suffix, Verb verb, int verbPosition, int suffixPosition) {
        String initialRoot = verb.getRoot();
        phrase.modifyWordContenAndRootAtPosition(suffix.getInitialValue() + verb.getInitialValue(), verbPosition);
        verb.updateRoot(suffix.getInitialValue() + initialRoot);
        phrase.updateWordContentAtPosition("xxtoremovexx", suffixPosition);
    }

    private int getVerbPosition(Phrase phrase, Verb precedingVerb) {
        for(int position=1;position<phrase.getMaxIndice();position++) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(position);
            Word word = wordContainer.getUniqueWord();
            if(word.isVerb() && word.getInitialValue().equals(precedingVerb.getInitialValue())) {
                return  position;
            }
        }
        return 0;
    }

    private Verb getPrecedingVerb(Phrase phrase, int position) {
        for(int indice=1;indice<position;indice++) {
            WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
            Word word = wordContainerAtPosition.getUniqueWord();
            if(!word.isVerb()) {
                continue;
            }
            if(verbRepository.hasVerb(word.getInitialValue()) && verbRepository.hasVerb(preposition + word.getInitialValue())) {
                return (Verb)word;
            }
        }
        return null;
    }

    private boolean isAPrecedingVerbForm(Phrase phrase, int position) {
        return getPrecedingVerb(phrase, position) != null;
    }
}

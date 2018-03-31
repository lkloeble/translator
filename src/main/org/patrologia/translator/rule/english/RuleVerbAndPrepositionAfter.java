package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.verb.Verb;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

import java.util.HashSet;
import java.util.Set;

public abstract class RuleVerbAndPrepositionAfter extends Rule {

    protected Set<String> upVerbs = new HashSet<>();
    protected String preposition;

    protected void apply(Word word, Phrase phrase, int position, String preposition) {
        Word precedingWord = phrase.getWordContainerAtPosition(position - 1).getUniqueWord();
        if(!precedingWord.isVerb()) return;
        Verb precedingVerb = (Verb)precedingWord;
        String root = precedingVerb.getRoot();
        if(!upVerbs.contains(root)) return;
        precedingVerb.updateRoot(preposition + root);
        boolean prefixed = precedingVerb.getInitialValue().startsWith("to ");
        precedingVerb.updateInitialValue(preposition + precedingVerb.getInitialValue().replace("to ",""));
        if(prefixed) precedingVerb.updateInitialValue("to " + precedingVerb.getInitialValue());
        word.setInitialValue("xxtoremovexx");
        word.setRoot("xxtoremovexx");
    }

}
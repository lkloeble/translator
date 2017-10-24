package org.patrologia.translator.rule.english;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.Verb;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lkloeble on 03/07/2017.
 */
public class RuleVerbUpFinder extends Rule {

    Set<String> upVerbs = new HashSet<>();

    public RuleVerbUpFinder()  {
        upVerbs.add("follow");
        upVerbs.add("lay");
        upVerbs.add("rise");
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word precedingWord = phrase.getWordContainerAtPosition(position - 1).getUniqueWord();
        if(!precedingWord.isVerb()) return;
        Verb precedingVerb = (Verb)precedingWord;
        String root = precedingVerb.getRoot();
        if(!upVerbs.contains(root)) return;
        precedingVerb.updateRoot("up" + root);
        boolean prefixed = precedingVerb.getInitialValue().startsWith("to ");
        precedingVerb.updateInitialValue("up" + precedingVerb.getInitialValue().replace("to ",""));
        if(prefixed) precedingVerb.updateInitialValue("to " + precedingVerb.getInitialValue());
        word.setInitialValue("xxtoremovexx");
        word.setRoot("xxtoremovexx");
    }

}

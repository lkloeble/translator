package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.Word;
import patrologia.translator.rule.Rule;

import java.util.HashSet;
import java.util.Set;

public abstract class RuleVerbAndPrepositionAfter extends Rule {

    protected Set<String> prepositionVerbs = new HashSet<>();
    protected String preposition;

    protected void apply(Word word, Phrase phrase, int position, String preposition) {
        Word precedingWord = phrase.getWordContainerAtPosition(position - 1).getUniqueWord();
        if(!precedingWord.isVerb()) return;
        Verb precedingVerb = (Verb)precedingWord;
        String root = precedingVerb.getRoot();
        if(!prepositionVerbs.contains(root)) return;
        precedingVerb.updateRoot(preposition + root);
        boolean prefixed = precedingVerb.getInitialValue().startsWith("to ");
        precedingVerb.updateInitialValue(preposition + precedingVerb.getInitialValue().replace("to ",""));
        if(prefixed) precedingVerb.updateInitialValue("to " + precedingVerb.getInitialValue());
        word.setInitialValue("xxtoremovexx");
        word.setRoot("xxtoremovexx");
    }

}

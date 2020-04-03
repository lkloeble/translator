package patrologia.translator.rule.german;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleEsBeforeVerb extends Rule {

    List<String> exceptionVerbs = new ArrayList<>();

    public RuleEsBeforeVerb() {
        exceptionVerbs.add("sein");
        exceptionVerbs.add("geb");
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getYetUnknownWordAtPosition(position+1);
        if(word.getInitialValue().equals("es") && followingWord != null && followingWord.isVerb() && !exceptionVerbs.contains(followingWord.getRoot())) {
            Preposition es = (Preposition)word;
            es.setDistinctiveTranslationByNumber();
        }
    }
}


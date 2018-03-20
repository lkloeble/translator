package org.patrologia.translator.rule.german;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.basicelements.Word;
import org.patrologia.translator.rule.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 08/09/2016.
 */
public class RuleEsBeforeVerb extends Rule {

    List<String>  exceptionVerbs = new ArrayList<>();

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

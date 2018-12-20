package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.Word;

import java.util.Comparator;

/**
 * Created by lkloeble on 18/03/2016.
 */
public class WordComparator implements Comparator<Word> {

    @Override
    public int compare(Word w1, Word w2) {
        if(w1.isNoun() && w2.isNoun()) {
            Noun n1 = (Noun)w1;
            Noun n2 = (Noun)w2;
            if(n1.isNotAnAdjective() && !n2.isNotAnAdjective()) {
                return -1;
            }
        }
        return w1.getInitialValue().compareTo(w2.getInitialValue());
    }
}

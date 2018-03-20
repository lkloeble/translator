package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.Word;

import java.util.Comparator;

/**
 * Created by lkloeble on 27/03/2017.
 */
public class WordPossibleCaseComparator implements Comparator<Word> {

    WordGenderComparator wordGenderComparator = new WordGenderComparator();

    @Override
    public int compare(Word w1, Word w2) {
        if(!w1.isNoun() || !w2.isNoun()) return wordGenderComparator.compare(w1,w2);
        Noun n1 = (Noun)w1;
        Noun n2 = (Noun)w2;
        Integer size1 = n1.getPossibleCaseNumbers().size();
        Integer size2 = n2.getPossibleCaseNumbers().size();
        return size1.compareTo(size2);
    }
}

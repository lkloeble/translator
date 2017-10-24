package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.Word;

import java.util.Comparator;

/**
 * Created by lkloeble on 13/03/2017.
 */
public class WordGenderComparator implements Comparator<Word> {

    @Override
    public int compare(Word w1, Word w2) {
        return w1.getGender().compareTo(w2.getGender());
    }
}

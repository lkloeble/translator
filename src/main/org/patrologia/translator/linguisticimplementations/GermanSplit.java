package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.Word;

import java.util.*;

/**
 * Created by lkloeble on 22/07/2017.
 */
public class GermanSplit {

    private List<Word> words = new ArrayList<>();
    private static final Integer LEFT_POSITION = 0;
    private static final Integer RIGHT_POSITION = 1;

    public GermanSplit(Word... wordParams) {
        for(Word word : wordParams) {
            if(word != null) {
                words.add(word);
            }
        }
    }

    public int length() {
        return words.size();
    }

    public Word getLeftValue() {
        return getAtPosition(LEFT_POSITION);
    }

    public Word getRightValue() {
        return getAtPosition(RIGHT_POSITION);
    }

    public Word getAtPosition(int position) {
        return new ArrayList<>(words).get(position);
    }

    public String toString() {
        return "GS : " + words;
    }
}

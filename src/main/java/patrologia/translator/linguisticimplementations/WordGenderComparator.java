package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.Word;

import java.util.Comparator;

public class WordGenderComparator  implements Comparator<Word> {

    @Override
    public int compare(Word w1, Word w2) {
        return w1.getGender().compareTo(w2.getGender());
    }
}

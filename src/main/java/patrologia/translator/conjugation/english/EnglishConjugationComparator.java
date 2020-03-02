package patrologia.translator.conjugation.english;

import patrologia.translator.conjugation.ConjugationComparator;

public class EnglishConjugationComparator extends ConjugationComparator {

    @Override
    public int compare(String o1, String o2) {
        if (o1.equals("IPR")) return -1000;
        return super.compare(o1, o2);
    }
}

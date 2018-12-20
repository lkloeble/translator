package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.noun.NounRepository;

public class WordRootFinder {

    protected NounRepository nounRepository;

    public WordRootFinder(NounRepository nounRepository) {
        this.nounRepository = nounRepository;
    }

    public WordRootFinder() {

    }

    public boolean wordExists(String word) {
        return true;
    }
}

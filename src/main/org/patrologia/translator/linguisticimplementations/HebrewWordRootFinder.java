package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.noun.NounRepository;

public class HebrewWordRootFinder extends WordRootFinder {

    public HebrewWordRootFinder(NounRepository nounRepository) {

        super(nounRepository);
    }

    @Override
    public boolean wordExists(String word) {
        return nounRepository.hasNoun(word);
    }
}

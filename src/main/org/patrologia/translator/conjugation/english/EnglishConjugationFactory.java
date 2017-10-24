package org.patrologia.translator.conjugation.english;

import org.patrologia.translator.conjugation.Conjugation;
import org.patrologia.translator.conjugation.ConjugationComparator;
import org.patrologia.translator.conjugation.ConjugationFactory;
import org.patrologia.translator.conjugation.VerbDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class EnglishConjugationFactory extends ConjugationFactory {

    private Map<String, List<String>> conjugationsDefinitionsList;

    public EnglishConjugationFactory(List<String> conjugationDefinitions, Map<String, List<String>> conjugationsDefinitionsList) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        populateConjugationMap(conjugationDefinitions);
    }

    @Override
    public Conjugation getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullEnglishConjugation();
        }
        return new EnglishConjugation(conjugationsDefinitionsList.get(conjugationPattern), verbDefinition);
    }

    @Override
    public String getConjugationSynonym(VerbDefinition verbDefinition) {
        return getConjugationByPattern(verbDefinition).getSynonym();
    }

    @Override
    public ConjugationComparator getComparator() {
        return new EnglishConjugationComparator();
    }
}

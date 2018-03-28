package org.patrologia.translator.conjugation.german;

import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.conjugation.Conjugation;
import org.patrologia.translator.conjugation.ConjugationFactory;
import org.patrologia.translator.conjugation.VerbDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanConjugationFactory extends ConjugationFactory {

    private Map<String, List<String>> conjugationsDefinitionsList;
    private NounRepository nounRepository;

    public GermanConjugationFactory(List<String> conjugationDefinitions, Map<String, List<String>> conjugationsDefinitionsList, NounRepository nounRepository) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        this.nounRepository = nounRepository;
        populateConjugationMap(conjugationDefinitions);
    }

    @Override
    public Conjugation getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullGermanConjugation();
        }
        return new GermanConjugation(conjugationsDefinitionsList.get(conjugationPattern), verbDefinition, nounRepository);
    }

    @Override
    public String getConjugationSynonym(VerbDefinition verbDefinition) {
        return getConjugationByPattern(verbDefinition).getSynonym();
    }

}

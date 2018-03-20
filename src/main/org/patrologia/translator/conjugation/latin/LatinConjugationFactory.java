package org.patrologia.translator.conjugation.latin;

import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.conjugation.ConjugationFactory;
import org.patrologia.translator.conjugation.VerbDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 15/09/2015.
 */
public class LatinConjugationFactory extends ConjugationFactory {


    private Map<String, List<String>> conjugationsDefinitionsList;
    private NounRepository nounRepository;

    public LatinConjugationFactory(List<String> conjugationDefinitions, Map<String, List<String>> conjugationsDefinitionsList, NounRepository nounRepository) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        this.nounRepository = nounRepository;
        populateConjugationMap(conjugationDefinitions);
    }

    @Override
    public String getConjugationSynonym(VerbDefinition verbDefinition) {
        return getConjugationByPattern(verbDefinition).getSynonym();
    }

    @Override
    public LatinConjugation getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullLatinConjugation();
        }
        /*
        String conjugationFile = conjugations.get(verbDefinition.getConjugationPattern().toLowerCase());
        return conjugationFile != null ? new LatinConjugation(conjugationsFilesPath + "\\" + conjugationFile, verbDefinition) : new NullLatinConjugation();
        */
        return new LatinConjugation(conjugationsDefinitionsList.get(conjugationPattern), verbDefinition, nounRepository);
    }
}

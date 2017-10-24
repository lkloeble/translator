package org.patrologia.translator.conjugation.greek;

import org.patrologia.translator.conjugation.ConjugationFactory;
import org.patrologia.translator.conjugation.VerbDefinition;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 08/10/2015.
 */
public class GreekConjugationFactory extends ConjugationFactory {

    public GreekConjugationFactory(List<String> conjugationDefinitions) {
        conjugations = new HashMap<>();
        populateConjugationMap(conjugationDefinitions);
    }

    @Override
    public GreekConjugation getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullGreekConjugation();
        }
        /*
        String conjugationFile = conjugations.get(conjugationPattern.toLowerCase());
        return conjugationFile != null ? new GreekConjugation(conjugationsFilesPath + "\\" + conjugationFile, verbDefinition) : new NullGreekConjugation();
        */
        return new NullGreekConjugation();
    }

    @Override
    public String getConjugationSynonym(VerbDefinition verbDefinition) {
        return null;
    }
}

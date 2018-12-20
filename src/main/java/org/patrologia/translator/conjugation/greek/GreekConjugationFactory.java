package patrologia.translator.conjugation.greek;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.ConjugationFactory;
import patrologia.translator.conjugation.VerbDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 08/10/2015.
 */
public class GreekConjugationFactory extends ConjugationFactory {

    private Map<String, List<String>> conjugationsDefinitionsList;
    private NounRepository nounRepository;

    public GreekConjugationFactory(List<String> conjugationDefinitions, Map<String, List<String>> conjugationsDefinitionsList, NounRepository nounRepository) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        this.nounRepository = nounRepository;
        populateConjugationMap(conjugationDefinitions);
    }

    @Override
    public GreekConjugation getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullGreekConjugation();
        }
        return new GreekConjugation(conjugationsDefinitionsList.get(conjugationPattern), verbDefinition, nounRepository);    }

    @Override
    public String getConjugationSynonym(VerbDefinition verbDefinition) {
        return null;
    }
}

package patrologia.translator.conjugation.greek;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.conjugation.ConjugationFactory;
import patrologia.translator.conjugation.VerbDefinition;
import patrologia.translator.conjugation.german.GermanConjugation2;
import patrologia.translator.conjugation.german.NullGermanConjugation2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreekConjugationFactory  extends ConjugationFactory {

    private Map<String, List<String>> conjugationsDefinitionsList;
    private NounRepository nounRepository;
    private DeclensionFactory declensionFactory;

    public GreekConjugationFactory(List<String> conjugationDefinitions, Map<String, List<String>> conjugationsDefinitionsList, DeclensionFactory declensionFactory) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        this.declensionFactory = declensionFactory;
        populateConjugationMap(conjugationDefinitions);
    }

    @Override
    public Conjugation2 getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullGreekConjugation2();
        }
        if(conjugationsDefinitionsList.get(conjugationPattern) == null) return new NullGreekConjugation2();
        return new GreekConjugation2(conjugationPattern, conjugationsDefinitionsList.get(conjugationPattern), declensionFactory);
    }

    @Override
    public String getConjugationSynonym(VerbDefinition verbDefinition) {
        //return null;
        return getConjugationByPattern(verbDefinition).getSynonym();
    }
}

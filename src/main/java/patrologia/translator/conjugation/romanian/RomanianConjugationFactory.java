package patrologia.translator.conjugation.romanian;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.conjugation.ConjugationFactory;
import patrologia.translator.conjugation.VerbDefinition;
import patrologia.translator.declension.DeclensionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RomanianConjugationFactory extends ConjugationFactory {

    private Map<String, List<String>> conjugationsDefinitionsList;
    private NounRepository nounRepository;
    private DeclensionFactory declensionFactory;

    public RomanianConjugationFactory(List<String> conjugationDefinitions, Map<String, List<String>> conjugationsDefinitionsList, DeclensionFactory declensionFactory) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        this.declensionFactory = declensionFactory;
        this.nounRepository = nounRepository;
        populateConjugationMap(conjugationDefinitions);
    }

    @Override
    public Conjugation2 getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if (conjugationPattern == null) {
            return new NullRomanianConjugation2();
        }
        return new RomanianConjugation2(conjugationPattern, conjugationsDefinitionsList.get(conjugationPattern), declensionFactory);
        //return new RomanianConjugation2(conjugationsDefinitionsList.get(conjugationPattern), verbDefinition, nounRepository);
    }

    @Override
    public String getConjugationSynonym(VerbDefinition verbDefinition) {
        return null;
    }
}

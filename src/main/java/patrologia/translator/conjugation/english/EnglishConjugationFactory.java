package patrologia.translator.conjugation.english;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.conjugation.ConjugationComparator;
import patrologia.translator.conjugation.ConjugationFactory;
import patrologia.translator.conjugation.VerbDefinition;
import patrologia.translator.declension.DeclensionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnglishConjugationFactory extends ConjugationFactory {

    private Map<String, List<String>> conjugationsDefinitionsList;
    private NounRepository nounRepository;
    private DeclensionFactory declensionFactory;

    public EnglishConjugationFactory(List<String> conjugationDefinitions, Map<String, List<String>> conjugationsDefinitionsList, DeclensionFactory declensionFactory) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        this.declensionFactory = declensionFactory;
        populateConjugationMap(conjugationDefinitions);
    }

    @Override
    public Conjugation2 getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullEnglishConjugation2();
        }
        //return new EnglishConjugation2(conjugationsDefinitionsList.get(conjugationPattern), verbDefinition, nounRepository);
        return new EnglishConjugation2(conjugationPattern, conjugationsDefinitionsList.get(conjugationPattern), declensionFactory);
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

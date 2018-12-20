package patrologia.translator.conjugation.hebrew;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation;
import patrologia.translator.conjugation.ConjugationFactory;
import patrologia.translator.conjugation.VerbDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewConjugationFactory extends ConjugationFactory {

    private Map<String, List<String>> conjugationsDefinitionsList;
    private NounRepository nounRepository;

    public HebrewConjugationFactory(List<String> conjugationLines, Map<String, List<String>> conjugationsDefinitionsList, NounRepository nounRepository) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        this.nounRepository = nounRepository;
        populateConjugationMap(conjugationLines);
    }

    @Override
    public Conjugation getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullHebrewConjugation();
        }
        //String conjugationFile = conjugations.get(conjugationPattern.toLowerCase());
        //return conjugationFile != null ? new HebrewConjugation(conjugationsFilesPath + "\\" + conjugationFile,verbDefinition) : new NullHebrewConjugation();
        return new HebrewConjugation(conjugationsDefinitionsList.get(conjugationPattern), verbDefinition, nounRepository);
    }

    @Override
    public String getConjugationSynonym(VerbDefinition verbDefinition) {
        return null;
    }

    @Override
    protected void populateConjugationMap(List<String> conjugations) {
        super.populateConjugationMap(conjugations);
    }
}

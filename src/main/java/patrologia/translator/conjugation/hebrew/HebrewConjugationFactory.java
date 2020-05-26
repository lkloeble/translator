package patrologia.translator.conjugation.hebrew;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.conjugation.ConjugationFactory;
import patrologia.translator.conjugation.VerbDefinition;
import patrologia.translator.declension.DeclensionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HebrewConjugationFactory extends ConjugationFactory {

    private Map<String, List<String>> conjugationsDefinitionsList;
    private NounRepository nounRepository;
    private DeclensionFactory declensionFactory;

    public HebrewConjugationFactory(List<String> conjugationLines, Map<String, List<String>> conjugationsDefinitionsList, DeclensionFactory declensionFactory) {
        conjugations = new HashMap<>();
        this.conjugationsDefinitionsList = conjugationsDefinitionsList;
        this.declensionFactory = declensionFactory;
        populateConjugationMap(conjugationLines);
    }

    @Override
    public Conjugation2 getConjugationByPattern(VerbDefinition verbDefinition) {
        String conjugationPattern = verbDefinition.getConjugationPattern();
        if(conjugationPattern == null) {
            return new NullHebrewConjugation2();
        }
        //String conjugationFile = conjugations.get(conjugationPattern.toLowerCase());
        //return conjugationFile != null ? new HebrewConjugation(conjugationsFilesPath + "\\" + conjugationFile,verbDefinition) : new NullHebrewConjugation();
        return new HebrewConjugation2(conjugationPattern, conjugationsDefinitionsList.get(conjugationPattern), declensionFactory);
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

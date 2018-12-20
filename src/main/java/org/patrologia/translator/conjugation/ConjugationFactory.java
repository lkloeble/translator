package patrologia.translator.conjugation;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 15/09/2015.
 */
public abstract class ConjugationFactory {

    protected String conjugationsAndFiles;
    protected Map<String, String> conjugations;

    public abstract Conjugation getConjugationByPattern(VerbDefinition verbDefinition);

    public abstract String getConjugationSynonym(VerbDefinition verbDefinition);

    protected void populateConjugationMap(List<String> conjugationDefinitions) {
        /*
        DictionaryLoader dictionaryLoader = new DictionaryLoader(conjugationsAndFiles);
        List<String> allLines = dictionaryLoader.getAllLines();
        */
        conjugationDefinitions.stream().
                filter(line -> line != null & line.length() > 0 && line.indexOf("%") > 0).
                forEach(line -> conjugations.put(getConjugationName(line), getConjugationFile(line)));
    }


    private String getConjugationFile(String line) {
        return line.split("%")[1];
    }

    private String getConjugationName(String line) {
        return line.split("%")[0].toLowerCase();
    }

    public ConjugationComparator getComparator() {
        return null;
    }
}

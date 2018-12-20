package patrologia.translator.conjugation.english;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation;
import patrologia.translator.conjugation.ConjugationLoader;
import patrologia.translator.conjugation.VerbDefinition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by lkloeble on 04/03/2016.
 */
public class EnglishConjugation extends Conjugation {

    private String conjugationFilePath;
    private ConjugationLoader conjugationLoader = new ConjugationLoader();

    protected static List<String> times = Arrays.asList(new String[]{"IPR","PAP","AIMP", "AII","PAPR","AIP","ACP","AIF"});

    public EnglishConjugation(List<String> conjugationElements, VerbDefinition verbDefinition, NounRepository nounRepository) {
        this.verbDefinition = verbDefinition;
        this.nounRepository = nounRepository;
        initializeMap(conjugationElements);
    }

    protected EnglishConjugation() {}

    private void initializeMap(List<String> conjugationELements) {
        allEndings = conjugationLoader.loadConjugation(conjugationELements);
    }

    @Override
    public List<String> getCongujationByTimePattern(String timePattern) {
        if (timePattern != null && !times.contains(timePattern)) {
            return Collections.EMPTY_LIST;
        }
        return allEndings.get(timePattern) != null ? allEndings.get(timePattern) : Collections.EMPTY_LIST;
    }
}

package patrologia.translator.conjugation.romanian;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation;
import patrologia.translator.conjugation.ConjugationLoader;
import patrologia.translator.conjugation.VerbDefinition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * CONJ => Conjuntiv prezent
 * MMCP =>  Mai Mult Ca Perfect
 * AIFINF => Infinitive Plural
 * ACPINF => CONDitionnal INFinitive
 *
 * Created by Laurent KLOEBLE on 19/10/2015.
 */
public class RomanianConjugation extends Conjugation {

    private String conjugationFilePath;
    private ConjugationLoader conjugationLoader = new ConjugationLoader();

    protected static List<String> times = Arrays.asList(new String[]{"IPR","AIMP","CONJ","MMCP","AIF","ACP","AIFINF","ACPINF"});

    public RomanianConjugation(List<String> conjugationElements, VerbDefinition verbDefinition, NounRepository nounRepository) {
        this.verbDefinition = verbDefinition;
        this.nounRepository = nounRepository;
        initializeMap(conjugationElements);
    }

    protected RomanianConjugation() {}

    private void initializeMap(List<String> conjugationElements) {
        allEndings = conjugationLoader.loadConjugation(conjugationElements);
    }

    @Override
    public List<String> getCongujationByTimePattern(String timePattern) {
        if(timePattern != null && !times.contains(timePattern)) {
            return Collections.EMPTY_LIST;
        }
        return allEndings.get(timePattern) != null ? allEndings.get(timePattern) : Collections.EMPTY_LIST;
    }
}
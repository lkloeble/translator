package patrologia.translator.conjugation.hebrew;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation;
import patrologia.translator.conjugation.ConjugationLoader;
import patrologia.translator.conjugation.VerbDefinition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ARAIPR => ARAmaic Indicative PResent
 * PALINF => PA'aL INFinitive
 * PALFUT => PA'aL FUTure
 * HIFPER => HIf'il PERfect
 * HIFIPR => HIF'il iPResent
 * BINPER => BINyan PERfect
 * BINPRE => BINyan PREsent
 * HITAIP => HITpael AIP (imperfect)
 * CONVFUT => CONVersive FUTure
 * SUBST => SUBSTantive
 * BINHUPER => Binyan hufal Perfect
 * BINHUFUT => Binyan hufal Futur
 * BINHIFUT => Binyan hifil futur
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewConjugation extends Conjugation {

    private String conjugationFilePath;
    private ConjugationLoader conjugationLoader = new ConjugationLoader();

    protected static List<String> times = Arrays.asList(new String[]{"AIP","AIF","PSP","ARAIPR","PALINF","PALFUT","HIFPER","HIFIPR","AIMP","HITAIP","CONVFUT","SUBST","BINHUFUT","BINHUPER","BINHIFUT"});

    public HebrewConjugation(List<String> conjugationElements, VerbDefinition verbDefinition, NounRepository nounRepository) {
        this.verbDefinition = verbDefinition;
        this.nounRepository = nounRepository;
        initializeMap(conjugationElements);
    }

    protected HebrewConjugation() {}

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
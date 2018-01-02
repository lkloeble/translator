package org.patrologia.translator.conjugation.latin;

import org.patrologia.translator.conjugation.Conjugation;
import org.patrologia.translator.conjugation.ConjugationLoader;
import org.patrologia.translator.conjugation.VerbDefinition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 *
 */
public class LatinConjugation extends Conjugation {

    protected static List<String> times = Arrays.asList(new String[]{"IPR", "SPA", "SPR", "AIP", "PSP", "PII", "AIF", "PIP", "ASI", "ASP", "IMP", "PIF", "AII", "AIFP","PASUPR","AIMP","AIPP","IAPP","PAPR","IAP","PSI","VENO"});
    private String conjugationFilePath;
    private ConjugationLoader conjugationLoader = new ConjugationLoader();

    public LatinConjugation(List<String> conjugationsElements, VerbDefinition verbDefinition) {
        this.verbDefinition = verbDefinition;
        initializeMap(conjugationsElements);
    }

    protected LatinConjugation() {}

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

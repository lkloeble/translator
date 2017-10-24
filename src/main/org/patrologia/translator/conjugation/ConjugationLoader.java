package org.patrologia.translator.conjugation;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 */
public class ConjugationLoader {

    public Map<String, List<String>> loadConjugation(List<String> conjugationElements) {
        Map<String, List<String>> conjugations = new HashMap<>();
        if(conjugationElements == null) conjugationElements = Collections.EMPTY_LIST;
        conjugationElements.stream().forEach(line -> conjugations.put(getConjugationName(line), correctSpaces(getConjugation(line))));
        return conjugations;
    }

    private List<String> correctSpaces(List<String> conjugation) {
        List<String> withoutAnySpace = new ArrayList<>(conjugation.size());
        for(String conjugationEnd : conjugation) {
            withoutAnySpace.add(conjugationEnd.trim());
        }
        return withoutAnySpace;
    }

    private List<String> getConjugation(String line) {
        return Arrays.asList(line.split("=>")[1].split(","));
    }

    private String getConjugationName(String line) {
        return line.split("=>")[0] ;
    }

}

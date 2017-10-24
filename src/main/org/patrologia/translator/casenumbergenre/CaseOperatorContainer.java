package org.patrologia.translator.casenumbergenre;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.declension.Declension;

import java.util.*;

/**
 * Created by lkloeble on 26/04/2017.
 */
public class CaseOperatorContainer {

    private List<CaseOperator> caseOperatorList = new ArrayList<>();
    private NounRepository nounRepository;
    private List<ResultCaseOperator> resultCaseOperatorList = new ArrayList<>();

    public CaseOperatorContainer(NounRepository nounRepository) {
        this.nounRepository = nounRepository;
    }

    public void addCaseOperator(CaseOperator caseOperator) {
        caseOperatorList.add(caseOperator);
    }

    public boolean isCompliantToOneOfCaseOperator(Word word) {
        return validateNounAndCaseOperator((Noun)word);
    }

    public boolean isCompliantToOneOfCaseOperator(WordContainer wordContainer) {
        String initialValue = wordContainer.getInitialValue();
        Collection<Noun> nounCollection = nounRepository.getNoun(initialValue);
        if(nounCollection == null || nounCollection.isEmpty()) nounCollection = decorateInitialValueWithHebrewStartPrepositionsCombination(initialValue);
        if(nounCollection == null || nounCollection.isEmpty()) nounCollection = decorateInitialValueWithHebrewStartPepositions(initialValue);
        if(nounCollection == null || nounCollection.isEmpty()) return false;
        Noun noun = new Noun((Noun)nounCollection.toArray()[0]);
        noun = affectCorrectCaseNumberGenre(noun, wordContainer);
        noun.updateInitialValue(initialValue);
        return validateNounAndCaseOperator(noun);
    }

    private boolean validateNounAndCaseOperator(Noun noun) {
        if(getCaseOperatorList().isEmpty()) return true;
        for(CaseOperator caseOperator : getCaseOperatorList()) {
            if(caseOperator.isCompliant(noun)) {
                addResultCaseOperator(noun, caseOperator);
                return true;
            }
        }
        return false;
    }

    private Noun affectCorrectCaseNumberGenre(Noun noun, WordContainer wordContainer) {
        String declensionPattern = noun.getDeclension();
        Declension declensionByPattern = nounRepository.getDeclensionFactory().getDeclensionByPattern(declensionPattern);
        for(CaseNumberGenre cng : declensionByPattern.getAllEndings().keySet()) {
            String suffix = declensionByPattern.getAllEndings().get(cng);
            String potentialInitialValue = noun.getRoot() + suffix;
            if((noun.getInitialValue()).equals(potentialInitialValue)) {
                noun.addPossibleCaseNumber(cng);
            }
        }
        wordContainer.clearAll();
        wordContainer.putOtherPossibleWord(noun);
        return noun;
    }

    private Collection<Noun> decorateInitialValueWithHebrewStartPrepositionsCombination(String initialValue) {
        Set<String> startPrepositions = new HashSet<>();
        startPrepositions.add("wb");
        startPrepositions.add("wk");
        startPrepositions.add("wl");
        for(String preposition : startPrepositions) {
            if(initialValue.startsWith(preposition)) {
                String tempInitialValue = initialValue.substring(2);
                Collection<Noun> noun = nounRepository.getNoun(tempInitialValue);
                return noun;
            }
        }
        return null;
    }

    private Collection<Noun> decorateInitialValueWithHebrewStartPepositions(String initialValue) {
        Set<String> startPrepositions = new HashSet<>();
        startPrepositions.add("b");
        startPrepositions.add("m");
        startPrepositions.add("k");
        startPrepositions.add("l");
        startPrepositions.add("w");
        for(String preposition : startPrepositions) {
            if(initialValue.startsWith(preposition)) {
                String tempInitialValue = initialValue.substring(1);
                Collection<Noun> noun = nounRepository.getNoun(tempInitialValue);
                return noun;
            }
        }
        return null;
    }

    private Collection<Noun> addPrepositionToInitialValue(Collection<Noun> nouns, String prepositionValue) {
        List<Noun> newNounList = new ArrayList<>();
        for(Noun noun : nouns) {
            Noun newNoun = new Noun(noun);
            newNoun.updateInitialValue(prepositionValue + noun.getInitialValue());
            newNounList.add(newNoun);
        }
        return newNounList;
    }

    private void addResultCaseOperator(Noun noun, CaseOperator caseOperator) {
        ResultCaseOperator resultCaseOperator = new ResultCaseOperator(noun, caseOperator);
        resultCaseOperatorList.add(resultCaseOperator);
    }

    public void emptyCases() {
        caseOperatorList.clear();
    }

    public List<ResultCaseOperator> getResultCaseOperatorList() {
        return resultCaseOperatorList;
    }

    public Word substituteWord(String initialValue, String pattern, Word foundAlready) {
        String valueAndPattern = initialValue + pattern;
        ResultCaseOperator resultCaseOperatorConcerned = selectResult(valueAndPattern);
        if(resultCaseOperatorConcerned == null) return foundAlready;
        String prefix = valueAndPattern.replace(resultCaseOperatorConcerned.getNounValue(),"");
        String differentierOfSubstitution = resultCaseOperatorConcerned.getDifferentierOfSubstitution();
        Collection<Noun> nouns = nounRepository.getNoun(resultCaseOperatorConcerned.getNounValue());
        if(nouns == null || nouns.isEmpty()) nouns = decorateInitialValueWithHebrewStartPrepositionsCombination(resultCaseOperatorConcerned.getNounValue());
        if(nouns == null || nouns.isEmpty()) nouns = decorateInitialValueWithHebrewStartPepositions(resultCaseOperatorConcerned.getNounValue());
        if(nouns == null || nouns.isEmpty()) return foundAlready;
        Noun noun = new Noun((Noun) nouns.toArray()[0]);
        String declensionPattern = noun.getDeclension();
        Declension declensionByPattern = nounRepository.getDeclensionFactory().getDeclensionByPattern(declensionPattern);
        for(CaseNumberGenre cng : declensionByPattern.getAllEndings().keySet()) {
            String trigramForCase = cng.getCase().getTrigramForCase();
            String differentier = cng.getCase().getDifferentier();
            String target = trigramForCase + differentier;
            if(target.equals(differentierOfSubstitution)) {
                String suffix = declensionByPattern.getAllEndings().get(cng);
                prefix = computePrefixForStartingPreposition(prefix,initialValue,noun.getRoot(), declensionByPattern);
                String root = noun.getRoot();
                if(declensionByPattern.isCustom()) {
                    root = suffix;
                    suffix = "";
                }
                return new Noun(Language.HEBREW, prefix + root + suffix, root, Collections.singletonList(cng), cng.getGender(), cng.getGender().name(), declensionPattern, Collections.EMPTY_LIST);
            }
        }
        for(CaseNumberGenre cng : declensionByPattern.getAllEndings().keySet()) {
            String trigramForCase = cng.getCase().getTrigramForCase();
            String differentier = cng.getCase().getDifferentier();
            String target = trigramForCase + differentier;
            if(target.equals(trigramForCase + differentierOfSubstitution)) {
                String goodSuffixEnding = declensionByPattern.getSuffixForDifferentier("nomsg");
                return new Noun(Language.HEBREW, prefix + noun.getRoot() + goodSuffixEnding, noun.getRoot(), Collections.singletonList(cng), cng.getGender(), cng.getGender().name(), declensionPattern, Collections.EMPTY_LIST);
            }
        }
        return new Noun(Language.HEBREW, noun.getRoot(), noun.getRoot(), Collections.EMPTY_LIST, Gender.getGenderByCode("NEUT"),"NEUT",noun.getDeclension(), Collections.EMPTY_LIST);
    }

    private String computePrefixForStartingPreposition(String prefix, String initialValue, String root, Declension declension) {
        if(prefix.length() > 0) return prefix;
        if(initialValue.startsWith(root)) return prefix;
        if(declension.isCustom()) {
            if(initialValue.startsWith("k") && !root.startsWith("k")) return "k";
            if(initialValue.startsWith("w") && !root.startsWith("w")) return "w";
        }
        int cutPoint = initialValue.indexOf(root);
        if(cutPoint < 1) return prefix;
        return initialValue.substring(0,cutPoint);
    }

    private ResultCaseOperator selectResult(String pattern) {
        for(ResultCaseOperator resultCaseOperator : resultCaseOperatorList) {
            if(pattern.equals(resultCaseOperator.getNounValue())) return resultCaseOperator;
        }
        for(ResultCaseOperator resultCaseOperator : resultCaseOperatorList) {
            if(pattern.endsWith(resultCaseOperator.getNounValue())) return resultCaseOperator;
        }
        return null;
    }

    public List<CaseOperator> getCaseOperatorList() {
        Collections.sort(caseOperatorList);
        return caseOperatorList;
    }
}

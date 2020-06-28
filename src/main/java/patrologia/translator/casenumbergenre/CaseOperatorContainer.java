package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.declension.Declension;

import java.util.*;

public class CaseOperatorContainer {

    private List<CaseOperator> caseOperatorList = new ArrayList<>();
    private NounRepository nounRepository;
    private PrepositionRepository prepositionRepository;
    private List<ResultCaseOperator> resultCaseOperatorList = new ArrayList<>();
    private List<String> combinationsOfTwoKinds;
    private Map<Integer, String> matchingLeadingPrepositionMap = new HashMap<>();

    public CaseOperatorContainer(NounRepository nounRepository, PrepositionRepository prepositionRepository) {
        this.nounRepository = nounRepository;
        this.prepositionRepository = prepositionRepository;
        this.combinationsOfTwoKinds = computePrepositionCombinations();
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
        if(nounCollection == null || nounCollection.isEmpty()) nounCollection = decorateInitialValueWithHebrewStartPrepositionsCombination(wordContainer.getPosition(), initialValue);
        if(nounCollection == null || nounCollection.isEmpty()) nounCollection = decorateInitialValueWithHebrewStartPepositions(wordContainer.getPosition(), initialValue);
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


    private List<String> computePrepositionCombinations() {
        List<String> prepositionsStartingWithWav = filterPrepositionsByAccentuation(prepositionRepository.getValuesStartingWith("w"));
        List<String> prepositionsStartingWithBeth = filterPrepositionsByAccentuation(prepositionRepository.getValuesStartingWith("b"));
        List<String> prepositionsStartingWithKaf = filterPrepositionsByAccentuation(prepositionRepository.getValuesStartingWith("k"));
        List<String> prepositionsStartingWithLamed = filterPrepositionsByAccentuation(prepositionRepository.getValuesStartingWith("l"));
        List<String> combinationsWithWavToPerform = new ArrayList<>();
        Collections.sort(prepositionsStartingWithWav);
        Collections.reverse(prepositionsStartingWithWav);
        Collections.sort(prepositionsStartingWithBeth);
        Collections.reverse(prepositionsStartingWithBeth);
        Collections.sort(prepositionsStartingWithKaf);
        Collections.reverse(prepositionsStartingWithKaf);
        combinationsWithWavToPerform.addAll(prepositionsStartingWithBeth);
        combinationsWithWavToPerform.addAll(prepositionsStartingWithKaf);
        combinationsWithWavToPerform.addAll(prepositionsStartingWithLamed);
        return performCardinalJoin(prepositionsStartingWithWav, combinationsWithWavToPerform);
    }

    private List<String> filterPrepositionsByAccentuation(List<String> prepositionsToFilter) {
        Set<String> filteredPropositions = new HashSet<>();
        for(String preprosition : prepositionsToFilter) {
            if(isBasicUnaccentuedPreposition(preprosition) || isAccentuedWithoutSofit(preprosition)) {
                filteredPropositions.add(preprosition);
            }
        }
        return new ArrayList<>(filteredPropositions);
    }

    private boolean isBasicUnaccentuedPreposition(String preprosition) {
        return preprosition.length() == 1;
    }

    private boolean isAccentuedWithoutSofit(String preprosition) {
        return !prepositionRepository.unaccentued(preprosition).equals(preprosition) && !preprosition.contains(prepositionRepository.SOFIT_END);
    }

    private List<String> performCardinalJoin(List<String> prepositionsStartingWithWav, List<String> combinationsWithWavToPerform) {
        List<String> joins = new ArrayList<>();
        for(String withWav : prepositionsStartingWithWav) {
            for(String toCombine : combinationsWithWavToPerform) {
                joins.add(withWav + toCombine);
            }
        }
        return joins;
    }

    private Collection<Noun> decorateInitialValueWithHebrewStartPrepositionsCombination(int position, String initialValue) {
        for(String preposition : combinationsOfTwoKinds) {
            if(initialValue.startsWith(preposition)) {
                matchingLeadingPrepositionMap.put(position,preposition);
                String tempInitialValue = initialValue.substring(preposition.length());
                Collection<Noun> noun = nounRepository.getNoun(tempInitialValue);
                return noun;
            }
        }
        return null;
    }

    private Collection<Noun> decorateInitialValueWithHebrewStartPepositions(int position, String initialValue) {
        Set<String> startPrepositions = new HashSet<>();
        startPrepositions.add("b");
        startPrepositions.add("m");
        startPrepositions.add("k");
        startPrepositions.add("l");
        startPrepositions.add("w");
        for(String preposition : startPrepositions) {
            if(initialValue.startsWith(preposition)) {
                String accentuedPreposition = getAccentuedPreposition(preposition, initialValue);
                matchingLeadingPrepositionMap.put(position, accentuedPreposition);
                String tempInitialValue = initialValue.substring(accentuedPreposition.length());
                Collection<Noun> noun = nounRepository.getNoun(tempInitialValue);
                return noun;
            }
        }
        return null;
    }

    private String getAccentuedPreposition(String preposition, String initialValue) {
        StringBuilder prepositionWithAccentuation = new StringBuilder();
        prepositionWithAccentuation.append(preposition);
        char[] letters = initialValue.toCharArray();
        for(int i=1;i<letters.length;i++) {
            if(!isANumber(letters[i])) {
                break;
            }
            prepositionWithAccentuation.append(letters[i]);
        }
        return prepositionWithAccentuation.toString();
    }

    private boolean isANumber(char letter) {
        int letterInt = Character.getNumericValue(letter);
        return (letterInt >= 0) && (letterInt <= 9);
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

    public Word substituteWord(String initialValue, String pattern, Word foundAlready, int position) {
        String valueAndPattern = initialValue + pattern;
        ResultCaseOperator resultCaseOperatorConcerned = selectResult(valueAndPattern);
        if(resultCaseOperatorConcerned == null) return foundAlready;
        String prefix = valueAndPattern.replace(resultCaseOperatorConcerned.getNounValue(),"");
        String differentierOfSubstitution = resultCaseOperatorConcerned.getDifferentierOfSubstitution();
        Collection<Noun> nouns = nounRepository.getNoun(resultCaseOperatorConcerned.getNounValue());
        if(nouns == null || nouns.isEmpty()) nouns = decorateInitialValueWithHebrewStartPrepositionsCombination(position, resultCaseOperatorConcerned.getNounValue());
        if(nouns == null || nouns.isEmpty()) nouns = decorateInitialValueWithHebrewStartPepositions(position, resultCaseOperatorConcerned.getNounValue());
        if(nouns == null || nouns.isEmpty()) return foundAlready;
        Noun noun = new Noun((Noun) nouns.toArray()[0]);
        String declensionPattern = noun.getDeclension();
        Declension declensionByPattern = nounRepository.getDeclensionFactory().getDeclensionByPattern(declensionPattern);
        for(CaseNumberGenre cng : declensionByPattern.getAllEndings().keySet()) {
            String trigramForCase = cng.getCase().getTrigramForCase();
            String differentier = cng.getCase().getDifferentier();
            String target = trigramForCase + differentier;
            if(target.equals(differentierOfSubstitution) || specialCaseOfMultipleDifferentiers(target,differentierOfSubstitution)) {
                String suffix = declensionByPattern.getAllEndings().get(cng);
                prefix = computePrefixForStartingPreposition(position, prefix,initialValue,noun.getRoot(), declensionByPattern);
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

    private boolean specialCaseOfMultipleDifferentiers(String target, String differentier) {
        if(!differentier.startsWith(target)) return false;
        String multipleDifferentierNumber = differentier.replace(target,"");
        return isNumberBelowFive(multipleDifferentierNumber);
    }

    private boolean isNumberBelowFive(String multipleDifferentierNumber) {
        if(multipleDifferentierNumber.length() != 1) return false;
        int value = 0;
        try {
            value = Integer.parseInt(multipleDifferentierNumber);
            return value > 0 && value < 5;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private String computePrefixForStartingPreposition(int position, String prefix, String initialValue, String root, Declension declension) {
        if(prefix.length() > 0) return prefix;
        if(initialValue.startsWith(root)) return prefix;
        if(declension.isCustom()) {
            if(initialValue.startsWith("k") && !root.startsWith("k")) return "k";
            if(initialValue.startsWith("w") && !root.startsWith("w")) return "w";
        }
        int cutPoint = initialValue.indexOf(root);
        if(cutPoint < 1 && prefix.length() > 0) return prefix;
        if(cutPoint < 1 && prefix.length() == 0) {
            cutPoint = computeCutPointForAccentuateComposedPrepositions(position, initialValue);
        }
        return initialValue.substring(0,cutPoint);
    }

    private int computeCutPointForAccentuateComposedPrepositions(int position, String initialValue) {
        String matchingLeadingPreposition = matchingLeadingPrepositionMap.get(position);
        if(matchingLeadingPreposition == null) return 0;
        String work = initialValue.replace(matchingLeadingPreposition,"");
        String unaccentuedWork = prepositionRepository.unaccentued(work);
        return work.indexOf(unaccentuedWork.charAt(0)) + matchingLeadingPreposition.length();
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

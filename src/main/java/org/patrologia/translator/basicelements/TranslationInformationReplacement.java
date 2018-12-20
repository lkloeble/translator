package patrologia.translator.basicelements;

import patrologia.translator.conjugation.ConjugationPart;
import patrologia.translator.conjugation.RootedConjugation;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 19/10/2015.
 */
public class TranslationInformationReplacement {

    private Map<Integer, Set<Integer>> indicesOfOnlyTimeExceptionMap = new HashMap();
    private Map<Integer, String> timeExceptionMap = new HashMap<>();
    private Map<Integer, String> toReplaceMap = new HashMap<>();
    private Map<Integer, String> replacementMap = new HashMap<>();
    private Map<Integer, Integer> positionMap = new HashMap<>();
    private Map<Integer, String> nounParticipleDeclensionMap = new HashMap<>();
    protected static final String NULL_REPLACEMENT_STRING = "foobar";
    protected static Set<Character> numbers = new HashSet<Character>();
    static {
        numbers.add('0');
        numbers.add('1');
        numbers.add('2');
        numbers.add('3');
        numbers.add('4');
        numbers.add('5');
        numbers.add('6');
    }

    public TranslationInformationReplacement(String replacementDefinition) {
        if(NULL_REPLACEMENT_STRING.equals(replacementDefinition))  return;
        String[] replacementsDefinition = replacementDefinition.split("@");
        int indice = 0;
        for(String singleDefinition : replacementsDefinition) {
            String[] elements = singleDefinition.split("\\*");
            String timeException = cleanIndicesAndTime(indice, elements[0]);
            timeExceptionMap.put(indice, timeException);
            String toReplace = elements[1];
            toReplaceMap.put(indice, toReplace);
            String replacement = elements[2];
            replacementMap.put(indice, replacement);
            int position = Integer.parseInt(elements[3]);
            positionMap.put(indice, position);
            if(elements.length == 5) {
                String nounParticipleDeclension = elements[4].replace("[", "").replace("]", "");
                nounParticipleDeclensionMap.put(indice, nounParticipleDeclension);
            }
            indice++;
        }
    }

    private String cleanIndicesAndTime(int indice, String timeException) {
        if(timeException == null) return null;
        if(hasNoNumber(timeException)) return timeException;
        String indices = timeException.substring(getFirstNumberPosition(timeException));
        String[] indicesArray = indices.split("");
        Set<Integer> positions = new HashSet<>();
        for(String indiceInArray : indicesArray) {
            if(indiceInArray.length() == 0) indiceInArray = "0";
            positions.add(Integer.parseInt(indiceInArray));
        }
        indicesOfOnlyTimeExceptionMap.put(indice, positions);
        String correctTimeException = timeException.substring(0,getFirstNumberPosition(timeException));
        return correctTimeException;
    }

    private int getFirstNumberPosition(String timeException) {
        char[] chars = timeException.toCharArray();
        int indice = 0;
        for(char c : chars) {
            if(numbers.contains(c)) return indice;
            indice++;
        }
        return 0;
    }

    private boolean hasNoNumber(String timeException) {
        char[] chars = timeException.toCharArray();
        for(char c : chars) {
            if(numbers.contains(c)) return false;
        }
        return true;
    }

    public String getTimeException(int indice) {
        return timeExceptionMap.get(indice);
    }

    public String getToReplace(int indice) {
        return toReplaceMap.get(indice);
    }

    public String getReplacement(int indice, int position) {
        Set<Integer> indicesOfOnlyTimeException = indicesOfOnlyTimeExceptionMap.get(indice);
        if(indicesOfOnlyTimeException.isEmpty()) return replacementMap.get(indice);
        if(indicesOfOnlyTimeException.contains(position)) return replacementMap.get(indice);
        return toReplaceMap.get(indice);
    }

    public int getPosition(int indice) {
        return positionMap.get(indice);
    }

    public boolean hasThisName(String name) {
        for(String timeException : timeExceptionMap.values()) {
            if(timeException.equals(name)) return true;
            if(timeException.startsWith(name)) return true;
        }
        return false;
    }

    public int getPossibleOperations() {
        return replacementMap.size();
    }

    //TODO : too much complexity
    public RootedConjugation performReplacement(String name, RootedConjugation rootedConjugation) {
        for(int possibleOperation = 0;possibleOperation<replacementMap.size();possibleOperation++) {
            Set<Integer> authorizedPositionsForReplacements = indicesOfOnlyTimeExceptionMap.get(possibleOperation);
            if(authorizedPositionsForReplacements == null) authorizedPositionsForReplacements = new HashSet<>();
            if(timeExceptionMap.get(possibleOperation).equals(name)) {
                for (ConjugationPart conjugationPart : rootedConjugation.getPartLists()) {
                    if(authorizedPositionsForReplacements.size() == 0 || authorizedPositionsForReplacements.contains(conjugationPart.getIndice())) {
                        String target = toReplaceMap.get(possibleOperation);
                        String replacement = replacementMap.get(possibleOperation);
                        conjugationPart.updateValue(target, replacement);
                    }
                }
            }
        }
        return rootedConjugation;
    }

    public String getAlternateRootForThisTime(String time, String baseConjugationRoot) {
        int indiceForThisTime = -1;
        for(Integer indice : timeExceptionMap.keySet()) {
            String timeException = timeExceptionMap.get(indice);
            if(time.equals(timeException)) {
                indiceForThisTime = indice;
                break;
            }
        }
        if(indiceForThisTime == -1) return baseConjugationRoot;
        if(indicesOfOnlyTimeExceptionMap.containsKey(indiceForThisTime)) return baseConjugationRoot;
        return replacementMap.get(indiceForThisTime);
    }
}

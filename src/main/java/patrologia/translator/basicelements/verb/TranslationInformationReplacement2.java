package patrologia.translator.basicelements.verb;

import patrologia.translator.conjugation.ConjugationPart;
import patrologia.translator.conjugation.ConjugationPosition;

import java.util.*;

public class TranslationInformationReplacement2 {

    private String description;
    private Map<String,String> patternMap = new HashMap<>();
    private Map<String,String> replaceMap = new HashMap<>();

    public TranslationInformationReplacement2(String description) {
        this.description = description;
        processAllDescriptions(description);
    }

    private void processAllDescriptions(String allDescriptions) {
        String[] allDescriptionsArray = allDescriptions.split("@");
        int indice = 0;
        for(String singleDescription : allDescriptionsArray) {
            processDescription(singleDescription, indice++);
        }
    }

    private void processDescription(String description, int indice) {
        if(description == null || description.length() == 0) return;
        String[] infos = description.split("\\*");
        String timeAndPosition = infos[0];
        String patternToFind =infos[1];
        String replacementForPattern =infos[2];
        List<String> allPositionsPossibleForTheTime = getAllPositions(timeAndPosition);
        for(String positionAndTime : allPositionsPossibleForTheTime) {
            patternMap.put(positionAndTime,patternToFind);
            replaceMap.put(positionAndTime,replacementForPattern);
        }
    }

    public String replace(String time, String patternToUpdate, ConjugationPosition position) {
        if(!hasReplacementForTime(time)) {
            return patternToUpdate;
        }
        if(hasReplacementForPosition(time,position)) {
            String keyForTimeAndPosition = time + position.getIndice();
            String patternToSearch = patternMap.get(keyForTimeAndPosition);
            String replacementToProceed =replaceMap.get(keyForTimeAndPosition);
            return patternToUpdate.replace(patternToSearch,replacementToProceed);
        }
        return patternToUpdate;
    }

    private boolean hasReplacementForPosition(String time, ConjugationPosition position) {
        return patternMap.containsKey(time + position.getIndice()) && replaceMap.containsKey(time + position.getIndice());
    }

    public boolean hasReplacementForTime(String time) {
        Set<String> allPossibleTimesWithPositions = patternMap.keySet();
        for(String possibleTimeWithPosition : allPossibleTimesWithPositions) {
            if(possibleTimeWithPosition.startsWith(time)) return true;
        }
        return false;
    }


    private List<String> getAllPositions(String time) {
        List<String> allPositions = new ArrayList<>();
        String timeOnly = cleanTimeFromPosition(time);
        List<Integer> positionsOnly = extractPositionsOnly(time);
        for(Integer position : positionsOnly) {
            allPositions.add(timeOnly + position);
        }
        return allPositions;
    }

    private List<Integer> extractPositionsOnly(String time) {
        char[] chars = time.toCharArray();
        List<Integer> positionList = new ArrayList<>();
        for(char c : chars) {
            if(c >= '0' && c <= '9') positionList.add(Character.getNumericValue(c));
        }
        if(thereIsNoPositionForThisTime(positionList)) {
            return getAllPositionsByDefault();
        }
        return positionList;
    }

    private List<Integer> getAllPositionsByDefault() {
        return Arrays.asList(0,1,2,3,4,5);
    }

    private boolean thereIsNoPositionForThisTime(List<Integer> positionList) {
        return positionList.size() == 0;
    }

    private String cleanTimeFromPosition(String time) {
        StringBuilder sb = new StringBuilder();
        char[] chars = time.toCharArray();
        for(char c : chars) {
            if(c >= '0' && c <= '9') continue;
            sb.append(c);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "TranslationInformationReplacement2{" +
                "description='" + description + '\'' +
                '}';
    }

    public String replace(String time,ConjugationPart conjugationPart) {
        return replace(time,conjugationPart.getValue(),conjugationPart.getConjugationPosition());
    }
}

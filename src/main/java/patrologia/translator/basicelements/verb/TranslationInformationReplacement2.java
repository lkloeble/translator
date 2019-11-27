package patrologia.translator.basicelements.verb;

import patrologia.translator.conjugation.ConjugationPart;
import patrologia.translator.conjugation.ConjugationPosition;

import java.util.*;

public class TranslationInformationReplacement2 {

    private String description;
    private Map<String,String> patternMap = new HashMap<>();
    private Map<String,String> replaceMap = new HashMap<>();

    private static final String RELATED_TO_NOUN_POSITION = "0";

    public TranslationInformationReplacement2(String description) {
        this.description = description;
        processAllDescriptions(description);
    }

    private void processAllDescriptions(String allDescriptions) {
        String[] allDescriptionsArray = allDescriptions.split("@");
        for(String singleDescription : allDescriptionsArray) {
            processDescription(singleDescription);
        }
    }

    private void processDescription(String description) {
        if(description == null || description.length() == 0) return;
        String[] infos = description.split("\\*");
        String timeAndPosition = infos[0];
        String patternToFind =infos[1];
        String replacementForPattern =infos[2];
        int indice = getPreviousExistingIndiceForTimeAndPosition(timeAndPosition);
        List<String> allPositionsPossibleForTheTime = getAllPositions(timeAndPosition,indice);
        for(String positionAndTime : allPositionsPossibleForTheTime) {
            patternMap.put(positionAndTime,patternToFind);
            replaceMap.put(positionAndTime,replacementForPattern);
        }
    }

    private int getPreviousExistingIndiceForTimeAndPosition(String timeAndPosition) {
        int indice = 0;
        while(patternMapIsEmptyForEveryPositions(indice,timeAndPosition)) {
            indice++;
        }
        return indice;
    }

    private boolean patternMapIsEmptyForEveryPositions(int indice,String timeAndPosition) {
        for(int position = 0;position<6;position++) {
            if(patternMap.containsKey(indice+timeAndPosition+position)) return true;
        }
        return false;
    }

    public String replace(String time, String patternToUpdate, ConjugationPosition position) {
        if(!hasReplacementForTime(time)) {
            return patternToUpdate.trim();
        }
        int indiceOfReplacementUpdate = 0;
        String updated = null;
        while(hasReplacementForPosition(time,position,indiceOfReplacementUpdate)) {
            if(updated == null) updated = patternToUpdate;
            String keyForTimeAndPosition = getKeyByTimeAndPosition(time,position);
            String patternToSearch = patternMap.get(indiceOfReplacementUpdate+keyForTimeAndPosition);
            String replacementToProceed =replaceMap.get(indiceOfReplacementUpdate+keyForTimeAndPosition);
            updated =  updated.replace(patternToSearch,replacementToProceed).trim();
            indiceOfReplacementUpdate++;
        }
        if(updated != null) {
            return updated;
        }
        return patternToUpdate.trim();
    }

    private String getKeyByTimeAndPosition(String time, ConjugationPosition position) {
        if(!position.isRelatedToNoun()) {
            return time + position.getIndice();
        }
        return time + RELATED_TO_NOUN_POSITION;
    }

    private boolean hasReplacementForPosition(String time, ConjugationPosition position, int indice) {
        String key = getKeyByTimeAndPosition(time,position);
        return patternMap.containsKey(indice+key) && replaceMap.containsKey(indice+key);
    }

    public boolean hasReplacementForTime(String time) {
        Set<String> allPossibleTimesWithPositions = cleanOccurenceOfTime(patternMap.keySet());
        for(String possibleTimeWithPosition : allPossibleTimesWithPositions) {
            if(possibleTimeWithPosition.startsWith(time)) return true;
        }
        return false;
    }

    private Set<String> cleanOccurenceOfTime(Set<String> keySet) {
        Set<String> cleanSet = new HashSet<>();
        for(String key : keySet) {
            cleanSet.add(key.substring(1));
        }
        return cleanSet;
    }


    private List<String> getAllPositions(String time,int indice) {
        List<String> allPositions = new ArrayList<>();
        String timeOnly = cleanTimeFromPosition(time);
        List<Integer> positionsOnly = extractPositionsOnly(time);
        for(Integer position : positionsOnly) {
            allPositions.add(indice + timeOnly + position);
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

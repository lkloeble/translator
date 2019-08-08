package patrologia.translator.basicelements.verb;

import patrologia.translator.conjugation.ConjugationPosition;

import java.util.*;

public class TranslationInformationReplacement2 {

    private String description;
    private Map<Integer,String> timeMap = new HashMap<>();
    private Map<String, List<Integer>> timePositions = new HashMap<>();
    private Map<Integer,String> patternMap = new HashMap<>();
    private Map<Integer,String> replaceMap = new HashMap<>();

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
        String time = infos[0];
        boolean timePosition = isTimePosition(time);
        if(timePosition) {
            timePositions.put(cleanTimeFromPosition(time),getPositions(time));
        }
        timeMap.put(indice,cleanTimeFromPosition(time));
        String patternToSearch = infos[1];
        patternMap.put(indice,patternToSearch);
        String replacement = infos[2];
        replaceMap.put(indice,replacement);

    }

    private List<Integer> getPositions(String time) {
        List<Integer> positions = new ArrayList<>();
        char[] chars = time.toCharArray();
        for(char c : chars) {
            if(c >= '0' && c <= '9') {
                positions.add(Character.getNumericValue(c));
            }
        }
        return positions;
    }

    private boolean isTimePosition(String time) {
        char[] chars = time.toCharArray();
        for(char c : chars) {
            if(c >= '0' && c <= '9') return true;
        }
        return false;
    }

    public String replace(String time, String patternToUpdate, ConjugationPosition position) {
        if(!hasReplacementForTime(time)) {
            return patternToUpdate;
        }
        int indiceOfReplacement = getIndiceReplacementInMaps(time);
        if(hasReplacementForPosition(time,position)) {
            String patternToSearch = patternMap.get(indiceOfReplacement);
            String replacementToProceed =replaceMap.get(indiceOfReplacement);
            return patternToUpdate.replace(patternToSearch,replacementToProceed);
        } else if (!hasReplacementForPosition(time,position) && timeContainsPositionsToHandle(time)) {
            return patternToUpdate;
        }
        String patternToSearch = patternMap.get(indiceOfReplacement);
        String replacementToProceed =replaceMap.get(indiceOfReplacement);
        return patternToUpdate.replace(patternToSearch,replacementToProceed);
    }

    private boolean timeContainsPositionsToHandle(String time) {
        return timePositions.containsKey(time);
    }

    private boolean hasReplacementForPosition(String time, ConjugationPosition position) {
        if(!timePositions.containsKey(time)) return false;
        List<Integer> authorizedPositions = timePositions.get(time);
        return authorizedPositions.contains(position.getIndice());
    }

    private int getIndiceReplacementInMaps(String time) {
        int indice = 0;
        Set<Map.Entry<Integer, String>> entries = timeMap.entrySet();
        for(Map.Entry entry : entries) {
            String timeValue = (String)entry.getValue();
            if(timeValue.equals(time)) return (Integer)entry.getKey();
        }
        return indice;
    }

    public boolean hasReplacementForTime(String time) {
        return timeMap.values().contains(time);
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
}

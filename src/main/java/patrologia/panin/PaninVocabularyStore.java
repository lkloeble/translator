package patrologia.panin;

import java.util.*;

public class PaninVocabularyStore {

    private Map<String,String> store = new HashMap<String,String>();
    private PaninGreekRepository greekRepository;

    public PaninVocabularyStore(PaninGreekRepository greekRepository) {
        this.greekRepository = greekRepository;
    }

    public void addVocabulary(String word, String root) {
        store.put(word.toLowerCase(),root.toLowerCase());
    }

    public int checkNumberOfWordsInVocabulary(String text) {
        Set<String> vocabularyFound = getVocabularyFoundForSpecificText(text);
        return vocabularyFound.size();
    }

    private Set<String> getVocabularyFoundForSpecificText(String text) {
        Set<String> vocabularyFound = new HashSet<>();
        StringTokenizer stringTokenizer = new StringTokenizer(text);
        while(stringTokenizer.hasMoreTokens()) {
            String word = stringTokenizer.nextToken();
            vocabularyFound.add(store.get(word));
        }
        return vocabularyFound;
    }

    public void addFullSentenceOfVocabulary(String sentence) {
        StringTokenizer sentenceTokenizer = new StringTokenizer(sentence, " ");
        while(sentenceTokenizer.hasMoreTokens()) {
            String wordWithStringId = sentenceTokenizer.nextToken();
            String[] split = wordWithStringId.split("@");
            addVocabulary(split[0],split[1]);
        }
    }

    public int letterCountForText(String text) {
        Set<String> vocabularyFoundForSpecificText = getVocabularyFoundForSpecificText(text);
        int total = 0;
        for(String word : vocabularyFoundForSpecificText) {
            String wordByStrongID = greekRepository.getWordByStrongID(word);
            total += wordByStrongID != null ? wordByStrongID.trim().length() : 0;
        }
        return total;
    }

    public String getAllInOneSentence(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> vocabularyFoundForSpecificText = getVocabularyFoundForSpecificText(text);
        for(String word : vocabularyFoundForSpecificText) {
            String wordByStrongID = greekRepository.getWordByStrongID(word);
            stringBuilder.append(wordByStrongID).append(" ");
        }
        return stringBuilder.toString().trim();
    }
}

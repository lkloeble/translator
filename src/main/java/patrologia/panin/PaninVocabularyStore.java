package patrologia.panin;

import java.util.*;

public class PaninVocabularyStore {

    private Map<String,String> store = new HashMap<String,String>();

    public void addVocabulary(String word, String root) {
        store.put(word.toLowerCase(),root.toLowerCase());
    }

    public int checkNumberOfWordsInVocabulary(String text) {
        Set<String> vocabularyFound = new HashSet<>();
        StringTokenizer stringTokenizer = new StringTokenizer(text);
        while(stringTokenizer.hasMoreTokens()) {
            String word = stringTokenizer.nextToken();
            vocabularyFound.add(store.get(word));
        }
        return vocabularyFound.size();
    }

    public void addFullSentenceOfVocabulary(String sentence) {
        StringTokenizer sentenceTokenizer = new StringTokenizer(sentence, " ");
        while(sentenceTokenizer.hasMoreTokens()) {
            String wordWithStringId = sentenceTokenizer.nextToken();
            String[] split = wordWithStringId.split("@");
            addVocabulary(split[0],split[1]);
        }
    }
}

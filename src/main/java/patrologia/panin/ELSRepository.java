package patrologia.panin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ELSRepository {

    private Set<String> words = new HashSet<>();

    public ELSRepository(Collection<String> wordsToAdd) {
        addWords(wordsToAdd);
    }

    public void addWords(Collection<String> wordsToAdd) {
        words.addAll(wordsToAdd);
    }

    public boolean vocabularyMatching(String textToStudy) {
        for(String word : words) {
            if(allLettersAreFoundInSequenceToStudy(textToStudy,word)) return true;
        }
        return false;
    }

    private boolean allLettersAreFoundInSequenceToStudy(String textToStudy, String word) {
        Set<String> letters = extractSingleLetters(word);
        for(String letter : letters) {
            if(!textToStudy.contains(letter)) return false;
        }
        return true;
    }

    private Set<String> extractSingleLetters(String word) {
        char[] letters = word.toCharArray();
        Set<String> uniqueLetters = new HashSet<>();
        for(char letter : letters) {
            uniqueLetters.add(Character.toString(letter));
        }
        return uniqueLetters;
    }
}

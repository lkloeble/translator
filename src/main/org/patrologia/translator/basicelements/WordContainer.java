package org.patrologia.translator.basicelements;

import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.linguisticimplementations.*;

import java.util.*;

/**
 * Created by lkloeble on 29/12/2015.
 */
public class WordContainer {

    private Set<Word> wordSet = new HashSet<>();
    private Language language;
    private int position;

    public  WordContainer(Language language, int position) {
        this.language = language;
        this.position = position;
    }

    public WordContainer(Word word, int position, Language language) {
        this.language = language;
        this.position = position;
        wordSet.add(word);
    }

    public WordContainer(Collection<Word> words, Language language) {
        this.language = language;
        wordSet.addAll(words);
    }

    public void putOtherPossibleWord(Word word) {
        wordSet.add(word);
    }

    public void putOtherPossibleWords(Collection<Word> words) {
        wordSet.addAll(words);
    }

    public boolean containsUniqueWord() {
        return wordSet.size() == 1;
    }

    public Word getUniqueWord() {
        if(wordSet.size() >= 1) {
            return getWordSet().get(0);
        }
        return new NullWord(language);
    }

    public Word getUniqueWordWithTypePreference(WordType wordType) {
        Word preferedTypeWord = getWordByType(wordType);
        if(preferedTypeWord != null) return preferedTypeWord;
        return getUniqueWord();
    }

    @Override
    public String toString() {
        return "WCont{" +
                "nb=" + wordSet.size() + " " + wordSet + '}';
    }

    public String getInitialValue() {
        return getUniqueWord().getInitialValue();
    }

    public int size() {
        return wordSet.size();
    }

    public int numberByWordType(WordType wordType) {
        for(Word word : getWordSet()) {
            if(word.wordType.equals(wordType)) {
                return 1;
            }
        }
        return  0;
    }

    public Word getWordByType(WordType wordType) {
        for(Word word : getWordSet()) {
            if(word.wordType.equals(wordType)) {
                return word;
            }
        }
        return new NullWord(language);
    }

    public void updateInitialValue(String newValue) {
        getUniqueWord().setInitialValue(newValue);
    }

    public void modifyContentByPatternReplacement(String origin, String replacement) {
        for(Word word : getWordSet()) {
            word.modifyContentByPatternReplacement(origin, replacement);
        }
    }

    public void deleteWordByWordType(WordType wordType) {
        Set<Word> newSet = new HashSet<Word>();
        for(Word word : getWordSet()) {
            if(word.wordType.equals(wordType)) {
                continue;
            }
            newSet.add(word);
        }
        wordSet = newSet;
    }

    public boolean needsToBeErased() {
        for(Word word : getWordSet()) {
            if(Word.WORD_TO_REMOVE.equals(word.getInitialValue())) {
                return true;
            }
        }
        return false;
    }

    public int getPosition() {
        return position;
    }

    public void updateRoot(String newRoot) {
        for(Word word : getWordSet()) {
            word.updateRoot(newRoot);
        }
    }

    public boolean hasOnlyOneType(WordType wordType) {
        for(Word word : getWordSet()) {
            if(!word.getWordType().equals(wordType)) {
                return false;
            }
        }
        return true;
    }

    public boolean keepNounOfThisGender(Gender genderToKeep) {
        Set<Word> newWordSet = new HashSet<>();
        for(Word word : getWordSet()) {
            if(word.getGender().equals(genderToKeep)) {
                newWordSet.add(word);
            }
        }
        if(newWordSet.size() > 0) {
            wordSet = newWordSet;
            return true;
        }
        return false;
    }

    public List<Word> getWordSet() {
        List<Word> words = new ArrayList<>(wordSet);
        Collections.sort(words, new WordGenderComparator());
        return words;
    }

    public void eraseElectedCaseNumberInMultipleWordset(CaseNumberGenre electedCaseNumber, Word wordWithElected) {
        for(Word word : getWordSet()) {
            if(!word.isNoun()) continue;
            if(wordWithElected.equals(word)) continue;
            if(word.hasElectedWithoutCompute() && word.isNoun() && ((Noun)word).getElectedCaseNumber().equals(electedCaseNumber)) continue;
            ((Noun)word).erasePossibleCaseNumberWithoutGenre(electedCaseNumber);
        }
    }

    public List<Word> getWordSetOrderedByPossibleCaseNumber() {
        List<Word> words = new ArrayList<>(getWordSet());
        Collections.sort(words, new WordPossibleCaseComparator());
        return words;
    }

    public String getRoot() {
        return getUniqueWord().getRoot();
    }

    public void clearAll() {
        wordSet.clear();
    }

    public void eraseUnelectedCaseNumberInMultipleWordsetNoun() {
        Set<Word> wordSetWithOnlyElected = new HashSet<>();
        for(Word word : getWordSet()) {
            if(!word.isNoun()) {
                wordSetWithOnlyElected.add(word);
                continue;
            }
            Noun noun = (Noun)word;
            if(noun.hasElectedWithoutCompute()) {
                wordSetWithOnlyElected.add(noun);
            }
        }
        wordSet = new HashSet<>(wordSetWithOnlyElected);
    }
}

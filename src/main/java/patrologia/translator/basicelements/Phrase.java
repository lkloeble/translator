package patrologia.translator.basicelements;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.casenumbergenre.Number;

import java.util.*;

public class Phrase {

    Map<Integer, WordContainer> wordContainersWithPosition = new HashMap<>();
    private Language language;

    public Phrase(int size, Language language) {
        this.language = language;
        wordContainersWithPosition = new HashMap<>(size);
    }

    public WordContainer getWordContainerAtPosition(int i) {
        if(i<0 || i>getHighestPositionInPhrase()) {
            return new WordContainer(new NullWord(language),i,language);
        }
        return wordContainersWithPosition.get(i) != null ? wordContainersWithPosition.get(i) : new WordContainer(new NullWord(language),i,language);
    }

    private int getHighestPositionInPhrase() {
        return wordContainersWithPosition.keySet().stream().max(Comparator.<Integer>naturalOrder()).get();
    }

    public Set<Map.Entry<Integer, WordContainer>> getWordEntrySet() {
        return wordContainersWithPosition.entrySet();
    }

    public Set<Map.Entry<Integer, WordContainer>> getWordContainerEntrySet() {
        return wordContainersWithPosition.entrySet();
    }

    public int size() {
        return wordContainersWithPosition.size();
    }

    public Set<Integer> keySet() {
        return wordContainersWithPosition.keySet();
    }

    public List<WordContainer> valueSet() { return new ArrayList(wordContainersWithPosition.values());}

    public void addWordAtPosition(Integer indice, Word word) {
        WordContainer wordContainerAtPosition = wordContainersWithPosition.get(indice);
        if(wordContainerAtPosition == null) {
            wordContainersWithPosition.put(indice, new WordContainer(word,indice,language));
        } else {
            wordContainerAtPosition.putOtherPossibleWord(word);
        }
    }

    public void addWordsAtPosition(Integer indice, Collection<Word> words) {
        WordContainer wordContainerAtPosition = wordContainersWithPosition.get(indice);
        if(wordContainerAtPosition == null) {
            wordContainersWithPosition.put(indice, new WordContainer(words,language));
        } else {
            wordContainerAtPosition.putOtherPossibleWords(words);
        }
    }

    public void addWordContainerAtPosition(Integer indice, WordContainer wordContainer, Phrase phraseToAdd) {
        WordContainer cloneContainer = getCloneContainer(wordContainer, phraseToAdd, indice);
        wordContainersWithPosition.put(indice, cloneContainer);
    }

    private WordContainer getCloneContainer(WordContainer wordContainer, Phrase phraseToAdd, Integer indice) {
        WordContainer clone = new WordContainer(language,indice);
        for(Word word : wordContainer.getWordSet()) {
            Word clonedWord = getClone(word);
            clonedWord.putSentenceInformation(phraseToAdd, indice);
            clone.putOtherPossibleWord(clonedWord);
        }
        return clone;
    }

    private Word getClone(Word word) {
        try {
            switch (word.wordType) {
                case NOUN:
                    return new Noun((Noun) word);
                case PREPOSITION:
                    return new Preposition((Preposition) word);
                case VERB:
                    return new Verb((Verb) word);
                default:
                    return new Word(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new NullWord(Language.UNKNOWN);
    }

    public boolean isEmpty() {
        return size() == 0;
    }


    public List<Word> getNearWords(int positionInPhrase) {
        int nbOfWords = wordContainersWithPosition.size() / 4 + 1;
        nbOfWords = nbOfWords > 2 ? nbOfWords : 2;
        nbOfWords = nbOfWords > 4 ? 4 : nbOfWords;
        List<Integer> indicesToCollect = getCollectionIndices(positionInPhrase, nbOfWords);
        List<Word> nearWords = new ArrayList<>(nbOfWords);
        indicesToCollect.stream().forEach(indice -> nearWords.add(wordContainersWithPosition.get(indice).getUniqueWordWithTypePreference(WordType.NOUN)));
        return nearWords;
    }

    private List<Integer> getCollectionIndices(int positionInPhrase, int nbOfWords) {
        if(positionInPhrase == 1) {
            return getFollowingWordsIndices(positionInPhrase, nbOfWords);
        } else if(positionInPhrase == getLastPosition()) {
            return getPrecedingWordsIndices(positionInPhrase, nbOfWords);
        }
        List<Integer> indices = new ArrayList<>();
        for(int i=0;i<nbOfWords;i+=2) {
            indices.addAll(getFollowingWordsIndices(positionInPhrase+i/2, 1));
            indices.addAll(getPrecedingWordsIndices(positionInPhrase - i / 2, 1));
        }
        return indices;
    }

    private List<Integer> getPrecedingWordsIndices(int positionInPhrase, int nbOfWords) {
        List<Integer> integers = new ArrayList(wordContainersWithPosition.keySet());
        Collections.sort(integers);
        int beginningIndice = integers.indexOf(positionInPhrase)-nbOfWords;
        beginningIndice = beginningIndice < 0 ? 0 : beginningIndice;
        return integers.subList(beginningIndice,beginningIndice+nbOfWords);
    }

    private List<Integer> getFollowingWordsIndices(int positionInPhrase, int nbOfWords) {
        List<Integer> integers = new ArrayList(wordContainersWithPosition.keySet());
        Collections.sort(integers);
        int beginningIndice = integers.indexOf(positionInPhrase) + 1;
        if(beginningIndice < 0 || beginningIndice+nbOfWords > wordContainersWithPosition.size()) {
            return Collections.EMPTY_LIST;
        }
        return integers.subList(beginningIndice,beginningIndice+nbOfWords);
    }

    private int getLastPosition() {
        return wordContainersWithPosition.keySet().stream().max(Comparator.<Integer>naturalOrder()).get();
    }

    public List<Number> getNumbers(List<Word> surroundingWords) {
        List<Number> numbers = new ArrayList<Number>();
        surroundingWords.stream().forEach(word -> numbers.add(word.getNumberElected()));
        return numbers;
    }

    public  boolean containsWordWithContent(String content) {
        for(WordContainer word : wordContainersWithPosition.values()) {
            if(word.getInitialValue().equals(content)) {
                return true;
            }
        }
        return false;
    }

    public WordContainer getWordByContent(String content) {
        if(!containsWordWithContent(content)) return new WordContainer(new NullWord(language),-1,language);
        for(WordContainer word : wordContainersWithPosition.values()) {
            if(word.getInitialValue().equals(content)) {
                return word;
            }
        }
        return new WordContainer(new NullWord(language),-1,language);
    }

    public Integer getIndiceByContent(String content) {
        if(!containsWordWithContent(content)) return 0;
        int indice = 1;
        for(WordContainer word : wordContainersWithPosition.values()) {
            if(word.getInitialValue().equals(content)) {
                return indice;
            }
            indice++;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Phrase{" +
                "words.size=" + wordContainersWithPosition.size() +
                '}';
    }

    public Word getYetUnknownWordAtPosition(Integer indice) {
        return getWordContainerAtPosition(indice).getUniqueWord();
    }

    public boolean hasWordContainerInPosition(int position) {
        return wordContainersWithPosition.get(position) != null;
    }

    public int getMaxIndice() {
        int max  = 0;
        for(Integer indice : wordContainersWithPosition.keySet()) {
            if(indice > max) {
                max = indice;
            }
        }
        return max;
    }

    public int getNumberOfWordsByType(WordType wordType) {
        int total = 0;
        for(WordContainer wordContainer : wordContainersWithPosition.values()) {
            total += wordContainer.numberByWordType(wordType);
        }
        return total;
    }

    public Verb getUniqueVerb() {
        for(WordContainer wordContainer : wordContainersWithPosition.values()) {
            if(wordContainer.getWordByType(WordType.VERB).wordType.equals(WordType.VERB)) {
                return (Verb)wordContainer.getWordByType(WordType.VERB);
            }
        }
        return null;
    }

    public void modifyWordContenAndRootAtPosition(String newValue, int position) {
        WordContainer wordContainer = getWordContainerAtPosition(position);
        wordContainer.modifyContentByPatternReplacement(wordContainer.getInitialValue(), newValue);
    }

    public void modifyOnlyRootAtPosition(String newRoot, int position) {
        WordContainer wordContainer = getWordContainerAtPosition(position);
        wordContainer.updateRoot(newRoot);
    }


    public void updateWordContentAtPosition(String newValue, int position) {
        WordContainer wordContainer = getWordContainerAtPosition(position);
        wordContainer.updateInitialValue(newValue);
    }

    public boolean hasUndefinedWords() {
        for(WordContainer wordContainer : wordContainersWithPosition.values()) {
            Word word = wordContainer.getUniqueWord();
            if(WordType.UNKNOWN == word.wordType) {
                return true;
            }
        }
        return false;
    }

    public List<WordContainer> getWordsByType(WordType wordType) {
        List<WordContainer> containerList = new ArrayList<>();
        for(WordContainer wordContainer : wordContainersWithPosition.values()) {
            if(wordContainer.numberByWordType(wordType) > 0) {
                containerList.add(wordContainer);
            }
        }
        return containerList;
    }

    public Phrase getSubPhraseFrom(int startPoint) {
        Phrase subPhrase = new Phrase(size()-startPoint, language);
        int subIndice = 0;
        for(int indice = startPoint;indice<=size();indice++) {
            subPhrase.addWordContainerAtPosition(subIndice++, getWordContainerAtPosition(indice),subPhrase);
        }
        return subPhrase;
    }

    public Phrase getSubPhraseFromTo(int startPoint,int endPoint) {
        Phrase subPhrase = new Phrase(endPoint-startPoint, language);
        int subIndice = 0;
        for(int indice = startPoint;subIndice<endPoint-startPoint;indice++) {
            subPhrase.addWordContainerAtPosition(subIndice++, getWordContainerAtPosition(indice),subPhrase);
        }
        return subPhrase;
    }


    public List<String> getListOfInitialValues() {
        List<String> initialValues = new ArrayList<>();
        for(WordContainer wordContainer : wordContainersWithPosition.values())  {
            initialValues.add(wordContainer.getInitialValue());
        }
        return initialValues;
    }

    public void emptyWordContainer(Integer position) {
        wordContainersWithPosition.put(position,null);
    }

    public boolean hasInitialValueBetweenPositions(String patterToSearch, int start, int end) {
        Phrase subPhrase = getSubPhraseFromTo(start,end);
        return subPhrase.containsWordWithContent(patterToSearch);
    }

    public void keepOnlyOneWordTypeAtPosition(WordType wordType, int position) {
        WordContainer wordContainer = getWordContainerAtPosition(position);
        List<Word> wordSet = wordContainer.getWordSet();
        wordContainer.clearAll();
        for(Word word : wordSet) {
            if(word.hasType(wordType)) {
                wordContainer.putOtherPossibleWord(word);
            }
        }
    }

}

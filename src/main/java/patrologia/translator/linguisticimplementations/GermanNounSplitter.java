package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository2;

import java.util.Locale;
import java.util.Set;

public class GermanNounSplitter {

    private static final Integer MINIMUM_LENGTH_TO_TRY_SPLIT = 3;

    private NounRepository nounRepository;
    private PrepositionRepository prepositionRepository;
    private VerbRepository2 verbRepository;
    private GermanSplits germanSplits = new GermanSplits();

    public GermanNounSplitter(NounRepository nounRepository, PrepositionRepository prepositionRepository, VerbRepository2 verbRepository) {
        this.prepositionRepository = prepositionRepository;
        this.nounRepository = nounRepository;
        this.verbRepository = verbRepository;
    }

    public Phrase germanFindComposedWords(Phrase phrase) {
        germanSplits.reset();
        if (!phrase.hasUndefinedWords()) {
            return phrase;
        }
        if (!thereAreSplitsToDo(phrase)) {
            return phrase;
        }
        Phrase newPhraseWithNounsAndOrPrepositions = germanSplits.expandPhrase(phrase);
        return newPhraseWithNounsAndOrPrepositions;
    }

    private GermanSplit splitWords(Word wordToSplit) {
        String initialValue = wordToSplit.getInitialValue();
        if (initialValue.length() <= MINIMUM_LENGTH_TO_TRY_SPLIT) {
            return null;
        }
        for (int indiceSplit = MINIMUM_LENGTH_TO_TRY_SPLIT; indiceSplit < initialValue.length(); indiceSplit++) {
            String leftWord = initialValue.substring(0, indiceSplit);
            if (isAGenitiveOfCombination(leftWord)) {
                leftWord = leftWord.substring(0, leftWord.length() - 1);
                if(!nounRepository.hasNoun(leftWord)) leftWord += "s";
            }
            String rightWord = initialValue.substring(indiceSplit, initialValue.length());
            if (isWordInRepositories(leftWord) && isWordInRepositories(rightWord)) {
                Word word0 = getWordFromEitherRepository(leftWord);
                Word word1 = getWordFromEitherRepository(rightWord);
                return new GermanSplit(word0, word1);
            } else if (isWordInRepositories(leftWord) && splitWords(new Word(WordType.NOUN, rightWord, Language.GERMAN)) != null) {
                Word word0 = getWordFromEitherRepository(leftWord);
                GermanSplit germanSplit = splitWords(new Word(WordType.NOUN, rightWord, Language.GERMAN));
                Word word1 = germanSplit.getLeftValue();
                Word word2 = germanSplit.getRightValue();
                return new GermanSplit(word0, word1, word2);
            }
        }
        return null;
    }

    private boolean isAGenitiveOfCombination(String leftWord) {
        String withoutS = leftWord.substring(0, leftWord.length() - 1);
        if(leftWord.toLowerCase(Locale.ROOT).endsWith("s") && nounRepository.hasNoun(leftWord) || nounRepository.hasNoun(withoutS)) {
            return true;
        }
        return false;
    }

    private boolean thereAreSplitsToDo(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        for (Integer indice : indices) {
            WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
            if (wordContainerAtPosition.numberByWordType(WordType.UNKNOWN) == 0) continue;
            Word unknownWord = wordContainerAtPosition.getWordByType(WordType.UNKNOWN);
            GermanSplit germanSplit = splitWords(unknownWord);
            if (germanSplit != null) {
                germanSplits.addSplit(germanSplit, indice);
            }
        }
        return germanSplits.hasSplits();
    }

    private boolean isWordInRepositories(String word) {
        if (nounRepository.hasNoun(word)) return true;
        if (prepositionRepository.hasPreposition(word)) return true;
        if(verbRepository.hasVerb(word)) return true;
        return false;
    }

    private Word getWordFromEitherRepository(String word) {
        if (nounRepository.hasNoun(word)) {
            return (Word) nounRepository.getNoun(word).toArray()[0];
        } else  if(prepositionRepository.hasPreposition(word)) {
            return prepositionRepository.getPreposition(word);
        } else {
            Verb verb = verbRepository.getVerb(word);
            verb.setInitialValue(word);
            return verb;
        }
    }


}

package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.modificationlog.ModificationLog;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.rule.RuleFactory;
import patrologia.translator.utils.ExpressionHolder;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Laurent KLOEBLE on 05/10/2015.
 */
public abstract class CustomLanguageRulePhraseChanger {

    protected Language language;

    public abstract Phrase modifyPhrase(Phrase startPhrase, ModificationLog modificationLog, CustomRule customRule);

    protected boolean endsWithPattern(WordContainer wordAtPosition, String patternToFind, List<String> stopWords, Accentuer accentuer) {
        String initialValue = wordAtPosition.getInitialValue() != null ? wordAtPosition.getInitialValue() : "";
        if(stopWords.contains(wordAtPosition.getInitialValue().toLowerCase())) return false;
        if(stopWords.contains(accentuer.unaccentued(wordAtPosition.getInitialValue().toLowerCase()))) return false;
        if(wordAtPosition.getInitialValue().length() == patternToFind.length()) return false;
        return initialValue.toLowerCase().endsWith(patternToFind);
    }

    protected boolean endsWithPatternInAnAuthorizedList(WordContainer wordAtPosition, String patternToFind, List<String> stopWords) {
        String initialValue = wordAtPosition.getInitialValue() != null ? wordAtPosition.getInitialValue() : "";
        if(!stopWords.contains(wordAtPosition.getInitialValue().toLowerCase())) return false;
        if(wordAtPosition.getInitialValue().length() == patternToFind.length()) return false;
        return initialValue.toLowerCase().endsWith(patternToFind);
    }

    protected Word wordWithoutEndingPattern(WordContainer wordAtPosition, String endingPattern) {
        Word modifiedWord = wordAtPosition.getUniqueWord();
        modifiedWord.updateInitialValue(wordAtPosition.getInitialValue().substring(0,wordAtPosition.getInitialValue().length()-endingPattern.length()));
        if(wordAtPosition.getRoot().endsWith(endingPattern)) {
            modifiedWord.updateRoot(wordAtPosition.getRoot().substring(0, wordAtPosition.getRoot().length() - endingPattern.length()));
        }
        return modifiedWord;
    }

    protected Word wordWithoutBeginningPattern(WordContainer wordContainer, String leadingPattern) {
        Word modifiedWord = wordContainer.getUniqueWord();
        String newValue = wordContainer.getInitialValue().substring(leadingPattern.length());
        modifiedWord.updateInitialValue(newValue);
        modifiedWord.updateRoot(newValue);
        return modifiedWord;
    }

    protected Phrase substituteEndPatternWithNewPreposition(Phrase phrase, String endingPattern, Word word, List<String> stopWords, Accentuer accentuer) {
        Set<Integer> integers = phrase.keySet();
        int numberOfEndingPattern = 0;
        for(Integer indice : integers) {
            numberOfEndingPattern += endsWithPattern(phrase.getWordContainerAtPosition(indice), endingPattern, stopWords,accentuer) ? 1 : 0;
        }
        if(numberOfEndingPattern == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfEndingPattern,language);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(endsWithPattern(phrase.getWordContainerAtPosition(indice), endingPattern, stopWords,accentuer)) {
                newPhrase.addWordAtPosition(newPhraseIndice, word);
                newPhrase.addWordAtPosition(newPhraseIndice+1, wordWithoutEndingPattern(phrase.getWordContainerAtPosition(indice), endingPattern));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    protected Phrase substituteBeginPatternWithNewWord(Phrase phrase, String beginPattern, Word wordReplace, Accentuer accentuer) {
        Set<Integer> integers = phrase.keySet();
        int numberOfBeginPattern = 0;
        Set<List<String>> emptyListSet = Collections.singleton(Collections.EMPTY_LIST);
        for(Integer indice : integers) {
            numberOfBeginPattern += startsWithLetter(phrase.getWordContainerAtPosition(indice).getUniqueWord(), beginPattern, emptyListSet, accentuer) ? 1 : 0;
        }
        if(numberOfBeginPattern == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfBeginPattern,language);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(startsWithLetter(phrase.getWordContainerAtPosition(indice).getUniqueWord(), beginPattern, emptyListSet, accentuer)) {
                newPhrase.addWordAtPosition(newPhraseIndice+1, wordWithoutBeginningPattern(phrase.getWordContainerAtPosition(indice), beginPattern));
                newPhrase.addWordAtPosition(newPhraseIndice, wordReplace);
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    protected Phrase splitBeginPrepositionAndVerbForm(Phrase phrase, Preposition preposition, String verbForm, VerbRepository verbRepository) {
        Set<Integer> integers = phrase.keySet();
        int numberOfBeginPreposition = 0;
        for(Integer indice : integers) {
            numberOfBeginPreposition += verbFormStartsWithPreposition(phrase.getWordContainerAtPosition(indice).getUniqueWord(), preposition, verbForm, verbRepository) ? 1 : 0;
        }
        if(numberOfBeginPreposition == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfBeginPreposition,language);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(verbFormStartsWithPreposition(phrase.getWordContainerAtPosition(indice).getUniqueWord(), preposition, verbForm, verbRepository)) {
                newPhrase.addWordAtPosition(newPhraseIndice, preposition);
                newPhrase.addWordAtPosition(newPhraseIndice+1, wordWithoutBeginningPattern(phrase.getWordContainerAtPosition(indice), preposition.getInitialValue()));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    protected Phrase extractLetterFromBeginningOfWord(Phrase phrase, String letter, List<String> stopWords, RuleFactory ruleFactory, String ruleName, Accentuer accentuer) {
        Set<Integer> integers = phrase.keySet();
        int numberOfStartingLetter = 0;
        for(Integer indice : integers) {
            numberOfStartingLetter += startsWithLetter(phrase.getWordContainerAtPosition(indice).getUniqueWord(), letter, Collections.singleton(stopWords), accentuer) ? 1 : 0;
        }
        if(numberOfStartingLetter == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfStartingLetter,language);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(startsWithLetter(phrase.getWordContainerAtPosition(indice).getUniqueWord(), letter, Collections.singleton(stopWords), accentuer)) {
                Noun leadingLetter = new Noun(language,letter, letter, Collections.EMPTY_LIST, null, null, null, Collections.EMPTY_LIST);//gugu
                if(ruleName != null) {
                    leadingLetter.addRule(ruleFactory.getRuleByName(ruleName,leadingLetter.getInitialValue()));
                }
                newPhrase.addWordAtPosition(newPhraseIndice, leadingLetter);
                newPhrase.addWordAtPosition(newPhraseIndice+1, wordWithoutBeginningPattern(phrase.getWordContainerAtPosition(indice), letter));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }


    protected boolean verbFormStartsWithPreposition(Word word, Preposition preposition, String verbForm, VerbRepository verbRepository) {
        String wordContent = word.getInitialValue();
        if(!wordContent.startsWith(preposition.getInitialValue()) || wordContent.equals(preposition.getInitialValue())) return false;
        String formWithoutLeadingPreposition = wordContent.substring(preposition.getInitialValue().length());
        return verbRepository.getVerb(formWithoutLeadingPreposition).getRoot().equals(verbForm);
    }

    protected boolean startsWithLetter(Word word, String letter, Set<List<String>> stopWordsLists, Accentuer accentuer) {
        String initialValue = word.getInitialValue() != null ? word.getInitialValue() : "";
        for(List<String> stopWords : stopWordsLists) {
            if (stopWords.contains(accentuer.unaccentued(word.getInitialValue().toLowerCase()))) return false;
            if (stopWords.contains(word.getInitialValue().toLowerCase())) return false;
        }
        if(word.getRoot().length() ==  1) return false;
        return initialValue.toLowerCase().startsWith(letter);
    }

    protected Phrase replaceExpressionsInManyWordsByAgreggateTranslatedWords(Phrase phrase, ExpressionHolder expressionHolder) {
        phrase = expressionHolder.aggregateExpressions(phrase);
        return phrase;
    }

}

package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.rule.english.EnglishRuleFactory;
import org.patrologia.translator.utils.ExpressionHolder;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class EnglishPhraseChanger extends CustomLanguageRulePhraseChanger {

    private EnglishModificationLog modificationLog;

    private List<String> stopWords = new ArrayList<>();
    private ExpressionHolder expressionHolder = new ExpressionHolder(Language.ENGLISH);

    public EnglishPhraseChanger(NounRepository nounRepository, EnglishRuleFactory englishRuleFactory) {
        super();
        stopWords.add("let's");
    }

    @Override
    public Phrase modifyPhrase(Phrase startPhrase, ModificationLog modificationLog, CustomRule customRule) {
        this.modificationLog = (EnglishModificationLog)modificationLog;
        modificationLog.reset();
        Phrase phraseWithoutAbbreviatedIs = replaceToBeIsAbbreviations(startPhrase);
        Phrase phraseWithoutAbbreviatedAre = replaceToBeAreAbbreviations(phraseWithoutAbbreviatedIs);
        Phrase phraseWithoutAbbreviatedHave = replaceToHaveAreAbbreviations(phraseWithoutAbbreviatedAre);
        Phrase replaceToBeIAmAbbreviations = replaceToBeIAmAbbreviations(phraseWithoutAbbreviatedHave);
        Phrase replaceExpressionsInManyWordsByAgreggateTranslatedWords = replaceExpressionsInManyWordsByAgreggateTranslatedWords(replaceToBeIAmAbbreviations, expressionHolder);
        return replaceExpressionsInManyWordsByAgreggateTranslatedWords;
    }

    private Phrase replaceToHaveAreAbbreviations(Phrase phrase) {
        return replaceVerbAbbreviations(phrase, "'ve", new Verb("have", "have",Language.ENGLISH), EnglishModificationLog.FINAL_VE_REPLACED_BY_HAVE, Collections.<String>emptyList());
    }

    private Phrase replaceVerbAbbreviations(Phrase phrase, String motifToSearch, Verb replacementVerb, Integer eventCode, List<String> stopWords) {
        Set<Integer> integers = phrase.keySet();
        int numberOfMotifsToReplace = 0;
        for (Integer indice : integers) {
            String initialValue = phrase.getWordContainerAtPosition(indice).getInitialValue().toLowerCase();
            if(stopWords.contains(initialValue)) continue;
            numberOfMotifsToReplace += initialValue.endsWith(motifToSearch) ? 1 : 0;
        }
        if (numberOfMotifsToReplace == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfMotifsToReplace,Language.ENGLISH);
        int newPhraseIndice = 1;
        for (Integer indice : integers) {
            if (phrase.getWordContainerAtPosition(indice).getInitialValue().toLowerCase().endsWith(motifToSearch)) {
                newPhrase.addWordAtPosition(newPhraseIndice, eraseFinalQuoteS(phrase.getWordContainerAtPosition(indice).getUniqueWord()));
                newPhrase.addWordAtPosition(newPhraseIndice + 1, replacementVerb);
                modificationLog.addEvent(new Event(eventCode, newPhraseIndice));
                newPhraseIndice += 2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice, phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }


    private Phrase replaceToBeAreAbbreviations(Phrase phrase) {
        return replaceVerbAbbreviations(phrase, "'re", new Verb("are", "be",Language.ENGLISH), EnglishModificationLog.FINAL_ABBREVIATED_ARE_REPLACED_BY_ARE, Collections.EMPTY_LIST);
    }

    private Phrase replaceToBeIsAbbreviations(Phrase phrase) {
        return replaceVerbAbbreviations(phrase, "'s", new Verb("is", "be",Language.ENGLISH), EnglishModificationLog.FINAL_ABBREVIATED_IS_REPLACED_BY_TO_BE, stopWords);
    }

    private Phrase replaceToBeIAmAbbreviations(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int numberOfIQuoteAm = 0;
        for (Integer indice : integers) {
            numberOfIQuoteAm += phrase.getWordContainerAtPosition(indice).getInitialValue().toLowerCase().equals("i'm") ? 1 : 0;
        }
        if (numberOfIQuoteAm == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfIQuoteAm,Language.ENGLISH);
        int newPhraseIndice = 1;
        for (Integer indice : integers) {
            if (phrase.getWordContainerAtPosition(indice).getInitialValue().toLowerCase().equals("i'm")) {
                newPhrase.addWordAtPosition(newPhraseIndice, new Preposition(Language.ENGLISH,"i", null));
                newPhrase.addWordAtPosition(newPhraseIndice + 1, new Verb("am", "be",Language.ENGLISH));
                newPhraseIndice += 2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice, phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    private Word eraseFinalQuoteS(Word word) {
        Word newWord = new Word(word);
        String newValue = word.getInitialValue().substring(0, word.getInitialValue().indexOf("'"));
        newWord.setInitialValue(newValue);
        newWord.setRoot(newValue);
        return newWord;
    }
}

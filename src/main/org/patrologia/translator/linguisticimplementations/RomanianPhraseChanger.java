package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.conjugation.ConjugationPart;
import org.patrologia.translator.conjugation.RootedConjugation;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianPhraseChanger extends CustomLanguageRulePhraseChanger {

    private NounRepository nounRepository;
    private VerbRepository verbRepository;
    private List<String> stopWords = new ArrayList<String>();


    public RomanianPhraseChanger(NounRepository nounRepository, VerbRepository verbRepository) {
        this.nounRepository = nounRepository;
        this.verbRepository = verbRepository;
        stopWords.add("inca");
        stopWords.add("ambigua");
        stopWords.add("doua");
        stopWords.add("mi_e");
        stopWords.add("s_a");
        stopWords.add("i_a");
        stopWords.add("printr_o");
    }

    @Override
    public Phrase modifyPhrase(Phrase startPhrase, ModificationLog modificationLog, CustomRule customRule) {
        Phrase badaReplacedByAccordingPreposition = replaceBada(startPhrase);
        Phrase phraseSint = replaceSintAbbreviation(badaReplacedByAccordingPreposition);
        Phrase gerondiveMinusI = replaceMinusIInGerondiveVerb(phraseSint);
        Phrase pentruCaByCustomNoun = replacePentruCaByCustomNoun(gerondiveMinusI);
        Phrase withSeparatedFeminineArticle = replaceEndAForFeminineNoun(pentruCaByCustomNoun, stopWords);
        Phrase withSeparatedMasculineArticle = replaceEndUlForMasculineNoun(withSeparatedFeminineArticle, Collections.EMPTY_LIST);
        Phrase replaceNWithMinusByNuWithSpace = replaceSuffixesWithMinus(withSeparatedMasculineArticle);
        Phrase splittedComposedWords = splitComposedWords(replaceNWithMinusByNuWithSpace);
        Phrase phraseSWithSunt = replaceSBySunt(splittedComposedWords);
        Phrase verbsInfinitiveWithPutea = verbsInfinitiveWithPuteaBeforeShouldIdentifyInfinitiveForms(phraseSWithSunt);
        Phrase verbsInfinitiveWithStie = verbsInfinitiveWithStieBeforeShouldIdentifyInfinitiveForms(verbsInfinitiveWithPutea);
        if(customRule.isSpecialToDo()) {
            return cleanPatternForAInRomanianAnalyzer(verbsInfinitiveWithStie);
        }
        return verbsInfinitiveWithStie;
    }

    private Phrase replaceMinusIInGerondiveVerb(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int numberOfEndingByMinusAndI = 0;
        for(Integer indice : integers) {
            numberOfEndingByMinusAndI += endWithLetters(phrase.getWordContainerAtPosition(indice).getUniqueWord(), "_i") && isAVerb(phrase.getWordContainerAtPosition(indice).getUniqueWord().getInitialValue().replace("_i", "")) ? 1 : 0;
        }
        if(numberOfEndingByMinusAndI == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfEndingByMinusAndI, Language.ROMANIAN);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(endWithLetters(phrase.getWordContainerAtPosition(indice).getUniqueWord(), "_i")) {
                Word wordWithoutMinusI = extractMinusAndI(phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhrase.addWordAtPosition(newPhraseIndice, wordWithoutMinusI);
                newPhrase.addWordAtPosition(newPhraseIndice+1, new Preposition(Language.ROMANIAN, "xxminusixx",null));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    private boolean isAVerb(String possibleVerb) {
        return verbRepository.hasVerb(possibleVerb);
    }

    private Phrase verbsInfinitiveWithPuteaBeforeShouldIdentifyInfinitiveForms(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        boolean hasFoundVerbs = false;
        int newIndice = 0;
        Phrase subPhrase = null;
        for(Integer indice : indices) {
            Word unknownWordAtPosition = phrase.getYetUnknownWordAtPosition(indice);
            if(!unknownWordAtPosition.getRoot().equals("pot")) continue;
            Phrase portionAfterPotVerb = phrase.getSubPhraseFrom(indice+1);
            int numberOfWordsByType = verbRepository.knowsThisInitialValues(decorateInitialValuesForRomanianInfinitive(portionAfterPotVerb.getListOfInitialValues()));
            if(numberOfWordsByType > 0) {
                hasFoundVerbs = true;
                subPhrase = portionAfterPotVerb;
                newIndice = indice;
                break;
            }
        }
        if(!hasFoundVerbs) return phrase;
        for(WordContainer wordContainer : subPhrase.valueSet()) {
            newIndice++;
            String initialValue = wordContainer.getInitialValue().replace("]","").replace("[","");
            if(!verbRepository.hasVerb("a " + initialValue)) continue;
            Verb verb = verbRepository.getVerb("a " + initialValue);
            TranslationInformationBean allFormsForTheVerbRoot = verbRepository.getAllFormsForTheVerbRoot(verb.getRoot());
            RootedConjugation rootedConjugation = allFormsForTheVerbRoot.getNameForms().get(verb.getRoot() + "@INFINITIVE");
            List<ConjugationPart> partLists = rootedConjugation.getPartLists();
            if(partLists.get(0).getValue().equals("a " + initialValue)) {
               wordContainer.updateInitialValue("a " + initialValue);
                phrase.addWordContainerAtPosition(newIndice, wordContainer, phrase);
            }
        }
        return phrase;
    }

    private Phrase verbsInfinitiveWithStieBeforeShouldIdentifyInfinitiveForms(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        boolean hasFoundVerbs = false;
        int newIndice = 0;
        Phrase subPhrase = null;
        for(Integer indice : indices) {
            Word unknownWordAtPosition = phrase.getYetUnknownWordAtPosition(indice);
            if(!unknownWordAtPosition.getRoot().equals("sti")) continue;
            Phrase portionAfterStiVerb = phrase.getSubPhraseFrom(indice+1);
            int numberOfWordsByType = verbRepository.knowsThisInitialValues(decorateInitialValuesForRomanianInfinitive(portionAfterStiVerb.getListOfInitialValues()));
            if(numberOfWordsByType > 0) {
                hasFoundVerbs = true;
                subPhrase = portionAfterStiVerb;
                newIndice = indice;
                break;
            }
        }
        if(!hasFoundVerbs) return phrase;
        for(WordContainer wordContainer : subPhrase.valueSet()) {
            newIndice++;
            if(!verbRepository.hasVerb("a " + wordContainer.getInitialValue())) continue;
            Verb verb = verbRepository.getVerb("a " +  wordContainer.getInitialValue());
            TranslationInformationBean allFormsForTheVerbRoot = verbRepository.getAllFormsForTheVerbRoot(verb.getRoot());
            RootedConjugation rootedConjugation = allFormsForTheVerbRoot.getNameForms().get(verb.getRoot() + "@INFINITIVE");
            List<ConjugationPart> partLists = rootedConjugation.getPartLists();
            String initialValue = wordContainer.getInitialValue();
            if(partLists.get(0).getValue().equals("a " + initialValue)) {
                wordContainer.updateInitialValue("a " + initialValue);
                wordContainer.updateRoot(initialValue);
                phrase.addWordContainerAtPosition(newIndice, wordContainer, phrase);
            }
        }
        return phrase;
    }


    private List<String> decorateInitialValuesForRomanianInfinitive(List<String> listOfInitialValues) {
        List<String> decoratedInifinitives = new ArrayList<>();
        for(String initialValue : listOfInitialValues) {
            initialValue = initialValue.replace("[", "");
            initialValue = initialValue.replace("]", "");
            decoratedInifinitives.add(initialValue);
            decoratedInifinitives.add("a "  + initialValue);
        }
        return decoratedInifinitives;
    }

    private Phrase splitComposedWords(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int numberOfEndingPattern = 0;
        for(Integer indice : integers) {
            String initialValue = phrase.getYetUnknownWordAtPosition(indice).getInitialValue();
            if(!stopWords.contains(initialValue) && initialValue.contains("_")) numberOfEndingPattern += 1;
        }
        if(numberOfEndingPattern == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfEndingPattern, Language.ROMANIAN);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            String initialValue = phrase.getYetUnknownWordAtPosition(indice).getInitialValue();
            if(!stopWords.contains(initialValue) && initialValue.contains("_")) {
                WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
                Word word = wordContainerAtPosition.getUniqueWord();
                String content = word.getInitialValue();
                String[] splittedContent = content.split("_");
                newPhrase.addWordAtPosition(newPhraseIndice, new Word(WordType.UNKNOWN, splittedContent[0], Language.ROMANIAN));
                newPhrase.addWordAtPosition(newPhraseIndice+1, new Word(WordType.UNKNOWN, splittedContent[1], Language.ROMANIAN));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    private Phrase replaceBada(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice  : integers) {
            Word currentWord = phrase.getYetUnknownWordAtPosition(indice);
            String current = currentWord.getInitialValue();
            Word nextWord = phrase.getYetUnknownWordAtPosition(indice + 1);
            String next = nextWord.getInitialValue();
            if("ba".equals(current) && "da".equals(next)) {
                currentWord.modifyContentByPatternReplacement("ba","bada");
                nextWord.modifyContentByPatternReplacement("da", Word.WORD_TO_REMOVE);
            }
        }
        return phrase;
    }

    private Phrase cleanPatternForAInRomanianAnalyzer(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice  : integers) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            wordContainer.modifyContentByPatternReplacement("[a]","a");
        }
        return phrase;
    }

    private Phrase replaceEndUlForMasculineNoun(Phrase phrase, List<String> stopWords) {
        return replaceEndPatternForPrecedingArticle(phrase, "ul", "masculinearticle", Collections.EMPTY_LIST);
    }

    private Phrase replaceEndAForFeminineNoun(Phrase phrase, List<String> stopWords) {
        return replaceEndPatternForPrecedingArticle(phrase, "a", "femininearticle", stopWords);
    }

    private Phrase replaceEndPatternForPrecedingArticle(Phrase phrase, String endPattern, String replacementArticle, List<String> stopWords) {
        Set<Integer> integers = phrase.keySet();
        int numberOfEndingPattern = 0;
        for(Integer indice : integers) {
            WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
            if(endsWithPattern(wordContainerAtPosition, endPattern, stopWords) && nounRepository.hasNoun(wordContainerAtPosition.getInitialValue())) {
                Collection<Noun> nounCollection = nounRepository.getNoun(wordContainerAtPosition.getInitialValue());
                Noun firstNoun = (Noun)nounCollection.toArray()[0];
                WordContainer previousWordContainer = phrase.getWordContainerAtPosition(indice-1);
                Word previousWord = previousWordContainer.getUniqueWord();
               // boolean isAlsoNotAdjective = previousWord != null && previousWord.isNoun() ? ((Noun)previousWord).isNotAnAdjective() : false;
                boolean otherCondition = true;
                if(previousWord.getInitialValue().equals("o")) otherCondition = false;
                if(previousWord.getInitialValue().equals("femininearticle")) otherCondition = false;
                if(firstNoun.isInvariable()) otherCondition = false;
                if(firstNoun.isNotAnAdjective() && !replacementArticle.equals(previousWord.getInitialValue()) && otherCondition) {
                    numberOfEndingPattern++;
                }
            }
        }
        if(numberOfEndingPattern == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfEndingPattern, Language.ROMANIAN);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            WordContainer wordContainerAtIndice = phrase.getWordContainerAtPosition(indice);
            if(endsWithPattern(wordContainerAtIndice, endPattern, stopWords) && nounRepository.hasNoun(wordContainerAtIndice.getInitialValue())) {
                Word word = wordContainerAtIndice.getUniqueWord();
                Noun noun = (Noun)nounRepository.getNoun(word.getInitialValue()).toArray()[0];
                String previousInitialValue = phrase.getWordContainerAtPosition(indice - 1).getInitialValue();
                if(noun.isAdjective() || noun.isInvariable() || previousInitialValue.equals("femininearticle") || previousInitialValue.equals("o")) {
                    newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                    newPhraseIndice++;
                    continue;
                }
                String newContenu  = word.getInitialValue().substring(0,word.getInitialValue().length());
                newPhrase.addWordAtPosition(newPhraseIndice, new Word(WordType.UNKNOWN, replacementArticle, Language.ROMANIAN));
                newPhrase.addWordAtPosition(newPhraseIndice+1, new Word(WordType.UNKNOWN, newContenu, Language.ROMANIAN));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordContainerAtPosition(newPhraseIndice, phrase.getWordContainerAtPosition(indice), newPhrase);
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    private Phrase replaceSintAbbreviation(Phrase phrase) {
        return replaceMinusAndIByEste(replaceEByEste(phrase));
    }

    private Phrase replaceSuffixesWithMinus(Phrase phrase) {
        phrase = separateEndPatternAndWord(phrase, "_mi");
        phrase = separateEndPatternAndWord(phrase, "_mea");
        phrase = separateEndPatternAndWord(phrase, "_l");
        return substituteBeginPatternWithNewWord(phrase, "n_", new Word(WordType.PREPOSITION, "nu", Language.ROMANIAN));
    }

    private Phrase separateEndPatternAndWord(Phrase phrase, String endingPattern) {
        Set<Integer> integers = phrase.keySet();
        int numberOfEndingPattern = 0;
        for(Integer indice : integers) {
            numberOfEndingPattern += endsWithPattern(phrase.getWordContainerAtPosition(indice), endingPattern, Collections.EMPTY_LIST) ? 1 : 0;
        }
        if(numberOfEndingPattern == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfEndingPattern, Language.ROMANIAN);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(endsWithPattern(phrase.getWordContainerAtPosition(indice), endingPattern, Collections.EMPTY_LIST)) {
                WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
                Word word = wordContainerAtPosition.getUniqueWord();
                String newContenu  = word.getInitialValue().replaceAll(endingPattern,"");
                newPhrase.addWordAtPosition(newPhraseIndice, new Word(WordType.UNKNOWN, newContenu, Language.ROMANIAN));
                newPhrase.addWordAtPosition(newPhraseIndice+1, new Word(WordType.UNKNOWN, endingPattern.replace("_",""), Language.ROMANIAN));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    private Phrase replacePentruCaByCustomNoun(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int numberOfPentruCa = 0;
        for(Integer indice : integers) {
            Word wordAtPosition = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            Word followingwordAtPosition = phrase.getWordContainerAtPosition(indice+1).getUniqueWord();
            if(wordAtPosition.getInitialValue().equals("pentru") && followingwordAtPosition.getInitialValue().replace("]","").replace("[","").equals("ca")) {
                numberOfPentruCa += 1;
            }
        }
        if(numberOfPentruCa == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() - numberOfPentruCa, Language.ROMANIAN);
        int phraseMaxIndice = phrase.getMaxIndice();
        int newIndice = 1;
        for(int indice = 1;indice<=phraseMaxIndice;indice++,newIndice++) {
            Word wordAtPosition = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            Word followingwordAtPosition = phrase.getWordContainerAtPosition(indice+1).getUniqueWord();
            if(wordAtPosition.getInitialValue().equals("pentru") && followingwordAtPosition.getInitialValue().replace("]","").replace("[","").equals("ca")) {
                newPhrase.addWordAtPosition(newIndice, new Noun(Language.ROMANIAN, "pentrucaXX", "pentrucaXX", Collections.EMPTY_LIST, new Gender(Gender.NEUTRAL), null, "inv", Collections.EMPTY_LIST));
                indice++;
            } else {
                newPhrase.addWordAtPosition(newIndice, wordAtPosition);
            }
        }
        return newPhrase;
    }

    private Phrase replaceMinusAndIByEste(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int numberOfEndingByMinusAndI = 0;
        for(Integer indice : integers) {
            numberOfEndingByMinusAndI += endWithLetters(phrase.getWordContainerAtPosition(indice).getUniqueWord(), "_i") && isNotAVerb(phrase.getWordContainerAtPosition(indice).getUniqueWord().getInitialValue().replace("_i","")) ? 1 : 0;
        }
        if(numberOfEndingByMinusAndI == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfEndingByMinusAndI, Language.ROMANIAN);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(endWithLetters(phrase.getWordContainerAtPosition(indice).getUniqueWord(), "_i")) {
                Word wordWithoutMinusI = extractMinusAndI(phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhrase.addWordAtPosition(newPhraseIndice, wordWithoutMinusI);
                newPhrase.addWordAtPosition(newPhraseIndice+1, new Verb("este", verbRepository, Language.ROMANIAN));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    private boolean isNotAVerb(String possibleVerb) {
        return !verbRepository.hasVerb(possibleVerb);
    }

    private Word extractMinusAndI(Word wordAtPosition) {
        Word newWord = new Word(wordAtPosition);
        newWord.setInitialValue(wordAtPosition.getInitialValue().substring(0,wordAtPosition.getInitialValue().length()-2));
        newWord.setRoot(newWord.getInitialValue());
        return newWord;
    }

    private Phrase replaceEByEste(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int numberOfSingleE = 0;
        for(Integer indice : integers) {
            numberOfSingleE += phrase.getWordContainerAtPosition(indice).getInitialValue().equals("e") ? 1 : 0;
        }
        if(numberOfSingleE == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfSingleE, Language.ROMANIAN);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(phrase.getWordContainerAtPosition(indice).getInitialValue().equals("e")) {
                newPhrase.addWordAtPosition(newPhraseIndice, new Verb("este", verbRepository, Language.ROMANIAN));
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice, phrase.getWordContainerAtPosition(indice).getUniqueWord());
            }
            newPhraseIndice++;
        }
        return newPhrase;
    }

    private Phrase replaceSBySunt(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int numberOfSingleS = 0;
        for(Integer indice : integers) {
            numberOfSingleS += phrase.getWordContainerAtPosition(indice).getInitialValue().equals("s") ? 1 : 0;
        }
        if(numberOfSingleS == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfSingleS, Language.ROMANIAN);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(phrase.getWordContainerAtPosition(indice).getInitialValue().equals("s")) {
                newPhrase.addWordAtPosition(newPhraseIndice, new Verb("sunt", verbRepository, Language.ROMANIAN));
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice, phrase.getWordContainerAtPosition(indice).getUniqueWord());
            }
            newPhraseIndice++;
        }
        return newPhrase;
    }


    private boolean endWithLetters(Word wordAtPosition, String s) {
        return wordAtPosition.getInitialValue().endsWith(s);
    }
}

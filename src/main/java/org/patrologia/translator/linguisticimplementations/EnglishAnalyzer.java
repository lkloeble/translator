package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.modificationlog.EnglishModificationLog;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.casenumbergenre.CaseOperatorContainer;
import patrologia.translator.casenumbergenre.NullCase;
import patrologia.translator.conjugation.Conjugation;
import patrologia.translator.rule.english.EnglishRuleFactory;
import patrologia.translator.utils.Analizer;
import patrologia.translator.utils.PhraseAnalizer;
import patrologia.translator.utils.WordAnalyzer;
import patrologia.translator.utils.WordSplitter;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class EnglishAnalyzer implements Analizer {

    private WordSplitter wordSplitter = new WordSplitter();
    private WordAnalyzer wordAnalyzer;
    private PhraseAnalizer phraseAnalizer = new PhraseAnalizer();
    private VerbRepository verbRepository;
    private CaseOperatorContainer caseOperatorContainer;

    private List<String> verbWithInfinitive = new ArrayList<>();

    public EnglishAnalyzer(PrepositionRepository prepositionRepository, NounRepository nounRepository, VerbRepository verbRepository) {
        wordAnalyzer = new WordAnalyzer(prepositionRepository, nounRepository, verbRepository, new EnglishPhraseChanger(nounRepository, new EnglishRuleFactory()), new EnglishModificationLog(), new CustomRule(), new CaseOperatorContainer(nounRepository,prepositionRepository), Language.ENGLISH);
        this.verbRepository = verbRepository;
        this.caseOperatorContainer = caseOperatorContainer;
        verbWithInfinitive.add("can");
        verbWithInfinitive.add("shall");
    }

    @Override
    public Analysis analyze(String sentence) {
        Phrase phrase = wordSplitter.splitSentence(handleSpecialChars(sentence), Language.ENGLISH, new EnglishWordSplitterPattern());
        phrase = splitVerbNegationIntoSingleWord(phrase);
        phrase = replaceCommonExpressionByCustomPreposition(phrase);
        phrase = wordAnalyzer.affectAllPossibleInformations(phrase);
        Phrase would1 = replaceWouldVerbalConstruction(phrase);
        phrase = handleToBeForVerbEndingWithIng(would1);
        phrase = affectConjugationWithVerbIs(phrase);
        Analysis analysis = phraseAnalizer.affectAllPossibleInformationsBetweenWords(Language.ENGLISH, phrase);
        Phrase addRuleForPluralNoun = addRuleForPluralNoun(analysis.getPhrase());
        Phrase locateAndAnalyzeHer = locateAndAnalyzeHer(addRuleForPluralNoun);
        Phrase electInfinitiveVerbForSomeVerbs = electInfinitiveVerbForSomeVerbs(locateAndAnalyzeHer);
        Phrase replaceComposedVerb = findAndAgregateComposedVerbs(electInfinitiveVerbForSomeVerbs);
        Phrase replaceDoVerbalConstruction = replaceDoVerbalConstruction(replaceComposedVerb);
        Phrase replaceWouldVerbalConstruction = replaceWouldVerbalConstruction(replaceDoVerbalConstruction);
        Phrase replaceWillVerbalConstruction = replaceWillVerbalConstruction(replaceWouldVerbalConstruction);
        Phrase replaceLikeVerbalPresentParticipleConstruction = replaceLikeVerbalConstruction(replaceWillVerbalConstruction);
        Phrase affectPAPConjugationForPrecedingHaveAndBeVerbs = findPAPConjugationFOrm(replaceLikeVerbalPresentParticipleConstruction);
        return phraseAnalizer.affectAllPossibleInformationsBetweenWords(Language.ENGLISH, affectPAPConjugationForPrecedingHaveAndBeVerbs);
    }

    private Phrase findPAPConjugationFOrm(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        for(int indice : indices) {
            WordContainer currentContainer = phrase.getWordContainerAtPosition(indice);
            WordContainer followingContainer = phrase.getWordContainerAtPosition(indice + 1);
            String currentWord = currentContainer.getInitialValue();
            String followingWord = followingContainer.getInitialValue();
            if(!verbRepository.hasVerb(currentWord)&& verbRepository.hasVerb(followingWord)) continue;
            Verb currentVerb = verbRepository.getVerb(currentWord);
            if(!currentVerb.getRoot().equals("have") && !currentVerb.getRoot().equals("be")) continue;
            Verb followingVerb = verbRepository.getVerb(followingWord);
            followingVerb.setInitialValue(followingWord);
            if(verbRepository.isConjugation(followingVerb,"PAP")) {
                followingVerb.setConjugation("PAP");
                WordContainer newWordContainer = new WordContainer(followingVerb,indice+1,Language.ENGLISH);
                phrase.addWordContainerAtPosition(indice+1, newWordContainer, phrase);
            }
        }
        return phrase;
    }

    private String handleSpecialChars(String sentence) {
        return sentence.replace("ï","i")
                .replace("A.D.","adabrev")
                .replace(":"," : ")
                .replace("ë","e");

    }

    private Phrase affectConjugationWithVerbIs(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        for(Integer indice : indices) {
            WordContainer currentWordContainer = phrase.getWordContainerAtPosition(indice);
            if(currentWordContainer.getWordByType(WordType.VERB).isTypeUnknow()) continue;
            Verb currentVerb = (Verb)currentWordContainer.getWordByType(WordType.VERB);
            if(!currentVerb.getRoot().equals("be")) continue;
            WordContainer nextWordContainer = phrase.getWordContainerAtPosition(indice+1);
            if(nextWordContainer.getWordByType(WordType.VERB).isTypeUnknow()) continue;
            Verb nextVerb = (Verb)nextWordContainer.getWordByType(WordType.VERB);
            if(nextVerb.getInitialValue().endsWith("ed")) {
                nextVerb.setConjugation(Conjugation.PAST_PARTICIPE);
            }
            nextVerb.addForbiddenConjugation(Conjugation.ACTIVE_IMPERATIVE_PRESENT);
            nextVerb.addForbiddenConjugation(Conjugation.INFINITIVE);
        }
        return phrase;
    }

    private Phrase replaceWouldVerbalConstruction(Phrase phrase) {
        return replaceVerbalConstructionWithEndModifier(phrase, "would");
    }

    private Phrase replaceWillVerbalConstruction(Phrase phrase) {
        return replaceVerbalConstructionWithEndModifier(phrase, "will");
    }

    private Phrase replaceVerbalConstructionWithEndModifier(Phrase phrase, String rootVerb) {
        return replaceVerbalConstruction(phrase, rootVerb, rootVerb);
    }

    private Phrase replaceLikeVerbalConstruction(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        for(Integer indice : indices) {
            Word word = phrase.getWordContainerAtPosition(indice).getWordByType(WordType.VERB);
            if(word == null || word.isTypeUnknow()) continue;
            Verb verb = (Verb)word;
            if(!verb.getRoot().equals("like")) continue;
            WordContainer nextWordContainer = phrase.getWordContainerAtPosition(indice + 1);
            Word nextWord = nextWordContainer.getWordByType(WordType.VERB);
            if(nextWord == null || nextWord.isTypeUnknow()) continue;
            Verb nextVerb = (Verb)nextWord;
            if(nextVerb.getInitialValue().endsWith("ing")) {
                String infinitiveForm = nextVerb.getRoot();
                nextWordContainer.updateInitialValue("to " + infinitiveForm);
            }
        }
        return phrase;
    }

    //TODO : gérer le cas ou l'on a plusieurs verbes composés dans la même phrase
    //TODO : gérer le cas d'un verbe conjugué pour reporter la conjugaison sur la fin : brings forth => bringforths
    private Phrase findAndAgregateComposedVerbs(Phrase phrase) {
        HashMap<String,String> composedVerbMap = new HashMap();
        composedVerbMap.put("bring","forth");
        composedVerbMap.put("get","up");
        composedVerbMap.put("shall","be");
        Set<Integer> integers = phrase.keySet();
        int verbPosition = 0;
        for(Integer indice : integers) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            Word verbWord = wordContainer.getWordByType(WordType.VERB);
            if(!verbWord.isVerb()) continue;
            Verb verb = (Verb) verbWord;
            if(verb != null && composedVerbMap.containsKey(verb.getRoot())) {
                String possibleCompositionWord = composedVerbMap.get(verb.getRoot());
                WordContainer nextWordContainer = phrase.getWordContainerAtPosition(indice + 1);
                String nextWordInitialValue = nextWordContainer.getInitialValue();
                if(possibleCompositionWord.equals(nextWordInitialValue)) {
                    verbPosition = indice;
                    String verbValueWithComposition = verb.getInitialValue() + possibleCompositionWord;
                    wordContainer.modifyContentByPatternReplacement(verb.getInitialValue(),verbValueWithComposition);
                    verb.updateRoot(verbRepository.getVerb(verbValueWithComposition).getRoot());
                    phrase.addWordContainerAtPosition(verbPosition, wordContainer, phrase);
                    nextWordContainer.updateInitialValue(Word.WORD_TO_REMOVE);
                    phrase.addWordContainerAtPosition(verbPosition+1,nextWordContainer,phrase);
                }
            }
        }
        return phrase;
    }

    private Phrase replaceDoVerbalConstruction(Phrase phrase) {
        return replaceVerbalConstruction(phrase,"do","");
    }

    private Phrase replaceVerbalConstruction(Phrase phrase, String rootVerb, String endConjugationModifier) {
        List<WordContainer> possibleVerbs = phrase.getWordsByType(WordType.VERB);
        if(possibleVerbs == null ||  possibleVerbs.size() == 0) return phrase;
        boolean hasSearchedVerb = false;
        int verbPosition = 0;
        int verbTranslationPosition = -1;
        String formerInitialValue = "";
        for(WordContainer wordContainer : possibleVerbs) {
            Verb verb = (Verb)wordContainer.getWordByType(WordType.VERB);
            if(verb.getRoot().equals(rootVerb) && !verb.getInitialValue().equals("willd")) {
                hasSearchedVerb = true;
                verbPosition = wordContainer.getPosition();
                verbTranslationPosition = verb.getPositionInTranslationTable();
                formerInitialValue = verb.getInitialValue();
                break;
            }
        }
        if(!hasSearchedVerb) return phrase;
        if(hasSearchedVerb && possibleVerbs.size() == 1) return phrase;
        boolean verbIsNearOtherVerb = false;
        int otherVerbPositionToCorrect = 0;
        for(WordContainer wordContainer : possibleVerbs) {
            int otherVerbPosition = wordContainer.getPosition();
            if(otherVerbPosition > verbPosition && otherVerbPosition - verbPosition < 4) {
                verbIsNearOtherVerb = true;
                otherVerbPositionToCorrect = otherVerbPosition;
            }
        }
        if(!verbIsNearOtherVerb) return phrase;
        if(thereIsAPointBetweenTwoPositions(phrase,verbPosition,otherVerbPositionToCorrect)) return phrase;
        WordContainer otherVerbWordContainer = phrase.getWordContainerAtPosition(otherVerbPositionToCorrect);
        Verb otherVerb = (Verb)otherVerbWordContainer.getWordByType(WordType.VERB);
        otherVerb.setPositionInTranslationTable(verbTranslationPosition);
        String newInitialValue = verbRepository.getEquivalentForOtherRoot(otherVerb.getRoot(), formerInitialValue, rootVerb, verbTranslationPosition);
        if(endConjugationModifier.length() == 0) {
            //phrase.modifyWordContenAndRootAtPosition(newInitialValue + endConjugationModifier, verbPosition);
            phrase.modifyOnlyRootAtPosition(otherVerb.getRoot(), verbPosition);
            phrase.updateWordContentAtPosition(newInitialValue + endConjugationModifier, verbPosition);
            phrase.keepOnlyOneWordTypeAtPosition(WordType.VERB,verbPosition);
        } else {
            phrase.modifyOnlyRootAtPosition(otherVerb.getRoot(), verbPosition);
            phrase.updateWordContentAtPosition(newInitialValue + endConjugationModifier, verbPosition);
            phrase.keepOnlyOneWordTypeAtPosition(WordType.VERB, verbPosition);
        }
        phrase.updateWordContentAtPosition(Word.WORD_TO_REMOVE,otherVerbPositionToCorrect);
        return phrase;
    }

    private boolean thereIsAPointBetweenTwoPositions(Phrase phrase, int verbPosition, int otherVerbPositionToCorrect) {
        return phrase.hasInitialValueBetweenPositions(".",verbPosition,otherVerbPositionToCorrect);
    }


    private Phrase handleToBeForVerbEndingWithIng(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            Word currentWord = phrase.getYetUnknownWordAtPosition(indice);
            Word followingWord = phrase.getYetUnknownWordAtPosition(indice+1);
            if(currentWord.isVerb() && followingWord.isVerb() && followingWord.getInitialValue().endsWith("ing"))  {
                if(!currentWord.getRoot().equals("be")) continue;
                Verb followingVerb = (Verb)followingWord;
                Verb currentVerb = (Verb)currentWord;
                TranslationInformationBean translationInformationBean = verbRepository.getAllFormsForTheVerbRoot("be");
                List<String> constructionNameForInitialValueList = translationInformationBean.getConstructionNameForInitialValue(currentVerb.getInitialValue(), verbRepository.getInfinitiveBuilder());
                Collections.sort(constructionNameForInitialValueList);
                String constructionName = constructionNameForInitialValueList.get(0);
                List<Integer> possibleTranlationPositions = translationInformationBean.getPossibleTranslationPositions(currentVerb.getInitialValue(),constructionName, "be");
                int correctPosition = possibleTranlationPositions.size() == 1 ? possibleTranlationPositions.get(0) :  Language.ENGLISH.getDefaultPositionInTranslationTableVerb();
                followingVerb.setConjugation(constructionName);
                followingVerb.setPositionInTranslationTable(correctPosition);
                followingVerb.updateInitialValue(verbRepository.getEquivalentForOtherRoot(followingVerb.getRoot(), currentVerb.getInitialValue(), "be", correctPosition));
                currentWord.updateInitialValue(Word.WORD_TO_REMOVE);
            }
        }
        return phrase;
    }

    private Phrase replaceCommonExpressionByCustomPreposition(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            String currentValue = phrase.getWordContainerAtPosition(indice).getInitialValue();
            String nextValue = phrase.getWordContainerAtPosition(indice+1).getInitialValue();
            if("i've".equals(currentValue)  && "got".equals(nextValue))  {
                Word nextWord = phrase.getWordContainerAtPosition(indice+1).getUniqueWord();
                nextWord.updateInitialValue(Word.WORD_TO_REMOVE);
            }
        }
        return phrase;
    }

    private Phrase electInfinitiveVerbForSomeVerbs(Phrase phrase) {
        Set<String> betweenVerbs = new HashSet<>();
        betweenVerbs.add("not");
        betweenVerbs.add("surely");
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            Word word = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            if(word.isVerb() && verbWithInfinitive.contains(word.getRoot())) {
                Word followingWord = phrase.getWordContainerAtPosition(indice+1).getUniqueWord();
                if(betweenVerbs.contains(followingWord.getInitialValue())) {
                    followingWord = phrase.getWordContainerAtPosition(indice+2).getUniqueWord();
                }
                if(followingWord.isVerb()) {
                    Verb followingVerb = (Verb)followingWord;
                    followingVerb.setConjugation(Conjugation.INFINITIVE);
                    followingVerb.updateInitialValue("to " + followingVerb.getInitialValue());
                }
            }
        }
        return phrase;
    }

    private Phrase splitVerbNegationIntoSingleWord(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int nbVerbWithNt = 0;
        for(Integer indice : integers) {
            String wordContent = phrase.getWordContainerAtPosition(indice).getInitialValue();
            if(wordContent.endsWith("n't")) nbVerbWithNt++;
        }
        if(nbVerbWithNt == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size()+nbVerbWithNt, Language.ENGLISH);
        int position = 1;
        for(Integer indice : integers) {
            String initialValue = phrase.getWordContainerAtPosition(indice).getInitialValue();
            if(initialValue.endsWith("n't")) {
                String verbValue = initialValue.substring(0, initialValue.length() - 3);
                if(!verbRepository.hasVerb(verbValue)) verbValue = initialValue.substring(0, initialValue.length() - 2);
                Verb verb = new Verb(verbValue, verbValue, Language.ENGLISH);
                verb.addForbiddenConjugation(Conjugation.INFINITIVE);
                verb.addForbiddenConjugation(Conjugation.ACTIVE_IMPERATIVE_PRESENT);
                WordContainer verbCorrected  = new WordContainer(verb, position,Language.ENGLISH);
                WordContainer negation  = new WordContainer(new Preposition(Language.ENGLISH, "ntendverb", new NullCase()), position,Language.ENGLISH);
                newPhrase.addWordContainerAtPosition(position++, verbCorrected, newPhrase);
                newPhrase.addWordContainerAtPosition(position++, negation, newPhrase);
            } else {
                newPhrase.addWordContainerAtPosition(position++,phrase.getWordContainerAtPosition(indice),newPhrase);
            }
        }
        return newPhrase;
    }

    private Phrase locateAndAnalyzeHer(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for (Integer indice : integers) {
            if(phrase.getWordContainerAtPosition(indice).getInitialValue().toLowerCase().equals("her")) {
                WordContainer next = phrase.getWordContainerAtPosition(indice+1);
                if(next.getUniqueWord().isNoun()) {
                    Preposition her = (Preposition)phrase.getWordContainerAtPosition(indice).getUniqueWord();
                    her.setDistinctiveTranslationByNumber();
                }
            }
        }
        return phrase;
    }


    private Phrase addRuleForPluralNoun(Phrase phrase) {
        if(!phrase.containsWordWithContent("are") || !phrase.containsWordWithContent("the")) return phrase;
        Phrase newPhrase = new Phrase(phrase.size(),Language.ENGLISH);
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers)  {
            Word word = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            if(word.isPreposition() && word.getInitialValue().equals("the")) {
                Preposition the = (Preposition)word;
                if(the.isDoubleNumberTranslation()) {
                    Verb are = (Verb)phrase.getWordByContent("are").getUniqueWord();
                    are.setPluralKnown(true);
                    newPhrase.addWordAtPosition(phrase.getIndiceByContent("are"), are);
                    newPhrase.addWordAtPosition(indice, the);
                    continue;
                }
            }
            newPhrase.addWordAtPosition(indice, word);
        }
        return newPhrase;
    }
}

package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.modificationlog.ModificationLog;
import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.basicelements.preposition.PrepositionRepository;
import org.patrologia.translator.basicelements.verb.Verb;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.casenumbergenre.*;
import org.patrologia.translator.casenumbergenre.hebrew.*;
import org.patrologia.translator.rule.RuleFactory;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewPhraseChanger extends CustomLanguageRulePhraseChanger {

    private List<String> stopWords = new ArrayList<String>();
    private List<String> stopWordsKSofit = new ArrayList<String>();
    private List<String> stopWordsVerbHeSofit = new ArrayList<String>();
    private List<String> stopWordsWavSofit = new ArrayList<String>();
    private List<String> stopWordsNounWavSofit = new ArrayList<String>();
    private List<String> stopNounsWithEndingHe = new ArrayList<String>();
    private List<String> stopNounsWithEndingMem = new ArrayList<String>();
    private List<String> stopNounsWithEndingNoun = new ArrayList<String>();
    private List<String> stopNounsWithEndingTavNoun = new ArrayList<String>();
    private List<String> stopNounsWithEndingYod = new ArrayList<String>();
    private List<String> stopWordsOneSofit = new ArrayList<String>();
    private NounRepository nounRepository;
    private VerbRepository verbRepository;
    private PrepositionRepository prepositionRepository;
    private RuleFactory ruleFactory;
    private Accentuer accentuer = new Accentuer();

    private static final String NO_FOLLOWING_INTERRUPTION_VALUE = "XXXXXXX";

    public HebrewPhraseChanger(NounRepository nounRepository, VerbRepository verbRepository, PrepositionRepository prepositionRepository,  RuleFactory ruleFactory) {
        this.nounRepository = nounRepository;
        this.verbRepository = verbRepository;
        this.prepositionRepository = prepositionRepository;
        this.ruleFactory = ruleFactory;

        this.language = Language.HEBREW;

        stopWords.addAll(nounRepository.getNounsValueStartingWith("h"));
        stopWords.addAll(verbRepository.getValuesStartingWith("h"));
        stopWords.addAll(prepositionRepository.getValuesStartingWith("h"));

        stopWords.addAll(prepositionRepository.getValuesStartingWith("w"));

        stopWords.addAll(nounRepository.getNounsValueStartingWith("m"));
        stopWords.addAll(verbRepository.getValuesStartingWith("m"));
        stopWords.addAll(prepositionRepository.getValuesStartingWith("m"));

        stopWords.addAll(nounRepository.getNounsValueStartingWith("l"));
        stopWords.addAll(verbRepository.getValuesStartingWith("l"));
        stopWords.addAll(prepositionRepository.getValuesStartingWith("l"));

        stopWords.addAll(nounRepository.getNounsValueStartingWith("b"));
        stopWords.addAll(verbRepository.getValuesStartingWith("b"));
        stopWords.addAll(prepositionRepository.getValuesStartingWith("b"));

        stopWords.addAll(nounRepository.getNounsValueStartingWith("k"));
        stopWords.addAll(verbRepository.getValuesStartingWith("k"));
        stopWords.addAll(prepositionRepository.getValuesStartingWith("k"));

        stopWords.addAll(nounRepository.getNounsValueStartingWith("s"));
        stopWords.addAll(verbRepository.getValuesStartingWith("s"));
        stopWords.addAll(prepositionRepository.getValuesStartingWith("s"));

        stopWordsKSofit.add("'hsk");
        stopWordsKSofit.add("mlk");
        stopWordsKSofit.add("hlk");
        stopWordsKSofit.add("k");
        stopWordsKSofit.add("lk");
        stopWordsKSofit.add("drk");
        stopWordsKSofit.add("kmwk");
        stopWordsVerbHeSofit.add("rah");
        stopWordsVerbHeSofit.add("lq'h");
        stopWordsVerbHeSofit.add("ysh");
        stopWordsVerbHeSofit.add("sl'h");
        stopWordsVerbHeSofit.add("hith");
        stopWordsVerbHeSofit.add("hnh");
        stopWordsWavSofit.add("w");
        stopWordsWavSofit.add("lw");
        stopWordsWavSofit.add("ysw");
        stopWordsWavSofit.add("thw");
        stopWordsWavSofit.add("bhw");
        stopWordsWavSofit.addAll(verbRepository.getValuesEndingWith("w"));
        //stopWordsWavSofit.addAll(verbRepository.getAllFormsForRoot(stopWordsWavSofit,null));
        stopWordsWavSofit.addAll(prepositionRepository.getValuesEndingWith("w"));
        stopWordsNounWavSofit.addAll(nounRepository.getNounsRootValueForEndingWith("nw"));
        stopWordsNounWavSofit.add("minw");
        stopWordsNounWavSofit.addAll(prepositionRepository.getValuesEndingWith("nw"));
        stopWordsNounWavSofit.addAll(verbRepository.getValuesEndingWith("nw"));
        //stopWords = verbRepository.getAllFormsForRoot(stopWords,null);

        stopNounsWithEndingHe.add("h");
        stopNounsWithEndingHe.addAll(nounRepository.getNounsValueForEndingWith("h"));
        stopNounsWithEndingHe.addAll(verbRepository.getValuesEndingWith("h"));
        stopNounsWithEndingHe.addAll(prepositionRepository.getValuesEndingWith("h"));

        stopNounsWithEndingMem.addAll(nounRepository.getNounsRootValueForEndingWith("m000"));
        stopNounsWithEndingMem.addAll(nounRepository.getNounsValueForEndingWith("im000"));
        stopNounsWithEndingMem.addAll(prepositionRepository.getValuesEndingWith("m000"));

        stopNounsWithEndingNoun.addAll(nounRepository.getNounsRootValueForEndingWith("n000"));
        stopNounsWithEndingNoun.addAll(verbRepository.getValuesEndingWith("n000"));
        stopNounsWithEndingNoun.addAll(prepositionRepository.getValuesEndingWith("n000"));

        stopNounsWithEndingTavNoun.addAll(nounRepository.getNounsRootValueForEndingWith("tn"));
        stopNounsWithEndingTavNoun.addAll(prepositionRepository.getValuesEndingWith("tn"));

        stopNounsWithEndingYod.addAll(nounRepository.getNounsRootValueForEndingWith("i"));
        //stopNounsWithEndingYod.addAll(nounRepository.getNounsValueForEndingWith("i"));
        stopNounsWithEndingYod.addAll(prepositionRepository.getValuesEndingWith("i"));
        stopNounsWithEndingYod.addAll(verbRepository.getValuesEndingWith("ti"));
        stopNounsWithEndingYod.add("pni");//ajouter les états construits

        //stopWordsOneSofit.addAll(nounRepository.getNounsRootValueForEndingWith("1"));//one is a  pattern for minus
    }

    @Override
    public Phrase modifyPhrase(Phrase startPhrase, ModificationLog modificationLog, CustomRule customRule) {
        CaseOperatorContainer caseOperatorContainer = new CaseOperatorContainer(nounRepository,prepositionRepository);
        CaseOperator keepDeclinedDirectionalState  = new KeepCaseOperator(new HebrewConstructedStateCase("cst"));
        caseOperatorContainer.addCaseOperator(keepDeclinedDirectionalState);
        Phrase withoutOneWhichIsMinusForUnknowConstructedState = substituteEndPatternWithNewPrepositionAfterWord(startPhrase, "&", new Preposition(Language.HEBREW, "xxdexx", null), stopWordsOneSofit, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);
        Phrase erasedPatternOfMinus = erasePatternInWords(withoutOneWhichIsMinusForUnknowConstructedState, "&","#");
        caseOperatorContainer.emptyCases();
        Phrase withoutWav56 = extractLetterFromBeginningOfNoun(erasedPatternOfMinus, "w56", stopWords, ruleFactory, null);
        Phrase withoutWav63 = extractLetterFromBeginningOfNoun(withoutWav56, "w63", stopWords, ruleFactory, null);
        Phrase withoutWav309 = extractLetterFromBeginningOfNoun(withoutWav63, "w309", stopWords, ruleFactory, null);
        Phrase withoutWav = extractLetterFromBeginningOfNoun(withoutWav309, "w", stopWords, ruleFactory, null);
        Phrase withoutLamed56 = extractLetterFromBeginningOfNoun(withoutWav, "l56", stopWords, ruleFactory, null);
        Phrase withoutLamedPreposition = extractLetterFromBeginningOfNoun(withoutLamed56, "l", stopWords, ruleFactory, null);
        Phrase withoutMinPreposition = extractLetterFromBeginningOfNoun(withoutLamedPreposition, "m", stopWords, ruleFactory, null);
        Phrase withoutKe56Preposition = extractLetterFromBeginningOfNoun(withoutMinPreposition, "k56", stopWords, ruleFactory, null);
        Phrase withoutKe31559Preposition = extractLetterFromBeginningOfNoun(withoutKe56Preposition, "k31559", stopWords, ruleFactory, null);
        Phrase withoutKe31560Preposition = extractLetterFromBeginningOfNoun(withoutKe31559Preposition, "k31560", stopWords, ruleFactory, null);
        Phrase withoutKe31563Preposition = extractLetterFromBeginningOfNoun(withoutKe31560Preposition, "k31563", stopWords, ruleFactory, null);
        Phrase withoutKe31564Preposition = extractLetterFromBeginningOfNoun(withoutKe31563Preposition, "k31564", stopWords, ruleFactory, null);
        Phrase withoutKe3156369Preposition = extractLetterFromBeginningOfNoun(withoutKe31564Preposition, "k3156369", stopWords, ruleFactory, null);
        Phrase withoutKe3156469Preposition = extractLetterFromBeginningOfNoun(withoutKe3156369Preposition, "k3156469", stopWords, ruleFactory, null);
        Phrase withoutKePreposition = extractLetterFromBeginningOfNoun(withoutKe3156469Preposition, "k", stopWords, ruleFactory, null);
        Phrase withoutBeth30556 = extractLetterFromBeginningOfNoun(withoutKePreposition, "b30556", stopWords, ruleFactory, null);
        Phrase withoutBeth30558 = extractLetterFromBeginningOfNoun(withoutBeth30556, "b30558", stopWords, ruleFactory, null);
        Phrase withoutBeth30564 = extractLetterFromBeginningOfNoun(withoutBeth30558, "b30564", stopWords, ruleFactory, null);
        Phrase withoutBeth56 = extractLetterFromBeginningOfNoun(withoutBeth30564, "b56", stopWords, ruleFactory, null);
        Phrase withoutBeth63 = extractLetterFromBeginningOfNoun(withoutBeth56, "b63", stopWords, ruleFactory, null);
        Phrase withoutBethPreposition = extractLetterFromBeginningOfNoun(withoutBeth63, "b", stopWords, ruleFactory, null);
        Phrase withoutShinAsPreposition = extractLetterFromBeginningOfNoun(withoutBethPreposition, "s", stopWords, ruleFactory, null);
        Phrase withoutHe59 = extractLetterFromBeginningOfNoun(withoutShinAsPreposition, "h6269", stopWords, ruleFactory, "articleCopyFollowingNoun");
        Phrase withoutHe6269 = extractLetterFromBeginningOfNoun(withoutHe59, "h59", stopWords, ruleFactory, "articleCopyFollowingNoun");
        Phrase withoutHe6369 = extractLetterFromBeginningOfNoun(withoutHe6269, "h6369", stopWords, ruleFactory, "articleCopyFollowingNoun");
        Phrase withoutHe6469 = extractLetterFromBeginningOfNoun(withoutHe6369, "h6469", stopWords, ruleFactory, "articleCopyFollowingNoun");
        Phrase withoutHe63 = extractLetterFromBeginningOfNoun(withoutHe6469, "h63", stopWords, ruleFactory, "articleCopyFollowingNoun");
        Phrase withoutHe69 = extractLetterFromBeginningOfNoun(withoutHe63, "h69", stopWords, ruleFactory, "articleCopyFollowingNoun");
        Phrase withoutHe64 = extractLetterFromBeginningOfNoun(withoutHe69, "h64", stopWords, ruleFactory, "articleCopyFollowingNoun");
        Phrase withoutHe = extractLetterFromBeginningOfNoun(withoutHe64, "h", stopWords, ruleFactory, "articleCopyFollowingNoun");

        OperatorCombination operatorCombination = new OperatorCombination(new NominativeHebrewCase("nom"),false,5);
        operatorCombination.addOperator(new AvoidCaseOperator(new NominativeHebrewCase("nom")));
        operatorCombination.addOperator(new KeepCaseOperator(new HebrewDeclinedNominativeCase("dec")));
        caseOperatorContainer.addCaseOperator(operatorCombination);
        Phrase withoutNounWavForEndingNounWithNw309PrepOur = substituteEndPatternWithNewPrepositionAfterWord(withoutHe, "nw309", new Preposition(Language.HEBREW,"xxnwxx", null), stopWordsNounWavSofit, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);
        Phrase withoutNounWavForEndingNounWithNwPrepOur = substituteEndPatternWithNewPrepositionAfterWord(withoutNounWavForEndingNounWithNw309PrepOur, "nw", new Preposition(Language.HEBREW,"xxnwxx", null), stopWordsNounWavSofit, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);

        Phrase withoutKeSofit64Preposition = substituteEndPatternWithNewPrepositionAfterWord(withoutNounWavForEndingNounWithNwPrepOur, "k00064", new Preposition(Language.HEBREW,"ksofit", null), stopWordsKSofit, NO_FOLLOWING_INTERRUPTION_VALUE,caseOperatorContainer);
        Phrase withoutKeSofitPreposition = substituteEndPatternWithNewPrepositionAfterWord(withoutKeSofit64Preposition, "k000", new Preposition(Language.HEBREW,"ksofit", null), stopWordsKSofit, NO_FOLLOWING_INTERRUPTION_VALUE,caseOperatorContainer);

        operatorCombination.clearAll();
        operatorCombination.addOperator(new AvoidCaseOperator(new NominativeHebrewCase("nom")));
        operatorCombination.addOperator(new KeepCaseOperator(new HebrewDeclinedNominativeCase("dec")));
        caseOperatorContainer.emptyCases();
        caseOperatorContainer.addCaseOperator(operatorCombination);
        Phrase withoutEndingHeAndWav = substituteEndPatternWithNewPrepositionAfterWord(withoutKeSofitPreposition, "hw", new Preposition(Language.HEBREW,"xxheandwav",null), stopWordsWavSofit, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);
        Phrase withoutEndingWav331 = substituteEndPatternWithNewPrepositionAfterWord(withoutEndingHeAndWav, "w331", new Preposition(Language.HEBREW,"wavend",null), stopWordsWavSofit, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);
        Phrase withoutEndingWav = substituteEndPatternWithNewPrepositionAfterWord(withoutEndingWav331, "w", new Preposition(Language.HEBREW,"wavend",null), stopWordsWavSofit, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);

        Phrase withoutEndingYod60 = substituteEndPatternWithNewPrepositionAfterWord(withoutEndingWav, "i60", new Preposition(Language.HEBREW,"xxixx",null), stopNounsWithEndingYod, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);
        Phrase withoutEndingYod = substituteEndPatternWithNewPrepositionAfterWord(withoutEndingYod60, "i", new Preposition(Language.HEBREW,"xxixx",null), stopNounsWithEndingYod, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);
        Phrase withoutEndingMem = substituteEndPatternWithNewPrepositionAfterWord(withoutEndingYod, "m000", new Preposition(Language.HEBREW,"xxmxx",null), stopNounsWithEndingMem, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);
        Phrase withoutEndingTavNoun = substituteEndPatternWithNewPrepositionAfterWord(withoutEndingMem, "tn000", new Preposition(Language.HEBREW,"xxtnxx",null), stopNounsWithEndingTavNoun, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);
        Phrase withoutEndingNoun = substituteEndPatternWithNewPrepositionAfterWord(withoutEndingTavNoun, "n000", new Preposition(Language.HEBREW,"xxnxx",null), stopNounsWithEndingNoun, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);

        operatorCombination.clearAll();
        operatorCombination.addOperator(new AvoidCaseOperator(new NominativeHebrewCase("nom")));
        operatorCombination.addOperator(new AvoidCaseOperator(new HebrewDeclinedNominativeCase("dec")));
        caseOperatorContainer.addCaseOperator(operatorCombination);
        Phrase withoutNounsEndingDirectionnalHe = substituteEndPatternWithNewPrepositionBeforeWord(withoutEndingNoun, "h", new Preposition(Language.HEBREW, "xxdirectionalhexx", null), stopNounsWithEndingHe, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);

        operatorCombination.clearAll();
        operatorCombination.addOperator(new AvoidCaseOperator(new NominativeHebrewCase("nom")));
        operatorCombination.addOperator(new KeepCaseOperator(new HebrewDeclinedNominativeCase("dec")));
        caseOperatorContainer.emptyCases();
        caseOperatorContainer.addCaseOperator(operatorCombination);
        Phrase withoutNounsEndingHe = substituteEndPatternWithNewPrepositionAfterWord(withoutNounsEndingDirectionnalHe, "h", new Preposition(Language.HEBREW, "xxhexx", null), stopNounsWithEndingHe, NO_FOLLOWING_INTERRUPTION_VALUE, caseOperatorContainer);

        return withoutNounsEndingHe;
    }

    private Phrase substituteEndPatternWithNewPrepositionBeforeWord(Phrase phrase, String endingPattern, Preposition preposition, List<String> stopNouns, String valueFollowingWordInterruption, CaseOperatorContainer caseOperatorContainer) {
        Set<Integer> integers = phrase.keySet();
        int numberOfEndingPattern = 0;
        for(Integer indice : integers) {
            if(valueFollowingWordInterruption.equals(phrase.getYetUnknownWordAtPosition(indice+1).getInitialValue())) continue;
            WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
            numberOfEndingPattern += endsWithPattern(wordContainerAtPosition, endingPattern, stopNouns,accentuer) && caseOperatorContainer.isCompliantToOneOfCaseOperator(wordContainerAtPosition) ? 1 : 0;
        }
        if(numberOfEndingPattern == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfEndingPattern,Language.HEBREW);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
            if(endsWithPattern(wordContainerAtPosition, endingPattern, stopNouns,accentuer)) {
                List<ResultCaseOperator> resultCaseOperatorList = caseOperatorContainer.getResultCaseOperatorList();
                Word newWordWithoutEndingPattern = wordWithoutEndingPattern(wordContainerAtPosition, endingPattern);
                if(isDirectional(newWordWithoutEndingPattern) || isUnknownInRepositories(newWordWithoutEndingPattern)) {
                    newWordWithoutEndingPattern = caseOperatorContainer.substituteWord(wordContainerAtPosition.getInitialValue(),endingPattern, newWordWithoutEndingPattern, indice);
                }
                newPhrase.addWordAtPosition(newPhraseIndice, preposition);
                newPhrase.addWordAtPosition(newPhraseIndice+1, newWordWithoutEndingPattern);
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice, wordContainerAtPosition.getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    private boolean isDirectional(Word word) {
        if(!word.isNoun()) return false;
        Noun noun = (Noun)word;
        return noun.hasPossibleCase(new HebrewDirectionalCase("ection"));
    }

    private Phrase erasePatternInWords(Phrase phrase, String patternToErase, String patternToReplaceIfExists) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            if(!wordContainer.getUniqueWord().getRoot().contains(patternToErase)) continue;
            List<Word> wordSet = wordContainer.getWordSet();
            for(Word word : wordSet) {
                String oldInitialValue = word.getInitialValue();
                String oldRoot = word.getRoot();
                String pattern = "";
                if(getPatternByTestingNounExistence(oldInitialValue, patternToReplaceIfExists)) pattern = patternToReplaceIfExists;
                //value al root al1 | value dbr root dbr1
                String newValue = oldRoot.replace(patternToErase,pattern);
                word.updateInitialValue(newValue);
                word.updateRoot(newValue);
            }
        }
        return phrase;
    }

    private boolean getPatternByTestingNounExistence(String initialValue, String patternOfConstructedState) {
        if(nounRepository.hasNoun(initialValue + patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"b")+patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"m")+patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"l")+patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"k")+patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"w")+patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"wb")+patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"wm")+patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"wl")+patternOfConstructedState)) return true;
        if(nounRepository.hasNoun(eraseLeadingLetter(initialValue,"wk")+patternOfConstructedState)) return true;
        return false;
    }

    private String eraseLeadingLetter(String initialValue, String leadingLetter) {
        if(!initialValue.startsWith(leadingLetter)) return initialValue;
        return initialValue.substring(leadingLetter.length(),initialValue.length());
    }



    private Phrase substituteEndPatternWithNewPrepositionAfterWord(Phrase phrase, String endingPattern, Preposition preposition, List<String> stopWords, String valueFollowingWordInterruption, CaseOperatorContainer caseOperatorContainer) {
        Set<Integer> integers = phrase.keySet();
        int numberOfEndingPattern = 0;
        for(Integer indice : integers) {
            if(valueFollowingWordInterruption.equals(phrase.getYetUnknownWordAtPosition(indice+1).getInitialValue())) continue;
            WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
            numberOfEndingPattern += endsWithPattern(wordContainerAtPosition, endingPattern, stopWords,accentuer) && caseOperatorContainer.isCompliantToOneOfCaseOperator(wordContainerAtPosition) ? 1 : 0;
        }
        if(numberOfEndingPattern == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfEndingPattern,Language.HEBREW);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
             if(endsWithPattern(wordContainerAtPosition, endingPattern, stopWords,accentuer)) {
                List<ResultCaseOperator> resultCaseOperatorList = caseOperatorContainer.getResultCaseOperatorList();
                Word newWordWithoutEndingPattern = wordWithoutEndingPattern(wordContainerAtPosition, endingPattern);
                if(isNotDeclined(newWordWithoutEndingPattern) || isUnknownInRepositories(newWordWithoutEndingPattern)) {
                    newWordWithoutEndingPattern = caseOperatorContainer.substituteWord(wordContainerAtPosition.getInitialValue(),endingPattern, newWordWithoutEndingPattern,indice);
                }
                newPhrase.addWordAtPosition(newPhraseIndice, newWordWithoutEndingPattern);
                newPhrase.addWordAtPosition(newPhraseIndice+1, preposition);
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice, wordContainerAtPosition.getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

    private boolean isNotDeclined(Word newWordWithoutEndingPattern) {
        if(!newWordWithoutEndingPattern.isNoun()) return false;
        Noun noun = (Noun)newWordWithoutEndingPattern;
        List<CaseNumberGenre> possibleCaseNumbers = noun.getPossibleCaseNumbers();
        if(possibleCaseNumbers.size() != 1) return false;
        CaseNumberGenre caseNumberGenre = possibleCaseNumbers.get(0);
        Case aCase = caseNumberGenre.getCase();
        return !aCase.getTrigramForCase().equals("dec");
    }

    private boolean isUnknownInRepositories(Word newWordWithoutEndingPattern) {
        if(nounRepository.getNoun(newWordWithoutEndingPattern.getInitialValue()).size() > 0) return false;
        if(prepositionRepository.getPreposition(newWordWithoutEndingPattern.getInitialValue()) != null) return false;
        Verb verb = verbRepository.getVerb(newWordWithoutEndingPattern.getInitialValue());
        if(!verb.isNullVerb()) return false;
        return true;
    }



    private Word nounWithoutBeginningPattern(WordContainer wordContainer, String leadingPattern) {
        Word modifiedWord = wordContainer.getUniqueWord();
        String newValue  = eraseLeadingAccentuedPattern(wordContainer.getInitialValue(), leadingPattern);
        modifiedWord.updateInitialValue(newValue);
        if(nounRepository.hasNoun(modifiedWord.getInitialValue())) {
            return new Noun(Language.HEBREW,modifiedWord.getInitialValue(), newValue, Collections.EMPTY_LIST, null, null, null, null);
        } else if(prepositionRepository.hasPreposition(modifiedWord.getInitialValue())) {
            return new Preposition(Language.HEBREW,modifiedWord.getInitialValue(),null);
        }
        return modifiedWord;
    }

    private String eraseLeadingAccentuedPattern(String initialValue, String leadingPattern) {
        String possibleSubstring = initialValue.substring(leadingPattern.length());
        if(isLeadingAccentued(possibleSubstring)) {
            possibleSubstring =eraseLeadingAccentuation(possibleSubstring);
        }
        return possibleSubstring;
    }

    private String eraseLeadingAccentuation(String possibleSubstring) {
        int firstLetterPosition =getFirstLetterPosition(possibleSubstring);
        return possibleSubstring.substring(firstLetterPosition);
    }

    private int getFirstLetterPosition(String possibleSubstring) {
        int firstLetterPosition = 0;
        char c = possibleSubstring.charAt(firstLetterPosition);
        while(c <= '9' && c != '\'') {
            c = possibleSubstring.charAt(++firstLetterPosition);
        }
        return firstLetterPosition;
    }

    private boolean isLeadingAccentued(String possibleSubstring) {
        char leadingChar = possibleSubstring.charAt(0);
        return leadingChar >= '0' && leadingChar <= '9';
    }

    private Phrase extractLetterFromBeginningOfNoun(Phrase phrase, String letter, List<String> stopWords, RuleFactory ruleFactory, String ruleName) {
        Set<Integer> integers = phrase.keySet();
        int numberOfStartingLetter = 0;
        for(Integer indice : integers) {
            Word uniqueWord = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            numberOfStartingLetter += startsWithLetter(uniqueWord, letter, Collections.singleton(stopWords), accentuer) ? 1 : 0;
        }
        if(numberOfStartingLetter == 0) {
            return phrase;
        }
        Phrase newPhrase = new Phrase(phrase.size() + numberOfStartingLetter, Language.HEBREW);
        int newPhraseIndice = 1;
        for(Integer indice : integers) {
            if(startsWithLetter(phrase.getWordContainerAtPosition(indice).getUniqueWord(), letter, Collections.singleton(stopWords), accentuer)) {
                Preposition leadingLetter = new Preposition(Language.HEBREW,letter,new NullCase());//gugu
                if(ruleName != null) {
                    leadingLetter.addRule(ruleFactory.getRuleByName(ruleName,leadingLetter.getInitialValue()));
                }
                newPhrase.addWordAtPosition(newPhraseIndice, leadingLetter);
                newPhrase.addWordAtPosition(newPhraseIndice+1, nounWithoutBeginningPattern(phrase.getWordContainerAtPosition(indice), letter));
                newPhraseIndice+=2;
            } else {
                newPhrase.addWordAtPosition(newPhraseIndice,phrase.getWordContainerAtPosition(indice).getUniqueWord());
                newPhraseIndice++;
            }
        }
        return newPhrase;
    }

}

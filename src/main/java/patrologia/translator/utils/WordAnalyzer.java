package patrologia.translator.utils;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.modificationlog.ModificationLog;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.CaseOperatorContainer;
import patrologia.translator.linguisticimplementations.CustomLanguageRulePhraseChanger;
import patrologia.translator.linguisticimplementations.CustomRule;

import java.util.*;

public class WordAnalyzer {

    private PrepositionRepository prepositionRepository;
    private NounRepository nounRepository;
    private VerbRepository2 verbRepository;
    private CustomLanguageRulePhraseChanger customLanguageRulePhraseChanger;
    private ModificationLog modificationLog;
    private Language language;
    private CustomRule customRule;
    private CaseOperatorContainer caseOperatorContainer;
    private Accentuer accentuer = new Accentuer();

    public WordAnalyzer(PrepositionRepository prepositionRepository, NounRepository nounRepository, VerbRepository2 verbRepository, CustomLanguageRulePhraseChanger customLanguageRulePhraseChanger, ModificationLog modificationLog, CustomRule customRule, CaseOperatorContainer caseOperatorContainer, Language language) {
        this.prepositionRepository = prepositionRepository;
        this.nounRepository = nounRepository;
        this.verbRepository = verbRepository;
        this.customLanguageRulePhraseChanger = customLanguageRulePhraseChanger;
        this.modificationLog = modificationLog;
        this.language = language;
        this.customRule = customRule;
        this.caseOperatorContainer = caseOperatorContainer;
    }

    public Phrase affectAllPossibleInformationsWithAnotherRule(Phrase phrase, CustomRule customRule) {
        this.customRule = customRule;
        return affectAllPossibleInformations(phrase);
    }

    public Phrase affectAllPossibleInformations(Phrase words) {
        if(words == null || Collections.EMPTY_MAP.equals(words)) {
            return null;
        }
        words = customLanguageRulePhraseChanger.modifyPhrase(words, modificationLog,customRule);
        Phrase result = new Phrase(words.size(),language);
        Set<Integer> indices = words.keySet();
        for(Integer indice : indices) {
            WordContainer wordContainer = words.getWordContainerAtPosition(indice);
            List<Word> wordSet = wordContainer.getWordSet();
            for(Word unknownYet : wordSet) {
                if(unknownYet == null) continue;
                String initialValue = unknownYet.getInitialValue();
                if(unknownYet.isPreposition() || ((prepositionRepository.hasPreposition(initialValue) && (unknownYet.isTypeUnknow())))) {
                    Preposition preposition = prepositionRepository.getPreposition(initialValue);
                    preposition.addRules(unknownYet.getRules());
                    preposition.setPreferedTranslation(unknownYet.getPreferedTranslation());
                    result.addWordAtPosition(indice, preposition);
                }
                if(unknownYet.isNoun() || unknownYet.isTypeUnknow() && nounRepository.hasNoun(initialValue)) {
                    Collection<Noun> nouns = nounRepository.getNoun(initialValue);
                    Collection<Word> wordsToAdd = new ArrayList<>();
                    for(Noun noun : nouns) {
                        if(!caseOperatorContainer.isCompliantToOneOfCaseOperator(noun)) continue;
                        noun.addRules(unknownYet.getRules());
                        noun.setPreferedTranslation(unknownYet.getPreferedTranslation());
                        noun.setPreferedElected(unknownYet.getPreferedElected());
                        if(unknownYet.isAbsoluteNoun() && ((Noun)unknownYet).hasPossibleCaseNumbers()) noun.setPossibleCaseNumbers(((Noun)unknownYet).getPossibleCaseNumbers());
                        wordsToAdd.add((Word) noun);
                    }
                    result.addWordsAtPosition(indice, wordsToAdd);
                }
                if (unknownYet.isVerb() || unknownYet.isTypeUnknow() && verbRepository.hasVerb(initialValue)) {
                    Collection<Verb> verbs = verbRepository.getVerbs(initialValue);
                    Collection<Word> wordsToAdd = new ArrayList<>();
                    for(Verb verb : verbs) {
                        verb.setInitialValue(accentuer.unaccentued(initialValue));
                        verb.setPreferedTranslation(unknownYet.getPreferedTranslation());
                        if (unknownYet.isVerb()) {
                            verb.setForbiddenConjugations(unknownYet.getForbiddenConjugations());
                            verb.setGender(unknownYet.getGender());
                        }
                        wordsToAdd.add((Word) verb);
                    }
                    result.addWordsAtPosition(indice, wordsToAdd);
                }
                if(unknownYet.isTypeUnknow() && shouldNotBeTranslated(initialValue)) {
                    result.addWordAtPosition(indice, new NoTranslationWord(language,initialValue));
                }
                if(unknownYet.isEmpty()) {
                    continue;
                }
                if(!result.hasWordContainerInPosition(indice)) {
                    result.addWordAtPosition(indice, unknownYet);
                }
            }
        }
        Phrase phrase = affectSentenceToEachWord(result);
        return modificationLog.processLastModification(phrase);
    }

    private boolean shouldNotBeTranslated(String initialValue) {
        return initialValue.startsWith("|") && initialValue.endsWith("|");
    }

    private Phrase affectSentenceToEachWord(Phrase resultPhrase) {
        Phrase finalComputedPhrase = new Phrase(resultPhrase.size(),language);
        resultPhrase.getWordContainerEntrySet().stream().forEach(entry -> finalComputedPhrase.addWordContainerAtPosition(entry.getKey(), entry.getValue(), resultPhrase));
        return finalComputedPhrase;
    }

    private Word addData(Word word, Integer position, Phrase result) {
        word.putSentenceInformation(result, position);
        return word;
    }

    public CustomLanguageRulePhraseChanger getPhraseChanger() {
        return customLanguageRulePhraseChanger;
    }

}

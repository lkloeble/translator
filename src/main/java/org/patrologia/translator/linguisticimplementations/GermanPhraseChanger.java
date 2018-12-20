package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.modificationlog.ModificationLog;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.casenumbergenre.*;
import patrologia.translator.rule.german.GermanRuleFactory;
import patrologia.translator.utils.ExpressionHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanPhraseChanger extends CustomLanguageRulePhraseChanger {

    private VerbRepository verbRepository;
    private NounRepository nounRepository;

    private ExpressionHolder expressionHolder = new ExpressionHolder(Language.GERMAN);

    public GermanPhraseChanger(NounRepository nounRepository, GermanRuleFactory germanRuleFactory, VerbRepository verbRepository) {
        super();
        this.verbRepository = verbRepository;
        this.nounRepository = nounRepository;
    }

    @Override
    public Phrase modifyPhrase(Phrase startPhrase, ModificationLog modificationLog, CustomRule customRule) {
        Phrase phrase = filterSingularAndPluralSie(startPhrase);
        Phrase filteredSie = filterVerbWithWir(phrase);
        Phrase questionsVerbCorrected = correctQuestionsVerbs(filteredSie);
        Phrase pluralVerbSeinWithNoLeadingWir = setPluralVerbSeinWithNoLeadingWir(questionsVerbCorrected);
        Phrase filterPhraseForDasArticle = filterPhraseForDasArticle(pluralVerbSeinWithNoLeadingWir);
        Phrase filterNounsWithGenetiveEndingS = filterGenetiveEndingS(filterPhraseForDasArticle);
        Phrase replaceExpressionsInManyWordsByAgreggateTranslatedWords = replaceExpressionsInManyWordsByAgreggateTranslatedWords(filterNounsWithGenetiveEndingS, expressionHolder);
        return replaceExpressionsInManyWordsByAgreggateTranslatedWords;
    }

    private Phrase filterGenetiveEndingS(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        int genitiveFound = 0;
        boolean genitiveFollowedByNoun = true;
        phraseLabel : for(Integer indice : integers) {
            String initialValue = phrase.getWordContainerAtPosition(indice).getInitialValue();
            if(initialValue.length() == 0) continue;
            String valueWithoutGenitive = initialValue.substring(0, initialValue.length() - 1);
            if(initialValue.endsWith("s") && nounRepository.hasNoun(valueWithoutGenitive)  && !nounRepository.hasNoun(initialValue)) {
                Collection<Noun> nouns = nounRepository.getNoun(valueWithoutGenitive);
                for(Noun noun : nouns) {
                    if(noun.hasSpecificRule("agenitive")) continue phraseLabel;
                }
                String initialValueNextWord = phrase.getWordContainerAtPosition(indice+1).getInitialValue();
                if(!nounRepository.hasNoun(initialValueNextWord)  && !verbRepository.hasVerb(initialValueNextWord)) continue phraseLabel;
                genitiveFound++;
                if(verbRepository.hasVerb(initialValueNextWord)) genitiveFollowedByNoun = false;
            }
        }
        if(genitiveFound == 0)  return phrase;
        int genitivePhraseSize = phrase.size();
        if(genitiveFollowedByNoun) genitivePhraseSize += genitiveFound;//TODO : handle the case of multiple different kind of genitive following type word
        Phrase phraseWithGenitive = new Phrase(genitivePhraseSize,Language.GERMAN);
        int indice = 1;
        for(int newIndice = 1;newIndice<=genitivePhraseSize;newIndice++,indice++) {
            WordContainer wordContainerAtPosition = phrase.getWordContainerAtPosition(indice);
            String initialValue = wordContainerAtPosition.getInitialValue();
            String initialValueNextWord = phrase.getWordContainerAtPosition(indice+1).getInitialValue();
            if(initialValue.endsWith("s") && nounRepository.hasNoun(initialValue.substring(0,initialValue.length()-1))  && (nounRepository.hasNoun(initialValueNextWord) || verbRepository.hasVerb(initialValueNextWord))) {
                if(nounRepository.hasNoun(initialValueNextWord)) {
                    WordContainer nextOne = phrase.getWordContainerAtPosition(indice + 1);
                    phraseWithGenitive.addWordContainerAtPosition(newIndice, nextOne, phraseWithGenitive);
                    indice++;
                    newIndice++;
                    Preposition xxdexx = new Preposition(Language.GERMAN, "xxdexx", new NullCase());
                    phraseWithGenitive.addWordContainerAtPosition(newIndice, new WordContainer(xxdexx, newIndice, Language.GERMAN), phraseWithGenitive);
                    newIndice++;
                    String initialValueWithoutS = initialValue.substring(0, initialValue.length() - 1);
                    Noun nounWithoutS = new Noun(Language.GERMAN, initialValueWithoutS, initialValueWithoutS, Collections.EMPTY_LIST, null, null, null, Collections.EMPTY_LIST);
                    nounWithoutS.updateInitialValue(initialValueWithoutS);
                    WordContainer wordContainerWithoutS = new WordContainer(nounWithoutS, newIndice, Language.GERMAN);
                    phraseWithGenitive.addWordContainerAtPosition(newIndice, wordContainerWithoutS, phraseWithGenitive);
                } else if(verbRepository.hasVerb(initialValueNextWord)) {
                    String initialValueWithoutS = initialValue.substring(0, initialValue.length() - 1);
                    Noun nounWithoutS = new Noun(Language.GERMAN, initialValueWithoutS, initialValueWithoutS, Collections.EMPTY_LIST, null, null, null, Collections.EMPTY_LIST);
                    nounWithoutS.updateInitialValue(initialValueWithoutS);
                    WordContainer wordContainerWithoutS = new WordContainer(nounWithoutS, newIndice, Language.GERMAN);
                    phraseWithGenitive.addWordContainerAtPosition(newIndice,wordContainerWithoutS,phraseWithGenitive);//no word inversion for genitive before a word
                }
            } else {
                phraseWithGenitive.addWordContainerAtPosition(newIndice,wordContainerAtPosition,phraseWithGenitive);
            }
        }
        return phraseWithGenitive;
    }

    private Phrase correctQuestionsVerbs(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            Word ich = phrase.getYetUnknownWordAtPosition(indice);
            Word previous = phrase.getYetUnknownWordAtPosition(indice-1);
            if(previous != null && ich.getInitialValue().equals("ich")) {
                String initialValue = previous.getInitialValue();
                if(!verbRepository.hasVerb(initialValue) && verbRepository.hasVerb(initialValue + "e")) {
                    previous.modifyContentByPatternReplacement(initialValue,initialValue + "e");
                    previous = new Verb(previous.getInitialValue(),verbRepository, Language.GERMAN);
                    phrase.addWordContainerAtPosition(indice-1, new WordContainer(previous,indice,Language.GERMAN), phrase);
                }
            }
        }
        return phrase;
    }

    private Phrase setPluralVerbSeinWithNoLeadingWir(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            Word word = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            if(word.isVerbInstance() && ((Verb)word).isThirdPLural(word.getInitialValue())) {
                Word previous = phrase.getWordContainerAtPosition(indice-1).getUniqueWord();
                if(previous == null || !previous.getInitialValue().equals("wir")) {
                    Verb verb = (Verb)word;
                    verb.setPluralKnown(true);
                }
            }
        }
        return phrase;
    }

    private Phrase filterSingularAndPluralSie(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            Word word = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            if(word.isVerb()) {
                Word previous = phrase.getWordContainerAtPosition(indice-1).getUniqueWord();
                if(previous != null && previous.getInitialValue().equals("sie")) {
                    Verb verb = (Verb)word;
                    Preposition sie = (Preposition)previous;
                    if(verb.isPlural(verb.getInitialValue())) {
                        verb.setPositionInTranslationTable(5);
                        sie.setDistinctiveTranslationByNumber();
                    }
                }
            }
        }
        return phrase;
    }

    private Phrase filterVerbWithWir(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            Word word = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            if(word.isVerb()) {
                Word previous = phrase.getWordContainerAtPosition(indice-1).getUniqueWord();
                Word next = phrase.getWordContainerAtPosition(indice+1).getUniqueWord();
                if((previous != null && previous.getInitialValue().equals("wir")) || (next != null && next.getInitialValue().equals("wir"))) {
                    Verb verb = (Verb)word;
                    if(verb.isPlural(verb.getInitialValue())) {
                        verb.setPositionInTranslationTable(3);
                    }
                }
            }
        }
        return phrase;
    }


    private Phrase filterPhraseForDasArticle(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            Word word = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            if(word.getInitialValue().equals("das")) {
                Word next = phrase.getWordContainerAtPosition(indice+1).getUniqueWord();
                if(next != null && next.isNoun() && ((Noun)next).getGender().equals(Gender.NEUTRAL)) {
                    Preposition das = (Preposition)word;
                    das.setDistinctiveTranslationByNumber();
                }
            }
        }
        return phrase;
    }
}

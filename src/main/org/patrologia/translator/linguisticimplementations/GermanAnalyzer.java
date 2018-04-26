package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.modificationlog.DefaultModificationLog;
import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.basicelements.preposition.PrepositionRepository;
import org.patrologia.translator.basicelements.verb.Verb;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.casenumbergenre.CaseOperatorContainer;
import org.patrologia.translator.conjugation.Conjugation;
import org.patrologia.translator.rule.german.GermanRuleFactory;
import org.patrologia.translator.utils.Analizer;
import org.patrologia.translator.utils.PhraseAnalizer;
import org.patrologia.translator.utils.WordAnalyzer;
import org.patrologia.translator.utils.WordSplitter;

import java.util.Set;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanAnalyzer implements Analizer {

    private WordSplitter wordSplitter = new WordSplitter();
    private WordAnalyzer wordAnalyzer;
    private PhraseAnalizer phraseAnalizer = new PhraseAnalizer();
    private GermanNounSplitter germanNounSplitter;
    private VerbRepository verbRepository;

    public GermanAnalyzer(PrepositionRepository prepositionRepository, NounRepository nounRepository, VerbRepository verbRepository) {
        wordAnalyzer = new WordAnalyzer(prepositionRepository, nounRepository, verbRepository, new GermanPhraseChanger(nounRepository, new GermanRuleFactory(verbRepository),verbRepository), new DefaultModificationLog(), new CustomRule(),  new CaseOperatorContainer(nounRepository,prepositionRepository),Language.GERMAN);
        germanNounSplitter = new GermanNounSplitter(nounRepository,prepositionRepository,verbRepository);
        this.verbRepository = verbRepository;
    }

    @Override
    public Analysis analyze(String sentence) {
        String characterAlphaOnly = replaceGermanWithAlpha(sentence);
        Phrase phrase = wordSplitter.splitSentence(characterAlphaOnly, Language.GERMAN, new DefaultWordSplitterPattern());
        phrase = wordAnalyzer.affectAllPossibleInformations(phrase);
        phrase = germanNounSplitter.germanFindComposedWords(phrase);
        phrase = dasPrepositionAndArticleChooser(phrase);
        phrase = wordAnalyzer.getPhraseChanger().modifyPhrase(phrase, null,null);
        phrase = affectInfinitive(phrase);
        return phraseAnalizer.affectAllPossibleInformationsBetweenWords(Language.GERMAN, phrase);
    }

    private Phrase affectInfinitive(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            //VERBE PRECEDE OU SUIVI PAR UNE PREPOSITION : on laisse le translationIndice
            WordContainer currentWordContainer = phrase.getWordContainerAtPosition(indice);
            Word currentWordVerb = currentWordContainer.getWordByType(WordType.VERB);
            if(currentWordVerb.isTypeUnknow()) continue;
            WordContainer previousWordContainer = phrase.getWordContainerAtPosition(indice-1);
            WordContainer nextWordContainer = phrase.getWordContainerAtPosition(indice+1);
            Word previousPreposition = previousWordContainer.getWordByType(WordType.PREPOSITION);
            Word nextPreposition = nextWordContainer.getWordByType(WordType.PREPOSITION);
            Verb currentVerb = (Verb) currentWordVerb;
            if(previousPreposition.isPreposition() && ((Preposition)previousPreposition).isArticle()) {
                  currentVerb.addForbiddenConjugation(Conjugation.INFINITIVE);
            } else if(nextPreposition.isPreposition() && ((Preposition)nextPreposition).isArticle()) {
                currentVerb.addForbiddenConjugation(Conjugation.INFINITIVE);
            } else {
                if(verbRepository.isPossibleInfinitive(currentVerb)) {
                    Word previousWordVerb = previousWordContainer.getWordByType(WordType.VERB);
                    if(previousWordVerb.isVerb()) {
                        currentVerb.addForbiddenConjugation(Conjugation.ACTIVE_INDICATIVE_PRESENT);
                    }
                    currentVerb.setPositionInTranslationTable(0);
                }
            }
            //VERBE SUIVANT UN VERBE => infinitive d'office
        }
        return phrase;
    }

    private Phrase dasPrepositionAndArticleChooser(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        for(Integer indice : integers) {
            Word currenWord = phrase.getWordContainerAtPosition(indice).getUniqueWord();
            if(!"das".equals(currenWord.getInitialValue())) continue;
            Word followingWord = phrase.getWordContainerAtPosition(indice+1).getUniqueWord();
            if(followingWord.isNoun()) {
                Preposition das = (Preposition)currenWord;
                das.setDistinctiveTranslationByNumber();
            }
        }
        return phrase;
    }

    private String replaceGermanWithAlpha(String sentence) {
        if(sentence == null) return "";
        StringBuilder sb = new StringBuilder();
        char[] chars = sentence.toCharArray();
        for (char c : chars) {
            int i = (int) c;
            switch (i) {
                case 196:
                    sb.append("a");
                    break;
                case 220:
                    sb.append("u");
                    break;
                case 223:
                    sb.append("ss");
                    break;
                case 246:
                    sb.append("o");
                    break;
                case 214:
                    sb.append("o");
                    break;
                case 228:
                    sb.append("a");
                    break;
                case 252:
                    sb.append("u");
                    break;
                case 233:
                    sb.append("e");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString().trim();
    }
}

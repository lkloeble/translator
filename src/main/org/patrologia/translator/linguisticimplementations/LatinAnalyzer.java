package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.DefaultModificationLog;
import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.casenumbergenre.CaseOperatorContainer;
import org.patrologia.translator.utils.Analizer;
import org.patrologia.translator.utils.PhraseAnalizer;
import org.patrologia.translator.utils.WordAnalyzer;
import org.patrologia.translator.utils.WordSplitter;

import java.util.Set;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public class LatinAnalyzer implements Analizer {

    private WordSplitter wordSplitter = new WordSplitter();
    private WordAnalyzer wordAnalyzer = null;
    private PhraseAnalizer phraseAnalizer = new PhraseAnalizer();
    private PrepositionRepository prepositionRepository = null;

    public LatinAnalyzer(PrepositionRepository prepositionRepository, NounRepository nounRepository, VerbRepository verbRepository) {
        wordAnalyzer = new WordAnalyzer(prepositionRepository, nounRepository,verbRepository, new LatinPhraseChanger(nounRepository, verbRepository, prepositionRepository), new DefaultModificationLog(), new CustomRule(), new CaseOperatorContainer(nounRepository,prepositionRepository),Language.LATIN);
        this.prepositionRepository = prepositionRepository;
    }

    public Analysis analyze(String sentence) {
        Phrase phrase = wordSplitter.splitSentence(sentence,Language.LATIN, new DefaultWordSplitterPattern());
        phrase = wordAnalyzer.affectAllPossibleInformations(phrase);
        phrase = addAdjectiveSelectionRule(phrase);
        return phraseAnalizer.affectAllPossibleInformationsBetweenWords(Language.LATIN, phrase);
    }

    private Phrase addAdjectiveSelectionRule(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        for(Integer indice : indices) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            Word wordNoun = wordContainer.getWordByType(WordType.NOUN);
            if(!wordNoun.isNoun()) continue;
            Noun noun = (Noun)wordNoun;
            if(noun.isAdjective()) {
                noun.addRule(prepositionRepository.getRuleFactory().getRuleByName("adjectiveSelectGenderRule",noun.getInitialValue()));
            }
        }
        return phrase;
    }
}

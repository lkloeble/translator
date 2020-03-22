package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.modificationlog.DefaultModificationLog;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.CaseOperatorContainer;
import patrologia.translator.utils.Analyzer;
import patrologia.translator.utils.PhraseAnalizer;
import patrologia.translator.utils.WordAnalyzer;
import patrologia.translator.utils.WordSplitter;

import java.util.Set;

public class LatinAnalyzer  implements Analyzer {

    private WordSplitter wordSplitter = new WordSplitter();
    private WordAnalyzer wordAnalyzer = null;
    private PhraseAnalizer phraseAnalizer = new PhraseAnalizer();
    private PrepositionRepository prepositionRepository = null;

    public LatinAnalyzer(PrepositionRepository prepositionRepository, NounRepository nounRepository, VerbRepository2 verbRepository) {
        wordAnalyzer = new WordAnalyzer(prepositionRepository, nounRepository,verbRepository, new LatinPhraseChanger(nounRepository, verbRepository, prepositionRepository), new DefaultModificationLog(), new CustomRule(), new CaseOperatorContainer(nounRepository,prepositionRepository), Language.LATIN);
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

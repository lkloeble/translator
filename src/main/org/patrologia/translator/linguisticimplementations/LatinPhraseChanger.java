package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.modificationlog.ModificationLog;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.basicelements.preposition.PrepositionRepository;
import org.patrologia.translator.basicelements.verb.VerbRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 05/10/2015.
 */
public class LatinPhraseChanger extends CustomLanguageRulePhraseChanger {

    private static final String QUOQUE = "quoque";
    private static final String ITAQUE = "itaque";
    private static final String UNUMQUODQUE = "unumquodque";
    private static final String QUOCUMQUE = "quocumque";
    private static final String UNDIQUE = "undique";
    private static final String DENIQUE = "denique";
    private static final String BENE = "bene";
    private static final String MANE = "mane";
    private static final String SANE = "sane";
    private static final String SINE = "sine";
    private static final String OMNE = "omne";
    private static final String PENE = "pene";
    private static final String DOMINE = "domine";
    private static final String PONE = "pone";
    private static final String CARNE = "carne";
    private static final String BONE = "bone";
    private static final String PLENE = "plene";
    private static final String SUME = "sume";
    private static final String QUINQUE = "quinque";
    private NounRepository nounRepository;
    private VerbRepository verbRepository;
    private PrepositionRepository prepositionRepository;
    private List<String> stopWordsQue = new ArrayList<String>();
    private List<String> stopWordsNe = new ArrayList<String>();
    private List<String> stopWordsVe = new ArrayList<String>();

    private Accentuer accentuer = new DummyAccentuer();

    public LatinPhraseChanger(NounRepository nounRepository, VerbRepository verbRepository, PrepositionRepository prepositionRepository) {
        this.nounRepository = nounRepository;
        this.verbRepository = verbRepository;
        this.prepositionRepository = prepositionRepository;
        this.language = Language.LATIN;
        stopWordsQue.addAll(nounRepository.getNounsValueForEndingWith("que"));
        stopWordsQue.addAll(prepositionRepository.getValuesEndingWith("que"));
        stopWordsQue.addAll(verbRepository.getValuesEndingWith("que"));
        stopWordsNe.addAll(nounRepository.getNounsValueForEndingWith("ne"));
        stopWordsNe.addAll(prepositionRepository.getValuesEndingWith("ne"));
        stopWordsNe.addAll(verbRepository.getValuesEndingWith("ne"));
        stopWordsVe.addAll(nounRepository.getNounsValueForEndingWith("ve"));
        stopWordsVe.addAll(prepositionRepository.getValuesEndingWith("ve"));
        stopWordsVe.addAll(verbRepository.getValuesEndingWith("ve"));
    }

    @Override
    public Phrase modifyPhrase(Phrase startPhrase, ModificationLog modificationLog, CustomRule customRule) {
        Phrase phrase = substituteEndingNeWithCustomPrep(startPhrase);
        phrase = splitAdPrepositionAndSumConjugation(phrase);
        phrase = splitAbPrepositionAndSumConjugation(phrase);
        phrase = substituteVeWithSeu(phrase);
        return substituteQueWithEt(phrase);
    }

    private Phrase substituteEndingNeWithCustomPrep(Phrase phrase) {
        return substituteEndPatternWithNewPreposition(phrase, "ne", new Preposition(language, "isntit", null), stopWordsNe, accentuer);
    }

    private Phrase substituteVeWithSeu(Phrase phrase) {
        return substituteEndPatternWithNewPreposition(phrase, "ve", new Preposition(language, "seu", null), stopWordsVe, accentuer);
    }

    private Phrase substituteQueWithEt(Phrase phrase) {
        return substituteEndPatternWithNewPreposition(phrase, "que", new Preposition(language, "et", null), stopWordsQue, accentuer);
    }

    private Phrase splitAdPrepositionAndSumConjugation(Phrase phrase) {
        return splitBeginPrepositionAndVerbForm(phrase, new Preposition(language, "ad", null), "sum", verbRepository);
    }

    private Phrase splitAbPrepositionAndSumConjugation(Phrase phrase) {
        return splitBeginPrepositionAndVerbForm(phrase, new Preposition(language, "ab", null), "sum", verbRepository);
    }

}

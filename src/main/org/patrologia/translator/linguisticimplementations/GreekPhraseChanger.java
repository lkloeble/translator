package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.basicelements.modificationlog.ModificationLog;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.Phrase;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class GreekPhraseChanger extends CustomLanguageRulePhraseChanger {

    private NounRepository nounRepository;

    public GreekPhraseChanger(NounRepository nounRepository) {
        this.nounRepository = nounRepository;
    }

    @Override
    public Phrase modifyPhrase(Phrase startPhrase, ModificationLog modificationLog, CustomRule customRule) {
        return startPhrase;
    }
}

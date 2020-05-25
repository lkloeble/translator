package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.modificationlog.ModificationLog;
import patrologia.translator.basicelements.noun.NounRepository;

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

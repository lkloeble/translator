package patrologia.translator.basicelements.modificationlog;

import patrologia.translator.basicelements.Phrase;

public class DefaultModificationLog  extends ModificationLog {

    @Override
    public Phrase processLastModification(Phrase phrase) {
        return phrase;
    }
}

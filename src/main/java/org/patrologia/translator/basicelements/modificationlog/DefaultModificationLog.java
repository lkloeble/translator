package patrologia.translator.basicelements.modificationlog;

import patrologia.translator.basicelements.modificationlog.ModificationLog;
import patrologia.translator.basicelements.Phrase;

/**
 * Created by lkloeble on 05/09/2016.
 */
public class DefaultModificationLog  extends ModificationLog {

    @Override
    public Phrase processLastModification(Phrase phrase) {
        return phrase;
    }
}

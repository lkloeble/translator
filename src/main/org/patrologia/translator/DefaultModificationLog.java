package org.patrologia.translator;

import org.patrologia.translator.basicelements.ModificationLog;
import org.patrologia.translator.basicelements.Phrase;

/**
 * Created by lkloeble on 05/09/2016.
 */
public class DefaultModificationLog  extends ModificationLog {

    @Override
    public Phrase processLastModification(Phrase phrase) {
        return phrase;
    }
}

package patrologia.translator.basicelements.modificationlog;

import patrologia.translator.basicelements.Phrase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 02/09/2016.
 */
public abstract class ModificationLog {

    protected List<Event> eventModificationsList = new ArrayList<>();

    public void reset() {
        eventModificationsList.clear();
    }

    public abstract Phrase processLastModification(Phrase phrase);
}

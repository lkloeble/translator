package patrologia.translator.basicelements.modificationlog;

import patrologia.translator.basicelements.Phrase;

import java.util.ArrayList;
import java.util.List;

public abstract class ModificationLog {

    protected List<Event> eventModificationsList = new ArrayList<>();

    public void reset() {
        eventModificationsList.clear();
    }

    public abstract Phrase processLastModification(Phrase phrase);

}

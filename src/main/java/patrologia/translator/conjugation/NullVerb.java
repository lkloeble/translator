package patrologia.translator.conjugation;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.verb.Verb;

public class NullVerb extends Verb {

    public NullVerb(Language language, String initialValue, String root) {
        super(initialValue, root, language);
    }

    public String getRoot() {
        return "";
    }

    public boolean isNullVerb() {
        return true;
    }
}

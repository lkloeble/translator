package org.patrologia.translator.basicelements;

import org.patrologia.translator.basicelements.verb.Verb;

/**
 * Created by lkloeble on 08/02/2016.
 */
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

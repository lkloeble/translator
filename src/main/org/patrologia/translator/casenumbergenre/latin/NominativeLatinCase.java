package org.patrologia.translator.casenumbergenre.latin;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public class NominativeLatinCase extends LatinCase {

    private static NominativeLatinCase singleton;

    private NominativeLatinCase() {}

    public static NominativeLatinCase getInstance() {
        if(singleton == null) {
            singleton = new NominativeLatinCase();
        }
        return singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NominativeLatinCase)) return false;

        return  true;//nominatives are equal each others if singleton is corrupted
    }

    @Override
    public String toString() {
        return "NominativeLatinCase{}";
    }
}

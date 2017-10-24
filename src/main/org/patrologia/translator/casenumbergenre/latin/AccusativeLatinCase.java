package org.patrologia.translator.casenumbergenre.latin;

/**
 * Created by Laurent KLOEBLE on 10/09/2015.
 */
public class AccusativeLatinCase extends LatinCase {

    private static AccusativeLatinCase singleton;

    private AccusativeLatinCase() {}

    public static AccusativeLatinCase getInstance() {
        if(singleton == null) {
            singleton = new AccusativeLatinCase();
        }
        return singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccusativeLatinCase)) return false;

        return  true;//accusatives are equal each others if singleton is doomed
    }

    @Override
    public String toString() {
        return "AccusativeLatinCase{}";
    }
}
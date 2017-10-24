package org.patrologia.translator.casenumbergenre.latin;

/**
 * Created by Laurent KLOEBLE on 12/09/2015.
 */
public class DativeLatinCase extends LatinCase {

    private static DativeLatinCase singleton;

    private DativeLatinCase() {}

    public static DativeLatinCase getInstance() {
        if(singleton == null) {
            singleton = new DativeLatinCase();
        }
        return singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DativeLatinCase)) return false;

        return  true;//datives are equal each others if singleton is doomed
    }

    @Override
    public String toString() {
        return "DativeLatinCase{}";
    }
}

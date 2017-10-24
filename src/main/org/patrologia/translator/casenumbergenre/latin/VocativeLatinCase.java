package org.patrologia.translator.casenumbergenre.latin;

/**
 * Created by Laurent KLOEBLE on 29/09/2015.
 */
public class VocativeLatinCase extends LatinCase {

    private static VocativeLatinCase singleton;

    private VocativeLatinCase() {}

    public static VocativeLatinCase getInstance() {
        if(singleton == null) {
            singleton = new VocativeLatinCase();
        }
        return singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VocativeLatinCase)) return false;

        return  true;//vocative are equal each others if singleton is doomed
    }

    @Override
    public String toString() {
        return "VocativeLatinCase{}";
    }
}
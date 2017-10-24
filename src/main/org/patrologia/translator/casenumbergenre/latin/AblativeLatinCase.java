package org.patrologia.translator.casenumbergenre.latin;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public class AblativeLatinCase extends LatinCase {

    private static AblativeLatinCase singleton;

    private AblativeLatinCase() {}

    public static AblativeLatinCase getInstance() {
        if(singleton == null) {
            singleton = new AblativeLatinCase();
        }
        return singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AblativeLatinCase)) return false;

        return  true;//ablatives are equal each others if singleton is doomed
    }

    @Override
    public String toString() {
        return "AblativeLatinCase{}";
    }
}

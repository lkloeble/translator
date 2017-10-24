package org.patrologia.translator.casenumbergenre.greek;

/**
 * Created by lkloeble on 04/08/2016.
 */
public class VocativeGreekCase extends GreekCase {

    private static VocativeGreekCase singleton;

    private VocativeGreekCase() {}

    public static VocativeGreekCase getInstance() {
        if(singleton == null) {
            singleton = new VocativeGreekCase();
        }
        return singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VocativeGreekCase)) return false;

        return  true;//vocatives are equal each others if singleton is corrupted
    }

    @Override
    public String toString() {
        return "VocativeGreekCase{}";
    }

}

package org.patrologia.translator.casenumbergenre.latin;

/**
 * Created by Laurent KLOEBLE on 10/09/2015.
 */
public class GenitiveLatinCase extends LatinCase {

    private static GenitiveLatinCase singleton;

    private GenitiveLatinCase() {}

    public static GenitiveLatinCase getInstance() {
        if(singleton == null) {
            singleton = new GenitiveLatinCase();
        }
        return singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenitiveLatinCase)) return false;

        return  true;//genitives are equal each others if singleton is doomed
    }

    @Override
    public String toString() {
        return "GenitiveLatinCase{}";
    }
}

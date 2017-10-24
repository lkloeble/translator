package org.patrologia.translator.casenumbergenre.romanian;

/**
 * Created by lkloeble on 07/04/2017.
 */
public class GenitiveRomanianCase  extends RomanianCase {

    public GenitiveRomanianCase(String differentier) {
        this.differentier = differentier;
    }

    @Override
    public String toString() {
        return "GenitiveRomanianCase{" + differentier + "}";
    }

}

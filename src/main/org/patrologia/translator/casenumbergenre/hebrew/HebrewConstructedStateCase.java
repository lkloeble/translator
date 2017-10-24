package org.patrologia.translator.casenumbergenre.hebrew;

/**
 * Created by lkloeble on 22/04/2017.
 */
public class HebrewConstructedStateCase extends HebrewCase {

    public HebrewConstructedStateCase(HebrewCase toClone) {
        this.differentier = toClone.differentier;
    }

    public HebrewConstructedStateCase(String differentier) {
        this.differentier = differentier;
        if(differentier == null) differentier = "foobar";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HebrewConstructedStateCase that = (HebrewConstructedStateCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }

    @Override
    public String toString() {
        return "HebrewConstructedStateCase{" +
                "differentier='" + differentier + '\'' +
                '}';
    }

    @Override
    protected String toStringCase() {
        return "HebrewConstructedStateCase";
    }

    @Override
    public String getTrigramForCase() {
        return "cst";
    }

}

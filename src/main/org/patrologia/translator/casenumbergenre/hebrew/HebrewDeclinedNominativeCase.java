package org.patrologia.translator.casenumbergenre.hebrew;

/**
 * Created by lkloeble on 24/04/2017.
 */
public class HebrewDeclinedNominativeCase extends HebrewCase {

    public HebrewDeclinedNominativeCase(HebrewCase aCase) {
        this.differentier = aCase.differentier;
    }

    public HebrewDeclinedNominativeCase(String differentier) {
        this.differentier = differentier;
        if(differentier == null) differentier = "foobar";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HebrewDeclinedNominativeCase that = (HebrewDeclinedNominativeCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }

    @Override
    public String toString() {
        return "HebrewDeclinedNominativeCase{" +
                "differentier='" + differentier + '\'' +
                '}';
    }

    @Override
    protected String toStringCase() {
        return "HebrewDeclinedNominativeCase";
    }

    @Override
    public String getTrigramForCase() {
        return "dec";
    }

}

package org.patrologia.translator.casenumbergenre.german;

/**
 * Created by lkloeble on 07/08/2017.
 */
public class NominativeGermanCase extends GermanCase {

    public NominativeGermanCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NominativeGermanCase that = (NominativeGermanCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "NominativeGermanCase{" + differentier+ "}";
    }
}

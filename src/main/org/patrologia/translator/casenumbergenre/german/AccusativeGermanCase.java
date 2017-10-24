package org.patrologia.translator.casenumbergenre.german;

/**
 * Created by lkloeble on 08/08/2017.
 */
public class AccusativeGermanCase  extends GermanCase {

    public AccusativeGermanCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccusativeGermanCase that = (AccusativeGermanCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "AccusativeGermanCase{" + differentier+ "}";
    }

}

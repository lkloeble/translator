package org.patrologia.translator.casenumbergenre.greek;

/**
 * Created by Laurent KLOEBLE on 08/10/2015.
 */
public class NominativeGreekCase extends GreekCase {

    public NominativeGreekCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NominativeGreekCase that = (NominativeGreekCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "NominativeGreekCase{" + differentier+ "}";
    }
}

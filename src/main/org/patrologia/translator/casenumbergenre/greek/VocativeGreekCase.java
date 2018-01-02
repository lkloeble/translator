package org.patrologia.translator.casenumbergenre.greek;

/**
 * Created by lkloeble on 04/08/2016.
 */
public class VocativeGreekCase extends GreekCase {

    public VocativeGreekCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VocativeGreekCase that = (VocativeGreekCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "VocativeGreekCase{" + differentier+ "}";
    }
}

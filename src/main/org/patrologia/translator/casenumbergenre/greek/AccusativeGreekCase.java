package org.patrologia.translator.casenumbergenre.greek;

/**
 * Created by lkloeble on 19/02/2016.
 */
public class AccusativeGreekCase extends GreekCase {

    public AccusativeGreekCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccusativeGreekCase that = (AccusativeGreekCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "AccusativeGreekCase{" + differentier+ "}";
    }

}

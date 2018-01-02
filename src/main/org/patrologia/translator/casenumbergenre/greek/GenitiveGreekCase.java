package org.patrologia.translator.casenumbergenre.greek;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
public class GenitiveGreekCase extends GreekCase {

    public GenitiveGreekCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenitiveGreekCase that = (GenitiveGreekCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "GenitiveGreekCase{" + differentier+ "}";
    }
}

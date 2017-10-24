package org.patrologia.translator.casenumbergenre.german;

/**
 * Created by lkloeble on 08/08/2017.
 */
public class GenitiveGermanCase  extends GermanCase {

    public GenitiveGermanCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenitiveGermanCase that = (GenitiveGermanCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "GenitiveGermanCase{" + differentier+ "}";
    }

}

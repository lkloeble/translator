package patrologia.translator.casenumbergenre.latin;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public class NominativeLatinCase extends LatinCase {

    public NominativeLatinCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NominativeLatinCase that = (NominativeLatinCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }

    @Override
    public String toString() {
        return "NominativeLatinCase{" +
                "differentier='" + differentier + '\'' +
                '}';
    }
}

package patrologia.translator.casenumbergenre.greek;

/**
 * Created by lkloeble on 22/03/2016.
 */
public class DativeGreekCase  extends GreekCase {

    public DativeGreekCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DativeGreekCase that = (DativeGreekCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "DativeGreekCase{" + differentier+ "}";
    }
}
package patrologia.translator.casenumbergenre.german;

public class DativeGermanCase extends GermanCase {

    public DativeGermanCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DativeGermanCase that = (DativeGermanCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }

    @Override
    public String toString() {
        return "DativeGermanCase{" + differentier+ "}";
    }

}

package patrologia.translator.casenumbergenre.latin;

public class DativeLatinCase extends LatinCase {

    public DativeLatinCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DativeLatinCase that = (DativeLatinCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }

    @Override
    public String toString() {
        return "DativeLatinCase{" +
                "differentier='" + differentier + '\'' +
                '}';
    }


}

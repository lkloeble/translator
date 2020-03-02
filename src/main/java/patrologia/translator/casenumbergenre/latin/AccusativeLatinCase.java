package patrologia.translator.casenumbergenre.latin;

public class AccusativeLatinCase extends LatinCase {

    public AccusativeLatinCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccusativeLatinCase that = (AccusativeLatinCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }

    @Override
    public String toString() {
        return "AccusativeLatinCase{" +
                "differentier='" + differentier + '\'' +
                '}';
    }


}

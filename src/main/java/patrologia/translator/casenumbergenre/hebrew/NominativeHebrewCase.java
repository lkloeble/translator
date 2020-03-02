package patrologia.translator.casenumbergenre.hebrew;

public class NominativeHebrewCase extends HebrewCase {

    public NominativeHebrewCase(HebrewCase aCase) {
        this.differentier = aCase.differentier;
    }

    public NominativeHebrewCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NominativeHebrewCase that = (NominativeHebrewCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }

    @Override
    public String toString() {
        return "NominativeHebrewCase{" +
                "differentier='" + differentier + '\'' +
                '}';
    }

    @Override
    public String getTrigramForCase() {
        return "nom";
    }

    @Override
    protected String toStringCase() {
        return "NominativeHebrewCase";
    }

}

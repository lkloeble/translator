package patrologia.translator.casenumbergenre.romanian;

public class GenitiveRomanianCase  extends RomanianCase {

    public GenitiveRomanianCase(String differentier) {
        if(differentier == null) differentier = "";
        this.differentier = differentier;
    }

    @Override
    public String toString() {
        return "GenitiveRomanianCase{" + differentier + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenitiveRomanianCase that = (GenitiveRomanianCase) o;

        return differentier.equals(that.differentier);
    }

    @Override
    public int hashCode() {
        return differentier != null ? differentier.hashCode() : toString().hashCode();
    }
}

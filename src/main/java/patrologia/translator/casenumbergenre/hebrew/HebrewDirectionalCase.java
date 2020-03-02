package patrologia.translator.casenumbergenre.hebrew;

public class HebrewDirectionalCase extends HebrewCase {

    public HebrewDirectionalCase(HebrewCase aCase) {
        this.differentier = aCase.differentier;
    }

    public HebrewDirectionalCase(String differentier) {
        this.differentier = differentier;
        if(differentier == null) differentier = "foobar";

    }

    @Override
    public String toString() {
        return "HebrewDirectionalCase{" +
                "differentier='" + differentier + '\'' +
                '}';
    }

    @Override
    protected String toStringCase() {
        return "HebrewDirectionalCase";
    }

    @Override
    public String getTrigramForCase() {
        return "dir";
    }

}

package patrologia.translator.casenumbergenre.greek;

import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.CaseNumberGenre;

import java.util.List;

public class AccusativeGreekCase extends GreekCase {

    public AccusativeGreekCase(String differentier) {
        this.differentier = differentier;
        if(this.differentier == null || this.differentier.length() == 0) {
            this.differentier = "foobar";
        }
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccusativeGreekCase that = (AccusativeGreekCase) o;

        return differentier.equals(that.differentier);

    }

    @Override
    public int hashCode() {
        return differentier.hashCode();
    }


    @Override
    public String toString() {
        return "AccusativeGreekCase{" + differentier+ "}";
    }

}

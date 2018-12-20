package patrologia.translator.casenumbergenre;

/**
 * Created by Laurent KLOEBLE on 29/09/2015.
 */
public class NullCaseNumberGenre extends CaseNumberGenre {

    public NullCaseNumberGenre() {
        super(null,null,null);
    }

    @Override
    public boolean isPlural() {
        return false;
    }

    @Override
    public boolean isGenitive() {
        return false;
    }

    @Override
    public boolean isDative() {
        return false;
    }
}

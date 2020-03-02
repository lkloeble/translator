package patrologia.translator.casenumbergenre;

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

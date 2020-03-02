package patrologia.translator.declension;

import patrologia.translator.casenumbergenre.CaseNumberGenre;

import java.util.Collections;
import java.util.Map;

public class NullDeclension  extends Declension {

    @Override
    public Map<CaseNumberGenre, String> getAllEndings() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public boolean isNull() {
        return true;
    }


}

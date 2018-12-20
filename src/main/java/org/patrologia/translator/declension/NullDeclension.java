package patrologia.translator.declension;

import patrologia.translator.casenumbergenre.CaseNumberGenre;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 04/09/2015.
 */
public class NullDeclension extends Declension {

    @Override
    public Map<CaseNumberGenre, String> getAllEndings() {
        return Collections.EMPTY_MAP;
    }

    @Override
    public boolean isNull() {
        return true;
    }
}

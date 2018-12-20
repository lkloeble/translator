package patrologia.translator.conjugation;

import java.util.Collections;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 15/09/2015.
 */
public class NullConjugation extends Conjugation {

    @Override
    public List<String> getAllEndings() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<String> getCongujationByTimePattern(String timePattern) {
        return null;
    }

    @Override
    public List<String> getTimes() {
        return null;
    }
}

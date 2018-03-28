package org.patrologia.translator.basicelements;

import org.patrologia.translator.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lkloeble on 03/10/2017.
 */
public class Accentuer {

    private List<String> sofitLetters = Arrays.asList(new String[]{"k", "m", "n", "p"});
    public String SOFIT_END = "000";

    public String unaccentuedWithSofit(String initialValue) {
        if(sofitLetters.contains(lastLetter(unaccentued(initialValue)))) {
            return unaccentued(initialValue) + SOFIT_END;
        }
        return unaccentued(initialValue);
    }

    public String unaccentued(String value) {
        return StringUtils.unaccentuate(value);
    }

    private String lastLetter(String unaccentued) {
        if(unaccentued != null && unaccentued.length() > 0) return unaccentued.substring(unaccentued.length()-1,unaccentued.length());
        return "";
    }

    @Override
    public String toString() {
        return "Accentuer{HebrewStyle}";
    }
}

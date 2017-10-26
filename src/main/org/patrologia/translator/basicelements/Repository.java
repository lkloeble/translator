package org.patrologia.translator.basicelements;

import org.patrologia.translator.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by lkloeble on 03/10/2017.
 */
public class Repository {

    private List<String> sofitLetters = Arrays.asList(new String[]{"k", "m", "n", "p"});
    private String SOFIT_END = "000";

    protected String unaccentuedWithSofit(String initialValue) {
        if(sofitLetters.contains(lastLetter(unaccentued(initialValue)))) {
            return unaccentued(initialValue) + SOFIT_END;
        }
        return unaccentued(initialValue);
    }

    protected String unaccentued(String value) {
        return StringUtils.unaccentuate(value);
    }

    private String lastLetter(String unaccentued) {
        return unaccentued.substring(unaccentued.length()-1,unaccentued.length());
    }

}

package org.patrologia.translator.basicelements;

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
        return initialValue;
    }

    protected String unaccentued(String value) {
        char[] letters = value.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : letters) {
            if(c >= 'a') sb.append(c);
        }
        return sb.toString();
    }

    private String lastLetter(String unaccentued) {
        return unaccentued.substring(unaccentued.length()-1,unaccentued.length());
    }

}

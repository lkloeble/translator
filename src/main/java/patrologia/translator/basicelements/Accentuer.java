package patrologia.translator.basicelements;

import patrologia.translator.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

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

    public String cleanAll(String initialValue) {
        //TODO
        return null;
    }
}

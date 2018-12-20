package patrologia.translator.casenumbergenre;

import java.util.List;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public enum Number {

    SINGULAR, PLURAL, UNKNOWN;

    public static int numberOf(List<Number> numbers, Number type) {
        int total = 0;
        for(Number number : numbers) {
            if(number.equals(type)) {
                total++;
            }
        }
        return total;
    }

    public static Number strValueOf(String number) {
        if("sing".equals(number) || "sg".equals(number)) {
            return SINGULAR;
        } else if("plr".equals(number)) {
            return PLURAL;
        }
        if(number.contains("plr")) return PLURAL;
        if(number.contains("sg")) return SINGULAR;
        if(number.equals("im-nw")) return PLURAL;
        return UNKNOWN;
    }

    public static Number getSingleNumber(List<CaseNumberGenre> possibleCaseNumbers) {
        int sing = 0, plural = 0;
        for(CaseNumberGenre cng : possibleCaseNumbers) {
            if(cng.isPlural()) {
                plural++;
            } else {
                sing++;
            }
        }
        if(sing > 0 && plural == 0) {
            return SINGULAR;
        } else if(sing == 0 && plural > 0) {
            return PLURAL;
        }
        return UNKNOWN;
    }
}

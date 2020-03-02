package patrologia.translator.utils;

public class StringUtils {

    public static String unaccentuate(String value) {
        char[] letters = value.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : letters) {
            if(c >= 'a' || c == '\'' || c == '&'  || c == ' ' || c == '$') sb.append(c);
            //if(c >= 'a') sb.append(c);
        }
        return sb.toString();
    }

}

package patrologia.translator.utils;

/**
 * Created by Laurent KLOEBLE on 24/10/2017.
 */
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

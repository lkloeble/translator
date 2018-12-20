package patrologia.translator.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 03/09/2015.
 */
public class DictionaryLoader {

    private String dictionaryFile;

    public DictionaryLoader(String dictionaryFile) {
        this.dictionaryFile = dictionaryFile;
    }

    public List<String> getAllLines() {
        List<String> lines = new ArrayList<String>();
        String charset = "UTF-8";
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFile), Charset.forName(charset)));
            while(br.ready()) {
                lines.add(cleanUTF8(br.readLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private String cleanUTF8(String line) {
        if(line == null) return null;
        if(line != null && line.length() == 0) return "";
        if((int)(new Character(line.charAt(0)).charValue()) > 65000) {
            return line.substring(1);
        }
        return line;
    }
}

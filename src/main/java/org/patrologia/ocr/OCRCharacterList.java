package patrologia.ocr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 31/01/2017.
 */
public class OCRCharacterList {

    List<OCRCharacter> ocrCharacterList = new ArrayList<>();
    private OCRDictionary ocrDictionary = new OCRDictionary();

    public void add(OCRCharacter ocrCharacter) {
        ocrCharacterList.add(ocrCharacter);
    }

    public int size() {
        int size = 0;
        for(OCRCharacter ocrCharacter : ocrCharacterList) {
            if(ocrCharacter.hasPoints()) size++;
        }
        return size;
    }

    public void draw() {
        for(OCRCharacter ocrCharacter : ocrCharacterList)  {
            if(!ocrCharacter.hasPoints()) continue;
            System.out.println(ocrCharacter.draw());
            System.out.println("\n\n@@@@@\n\n");
        }
    }

    public char[] readOcrValues() {
        List<Character> characterList = new ArrayList<>();
        for(OCRCharacter ocrCharacter : ocrCharacterList) {
            if(!ocrCharacter.hasPoints()) continue;
            characterList.add(ocrDictionary.read(ocrCharacter));
        }
        return null;
    }
}

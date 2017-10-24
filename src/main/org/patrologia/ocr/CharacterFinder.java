package org.patrologia.ocr;

/**
 * Created by lkloeble on 30/01/2017.
 */
public class CharacterFinder {

    public char[] extract(boolean[][] pixels) {
        OCRCharacterList ocrCharacterList = new OCRCharacterList();
        OCRCharacter ocrCharacter = new OCRCharacter();
        ocrCharacterList.add(ocrCharacter);
        boolean foundChar = false;
        for(int i=0;i<pixels.length;i++) {
            boolean columnStarted = true;
            foundChar = false;
            for (int j = 0; j < pixels[0].length; j++) {
                if (pixels[i][j]) {
                    foundChar = true;
                    ocrCharacter.addPoint(i,j);
                }
                columnStarted = false;
            }
            if(!foundChar && !columnStarted) {
                ocrCharacter = new OCRCharacter();
                ocrCharacterList.add(ocrCharacter);
            }
        }
        return ocrCharacterList.readOcrValues();
    }

}

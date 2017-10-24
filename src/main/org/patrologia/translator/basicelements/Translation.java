package org.patrologia.translator.basicelements;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 01/09/2015.
 */
public class Translation {

    protected FinalModifier finalModifier;

    public Translation(FinalModifier finalModifier) {
        this.finalModifier = finalModifier;
    }

    public Translation() {
        this.finalModifier = new DefaultFinalModifier();
    }

    private Map<Integer, String> translationWithPositions = new HashMap<Integer, String>();

    public String getPossibleTranslation() {
        List<Integer> positions = new ArrayList(translationWithPositions.keySet());
        Collections.sort(positions);
        StringBuilder sb = new StringBuilder();
        for(Integer position : positions) {
            String str = translationWithPositions.get(position);
            if(position > 1 && !isPonctuationSpacedCharacter(str)) {
                sb.append(" ");
            }
            sb.append(str);
        }
        return finalModifier.decorate(sb.toString().trim());
    }

    private boolean isPonctuationSpacedCharacter(String str) {
        if(str == null) return false;
        if(str.equals(".")) return true;
        if(str.equals(",")) return true;
        if(str.equals(";")) return true;
        return false;
    }

    public void put(int position, String translatedResult) {
        translationWithPositions.put(position, translatedResult);
    }

    @Override
    public String toString() {
        return "Translation{" +
                "translationWithPositions=" + translationWithPositions +
                '}';
    }
}

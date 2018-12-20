package patrologia.translator.translationrules.hebrew;

import patrologia.translator.basicelements.TranslationRule;
import patrologia.translator.conjugation.ConjugationPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 04/10/2017.
 */
public class SofitRootLetter extends TranslationRule {

    private String letterToUpdate;
    private String updatedValue;
    private Integer xLetter; //1 = first letter, 2 = second letter, 3 = third letter, etc.

    public SofitRootLetter(String conjugationName, String ruleParameters) {
        this.conjugationName = conjugationName;
        this.ruleParameters = ruleParameters;
        String[] parts = ruleParameters.split("\\*");
        letterToUpdate = parts[0].substring(0,1);
        updatedValue = parts[1];
        xLetter = Integer.parseInt(parts[0].substring(1));
    }

    @Override
    public List<ConjugationPart> transform(List<ConjugationPart> conjugationPartList) {
        List<ConjugationPart> modifiedList = new ArrayList<>();
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(isLastLetter(letterToUpdate, xLetter, conjugationPart.getValue())) {
                int position = getPosition(letterToUpdate,xLetter, conjugationPart.getValue());
                String modifiedValue = insertAtPosition(position,updatedValue, conjugationPart.getValue());
                ConjugationPart modified = new ConjugationPart(conjugationPart.getConjugationPosition(), modifiedValue, conjugationPart.getUnaccentuedValue(), conjugationPart.getPositionInDefinition());
                modifiedList.add(modified);
            } else  {
                modifiedList.add(conjugationPart);
            }
        }
        return modifiedList;
    }

    private String insertAtPosition(int position, String updatedValue, String value) {
        return value.substring(0,position) + updatedValue;
    }

    private int getPosition(String letterToUpdate, Integer xLetter, String value) {
        int letterFound = 0;
        boolean letterToUpdateIsFoundAtPosition = false;
        int positionFound = 0;
        int indice = 0;
        char[] chars = value.toCharArray();
        for(char c : chars) {
            if(isLetter(c)) {
                letterFound++;
                if(letterToUpdateIsFoundAtPosition) return -1;
            }
            if(letterFound == xLetter  && letterToUpdate.charAt(0) == c) {
                positionFound  = indice;
                letterToUpdateIsFoundAtPosition = true;
                if(indice == (chars.length-1)) return positionFound;
            }
            indice++;
        }
        return positionFound;
    }

    private boolean isLastLetter(String letterToUpdate, Integer xLetter, String value) {
        int letterFound = 0;
        boolean letterToUpdateIsFoundAtPosition = false;
        char[] chars = value.toCharArray();
        int indice = 0;
        for(char c : chars) {
            if(isLetter(c)) {
                letterFound++;
                if(letterToUpdateIsFoundAtPosition) return false;
            }
            if(letterFound == xLetter  && letterToUpdate.charAt(0) == c) {
                letterToUpdateIsFoundAtPosition = true;
                if(indice == (chars.length-1)) return true;
            }
            indice++;
        }
        return letterToUpdateIsFoundAtPosition;
    }

    private boolean isLetter(char c) {
        return Character.isLetter(c);
    }

    @Override
    public String toString() {
        return "SofitRootLetter{" + conjugationName +  " " + ruleParameters + "}";
    }

}

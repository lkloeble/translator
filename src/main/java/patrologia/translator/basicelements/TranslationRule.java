package patrologia.translator.basicelements;

import patrologia.translator.conjugation.ConjugationPart2;
import patrologia.translator.utils.StringUtils;

import java.util.List;

public abstract class TranslationRule {

    protected String conjugationName;
    protected String ruleParameters;

    public abstract List<ConjugationPart2> transform(List<ConjugationPart2> conjugationPartList);

    protected String withoutNumbers(String value) {
        char[] letters = value.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : letters) {
            if(c >= 'a') sb.append(c);
        }
        return sb.toString();
    }

    protected boolean isPositionAllowedForChange(ConjugationPart2 conjugationPart, List<Integer> strictPositions) {
        if(strictPositions.size() == 0) return true;
        return strictPositions.contains(conjugationPart.getStrictPosition());
    }

    protected boolean isAllowedForAnyPosition(List<Integer> indices) {
        return indices.size() == 0;
    }

    protected String unaccentued(String value) {
        return StringUtils.unaccentuate(value);
    }


    public boolean concernsThisTime(String time) {
        return conjugationName.equals(time);
    }
}

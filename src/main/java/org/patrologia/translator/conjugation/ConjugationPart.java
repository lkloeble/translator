package patrologia.translator.conjugation;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.utils.StringUtils;

/**
 * Created by lkloeble on 10/01/2017.
 */
public class ConjugationPart {

    private static final String TO_ERASE_WORK_SUFFIX = "@";
    private static final String TO_ERASE_WORK_PREFIX = new Character(Character.MIN_VALUE).toString();

    private ConjugationPosition conjugationPosition;
    private String value;
    private String unaccentuedValue;
    private Integer positionInDefinition;
    private boolean isNounRelated;
    private CaseNumberGenre caseNumberGenre;

    public ConjugationPart(ConjugationPosition conjugationPosition, String value, String unaccentuedValue, Integer positionInDefinition) {
        this.conjugationPosition = conjugationPosition;
        this.value = cleanValue(value);
        this.unaccentuedValue = StringUtils.unaccentuate(unaccentuedValue);
        this.positionInDefinition = positionInDefinition;
    }

    public ConjugationPart(CaseNumberGenre caseNumberGenre, String value, String unaccentuedValue) {
        this.conjugationPosition = ConjugationPosition.RELATED_TO_NOUN;
        this.caseNumberGenre = caseNumberGenre;
        this.value = value;
        this.unaccentuedValue = StringUtils.unaccentuate(unaccentuedValue);
    }

    private String cleanValue(String value) {
        return value.replace(TO_ERASE_WORK_SUFFIX,"").replace(TO_ERASE_WORK_PREFIX,"");
    }

    public String getValue() {
        return value;
    }

    public Integer getIndice() {
        return conjugationPosition.getIndice();
    }

    public void updateValue(String target, String replacement) {
        value = value.replace(target,replacement);
        unaccentuedValue = unaccentuedValue.replace(target,replacement);
    }

    public boolean isPlural() {
        return conjugationPosition.isPlural();
    }

    public boolean isIndiceThirPlural() {
        return conjugationPosition.equals(ConjugationPosition.PLURAL_THIRD_PERSON);
    }

    public ConjugationPosition getConjugationPosition() {
        return conjugationPosition;
    }

    public Integer getPositionInDefinition() {
        return positionInDefinition;
    }

    public String getUnaccentuedValue() {
        return unaccentuedValue;
    }

    @Override
    public String toString() {
        return "ConjugationPart{" +
                "conjugationPosition=" + conjugationPosition +
                ", value='" + value + '/' + unaccentuedValue + '\'' +
                '}';
    }

    public boolean contains(String toTranslate) {
        return value.equals(toTranslate) || unaccentuedValue.equals(toTranslate);
    }
}

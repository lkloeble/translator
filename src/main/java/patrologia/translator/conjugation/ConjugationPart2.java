package patrologia.translator.conjugation;

import patrologia.translator.casenumbergenre.CaseNumberGenre;

public class ConjugationPart2 {

    private ConjugationPosition conjugationPosition;
    private String value;
    private String unaccentuedValue;
    private int indice;

    public ConjugationPart2(ConjugationPosition conjugationPosition, String value) {
        this.conjugationPosition = conjugationPosition;
        this.value=value;
    }

    public ConjugationPart2(String value, String unaccentuedValue) {
        this.conjugationPosition = ConjugationPosition.RELATED_TO_NOUN;
        this.value=value;
        this.unaccentuedValue = unaccentuedValue;
        this.indice = 0;
    }

    public ConjugationPart2(ConjugationPosition conjugationPosition, String value, String unaccentuedValue, int indice) {
        this.conjugationPosition = conjugationPosition;
        this.value=value;
        this.unaccentuedValue = unaccentuedValue;
        this.indice = indice;
    }

    public String getValue() {
        return value;
    }

    public boolean isIndiceThirPlural() {
        return conjugationPosition.isThirdPlural();
    }

    public boolean isPlural() {
        return conjugationPosition.isPlural();
    }

    public Integer getIndice() {
        return indice;
    }

    public String getUnaccentuedValue() {
        return unaccentuedValue;
    }

    public boolean contains(String toTranslate) {
        return toTranslate.equals(value) || toTranslate.equals(unaccentuedValue);
    }

    public ConjugationPosition getConjugationPosition() {
        return conjugationPosition;
    }

    public void updateValue(String target, String replacement) {
        this.value = value.replace(target,replacement);
        this.unaccentuedValue = unaccentuedValue.replace(target,replacement);
    }

    public Integer getPositionInDefinition() {
        return getConjugationPosition().getIndice();
    }

    @Override
    public String toString() {
        return "ConjugationPart2{" +
                "conjugationPosition=" + conjugationPosition +
                ", value='" + value + '\'' +
                ", unaccentuedValue='" + unaccentuedValue + '\'' +
                ", indice=" + indice +
                '}';
    }
}

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

    public ConjugationPart2(CaseNumberGenre caseNumberGenre, String s, String s1) {
        //TODO
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
        //TODO
        return false;
    }

    public boolean isPlural() {
        //TODO
        return false;
    }

    public Integer getIndice() {
        //TODO
        return conjugationPosition.getIndice();
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
        System.out.println("stop");
        //TODO
    }

    public Integer getPositionInDefinition() {
        //TODO
        return 0;
    }
}

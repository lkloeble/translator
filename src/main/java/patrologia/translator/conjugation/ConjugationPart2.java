package patrologia.translator.conjugation;

import patrologia.translator.casenumbergenre.CaseNumberGenre;

public class ConjugationPart2 {

    private ConjugationPosition conjugationPosition;
    private String value;

    public ConjugationPart2(ConjugationPosition conjugationPosition, String value) {
        this.conjugationPosition = conjugationPosition;
        this.value=value;
    }

    public ConjugationPart2(CaseNumberGenre caseNumberGenre, String s, String s1) {
        //TODO
    }

    public ConjugationPart2(ConjugationPosition valueByPosition, String value, String value1, int i) {
        //TODO
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
        return 0;
    }

    public String getUnaccentuedValue() {
        //TODO
        return null;
    }

    public boolean contains(String toTranslate) {
        //TODO
        return false;
    }

    public ConjugationPosition getConjugationPosition() {
        return conjugationPosition;
    }

    public void updateValue(String target, String replacement) {
        //TODO
    }

    public Integer getPositionInDefinition() {
        //TODO
        return 0;
    }
}

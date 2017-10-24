package org.patrologia.translator.conjugation;

/**
 * Created by lkloeble on 10/01/2017.
 */
public enum ConjugationPosition {

    SINGULAR_FIRST_PERSON(0), SINGULAR_SECOND_PERSON(1), SINGULAR_THIRD_PERSON(2),
    PLURAL_FIRST_PERSON(3), PLURAL_SECOND_PERSON(4), PLURAL_THIRD_PERSON(5);

    private int position;

    ConjugationPosition(int position) {
        this.position = position;
    }

    public static ConjugationPosition getValueByPosition(int searchedPosition) {
        switch (searchedPosition) {
            case  0:
                return SINGULAR_FIRST_PERSON;
            case 1:
                return SINGULAR_SECOND_PERSON;
            case 2:
                return SINGULAR_THIRD_PERSON;
            case 3:
                return PLURAL_FIRST_PERSON;
            case 4:
                return PLURAL_SECOND_PERSON;
            case 5:
                return PLURAL_THIRD_PERSON;
            default:
                return SINGULAR_FIRST_PERSON;
        }
    }

    public int getIndice() {
        return position;
    }

    public boolean isPlural() {
        return position>2;
    }
}

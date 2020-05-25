package patrologia.translator.casenumbergenre.greek;

import patrologia.translator.casenumbergenre.Gender;

public class GreekGender extends Gender {

    public static final int MASCULINE_FIRST_AS = 5;

    public GreekGender(int value) {
        super(value);
    }

    public String name() {
        switch (value) {
            case MASCULINE_FIRST_AS: return "MASCULINE_AS_OU";
            default:return super.name();
        }
    }

}


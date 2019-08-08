package patrologia.translator.basicelements.verb;

public class NullTranslationInformationReplacement2 extends TranslationInformationReplacement2 {

    private static final String NO_DESCRIPTION = "";

    public NullTranslationInformationReplacement2() {
        super(NO_DESCRIPTION);
    }

    public NullTranslationInformationReplacement2(String description) {
        super(description);
    }
}

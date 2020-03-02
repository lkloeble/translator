package patrologia.translator.conjugation;

import patrologia.translator.basicelements.TranslationInformationReplacement;

public class NullTranslationInformationReplacement  extends TranslationInformationReplacement {

    public NullTranslationInformationReplacement() {
        super(NULL_REPLACEMENT_STRING);
    }

    @Override
    public String getTimeException(int indice) {
        return "ZZZZZZ";
    }
}

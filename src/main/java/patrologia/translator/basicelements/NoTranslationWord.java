package patrologia.translator.basicelements;

public class NoTranslationWord extends Word {

    public NoTranslationWord(Language language, String content) {
        super(WordType.NO_TRANSLATION, content, language);
    }
}

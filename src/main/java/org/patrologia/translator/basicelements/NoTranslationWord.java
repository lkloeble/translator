package patrologia.translator.basicelements;

/**
 * Created by lkloeble on 10/10/2016.
 */
public class NoTranslationWord extends Word {

    public NoTranslationWord(Language language, String content) {
        super(WordType.NO_TRANSLATION, content, language);
    }
}

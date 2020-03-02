package patrologia.translator.basicelements;

public class NullAnalysis extends Analysis {

    public NullAnalysis(Language language) {
        super(language, new NullPhrase(language));
    }

    @Override
    public Language getLanguage() {
        return super.getLanguage();
    }

    @Override
    public int nbWords() {
        return super.nbWords();
    }

    @Override
    public String toString() {
        return "NullAnalysis";
    }

}

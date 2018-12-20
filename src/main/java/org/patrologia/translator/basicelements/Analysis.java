package patrologia.translator.basicelements;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public class Analysis {

    private Phrase phrase;
    private Language language;

    public Analysis(Language language, Phrase phrase) {
        this.language = language;
        this.phrase = phrase;
    }

    public Language getLanguage() {
        return language;
    }

    public int nbWords() {
        return phrase.size();
    }

    public WordContainer getWordContainerByPosition(int position) {
        return phrase.getWordContainerAtPosition(position);
    }

    @Override
    public String toString() {
        return "Analysis{" +
                "nbPositions=" + phrase.size() +
                ", language=" + language +
                '}';
    }

    public Phrase getPhrase() {
        return phrase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Analysis analysis = (Analysis) o;

        return phrase.equals(analysis.phrase);

    }

    @Override
    public int hashCode() {
        int result = 31 * phrase.hashCode();
        return result;
    }

}

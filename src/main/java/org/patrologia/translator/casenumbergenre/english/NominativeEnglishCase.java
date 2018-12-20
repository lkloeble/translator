package patrologia.translator.casenumbergenre.english;

public class NominativeEnglishCase extends EnglishCase {

    private static NominativeEnglishCase singleton;

    private NominativeEnglishCase() {}

    public static NominativeEnglishCase getInstance() {
        if(singleton == null) {
            singleton = new NominativeEnglishCase();
        }
        return singleton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NominativeEnglishCase)) return false;

        return  true;//nominatives are equal each others if singleton is corrupted
    }

    @Override
    public String toString() {
        return "NominativeEnglishCase{}";
    }
}

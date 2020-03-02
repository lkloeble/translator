package patrologia.translator.casenumbergenre;

public abstract class CaseFactory {

    public abstract Case getCaseByStringPattern(String pattern, String differencier);

}

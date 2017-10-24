package org.patrologia.translator.casenumbergenre;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public abstract class CaseFactory {

    public abstract Case getCaseByStringPattern(String pattern, String differencier);
}

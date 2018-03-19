package org.patrologia.translator.casenumbergenre.english;

import org.patrologia.translator.casenumbergenre.Case;

public abstract class EnglishCase extends Case {

    public static Case getCaseByName(String caseName) {
        if ("nom".equals(caseName)) {
            return NominativeEnglishCase.getInstance();
        }
        return null;
    }
}
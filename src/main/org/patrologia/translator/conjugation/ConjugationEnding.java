package org.patrologia.translator.conjugation;

import java.util.List;

public class ConjugationEnding {

    private String endingName;
    private List<String> endings;

    public ConjugationEnding(String endingName, List<String> endings) {
        this.endingName = endingName;
        this.endings = endings;
    }

    public String getEndingName() {
        return endingName;
    }

    public List<String> getEndings() {
        return endings;
    }
}

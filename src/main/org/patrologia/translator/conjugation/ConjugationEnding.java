package org.patrologia.translator.conjugation;

import java.util.List;

public class ConjugationEnding {

    private String endingName;
    private List<String> endings;
    private boolean relatedToNoun;

    public ConjugationEnding(String endingName, boolean relatedToNoun, List<String> endings) {
        this.endingName = endingName;
        this.relatedToNoun = relatedToNoun;
        this.endings = endings;
    }

    public String getEndingName() {
        return endingName;
    }

    public List<String> getEndings() {
        return endings;
    }

    public boolean isRelatedToNoun() {
        return relatedToNoun;
    }
}

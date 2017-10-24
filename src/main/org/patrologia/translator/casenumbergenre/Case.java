package org.patrologia.translator.casenumbergenre;

import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.casenumbergenre.german.GermanCaseFactory;
import org.patrologia.translator.casenumbergenre.greek.GreekCaseFactory;
import org.patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import org.patrologia.translator.casenumbergenre.latin.LatinCase;
import org.patrologia.translator.casenumbergenre.romanian.RomanianCaseFactory;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public abstract class Case {

    public static Case getCaseByName(String caseName, String differentier, Language language) {
        switch (language) {
            case LATIN:
                return LatinCase.getCaseByName(caseName);
            case GERMAN:
                return new GermanCaseFactory().getCaseByStringPattern(caseName, differentier);
            case GREEK:
                return new GreekCaseFactory().getCaseByStringPattern(caseName, differentier);
            case HEBREW:
                return new HebrewCaseFactory().getCaseByStringPattern(caseName, differentier);
            case ROMANIAN:
                return new RomanianCaseFactory().getCaseByStringPattern(caseName, differentier);
            default:
                return null;
        }
    }

    public static Case getSingleCase(List<CaseNumberGenre> possibleCaseNumbers) {
        Set<Case> caseSet = new HashSet<>();
        for(CaseNumberGenre cng : possibleCaseNumbers) {
            caseSet.add(cng.getCase());
        }
        List<Case> caseList = new ArrayList<>(caseSet);
        Collections.sort(caseList, new CaseComparator());
        if(caseList.size() == 0)  return new NullCase();
        return caseList.get(0);
    }

    public boolean compareOnlyType(Case otherCase) {
        return toStringCase().compareTo(otherCase.toStringCase()) == 0;
    }

    protected String toStringCase() {
        return "toimplement";
    }

    protected String getDifferentier() { return "toimplement";}

    public String getTrigramForCase() { return "toimplement";}

    protected void updateDifferentier(String newDifferentier) {

    }
}

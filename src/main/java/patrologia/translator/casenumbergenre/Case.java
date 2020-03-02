package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.Language;
import patrologia.translator.casenumbergenre.english.EnglishCaseFactory;
import patrologia.translator.casenumbergenre.german.GermanCaseFactory;
import patrologia.translator.casenumbergenre.greek.GreekCaseFactory;
import patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import patrologia.translator.casenumbergenre.latin.LatinCase;
import patrologia.translator.casenumbergenre.romanian.RomanianCaseFactory;

import java.util.*;

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
            case ENGLISH:
                return new EnglishCaseFactory().getCaseByStringPattern(caseName,differentier);
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

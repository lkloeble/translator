package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;

import java.util.*;

public class CaseNumberGenre implements Comparable {

    private Case _case;
    private Number number;
    private Gender gender;
    private boolean invariable;

    private static final String EMPTY_DIFFERENTIER = "";

    public CaseNumberGenre(Case _case, Number number, Gender gender) {
        this._case = _case != null ? _case : new NullCase();
        this.number = number;
        this.gender = gender != null ? gender : new Gender(Gender.NEUTRAL);
    }

    public int compareTo(Object o) {
        return toString().compareTo(o.toString());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaseNumberGenre)) return false;

        CaseNumberGenre that = (CaseNumberGenre) o;

        if (!_case.equals(that._case)) return false;
        if (number != that.number) return false;
        return gender.value == that.gender.value;

    }

    @Override
    public int hashCode() {
        int result = _case.hashCode();
        result = 31 * result + number.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CaNumbGen{" +
                " ca=" + _case +
                ", ge=" + gender +
                ", nu=" + number +
                '}';
    }

    public String getDifferentier() {
        return _case.getDifferentier();
    }

    public static CaseNumberGenre electMostProbable(List<CaseNumberGenre> possibleCaseNumbers, Phrase phrase, int positionInPhrase, Gender gender) {
        Set<CaseNumberGenre> matchingOnesSet = new HashSet<>();
        for(CaseNumberGenre caseNumber : possibleCaseNumbers) {
            if(caseNumber._case.toString().toLowerCase().contains("nom")) {
                matchingOnesSet.add(caseNumber);
            }
        }
        List<CaseNumberGenre> matchingOnes = new ArrayList<>(matchingOnesSet);
        if(matchingOnes.size() == 1) {
            return matchingOnes.get(0);
        } else if(matchingOnes.size() > 1) {
            List<Word> surroundingWords = phrase.getNearWords(positionInPhrase);
            List<Number> numbers = phrase.getNumbers(surroundingWords);
            int nbPlural = Number.numberOf(numbers, Number.PLURAL);
            int nbSingular = Number.numberOf(numbers, Number.SINGULAR);
            Number numberMost = nbPlural > nbSingular ? Number.PLURAL : Number.SINGULAR;
            Collections.sort(matchingOnes);
            return electMostProbable(matchingOnes, numberMost);
        } else if(matchingOnes.size() == 0) {
            Number singleNumber = Number.getSingleNumber(possibleCaseNumbers);
            Case singleCase = Case.getSingleCase(possibleCaseNumbers);
            Gender singleGender = Gender.getSingleGender(possibleCaseNumbers, gender);
            return new CaseNumberGenre(singleCase, singleNumber, singleGender);
        } else {
            return possibleCaseNumbers.get(0);
        }
    }

    public static CaseNumberGenre electMostProbable(List<CaseNumberGenre> possibleCaseNumbers, Number number) {
        return possibleCaseNumbers.stream().filter(possibleCaseNumber -> possibleCaseNumber.number.equals(number)).findFirst().get();
    }

    public static CaseNumberGenre electMostProbable(List<CaseNumberGenre> possibleCaseNumbers, String preferedElected, Language language) {
        Collections.reverse(possibleCaseNumbers);
        Case caseByName = Case.getCaseByName(preferedElected, EMPTY_DIFFERENTIER, language);
        if (caseByName != null) {
            CaseNumberGenre caseNumberGenre = possibleCaseNumbers.stream().filter(possibleCaseNumber -> possibleCaseNumber.getCase().getCaseByName(preferedElected, EMPTY_DIFFERENTIER, language) != null).findFirst().get();
            caseNumberGenre.overrideCase(caseByName);
            return caseNumberGenre;
        }
        return new NullCaseNumberGenre();
    }

    private void overrideCase(Case aCase) {
        this._case = aCase;
    }

    public boolean isPlural() {
        return number.equals(Number.PLURAL);
    }

    public boolean isGenitive() {
        return _case != null && _case.toString().toLowerCase().contains("genit");
    }

    public boolean isConstructedState() {
        return _case != null && _case.toString().toLowerCase().contains("constr");
    }

    public boolean isDative() {
        return _case != null && _case.toString().toLowerCase().contains("dat");
    }
    public boolean isAccusative() {
        return _case != null && _case.toString().toLowerCase().contains("acc");
    }

    public Number getNumber() {
        return number;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isInvariable() {
        return invariable;
    }

    public void setInvariable(boolean invariable) {
        this.invariable = invariable;
    }

    public Case getCase() {
        return _case;
    }

    public boolean isAblative() {
        return _case != null && _case.toString().toLowerCase().contains("abl");
    }


}

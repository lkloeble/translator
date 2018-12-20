package patrologia.translator.basicelements.noun;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.NullCaseNumberGenre;
import patrologia.translator.casenumbergenre.latin.AblativeLatinCase;
import patrologia.translator.casenumbergenre.latin.DativeLatinCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public class Noun extends Word {

    private CaseNumberGenre electedCaseNumber;
    private List<CaseNumberGenre> possibleCaseNumbers = new ArrayList<CaseNumberGenre>();
    private Gender gender;
    private boolean adjective;
    private boolean withoutArticle;
    private String genderFamily;
    private List<String> specifiRules;
    private String declension;

    public Noun(Language language, String initialValue, String root, List<CaseNumberGenre> possibleCaseNumbers, Gender gender, String genderFamily, String declension, List<String> specificRules) {
        super(WordType.NOUN, initialValue, root, language);
        this.gender = gender;
        this.possibleCaseNumbers.addAll(possibleCaseNumbers);
        this.genderFamily = genderFamily;
        this.specifiRules = specificRules;
        this.declension = declension;
        this.preferedTypeSymbol = 'N';
    }

    public void setElectedCaseNumber(CaseNumberGenre electedCaseNumber) {
        if (this.electedCaseNumber != null) return;
        this.electedCaseNumber = electedCaseNumber;
    }

    public void addPossibleCaseNumber(CaseNumberGenre caseNumber) {
        if (!possibleCaseNumbers.contains(caseNumber)) {
            possibleCaseNumbers.add(caseNumber);
        }
    }

    public Noun(Noun toClone) {
        super(toClone);
        this.electedCaseNumber = toClone.electedCaseNumber;
        this.possibleCaseNumbers = toClone.possibleCaseNumbers;
        this.gender = toClone.gender;
        this.adjective = toClone.adjective;
        this.withoutArticle = toClone.withoutArticle;
        this.genderFamily = toClone.genderFamily;
        this.specifiRules = toClone.specifiRules;
        this.declension = toClone.declension;
    }

    public boolean hasPossibleCaseNumber(CaseNumberGenre expectedCaseNumber) {
        for (CaseNumberGenre caseNumber : possibleCaseNumbers) {
            if (caseNumber.equals(expectedCaseNumber)) {
                return true;
            }
        }
        return false;
    }

    public List<CaseNumberGenre> getPossibleCaseNumbers() {
        Collections.sort(possibleCaseNumbers);
        return possibleCaseNumbers;
    }

    public boolean hasPossibleCase(Case aCase) {
        for(CaseNumberGenre caseNumberGenre : possibleCaseNumbers) {
            Case thisCase = caseNumberGenre.getCase();
            if(thisCase.getTrigramForCase().equals(aCase.getTrigramForCase())) return  true;
        }
        return false;
    }

    public CaseNumberGenre getElectedCaseNumber() {
        if (electedCaseNumber == null && preferedElected != null) {
            electedCaseNumber = CaseNumberGenre.electMostProbable(possibleCaseNumbers, preferedElected, language);
        }
        if (electedCaseNumber == null) super.selfElect();
        return electedCaseNumber;
    }

    public CaseNumberGenre getNoDepthElectedCaseNumber() {
        if (electedCaseNumber == null) new NullCaseNumberGenre();
        return electedCaseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Noun)) return false;
        if (!super.equals(o)) return false;

        Noun noun = (Noun) o;

        if (electedCaseNumber != null ? !electedCaseNumber.equals(noun.electedCaseNumber) : noun.electedCaseNumber != null)
            return false;
        if (possibleCaseNumbers != null ? !getPossibleCaseNumbers().equals(noun.getPossibleCaseNumbers()) : noun.getPossibleCaseNumbers() != null)
            return false;
        return gender == noun.gender;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (electedCaseNumber != null ? electedCaseNumber.hashCode() : 0);
        result = 31 * result + (possibleCaseNumbers != null ? possibleCaseNumbers.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        return result;
    }

    public void selfElect() {
        if (electedCaseNumber != null) {
            return;
        }
        if (possibleCaseNumbers != null && possibleCaseNumbers.size() == 1) {
            electedCaseNumber = possibleCaseNumbers.get(0);
            //log();
            //System.out.println("ELECTED 1 : " + electedCaseNumber + " for " + initialValue + " " + gender);
            return;
        }
        if (hashAPossibleGenitive(possibleCaseNumbers) && followsANonAdjectiveNoun()) {
            electedCaseNumber = getGenitive(possibleCaseNumbers);
            //System.out.println("ELECTED 2 : " + electedCaseNumber + " for " + initialValue + " " + gender);
            return;
        }
        if (hashAPossibleGenitive(possibleCaseNumbers) && uniqueVerbIsSingular()) {
            electedCaseNumber = getGenitive(possibleCaseNumbers);
            //System.out.println("ELECTED 3 : " + electedCaseNumber + " for " + initialValue + " " + gender);
            return;
        }
        electedCaseNumber = CaseNumberGenre.electMostProbable(possibleCaseNumbers, phrase, positionInPhrase, gender);
        //log();
        //System.out.println("ELECTED 4 : " + electedCaseNumber + " for " + initialValue + " " + gender);
    }

    private void log() {
        /*
        System.out.println("****  : " + initialValue + " " + gender);
        for(CaseNumberGenre cng : possibleCaseNumbers) {
            System.out.println(cng);
        }
        System.out.println("***");
        */
    }

    private boolean uniqueVerbIsSingular() {
        if (phrase.getNumberOfWordsByType(WordType.VERB) > 1) {
            return false;
        }
        Verb uniqueVerb = phrase.getUniqueVerb();
        return uniqueVerb != null && !uniqueVerb.isPlural(uniqueVerb.getInitialValue());
    }

    public boolean isNotAnAdjective() {
        return !adjective;
    }

    private CaseNumberGenre getGenitive(List<CaseNumberGenre> possibleCaseNumbers) {
        return possibleCaseNumbers.stream().filter(caseNumber -> caseNumber.isGenitive()).findAny().get();
    }

    private boolean hashAPossibleGenitive(List<CaseNumberGenre> possibleCaseNumbers) {
        return possibleCaseNumbers.stream().filter(caseNumber -> caseNumber.isGenitive()).count() > 0;
    }

    private boolean followsANonAdjectiveNoun() {
        if (positionInPhrase == STARTING_COUNTINDICE_IN_SENTENCE) return false;
        Word previous = phrase.getWordContainerAtPosition(positionInPhrase - 1).getUniqueWord();
        return previous.isNoun() && ((Noun) previous).isNotAnAdjective();
    }

    public boolean isGenitive() {
        if(electedCaseNumber != null && electedCaseNumber.isGenitive()) return true;
        return hashAPossibleGenitive(possibleCaseNumbers);
    }

    /*
    @Override
    public String toString() {
        return "Noun{" + super.toString() +
                "electedCaseNumber=" + electedCaseNumber +
                ", possibleCaseNumbers=" + possibleCaseNumbers +
                ", gender=" + gender +
                '}';
    }
    */

    public String toString() {
        return "Noun{" + initialValue +
                " elect=" + electedCaseNumber +
                ", possibleCaseNumbers=" + possibleCaseNumbers +
                ", gender=" + gender +
                '}';
    }

    public void setAdjective() {
        adjective = true;
    }

    public Gender getGender() {
        if (gender == null) {
            return new Gender(Gender.UNKNOWN);
        }
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPossibleCaseNumbers(List<CaseNumberGenre> possibleCaseNumbers) {
        this.possibleCaseNumbers = possibleCaseNumbers;
    }

    public void setNoArticle() {
        this.withoutArticle = true;
    }

    public void setWithArticle() {
        this.withoutArticle = true;
    }

    public boolean isWithoutArticle() {
        return withoutArticle;
    }

    public boolean isPlural() {
        if (electedCaseNumber != null) return electedCaseNumber.isPlural();
        for (CaseNumberGenre caseNumberGenre : possibleCaseNumbers) {
            if (!caseNumberGenre.isPlural()) {
                return false;
            }
        }
        return true;
    }

    public String getGenderFamily() {
        return genderFamily;
    }

    public boolean hasSpecificRule(String rule) {
        return specifiRules.contains(rule);
    }

    public boolean isNotPrecededByAPrepositionWithRule() {
        if(phrase == null) return false;
        WordContainer precedingWordContainer = phrase.getWordContainerAtPosition(positionInPhrase - 1);
        boolean preposition = precedingWordContainer.getWordByType(WordType.PREPOSITION).isPreposition();
        if (!preposition) return true;
        Preposition precedingPreposition = (Preposition) precedingWordContainer.getWordByType(WordType.PREPOSITION);
        return precedingPreposition.getRules().size() == 0;
    }

    public boolean isNotPrecedByAnAdjective() {
        WordContainer precedingWordContainer = phrase.getWordContainerAtPosition(positionInPhrase - 1);
        boolean noun = precedingWordContainer.getWordByType(WordType.NOUN).isNoun();
        if (!noun) return true;
        Noun precedingNoun = (Noun) precedingWordContainer.getWordByType(WordType.NOUN);
        return precedingNoun.isNotAnAdjective();
    }

    public void setPreferedElected(String preferedElected) {
        this.preferedElected = preferedElected;
    }

    public boolean IsNotPrecededByADativeNoun() {
        return IsNotPrecededByADeclinedNoun(new DativeLatinCase(null));
    }

    public boolean IsNotPrecededByAnAblativeNoun() {
        return IsNotPrecededByADeclinedNoun(new AblativeLatinCase(null));
    }

    private boolean IsNotPrecededByADeclinedNoun(Case aCase) {
        WordContainer precedingWordContainer = phrase.getWordContainerAtPosition(positionInPhrase - 1);
        boolean noun = precedingWordContainer.getWordByType(WordType.NOUN).isNoun();
        if (!noun) return true;
        Noun precedingNoun = (Noun) precedingWordContainer.getWordByType(WordType.NOUN);
        List<CaseNumberGenre> possibleCaseNumbers = precedingNoun.getPossibleCaseNumbers();
        for (CaseNumberGenre cng : possibleCaseNumbers) {
            if (cng.getCase().equals(aCase)) return true;
        }
        return true;
    }

    public boolean isAdjective() {
        return adjective;
    }

    public String getDeclension() {
        return declension;
    }

    public void erasePossibleCaseNumberWithoutGenre(CaseNumberGenre eraseCaseNumber) {
        //System.out.println("on demande un erase de " + eraseCaseNumber + " pour " + initialValue + " " + gender);
        //System.out.println("on a déjà un elected qui est " + electedCaseNumber + " pour " + initialValue + " " + gender);
        if (possibleCaseNumbers == null || possibleCaseNumbers.size() == 0) return;
        List<CaseNumberGenre> newCaseNumberGenre = new ArrayList<>();
        for (CaseNumberGenre caseNumberGenre : getPossibleCaseNumbers()) {
            //System.out.println("on étudie le erase de " + caseNumberGenre);
            if(caseNumberGenre.equals(electedCaseNumber)) {
                newCaseNumberGenre.add(caseNumberGenre);
                //System.out.println("Keep1 " + caseNumberGenre);
                continue;
            }
            if (caseNumberGenre.getCase().equals(eraseCaseNumber.getCase()) && caseNumberGenre.getNumber().equals(eraseCaseNumber.getNumber())) {
                //System.out.println("Remove " + caseNumberGenre);
                continue;
            }
            //System.out.println("Keep2 " + caseNumberGenre);
            newCaseNumberGenre.add(caseNumberGenre);
        }
        possibleCaseNumbers = newCaseNumberGenre;
        //log();
    }

    public boolean hasElectedWithoutCompute() {
        return electedCaseNumber != null;
    }

    public String getCases() {
        StringBuilder sb = new StringBuilder();
        for(CaseNumberGenre caseNumberGenre : possibleCaseNumbers) {
            sb.append(caseNumberGenre.getCase()).append("-");
        }
        return sb.toString();
    }

    public boolean hasOnlyOneOfTheseCases(List<Case> cases) {
        for(Case _case : cases) {
           if (!hasOnlyThisCase(_case)) return false;
        }
        return true;
    }

    private boolean hasOnlyThisCase(Case _case) {
        for(CaseNumberGenre caseNumberGenre : possibleCaseNumbers) {
            if(!caseNumberGenre.getCase().compareOnlyType(_case)) return false;
        }
        return true;
    }

    protected String unaccentuedRoot() {
        return unaccentued(root);
    }

    protected String unaccentuedInitialValue() {
        return unaccentued(initialValue);
    }

    private String unaccentued(String value) {
        char[] letters = value.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : letters) {
            if(c >= 'a') sb.append(c);
        }
        return sb.toString();
    }

    public boolean isInvariable() {
        return declension.equals("inv");
    }


    public boolean hasPossibleCaseNumbers() {
        return possibleCaseNumbers.size() > 0;
    }
}
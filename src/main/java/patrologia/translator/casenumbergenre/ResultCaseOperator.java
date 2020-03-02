package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.hebrew.HebrewCase;

import java.util.List;

public class ResultCaseOperator {

    private Noun noun;
    private CaseOperator caseOperator;

    public ResultCaseOperator(Noun noun, CaseOperator caseOperator) {
        this.noun = new Noun(noun);
        this.caseOperator = CaseOperator.buildFromCaseOperator(caseOperator);
        updateCaseOperatorByExtractOfTargetSubstitutionCase();
    }

    private void updateCaseOperatorByExtractOfTargetSubstitutionCase() {
        List<CaseNumberGenre> possibleCaseNumbers = noun.getPossibleCaseNumbers();
        for(CaseNumberGenre cng : possibleCaseNumbers) {
            if(cng.getCase().compareOnlyType(cng.getCase())) {
                caseOperator.updateCaseDifferentier((HebrewCase)cng.getCase());
            }
        }
    }

    public String getNounValue() {
        return noun.getInitialValue();
    }

    public String getDifferentierOfSubstitution() {
        return caseOperator.getCase().getDifferentier().replace("(","").replace(")","");
    }

    @Override
    public String toString() {
        return "ResultCaseOperator{" +
                "noun=" + noun.getInitialValue() +
                " case=" + caseOperator.getCase().getTrigramForCase() +
                " differentier=" + caseOperator.getCase().getDifferentier() +
                '}';
    }

}

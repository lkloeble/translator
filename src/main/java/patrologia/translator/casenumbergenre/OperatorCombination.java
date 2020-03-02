package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.hebrew.HebrewCase;

import java.util.ArrayList;
import java.util.List;

public class OperatorCombination extends CaseOperator  {

    List<CaseOperator> operators = new ArrayList<>();

    public OperatorCombination(HebrewCase _case, boolean contains, int priority) {
        super(_case, contains, priority);
        addOperator(new AvoidCaseOperator(_case));
    }

    public OperatorCombination(CaseOperator caseOperator) {
        super(caseOperator._case,false,5);
        addOperator(caseOperator);
    }

    public void addOperator(CaseOperator caseOperator) {
        operators.add(caseOperator);
    }

    public boolean isCompliant(Noun noun) {
        boolean result = true;
        for(CaseOperator caseOperator : operators)  {
            result &= caseOperator.isCompliant(noun);
        }
        return result;
    }

    public void clearAll() {
        operators.clear();
    }

}

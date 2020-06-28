package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.hebrew.HebrewCase;

import java.util.Collections;

public abstract class CaseOperator implements Comparable {

    protected HebrewCase _case;
    protected boolean contains;
    protected int priority;

    public CaseOperator(HebrewCase _case, boolean contains, int priority) {
        this._case = HebrewCase.getClone(_case);
        this.contains = contains;
        this.priority = priority;
    }

    public CaseOperator(HebrewCase _case) {
        this._case = HebrewCase.getClone(_case);
    }

    public boolean isCompliant(Noun noun) {
        if(_case.compareOnlyType(new NullCase())) return true;
        if(contains) {
            return noun.hasOnlyOneOfTheseCases(Collections.singletonList(_case));
        } else {
            return !noun.hasOnlyOneOfTheseCases(Collections.singletonList(_case));
        }
    }

    public Case getCase() {
        return _case;
    }

    public int getPriority() {
        return this.priority;
    }

    public void updateCaseDifferentier(HebrewCase aCase) {
        _case = HebrewCase.getClone(aCase);
    }

    @Override
    public String toString() {
        return "CaseOperator{" +
                "_case=" + _case +
                ", contains=" + contains +
                ", priority=" + priority +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof CaseOperator) {
            CaseOperator other = (CaseOperator)o;
            if(other.getPriority() > getPriority()) {
                return -1;
            } else {
                return 1;
            }
        } else {
            return 0;
        }
    }

    public static CaseOperator buildFromCaseOperator(CaseOperator caseOperator) {
        if(caseOperator instanceof AvoidCaseOperator) {
            return new AvoidCaseOperator(caseOperator);
        } else if(caseOperator instanceof  KeepCaseOperator) {
            return new KeepCaseOperator(caseOperator);
        } else if(caseOperator instanceof OperatorCombination) {
            return new OperatorCombination(caseOperator);
        }
        return null;
    }

    public boolean isConstructed() {
        return _case.getTrigramForCase().equals("cst");
    }
}

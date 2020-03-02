package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.hebrew.HebrewCase;

import java.util.Collections;

public class KeepCaseOperator  extends CaseOperator {

    public KeepCaseOperator(HebrewCase hebrewCase) {
        super(hebrewCase,true,10);
    }

    public KeepCaseOperator(CaseOperator caseOperator) {
        super(new HebrewCase(caseOperator._case),true,10);
    }

    public boolean isCompliant(Noun noun) {
        return noun.hasOnlyOneOfTheseCases(Collections.singletonList(_case));
    }

    @Override
    public String toString() {
        return "keepCaseOperator : case=" + this._case;
    }
}

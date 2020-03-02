package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.hebrew.HebrewCase;

import java.util.Collections;

public class AvoidCaseOperator  extends CaseOperator {

    public AvoidCaseOperator(HebrewCase hebrewCase) {
        super(hebrewCase,false,5);
    }

    public AvoidCaseOperator(CaseOperator caseOperator) {
        super(new HebrewCase(caseOperator._case),false,5);
    }

    public boolean isCompliant(Noun noun) {
        return !noun.hasOnlyOneOfTheseCases(Collections.singletonList(_case));
    }

    @Override
    public String toString() {
        return "avoidCaseOperator : case=" + this._case;
    }
}

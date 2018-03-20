package org.patrologia.translator.casenumbergenre;

import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.casenumbergenre.hebrew.HebrewCase;

import java.util.Collections;

/**
 * Created by lkloeble on 10/05/2017.
 */
public class AvoidCaseOperator extends CaseOperator {

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

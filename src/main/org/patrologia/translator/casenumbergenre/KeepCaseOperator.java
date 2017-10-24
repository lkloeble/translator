package org.patrologia.translator.casenumbergenre;

import org.patrologia.translator.basicelements.Noun;
import org.patrologia.translator.casenumbergenre.hebrew.HebrewCase;

import java.util.Collections;

/**
 * Created by lkloeble on 10/05/2017.
 */
public class KeepCaseOperator extends CaseOperator {

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

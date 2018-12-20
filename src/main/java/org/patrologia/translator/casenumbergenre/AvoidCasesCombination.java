package patrologia.translator.casenumbergenre;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.hebrew.HebrewCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lkloeble on 10/05/2017.
 */
public class AvoidCasesCombination extends CaseOperator {

    List<HebrewCase> cases = new ArrayList<>();

    public AvoidCasesCombination(HebrewCase _case) {
        super(_case, false, 5);
        cases.add(_case);
    }

    public void addCase(HebrewCase hebrewCase) {
        cases.add(hebrewCase);
    }

    public boolean isCompliant(Noun noun) {
        boolean result = true;
        for(HebrewCase hebrewCase : cases)  {
            result &= !noun.hasOnlyOneOfTheseCases(Collections.singletonList(hebrewCase));
        }
        return result;
    }

}

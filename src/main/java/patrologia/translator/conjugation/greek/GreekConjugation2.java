package patrologia.translator.conjugation.greek;

import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

public class GreekConjugation2 extends Conjugation2 {

    public GreekConjugation2(String conjugationName, List<String> conjugationDescription, DeclensionFactory declensionFactory) {
        this.conjugationName=conjugationName;
        this.declensionFactory = declensionFactory;
        processConjugationDescription(conjugationDescription);
        processRelationToNoun(conjugationDescription);
    }

}

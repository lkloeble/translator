package patrologia.translator.conjugation.latin;

import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

public class LatinConjugation2 extends Conjugation2 {

    public LatinConjugation2(String conjugationName, List<String> conjugationDescription, DeclensionFactory declensionFactory) {
        this.conjugationName=conjugationName;
        this.declensionFactory = declensionFactory;
        processConjugationDescription(conjugationDescription);
        processRelationToNoun(conjugationDescription);
    }

}

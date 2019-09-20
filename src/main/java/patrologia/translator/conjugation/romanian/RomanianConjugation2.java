package patrologia.translator.conjugation.romanian;

import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

public class RomanianConjugation2 extends Conjugation2 {

    public RomanianConjugation2(String conjugationName, List<String> conjugationDescription, DeclensionFactory declensionFactory) {
        this.conjugationName=conjugationName;
        this.declensionFactory = declensionFactory;
        processConjugationDescription(conjugationDescription);
        processRelationToNoun(conjugationDescription);
    }



}
package patrologia.translator.conjugation.romanian;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.conjugation.VerbDefinition;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

public class RomanianConjugation2 extends Conjugation2 {

    public RomanianConjugation2(String conjugationName, List<String> conjugationDescription, DeclensionFactory declensionFactory) {
        this.conjugationName=conjugationName;
        this.declensionFactory = declensionFactory;
        processConjugationDescription(conjugationDescription);
        processRelationToNoun(conjugationDescription);
    }


    public RomanianConjugation2(List<String> strings, VerbDefinition verbDefinition, NounRepository nounRepository) {
        //TODO
    }
}

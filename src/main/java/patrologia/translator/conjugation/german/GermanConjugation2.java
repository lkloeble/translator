package patrologia.translator.conjugation.german;

import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

public class GermanConjugation2 extends Conjugation2  {

        public GermanConjugation2(String conjugationName, List<String> conjugationDescription, DeclensionFactory declensionFactory) {
            this.conjugationName=conjugationName;
            this.declensionFactory = declensionFactory;
            processConjugationDescription(conjugationDescription);
            processRelationToNoun(conjugationDescription);
        }

}

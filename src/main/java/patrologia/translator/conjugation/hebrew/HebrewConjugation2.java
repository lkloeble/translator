package patrologia.translator.conjugation.hebrew;

import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

public class HebrewConjugation2 extends Conjugation2 {

    private static final String EMPTY_SEPARATOR = "";
    private static final String MARK_FOR_EMPTY_SEPARATOR = "@";

    public HebrewConjugation2(String conjugationName, List<String> conjugationDescription, DeclensionFactory declensionFactory) {
        this.conjugationName=conjugationName;
        this.declensionFactory = declensionFactory;
        processConjugationDescription(conjugationDescription);
        processRelationToNoun(conjugationDescription);
    }

    @Override
    public String appendTermination(String root, String value, String separator) {
        StringBuilder sb = new StringBuilder();
        String[] prefixSuffix = value.split("\\*");
        if(prefixSuffix.length == 1) {
            prefixSuffix = new String[]{EMPTY_SEPARATOR,EMPTY_SEPARATOR,value};
        }
        String prefix = prefixSuffix[1];
        String suffix = prefixSuffix[2];
        suffix = suffix.equals(MARK_FOR_EMPTY_SEPARATOR) ? EMPTY_SEPARATOR : suffix;
        sb.append(prefix).append(root).append(suffix).append(separator);
        return sb.toString();
    }

    @Override
    public String appendTerminationWithMultipleValues(String root, String partValue) {
        return appendTermination(root,partValue,EMPTY_SEPARATOR);
    }

}

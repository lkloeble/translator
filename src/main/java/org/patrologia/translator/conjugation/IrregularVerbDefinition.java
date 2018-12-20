package patrologia.translator.conjugation;

import java.util.List;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 */
public abstract class IrregularVerbDefinition extends VerbDefinition {

    protected String[] nameAndConjugations;
    protected String name;
    protected List<String> conjugationsList;
    protected String conjugations;
    protected List<String> formsList;

    public String[] getNameAndConjugations() {
        return nameAndConjugations;
    }

    public String getName() {
        return name;
    }

    public List<String> getConjugationsList() {
        return conjugationsList;
    }

    public String getConjugations() {
        return conjugations;
    }

    public List<String> getFormsList() {
        return formsList;
    }

    public abstract void extractForms(String form);
}

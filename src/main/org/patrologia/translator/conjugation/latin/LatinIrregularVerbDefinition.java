package org.patrologia.translator.conjugation.latin;

import org.patrologia.translator.conjugation.IrregularVerbDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 */
public class LatinIrregularVerbDefinition extends IrregularVerbDefinition {

    private String definition;

    public LatinIrregularVerbDefinition(String definition) {
        this.definition = definition;
        extractValuesFromDefinition();
    }

    private void extractValuesFromDefinition() {
        String[] nameForms = definition.split("@");
        root = nameForms[0];
        String[] forms = nameForms[1].split("%");
        formsList = removeIrregularMention(Arrays.asList(forms));
    }

    public void extractForms(String form) {
        nameAndConjugations = form.split("=");
        name = removeBrackets(nameAndConjugations[0]);
        conjugations = removeBrackets(nameAndConjugations[1]);
        conjugationsList = Arrays.asList(conjugations.split(","));
    }

    private List<String> removeIrregularMention(List<String> formsList) {
        return formsList.stream().filter(form -> !form.contains("IRREGULAR"))
                .collect(Collectors.toList());
    }


}

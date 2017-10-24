package org.patrologia.translator.basicelements;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 23/09/2015.
 */
public class DemonstrativeRepository {

    private Map<String, List<String>> demonstrativeForms = new HashMap<>();
    private List<String> allForms = new ArrayList<>();
    private Language language;

    public DemonstrativeRepository(Language language, String... definitions) {
        this.language = language;
        initializeMap(Arrays.asList(definitions));
    }

    private void initializeMap(List<String> definitions) {
        definitions.stream().forEach(definition -> addForms(definition));
    }

    private void addForms(String definition) {
        if(definition ==  null || definition.length() == 0) return;
        String[] demonstrativeAndForms = definition.split("@");
        String demonstrative = demonstrativeAndForms[0];
        String allFormsForThisDemonstrative = demonstrativeAndForms[1];
        List<String> forms = Arrays.asList(allFormsForThisDemonstrative.split(","));
        demonstrativeForms.put(demonstrative, forms);
        allForms.add(demonstrative);
        forms.stream().forEach(form -> allForms.add(form));
    }

    public List<String> getAllForms(String latin) {
        return demonstrativeForms.get(latin) != null ? demonstrativeForms.get(latin) : Collections.EMPTY_LIST;
    }

    public boolean hasDemonstrative(String initialValue) {
        return allForms.contains(initialValue);
    }

    public Demonstrative getDemonstrative(String initialValue) {
        return new Demonstrative(language, getFirstForm(initialValue), initialValue);
    }

    private String getFirstForm(String initialValue) {
        return demonstrativeForms.entrySet().stream().filter(v -> hasValue(v.getValue(), initialValue)).findAny().get().getKey();
    }

    private boolean hasValue(List<String> allForms, String initialValue) {
        return allForms.contains(initialValue);
    }
}

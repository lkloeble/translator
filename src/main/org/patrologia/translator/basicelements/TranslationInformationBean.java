package org.patrologia.translator.basicelements;

import org.patrologia.translator.conjugation.RootedConjugation;
import org.patrologia.translator.linguisticimplementations.DefaultLanguageSelector;
import org.patrologia.translator.linguisticimplementations.SpecificLanguageSelector;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 08/09/2015.
 */
public class TranslationInformationBean {

    private static final List<String> SOFIT_LETTERS = Arrays.asList(new String[]{"k", "m", "n", "p"});
    private static final String SOFIT_END = "000";
    private static final String EMPTY_STRING = "";
    private String root;
    private Map<String, RootedConjugation> nameForms = new HashMap<String, RootedConjugation>();
    private TranslationReplacements customReplacements;

    public TranslationInformationBean(String root, TranslationInformationReplacement... customReplacements) {
        this.root = root;
        this.customReplacements = new TranslationReplacements(Arrays.asList(customReplacements));
    }

    public String getRoot() {
        return root;
    }

    public Map<String, RootedConjugation> getNameForms() {
        return nameForms;
    }

    public List<String> allForms() {
        if (nameForms.size() == 0) {
            return Collections.EMPTY_LIST;
        }
        List<String> forms = new ArrayList<>();
        nameForms.values().stream().forEach(form -> forms.addAll(form.allForms()));
        return addSofit(forms);
    }

    private List<String> addSofit(List<String> forms) {
        Set<String> allForms = new HashSet<>();
        int rootSize = getRootSize(forms);
        for(String form : forms) {
            if(form.length() == rootSize && SOFIT_LETTERS.contains(lastLetter(form,rootSize))) {
                allForms.add(form + SOFIT_END);
            }
            allForms.add(form);
        }
        return new ArrayList<>(allForms);
    }

    private String lastLetter(String form, int rootSize) {
        if(form.length() == rootSize) {
            return new Character(form.charAt(rootSize-1)).toString();
        }
        return EMPTY_STRING;
    }

    private int getRootSize(List<String> list) {
        int size = 5000;
        for(String s : list) {
            if(s.length() < size) size = s.length();
        }
        return size;
    }

    public void addForm(String name, RootedConjugation rootedConjugation) {
        if (customReplacements.hasThisNameAsReplacement(name)) {
            String s = customReplacements.correctFormName(name, rootedConjugation);
            s = "rootedConjugation";
        }
        nameForms.put(name, rootedConjugation);
    }

    @Override
    public String toString() {
        return "TranslationInformationBean{" +
                "root='" + root + '\'' +
                ", nameForms=" + nameForms +
                '}';
    }

    public Set<String> getConstructionName(String toTranslate, SpecificLanguageSelector specificLanguageSelector, Verb verb) {

        /*
        Map<String, String> result = nameForms.entrySet().
                stream().
                filter(p -> specificLanguageSelector.electConstruction(p.getKey(), toTranslate)).
                collect(Collectors.toMap(p -> p.getKey(), p -> "p.getValue()"));
        return result.keySet();
        */
        Set<String>  constructionNames = new HashSet<>();
        List<RootedConjugation> values = new ArrayList<>(nameForms.values());
        for(RootedConjugation rootedConjugation :  values) {
            if(verb.isOnlyFormKnown() && !rootedConjugation.getConstructionName().equals(verb.getOnlyFormKnown())) continue;
            if(rootedConjugation.contains(toTranslate) || rootedConjugation.contains(unaccentued(toTranslate))) {
                constructionNames.add(rootedConjugation.getConstructionName());
            }
        }
        return constructionNames;
    }

    private String unaccentued(String value) {
        char[] letters = value.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : letters) {
            if(c >= 'a') sb.append(c);
        }
        return sb.toString();
    }

    public Map<String, Integer> getFormPosition(Set<String> constructionNames, String toTranslate, Verb verb) {
        Map<String, Integer> formPositions = new HashMap<String, Integer>();
        boolean wasFoundConstruction = false;
        boolean mustConstructionKnownBeFound = false;
        for (String constructionName : constructionNames) {
            if (verb.isOnlyFormKnown()) {
                mustConstructionKnownBeFound = true;
                if (!constructionName.endsWith(verb.getOnlyFormKnown())) {
                    continue;
                } else {
                    wasFoundConstruction = true;
                }
            }
            formPositions.put(constructionName, getFormPositionByConstructionName(constructionName, toTranslate, verb));
        }
        if (mustConstructionKnownBeFound && !wasFoundConstruction) {
            formPositions.put(verb.getOnlyFormKnown(), getFormPositionByConstructionName(verb.getRoot() + "@" + verb.getOnlyFormKnown(), toTranslate, verb));
        }
        return formPositions;
    }

    private int getFormPositionByConstructionName(String constructionName, String toTranslate, Verb verb) {
        //String nameForm = nameForms.get(constructionName);
        //List<String> allForms = Arrays.asList(nameForm.split(","));
        RootedConjugation rootedConjugation = nameForms.get(root + "@" + constructionName);
        if(rootedConjugation == null) rootedConjugation = nameForms.get(constructionName);
        if (verb.isPositionInTranslationTableKnown() && rootedConjugation.positionIsCorrect(verb.getPositionInTranslationTable(), toTranslate)) {
            return verb.getPositionInTranslationTable();
        }
        if (verb.isPluralKnown()) {
            return  rootedConjugation.getMaxPosition();
        }
        return rootedConjugation.positionFound(toTranslate).get(0);
    }

    public boolean isPlural(String toCheck) {
        Set<String> names = nameForms.keySet();
        for (String name : names) {
            if (name.equals("INFINITIVE")) continue;
            RootedConjugation rootedConjugation = nameForms.get(name);
            if(rootedConjugation.isPlural(toCheck)) return true;
        }
        return false;
    }

    public boolean isThirdPlural(String valueToCheck) {
        Set<String> names = nameForms.keySet();
        for (String name : names) {
            if (name.equals("INFINITIVE") || name.equals("PAP")) continue;
            RootedConjugation rootedConjugation = nameForms.get(name);
            if(rootedConjugation.isThirdPlural(valueToCheck)) return  true;
        }
        return false;
    }

    public boolean hasRoot(Word word) {
        return root.substring(0, 2).equals(word.initialValue.substring(0, 2));
    }

    public boolean hasNearRoot(Word word) {
        int same = 0;
        int length = root.length();
        for (int letter = 0; letter < length && letter < word.initialValue.length(); letter++) {
            if (checkLetters(root, word.initialValue, letter)) {
                same++;
            }
        }
        return same > length / 2;
    }

    private boolean checkLetters(String s1, String s2, int indice) {
        return s1.charAt(indice) == s2.charAt(indice);
    }

    //TODO : I lost the defaultLanguageSelector here. It wasn't useless
    public List<String> getConstructionNameForInitialValue(String initialValue, DefaultLanguageSelector defaultLanguageSelector) {
        /*
        Set<String> allConstructions = getConstructionName(initialValue, defaultLanguageSelector);
        return allConstructions.toArray()[0].toString().split("@")[1];
        */
        List<String> results = new ArrayList<>();
        for(RootedConjugation rootedConjugation : nameForms.values()) {
            if(rootedConjugation.contains(initialValue)) results.add(rootedConjugation.getConstructionName());
        }
        return results;
    }

    public boolean hasWordRoot(Word word) {
        return root.equals(word.getRoot());
    }

    public List<Integer> getPossibleTranslationPositions(String initialValue, String constructionName, String root) {
        RootedConjugation rootedConjugation = nameForms.get(root + "@" + constructionName);
        return rootedConjugation.positionFound(initialValue);
    }
}

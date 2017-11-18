package org.patrologia.translator.basicelements;

import org.patrologia.translator.conjugation.*;
import org.patrologia.translator.linguisticimplementations.DefaultLanguageSelector;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 07/09/2015.
 */
public class VerbRepository extends Repository {

    private Map<String, String> conjugationsMap = new HashMap<>();
    private Map<String, Verb> verbMap = new HashMap<>();
    private Map<String, RootedConjugation> rootedConjugationMap = new HashMap<>();
    private TranslationBeanMap translationBeansMap = new TranslationBeanMap();

    private ConjugationFactory conjugationFactory;
    private Language language;
    private VerbDefinitionFactory verbDefinitionFactory = new VerbDefinitionFactory();
    private ConjugationComparator conjugationComparator;

    public VerbRepository(ConjugationFactory conjugationFactory, Language language, List<String> definitionList) {
        this.conjugationFactory = conjugationFactory;
        this.language = language;
        conjugationComparator = conjugationFactory.getComparator();
        definitionList.stream().forEach(definition -> addVerb(definition));
    }

    private void addVerb(String definition) {
        if (definition != null && definition.contains("IRREGULAR")) {
            addIrregularVerb(definition);
        } else {
            addRegularVerb(definition);
        }
    }

    private void addRegularVerb(String definition) {
        VerbDefinition verbDefinition = verbDefinitionFactory.getVerbDefinition(language, definition);
        translationBeansMap.addGlobalVerbKey(verbDefinition.getRoot());
        Conjugation conjugation = conjugationFactory.getConjugationByPattern(verbDefinition);
        conjugation.getTimes().stream().forEach(time -> addAllConjugationAndRoot(time, verbDefinition.getBaseConjugationRootByTime(time), verbDefinition.getRoot(), conjugation));
        rootedConjugationMap.put(verbDefinition.getRoot() + "@INFINITIVE", new RootedConjugation("INFINITIVE", verbDefinition.getInfinitiveForm()));
        translationBeansMap.addConjugationForGlobalKey(verbDefinition.getRoot(), "INFINITIVE");
        conjugationsMap.put(verbDefinition.getInfinitiveForm(), verbDefinition.getRoot());
        Verb verb = new Verb(verbDefinition.getRoot(), this, language);
        verbMap.put(verbDefinition.getRoot(), verb);
    }

    private void addAllConjugationAndRoot(String time, String baseConjugationRoot, String root, Conjugation conjugation) {
        String value = conjugation.getTerminationsWithRootAllValues(baseConjugationRoot, time);
        translationBeansMap.addConjugationForGlobalKey(root, time);
        RootedConjugation rootedConjugation = new RootedConjugation(time, value);
        VerbDefinition verbDefinition = conjugation.getVerbDefinition();
        if (verbDefinition != null && verbDefinition.hasCustomReplacements()) {
            TranslationReplacements translationReplacements = new TranslationReplacements(Collections.singletonList(verbDefinition.getTranslationInformationReplacement()));
            value = translationReplacements.hasThisNameAsReplacement(time) ? translationReplacements.correctFormName(time, rootedConjugation) : value;
        }
        List<ConjugationPart> conjugationPartList = rootedConjugation.getConjugationPartList(value);
        if (verbDefinition != null && verbDefinition.hasCustomRules()) {
            TranslationRules translationRules = verbDefinition.getTranslationRules();
            if(translationRules !=  null) conjugationPartList = translationRules.transform(conjugationPartList);
        }
        rootedConjugationMap.put(root + "@" + time, new RootedConjugation(time, conjugationPartList));
        conjugationPartList.stream().forEach(singleConjugationPart -> conjugationsMap.put(singleConjugationPart.getValue(), root));
        conjugationPartList.stream().forEach(singleConjugationPart -> conjugationsMap.put(unaccentued(singleConjugationPart.getValue()), root));
    }

    private void extractIrregularForm(IrregularVerbDefinition verbDefinition, String form) {
        verbDefinition.extractForms(form);
        String[] formParts = form.split("=");
        String time = cleanBrackets(formParts[0]);
        String value = cleanBrackets(formParts[1]);
        RootedConjugation rootedConjugation = new RootedConjugation(time, value);
        List<ConjugationPart> conjugationPartList = rootedConjugation.getConjugationPartList(value);
        conjugationPartList.stream().forEach(conjugation -> conjugationsMap.put(conjugation.getValue(), verbDefinition.getRoot()));
        conjugationPartList.stream().forEach(conjugation -> conjugationsMap.put(unaccentued(conjugation.getValue()), verbDefinition.getRoot()));
        rootedConjugationMap.put(verbDefinition.getRoot() + "@" + verbDefinition.getName(), new RootedConjugation(verbDefinition.getName(), verbDefinition.getConjugations()));
        translationBeansMap.addConjugationForGlobalKey(verbDefinition.getRoot(), verbDefinition.getName());
    }

    private String cleanBrackets(String toClean) {
        return toClean.replace("[", "").replace("]", "");
    }

    private void addIrregularVerb(String definition) {
        IrregularVerbDefinition verbDefinition = verbDefinitionFactory.getIrregularVerbDefinition(language, definition);
        List<String> formsList = verbDefinition.getFormsList();
        translationBeansMap.addGlobalVerbKey(verbDefinition.getRoot());
        formsList.forEach(form -> extractIrregularForm(verbDefinition, form));
        Verb verb = new Verb(verbDefinition.getRoot(), this, language);
        verbMap.put(verbDefinition.getRoot(), verb);
    }

    public boolean hasVerb(String initialValue) {
        /*
        List<String> strings = new ArrayList<>(conjugationsMap.keySet());
        Collections.sort(strings);
        for(String s : strings) {
            if(s.startsWith("behiel")) System.out.println(s);
        }
        */
        return conjugationsMap.containsKey(initialValue) || conjugationsMap.containsKey(unaccentued(initialValue));
    }

    public Verb getVerb(String initialValue) {
        String key = conjugationsMap.get(initialValue);
        if (key == null) key = conjugationsMap.get(unaccentued(initialValue));
        if (key == null) key = initialValue;
        Verb toClone = verbMap.get(key);
        if (toClone == null) return new NullVerb(language, null, null);
        return new Verb(toClone);
    }

    public TranslationInformationBean getAllFormsForTheVerbRoot(String root) {
        TranslationInformationBean allFormsForThisRoot = new TranslationInformationBean(root);
        List<String> times = translationBeansMap.getAllTimes(root);
        for (String constructionName : times) {
            String key = root + "@" + constructionName;
            allFormsForThisRoot.addForm(key, rootedConjugationMap.get(key));
        }
        return allFormsForThisRoot;
    }

    public String getConjugationSysnonym(String constructionName) {
        return conjugationFactory.getConjugationSynonym(new DefaultVerbDefinition(constructionName));
    }

    public List<String> getAllFormsForRoot(List<String> stopWords, Conjugation conjugation) {
        List<String> allWordsForRoot = new ArrayList<>();
        Collection<Verb> values = verbMap.values();
        for (String root : stopWords) {
            boolean wasFound = false;
            for (Verb verb : values) {
                String verbRoot = verb.getRoot();
                if (root.equals(verbRoot)) {
                    TranslationInformationBean translationInformationBean = getAllFormsForTheVerbRoot(root);
                    for (String formConjuged : translationInformationBean.allForms()) {
                        allWordsForRoot.add(formConjuged);
                    }
                    wasFound = true;
                }
            }
            if (!wasFound) {
                allWordsForRoot.add(root);
            }
        }
        return allWordsForRoot;
    }

    public List<String> getValuesStartingWith(String beginningPattern) {
        Set<String> verbValues = new HashSet<>();
        for (String verbValue : conjugationsMap.keySet()) {
            if (verbValue.startsWith(beginningPattern)) {
                verbValues.add(verbValue);
                verbValues.add(unaccentued(verbValue));
                verbValues.add(unaccentuedWithSofit(verbValue));
            }
        }
        return new ArrayList<>(verbValues);
    }

    public Collection<? extends String> getValuesEndingWith(String endingPattern) {
        List<String> verbValues = new ArrayList<>();
        for (String verbValue : conjugationsMap.keySet()) {
            if (verbValue.endsWith(endingPattern)) {
                verbValues.add(verbValue);
            }
        }
        return verbValues;
    }

    public int knowsThisInitialValues(List<String> listOfInitialValues) {
        int numberOfInitialValuesKnown = 0;
        for (String initialValue : listOfInitialValues) {
            if (hasVerb(initialValue)) numberOfInitialValuesKnown++;
        }
        return numberOfInitialValuesKnown;
    }

    public String getEquivalentForOtherRoot(String otherVerbRoot, String otherInitialValue, String formerInitialValue, String formerRoot, int positionTranslation, DefaultLanguageSelector languageSelector) {
        TranslationInformationBean allFormsForTheFormerVerbRoot = getAllFormsForTheVerbRoot(formerRoot);
        List<String> constructionNameForInitialValueList = allFormsForTheFormerVerbRoot.getConstructionNameForInitialValue(formerInitialValue, languageSelector);
        Collections.sort(constructionNameForInitialValueList,conjugationComparator);
        Map<String, RootedConjugation> nameForms = allFormsForTheFormerVerbRoot.getNameForms();
        for (String constructionNameForInitialValue : constructionNameForInitialValueList) {
            RootedConjugation rootedConjugation = nameForms.get(formerRoot + "@" + constructionNameForInitialValue);
            //int positionFound = rootedConjugation.positionFound(formerInitialValue);
            int positionFound = positionTranslation;
            TranslationInformationBean allFormsForTheTargetVerb = getAllFormsForTheVerbRoot(otherVerbRoot);
            RootedConjugation targetRootedConjugation = allFormsForTheTargetVerb.getNameForms().get(otherVerbRoot + "@" + constructionNameForInitialValue);
            if (targetRootedConjugation.hasValueForPosition(positionFound)) {
                String result = targetRootedConjugation.getValueByPosition(positionFound);
                return result.replace("-", "");
            }
        }
        return "NEED_TO_REFACTOR_THIS_SHIT";
    }

    public boolean isPossibleInfinitive(Verb verb) {
        TranslationInformationBean allFormsForTheVerbRoot = getAllFormsForTheVerbRoot(verb.getRoot());
        List<String> constructionNameForInitialValueList = allFormsForTheVerbRoot.getConstructionNameForInitialValue(verb.getInitialValue(), new DefaultLanguageSelector());
        return constructionNameForInitialValueList.contains(Conjugation.INFINITIVE);
    }

    public String isOnlyInThisTime(Verb verb, List<String> times) {
        TranslationInformationBean allFormsForTheVerbRoot = getAllFormsForTheVerbRoot(verb.getRoot());
        List<String> constructionNamesForInitialValue = allFormsForTheVerbRoot.getConstructionNameForInitialValue(verb.getInitialValue(), null);
        if (constructionNamesForInitialValue.size() == 1 && times.contains(constructionNamesForInitialValue.get(0)))
            return constructionNamesForInitialValue.get(0);
        Collections.sort(times);
        Collections.sort(constructionNamesForInitialValue);
        for (String time : times) {
            if (constructionNamesForInitialValue.contains(time)) return time;
        }
        return null;
    }


    public Verb affectTime(Verb verb, String oldTime, String newTime) {
        TranslationInformationBean allFormsForTheVerbRoot = getAllFormsForTheVerbRoot(verb.getRoot());
        RootedConjugation rootedConjugationOld = allFormsForTheVerbRoot.getNameForms().get(verb.getRoot() + "@" + oldTime);
        List<Integer> indices = rootedConjugationOld.positionFound(verb.getInitialValue());
        int position = indices.get(0);
        RootedConjugation rootedConjugationNew = allFormsForTheVerbRoot.getNameForms().get(verb.getRoot() + "@" + newTime);
        String valueByPosition = rootedConjugationNew.getValueByPosition(ConjugationPosition.getValueByPosition(position));
        verb.updateInitialValue(valueByPosition);
        return verb;
    }

    public void addIndiceOfPreferedTranslation(String root, int indiceOfPreferedTranslation) {
        Verb verb = getVerb(root);
        verb.setPreferedTranslation(indiceOfPreferedTranslation - 1);
        verbMap.put(root, verb);
    }

    public Verb updatePreferedTranslation(Verb verb) {
        int preferedTranslation = getVerb(verb.getRoot()).getPreferedTranslation();
        if (verb.getPreferedTranslation() == 0) {
            verb.setPreferedTranslation(preferedTranslation);
        }
        return verb;
    }
}

package patrologia.translator.basicelements.verb;

import patrologia.translator.basicelements.*;
import patrologia.translator.conjugation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class VerbRepository2 {

    public static final ConjugationPosition ENGLISH_PLURAL_POSITION = ConjugationPosition.PLURAL_THIRD_PERSON;
    private ConjugationFactory conjugationFactory;
    private Language language;
    private Accentuer accentuer;
    private VerbDefinitionFactory verbDefinitionFactory = new VerbDefinitionFactory();

    private ConjugationComparator conjugationComparator;
    private VerbMap verbMap;

    private TranslationBeanMap translationBeansMap = new TranslationBeanMap();
    private InfinitiveBuilder infinitiveBuilder;
    private Map<String, RootedConjugation> rootedConjugationMap = new HashMap<>();

    private ConjugationMap conjugationMap;

    public VerbRepository2(ConjugationFactory conjugationFactory, Language language, Accentuer accentuer, List<String> definitionList) {
        this.conjugationFactory = conjugationFactory;
        this.language = language;
        this.accentuer = accentuer;
        this.infinitiveBuilder = InfinitiveBuilderFactory.getInfinitiveBuilder(language);
        this.conjugationMap = new ConjugationMap(accentuer, infinitiveBuilder);
        this.verbMap = new VerbMap(conjugationMap);
        conjugationComparator = conjugationFactory.getComparator();
        definitionList.stream().forEach(definition -> addAllVerbs(definition));
    }

    public boolean hasVerb(String initialValue) {
        return conjugationMap.containsKey(accentuer.cleanAll(initialValue)) || conjugationMap.containsKey(infinitiveBuilder.getInfinitiveFromInitialValue(initialValue));
    }

    public Verb getVerb(String initialValue) {
        String key = conjugationMap.getFirstKey(initialValue);
        if (key == null) key = conjugationMap.getFirstKey(accentuer.unaccentued(initialValue));
        if (key == null) key = initialValue;
        Verb toClone = verbMap.getVerb(key);
        if (toClone == null) return new NullVerb(language, null, null);
        return new Verb(toClone);
    }

    public boolean isPossibleInfinitive(Verb verb) {
        return false;
    }

    public TranslationInformationBean getAllFormsForTheVerbRoot(String root) {
        TranslationInformationBean allFormsForThisRoot = new TranslationInformationBean(root, infinitiveBuilder);
        List<String> times = translationBeansMap.getAllTimes(root);
        for (String constructionName : times) {
            String key = root + "@" + constructionName;
            allFormsForThisRoot.addForm(key, rootedConjugationMap.get(key));
        }
        return allFormsForThisRoot;
    }

    public Collection<Verb> getVerbs(String initialValue) {
        Collection<Verb> allVerbs = verbMap.getAllVerbs(accentuer.cleanAll(initialValue));
        if(allVerbs.size() == 0 && lookForInfinitive(language,initialValue).getRoot() != null) {
            allVerbs = new ArrayList<>();
            ((ArrayList<Verb>) allVerbs).add(0,lookForInfinitive(language,initialValue));
        }
        if (allVerbs != null && allVerbs.size() > 0) return allVerbs;
        return verbMap.getAllVerbs(accentuer.unaccentued(initialValue));
    }

    public InfinitiveBuilder getInfinitiveBuilder() {
        return infinitiveBuilder;
    }

    private void addAllVerbs(String definition) {
        if (isIrregular(definition)) {
            addIrregularVerb(definition);
        } else {
            addVerb(definition);
        }

    }

    private void addIrregularVerb(String definition) {
        IrregularVerbDefinition verbDefinition = verbDefinitionFactory.getIrregularVerbDefinition(language, definition);
        List<String> formsList = verbDefinition.getFormsList();
        translationBeansMap.addGlobalVerbKey(verbDefinition.getRoot());
        formsList.forEach(form -> extractIrregularForm(verbDefinition, form));
        Verb verb = new Verb(verbDefinition.getRoot(), this, language);
        verbMap.put(verbDefinition.getRoot(), verb);
    }

    private void extractIrregularForm(IrregularVerbDefinition verbDefinition, String form) {
        verbDefinition.extractForms(form);
        String[] formParts = form.split("=");
        String time = cleanBrackets(formParts[0]);
        String value = cleanBrackets(formParts[1]);
        RootedConjugation rootedConjugation = new RootedConjugation(time, value, accentuer);
        List<ConjugationPart2> conjugationPartList = rootedConjugation.getConjugationPartList(value);
        conjugationPartList.stream().forEach(conjugation -> conjugationMap.put(conjugation.getValue(), verbDefinition.getRoot()));
        conjugationPartList.stream().forEach(conjugation -> conjugationMap.put(accentuer.unaccentued(conjugation.getValue()), verbDefinition.getRoot()));
        rootedConjugationMap.put(verbDefinition.getRoot() + "@" + verbDefinition.getName(), new RootedConjugation(verbDefinition.getName(), verbDefinition.getConjugations(), accentuer));
        translationBeansMap.addConjugationForGlobalKey(verbDefinition.getRoot(), verbDefinition.getName());

    }

    private String cleanBrackets(String value) {
        return value.replace("[", "").replace("]", "");
    }

    private boolean isIrregular(String definition) {
        return definition != null && definition.contains("IRREGULAR");
    }

    private void addVerb(String definition) {
        VerbDefinition verbDefinition = verbDefinitionFactory.getVerbDefinition(language, definition,accentuer);
        translationBeansMap.addGlobalVerbKey(verbDefinition.getRoot());
        rootedConjugationMap.put(verbDefinition.getRoot() + "@INFINITIVE", new RootedConjugation("INFINITIVE", verbDefinition.getInfinitiveForm(),accentuer));
        Conjugation2 conjugation = conjugationFactory.getConjugationByPattern(verbDefinition);
        conjugation.getTimes().stream().forEach(time -> addAllConjugationAndRoot(time, conjugation, verbDefinition.getBaseConjugationRoot(), verbDefinition));
        conjugationMap.put(verbDefinition.getInfinitiveForm(),verbDefinition.getRoot());
        translationBeansMap.addConjugationForGlobalKey(verbDefinition.getRoot(), "INFINITIVE");
    }

    private void addAllConjugationAndRoot(String time, Conjugation2 conjugation, String baseConjugationRoot, VerbDefinition verbDefinition) {
        String valuesAllInOne = conjugation.getRootWithEveryEndingsByTime(baseConjugationRoot, time);
        translationBeansMap.addConjugationForGlobalKey(verbDefinition.getRoot(), time);
        RootedConjugation rootedConjugation = new RootedConjugation(time, valuesAllInOne, conjugation.isRelatedTonoun(time), conjugation.getConjugationName(), conjugation.getDeclension(time));
        TranslationInformationReplacement2 translationInformationReplacement = verbDefinition.getTranslationInformationReplacement2();
        rootedConjugation.updateValues(translationInformationReplacement, time);
        if(verbDefinition.hasTranslationRules() && verbDefinition.getTranslationRules().hasTransformationForThisTime(time)) {
            TranslationRules translationRules = verbDefinition.getTranslationRules();
            List<ConjugationPart2> transformedConjugationPartList = translationRules.transform(rootedConjugation.getPartLists(),time);
            rootedConjugation = new RootedConjugation(time, transformedConjugationPartList);
        }
        storeAllConjugations(rootedConjugation,translationInformationReplacement,time,verbDefinition);
        Verb verb = new Verb(verbDefinition.getRoot(), this, language);
        rootedConjugationMap.put(verbDefinition.getRoot() + "@" + time, rootedConjugation);
        verbMap.put(verbDefinition.getRoot(), verb);
    }

    private void storeAllConjugations(RootedConjugation rootedConjugation, TranslationInformationReplacement2 translationInformationReplacement, String time, VerbDefinition verbDefinition) {
        List<String> allFormsByTime = rootedConjugation.allFormsByTime();
        for (String value : allFormsByTime) {
            String replace = value;
            String root = verbDefinition.getRoot();
            conjugationMap.put(replace, root);
            String cleanAll = accentuer.cleanAll(replace);
            if(!conjugationMap.containsKey(cleanAll)) {
                conjugationMap.put(cleanAll, root);
            }
        }
    }

    public String getEquivalentForOtherRoot(String root, String formerInitialValue, String rootVerb, ConjugationPosition verbTranslationPosition) {
        TranslationInformationBean allFormsForTheFormerVerbRoot = getAllFormsForTheVerbRoot(rootVerb);
        List<String> constructionNameForInitialValueList = allFormsForTheFormerVerbRoot.getConstructionNameForInitialValue(formerInitialValue, infinitiveBuilder);
        Collections.sort(constructionNameForInitialValueList, conjugationComparator);
        for (String constructionNameForInitialValue : constructionNameForInitialValueList) {
            ConjugationPosition positionFound = verbTranslationPosition;
            TranslationInformationBean allFormsForTheTargetVerb = getAllFormsForTheVerbRoot(root);
            RootedConjugation targetRootedConjugation = allFormsForTheTargetVerb.getNameForms().get(root + "@" + constructionNameForInitialValue);
            if (targetRootedConjugation.hasValueForPosition(positionFound)) {
                String result = targetRootedConjugation.getValueByPosition(positionFound);
                return result.replace("-", "");
            }
        }
        return "UNKNOWN_EQUIVALENT";
    }

    public boolean isConjugation(Verb verb, String conjugationName) {
        RootedConjugation rootedConjugation = rootedConjugationMap.get(verb.getRoot() + "@" + conjugationName);
        return rootedConjugation != null && rootedConjugation.contains(verb.getInitialValue());
    }

    public Collection<? extends String> getValuesStartingWith(String beginningPattern) {
        Set<String> verbValues = new HashSet<>();
        List<String> verbsInRepository = conjugationMap.keySet();
        for (String verbValue : verbsInRepository) {
            if (verbValue.startsWith(beginningPattern)) {
                verbValues.add(verbValue);
                verbValues.add(accentuer.unaccentued(verbValue));
                verbValues.add(accentuer.unaccentuedWithSofit(verbValue));
            }
        }
        return new ArrayList<>(verbValues);
    }

    public Collection<? extends String> getValuesEndingWith(String endingPattern) {
        List<String> verbValues = new ArrayList<>();
        for (String verbValue : conjugationMap.allConjugations()) {
            if (verbValue.endsWith(endingPattern)) {
                verbValues.add(verbValue);
            }
        }
        return verbValues;
    }

    public int knowsThisInitialValues(List<String> decorateInitialValuesForRomanianInfinitive) {
        int numberOfInitialValuesKnown = 0;
        for (String initialValue : decorateInitialValuesForRomanianInfinitive) {
            if (hasVerb(initialValue)) numberOfInitialValuesKnown++;
        }
        return numberOfInitialValuesKnown;
    }

    public String isOnlyInThisTime(Verb followingVerb, List<String> futureTimes) {
        TranslationInformationBean allFormsForTheVerbRoot = getAllFormsForTheVerbRoot(followingVerb.getRoot());
        List<String> constructionNamesForInitialValue = allFormsForTheVerbRoot.getConstructionNameForInitialValue(followingVerb.getInitialValue(), infinitiveBuilder);
        if (constructionNamesForInitialValue.size() == 1 && futureTimes.contains(constructionNamesForInitialValue.get(0)))
            return constructionNamesForInitialValue.get(0);
        Collections.sort(futureTimes);
        Collections.sort(constructionNamesForInitialValue);
        for (String time : futureTimes) {
            if (constructionNamesForInitialValue.contains(time)) return time;
        }
        return null;
    }

    public Verb affectTime(Verb followingVerb, String onlyInThisTime, List<String> pastTimes) {
        TranslationInformationBean allFormsForTheVerbRoot = getAllFormsForTheVerbRoot(followingVerb.getRoot());
        RootedConjugation rootedConjugationOld = allFormsForTheVerbRoot.getNameForms().get(followingVerb.getRoot() + "@" + onlyInThisTime);
        List<ConjugationPosition> indices = rootedConjugationOld.positionFound(followingVerb.getInitialValue());
        ConjugationPosition conjugationPosition = indices.get(0);
        for(String newTime : pastTimes) {
            if(!allFormsForTheVerbRoot.hasThisTime(newTime)) continue;
            RootedConjugation rootedConjugationNew = allFormsForTheVerbRoot.getNameForms().get(followingVerb.getRoot() + "@" + newTime);
            String valueByPosition = rootedConjugationNew.getValueByPosition(conjugationPosition);
            followingVerb.updateInitialValue(valueByPosition);
            return followingVerb;
        }
        return null;
    }

    public Verb updatePreferedTranslation(Verb verb) {
        if(verb.getRoot() == null) {
            verb.setRoot(infinitiveBuilder.eraseInfinitiveForm(verb.getInitialValue()));
        }
        int preferedTranslation = getVerb(verb.getRoot()).getPreferedTranslation();
        if (verb.getPreferedTranslation() == 0) {
            verb.setPreferedTranslation(preferedTranslation);
        }
        return verb;
    }

    public String getConjugationSynonym(String constructionName) {
        return conjugationFactory.getConjugationSynonym(new DefaultVerbDefinition(constructionName));
    }

    public void addIndiceOfPreferedTranslation(String root, int indiceOfPreferedTranslation) {
        Verb verb = getVerb(root);
        verb.setPreferedTranslation(indiceOfPreferedTranslation - 1);
        verbMap.put(root, verb);
    }

    public Verb lookForInfinitive(Language language, String initialValue) {
        String infinitiveFromInitialValue = infinitiveBuilder.getInfinitiveFromInitialValue(initialValue);
        RootedConjugation rootedConjugation = rootedConjugationMap.get(initialValue + "@INFINITIVE");
        if (rootedConjugation != null && rootedConjugation.getValueByPosition(0).equals(infinitiveFromInitialValue)) {
            Verb verb = new Verb(language, initialValue, ConjugationPosition.SINGULAR_FIRST_PERSON);
            verb.updateInitialValue(infinitiveFromInitialValue);
            return verb;
        } else {
            return new NullVerb(language, null, null);
        }
    }

    public String getConjugationSysnonym(String constructionName) {
        //System.out.println("il faut écrire getConjugationSysnonym");
        return conjugationFactory.getConjugationSynonym(new DefaultVerbDefinition(constructionName));
        //return null;
    }
}

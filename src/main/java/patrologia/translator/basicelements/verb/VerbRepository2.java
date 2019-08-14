package patrologia.translator.basicelements.verb;

import patrologia.translator.basicelements.Accentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.NullVerb;
import patrologia.translator.basicelements.TranslationInformationBean;
import patrologia.translator.conjugation.*;

import java.util.*;

public class VerbRepository2 {

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
        return conjugationMap.containsKey(initialValue);
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
        Collection<Verb> allVerbs = verbMap.getAllVerbs(initialValue);
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
        RootedConjugation rootedConjugation = new RootedConjugation(time, value);
        List<ConjugationPart> conjugationPartList = rootedConjugation.getConjugationPartList(value);
        conjugationPartList.stream().forEach(conjugation -> conjugationMap.put(conjugation.getValue(), verbDefinition.getRoot()));
        conjugationPartList.stream().forEach(conjugation -> conjugationMap.put(accentuer.unaccentued(conjugation.getValue()), verbDefinition.getRoot()));
        rootedConjugationMap.put(verbDefinition.getRoot() + "@" + verbDefinition.getName(), new RootedConjugation(verbDefinition.getName(), verbDefinition.getConjugations()));
        translationBeansMap.addConjugationForGlobalKey(verbDefinition.getRoot(), verbDefinition.getName());

    }

    private String cleanBrackets(String value) {
        return value.replace("[", "").replace("]", "");
    }

    private boolean isIrregular(String definition) {
        return definition != null && definition.contains("IRREGULAR");
    }

    private void addVerb(String definition) {
        VerbDefinition verbDefinition = verbDefinitionFactory.getVerbDefinition(language, definition);
        translationBeansMap.addGlobalVerbKey(verbDefinition.getRoot());
        rootedConjugationMap.put(verbDefinition.getRoot() + "@INFINITIVE", new RootedConjugation("INFINITIVE", verbDefinition.getInfinitiveForm()));
        Conjugation2 conjugation = conjugationFactory.getConjugationByPattern(verbDefinition);
        conjugation.getTimes().stream().forEach(time -> addAllConjugationAndRoot(time, conjugation, verbDefinition.getBaseConjugationRoot(), verbDefinition));
        translationBeansMap.addConjugationForGlobalKey(verbDefinition.getRoot(), "INFINITIVE");
    }

    private void addAllConjugationAndRoot(String time, Conjugation2 conjugation, String baseConjugationRoot, VerbDefinition verbDefinition) {
        String valuesAllInOne = conjugation.getRootWithEveryEndingsByTime(baseConjugationRoot, time);
        translationBeansMap.addConjugationForGlobalKey(verbDefinition.getRoot(), time);
        RootedConjugation rootedConjugation = new RootedConjugation(time, valuesAllInOne, conjugation.isRelatedTonoun(time), conjugation.getConjugationName(), conjugation.getDeclension(time));
        TranslationInformationReplacement2 translationInformationReplacement = verbDefinition.getTranslationInformationReplacement2();
        int indice = 0;
        StringBuilder sb = new StringBuilder();
        for (String value : rootedConjugation.allFormsByTime()) {
            String replace = translationInformationReplacement.replace(time, value, ConjugationPosition.getValueByPosition(indice++));
            conjugationMap.put(replace, verbDefinition.getRoot());
            sb.append(replace).append(",");
        }
        rootedConjugation.updateValues(translationInformationReplacement,time);
        rootedConjugationMap.put(verbDefinition.getRoot() + "@" + time, rootedConjugation);
        Verb verb = new Verb(verbDefinition.getRoot(), this, language);
        verbMap.put(verbDefinition.getRoot(), verb);

    }

    public String getEquivalentForOtherRoot(String root, String formerInitialValue, String rootVerb, int verbTranslationPosition) {
        TranslationInformationBean allFormsForTheFormerVerbRoot = getAllFormsForTheVerbRoot(rootVerb);
        List<String> constructionNameForInitialValueList = allFormsForTheFormerVerbRoot.getConstructionNameForInitialValue(formerInitialValue, infinitiveBuilder);
        Collections.sort(constructionNameForInitialValueList, conjugationComparator);
        for (String constructionNameForInitialValue : constructionNameForInitialValueList) {
            int positionFound = verbTranslationPosition;
            TranslationInformationBean allFormsForTheTargetVerb = getAllFormsForTheVerbRoot(root);
            RootedConjugation targetRootedConjugation = allFormsForTheTargetVerb.getNameForms().get(root + "@" + constructionNameForInitialValue);
            if (targetRootedConjugation.hasValueForPosition(positionFound)) {
                String result = targetRootedConjugation.getValueByPosition(positionFound);
                return result.replace("-", "");
            }
        }
        return "UNKNOWN_EQUIVALENT";
    }

    public boolean isConjugation(Verb followingVerb, String pap) {

        return false;
    }

    public Collection<? extends String> getValuesStartingWith(String value) {

        return Collections.EMPTY_LIST;
    }

    public Collection<? extends String> getValuesEndingWith(String value) {

        return Collections.EMPTY_LIST;
    }

    public int knowsThisInitialValues(List<String> decorateInitialValuesForRomanianInfinitive) {
        int numberOfInitialValuesKnown = 0;
        for (String initialValue : decorateInitialValuesForRomanianInfinitive) {
            if (hasVerb(initialValue)) numberOfInitialValuesKnown++;
        }
        return numberOfInitialValuesKnown;
    }

    public String isOnlyInThisTime(Verb followingVerb, List<String> futureTimes) {

        return null;
    }

    public Verb affectTime(Verb followingVerb, String onlyInThisTime, List<String> pastTimes) {

        return null;
    }

    public Verb updatePreferedTranslation(Verb verb) {
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
}

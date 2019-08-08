package patrologia.translator.basicelements.verb;

import patrologia.translator.basicelements.Accentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.TranslationInformationBean;
import patrologia.translator.conjugation.*;

import java.util.*;

public class VerbRepository2 {

    private ConjugationFactory conjugationFactory;
    private Language language;
    private Accentuer accentuer;
    private VerbDefinitionFactory verbDefinitionFactory = new VerbDefinitionFactory();

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
        definitionList.stream().forEach(definition -> addAllVerbs(definition));
    }

    public boolean hasVerb(String initialValue) {
        return conjugationMap.containsKey(initialValue);
    }

    public Verb getVerb(String key) {
        return new Verb(verbMap.getVerb(key));
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
        return Collections.EMPTY_LIST;
    }

    public InfinitiveBuilder getInfinitiveBuilder() {
        return infinitiveBuilder;
    }

    private void addAllVerbs(String definition) {
        if(isIrregular(definition)) {
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
        Conjugation2 conjugation = conjugationFactory.getConjugationByPattern(verbDefinition);
        conjugation.getTimes().stream().forEach(time -> addAllConjugationAndRoot(time, conjugation, verbDefinition.getBaseConjugationRoot(),verbDefinition));
    }

    private void addAllConjugationAndRoot(String time, Conjugation2 conjugation, String baseConjugationRoot, VerbDefinition verbDefinition) {
        String valuesAllInOne = conjugation.getRootWithEveryEndingsByTime(baseConjugationRoot, time);
        String[] values = valuesAllInOne.split(",");
        TranslationInformationReplacement2 translationInformationReplacement = verbDefinition.getTranslationInformationReplacement2();
        int indice = 0;
        for (String value : values) {
            conjugationMap.put(translationInformationReplacement.replace(time,value,ConjugationPosition.getValueByPosition(indice++)), baseConjugationRoot);
        }
    }

    public String getEquivalentForOtherRoot(String root, String formerInitialValue, String rootVerb, int verbTranslationPosition) {
        return null;
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
        return 0;
    }

    public String isOnlyInThisTime(Verb followingVerb, List<String> futureTimes) {
        return null;
    }

    public Verb affectTime(Verb followingVerb, String onlyInThisTime, List<String> pastTimes) {
        return null;
    }

    public Verb updatePreferedTranslation(Verb verb) {
        return null;
    }

    public String getConjugationSynonym(String constructionName) {
        return null;
    }

    public void addIndiceOfPreferedTranslation(String root, int indiceOfPreferedTranslation) {

    }
}

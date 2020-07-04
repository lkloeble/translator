package patrologia.translator.basicelements.verb;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.TranslationInformationBean;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.conjugation.ConjugationPosition;

import java.util.HashSet;
import java.util.Set;

public class Verb extends Word {

    private boolean pluralKnown;
    private VerbRepository2 verbRepository;
    private ConjugationPosition positionInTranslationTable = ConjugationPosition.UNKNOWN;
    private boolean isOnlyFormKnown;
    private String onlyFormKnown;
    private String conjugation;
    private Set<String> forbiddenConjugations;

    public Verb(String initialValue, String root, Language language) {
        super(WordType.VERB, initialValue, root, language);
        forbiddenConjugations = new HashSet<>();
    }

    public Verb(String initialValue, VerbRepository2 verbRepository, Language language) {
        super(WordType.VERB, initialValue, language);
        this.verbRepository = verbRepository;
        this.preferedTypeSymbol = 'V';
        this.positionInTranslationTable = language.getDefaultPositionInTranslationTableVerb();
        forbiddenConjugations = new HashSet<>();
    }

    public Verb(Verb toClone) {
        super(toClone);
        if(toClone == null) return;
        this.pluralKnown = toClone.pluralKnown;
        this.verbRepository = toClone.verbRepository;
        this.positionInTranslationTable = toClone.positionInTranslationTable;
        this.rules = toClone.rules;
        this.onlyFormKnown = toClone.onlyFormKnown;
        this.isOnlyFormKnown = toClone.isOnlyFormKnown;
        this.conjugation = toClone.conjugation;
        this.forbiddenConjugations = toClone.forbiddenConjugations;
        this.gender = toClone.gender;
        this.preferedTranslation = toClone.preferedTranslation;
    }

    public Verb(Language language, String initialValue, ConjugationPosition positionInTranslationTable) {
        super(WordType.VERB, initialValue, language);
        this.positionInTranslationTable = positionInTranslationTable;
        forbiddenConjugations = new HashSet<>();
    }

    public boolean isPluralKnown() {
        return pluralKnown;
    }

    public void setPluralKnown(boolean pluralKnown) {
        this.pluralKnown = pluralKnown;
    }

    public boolean isPlural(String valueToCheck) {
        TranslationInformationBean allFormsForTheVerbRoot = verbRepository.getAllFormsForTheVerbRoot(root);
        return allFormsForTheVerbRoot.isPlural(valueToCheck);
    }

    public boolean isThirdPLural(String valueToCheck) {
        TranslationInformationBean allFormsForTheVerbRoot = verbRepository.getAllFormsForTheVerbRoot(root);
        return allFormsForTheVerbRoot.isThirdPlural(valueToCheck);
    }

    public ConjugationPosition getPositionInTranslationTable() {
        return positionInTranslationTable;
    }

    public void setPositionInTranslationTable(ConjugationPosition conjugationPosition) {
        this.positionInTranslationTable = conjugationPosition;
    }

    public boolean isPositionInTranslationTableKnown() {
        return positionInTranslationTable != ConjugationPosition.UNKNOWN;
    }

    public String getOnlyFormKnown() {
        return onlyFormKnown;
    }

    public boolean isOnlyFormKnown() {
        return isOnlyFormKnown;
    }

    public void setConjugation(String conjugation) {
        this.isOnlyFormKnown = true;
        this.onlyFormKnown = conjugation;
    }

    public void addForbiddenConjugation(String forbiddenConjugation) {
        if(forbiddenConjugations.size() == 0) forbiddenConjugations = new HashSet<String>();
        forbiddenConjugations.add(forbiddenConjugation);
    }

    public void updateRoot(String root) {
        this.root = root;
    }

    public boolean isConjugationForbidden(String constructionNameAlone) {
        return forbiddenConjugations.contains(constructionNameAlone);
    }

    public Set<String> getForbiddenConjugations() {
        return forbiddenConjugations;
    }

    public void setForbiddenConjugations(Set<String> forbiddenConjugations) {
        this.forbiddenConjugations = forbiddenConjugations;
    }

    public String getConjugation() {
        return conjugation;
    }

    public boolean isNullVerb() {
        return false;
    }

    public VerbRepository2 getVerbRepository() {
        return verbRepository;
    }

}

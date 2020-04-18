package patrologia.translator.basicelements;

import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.Number;
import patrologia.translator.rule.Rule;

import java.util.*;

public class Word {

    public static final String WORD_TO_REMOVE = "xxtoremovexx";

    protected WordType wordType;
    protected String initialValue;
    protected String root;
    protected Set<Rule> rules = new HashSet<Rule>();
    protected Phrase phrase;
    protected int positionInPhrase;
    protected int preferedTranslation = 0;
    protected char preferedTypeSymbol  = 'U';
    protected String preferedElected = null;
    protected Gender gender;
    protected Language language;

    public static final Integer STARTING_COUNTINDICE_IN_SENTENCE = 1;
    private List<CaseNumberGenre> possibleCaseNumbers;

    public Word(WordType wordType, String initialValue, String root, Language language) {
        this.wordType = wordType;
        this.initialValue = initialValue;
        this.root = root;
        this.language = language;
    }

    public Word(WordType wordType, String initialValue, Language language) {
        this.wordType = wordType;
        this.initialValue = initialValue;
        this.root = initialValue;
        this.language = language;
    }

    public Word(Word toClone) {
        if(toClone == null) return;
        this.wordType = toClone.wordType;
        this.initialValue = toClone.initialValue;
        this.root = toClone.root;
        this.rules = toClone.rules;
        this.phrase = toClone.phrase;
        this.positionInPhrase = toClone.positionInPhrase;
        this.preferedTranslation = toClone.preferedTranslation;
        this.preferedTypeSymbol = toClone.preferedTypeSymbol;
        this.preferedElected = toClone.preferedElected;
        this.language  = toClone.language;
    }

    public void putSentenceInformation(Phrase phrase, int positionInPhrase) {
        this.phrase = phrase;
        this.positionInPhrase = positionInPhrase;
    }

    public void addRule(Rule rule) {
        rules.add(rule);
    }

    public void addRules(Set<Rule> rules) {
        rules.stream().forEach(rule -> addRule(rule));
    }


    public boolean hasRules() {
        return rules.size() > 0;
    }

    public Set<Rule> getRules() {
        List<Rule> orderedRules = new ArrayList<>(rules);
        Collections.sort(orderedRules);
        return new HashSet<>(orderedRules);
    }

    public String getInitialValue() {
        if(!initialValue.contains("@")) return initialValue.trim();
        String[] contentWithPreferedTranslation = initialValue.split("@");
        if(contentWithPreferedTranslation[1].length() == 1) {
            try {
                this.preferedTranslation = Integer.parseInt(contentWithPreferedTranslation[1]);
            } catch (NumberFormatException nfe) {
                this.preferedTypeSymbol = contentWithPreferedTranslation[1].charAt(0);
            }
        } else if (contentWithPreferedTranslation[1].length() == 2){
            this.preferedTypeSymbol = contentWithPreferedTranslation[1].charAt(0);
            this.preferedTranslation = Character.getNumericValue(contentWithPreferedTranslation[1].charAt(1));
        } else if (contentWithPreferedTranslation[1].length() == 5 && contentWithPreferedTranslation[1].startsWith("[") && contentWithPreferedTranslation[1].endsWith("]")) {
            this.preferedElected = contentWithPreferedTranslation[1].substring(1,4);
        }
        return contentWithPreferedTranslation[0].trim();
    }

    public boolean hasType(WordType expectedWordType) {
        return wordType.equals(expectedWordType);
    }

    public void setInitialValue(String initialValue) {
        this.initialValue = initialValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Word)) return false;

        Word word = (Word) o;

        if (wordType != word.wordType) return false;
        if (initialValue != null ? !initialValue.equals(word.initialValue) : word.initialValue != null) return false;
        return !(root != null ? !root.equals(word.root) : word.root != null);

    }

    @Override
    public int hashCode() {
        int result = wordType != null ? wordType.hashCode() : 0;
        result = 31 * result + (initialValue != null ? initialValue.hashCode() : 0);
        result = 31 * result + (root != null ? root.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "W{" +
                "Type=" + wordType +
                ", root='" + root + '\'' +
                ", iniVal='" + initialValue + '\'' +
                '}';
    }

    public boolean isNoun() {
        return wordType.equals(WordType.NOUN) || getPreferedTypeSymbol() == 'n';
    }

    public boolean isVerb() {
        return wordType.equals(WordType.VERB) || getPreferedTypeSymbol() == 'v';
    }

    public boolean isPreposition() {
        return wordType.equals(WordType.PREPOSITION) || getPreferedTypeSymbol() == 'p';
    }

    public boolean isNoTranslation() { return wordType.equals(WordType.NO_TRANSLATION); }

    public boolean hasElected() {
        if(isNoun()) {
            Noun n = (Noun)this;
            return n.getElectedCaseNumber() != null;
        }
        return false;
    }

    public boolean hasNoRecursiveElected() {
        if(isNoun()) {
            Noun n = (Noun)this;
            return n.getNoDepthElectedCaseNumber() != null;
        }
        return false;
    }

    public void selfElect() {
        if(isNoun()) {
            Noun n = (Noun)this;
            n.selfElect();
        }
    }

    public Number getNumberElected() {
        if(hasNoRecursiveElected()) {
            return ((Noun)this).getElectedCaseNumber().getNumber();
        }
        return Number.UNKNOWN;
    }

    public void updateInitialValue(String newString) {
        initialValue = newString;
    }

    public String getRoot() {
        return root;
    }

    public WordType getWordType() {
        return wordType;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public Gender getGender() {
        if(gender==null) {
            return new Gender(Gender.UNKNOWN);
        }
        return gender;
    }

    public void modifyContentByPatternReplacement(String origin, String replacement) {
        initialValue = initialValue.replace(origin,replacement);
        root = root.replace(origin,replacement);
        //root = root.contains("[") ? root.replace(origin,replacement) : root.replace(root,replacement);
    }

    public void modifyContentByPatternReplacementAndPosition(String replacement, int position) {
        initialValue = initialValue.substring(0,position) + replacement;
        root = root.substring(0,position) + replacement;
    }

    public int getPreferedTranslation() {
        return preferedTranslation;
    }

    public void setPreferedTranslation(int preferedTranslation) {
        this.preferedTranslation = preferedTranslation;
    }

    private char getPreferedTypeSymbol() {
        return preferedTypeSymbol;
    }

    public boolean isTypeUnknow() {
        if(getPreferedTypeSymbol() != 'U') return false;
        return wordType == null || getPreferedTypeSymbol() == 'U'  || WordType.UNKNOWN.equals(wordType);
    }

    public void updatePreferedTypeSymbol(char preferedTypeSymbol) {
        if(isTypeUnknow()) {
            this.preferedTypeSymbol = preferedTypeSymbol;
        }
    }

    public boolean isVerbInstance() {
        return wordType.equals(WordType.VERB);
    }

    public String getPreferedElected() {
        return preferedElected;
    }

    public void updateRoot(String newRoot) {
        this.root = newRoot;
    }


    public Set<String> getForbiddenConjugations() {
        return Collections.EMPTY_SET;
    }

    public char lastLetter() {
        return initialValue.charAt(initialValue.length()-1);
    }

    public boolean hasElectedWithoutCompute() {
        return false;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean hasNewRules() {
        return false;
    }

    public boolean isEmpty() {
        return getInitialValue().isEmpty();
    }

    public Language getLanguage() {
        return language;
    }

    public boolean isAbsoluteNoun() {
        return WordType.NOUN.equals(this.wordType);
    }

}

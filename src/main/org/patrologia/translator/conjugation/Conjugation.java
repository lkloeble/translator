package org.patrologia.translator.conjugation;

import org.patrologia.translator.basicelements.NounRepository;
import org.patrologia.translator.basicelements.TranslationInformationReplacement;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionFactory;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 15/09/2015.
 * IPR = active indicative present
 * SPA = active subjonctive perfect
 * ASP = active subjonctive present
 * AIP = active indicative perfect
 * AII = active indicative imperfect
 * MPII = middle/passive indicative imperfect
 * PSP = passive subjonctive present
 * PSI = passive subjonctive  imperfect
 * PII = passive indicative imperfect
 * AIF = active indicative future
 * PAP = participe passé TODO : à changer
 * AIMP = active imperative present
 * PIP = passive indicative present
 * ASI = active subjonctive imparfait
 * PIF = passive indicative future
 * AIFP = active indicative future perfect
 * INFINITIVE = infinitive
 * PAPR = participe présent
 * PASUPR = passive subjonctif present
 * AIPP = active indicative pluperfect
 * IAPP = infinitive active passive  present
 * ACP = active conditional present
 * IAP = infinitive active perfect
 * VENO = verbal nouns
 * PEACIN => Perfect Active Indicative
 * PRPARPASS => Present Participle Middle/Passive
 * PASANT => passé antérieur
 */
public abstract class Conjugation {

    protected Map<String, List<String>> allEndings = new HashMap<String, List<String>>();
    protected VerbDefinition verbDefinition;
    protected NounRepository nounRepository;
    protected boolean isRelatedToParticipeAndIsANoun;
    protected String declensionPattern;
    protected Declension declension;

    public static final String ACTIVE_IMPERATIVE_PRESENT = "AIMP";
    public static final String ACTIVE_INDICATIVE_PRESENT = "IPR";
    public static final String INFINITIVE = "INFINITIVE";
    public static final String PAST_PARTICIPE = "PAP";

    public abstract List<String> getCongujationByTimePattern(String timePattern);

    public List<String> getAllEndings() {
        List<String> allEndingsAgregate = new ArrayList<>();
        allEndings.values().stream().forEach(list -> allEndingsAgregate.addAll(list));
        return allEndingsAgregate;
    }

    public String rootCorrectionByTimePattern(String root, String timePattern) {
        return root;
    }

    public String getTime() {
        return null;
    }

    public String getNewBase(String[] originBase) {
        return null;
    }

    public String getSynonym() {
        return null;
    }

    public VerbDefinition getVerbDefinition() {
        return verbDefinition;
    }

    public List<String> getTimes() {
        return new ArrayList(allEndings.keySet());//this returns only the times available in the conjugation file
        //return times; this returns all the times available in the language
    }


    public String getTerminationsWithRootAllValues(String baseConjugationRoot, String time) {
        if(isWithPrefixSpecialSyntax(time)) return getTerminationComputedWithPrefixes(baseConjugationRoot, time);
        List<String> strings = allEndings.get(time);
        if(isRelatedToNounsAsParticiple(strings)) strings = getParticipleDeclensonFromNounRepository(strings);
        StringBuilder sb = new StringBuilder();
        char splitter = '-';
        for(String ending : strings) {
            splitter = iterateInValues(ending, sb, splitter, baseConjugationRoot);
        }
        if(sb.length() == 0) return "";
        return sb.deleteCharAt(0).toString();
    }

    private List<String> getParticipleDeclensonFromNounRepository(List<String> strings) {
        String declensionPattern = strings.get(0).replace("[","").replace("]","");
        DeclensionFactory declensionFactory = nounRepository.getDeclensionFactory();
        Declension declensionByPattern = declensionFactory.getDeclensionByPattern(declensionPattern);
        List<String> endingsFromDeclension = new ArrayList<>(declensionByPattern.getAllEndings().values());
        if(endingsFromDeclension.size() > 0) {
            isRelatedToParticipeAndIsANoun = true;
            this.declensionPattern = declensionPattern;
            endingsFromDeclension.clear();
            endingsFromDeclension.add("");
        }
        return endingsFromDeclension;
    }

    private boolean isRelatedToNounsAsParticiple(List<String> strings) {
        if(strings.size() != 1) return false;
        String declensionPattern = strings.get(0);
        return declensionPattern.startsWith("[") && declensionPattern.endsWith("]");
    }

    private String getTerminationComputedWithPrefixes(String baseConjugationRoot, String time) {
        TranslationInformationReplacement translationInformationReplacement = getVerbDefinition().getTranslationInformationReplacement();
        if(translationInformationReplacement.hasThisName(time)) {
            baseConjugationRoot = translationInformationReplacement.getAlternateRootForThisTime(time, baseConjugationRoot);
        }
        List<String> endingsForTime = allEndings.get(time);
        StringBuilder sb = new StringBuilder();
        char splitter = '-';
        for(String ending : endingsForTime) {
            splitter = iterateInValues(ending,sb,splitter,baseConjugationRoot);
        }
        return sb.toString();
    }

    private char iterateForPrefixesUniqueValues(String ending, StringBuilder sb, String baseConjugationRoot, char splitter) {
        String[] prefixSuffix = ending.split("\\*");
        String prefix = prefixSuffix.length == 3 ? prefixSuffix[1] : "";
        String suffix = prefixSuffix.length == 3 ? prefixSuffix[2] : prefixSuffix[0];
        splitter = iterateInValues(suffix, sb, splitter, prefix + baseConjugationRoot);
        return splitter;
    }

    private boolean isWithPrefixSuffixesSpecialChars(String value) {
        if(value.split("\\*").length < 2) return false;
        return true;
    }

    private boolean isWithPrefixSpecialSyntax(String time) {
        for(String ending : allEndings.get(time)) {
            if(ending.equals("-")) continue;
            if(ending.split("\\*").length < 2) return false;
        }
        return true;
    }

    private char getSplitter(char splitter) {
        switch(splitter)  {
            case '-':
                return Character.MIN_VALUE;
            case Character.MIN_VALUE:
                return ',';
            case ',':
                return ',';
        }
        return 0;
    }

    private char iterateInValues(String st, StringBuilder sb, char splitter, String baseConjugationRoot) {
        if(st.contains("|")) {
            String values[] = st.split("\\|");
            splitter = getSplitter(splitter);
            sb.append(splitter);
            for(String value : values) {
                if(isWithPrefixSuffixesSpecialChars(value)) {
                    String[] prefixSuffix = value.split("\\*");
                    String prefix = prefixSuffix.length == 3 ? prefixSuffix[1] : "";
                    String suffix = prefixSuffix.length == 3 ? prefixSuffix[2] : prefixSuffix[0];
                    sb.append(prefix).append(baseConjugationRoot).append(suffix).append("|");
                } else {
                    sb.append(baseConjugationRoot).append(value).append("|");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        } else {
            if(isWithPrefixSuffixesSpecialChars(st)) {
                splitter = getSplitter(splitter);
                String[] prefixSuffix = st.split("\\*");
                String prefix = prefixSuffix.length == 3 ? prefixSuffix[1] : "";
                String suffix = prefixSuffix.length == 3 ? prefixSuffix[2] : prefixSuffix[0];
                sb.append(splitter).append(prefix).append(baseConjugationRoot).append(suffix);
            } else {
                splitter = getSplitter(splitter);
                sb.append(splitter).append(baseConjugationRoot).append(st);
            }
        }
        return  splitter;
    }

    @Override
    public String toString() {
        return "Conjugation{" +
                "verbDefinition=" + verbDefinition.getRoot() +
                ", allEndings=" + allEndings.keySet() +
                '}';
    }

    public boolean isRelatedToParticipeAndIsANoun() {
        return isRelatedToParticipeAndIsANoun;
    }

    public String getDeclensionPattern() {
        return declensionPattern;
    }

    public Declension getDeclension() {
        return nounRepository.getDeclensionFactory().getDeclensionByPattern(declensionPattern);
    }
}

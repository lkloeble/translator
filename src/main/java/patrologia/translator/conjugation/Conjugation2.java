package patrologia.translator.conjugation;

import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionFactory;
import patrologia.translator.declension.NullDeclension;

import java.util.*;

/**
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
 * ASPP => Active Subjonctive PluPerfect
 * PEACIN => PErfect ACtive INfinitive
 * INACPAS => INFINITIVE ACTIVE PASSIVE
 */


public abstract class Conjugation2 {

    public static final String ACTIVE_IMPERATIVE_PRESENT = "AIMP";
    public static final String ACTIVE_INDICATIVE_PRESENT = "IPR";
    public static final String INFINITIVE = "INFINITIVE";
    public static final String PAST_PARTICIPE = "PAP";

    protected String conjugationName;
    protected Map<String,Boolean> relatedToNounMap = new HashMap<>();

    protected Map<String,String> descriptions =new HashMap<>();
    protected DeclensionFactory declensionFactory;

    public List<String> getTimes() {
        return new ArrayList<>(descriptions.keySet());
    }

    public String getRootWithEveryEndingsByTime(String root,String time) {
        StringBuilder sb = new StringBuilder();
        String allEndings = null;
        if(isRelatedTonoun(time)) {
            return root;
        } else {
            allEndings = descriptions.get(time);
        }
        if(allEndings == null) return root;
        String[] allValues = allEndings.split(",");
        for(String value : allValues) {
            if(value.contains("|")) {
                int indicePipe = 1;
                String[] multipleValues = value.split("\\|");
                for(String partValue : multipleValues) {
                    sb.append(appendTerminationWithMultipleValues(root, partValue));
                    if(indicePipe < multipleValues.length) {
                        sb.append("|");
                    }
                    indicePipe++;
                }
                sb.append(",");
            } else {
                sb.append(appendTermination(root,value,","));
            }
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }

    public String appendTerminationWithMultipleValues(String root, String partValue) {
        StringBuilder sb = new StringBuilder();
        sb.append(root).append(partValue);
        return sb.toString();
    }

    public String appendTermination(String root, String value, String s) {
        StringBuilder sb = new StringBuilder();
        sb.append(root).append(value).append(",");
        return sb.toString();
    }

    private String getAllEndingsByDeclensions(List<String> declensions) {
        Set<String> allEndings = new HashSet<>();
        for(String declension : declensions) {
            Declension declensionByPattern = declensionFactory.getDeclensionByPattern(declension);
            Collection<String> endings = declensionByPattern.getAllEndings().values();
            allEndings.addAll(endings);
        }
        return getAllSetValuesInOneString(allEndings);
    }

    private String getAllSetValuesInOneString(Set<String> allEndings) {
        StringBuilder sb = new StringBuilder();
        for(String ending : allEndings) {
            sb.append(ending).append(",");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
    }

    public boolean isRelatedTonoun(String time) {
        return relatedToNounMap.containsKey(time) ? relatedToNounMap.get(time) : false;
    }


    private List<String> extractNounDeclensionsFromDescription(String time) {
        List<String> nounDeclensions = new ArrayList<>();
        String description = descriptions.get(time).replace("[","").replace("]","");
        String[] declensionArray = description.split(",");
        for(String declension : declensionArray) {
            nounDeclensions.add(declension);
        }
        return nounDeclensions;
    }

    protected void processConjugationDescription(List<String> conjugationDescription) {
        if(conjugationDescription == null) {
            System.out.println("oh oh");
        }
        for(String description : conjugationDescription) {
            descriptions.put(extractTimeName(description), extractTimeDescription(description));
        }
    }

    protected void processRelationToNoun(List<String> conjugationDescription) {
        for(String description : conjugationDescription) {
            if(syntaxOfDescriptionContainsNounDeclension(description)) {
                relatedToNounMap.put(extractTimeName(description),true);
            }
        }
    }

    private boolean syntaxOfDescriptionContainsNounDeclension(String description) {
        return description.contains("[") && description.contains("]");
    }

    private String extractTimeDescription(String conjugationDescription) {
        return conjugationDescription.split("=>")[1];
    }

    private String extractTimeName(String conjugationDescription) {
        return conjugationDescription.split("=>")[0];
    }

    public String getConjugationName() {
        return conjugationName;
    }

    public List<Declension> getDeclension(String time) {
        if(relatedToNounMap.get(time) == null || !relatedToNounMap.get(time)) return Arrays.asList(new NullDeclension());
        List<Declension> declensionList = new ArrayList<>();
        String declensionName = descriptions.get(time).replace("[","").replace("]","");
        String allNames[] = declensionName.split(",");
        for(String name : allNames) {
            declensionList.add(declensionFactory.getDeclensionByPattern(name));
        }
        return declensionList;
    }

    public String getSynonym() {
        return null;
    }
}

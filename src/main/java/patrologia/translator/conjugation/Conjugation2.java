package patrologia.translator.conjugation;

import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionFactory;
import patrologia.translator.declension.romanian.RomanianDeclensionFactory;

import java.util.*;

public abstract class Conjugation2 {

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
            List<String> declensions = extractNounDeclensionsFromDescription(time);
            allEndings = getAllEndingsByDeclensions(declensions);
        } else {
            allEndings = descriptions.get(time);
        }
        if(allEndings == null) return root;
        String[] allValues = allEndings.split(",");
        for(String value : allValues) {
            sb.append(root).append(value).append(",");
        }
        return sb.deleteCharAt(sb.length()-1).toString();
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



}

package patrologia.translator.conjugation;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.declension.Declension;

import java.util.*;

/**
 * Created by lkloeble on 11/01/2017.
 */
public class RootedConjugation {

    private List<ConjugationPart> conjugationPartList;
    private String constructionName;
    private boolean isParticipleRelatedToNounDeclension;
    private String declensionPattern;
    private Declension declension;

    public RootedConjugation(String constructionName, String values, boolean isParticipleRelatedToNounDeclension, String declensionPattern, Declension declension) {
        this.constructionName = constructionName;
        this.isParticipleRelatedToNounDeclension = isParticipleRelatedToNounDeclension;
        this.declensionPattern = declensionPattern;
        this.declension = declension;
        this.conjugationPartList = getConjugationPartList(values);
    }

    public RootedConjugation(String constructionName, String values) {
        this.constructionName = constructionName;
        this.conjugationPartList = getConjugationPartList(values);
    }

    public RootedConjugation(String constructionName, List<ConjugationPart> conjugationPartList) {
        this.constructionName = constructionName;
        this.conjugationPartList = conjugationPartList;
    }

    public List<ConjugationPart> getConjugationPartList(String conjugationValues) {
        if(isParticipleRelatedToNounDeclension)  {
            return getNounRelatedConjugationPartList(conjugationValues);
        }
        return getPureVerbalConjugationPartList(conjugationValues);
    }

    private List<ConjugationPart> getNounRelatedConjugationPartList(String prefix) {
        if(conjugationPartList != null && conjugationPartList.size() > 0) return conjugationPartList;
        List<ConjugationPart> conjugationPartList = new ArrayList<>();
        Set<Map.Entry<CaseNumberGenre, String>> entries = declension.getAllEndings().entrySet();
        for(Map.Entry entry : entries) {
            CaseNumberGenre caseNumberGenre =  (CaseNumberGenre)entry.getKey();
            String conjugationValue = (String)entry.getValue();
            ConjugationPart conjugationPart = new ConjugationPart(caseNumberGenre, prefix + conjugationValue, prefix + conjugationValue);
            conjugationPartList.add(conjugationPart);
        }
        return conjugationPartList;
    }

    private List<ConjugationPart> getPureVerbalConjugationPartList(String conjugationValues) {
        List<ConjugationPart> conjugationPartList = new ArrayList<>();
        String[] valueTab = conjugationValues.split(",");
        if(valueTab.length == 0) return Collections.EMPTY_LIST;
        int positionInDefinition = 1;
        for(int i=0;i<valueTab.length;i++) {
            if(valueTab[i].contains("|")) {
                String[] allValuesForIndice = valueTab[i].split("\\|");
                for(String value : allValuesForIndice) {
                    conjugationPartList.add(new ConjugationPart(ConjugationPosition.getValueByPosition(i),value,value,positionInDefinition++));
                }
            } else {
                if(valueTab[i] == null || valueTab[i].isEmpty()) continue;
                conjugationPartList.add(new ConjugationPart(ConjugationPosition.getValueByPosition(i),valueTab[i],valueTab[i],positionInDefinition++));
            }
        }
        return conjugationPartList;
    }


    public boolean isThirdPlural(String valueToCheck) {
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(conjugationPart.isIndiceThirPlural()  && conjugationPart.getValue().equals(valueToCheck)) return true;
        }
        return false;
    }

    public boolean isPlural(String toCheck) {
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(conjugationPart.getValue().equals(toCheck) && conjugationPart.isPlural()) return true;
        }
        return false;
    }

    public int getMaxPosition() {
        int positionFound = -1;
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(conjugationPart.getIndice() > positionFound) positionFound = conjugationPart.getIndice();
        }
        return positionFound;
    }

    public List<Integer> positionFound(String toTranslate) {
        List<Integer> positions = new ArrayList<>();
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(conjugationPart.getValue().equals(toTranslate)) positions.add(conjugationPart.getIndice());
            if(conjugationPart.getValue().equals(unaccentued(toTranslate))) positions.add(conjugationPart.getIndice());
            if(conjugationPart.getUnaccentuedValue().equals(toTranslate)) positions.add(conjugationPart.getIndice());
            if(conjugationPart.getUnaccentuedValue().equals(unaccentued(toTranslate))) positions.add(conjugationPart.getIndice());
        }
        if(positions.size() == 0) positions.add(0);
        return positions;
    }

    private String unaccentued(String value) {
        char[] letters = value.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : letters) {
            if(c >= 'a') sb.append(c);
        }
        return sb.toString();
    }


    public List<String> allForms() {
        Set<String> values = new HashSet<>();
        for(ConjugationPart conjugationPart : conjugationPartList) {
            values.add(conjugationPart.getValue());
            values.add(conjugationPart.getUnaccentuedValue());
        }
        return new ArrayList<>(values);
    }

    public String getValueByPosition(ConjugationPosition conjugationPosition) {
        for(ConjugationPart conjugationPart : conjugationPartList)  {
            if(conjugationPart.getIndice().equals(conjugationPosition.getIndice())) {
                return conjugationPart.getValue();
            }
        }
        return "NOTFOUND";
    }

    @Override
    public String toString() {
        return "RootedConjugation{" +
                "construc : " + constructionName +
                " conjugationPartList=" + conjugationPartList +
                '}';
    }

    public boolean contains(String toTranslate) {
        for(ConjugationPart conjugationPart : conjugationPartList) {
            if(conjugationPart.contains(toTranslate)) return true;
        }
        return false;
    }

    public String getConstructionName() {
        return constructionName;
    }

    public List<ConjugationPart> getPartLists() {
        return conjugationPartList;
    }

    public String allFormsInOneString() {
        StringBuilder sb = new StringBuilder();
        int previousIndice = -1;
        char pattern = ',';
        for(ConjugationPart conjugationPart : conjugationPartList) {
            int indice = conjugationPart.getIndice();
            if(indice != previousIndice) {
                pattern = ',';
            } else {
                pattern = '|';
            }
            sb.append(pattern).append(conjugationPart.getValue());
            previousIndice = indice;
        }
        return sb.deleteCharAt(0).toString();
    }

    public boolean positionIsCorrect(int positionInTranslationTable, String toTranslate) {
        if(conjugationPartList.size() <= positionInTranslationTable) return false;
        for(ConjugationPart conjugationPart : conjugationPartList) {
            int positionCheck = conjugationPart.getIndice().intValue();
            if (positionCheck == positionInTranslationTable) {
                return conjugationPart.getValue().equals(toTranslate);
            }
        }
        return false;
    }

    public boolean hasValueForPosition(int positionFound) {
        return !getValueByPosition(ConjugationPosition.getValueByPosition(positionFound)).equals("NOTFOUND");
    }

    public String getValueByPosition(int positionFound) {
        return getValueByPosition(ConjugationPosition.getValueByPosition(positionFound));
    }

    public boolean isParticipleRelatedToNounDeclension() {
        return isParticipleRelatedToNounDeclension;
    }

    public String getDeclensionPattern() {
        return declensionPattern;
    }


    public CaseNumberGenre getElectedCaseNumber(String initialValue) {
        return declension.getCaseNumberGenreByEndingValue(initialValue);
    }
}

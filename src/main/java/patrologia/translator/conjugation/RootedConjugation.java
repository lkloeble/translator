package patrologia.translator.conjugation;

import patrologia.translator.basicelements.Accentuer;
import patrologia.translator.basicelements.DummyAccentuer;
import patrologia.translator.basicelements.verb.TranslationInformationReplacement2;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.NullCaseNumberGenre;
import patrologia.translator.declension.Declension;

import java.util.*;

public class RootedConjugation {

    private List<ConjugationPart2> conjugationPartList;
    private String constructionName;
    private boolean isParticipleRelatedToNounDeclension;
    private String declensionPattern;
    private List<Declension> declensionList;
    private Accentuer accentuer;

    public RootedConjugation(String constructionName, String values, boolean isParticipleRelatedToNounDeclension, String declensionPattern, List<Declension> declensionList) {
        this.constructionName = constructionName;
        this.isParticipleRelatedToNounDeclension = isParticipleRelatedToNounDeclension;
        this.declensionPattern = declensionPattern;
        this.declensionList = declensionList;
        this.accentuer = new DummyAccentuer();
        this.conjugationPartList = getConjugationPartList(values);
    }

    public RootedConjugation(String constructionName, String values, Accentuer accentuer) {
        this.accentuer = accentuer;
        this.constructionName = constructionName;
        this.conjugationPartList = getConjugationPartList(values);
    }

    public RootedConjugation(String constructionName, List<ConjugationPart2> conjugationPartList) {
        this.constructionName = constructionName;
        this.conjugationPartList = conjugationPartList;
        this.accentuer = new DummyAccentuer();
    }

    public List<ConjugationPart2> getConjugationPartList(String conjugationValues) {
        if(isParticipleRelatedToNounDeclension)  {
            return getNounRelatedConjugationPartList(conjugationValues);
        }
        return getPureVerbalConjugationPartList(conjugationValues);
    }

    private List<ConjugationPart2> getNounRelatedConjugationPartList(String prefix) {
        if(conjugationPartList != null && conjugationPartList.size() > 0) return conjugationPartList;
        List<ConjugationPart2> conjugationPartList = new ArrayList<>();
        for(Declension declension : declensionList) {
            Set<Map.Entry<CaseNumberGenre, String>> entries = declension.getAllEndings().entrySet();
            for (Map.Entry entry : entries) {
                CaseNumberGenre caseNumberGenre = (CaseNumberGenre) entry.getKey();
                String conjugationValue = (String) entry.getValue();
                ConjugationPart2 conjugationPart = new ConjugationPart2(prefix + conjugationValue, prefix + conjugationValue);
                conjugationPartList.add(conjugationPart);
            }
        }
        return conjugationPartList;
    }

    private List<ConjugationPart2> getPureVerbalConjugationPartList(String conjugationValues) {
        List<ConjugationPart2> conjugationPartList = new ArrayList<>();
        String[] valueTab = conjugationValues.split(",");
        if(valueTab.length == 0) return Collections.EMPTY_LIST;
        int positionInDefinition = 0;
        int strictPosition = 1;
        for(int i=0;i<valueTab.length;i++) {
            if(valueTab[i].contains("|")) {
                String[] allValuesForIndice = valueTab[i].split("\\|");
                for(String value : allValuesForIndice) {
                    conjugationPartList.add(new ConjugationPart2(ConjugationPosition.getValueByPosition(i),value,accentuer.unaccentued(value),positionInDefinition,strictPosition));
                    strictPosition++;
                }
                positionInDefinition++;
            } else {
                if(valueTab[i] == null || valueTab[i].isEmpty()) continue;
                conjugationPartList.add(new ConjugationPart2(ConjugationPosition.getValueByPosition(i),valueTab[i],accentuer.unaccentued(valueTab[i]),positionInDefinition++,strictPosition++));
            }
        }
        return conjugationPartList;
    }


    public boolean isThirdPlural(String valueToCheck) {
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(conjugationPart.isIndiceThirPlural()  && conjugationPart.getValue().equals(valueToCheck)) return true;
        }
        return false;
    }

    public boolean isPlural(String toCheck) {
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(conjugationPart.getValue().equals(toCheck) && conjugationPart.isPlural()) return true;
        }
        return false;
    }

    public ConjugationPosition getMaxPosition() {
        int positionFound = -1;
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(conjugationPart.getIndice() > positionFound) positionFound = conjugationPart.getIndice();
        }
        return ConjugationPosition.getValueByPosition(positionFound);
    }

    public List<ConjugationPosition> positionFound(String toTranslate) {
        List<ConjugationPosition> positions = new ArrayList<>();
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(conjugationPart.getValue().equals(toTranslate)) positions.add(conjugationPart.getConjugationPosition());
            if(conjugationPart.getValue().equals(unaccentued(toTranslate))) positions.add(conjugationPart.getConjugationPosition());
            if(conjugationPart.getUnaccentuedValue().equals(toTranslate)) positions.add(conjugationPart.getConjugationPosition());
            if(conjugationPart.getUnaccentuedValue().equals(unaccentued(toTranslate))) positions.add(conjugationPart.getConjugationPosition());
        }
        if(positions.size() == 0) positions.add(ConjugationPosition.SINGULAR_FIRST_PERSON);
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
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            values.add(conjugationPart.getValue());
            values.add(conjugationPart.getUnaccentuedValue());
        }
        return new ArrayList<>(values);
    }

    public String getValueByPosition(ConjugationPosition conjugationPosition) {
        for(ConjugationPart2 conjugationPart : conjugationPartList)  {
            if(conjugationPart.getConjugationPosition() == conjugationPosition) {
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
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(conjugationPart.contains(toTranslate)) return true;
        }
        return false;
    }

    public String getConstructionName() {
        return constructionName;
    }

    public List<ConjugationPart2> getPartLists() {
        return conjugationPartList;
    }

    public String allFormsInOneString() {
        StringBuilder sb = new StringBuilder();
        int previousIndice = -1;
        char pattern = ',';
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            int indice = conjugationPart.getIndice();
            if(indice != previousIndice) {
                pattern = ',';
            } else {
                pattern = '|';
            }
            sb.append(pattern).append(conjugationPart.getValue());
            previousIndice = indice;
        }
        return sb.length() > 0 ? sb.deleteCharAt(0).toString() : sb.toString();
    }

    public boolean positionIsCorrect(ConjugationPosition positionInTranslationTable, String toTranslate) {
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            ConjugationPosition positionCheck = conjugationPart.getConjugationPosition();
            if (positionCheck == positionInTranslationTable) {
                return conjugationPart.getValue().equals(toTranslate);
            }
        }
        return false;
    }

    public boolean hasValueForPosition(ConjugationPosition positionFound) {
        return !getValueByPosition(positionFound).equals("NOTFOUND");
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
        for(Declension declension : declensionList) {
            return declension.getCaseNumberGenreByEndingValue(initialValue);
        }
        return new NullCaseNumberGenre();
    }

    public void updateValues(TranslationInformationReplacement2 translationInformationReplacement, String time) {
        if(!constructionName.equals(time)) return;
        if(!translationInformationReplacement.hasReplacementForTime(time)) return;
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            conjugationPart.updateValue(conjugationPart.getValue(),conjugationPart.getUnaccentuedValue(),translationInformationReplacement.replace(time, conjugationPart));
        }
    }

    public List<String> allFormsByTime() {
        Set<String> allForms = new HashSet<>();
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            allForms.add(conjugationPart.getValue());
            allForms.add(conjugationPart.getUnaccentuedValue());
        }
        return new ArrayList<>(allForms);
    }

    public int getPositionForConstructionNumber(int i) {
        return conjugationPartList.get(i).getIndice();
    }

    public int getPositionByForm(String form) {
        for(ConjugationPart2 conjugationPart : conjugationPartList) {
            if(conjugationPart.getValue().equals(form)) return conjugationPart.getIndice();
        }
        return 0;
    }
}

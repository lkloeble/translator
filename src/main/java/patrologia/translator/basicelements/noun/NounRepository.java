package patrologia.translator.basicelements.noun;

import patrologia.translator.basicelements.Accentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.Number;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionFactory;

import java.util.*;

public class NounRepository {

    private DeclensionFactory declensionFactory;
    private NounMap nounsMap = new NounMap();
    private Set<String> rootSet = new HashSet<>();
    private Map<String, List<CaseNumberGenre>> formCaseNumberCorrespondanceMap = new HashMap<String, List<CaseNumberGenre>>();
    private Map<String, Map<CaseNumberGenre, String>> exceptionsForEachNoun = new HashMap<>();
    private Language language;
    private Accentuer accentuer;

    private static final String EMPTY_DIFFERENTIER = "";

    public NounRepository(Language language, DeclensionFactory declensionFactory, Accentuer accentuer, List<String> definitionList) {
        this.declensionFactory = declensionFactory;
        this.language = language;
        this.accentuer = accentuer;
        definitionList.stream().forEach(definition -> addNoun(definition));
    }

    public boolean hasNoun(String construction) {
        /*
        Set<String> strings = formCaseNumberCorrespondanceMap.keySet();
        List<String> orderedStrings = new ArrayList<>(strings);
        Collections.sort(orderedStrings);
        for(String key : orderedStrings) {
            if(key.startsWith("αναδ")) {
                System.out.println(key);
            }
        }
        */
        return formCaseNumberCorrespondanceMap.containsKey(construction) || formCaseNumberCorrespondanceMap.containsKey(accentuer.unaccentued(construction));
    }

    public Collection<Noun> getNoun(String construction) {
        return nounsMap.get(construction,true);
    }

    private void addNoun(String definition) {
        if(definition ==  null || definition.length() == 0) return;
        String[] nameForms = definition.split("@");
        String root = nameForms[0];
        rootSet.add(root);
        if(!root.equals(accentuer.unaccentuedWithSofit(root))) {
            rootSet.add(accentuer.unaccentuedWithSofit(root));
        }
        String[] forms = nameForms[1].split("%");
        Gender genderOrigin = Gender.getGenderByCode(forms[0]);
        if(genderOrigin == null) {
            System.out.println("unknown gender for " + definition);
            return;
        }
        String declensionSymbol = extractDeclension(forms[1]);
        List<String> specificRules = extractSpecificRules(forms[1]);
        String declensionPattern = extractDeclensionFromExceptionsForms(declensionSymbol);
        Map<CaseNumberGenre, String> exceptionsForms = extractExceptionsForms(declensionSymbol, genderOrigin, root);
        declensionFactory.setTemporaryGenderAndRoot(genderOrigin,root);
        Declension declension = declensionFactory.getDeclensionByPattern(declensionPattern);
        if(declension == null) {
            System.out.println("declension not defined : " + declensionPattern);
            return;
        }
        Map<CaseNumberGenre, String> allEndings = declension.getAllEndings();
        List<CaseNumberGenre> caseNumberGenres = new ArrayList(allEndings.keySet());
        Collections.sort(caseNumberGenres);
        for(CaseNumberGenre caseNumber : caseNumberGenres) {
            Gender gender = caseNumber.getGender();
            String construction = declension.isCustom() ? allEndings.get(caseNumber) : root + allEndings.get(caseNumber);
            if(exceptionsForms.containsKey(caseNumber)) {
                construction = exceptionsForms.get(caseNumber);
            }
            String unaccentuedConstruction = accentuer.unaccentuedWithSofit(construction);
            if(!nounsMap.containsKey(construction, declensionPattern, gender)) {
                addNounToMaps(genderOrigin, construction, root, caseNumber, gender, declensionPattern, specificRules, declension, unaccentuedConstruction);
            } else {
                Noun noun = nounsMap.get(construction, declensionPattern, gender);
                noun.addPossibleCaseNumber(caseNumber);
                List<CaseNumberGenre> caseNumbers = formCaseNumberCorrespondanceMap.get(construction);
                caseNumbers.add(caseNumber);
                formCaseNumberCorrespondanceMap.put(construction, caseNumbers);
            }
        }
    }

    private void addNounToMaps(Gender genderOrigin, String construction, String root, CaseNumberGenre caseNumber, Gender gender, String declensionPattern, List<String> specificRules, Declension declension, String unaccentuedConstruction) {
        Noun noun = new Noun(language, construction, root, Collections.singletonList(caseNumber), gender, declensionPattern, declensionPattern, specificRules);
        if(genderOrigin.equals(new Gender(Gender.ADJECTIVE))) {
            noun.setAdjective();
            rootSet.add(noun.getInitialValue());
        }
        if(declension.isWithoutArticle()) {
            noun.setNoArticle();
        }
        nounsMap.put(construction, noun, declensionPattern, gender);
        if(!unaccentuedConstruction.equals(construction)) {
            nounsMap.put(unaccentuedConstruction, noun, declensionPattern, gender);
        }
        formCaseNumberCorrespondanceMap.put(construction,new ArrayList<CaseNumberGenre>(Collections.singletonList(caseNumber)));
        if(!construction.equals(accentuer.unaccentued(construction))) {
            formCaseNumberCorrespondanceMap.put(accentuer.unaccentued(construction),new ArrayList<CaseNumberGenre>(Collections.singletonList(caseNumber)));
        }
    }

    private List<String> extractSpecificRules(String form) {
        if(form.indexOf("!") < 0) return Collections.EMPTY_LIST;
        return Arrays.asList(form.split("!")).subList(1,form.split("!").length);
    }

    private String extractDeclension(String form) {
        if(form.indexOf("!") < 0) return form;
        return form.split("!")[0];
    }

    private Map<CaseNumberGenre, String> extractExceptionsForms(String form, Gender gender, String root) {
        if(!form.contains("[")) {
            return Collections.EMPTY_MAP;
        }
        if(gender.equals(new Gender(Gender.ADJECTIVE))) gender = new Gender(Gender.MASCULINE);
        String exceptionsListed = form.split("\\[")[1];
        exceptionsListed = exceptionsListed.replace("]","");
        String[] exceptionsSplitted = exceptionsListed.split("\\,");
        Map<CaseNumberGenre, String> exceptionsForms = new HashMap<>();
        for(String exception : exceptionsSplitted) {
            String[] caseNumberNames = exception.split(":");
            String differentier = caseNumberNames[0].indexOf(')') > 0 ? caseNumberNames[0].substring(caseNumberNames[0].lastIndexOf('(')+1,caseNumberNames[0].lastIndexOf(')')) : EMPTY_DIFFERENTIER;
            String numberName = caseNumberNames[0].indexOf(')') > 0 ? caseNumberNames[0].substring(3,caseNumberNames[0].lastIndexOf('(')) : caseNumberNames[0].substring(3);
            String caseName = caseNumberNames[0].substring(0,3);
            Case _case = Case.getCaseByName(caseName, differentier, declensionFactory.getLanguage());
            Number number = Number.strValueOf(numberName);
            CaseNumberGenre caseNumberGenre = new CaseNumberGenre(_case, number, gender);
            exceptionsForms.put(caseNumberGenre, caseNumberNames[1]);
        }
        exceptionsForEachNoun.put(root, exceptionsForms);
        return exceptionsForms;
    }

    private String extractDeclensionFromExceptionsForms(String form) {
        if(form.contains("[")) {
            return form.split("\\[")[0];
        }
        return form;
    }

    public Map<CaseNumberGenre, String> getExceptionByDefinition(String definition) {
        return exceptionsForEachNoun.get(definition);
    }

    public boolean hasNounForDeclensionFamily(String initialValue, String family) {
        if(!hasNoun(initialValue)) return false;
        Noun noun = (Noun)getNoun(initialValue).toArray()[0];
        return noun.getGenderFamily().equals(family);
    }

    public List<String> getNounsValueStartingWith(String beginningPattern) {
        Set<String> nounValues = new HashSet<>();
        Collection<Noun> allNouns = nounsMap.values();
        for(Noun noun : allNouns) {
            if(noun.getInitialValue().startsWith(beginningPattern)) {
                nounValues.add(noun.getInitialValue());
                nounValues.add(accentuer.unaccentued(noun.getInitialValue()));
                nounValues.add(accentuer.unaccentuedWithSofit(noun.getInitialValue()));
            }
        }
        return new ArrayList<>(nounValues);
    }

    public List<String> getNounsValueForEndingWith(String endingPattern) {
        List<String> nounValues = new ArrayList<>();
        for(Noun noun : nounsMap.values()) {
            if(noun.getInitialValue().endsWith(endingPattern)) {
                nounValues.add(noun.getInitialValue());
                nounValues.add(accentuer.unaccentued(noun.getInitialValue()));
                nounValues.add(accentuer.unaccentuedWithSofit(noun.getInitialValue()));
            }
        }
        return nounValues;
    }

    public List<String> getNounsRootValueForEndingWith(String endingPattern) {
        List<String> nounValues = new ArrayList<>();
        for(String root : rootSet) {
            if(root.endsWith(endingPattern)) {
                nounValues.add(root);
            }
        }
        return nounValues;
    }

    public DeclensionFactory getDeclensionFactory() {
        return declensionFactory;
    }

}

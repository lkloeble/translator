package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.TranslatorRepository;
import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.modifier.FinalModifier;
import org.patrologia.translator.basicelements.noun.Noun;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.preposition.Preposition;
import org.patrologia.translator.basicelements.verb.InfinitiveBuilder;
import org.patrologia.translator.basicelements.verb.Verb;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.conjugation.ConjugationPosition;
import org.patrologia.translator.conjugation.RootedConjugation;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionFactory;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 13/10/2015.
 */
public abstract class LanguageToFrench implements TranslatorRepository {

    protected static final String UNKNOWN_TRANSLATION = "[XXX]";

    protected List<String> dictionaryDefinitions;
    protected List<String> frenchVerbsDefinitions;
    protected Map<String, String> allTranslationMap = new HashMap<String, String>();
    protected Map<String, String> mainTranslationMap = new HashMap<String, String>();
    protected Map<String, String> frenchVerbs = new HashMap<>();
    protected VerbRepository verbRepository;
    protected FormRepository formRepository;
    protected NounRepository nounRepository;

    protected Map<String, List<TranslationInformationBean>> originLanguageVerbFormConstruction = new HashMap<>();
    protected DeclensionFactory originLanguageDeclensionFactory;

    protected ConjugationGenderAnalyser conjugationGenderAnalyser;
    protected LanguageDecorator languageDecorator;
    protected FinalModifier finalModifier;
    protected InfinitiveBuilder infinitiveBuilder;

    public LanguageToFrench(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
        this.infinitiveBuilder = verbRepository.getInfinitiveBuilder();
        this.conjugationGenderAnalyser = new DefaultConjugationAnalyzer();
        this.formRepository =  new FormRepository(infinitiveBuilder);
    }

    public Translation computeReaderTranslation(Analysis analysis) {
        return computeTranslationWithCustomMap(analysis, mainTranslationMap);
    }

    private Translation computeTranslationWithCustomMap(Analysis analysis, Map<String, String> translationMap) {
        Translation result = new Translation(finalModifier);
        for(int position=1; position<=analysis.nbWords(); position++) {
            WordContainer wordContainer = analysis.getWordContainerByPosition(position);
            String initialValue = wordContainer.getInitialValue();
            String translatedResult = null;
            if(formIsRelatedToATranslableRoot(initialValue)) {
                translatedResult = translateWordContainerUsingCustomMap(wordContainer, translationMap);
            } else if(isPonctuationSign(initialValue)) {
                translatedResult = specificPonctuationReplace(initialValue);
            } else if(wordContainer.getUniqueWord().isNoTranslation()) {
                if(initialValue.equals("|")) { translatedResult = "";}
                else {
                    translatedResult = initialValue.length() > 1 ? initialValue.substring(1, initialValue.length() - 1) : initialValue;
                }
            } else {
                translatedResult = UNKNOWN_TRANSLATION;
            }
            result.put(position, translatedResult);
        }
        return result;
    }

    protected String specificPonctuationReplace(String initialValue) {
        return initialValue;
    }

    protected boolean formIsRelatedToATranslableRoot(String initialValue) {
        return formRepository.containsFormValue(initialValue);
    }

    protected boolean isPonctuationSign(String initialValue) {
        if(initialValue == null) return false;
        if("?".equals(initialValue)) return true;
        if("!".equals(initialValue)) return true;
        if(".".equals(initialValue)) return true;
        if(",".equals(initialValue)) return true;
        if(";".equals(initialValue)) return true;
        if(":".equals(initialValue)) return true;
        if("(".equals(initialValue)) return true;
        if(")".equals(initialValue)) return true;
        if("[".equals(initialValue)) return true;
        if("]".equals(initialValue)) return true;
        if("-".equals(initialValue)) return true;
        if("–".equals(initialValue)) return true;
        if("*".equals(initialValue)) return true;
        if("\"".equals(initialValue)) return true;
        return false;
    }

    private String translateWordContainerUsingCustomMap(WordContainer wordContainer, Map<String, String> customTranslationMap) {
        if(wordContainer.containsUniqueWord()) {
            String s = translateWordUsingCustomMap(wordContainer.getUniqueWord(), customTranslationMap);
            if(s == null || s.equals("null")) {
                System.out.println("wtf");
            }
            return s;
        }
        StringBuilder sb = new StringBuilder();
        List<Word> words = wordContainer.getWordSet();
        List<String> translatedWords = new ArrayList<>();
        for(Word word : words) {
            String translation = translateWordUsingCustomMap(word, customTranslationMap);
            if(translation == null) continue;
            translatedWords.add(translation);
        }
        if(translatedWords.size() != 1 && translatedWords.get(1) != null) Collections.sort(translatedWords);
        Set<String> translatedUniqueStrings = new HashSet<>(translatedWords);
        if(translatedUniqueStrings.size() == 1) return (String)translatedUniqueStrings.toArray()[0];
        translatedWords = new ArrayList<>(translatedUniqueStrings);
        Collections.sort(translatedWords);
        for(String translatedWord : translatedWords) {
            sb.append(translatedWord);
            sb.append("@");
        }
        return sb.toString();
    }

    protected String translateWordUsingCustomMap(Word word, Map<String, String> customTranslationMap) {
        if(word.isNoun()) {
            String genderName = word.getGender().name();
            String declension = ((Noun)word).getDeclension();
            if(word.getPreferedTranslation() > 0) {
                return numberCaseDecorate(getAlternateTranslation(word), word);
            }
            if(customTranslationMap.get(word.getRoot() + "N" + genderName + "D" + declension) != null) {
                return numberCaseDecorate(customTranslationMap.get(word.getRoot() + "N" +  genderName + "D" + declension), word);
            }
            String translationRoot = customTranslationMap.get(word.getRoot() + "N");
            return numberCaseDecorate(translationRoot, word);
        } else if(word.isVerb()) {
            String toTranslate = word.getInitialValue();
            Verb verb = (Verb)word;
            verb = verbRepository.updatePreferedTranslation(verb);
            String frenchRoot = formRepository.getValueByForm(new Form(toTranslate, verb.getRoot(), WordType.VERB, verb.getConjugation(),verb.getPreferedTranslation(), infinitiveBuilder));
            TranslationInformationBean construction = getTranslationbean(word, frenchRoot);
            if(construction == null) {
                System.out.println(word + " " + frenchRoot + " : pas de construction trouvée");
                return "XXX";
            }
            Set<String> constructionNames = construction.getConstructionName(toTranslate, verb);
            constructionNames = filterPastParticipleForVerbalNoun(constructionNames);
            Map<String, Integer> formPositionByConstructionName = construction.getFormPosition(constructionNames, toTranslate, (Verb)word);
            List<String> possibleVerbs = extractTranslation(formPositionByConstructionName, frenchVerbs.get(frenchRoot), verb.getPositionInTranslationTable(), verb);
            if(possibleVerbs.size() == 0 || possibleVerbs.get(0).equals("[XXX]")) {
                System.out.println(word + " " + frenchRoot + " "  + formPositionByConstructionName.keySet().toString());
            }
            if(isVerbalNounCase(constructionNames,formPositionByConstructionName)) {
                String constructionName = constructionNames.iterator().next();
                return numberCaseDecorate(possibleVerbs.get(0),createVerbalNoun(verb,construction.getRootedConjugationByConstructionName(constructionName)));
            }
            return agregateVerbs(decorateVerbs(possibleVerbs,verb));
        } else if(word.isPreposition()) {
            String translationRoot = "";
            if(word.getPreferedTranslation() > 0) {
                translationRoot = getAlternateTranslation(word);
            } else {
                translationRoot = customTranslationMap.get(word.getRoot() + "P");
            }
            return translationRoot;
        }
        return "";
    }

    private Set<String> filterPastParticipleForVerbalNoun(Set<String> constructionNames) {
        if(constructionNames.size() < 2) return constructionNames;
        if(constructionNames.contains("PAP")) constructionNames.remove("PAP");
        return constructionNames;
    }

    private Word createVerbalNoun(Verb verb, RootedConjugation rootedConjugation) {
        Noun noun = new Noun(verb.getLanguage(), verb.getInitialValue(), verb.getInitialValue(), Collections.EMPTY_LIST, null, null, null, null);
        noun.setElectedCaseNumber(rootedConjugation.getElectedCaseNumber(verb.getInitialValue()));
        return noun;
    }

    private boolean isVerbalNounCase(Set<String> constructionNames, Map<String, Integer> formPositionByConstructionName) {
        if(constructionNames.size() != 1) return false;
        String uniqueConstructionName = constructionNames.iterator().next();
        return formPositionByConstructionName.get(uniqueConstructionName).intValue() == ConjugationPosition.RELATED_TO_NOUN.getIndice();
    }

    private List<String> decorateVerbs(List<String> possibleVerbs, Verb verb) {
        if(!verb.getGender().isFeminine()) return possibleVerbs;
        List<String> verbsDecorated = new ArrayList<>();
        for(String verbValue : possibleVerbs) {
            if(verb.getGender().isFeminine()) {
                verbValue += "(e)";
            }
            verbsDecorated.add(verbValue);
        }
        return verbsDecorated;
    }

    private String getAlternateTranslation(Word word) {
        String fullWordDefinition = null;
        String root = word.getRoot() + "@";
        for(String definition : dictionaryDefinitions) {
            if(definition.startsWith(root) && wordHasCorrectExpectedType(definition, word.getWordType())) {
                fullWordDefinition = definition;
                break;
            }
        }
        return getAlternateDictionaryTranslation(fullWordDefinition, word.getPreferedTranslation(), word);
    }

    protected boolean wordHasCorrectExpectedType(String definition, WordType wordType) {
        return definition.contains(wordType.getDefinitionString().toLowerCase());
    }

    private String getAlternateDictionaryTranslation(String fullWordDefinition, int preferedTranslation, Word word) {
        if(word.isPreposition()) {
            Preposition preposition = (Preposition)word;
            preposition.avoidDoubleNumberTranslation();
        }
        fullWordDefinition = fullWordDefinition.substring(fullWordDefinition.indexOf("%"));
        String beginning = fullWordDefinition.substring(fullWordDefinition.indexOf(preferedTranslation + ""), fullWordDefinition.indexOf(preferedTranslation+1+ ""));
        return beginning.split("=")[1].split("%")[0];
    }

    private TranslationInformationBean getTranslationbean(Word word, String frenchRoot) {
        List<TranslationInformationBean> translationInformationBeans = originLanguageVerbFormConstruction.get(frenchRoot);
        if(translationInformationBeans == null) {
            System.out.println("verbe à compléter " + word);
            return null;
        }
        if(translationInformationBeans.size() == 1) return translationInformationBeans.get(0);
        for(TranslationInformationBean translationInformationBean : translationInformationBeans)  {
            if(translationInformationBean.hasNoForms()) continue;
            if(translationInformationBean.hasRoot(word)) {
                return translationInformationBean;
            }
        }
        TranslationInformationBean mostProbableTranslationBean = getMostProbableTranslationBean(translationInformationBeans, word);
        if(mostProbableTranslationBean != null) {
            return mostProbableTranslationBean;
        }
        for(TranslationInformationBean translationInformationBean : translationInformationBeans)  {
            if(translationInformationBean.hasWordRoot(word)) {
                return translationInformationBean;
            }
        }
        return null;
    }

    private TranslationInformationBean getMostProbableTranslationBean(List<TranslationInformationBean> beans, Word word) {
        for(TranslationInformationBean bean : beans) {
            if(bean.hasNearRoot(word)) {
                return bean;
            }
        }
        return null;
    }

    private String agregateVerbs(List<String> possibleVerbs) {
        Set<String> setStr =  new HashSet<>(possibleVerbs);
        possibleVerbs = new ArrayList<>(filterVerbValues(setStr));
        Collections.sort(possibleVerbs);
        if(possibleVerbs.size() == 1)  {
            return possibleVerbs.get(0);
        }
        StringBuilder sb = new StringBuilder();
        for(String possibleVerb : possibleVerbs) {
            sb.append(possibleVerb).append("/");
        }
        if(sb.length() == 0) return "YYY";
        return sb.deleteCharAt(sb.length()-1).toString();
    }

    private Set<String> filterVerbValues(Set<String> setStr) {
        Set<String> newSet = new HashSet<>();
        for(String value : setStr) {
            if("-".equals(value)) continue;
            else if("".equals(value)) continue;
            newSet.add(value);
        }
        return newSet;
    }

    protected abstract SpecificLanguageSelector getLanguageSelector();


    private List<String> extractTranslation(Map<String, Integer> formPositionByConstructionName, String frenchVerbDescription, int suggestedPositionInTranslation, Verb verb) {
        if(frenchVerbDescription == null) return Collections.singletonList(UNKNOWN_TRANSLATION);
        List<String> allForms = Arrays.asList(frenchVerbDescription.split("%"));
        List<String> resultsFound = new ArrayList<>();
        for(String constructionName : formPositionByConstructionName.keySet()) {
            String constructionNameAlone = extractExactVerbTime(constructionName);
            String constructionNameForPatternMatching = "[" + constructionNameAlone + "]";
            if(verb.isConjugationForbidden(constructionNameAlone)) continue;
            if(!frenchVerbDescription.contains(constructionNameForPatternMatching)) continue;
            String rightForm = allForms.stream().filter(form -> new FrenchVerbPattern(form).hasExactKey (handleConstructionSynonyms(constructionNameAlone))).findFirst().get();
            //String rightForm = allForms.stream().filter(form -> form.contains(handleConstructionSynonyms(constructionNameAlone))).findFirst().get();
            List<String> translations = Arrays.asList(rightForm.split("=")[1].replace("]", "").replace("[", "").split(","));
            Integer formPosition = formPositionByConstructionName.get(constructionName);
            if(suggestedPositionInTranslation != 100 && suggestedPositionInTranslation > translations.size()) continue;
            if(formPosition == -1) continue;
            if(translations.size() > formPosition && formPosition > 0) {
              resultsFound.add(translations.get(formPosition));
            } else {
                resultsFound.add(translations.get(0));
            }
        }
        if(resultsFound.size() == 0 && suggestedPositionInTranslation != 100) resultsFound = extractTranslation(formPositionByConstructionName, frenchVerbDescription, 100, verb);
        return resultsFound;
    }

    private String extractExactVerbTime(String constructionName) {
        if(!constructionName.contains("@")) return constructionName;
        return constructionName.split("@")[1];
    }

    private String handleConstructionSynonyms(String constructionName) {
        String conjugationSysnonym = verbRepository.getConjugationSysnonym(constructionName);
        return conjugationSysnonym != null ? conjugationSysnonym : constructionName;
    }

    protected String numberCaseDecorate(String translationRoot, Word word) {
        Noun noun = (Noun)word;
        CaseNumberGenre caseNumber = noun.getElectedCaseNumber();
        return numberCaseDecorateWithElectedCaseNumberGenre(noun, translationRoot, caseNumber);
    }

    protected String numberCaseDecorateWithElectedCaseNumberGenre(Noun noun, String translationRoot, CaseNumberGenre caseNumber) {
        String genitiveArticle = "du";
        String dativeArticle = "au";
        String ablativeArticle = "par";
        if(caseNumber.isPlural()) {
            translationRoot += "(s)";
            genitiveArticle = "des";
            dativeArticle = "aux";
        }
        if(caseNumber.isGenitive()) {
            translationRoot = "(" + genitiveArticle + ") " + translationRoot;
        } else if(caseNumber.isConstructedState()) {
            translationRoot += " (" + genitiveArticle + ")";
        } else if(caseNumber.isDative() && languageDecorator.dativeHandlerIsTrue(noun)) {
            translationRoot = "(" + dativeArticle + ") " + translationRoot;
        } else if (caseNumber.isAblative() && languageDecorator.ablativeHandlerIsTrue(noun)) {
            translationRoot = "(" + ablativeArticle + ") " + translationRoot;
        }
        if(caseNumber.getGender() != null && caseNumber.getGender().equals(new Gender(Gender.FEMININE)) && !caseNumber.isPlural()) {
            if(translationRoot.equals("le")) {
                return "la";
            }
            if(!caseNumber.isInvariable()) {
                translationRoot = translationRoot + "(e)";
            }
        }
        if(caseNumber.getGender() != null && caseNumber.getGender().equals(new Gender(Gender.FEMININE)) && caseNumber.isPlural()) {
            if(translationRoot.equals("le")) {
                return "les";
            }
            if(!caseNumber.isInvariable()) {
                translationRoot = translationRoot + "(e)";
            }
        }
        /*
        if(caseNumber.isConstructedState()) {
            translationRoot = translationRoot + "(" + genitiveArticle + ") ";
        }
        */
        if(translationRoot == null || translationRoot.equals("null")) {
            System.out.println("wtf");
        }
        return translationRoot;
    }

    private void computeNoun(String[] originFrenchs, String[] typeTranslations, String lineInDictionaryData) {
        String type = typeTranslations[0];
        String[] typeDeclension = type.split("!");
        if(checkArrayError(typeDeclension, lineInDictionaryData)) return;
        String declensionStr = typeDeclension[1];
        String exceptionKey = lineInDictionaryData.split("@")[0];
        Map<CaseNumberGenre, String> exceptions = nounRepository.getExceptionByDefinition(exceptionKey);
        String root = originFrenchs[0];
        originLanguageDeclensionFactory.setTemporaryGenderAndRoot(null, root);
        Declension declension = originLanguageDeclensionFactory.getDeclensionByPattern(declensionStr);
        if(declension.isNull()) {
            System.out.println("unknown declension for " + lineInDictionaryData);
            return;
        }
        //declension.getAllEndings().entrySet().stream().forEach(ending -> formRepository.addForm(new Form(root + ending.getValue(), root, WordType.NOUN), new Form(root, root, WordType.NOUN), exceptions, ending.getKey()));
        Set<Map.Entry<CaseNumberGenre, String>> entrySet = declension.getAllEndings().entrySet();
        for(Map.Entry entry : entrySet) {
            String value = declension.isCustom() ? (String)entry.getValue() : root + entry.getValue();
            Form firstForm = new Form(value, root, WordType.NOUN, declensionStr,0, infinitiveBuilder);
            Form secondForm = new Form(root, root, WordType.NOUN, declensionStr,0, infinitiveBuilder);
            formRepository.addForm(firstForm, secondForm, exceptions, (CaseNumberGenre)entry.getKey());
        }
    }

    private void computeForms(String lineInDictionaryData) {
        if(lineInDictionaryData == null || lineInDictionaryData.length() == 0) {
            return;
        }
        String[] originFrenchs = lineInDictionaryData.split("@");
        if(checkArrayError(originFrenchs, lineInDictionaryData)) return;
        String origin = originFrenchs[0];
        String frenchs = originFrenchs[1];
        String[] typeTranslations = frenchs.split("%");
        if(checkArrayError(typeTranslations, lineInDictionaryData)) return;
        String type = typeTranslations[0];
        if(type.contains("noun") || type.contains("adj")) {
            computeNoun(originFrenchs, typeTranslations, lineInDictionaryData);
            allTranslationMap.put(origin, getTranslations(frenchs));
            if(mainTranslationMap.containsKey(origin + "N")) {
                String conjugationCode = type.split("!")[1];
                String keyForAlreadyExistingTranslation = origin + "N" + conjugationGenderAnalyser.getGenderByConjugationCode(conjugationCode) + "D" + conjugationCode;
                String translation = getMainTranslation(frenchs, lineInDictionaryData);
                mainTranslationMap.put(keyForAlreadyExistingTranslation, translation);
            } else {
                mainTranslationMap.put(origin + "N", getMainTranslation(frenchs, lineInDictionaryData));
            }
        } else if(type.contains("verb")) {
            computeVerb(origin, mostCommonFrenchTranslation(frenchs));
        } else if(type.contains("prep")) {
            formRepository.addForm(new Form(origin, origin, WordType.PREPOSITION, origin,0, infinitiveBuilder), new Form(origin, origin, WordType.PREPOSITION, origin,0, infinitiveBuilder));
            allTranslationMap.put(origin, getTranslations(frenchs));
            String mainTranslation = getMainTranslation(frenchs, lineInDictionaryData);
            if(hasDoubleTranslation(mainTranslation)) {
                String usualTranslation = mainTranslation.split("\\[")[0];
                String alternativesTranslations = mainTranslation.split("\\[")[1].split("]")[0];
                String[] translations = alternativesTranslations.split(",");
                String secondTranslation = null;
                String thirdTranslation = null;
                mainTranslationMap.put(origin + "P", usualTranslation);
                if(translations.length == 2) {
                    secondTranslation = translations[0];
                    thirdTranslation = translations[1];
                    mainTranslationMap.put(origin + "SECONDGENDER", thirdTranslation);
                } else {
                    secondTranslation = alternativesTranslations;
                }
                mainTranslationMap.put(origin + "SECONDNUMBER", secondTranslation);
            } else {
                mainTranslationMap.put(origin + "P", mainTranslation);
            }
        } else if(type.contains("demo")) {
            /*
            formRepository.addForm(new Form(origin, origin, WordType.DEMONSTRATIVE, origin,0),new Form(origin, origin, WordType.DEMONSTRATIVE, origin,0));
            mainTranslationMap.put(origin + "D",getMainTranslation(frenchs, lineInDictionaryData));
            computeDemonstrative(origin, originFrenchs, lineInDictionaryData);
            */
        } else {
            System.out.println("WARNING : line not used : " + lineInDictionaryData);
        }
    }

    private boolean hasDoubleTranslation(String mainTranslation) {
        return mainTranslation.contains("[") && mainTranslation.contains("]");
    }

    private void computeFrenchVerbs(String lineInDictionaryData) {
        if(lineInDictionaryData == null || lineInDictionaryData.length() == 0) {
            return;
        }
        String[] originFrenchs = lineInDictionaryData.split("@");
        if(checkArrayError(originFrenchs, lineInDictionaryData)) return;
        String origin = originFrenchs[0];
        String frenchs = originFrenchs[1];
        frenchVerbs.put(origin, frenchs);
    }

    private String[] mostCommonFrenchTranslation(String frenchs) {
        frenchs = frenchs.replace("verb!norm%","");
        frenchs = frenchs.replace("verb!irrg%","");
        frenchs = frenchs.replace("verb!%","");
        String[] strings = frenchs.split("%");
        int indice = 0;
        for(String s : strings) {
            int egalPosition = s.lastIndexOf("=");
            s = s.substring(egalPosition + 1);
            strings[indice] = s;
            indice++;
        }
        return  strings;
    }

    protected void computeVerb(String root, String[] mostCommonTranslations) {
        TranslationInformationBean allFormsForTheVerbRoot = verbRepository.getAllFormsForTheVerbRoot(root);
        int indiceOfPreferedTranslation = 1;
        for(String mostCommonTranslation : mostCommonTranslations) {
            //allFormsForTheVerbRoot.allForms().stream().forEach(eachForm -> formRepository.addForm(new Form(eachForm, allFormsForTheVerbRoot.getRoot(), WordType.VERB, null,indiceOfPreferedTranslation), new Form(mostCommonTranslation, mostCommonTranslation, WordType.VERB, null,indiceOfPreferedTranslation)));
            List<String> allForms = allFormsForTheVerbRoot.allForms();
            for(String form : allForms) {
                formRepository.addForm(new Form(form, allFormsForTheVerbRoot.getRoot(), WordType.VERB, null,indiceOfPreferedTranslation,infinitiveBuilder), new Form(mostCommonTranslation, mostCommonTranslation, WordType.VERB, null,indiceOfPreferedTranslation,infinitiveBuilder));
            }
            if (!originLanguageVerbFormConstruction.containsKey(mostCommonTranslation)) {
                List allFormsInList = new ArrayList<TranslationInformationBean>();
                allFormsInList.add(allFormsForTheVerbRoot);
                originLanguageVerbFormConstruction.put(mostCommonTranslation, allFormsInList);
            } else {
                originLanguageVerbFormConstruction.get(mostCommonTranslation).add(allFormsForTheVerbRoot);
            }
            indiceOfPreferedTranslation++;
        }
        if(indiceOfPreferedTranslation > 2) {
            verbRepository.addIndiceOfPreferedTranslation(root, indiceOfPreferedTranslation);
        }
    }

    protected boolean checkArrayError(String[] array, String lineInDictionaryData) {
        if(array == null || array.length < 2) {
            System.out.println("error in dictionary definitions " + dictionaryDefinitions + " " + lineInDictionaryData);
            return true;
        }
        return false;
    }

    protected void populateAllForms() {
        frenchVerbsDefinitions.forEach(lineInDictionaryData -> computeFrenchVerbs(lineInDictionaryData));
        dictionaryDefinitions.forEach(lineInDictionaryData -> computeForms(lineInDictionaryData));
    }

    private String getMainTranslation(String frenchs, String lineInDictionaryData) {
        String[] allTranslations = frenchs.split("%");
        return cleanTranslation(allTranslations[allTranslations.length-1], lineInDictionaryData);
    }

    private String cleanTranslation(String allTranslation, String lineInDictionaryData) {
        String[] typeTranslation = allTranslation.split("=");
        if(checkArrayError(typeTranslation, lineInDictionaryData)) return null;
        return typeTranslation[1];
    }

    private String getTranslations(String frenchs) {
        String[] allTranslations = frenchs.split("%");
        List<String> translationList = new ArrayList<>(Arrays.asList(allTranslations));
        int lastIndice = translationList.size()-1;
        translationList.remove(lastIndice);
        translationList.remove(0);
        return String.join(" - ", translationList);
    }
}

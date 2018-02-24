package org.patrologia.translator.basicelements;

import org.patrologia.translator.casenumbergenre.Case;
import org.patrologia.translator.casenumbergenre.CaseFactory;
import org.patrologia.translator.rule.Rule;
import org.patrologia.translator.rule.RuleFactory;

import java.util.*;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public class PrepositionRepository extends Accentuer {

    private Map<String, Preposition> prepositionMap = new HashMap<String, Preposition>();
    private CaseFactory caseFactory;
    private RuleFactory ruleFactory;
    private Language language;

    public PrepositionRepository(Language language, CaseFactory caseFactory, RuleFactory ruleFactory, List<String> definitions) {
        this.caseFactory = caseFactory;
        this.ruleFactory = ruleFactory;
        this.language = language;
        for (String definition : definitions) {
            addPreposition(definition);
        }
    }

    public boolean hasPreposition(String initialValue) {
        /*
        Set<String> strings = prepositionMap.keySet();
        List<String> orderedStrings = new ArrayList<>(strings);
        Collections.sort(orderedStrings);
        for(String s : orderedStrings) {
            System.out.println(s);
        }
        */
        return prepositionMap.containsKey(initialValue) || prepositionMap.containsKey(unaccentued(initialValue));
    }

    public Preposition getPreposition(String initialValue) {
        return prepositionMap.get(initialValue) != null ?
                prepositionMap.get(initialValue) :
                prepositionMap.get(unaccentued(initialValue));
    }

    private void addPreposition(String definition) {
        if (definition == null || definition.length() == 0) return;
        String[] nameForms = definition.split("@");
        String root = nameForms[0];
        String[] forms = nameForms[1].split("%");
        List<String> descriptions = new ArrayList<String>();
        boolean affectEveryRelatedWord = forms.length <= 1 ? true : false;
        Preposition preposition = null;
        for (String form : forms) {
            if (form.contains("(") && form.contains(")")) {
                String caseString = form.substring(form.indexOf("(") + 1, form.indexOf(")"));
                Case _case = caseFactory.getCaseByStringPattern(caseString,null);
                preposition = new Preposition(language, root, _case, affectEveryRelatedWord);
            }
            if (form.contains("[") && form.contains("]")) {
                String ruleNames = form.substring(form.indexOf("[") + 1, form.indexOf("]"));
                String[] ruleNamesSplitted = ruleNames.split(",");
                for (String ruleName : ruleNamesSplitted) {
                    Rule rule = ruleFactory.getRuleByName(ruleName,root);
                    preposition.addRule(rule);
                }
            }
            if(form.contains("!")) {
                preposition.defineAsArticle();
            }
            descriptions.add(form);
        }
        for (String description : descriptions) {
            if(preposition ==  null) { System.out.println(description);}
            preposition.addDescription(description);
        }
        prepositionMap.put(root, preposition);
    }

    public List<String> getValuesStartingWith(String pattern) {
        List<String> prepValues = new ArrayList<>();
        for (String prepValue : prepositionMap.keySet()) {
            if (prepValue.startsWith(pattern)) {
                prepValues.add(prepValue);
                prepValues.add(unaccentued(prepValue));
                prepValues.add(unaccentuedWithSofit(prepValue));
            }
        }
        return prepValues;
    }

    public Collection<? extends String> getValuesEndingWith(String endingPattern) {
        List<String> prepValues = new ArrayList<>();
        for(String prep : prepositionMap.keySet()) {
            if(prep.endsWith(endingPattern)) {
                prepValues.add(prep);
            }
        }
        return prepValues;
    }

    public RuleFactory getRuleFactory() {
        return ruleFactory;
    }


}

package org.patrologia.translator.basicelements;

import org.patrologia.translator.translationrules.hebrew.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lkloeble on 01/10/2017.
 */
public class TranslationRuleFactory {


    public static TranslationRule getTranslationRule(String definitionElements) {
        String[] elements = definitionElements.split("%");
        if(elements.length < 2) return null;
        String time = elements[0];
        String ruleName = extractRuleName(elements[1]);
        List<Integer> indices = extractIndices(elements[1]);
        String ruleParameters = elements[2];
        switch(ruleName) {
            case "leadingrootletter" :
                return new LeadingRootLetter(time,ruleParameters);
            case "secondletterroot" :
                return new SecondLetterRoot(time,ruleParameters);
            case "sofitrootletter" :
                return new SofitRootLetter(time,ruleParameters);
            case "sofitaccentuedletter" :
                return new SofitAccentuedLetter(time,ruleParameters);
            case "alternateaccentuation" :
                return new AlternateAccentuation(time,ruleParameters,indices);
        }
        return null;
    }

    private static List<Integer> extractIndices(String element) {
        if(!containsNumber(element)) return Collections.emptyList();
        int position = getFirstNumberPosition(element);
        String indicesString = element.substring(position);
        List<Integer> indices = new ArrayList<>();
        String[] indicesArray = indicesString.split(":");
        for(String indiceString : indicesArray) {
            indices.add(Integer.parseInt(indiceString));
        }
        return indices;
    }

    private static String extractRuleName(String element) {
        if(!containsNumber(element)) return element;
        int position = getFirstNumberPosition(element);
        return element.substring(0,position);
    }

    private static int getFirstNumberPosition(String element) {
        char[] chars = element.toCharArray();
        int position = 0;
        for(char c : chars) {
            if(c >= '0' && c <= '9') return position;
            position++;
        }
        return position;
    }

    private static boolean containsNumber(String element) {
        char[] chars = element.toCharArray();
        for(char c : chars) {
            if(c >= '0' && c <= '9') return true;
        }
        return false;
    }
}

package org.patrologia.translator.declension.hebrew;

import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionFactory;
import org.patrologia.translator.declension.NullDeclension;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
public class HebrewDeclensionFactory extends DeclensionFactory {

    public HebrewDeclensionFactory(List<String> declensionsDefinitions, List<Declension> declensionList) {
        declensions = new HashMap<>();
        declensionMap = new HashMap<>();
        populateDeclensionMap(declensionsDefinitions, declensionList);
    }

    @Override
    public Declension getDeclensionByPattern(String declensionPattern) {
        if(declensionPattern == null) {
            return new NullDeclension();
        }
        if(declensionMap.containsKey(declensionPattern)) {
            return declensionMap.get(declensionPattern);
        }
        if(isCustomPluralAndConstructedState(declensionPattern)) {
            HebrewCustomDeclension customHebrewDeclension = new HebrewCustomDeclension(declensionPattern, gender, root);
            return customHebrewDeclension;
        }
        String declensionFile = declensions.get(declensionPattern.toLowerCase());
        Declension result = null;//declensionFile != null ? new HebrewDeclension(declensionPath,) : new NullDeclension();
        declensionMap.put(declensionPattern,result);
        return result;
    }

    protected boolean isCustomPluralAndConstructedState(String declensionPattern) {
        return declensionPattern.startsWith("custom(") && declensionPattern.endsWith(")");
    }

    @Override
    public Language getLanguage() {
        return Language.HEBREW;
    }

}

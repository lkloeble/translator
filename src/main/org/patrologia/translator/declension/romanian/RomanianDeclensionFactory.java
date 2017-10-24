package org.patrologia.translator.declension.romanian;

import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionFactory;
import org.patrologia.translator.declension.NullDeclension;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianDeclensionFactory extends DeclensionFactory {

    private static final String INVARIABLE_DECLENSION = "inv";

    public RomanianDeclensionFactory(List<String> declensionsDefinitions, List<Declension> declensionList) {
        this.language = Language.ROMANIAN;
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
            RomanianCustomDeclension customEnglishDeclension = new RomanianCustomDeclension(declensionPattern, gender, root);
            return customEnglishDeclension;
        }
        boolean isInvariable = false;
        if(declensionPattern.equals(INVARIABLE_DECLENSION)) isInvariable = true;
        Declension result = declensionMap.get(declensionPattern);
        declensionMap.put(declensionPattern,result);
        return result;
    }


}

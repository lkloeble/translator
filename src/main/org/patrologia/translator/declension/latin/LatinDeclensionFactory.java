package org.patrologia.translator.declension.latin;

import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionFactory;
import org.patrologia.translator.declension.NullDeclension;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public class LatinDeclensionFactory extends DeclensionFactory {

    public LatinDeclensionFactory(List<String> declensionsDefinitions, List<Declension> declensionList) {
        declensions = new HashMap<>();
        declensionMap = new HashMap<>();
        populateDeclensionMap(declensionsDefinitions, declensionList);
    }

    @Override
    public Declension getDeclensionByPattern(String declensionPattern) {
        if(declensionPattern == null) {
            return new NullDeclension();
        }
        Declension result = declensionMap.get(declensionPattern);
        declensionMap.put(declensionPattern,result);
        return result;
    }

    @Override
    public Language getLanguage() {
        return Language.LATIN;
    }

}

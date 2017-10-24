package org.patrologia.translator.declension.german;

import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionFactory;
import org.patrologia.translator.declension.NullDeclension;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
public class GermanDeclensionFactory extends DeclensionFactory {


    public GermanDeclensionFactory(List<String> declensionsDefinitions, List<Declension> declensionList) {
        this.language = Language.GERMAN;
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
}

package patrologia.translator.declension.german;

import patrologia.translator.basicelements.Language;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionFactory;
import patrologia.translator.declension.NullDeclension;

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

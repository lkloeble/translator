package patrologia.translator.declension.latin;

import patrologia.translator.basicelements.Language;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionFactory;
import patrologia.translator.declension.NullDeclension;

import java.util.HashMap;
import java.util.List;

public class LatinDeclensionFactory  extends DeclensionFactory {

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

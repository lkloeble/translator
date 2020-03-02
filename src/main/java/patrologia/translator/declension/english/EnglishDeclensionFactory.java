package patrologia.translator.declension.english;

import patrologia.translator.basicelements.Language;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionFactory;
import patrologia.translator.declension.NullDeclension;

import java.util.HashMap;
import java.util.List;

public class EnglishDeclensionFactory extends DeclensionFactory {

    public EnglishDeclensionFactory(List<String> declensionsDefinitions, List<Declension> declensionList) {
        this.language = Language.ENGLISH;
        declensions = new HashMap<>();
        declensionMap = new HashMap<>();
        populateDeclensionMap(declensionsDefinitions, declensionList);
    }

    @Override
    public Declension getDeclensionByPattern(String declensionPattern) {
        if(declensionPattern == null) {
            return new NullDeclension();
        }
        if(isCustomPluralAndConstructedState(declensionPattern)) {
            EnglishCustomDeclension customEnglishDeclension = new EnglishCustomDeclension(declensionPattern, gender, root);
            return customEnglishDeclension;
        }
        Declension result = declensionMap.get(declensionPattern);
        declensionMap.put(declensionPattern,result);
        return result;
    }
}

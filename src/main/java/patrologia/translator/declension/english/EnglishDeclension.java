package patrologia.translator.declension.english;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.english.EnglishCaseFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

public class EnglishDeclension extends Declension {

    protected EnglishCaseFactory englishCaseFactory = new EnglishCaseFactory();
    private DeclensionLoader declensionLoader = new DeclensionLoader();

    public EnglishDeclension(String endingsFilePath, List<String> declensionElements) {
        this.endingsFilePath = endingsFilePath;
        initializeMap(declensionElements);
    }

    public Map<CaseNumberGenre, String> getAllEndings() {
        return allEndings;
    }

    private void initializeMap(List<String> declensionElements) {
        allEndings = declensionLoader.getEndings(declensionElements, englishCaseFactory);
    }
}

package patrologia.translator.declension.hebrew;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

public class HebrewDeclension extends Declension {

    protected HebrewCaseFactory hebrewCaseFactory = new HebrewCaseFactory();
    private DeclensionLoader declensionLoader = new DeclensionLoader();

    public HebrewDeclension(String endingsFilePath, List<String> declensionElements) {
        this.endingsFilePath = endingsFilePath;
        initializeMap(declensionElements);
    }

    public Map<CaseNumberGenre, String> getAllEndings() {
        return allEndings;
    }

    private void initializeMap(List<String> declensionElements) {
        allEndings = declensionLoader.getEndings(declensionElements, hebrewCaseFactory);
    }

    @Override
    public String toString() {
        return "HebrewDeclension{" +
                "allEndings=" + allEndings +
                '}';
    }
}

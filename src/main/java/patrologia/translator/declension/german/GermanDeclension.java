package patrologia.translator.declension.german;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.german.GermanCaseFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

public class GermanDeclension extends Declension {

    protected GermanCaseFactory germanCaseFactory = new GermanCaseFactory();
    private DeclensionLoader declensionLoader = new DeclensionLoader();

    public GermanDeclension(String endingsFilePath, List<String> declensionElements) {
        this.endingsFilePath = endingsFilePath;
        initializeMap(declensionElements);
    }

    public Map<CaseNumberGenre, String> getAllEndings() {
        return allEndings;
    }

    private void initializeMap(List<String> declensionElements) {
        allEndings = declensionLoader.getEndings(declensionElements, germanCaseFactory);
    }

}


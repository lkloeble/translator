package patrologia.translator.declension.greek;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.greek.GreekCaseFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

public class GreekDeclension extends Declension {

    protected GreekCaseFactory greekCaseFactory = new GreekCaseFactory();
    private DeclensionLoader declensionLoader = new DeclensionLoader();

    public GreekDeclension(String endingsFilePath, List<String> declensionElements) {
        this.endingsFilePath = endingsFilePath;
        initializeMap(declensionElements);
    }

    public Map<CaseNumberGenre, String> getAllEndings() {
        return allEndings;
    }

    private void initializeMap(List<String> declensionElements) {
        allEndings = declensionLoader.getEndings(declensionElements, greekCaseFactory);
    }

    @Override
    public String toString() {
        return "GreekDeclension{" +
                "allEndings='" + allEndings + '\'' +
                "endingsFilePath='" + endingsFilePath + '\'' +
                '}';
    }
}

package patrologia.translator.declension.latin;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.latin.LatinCaseFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 13/10/2015.
 */
public class LatinDeclension extends Declension {

    protected LatinCaseFactory latinCaseFactory = new LatinCaseFactory();
    private DeclensionLoader declensionLoader = new DeclensionLoader();

    public LatinDeclension(String endingsFilePath, List<String> declensionElements) {
        this.endingsFilePath = endingsFilePath;
        initializeMap(declensionElements);
    }

    public Map<CaseNumberGenre, String> getAllEndings() {
        return allEndings;
    }

    private void initializeMap(List<String> declensionElements) {
        allEndings = declensionLoader.getEndings(declensionElements, latinCaseFactory);
    }

}

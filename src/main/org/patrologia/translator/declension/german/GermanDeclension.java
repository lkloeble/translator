package org.patrologia.translator.declension.german;

import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.german.GermanCaseFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 20/10/2015.
 */
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

package org.patrologia.translator.declension.hebrew;

import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 15/10/2015.
 */
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

}

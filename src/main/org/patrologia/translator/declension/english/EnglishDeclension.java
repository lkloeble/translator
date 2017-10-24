package org.patrologia.translator.declension.english;

import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.english.EnglishCaseFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 21/10/2015.
 */
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

package org.patrologia.translator.declension.greek;

import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.greek.GreekCaseFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 13/10/2015.
 */
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

package org.patrologia.translator.declension.greek;

import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.greek.GreekCaseFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionLoader;

import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 13/10/2015.
 */
public class GreekDeclension extends Declension {

    protected GreekCaseFactory greekCaseFactory = new GreekCaseFactory();
    protected String endingsFilePath;
    private DeclensionLoader declensionLoader = new DeclensionLoader();

    public GreekDeclension(String endingsFilePath) {
        this.endingsFilePath = endingsFilePath;
        initializeMap();
    }

    public Map<CaseNumberGenre, String> getAllEndings() {
        return allEndings;
    }

    private void initializeMap() {
        //allEndings = declensionLoader.getEndings(endingsFilePath, greekCaseFactory);
    }

    @Override
    public String toString() {
        return "GreekDeclension{" +
                "allEndings='" + allEndings + '\'' +
                "endingsFilePath='" + endingsFilePath + '\'' +
                '}';
    }
}

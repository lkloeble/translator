package org.patrologia.translator.declension.greek;

import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.DeclensionFactory;
import org.patrologia.translator.declension.NullDeclension;

import java.util.HashMap;

/**
 * Created by Laurent KLOEBLE on 08/10/2015.
 */
public class GreekDeclensionFactory extends DeclensionFactory {

    public GreekDeclensionFactory(String declensionPath, String declensionsAndFiles) {
        this.declensionsAndFiles = declensionsAndFiles;
        this.declensionPath = declensionPath;
        this.language = Language.GREEK;
        declensions = new HashMap<>();
        //populateDeclensionMap();
    }

    @Override
    public Declension getDeclensionByPattern(String declensionPattern) {
        if(declensionPattern == null) {
            return new NullDeclension();
        }
        String declensionFile = declensions.get(declensionPattern.toLowerCase());
        return declensionFile != null ? new GreekDeclension(declensionPath + "\\" + declensionFile) : new NullDeclension();
    }
}

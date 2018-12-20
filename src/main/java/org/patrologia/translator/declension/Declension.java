package patrologia.translator.declension;

import patrologia.translator.casenumbergenre.CaseNumberGenre;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public abstract class Declension {

    protected Map<CaseNumberGenre, String> allEndings = new HashMap<CaseNumberGenre, String>();

    public abstract Map<CaseNumberGenre, String> getAllEndings();

    protected String endingsFilePath;

    public boolean hasChangeRoot() {
        return false;
    }

    public boolean isWithoutArticle() { return false;}

    @Override
    public String toString() {
        return super.toString();
    }

    public boolean isCustom() { return false;}

    public boolean isNull() { return false;}

    public String getSuffixForDifferentier(String differentier) {
        if(isCustom()) return "";
        return "h";//à coder le jour où ce ne sera pas un h pour les noms féminins en déclined state hebrew
    }

    public CaseNumberGenre getCaseNumberGenreByEndingValue(String initialValue) {
        for(CaseNumberGenre caseNumberGenre : allEndings.keySet()) {
            String value = allEndings.get(caseNumberGenre);
            if(initialValue.endsWith(value)) return caseNumberGenre;
        }
        return null;
    }
}

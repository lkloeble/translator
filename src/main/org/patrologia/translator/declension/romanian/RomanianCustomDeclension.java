package org.patrologia.translator.declension.romanian;

import org.patrologia.translator.casenumbergenre.CaseNumberGenre;
import org.patrologia.translator.casenumbergenre.Gender;
import org.patrologia.translator.casenumbergenre.romanian.RomanianCaseFactory;
import org.patrologia.translator.declension.CustomDeclension;

/**
 * Created by lkloeble on 18/04/2017.
 */
public class RomanianCustomDeclension extends CustomDeclension {

    public RomanianCustomDeclension(String pattern, Gender gender, String root) {
        super(pattern, gender, root, new RomanianCaseFactory());
    }

    protected CaseNumberGenre getCaseNumberGenre(String cnb) {
        switch(cnb) {
            case "plr"://plural nominative standard
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), org.patrologia.translator.casenumbergenre.Number.PLURAL, gender);
            case "sing"://alter nominative standard
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), org.patrologia.translator.casenumbergenre.Number.SINGULAR, gender);
            case "root"://singular nominative standard, aka root itself
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), org.patrologia.translator.casenumbergenre.Number.SINGULAR, gender);
        }
        return null;
    }

}

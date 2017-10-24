package org.patrologia.translator.declension.english;

import org.patrologia.translator.casenumbergenre.*;
import org.patrologia.translator.casenumbergenre.english.EnglishCaseFactory;
import org.patrologia.translator.declension.CustomDeclension;

/**
 * Created by lkloeble on 20/03/2017.
 */
public class EnglishCustomDeclension extends CustomDeclension {

    public EnglishCustomDeclension(String pattern, Gender gender, String root) {
        super(pattern,gender,root,new EnglishCaseFactory());
    }

    protected CaseNumberGenre getCaseNumberGenre(String cnb) {
        switch(cnb) {
            case "plr"://plural nominative standard
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), org.patrologia.translator.casenumbergenre.Number.PLURAL, gender);
            case "root"://singular nominative standard, aka root itself
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), org.patrologia.translator.casenumbergenre.Number.SINGULAR, gender);
        }
        return null;
    }


}

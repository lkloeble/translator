package patrologia.translator.declension.english;

import patrologia.translator.casenumbergenre.*;
import patrologia.translator.casenumbergenre.Number;
import patrologia.translator.casenumbergenre.english.EnglishCaseFactory;
import patrologia.translator.declension.CustomDeclension;

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
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), Number.PLURAL, gender);
            case "root"://singular nominative standard, aka root itself
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), Number.SINGULAR, gender);
        }
        return null;
    }


}

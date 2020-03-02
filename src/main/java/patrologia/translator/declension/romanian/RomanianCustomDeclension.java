package patrologia.translator.declension.romanian;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.romanian.RomanianCaseFactory;
import patrologia.translator.declension.CustomDeclension;

import static patrologia.translator.casenumbergenre.Number.PLURAL;
import static patrologia.translator.casenumbergenre.Number.SINGULAR;

public class RomanianCustomDeclension extends CustomDeclension {

    public RomanianCustomDeclension(String pattern, Gender gender, String root) {
        super(pattern, gender, root, new RomanianCaseFactory());
    }

    protected CaseNumberGenre getCaseNumberGenre(String cnb) {
        switch(cnb) {
            case "plr"://plural nominative standard
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), PLURAL, gender);
            case "sing"://alter nominative standard
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), SINGULAR, gender);
            case "root"://singular nominative standard, aka root itself
                return new CaseNumberGenre(caseFactory.getCaseByStringPattern("nom",null), SINGULAR, gender);
        }
        return null;
    }
}

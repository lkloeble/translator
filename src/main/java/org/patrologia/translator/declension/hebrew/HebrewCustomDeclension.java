package patrologia.translator.declension.hebrew;

import patrologia.translator.casenumbergenre.*;
import patrologia.translator.casenumbergenre.Number;
import patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import patrologia.translator.declension.Declension;

import java.util.Map;

/**
 * Created by lkloeble on 23/01/2017.
 */
public class HebrewCustomDeclension extends Declension {

    private Gender gender;
    private HebrewCaseFactory hebrewCaseFactory = new HebrewCaseFactory();

    @Override
    public boolean isCustom() {
        return true;
    }

    public HebrewCustomDeclension(String description, Gender gender, String root) {
        this.gender = gender;
        String  cleanWithoutCustom = cleanWithoutCustom(description);
        String[] allCustomValues = cleanWithoutCustom.split("\\|");
        for(String customValue : allCustomValues) {
            String[] keyValue = customValue.split("=");
            String caseKey = keyValue[0].substring(0,3);
            String differentier = keyValue[0].substring(3);
            Number number = keyValue[0].length() > 3 ? Number.strValueOf(differentier) : Number.SINGULAR;
            CaseNumberGenre caseNumberGenre = getCaseNumberGenre(caseKey, number, differentier);
            allEndings.put(caseNumberGenre, keyValue[1]);
        }
    }

    private CaseNumberGenre getCaseNumberGenre(String cnb, Number number, String differentier) {
        switch(cnb) {
            case "plr"://plural nominative standard
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("nom",differentier), number, gender);
            case "cst":// constructed state
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("cst",differentier),number, gender);
            case "gen":// gen
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("gen",differentier),number, gender);
            case "nom"://plural nominative standard
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("nom",differentier), number, gender);
            case "dec"://declined
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("dec", differentier), number, gender);
            case "dir" :
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("dir", differentier), number, gender);

        }
        return null;
    }

    private String cleanWithoutCustom(String description) {
        String withoutCustom = description.replace("custom", "");
        return withoutCustom.substring(1,withoutCustom.length()-1);
    }

    @Override
    public Map<CaseNumberGenre, String> getAllEndings() {
        return allEndings;
    }
}

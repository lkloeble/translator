package org.patrologia.translator.declension.hebrew;

import org.patrologia.translator.casenumbergenre.*;
import org.patrologia.translator.casenumbergenre.Number;
import org.patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import org.patrologia.translator.declension.Declension;

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
            String differentier = keyValue[0].length() > 3 ? keyValue[0].substring(3) : null;
            CaseNumberGenre caseNumberGenre = getCaseNumberGenre(caseKey, differentier);
            allEndings.put(caseNumberGenre, keyValue[1]);
        }
        allEndings.put(getCaseNumberGenre("roo", null), root);
    }

    private CaseNumberGenre getCaseNumberGenre(String cnb, String differentier) {
        switch(cnb) {
            case "plr"://plural nominative standard
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("nom",differentier), Number.PLURAL, gender);
            case "cst":// constructed state
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("cst",differentier),Number.PLURAL, gender);
            case "gen":// gen
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("gen",differentier),Number.PLURAL, gender);
            case "nom"://plural nominative standard
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("nom",differentier), Number.PLURAL, gender);
            case "roo"://singular nominative standard, aka root itself
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("nom",differentier), Number.SINGULAR, gender);
            case "dec"://declined
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("dec", differentier), Number.SINGULAR, gender);
            case "dir" :
                return new CaseNumberGenre(hebrewCaseFactory.getCaseByStringPattern("dir", differentier), Number.SINGULAR, gender);

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

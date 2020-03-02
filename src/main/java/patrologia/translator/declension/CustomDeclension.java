package patrologia.translator.declension;

import patrologia.translator.casenumbergenre.CaseFactory;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.NullCaseNumberGenre;

import java.util.Map;

public class CustomDeclension  extends Declension {

    protected Gender gender;
    protected CaseFactory caseFactory;

    public CustomDeclension(String pattern, Gender gender, String root, CaseFactory caseFactory) {
        this.gender = gender;
        this.caseFactory = caseFactory;
        String  cleanWithoutCustom = cleanWithoutCustom(pattern);
        String[] allCustomValues = cleanWithoutCustom.split("\\|");
        for(String customValue : allCustomValues) {
            String[] keyValue = customValue.split("=");
            CaseNumberGenre caseNumberGenre = getCaseNumberGenre(keyValue[0]);
            allEndings.put(caseNumberGenre, keyValue[1]);
        }
        allEndings.put(getCaseNumberGenre("root"), root);
    }

    protected CaseNumberGenre getCaseNumberGenre(String cnb) {
        return new NullCaseNumberGenre();
    }

    @Override
    public boolean isCustom() {
        return true;
    }

    @Override
    public Map<CaseNumberGenre, String> getAllEndings() {
        return allEndings;
    }

    private String cleanWithoutCustom(String description) {
        return description.replace("custom","").replace("(","").replace(")","");
    }


}

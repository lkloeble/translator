package patrologia.translator.declension.romanian;

import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.romanian.RomanianCaseFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.DeclensionLoader;

import java.util.List;
import java.util.Map;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianDeclension extends Declension {

    private static final String INVARIABLE_DECLENSION_FILE = "invariablemasc.txt";
    protected RomanianCaseFactory romanianCaseFactory = new RomanianCaseFactory();
    private DeclensionLoader declensionLoader = new DeclensionLoader();
    private boolean isInvariable = false;

    public RomanianDeclension(String endingsFilePath, List<String> declensionElements, boolean isInvariable) {
        this.isInvariable = isInvariable;
        this.endingsFilePath = endingsFilePath;
        initializeMap(declensionElements);
    }

    public Map<CaseNumberGenre, String> getAllEndings() {
        if(isInvariable) {
           allEndings.entrySet().stream().forEach(entry -> setInvariableCaseNumberGenre(entry.getKey()));
        }
        return allEndings;
    }

    private void setInvariableCaseNumberGenre(CaseNumberGenre caseNumberGenre) {
        caseNumberGenre.setInvariable(true);
    }

    private void initializeMap(List<String> declensionElements) {
        allEndings = declensionLoader.getEndings(declensionElements, romanianCaseFactory);
    }

    @Override
    public boolean isWithoutArticle() {
        if(endingsFilePath.endsWith(INVARIABLE_DECLENSION_FILE)) return true;
        return super.isWithoutArticle();
    }

    @Override
    public String toString() {
        return "RomanianDeclension{" +
                "endingsFilePath='" + endingsFilePath + '\'' +
                " allEndings='" + allEndings + '\'' +
                '}';
    }
}

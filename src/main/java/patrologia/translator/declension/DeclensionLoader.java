package patrologia.translator.declension;

import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseFactory;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.Number;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeclensionLoader {

    private static final String SEPARATOR = "%";
    private CaseFactory caseFactory;

    public Map<CaseNumberGenre, String> getEndings(List<String> declensionElements, CaseFactory caseFactory) {
        Map<CaseNumberGenre, String> endings = new HashMap<>();
        declensionElements.stream().forEach(line -> endings.put(getNumberCaseGenreByLine(line, caseFactory),getEndByLine(line)));
        return endings;
    }

    private String[] splitLine(String line, String separator) {
        return line.split(separator);
    }

    private CaseNumberGenre getNumberCaseGenreByLine(String line, CaseFactory caseFactory) {
        String[] splittedLine = splitLine(line, SEPARATOR);
        Case caseByStringPattern = caseFactory.getCaseByStringPattern(splittedLine[0].substring(0,3),getCaseDifferencier(splittedLine[0]));
        Number number = Number.strValueOf(splittedLine[1]);
        Gender gender = Gender.getGenderByCode(splittedLine[2]);
        return new CaseNumberGenre(caseByStringPattern,number,gender);
    }

    private String getCaseDifferencier(String pattern) {
        if(pattern.length() <= 3) return null;
        return pattern.substring(3);
    }

    private String getEndByLine(String line) {
        String[] strings = splitLine(line, SEPARATOR);
        return strings.length == 4 ? strings[3] : "";
    }

}

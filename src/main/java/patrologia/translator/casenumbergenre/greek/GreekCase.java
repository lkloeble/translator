package patrologia.translator.casenumbergenre.greek;

import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.casenumbergenre.Case;
import patrologia.translator.casenumbergenre.CaseNumberGenre;

import java.util.List;

public class GreekCase extends Case {

    protected String differentier;

    @Override
    public String getDifferentier() {
        return differentier;
    }

    public void setDifferentier(Word word) {
        if(!word.isNoun()) return;
        Noun noun = (Noun)word;
        CaseNumberGenre electedCaseNumber = noun.getElectedCaseNumber();
        String newDifferentier = null;
        if(electedCaseNumber == null) {
            List<CaseNumberGenre> possibleCaseNumbers = noun.getPossibleCaseNumbers();
            for(CaseNumberGenre caseNumberGenre : possibleCaseNumbers) {
                if(caseNumberGenre.isAccusative()) {
                    newDifferentier = caseNumberGenre.getDifferentier();
                }
            }
        } else {
            newDifferentier = electedCaseNumber.getDifferentier();
        }
        this.differentier = newDifferentier;
    }
}

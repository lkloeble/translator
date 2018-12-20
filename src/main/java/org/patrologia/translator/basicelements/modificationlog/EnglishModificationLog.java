package patrologia.translator.basicelements.modificationlog;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.preposition.Preposition;
import patrologia.translator.casenumbergenre.CaseNumberGenre;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.casenumbergenre.NullCase;

import java.util.List;

/**
 * Created by lkloeble on 02/09/2016.
 */
public class EnglishModificationLog extends ModificationLog {

    public static final Integer FINAL_ABBREVIATED_IS_REPLACED_BY_TO_BE = 1;
    public static final Integer FINAL_VE_REPLACED_BY_HAVE = 2;
    public static final Integer FINAL_ABBREVIATED_ARE_REPLACED_BY_ARE = 3;

    public void addEvent(Event event) {
        eventModificationsList.add(event);
    }

    public boolean hasEvent(Integer eventCode) {
        return false;
    }

    @Override
    public Phrase processLastModification(Phrase phrase) {
        for(Event event :  eventModificationsList) {
            int position = event.getPosition();
            int eventCode = event.getEventCode();
            if(eventCode == EnglishModificationLog.FINAL_ABBREVIATED_IS_REPLACED_BY_TO_BE) {
                Word currentWord = phrase.getYetUnknownWordAtPosition(position);
                if(!currentWord.isNoun()) continue;
                Noun currentNoun = (Noun)currentWord;
                if(currentNoun.isNotAnAdjective()) {
                    Word mayBeInversed = phrase.getYetUnknownWordAtPosition(position+2);
                    if(mayBeInversed != null && mayBeInversed.isNoun() && ((Noun)mayBeInversed).isNotAnAdjective() && !((Noun)mayBeInversed).hasSpecificRule("agenitive")) {
                        String firstValue = currentNoun.getInitialValue();
                        String lastValue = mayBeInversed.getInitialValue();
                        String lastRoot = mayBeInversed.getRoot();
                        currentNoun.modifyContentByPatternReplacement(firstValue, lastValue);
                        currentNoun.setRoot(lastRoot);
                        Gender currentGender = currentNoun.getGender();
                        Gender mayBeInversedGender = mayBeInversed.getGender();
                        List<CaseNumberGenre> currentNounPossibleCaseNumbers = currentNoun.getPossibleCaseNumbers();
                        Noun mayBeInversedNoun = (Noun)mayBeInversed;
                        List<CaseNumberGenre> mayBeInversedPossibleCaseNumbers = mayBeInversedNoun.getPossibleCaseNumbers();
                        mayBeInversed.modifyContentByPatternReplacement(lastValue, firstValue);
                        mayBeInversed.setRoot(firstValue);
                        mayBeInversedNoun.setGender(currentGender);
                        currentNoun.setGender(mayBeInversedGender);
                        mayBeInversedNoun.setPossibleCaseNumbers(currentNounPossibleCaseNumbers);
                        currentNoun.setPossibleCaseNumbers(mayBeInversedPossibleCaseNumbers);
                        phrase.addWordContainerAtPosition(position+1, new WordContainer(new Preposition(Language.ENGLISH,"of",new NullCase()),position,Language.ENGLISH), phrase);
                    }
                }
            }
        }
        return phrase;
    }
}

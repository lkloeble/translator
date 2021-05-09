package patrologia.translator.rule.hebrew;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.basicelements.WordType;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class RuleWawConversiveForVerbTime extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWordVerb = phrase.getWordContainerAtPosition(position+1).getWordByType(WordType.VERB);
        if(!followingWordVerb.isVerb()) return;
        Verb followingVerb = (Verb)followingWordVerb;
        VerbRepository2 verbRepository = followingVerb.getVerbRepository();
        List<String> futureTimes = new ArrayList<>();
        futureTimes.add("AIF");
        futureTimes.add("PALFUT");
        futureTimes.add("CONVFUT");
        futureTimes.add("HIFFUT");
        futureTimes.add("BINHUFUT");
        futureTimes.add("BINPUFUT");
        List<String> pastTimes = new ArrayList<>();
        pastTimes.add("AIP");
        pastTimes.add("HIFPER");
        pastTimes.add("BINHUPER");
        pastTimes.add("BINPUPER");
        if(verbRepository.isOnlyInThisTime(followingVerb,futureTimes) != null) {
            followingVerb = verbRepository.affectTime(followingVerb, verbRepository.isOnlyInThisTime(followingVerb,futureTimes),  pastTimes);
        } else if(verbRepository.isOnlyInThisTime(followingVerb, pastTimes) != null) {
            followingVerb = verbRepository.affectTime(followingVerb, verbRepository.isOnlyInThisTime(followingVerb,pastTimes), futureTimes);
        }
        WordContainer followingWordContainer = phrase.getWordContainerAtPosition(position+1);
        followingWordContainer.deleteWordByWordType(WordType.VERB);
        followingWordContainer.putOtherPossibleWord(followingWordVerb);
    }

}

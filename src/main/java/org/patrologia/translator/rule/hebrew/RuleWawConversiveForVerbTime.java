package patrologia.translator.rule.hebrew;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.rule.Rule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 01/05/2017.
 */
public class RuleWawConversiveForVerbTime extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWordVerb = phrase.getWordContainerAtPosition(position+1).getWordByType(WordType.VERB);
        if(!followingWordVerb.isVerb()) return;
        Verb followingVerb = (Verb)followingWordVerb;
        VerbRepository verbRepository = followingVerb.getVerbRepository();
        List<String> futureTimes = new ArrayList<>();
        futureTimes.add("AIF");
        futureTimes.add("PALFUT");
        futureTimes.add("CONVFUT");
        futureTimes.add("HIFFUT");
        futureTimes.add("BINHUFUT");
        List<String> pastTimes = new ArrayList<>();
        pastTimes.add("AIP");
        pastTimes.add("HIFPER");
        pastTimes.add("BINHUPER");
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

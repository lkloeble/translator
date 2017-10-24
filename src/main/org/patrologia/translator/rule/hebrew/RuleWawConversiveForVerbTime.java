package org.patrologia.translator.rule.hebrew;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.rule.Rule;

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
        List<String> pastTimes = new ArrayList<>();
        pastTimes.add("AIP");
        if(verbRepository.isOnlyInThisTime(followingVerb,futureTimes) != null) {
            followingVerb = verbRepository.affectTime(followingVerb, verbRepository.isOnlyInThisTime(followingVerb,futureTimes),  "AIP");
        } else if(verbRepository.isOnlyInThisTime(followingVerb, pastTimes) != null) {
            followingVerb = verbRepository.affectTime(followingVerb, verbRepository.isOnlyInThisTime(followingVerb,pastTimes), "AIF");
        }
        WordContainer followingWordContainer = phrase.getWordContainerAtPosition(position+1);
        followingWordContainer.clearAll();
        followingWordContainer.putOtherPossibleWord(followingWordVerb);
    }
}

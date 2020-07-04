package patrologia.translator.rule.english;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.conjugation.ConjugationPosition;
import patrologia.translator.rule.Rule;

public class RuleElectImperativeConjugation extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word followingWord = phrase.getYetUnknownWordAtPosition(position+1);
        if(followingWord == null || !followingWord.isVerb()) {
            return;
        }
        Verb followingVerb = (Verb)followingWord;
        followingVerb.setConjugation(Conjugation2.ACTIVE_IMPERATIVE_PRESENT);
        followingVerb.setPositionInTranslationTable(ConjugationPosition.SINGULAR_THIRD_PERSON);//position pour le let's buy => achetons
        followingVerb.addForbiddenConjugation(Conjugation2.ACTIVE_INDICATIVE_PRESENT);
    }

}

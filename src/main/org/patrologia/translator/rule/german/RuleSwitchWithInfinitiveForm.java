package org.patrologia.translator.rule.german;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.verb.Verb;
import org.patrologia.translator.rule.Rule;

/**
 * Created by lkloeble on 08/09/2016.
 */
public class RuleSwitchWithInfinitiveForm extends Rule {

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word precedingWord = phrase.getYetUnknownWordAtPosition(position-1);
        if(precedingWord != null && precedingWord.isVerb()) {
            String root = precedingWord.getRoot();
            String difference = precedingWord.getInitialValue().replaceAll(precedingWord.getRoot(),"");
            precedingWord.modifyContentByPatternReplacement(precedingWord.getInitialValue(), "lieb" + difference);
            precedingWord.modifyContentByPatternReplacement(precedingWord.getRoot(), "lieb" + difference);
            Verb precedingVerb = (Verb)precedingWord;
            precedingVerb.updateRoot("lieb");
            Verb verb = new Verb(language, root + "en", ((Verb)precedingWord).getPositionInTranslationTable());
            verb.updateRoot(root);
            phrase.addWordContainerAtPosition(position, new WordContainer(verb,position,language), phrase);
        }
    }
}

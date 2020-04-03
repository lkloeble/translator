package patrologia.translator.rule.german;

import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordContainer;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.rule.Rule;

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


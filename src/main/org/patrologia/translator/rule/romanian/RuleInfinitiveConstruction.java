package org.patrologia.translator.rule.romanian;

import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.conjugation.RootedConjugation;
import org.patrologia.translator.rule.Rule;

import java.util.Map;
import java.util.Set;

/**
 * Created by lkloeble on 16/12/2016.
 */
public class RuleInfinitiveConstruction extends Rule {

    private VerbRepository verbRepository;

    public RuleInfinitiveConstruction(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    @Override
    public void apply(Word word, Phrase phrase, int position) {
        Word nextWord = phrase.getYetUnknownWordAtPosition(position+1);
        if(nextWord == null) return;
        String initialValue = nextWord.getInitialValue();
        String possibleInfinitiveForm = "a " + initialValue;
        Verb verb = verbRepository.getVerb(possibleInfinitiveForm);
        String root = verb.getRoot();
        if(root == null || root.length() == 0) return;
        TranslationInformationBean translationInformationBean = verbRepository.getAllFormsForTheVerbRoot(root);
        Set<Map.Entry<String, RootedConjugation>> wordEntrySet = translationInformationBean.getNameForms().entrySet();
        for(Map.Entry<String , RootedConjugation> entry : wordEntrySet) {
            if(entry.getKey().contains("INFINITIVE") && entry.getValue().allForms().contains(possibleInfinitiveForm)) {
                nextWord.updateInitialValue("xxtoremovexx");
                word = new Verb(possibleInfinitiveForm, root, Language.ROMANIAN);
                phrase.addWordContainerAtPosition(position, new WordContainer(word,position,Language.ROMANIAN), phrase);
            }
        }
    }
}

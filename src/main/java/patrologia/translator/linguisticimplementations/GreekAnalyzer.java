package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.modificationlog.DefaultModificationLog;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.CaseOperatorContainer;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.rule.greek.RuleElectNounElectedForPrecedingAdjective;
import patrologia.translator.rule.greek.RulePlurialNeutralWithSingularVerbMustChangeTimeVerbToPlural;
import patrologia.translator.utils.Analyzer;
import patrologia.translator.utils.PhraseAnalizer;
import patrologia.translator.utils.WordAnalyzer;
import patrologia.translator.utils.WordSplitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GreekAnalyzer implements Analyzer {

    private WordSplitter wordSplitter = new WordSplitter();
    private WordAnalyzer wordAnalyzer = null;
    private PhraseAnalizer phraseAnalizer = new PhraseAnalizer();
    private GreekConjugationAnalyzer greekConjugationAnalyzer = new GreekConjugationAnalyzer();

    public GreekAnalyzer(PrepositionRepository prepositionRepository, NounRepository nounRepository, VerbRepository2 verbRepository) {
        wordAnalyzer = new WordAnalyzer(prepositionRepository, nounRepository,verbRepository, new GreekPhraseChanger(nounRepository), new DefaultModificationLog(), new CustomRule(), new CaseOperatorContainer(nounRepository,prepositionRepository),Language.GREEK);
    }

    public Analysis analyze(String sentence) {
        String unaccentuetedCharacters = skipAccentuetedCharacters(sentence);
        Phrase phrase = wordSplitter.splitSentence(unaccentuetedCharacters, Language.GREEK, new DefaultWordSplitterPattern());
        phrase = eraseSubstantiveSuffix(phrase);
        phrase = wordAnalyzer.affectAllPossibleInformations(phrase);
        phrase =  affectGreekGenders(phrase);
        phrase = affectRuleForNeutralWords(phrase);
        phrase = linkAdjectiveAndNouns(phrase);
        phrase = substituteAbbreviations(phrase);
        return phraseAnalizer.affectAllPossibleInformationsBetweenWords(Language.GREEK, phrase);
    }

    private String skipAccentuetedCharacters(String sentence) {
        if(sentence == null) return "";
        StringBuilder sb = new StringBuilder();
        char[] chars = sentence.toLowerCase().toCharArray();
        int[] charsz = new int[]{7936,7937,7938,7939,7940,7941,7942,7952,7953,7955,7956,7957,7968,7969,7970,7971,7972,7973,7974,7975,
                7984,7985,7987,7988,7989,7990,8000,8001,8003,8004,8005,8016,8017,8019,8020,8021,8023,8033,8036,8037,8039,8048,8049,
                8050,8051,8052,8053,8054,8055,8056,8057,8058,8059,8060,8061,8069,8103,8115,8118,8125,8127,8131,8134,8135,8147,8150,8165,
                8166,8179,8180,8182,8183,8230};
        for (char c : chars) {
            int i = (int) c;
            if(i>6000 && notInTab(i,charsz)) {
                System.out.println("accentuation grecque manquante");
            }
            switch (i) {
                case 39://'
                    break;
                case 91://[
                    sb.append(" |[| ");
                    break;
                case 93://]
                    sb.append(" |]| ");
                    break;
                case 183://.
                    sb.append(".");
                    break;
                case 903://'·' greek point
                    sb.append(".");
                    break;
                case 912://'ΐ'  912
                    sb.append("ι");
                    break;
                case 940://'ά' 940
                    sb.append("α");
                    break;
                case 941://'ἐ' 941
                    sb.append("ε");
                    break;
                case 942://'ἡ' 942
                    sb.append("η");
                    break;
                case 970://''ϊ' 970
                    sb.append("ι");
                    break;
                case 971://'ϋ' 971
                    sb.append("υ");
                    break;
                case 972://'ό' 972
                    sb.append("ο");
                    break;
                case 973://'ὐ' 8016
                    sb.append("υ");
                    break;
                case 974://'ώ' 974
                    sb.append("ω");
                    break;
                case 943://'i' strange other i
                    sb.append("ι");
                    break;
                case 7936://'ἀ' accentueted a
                    sb.append("α");
                    break;
                case 7937://'ἁ' accentued a
                    sb.append("α");
                    break;
                case 7938://'ἂ' 7938
                    sb.append("α");
                    break;
                case 7939://'ἃ' 7939
                    sb.append("α");
                    break;
                case 7940://'ἀ' accentueted a
                    sb.append("α");
                    break;
                case 7941://'ἅ' 7941
                    sb.append("α");
                    break;
                case 7942://'ἆ' 7942
                    sb.append("α");
                    break;
                case 7952://'ἐ' 7952
                    sb.append("ε");
                    break;
                case 7953://'ἑ' 7953
                    sb.append("ε");
                    break;
                case 7955://'ἓ' 7955
                    sb.append("ε");
                    break;
                case 7956://'ἔ' 7956
                    sb.append("ε");
                    break;
                case 7957://'ἕ' 7957
                    sb.append("ε");
                    break;
                case 7968://''ἠ' 7968
                    sb.append("η");
                    break;
                case 7969://'ἡ' 7969
                    sb.append("η");
                    break;
                case 7970://'ἢ' 7970
                    sb.append("η");
                    break;
                case 7971://'ἣ' 7971
                    sb.append("η");
                    break;
                case 7972://'ἤ' 7972
                    sb.append("η");
                    break;
                case 7973://'ἥ' 7973
                    sb.append("η");
                    break;
                case 7974://'ἦ' 7974
                    sb.append("η");
                    break;
                case 7975://'ἧ' 7975
                    sb.append("η");
                    break;
                case 7984://'ἴ' 7984
                    sb.append("ι");
                    break;
                case 7985://'ἱ' 7985
                    sb.append("ι");
                    break;
                case 7987://'ἳ'
                    sb.append("ι");
                    break;
                case 7988://'ἴ' 7988
                    sb.append("ι");
                    break;
                case 7989://'ἵ' 7989
                    sb.append("ι");
                    break;
                case 7990://'ἶ' 7990
                    sb.append("ι");
                    break;
                case 8000://'ὀ' 8000
                    sb.append("ο");
                    break;
                case 8001://'ὁ' 8001
                    sb.append("ο");
                    break;
                case 8003://'ὃ' 8003
                    sb.append("ο");
                    break;
                case 8004://'ὄ' 8004
                    sb.append("ο");
                    break;
                case 8005://'ὅ' 8005
                    sb.append("ο");
                    break;
                case 8016://'ὐ' 8016
                    sb.append("υ");
                    break;
                case 8017://'ὑ' 8017
                    sb.append("υ");
                    break;
                case 8019://'ὓ' 8019
                    sb.append("υ");
                    break;
                case 8020://'ὔ' 8020
                    sb.append("υ");
                    break;
                case 8021://'ὕ' 8021
                    sb.append("υ");
                    break;
                case 8023://'ὗ' 8023
                    sb.append("υ");
                    break;
                case 8033://'ὡ' 8033
                    sb.append("ω");
                    break;
                case 8036://'ὡ' 8036
                    sb.append("ω");
                    break;
                case 8037://'ὥ' 8037
                    sb.append("ω");
                    break;
                case 8039://'ὧ' 8039
                    sb.append("ω");
                    break;
                case 8048://'ὰ' 8048
                    sb.append("α");
                    break;
                case 8049://'ά' 8049
                    sb.append("α");
                    break;
                case 8050://'ὲ' 8050
                    sb.append("ε");
                    break;
                case 8051://'έ' 8051
                    sb.append("ε");
                    break;
                case 8052://'ὴ' 8052
                    sb.append("η");
                    break;
                case 8053://'ή' 8053
                    sb.append("η");
                    break;
                case 8054://'ὶ' 8054
                    sb.append("ι");
                    break;
                case 8055://'ί' accentueted i
                    sb.append("ι");
                    break;
                case 8056://'ὸ' 8056
                    sb.append("ο");
                    break;
                case 8057://'ό' 8057
                    sb.append("ο");
                    break;
                case 8058://'ὺ' 8058
                    sb.append("υ");
                    break;
                case 8059://'ύ' 8059
                    sb.append("υ");
                    break;
                case 8060://'ὼ' 8060
                    sb.append("ω");
                    break;
                case 8061://'ώ' 8061
                    sb.append("ω");
                    break;
                case 8069://'ᾅ' 8069
                    sb.append("α");
                    break;
                case 8103://'ᾧ' 8103
                    sb.append("ω");
                    break;
                case 8115://'ᾳ' 8115
                    sb.append("α");
                    break;
                case 8118://'ᾶ' 8118
                    sb.append("α");
                    break;
                case 8125://'᾽' 8125
                    sb.append("");//on efface l'apostrophe
                    break;
                case 8127://' 81éè
                    sb.append("");
                    break;
                case 8131://'ῃ' 8131
                    sb.append("η");
                    break;
                case 8134://'ῆ' 8134
                    sb.append("η");
                    break;
                case 8135://'ῇ' 8135
                    sb.append("η");
                    break;
                case 8147://'ΐ' 8147
                    sb.append("ι");
                    break;
                case 8150://'ῖ' 8150
                    sb.append("ι");
                    break;
                case 8165://'ῥ' 8165
                    sb.append("ρ");
                    break;
                case 8166://'ῦ' 8166
                    sb.append("υ");
                    break;
                case 8179://'ῳ' 8179
                    sb.append("ω");
                    break;
                case 8180://'ῴ' 8180
                    sb.append("ω");
                    break;
                case 8182://'ῶ' 8182
                    sb.append("ω");
                    break;
                case 8183://'ῷ' 8183
                    sb.append("ω");
                    break;
                case 8230://'...' 8230
                    sb.append("...");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString().trim();
    }

    private boolean notInTab(int i, int[] charsz) {
        for(int c : charsz) {
            if(c == i) return false;
        }
        return true;
    }

    private Phrase eraseSubstantiveSuffix(Phrase phrase) {
        Map<String,String> toSubstitute = new HashMap<>();
        toSubstitute.put("ωτεροις","ος");
        Set<Integer> indices = phrase.keySet();
        for(Integer indice : indices) {
            Word currentWord = phrase.getYetUnknownWordAtPosition(indice);
            Set<Map.Entry<String, String>> entries = toSubstitute.entrySet();
            for(Map.Entry<String, String> entry : entries) {
                if (currentWord.getInitialValue().endsWith(entry.getKey())) {
                    String oldValue = currentWord.getInitialValue();
                    String newValue = oldValue.replace(entry.getKey(),entry.getValue());
                    currentWord.updateInitialValue(newValue);
                    currentWord.updateRoot(newValue);
                }
            }
        }
        return phrase;
    }


    private Phrase substituteAbbreviations(Phrase phrase) {
        Map<String,String> toSubstitute = new HashMap<>();
        toSubstitute.put("δ","δε");
        Set<Integer> indices = phrase.keySet();
        for(Integer indice : indices) {
            Word currentWord = phrase.getYetUnknownWordAtPosition(indice);
            if(currentWord.isTypeUnknow() && toSubstitute.containsKey(currentWord.getInitialValue())) {
                String oldValue = currentWord.getInitialValue();
                currentWord.updateInitialValue(toSubstitute.get(oldValue));
                currentWord.updateRoot(toSubstitute.get(oldValue));
            }
        }
        return phrase;
    }

    private Phrase linkAdjectiveAndNouns(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        for(Integer indice : indices) {
            Word currentWord = phrase.getYetUnknownWordAtPosition(indice);
            Word precedingWord = phrase.getYetUnknownWordAtPosition(indice-1);
            if(precedingWord == null) continue;
            if(currentWord.isNoun() && precedingWord.isNoun() && ((Noun)precedingWord).getGender().equals(new Gender(Gender.ADJECTIVE))) {
                String currentValue = currentWord.getInitialValue();
                int currentLength = currentValue.length();
                String precedingValue = precedingWord.getInitialValue();
                int precedingLength = precedingValue.length();
                if(currentValue.charAt(currentLength-1) == precedingValue.charAt(precedingLength-1)) {
                    precedingWord.addRule(new RuleElectNounElectedForPrecedingAdjective());
                }
            }
        }
        return phrase;
    }

    private Phrase affectRuleForNeutralWords(Phrase phrase) {
        for(Integer indice : phrase.keySet()) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            Word word = wordContainer.getUniqueWord();
            if(!word.isNoun()) {
                continue;
            }
            Noun noun = (Noun)word;
            if(new Gender(Gender.NEUTRAL).name().equals(noun.getGender().name())) {
                noun.addRule(new RulePlurialNeutralWithSingularVerbMustChangeTimeVerbToPlural());
            }
        }
        return phrase;
    }

    private Phrase affectGreekGenders(Phrase phrase) {
        for(Integer indice : phrase.keySet()) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            Word word = wordContainer.getUniqueWord();
            if(!word.isNoun()) {
                continue;
            }
            Noun noun = (Noun)word;
            if(noun.getGender().equals(new Gender(Gender.ADJECTIVE))) continue;
            noun.setGender(greekConjugationAnalyzer.getGenderByConjugationCode(noun.getGenderFamily()));
        }
        return phrase;
    }
}


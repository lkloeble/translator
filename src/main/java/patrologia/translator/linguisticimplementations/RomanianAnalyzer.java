package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.modificationlog.DefaultModificationLog;
import patrologia.translator.basicelements.noun.Noun;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.CaseOperatorContainer;
import patrologia.translator.casenumbergenre.Gender;
import patrologia.translator.rule.romanian.RomanianPhraseChanger;
import patrologia.translator.utils.Analyzer;
import patrologia.translator.utils.PhraseAnalizer;
import patrologia.translator.utils.WordAnalyzer;
import patrologia.translator.utils.WordSplitter;

import java.util.List;
import java.util.Set;

/**
 * Created by Laurent KLOEBLE on 16/10/2015.
 */
public class RomanianAnalyzer implements Analyzer {

    private WordSplitter wordSplitter = new WordSplitter();
    private WordAnalyzer wordAnalyzer = null;
    private PhraseAnalizer phraseAnalizer = new PhraseAnalizer();
    private NounRepository nounRepository;
    private VerbRepository2 verbRepository;

    public RomanianAnalyzer(PrepositionRepository prepositionRepository, NounRepository nounRepository, VerbRepository2 verbRepository) {
        wordAnalyzer = new WordAnalyzer(prepositionRepository, nounRepository,verbRepository, new RomanianPhraseChanger(nounRepository,prepositionRepository,verbRepository), new DefaultModificationLog(), new CustomRule(), new CaseOperatorContainer(nounRepository,prepositionRepository),Language.ROMANIAN);
        this.nounRepository = nounRepository;
        this.verbRepository = verbRepository;
    }

    public patrologia.translator.basicelements.Analysis analyze(String sentence) {
        String characterAlphaOnly = replaceRomanianSpecialsWithAlpha(sentence);
        String romanianFirstCleaning = replaceExpressions(characterAlphaOnly);
        Phrase phrase = wordSplitter.splitSentence(romanianFirstCleaning, Language.ROMANIAN, new RomanianWordSplitterPattern());
        Phrase phraseWithUAModified = replaceFinalAForUAFemininesNouns(phrase);
        Phrase phraseWithPastParticipeFound = identifyFemininePastParticipe(phraseWithUAModified);
        phrase = wordAnalyzer.affectAllPossibleInformationsWithAnotherRule(phraseWithPastParticipeFound, new CustomRule());
        phrase = affectPluralForSintVerbAndPluralWords(phrase);
        phrase = wordAnalyzer.affectAllPossibleInformationsWithAnotherRule(phrase, new CustomRule(true));
        Analysis analysis = phraseAnalizer.affectAllPossibleInformationsBetweenWords(Language.ROMANIAN, phrase);

        return phraseAnalizer.affectAllPossibleInformationsBetweenWords(Language.ROMANIAN, analysis.getPhrase());
    }

    private String replaceExpressions(String characterAlphaOnly) {
        String replace = characterAlphaOnly.toLowerCase().replace("s.a.m.d", "samd");
        replace = eraseMinus(replace);
        replace = replace.replace("in fond", "infondexpr");
        replace = replace.replace("f[a]r[a] seam[a]n", "faraseaman");
        replace = replace.replace("prin urmare", "prinurmare");
        replace = replace.replace("s[a]_si","sasiexpr");
        replace = replace.replace("s[a]_i","sasiexpr");
        replace = replace.replace("ndu-l","ndu lexpr");
        replace = replace.replace("s[a]_si","sasiexpr");
        replace = replace.replace("v_atsi","avetsi");
        replace = replace.replace(" atsi"," avetsi");
        replace = replace.replace("intr_adev[a]r","intradevar");
        replace = replace.replace("v[a] rog","varog");
        replace = replace.replace("c_a","cminusaexpress");
        replace = replace.replace(" _ "," underscoreexpr ");
        return replace.replace("ce tsi","cetsiexpr");
    }

    private String eraseMinus(String replace) {
        char[] withoutMinus = replace.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(char c : withoutMinus) {
            if((int)c == 173) {
                stringBuilder.append(" ");
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private Phrase identifyFemininePastParticipe(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        for(Integer indice : indices) {
            patrologia.translator.basicelements.Word unknowYet = phrase.getYetUnknownWordAtPosition(indice);
            String initialValue = unknowYet.getInitialValue();
            if(!initialValue.endsWith("a")) continue;
            String possibleMasculinePastParticiple = initialValue.substring(0,initialValue.length()-1);
            if(!verbRepository.hasVerb(possibleMasculinePastParticiple)) continue;
            Verb verb = verbRepository.getVerb(possibleMasculinePastParticiple);
            TranslationInformationBean allFormsForTheVerbRoot = verbRepository.getAllFormsForTheVerbRoot(verb.getRoot());
            List<String> constructionNameForInitialValue = allFormsForTheVerbRoot.getConstructionNameForInitialValue(possibleMasculinePastParticiple, verbRepository.getInfinitiveBuilder());
            if(!constructionNameForInitialValue.contains("PAP")) continue;
            verb.updateInitialValue(possibleMasculinePastParticiple);
            verb.setGender(new Gender(Gender.FEMININE));
            phrase.emptyWordContainer(indice);
            phrase.addWordContainerAtPosition(indice, new WordContainer(verb, indice, Language.ROMANIAN), phrase);
        }
        return phrase;
    }

    private Phrase affectPluralForSintVerbAndPluralWords(Phrase phrase) {
        for(Integer indice : phrase.keySet()) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            Word word = wordContainer.getUniqueWord();
            if(!word.isVerb() || !word.getInitialValue().equals("sint")) {
                continue;
            }
            Word followingWord = phrase.getWordContainerAtPosition(indice+1).getUniqueWord();
            if(!followingWord.isNoun()) {
                continue;
            }
            Noun followingNoun = (Noun)followingWord;
            if(followingNoun.isPlural()) {
                Verb sint = (Verb)word;
                sint.setPluralKnown(true);
            }
        }
        return phrase;
    }


    private Phrase replaceFinalAForUAFemininesNouns(Phrase phrase) {
        Set<Integer> indices = phrase.keySet();
        for(Integer indice : indices) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            Word uniqueWord = wordContainer.getUniqueWord();
            if(uniqueWord.getInitialValue().endsWith("a") && nounRepository.hasNounForDeclensionFamily(uniqueWord.getInitialValue(), "cafea")) {
                List<Word> words = wordContainer.getWordSet();
                for(Word word : words) {
                    word.modifyContentByPatternReplacementAndPosition("[a]", word.getInitialValue().length() - 1);
                }
            }
        }
        return phrase;
    }

    private String replaceRomanianSpecialsWithAlpha(String sentence) {
        if(sentence == null) return "";
        StringBuilder sb = new StringBuilder();
        char[] chars = sentence.toCharArray();
        for (char c : chars) {
            int i = (int) c;
            switch (i) {
                case 206://Î
                    sb.append("i");
                    break;
                case 226://â
                    sb.append("a");
                    break;
                case 238://î
                    sb.append("i");
                    break;
                case 259://ă
                    sb.append("[a]");
                    break;
                case 351://'ş' 351
                    sb.append("s");
                    break;
                case 536://'Ș'
                    sb.append("s");
                    break;
                case 537://ș
                    sb.append("s");
                    break;
                case 354://ț
                    sb.append("ts");
                    break;
                case 355://ț
                    sb.append("ts");
                    break;
                case 539://ț
                    sb.append("ts");
                    break;
                case 45://- replaced by _ for hebrew compatibility
                    sb.append("_");
                    break;
                case 350://S majuscule
                    sb.append("s");
                    break;
                default:
                    sb.append(c);
                    break;
            }
        }
        return sb.toString().trim();
    }
}

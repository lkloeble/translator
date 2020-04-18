package patrologia.translator.utils;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.Word;
import patrologia.translator.basicelements.WordType;

import java.util.StringTokenizer;

public class WordSplitter {

    public Phrase splitSentence(String sentence, Language language, WordSplitterPattern wordSplitterPattern) {
        if(sentence == null) {
            return null;
        }
        String nonTranslationPhrase = withNonTranslationMarkPutAroundEveryWords(putSpaceToHandlePonctuation(sentence));
        StringTokenizer stringTokenizer = new StringTokenizer(nonTranslationPhrase.toLowerCase(),wordSplitterPattern.getPattern());
        int indice = Word.STARTING_COUNTINDICE_IN_SENTENCE;
        Phrase result = new Phrase(stringTokenizer.countTokens(), language);
        while(stringTokenizer.hasMoreTokens()) {
            String wordToCorrect = stringTokenizer.nextToken();
            Word word = new Word(WordType.UNKNOWN, correctOnlyNumberWithNoTranslationMark(wordToCorrect), language);
            result.addWordAtPosition(indice++, word);
        }
        return result;
    }

    private String withNonTranslationMarkPutAroundEveryWords(String sentence) {
        if(!sentence.contains("|")) return sentence;
        int beginningIndex = sentence.indexOf("|");
        int endIndex = sentence.lastIndexOf("|");
        String partToNotTranslate = sentence.substring(beginningIndex,endIndex+1);
        if(!partToNotTranslate.contains(" ")) return sentence;
        if(countNumberOfChar(partToNotTranslate,"|") > 2) return sentence;
        partToNotTranslate = partToNotTranslate.replace(" ", "| |");
        String finalString = sentence.substring(0,beginningIndex) + partToNotTranslate + sentence.substring(endIndex,sentence.length());
        String replace = finalString.replace("||", "|");
        return replace;
    }

    private int countNumberOfChar(String partToNotTranslate, String s) {
        char[] chars = partToNotTranslate.toCharArray();
        int total = 0;
        for(char c : chars) {
            if(new Character(c).toString().equals(s)) total++;
        }
        return total;
    }

    private String putSpaceToHandlePonctuation(String sentence) {
        StringBuilder sb = new StringBuilder();
        char[] letters = sentence.toCharArray();
        for(char c : letters) {
            if(c == '.' || c == ',' || c == ';' || c == ')') {
                sb.append(" ").append(c).append(" ");
            } else {
                sb.append(c);
            }
            if(c == '(') {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    private String correctOnlyNumberWithNoTranslationMark(String wordToCorrect) {
        try {
            Integer integer = Integer.parseInt(wordToCorrect);
        } catch (NumberFormatException nfe) {
            return wordToCorrect;
        }
        return "|" + wordToCorrect + "|";
    }

}

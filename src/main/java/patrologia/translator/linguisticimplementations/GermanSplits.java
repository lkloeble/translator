package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.WordContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GermanSplits {

    private Map<Integer, GermanSplit> splitMap  =  new HashMap<>();

    public void addSplit(GermanSplit germanSplit, int position) {
        splitMap.put(position,germanSplit);
    }

    public boolean hasSplits() {
        return splitMap.size() > 0;
    }

    public Phrase expandPhrase(Phrase phrase) {
        Phrase newPhrase = new Phrase(phrase.size()+getTotalSplitLength(), Language.GERMAN);
        Set<Integer> indices = phrase.keySet();
        int newIndice = 1;
        for(Integer indice : indices) {
            if(splitMap.containsKey(indice)) {
                GermanSplit split = splitMap.get(indice);
                for(int newWord = 0;newWord < split.length();newWord++) {
                    newPhrase.addWordContainerAtPosition(newIndice, new WordContainer(split.getAtPosition(newWord),newIndice,Language.GERMAN),newPhrase);
                    newIndice++;
                }
            }  else {
                newPhrase.addWordContainerAtPosition(newIndice, phrase.getWordContainerAtPosition(indice),newPhrase);
                newIndice++;
            }
        }
        return newPhrase;
    }

    private int getTotalSplitLength() {
        int total = 0;
        for(GermanSplit germanSplit : splitMap.values()) {
            total += germanSplit.length()-1;
        }
        return total;
    }

    public void reset() {
        splitMap.clear();
    }

}

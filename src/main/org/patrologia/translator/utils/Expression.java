package org.patrologia.translator.utils;

import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.basicelements.WordContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by lkloeble on 21/12/2016.
 */
public class Expression implements Comparable {

    private List<String> members = new ArrayList<>();
    private int expressionStartInPhrase = 0;

    public Expression(String ... expressionMembers) {
        for(String expressionMember : expressionMembers) {
            members.add(expressionMember);
        }
    }

    private int getSize() {
        return members.size();
    }

    public String getBeginning() {
        return ((String)members.toArray()[0]).substring(0,1);
    }

    public String getFirstWordOfExpression() {
        return getWordOfExpressionByPosition(0);
    }

    public String getWordOfExpressionByPosition(int position) {
        if(position > getSize()) return "ZZZ";
        return (String)members.toArray()[position];
    }

    public boolean isCompatible(Phrase phrase) {
        Set<Integer> integers = phrase.keySet();
        phraseiterator : for(Integer indice : integers) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice);
            String initialValue = wordContainer.getInitialValue();
            if(initialValue.equals(getFirstWordOfExpression())) {
                for(int otherPartOfExpression = 1;otherPartOfExpression<getSize();otherPartOfExpression++) {
                    WordContainer next = phrase.getWordContainerAtPosition(indice+otherPartOfExpression);
                    String nextValue = next.getInitialValue();
                    if(!nextValue.equals(getWordOfExpressionByPosition(otherPartOfExpression))) {
                        continue phraseiterator;
                    }
                }
                expressionStartInPhrase = indice;
                return true;
            }
        }
        return false;
    }

    public Phrase aggregateWords(Phrase phrase) {
        boolean start = true;
        for(int indice=0;indice<getSize();indice++) {
            WordContainer wordContainer = phrase.getWordContainerAtPosition(indice+expressionStartInPhrase);
            if(start) {
                wordContainer.updateInitialValue(getAgregateExpression());
            } else {
                wordContainer.updateInitialValue("xxtoremovexx");
            }
            start = false;
        }
        return phrase;
    }

    private String getAgregateExpression() {
        StringBuilder sb = new StringBuilder();
        for(String s : members) {
            sb.append(s);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        Expression other = (Expression)o;
        if(getSize() > other.getSize()) return -1;
        if(getSize() < other.getSize()) return 1;
        return getBeginning().compareTo(other.getBeginning());
    }

}

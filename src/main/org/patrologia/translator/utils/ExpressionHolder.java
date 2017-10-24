package org.patrologia.translator.utils;

import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.basicelements.Phrase;
import org.patrologia.translator.linguisticimplementations.EnglishExpressionListBuilder;
import org.patrologia.translator.linguisticimplementations.GermanExpressionListBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lkloeble on 21/12/2016.
 */
public class ExpressionHolder {

    private List<Expression> expressionList = new ArrayList<>();

    public ExpressionHolder(Language language) {
        if(language.equals(Language.ENGLISH)) {
            expressionList = new EnglishExpressionListBuilder().getExpressionList();
        } else if(language.equals(Language.GERMAN)) {
            expressionList = new GermanExpressionListBuilder().getExpressionList();
        }
    }

    public Phrase aggregateExpressions(Phrase phrase) {
        List<Expression> compatibleExpressions = new ArrayList<>();
        for(Expression expression : expressionList) {
            if(expression.isCompatible(phrase)) compatibleExpressions.add(expression);
        }
        Collections.sort(compatibleExpressions);
        if(compatibleExpressions.size() == 0) return  phrase;
        Expression electedExpression = compatibleExpressions.get(0);
        return electedExpression.aggregateWords(phrase);
    }
}

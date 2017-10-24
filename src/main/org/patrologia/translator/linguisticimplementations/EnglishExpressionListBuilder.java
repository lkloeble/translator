package org.patrologia.translator.linguisticimplementations;

import org.patrologia.translator.utils.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lkloeble on 28/12/2016.
 */
public class EnglishExpressionListBuilder {

    private List<Expression> expressionList = new ArrayList<>();

    public EnglishExpressionListBuilder() {
        expressionList.add(new Expression("of","course"));
        expressionList.add(new Expression("of","course","not"));
        expressionList.add(new Expression("washing","up"));
        expressionList.add(new Expression("welcome","to"));
        expressionList.add(new Expression("not","too"));
        expressionList.add(new Expression("middle","ages"));
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }
}

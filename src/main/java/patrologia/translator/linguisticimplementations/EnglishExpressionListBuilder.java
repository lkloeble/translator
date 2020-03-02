package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.Expression;

import java.util.ArrayList;
import java.util.List;

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

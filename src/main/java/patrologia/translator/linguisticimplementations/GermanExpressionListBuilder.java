package patrologia.translator.linguisticimplementations;

import patrologia.translator.utils.Expression;

import java.util.ArrayList;
import java.util.List;

public class GermanExpressionListBuilder {

    private List<Expression> expressionList = new ArrayList<>();

    public GermanExpressionListBuilder() {
        expressionList.add(new Expression("ein","paar"));
        expressionList.add(new Expression("vielen","dank"));
        expressionList.add(new Expression("auf","wiederhoren"));
        expressionList.add(new Expression("auf","wiedersehen"));
        expressionList.add(new Expression("gar","nicht"));
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

}

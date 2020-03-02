package patrologia.translator.rule;

public abstract class RuleFactory {

    public abstract Rule getRuleByName(String ruleName, String preposition);

    protected String extractParameter(String ruleName) {
        if(ruleName.indexOf("(") <= 0) {
            return null;
        }
        int leftParenthesisIndice = ruleName.indexOf("(");
        int rightParenthesisIndice = ruleName.indexOf(")");
        return ruleName.substring(leftParenthesisIndice + 1, rightParenthesisIndice);
    }

    protected String extractRuleNameWhenHasParameter(String ruleName) {
        return ruleName.indexOf("(") > 0 ? ruleName.substring(0, ruleName.indexOf("(")) : ruleName;
    }

}

package patrologia.translator.linguisticimplementations;

public class CustomRule {

    private boolean specialToDo;

    public CustomRule(boolean specialToDo) {
        this.specialToDo = specialToDo;
    }

    public CustomRule()  {
        this.specialToDo = false;
    }

    public boolean isSpecialToDo() {
        return specialToDo;
    }

}

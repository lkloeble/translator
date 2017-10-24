package org.patrologia.translator.linguisticimplementations;

/**
 * Created by lkloeble on 23/04/2017.
 */
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

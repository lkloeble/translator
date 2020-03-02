package patrologia.translator.basicelements;

public enum WordType {

    PREPOSITION, NOUN, VERB, NO_TRANSLATION, UNKNOWN;

    public String getDefinitionString() {
        return toString().substring(0,4);

    }

}

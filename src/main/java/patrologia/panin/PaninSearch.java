package patrologia.panin;

public enum PaninSearch {

    WORDS("words"),FORMS("forms"),FORM_VALUES("values of forms"),
    VOCABULARY("vocabulary"),LETTERS("letters"),FORMS_ONLY_ONCE("forms found once"),
    FORMS_TWICE_MIN("forms found twice or more"), VOCABULARY_LETTERS("vocabulary letters"),
    VOCABULARY_LETTERS_VOWELS("vocabulary letters vowels"), VOCABULARY_LETTERS_CONSONANTS("vocabulary letters consonants");

    private String viewLabel;

    PaninSearch(final String viewLabel) {
        this.viewLabel=viewLabel;
    }

    public String getViewLabel() {
        return viewLabel;
    }
}

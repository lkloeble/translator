package panin;

import org.junit.Test;
import patrologia.panin.PaninChecker;
import patrologia.panin.PaninSearch;
import patrologia.panin.PaninVocabularyStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaninCheckerTest {

    private PaninChecker paninChecker;
    private PaninVocabularyStore defaultStore;

    @Test
    public void constructor_should_erase_numbers() {
        paninChecker = new PaninChecker("1234",defaultStore);
        assertEquals(0,paninChecker.letterCount());
    }

    @Test
    public void word_counter_should_return_correct_number() {
        paninChecker = new PaninChecker("one two three",defaultStore);
        assertEquals(3, paninChecker.wordCount());
        paninChecker = new PaninChecker("one",defaultStore);
        assertEquals(1, paninChecker.wordCount());
        paninChecker = new PaninChecker("",defaultStore);
        assertEquals(0, paninChecker.wordCount());
        paninChecker = new PaninChecker("one two three four",defaultStore);
        assertEquals(4, paninChecker.wordCount());
    }

    @Test
    public void letter_counter_should_return_correct_number() {
        paninChecker = new PaninChecker("abcd",defaultStore);
        assertEquals(4, paninChecker.letterCount());
        paninChecker = new PaninChecker("abcd efgh",defaultStore);
        assertEquals(8, paninChecker.letterCount());
        paninChecker = new PaninChecker("",defaultStore);
        assertEquals(0, paninChecker.letterCount());
    }

    @Test
    public void vowel_count_should_return_correct_number() {
        paninChecker = new PaninChecker("Αναστας",defaultStore);
        assertEquals(3,paninChecker.vowelCount());
        paninChecker = new PaninChecker("Αναστας δε πρωι",defaultStore);
        assertEquals(6,paninChecker.vowelCount());
    }

    @Test
    public void default_text_is_well_cleaned() {
        paninChecker = new PaninChecker();
        String withoutConsons = paninChecker.eraseLetters(paninChecker.greekConsons);
        paninChecker = new PaninChecker(withoutConsons,defaultStore);
        String withoutVowels = paninChecker.eraseLetters(paninChecker.greekVowels);
        paninChecker = new PaninChecker(withoutVowels,defaultStore);
        assertTrue(paninChecker.getText().trim().length() == 0);
    }

    @Test
    public void heptadic_formatting_text() {
        paninChecker = new PaninChecker("ααα ααα ααα ααα ααα ααα ααα",defaultStore);
        assertEquals("letters 7*3",paninChecker.getHeptadicFormat(PaninSearch.LETTERS));
        assertEquals("words 7*1",paninChecker.getHeptadicFormat(PaninSearch.WORDS));
    }

    @Test
    public void forms_counter_should_return_correct_number() {
        paninChecker = new PaninChecker("forma formb forma formc formb",defaultStore);
        assertEquals(3, paninChecker.formsCount());
    }

    @Test
    public void form_values_counter_should_return_correct_number() {
        paninChecker = new PaninChecker("αααααα αααααα ββ ββ ββ γ",defaultStore);
        assertEquals(13, paninChecker.formValuesCount());
    }

    @Test
    public void vocabulary_counter_should_return_correct_number() {

        PaninVocabularyStore customStore = new PaninVocabularyStore();
        customStore.addVocabulary("aaaab","aaa");
        customStore.addVocabulary("aaaac","aaa");
        customStore.addVocabulary("aaaad","aaa");
        customStore.addVocabulary("bbbbc","bbb");
        customStore.addVocabulary("bbbba","bbb");
        customStore.addVocabulary("cccca","ccc");
        customStore.addVocabulary("ccccb","ccc");

        paninChecker = new PaninChecker("aaaab aaaac aaaad bbbbc bbbba",customStore);
        assertEquals(2, paninChecker.vocabularyCount());
    }

    @Test
    public void test_panin_assertions() {
        paninChecker = new PaninChecker();
        assertEquals("words 7*25", paninChecker.getHeptadicFormat(PaninSearch.WORDS));
        assertEquals("vocabulary 7*14", paninChecker.getHeptadicFormat(PaninSearch.VOCABULARY));
        assertEquals("forms 7*19", paninChecker.getHeptadicFormat(PaninSearch.FORMS));
        assertEquals("values of forms 7*12812", paninChecker.getHeptadicFormat(PaninSearch.FORM_VALUES));
        assertEquals("forms found once 7*16", paninChecker.getHeptadicFormat(PaninSearch.FORMS_ONLY_ONCE));
        assertEquals("forms found twice or more 7*3", paninChecker.getHeptadicFormat(PaninSearch.FORMS_TWICE_MIN));
    }
}

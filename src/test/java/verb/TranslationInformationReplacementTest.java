package verb;

import org.junit.Test;
import patrologia.translator.basicelements.verb.TranslationInformationReplacement2;
import patrologia.translator.conjugation.ConjugationPosition;

import static org.junit.Assert.assertEquals;

public class TranslationInformationReplacementTest {

    private TranslationInformationReplacement2 translationInformationReplacement;

    @Test
    public void replacement_should_not_alter_non_modified_times() {
        translationInformationReplacement = new TranslationInformationReplacement2("PAP*foobar*foobarbar");
        assertEquals("foobar", translationInformationReplacement.replace("IPR","foobar", ConjugationPosition.SINGULAR_FIRST_PERSON));
        assertEquals("foobarbar", translationInformationReplacement.replace("PAP","foobar", ConjugationPosition.SINGULAR_FIRST_PERSON));
    }

    @Test
    public void replacement_should_handle_multiple_time_alteration() {
        translationInformationReplacement = new TranslationInformationReplacement2("PAP*foobar*foobarbar@IPR*foo*onoff");
        assertEquals("onoffbar", translationInformationReplacement.replace("IPR","foobar", ConjugationPosition.SINGULAR_FIRST_PERSON));
        assertEquals("foobarbar", translationInformationReplacement.replace("PAP","foobar", ConjugationPosition.SINGULAR_FIRST_PERSON));
    }

    @Test
    public void replacement_should_handle_specific_position_in_alteration() {
        translationInformationReplacement = new TranslationInformationReplacement2("IPR1*foo*onoff");
        assertEquals("foobar", translationInformationReplacement.replace("IPR","foobar", ConjugationPosition.SINGULAR_FIRST_PERSON));
        assertEquals("onoffbar", translationInformationReplacement.replace("IPR","foobar", ConjugationPosition.SINGULAR_SECOND_PERSON));
    }

    @Test
    public void replacement_should_handle_multiple_specific_position_in_alteration() {
        translationInformationReplacement = new TranslationInformationReplacement2("IPR1*foo*first@IPR2*foo*second");
        assertEquals("foobar",translationInformationReplacement.replace("IPR","foobar",ConjugationPosition.PLURAL_THIRD_PERSON));
        assertEquals("firstbar",translationInformationReplacement.replace("IPR","foobar",ConjugationPosition.SINGULAR_SECOND_PERSON));
        assertEquals("secondbar",translationInformationReplacement.replace("IPR","foobar",ConjugationPosition.SINGULAR_THIRD_PERSON));
    }

    @Test
    public void replacement_should_handle_multiple_steps_in_correct_order() {
        translationInformationReplacement = new TranslationInformationReplacement2("IPR*toto*toti@IPR*toti*totu");
        assertEquals("totafoo", translationInformationReplacement.replace("IPR", "totafoo", ConjugationPosition.SINGULAR_FIRST_PERSON));
        assertEquals("totufoo", translationInformationReplacement.replace("IPR", "totofoo", ConjugationPosition.SINGULAR_FIRST_PERSON));
    }
}

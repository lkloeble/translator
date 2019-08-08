package verb;

import org.junit.Test;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.conjugation.romanian.RomanianConjugation2;
import patrologia.translator.declension.romanian.RomanianDeclensionFactory;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class ConjugationTest {

    private RomanianDeclensionFactory declensionFactory;
    private Conjugation2 conjugation = new RomanianConjugation2("incerca", Arrays.asList("IPR=>,i,a,am,atsi,a","AII=>am,ai,a,am,atsi,au"), declensionFactory);

    @Test
    public void conjugation_should_return_correct_times() {
        assertEquals(conjugation.getTimes().size(),2);
        List<String> times = conjugation.getTimes();
        assertTrue(times.contains("IPR"));
        assertTrue(times.contains("AII"));
    }

    @Test
    public void wrong_time_to_conjugate_root_return_only_root() {
        assertEquals("root", conjugation.getRootWithEveryEndingsByTime("root","UNKNOWN_TIME"));
    }

    @Test
    public void correct_time_should_give_expected_conjugation_for_a_given_root() {
        assertEquals("root,rooti,roota,rootam,rootatsi,roota",conjugation.getRootWithEveryEndingsByTime("root","IPR"));
        assertEquals("rootam,rootai,roota,rootam,rootatsi,rootau",conjugation.getRootWithEveryEndingsByTime("root","AII"));
    }

    @Test
    public void conjugation_should_detect_when_related_noun() {
        Conjugation2 conjugationWithRelationToNoun = new RomanianConjugation2("aciti",Arrays.asList("IPR=>esc,esti,este,im,itsi,esc","PAP=>[adj]"), declensionFactory);
        assertFalse(conjugation.isRelatedTonoun("IPR"));
        assertFalse(conjugation.isRelatedTonoun("AII"));
        assertFalse(conjugationWithRelationToNoun.isRelatedTonoun("IPR"));
        assertTrue(conjugationWithRelationToNoun.isRelatedTonoun("PAP"));
    }
}

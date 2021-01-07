package panin;

import org.junit.Test;
import patrologia.panin.ELSRepository;
import patrologia.panin.EquidistantLetter;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;

public class EquidistantLetterTest {

    private EquidistantLetter equidistantLetter;

    @Test
    public void equidistant_should_not_find_not_existing_sequence() {
        List<String> stringList = Arrays.asList("foobar", "toto");
        ELSRepository elsRepository = new ELSRepository(stringList);
        equidistantLetter = new EquidistantLetter(elsRepository, null);
        assertFalse(equidistantLetter.hasSequence());
        equidistantLetter = new EquidistantLetter(elsRepository, "");
        assertFalse(equidistantLetter.hasSequence());
        equidistantLetter = new EquidistantLetter(elsRepository, "aaaaabcbdbcdcbcbbcbdbcd");
        //assertFalse(equidistantLetter.hasSequence());
    }
}

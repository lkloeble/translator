import org.junit.Test;
import patrologia.translator.basicelements.Accentuer;

import static org.junit.Assert.assertEquals;

public class HebrewAccentuerTest {

    private Accentuer accentuer;

    @Test
    public void accentuer_erase_numbers_except_the_final_ones() {
        accentuer = new Accentuer();
        assertEquals("abc",accentuer.unaccentued("1a2b3c"));
        assertEquals("abc4",accentuer.unaccentued("1a2b3c4"));
        assertEquals("abc456",accentuer.unaccentued("1a2b3c456"));
        assertEquals("abc456",accentuer.unaccentued("1a456b3c456"));
    }
}

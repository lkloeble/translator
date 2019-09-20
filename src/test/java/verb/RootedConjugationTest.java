package verb;

import org.junit.Test;
import patrologia.translator.conjugation.RootedConjugation;

import static org.junit.Assert.assertEquals;

public class RootedConjugationTest {

    private RootedConjugation rootedConjugation;

    @Test
    public void can_find_good_position_for_nominal_parts_and_position() {
        rootedConjugation = new RootedConjugation("construction","part0,part1,part2,part3,part4,part5");
        assertEquals(rootedConjugation.getPositionForConstructionNumber(0),0);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(1),1);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(2),2);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(3),3);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(4),4);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(5),5);
    }

    @Test
    public void can_find_good_position_for_multiple_parts_and_position() {
        rootedConjugation = new RootedConjugation("construction","part0,part1a|part1b,part2,part3,part4,part5");
        assertEquals(rootedConjugation.getPositionForConstructionNumber(0),0);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(1),1);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(2),1);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(3),2);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(4),3);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(5),4);
        assertEquals(rootedConjugation.getPositionForConstructionNumber(6),5);
    }

}

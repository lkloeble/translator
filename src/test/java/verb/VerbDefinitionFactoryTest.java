package verb;

import org.junit.Test;
import patrologia.translator.basicelements.Language;
import patrologia.translator.conjugation.VerbDefinition;
import patrologia.translator.conjugation.VerbDefinitionFactory;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class VerbDefinitionFactoryTest {

    VerbDefinitionFactory factory = new VerbDefinitionFactory();

    @Test
    public void romanian_factory_should_return_romanian_verb_definition() {
        VerbDefinition verbDefinition = factory.getVerbDefinition(Language.ROMANIAN, "foobar");
        assertTrue(verbDefinition != null);
        assertEquals(verbDefinition.getLanguage(),Language.ROMANIAN);
    }
}

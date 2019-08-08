package verb;

import org.junit.Test;
import patrologia.translator.basicelements.DummyAccentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.conjugation.romanian.RomanianConjugationFactory;
import patrologia.translator.declension.romanian.RomanianDeclension;
import patrologia.translator.declension.romanian.RomanianDeclensionFactory;
import patrologia.translator.utils.DictionaryLoader;

import java.util.*;

import static junit.framework.TestCase.assertTrue;

public class VerbRepositoryTest {

    private VerbRepository2 verbRepository;

    @Test
    public void romanian_simple_verb_is_loaded_from_text_verb_description() {
        //GIVEN
        List<String> verbDefinitions = getParams("imbrac,a,[incerca]");
        List<String> conjugationDefinitions = getParams("incerca%incerca.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("incerca",Arrays.asList("IPR=>,i,a,am,atsi,a","AII=>am,ai,a,am,atsi,au"));
        RomanianDeclensionFactory declensionFactory = null;
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);

        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("imbraci"));
    }

    @Test
    public void romanian_verb_handles_participle_replacement_from_text_verb_description() {
        //GIVEN
        List<String> verbDefinitions = getParams("doband,,[aciti],(PAP*doband*dobandit*0)");
        List<String> conjugationDefinitions = getParams("aciti%aciti.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("aciti",Arrays.asList("IPR=>esc,esti,este,im,itsi,esc","PAP=>[adj]"));
        RomanianDeclension adjective = new RomanianDeclension("adjective.txt", Arrays.asList("nomadjmascsga%sing%masc%","nomnounmascsg%sing%masc%ul","nomnounfemsga%sing%fem%a"),false);
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Arrays.asList("adj%adjective.txt"),Arrays.asList(adjective));
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);

        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("dobandesc"));
        assertTrue(verbRepository.hasVerb("dobandit"));
        assertTrue(verbRepository.hasVerb("dobandita"));
    }

    @Test
    public void romanian_verb_handles_participle_replacement_for_multiple_declensions_from_text_verb_description() {
        //GIVEN
        List<String> verbDefinitions = getParams("doband,,[aciti],(PAP*doband*dobandit*0)");
        List<String> conjugationDefinitions = getParams("aciti%aciti.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("aciti",Arrays.asList("IPR=>esc,esti,este,im,itsi,esc","PAP=>[adj,adjts]"));
        RomanianDeclension adjective = new RomanianDeclension("adjective.txt", Arrays.asList("nomadjmascsga%sing%masc%","nomnounmascsg%sing%masc%ul","nomnounfemsga%sing%fem%a"),false);
        RomanianDeclension adjectivets = new RomanianDeclension("adjectivets.txt", Arrays.asList("nom%sing%masc%","nom%sing%fem%a","gen%plr%masc%silor"),false);
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Arrays.asList("adj%adjective.txt","adjts%adjectivets.txt"),Arrays.asList(adjective,adjectivets));
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);

        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("dobandesc"));
        assertTrue(verbRepository.hasVerb("dobandit"));
        assertTrue(verbRepository.hasVerb("dobandita"));
        assertTrue(verbRepository.hasVerb("dobanditsilor"));
    }

    @Test
    public void replacement_rule_for_specific_position_is_handled() {
        //GIVEN
        List<String> verbDefinitions = getParams("imbogats,,[aciti],(ASP25*imbogats*imbogatse*0)");
        List<String> conjugationDefinitions = getParams("aciti%aciti.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("aciti",Arrays.asList("IPR=>esc,esti,este,im,itsi,esc","ASP=>esc,esti,asca,im,itsi,asca"));
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Collections.EMPTY_LIST,Collections.EMPTY_LIST);
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);

        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("imbogatsesc"));
        assertTrue(verbRepository.hasVerb("imbogatseasca"));

    }

    private List<String> getParams(String... testData) {
        List<String> params = new ArrayList<>();
        for(String data : testData) {
            params.add(data);
        }
        return params;
    }
}

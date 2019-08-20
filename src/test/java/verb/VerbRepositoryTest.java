package verb;

import org.junit.Test;
import patrologia.translator.basicelements.Analysis;
import patrologia.translator.basicelements.DummyAccentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.verb.Verb;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.conjugation.english.EnglishConjugationFactory;
import patrologia.translator.conjugation.romanian.RomanianConjugationFactory;
import patrologia.translator.declension.english.EnglishDeclensionFactory;
import patrologia.translator.declension.romanian.RomanianDeclension;
import patrologia.translator.declension.romanian.RomanianDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.Translator;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

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

    @Test
    public void verbs_with_adverb_construction_is_handled() {
        //GIVEN
        List<String> verbDefinitions = getParams("bringforth,[see],(AIP*bring*brought*0)");
        List<String> conjugationDefinitions = getParams("see%see.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("see",Arrays.asList("IPR=>,,s,,, ,","AIP=>,,,,, ,"));
        EnglishDeclensionFactory declensionFactory = new EnglishDeclensionFactory(Collections.EMPTY_LIST,Collections.EMPTY_LIST);
        EnglishConjugationFactory conjugationFactory = new EnglishConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ENGLISH, new DummyAccentuer(), verbDefinitions);

        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("broughtforth"));

    }


    @Test
    public void verbs_with_conjugation_having_multiple_construction_for_the_same_person() {
        //GIVEN
        List<String> verbDefinitions = getParams("locu,,[locuiesc]");
        List<String> conjugationDefinitions = getParams("locuiesc%locuiesc.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("locuiesc",Arrays.asList("IPR=>iesc,iesti,este|ieste,im,itsi,iesc"));
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Collections.EMPTY_LIST,Collections.EMPTY_LIST);
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);
        Translator frenchTranslator = new FrenchTranslator(Arrays.asList("locu@verb!norm%1(verb)=habiter"), Arrays.asList("habiter@NORM%[INFINITIVE]=[habiter]%[IPR]=[habite,habites,habite,habitons,habitez,habitent]"), verbRepository, null, null, null, declensionFactory);
        Phrase phrase = new Phrase(1,Language.ROMANIAN);
        Verb verb = verbRepository.getVerb("locuieste");
        verb.updateInitialValue("locuieste");
        phrase.addWordAtPosition(1, verb);
        Analysis analysis = new Analysis(Language.ROMANIAN,phrase);
        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("locuieste"));
        assertEquals("habite",frenchTranslator.translateToRead(analysis));
    }

    @Test
    public void verbs_with_conjugation_having_translation_replacement_is_well_translated() {
        //GIVEN
        List<String> verbDefinitions = getParams("intseleg,e,[merg],(PAP*intseleg*intseles*0@ASP*intseleg*intseleag*0)");
        List<String> conjugationDefinitions = getParams("merg%merg.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("merg",Arrays.asList("IPR=>,i,e,em,etsi,","ASP=>,i,a,em,etsi,a"));
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Collections.EMPTY_LIST,Collections.EMPTY_LIST);
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);
        Translator frenchTranslator = new FrenchTranslator(Arrays.asList("intselege@verb!norm%1(verb)=comprendre"), Arrays.asList("comprendre@NORM%[INFINITIVE]=[comprendre]%[IPR]=[comprends,comprends,comprend,comprenons,comprenez,comprennent]%[PAP]=[compris]%[ASP]=[comprenne,comprennes,comprenne,comprenions,compreniez,comprennent]"), verbRepository, null, null, null, declensionFactory);
        Phrase phrase = new Phrase(1,Language.ROMANIAN);
        Verb verb = verbRepository.getVerb("intseleg");
        verb.updateInitialValue("intseleg");
        phrase.addWordAtPosition(1, verb);
        Analysis analysis = new Analysis(Language.ROMANIAN,phrase);
        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("intseleg"));
        assertEquals("comprends",frenchTranslator.translateToRead(analysis));
    }


    @Test
    public void verbs_with_conjugation_having_more_than_two_construction_for_the_same_person_is_well_handled() {
        //GIVEN
        List<String> verbDefinitions = getParams("zic,,[merg],(AIP*zic*zis*0@AII*zic*zice*0@PAP*zic*zis*0)");
        List<String> conjugationDefinitions = getParams("merg%merg.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("merg",Arrays.asList("PAPR=>ind|indu|and|andu"));
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Collections.EMPTY_LIST,Collections.EMPTY_LIST);
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);
        Translator frenchTranslator = new FrenchTranslator(Arrays.asList("zic@verb!norm%1(verb)=dire"), Arrays.asList("dire@NORM%[INFINITIVE]=[dire]%[IPR]=[dis,dis,dit,disons,dîtes,disent]%[AIP]=[dis,dis,dit,dîmes,dîtes,dirent]%[PIP]=[suis dit,es dit,est dit,sommes dits, êtes dits, sont dit]%[AIP]=[x,dis,x,x,dites,x]%[AII]=[disais,disais,disait,disions,disiez,disaient]%[PAPR]=[disant]%[AIPP]=[ai dit,as dit,a dit,avons dit,avez dit,ont dit]%[ACAOIM]=[dis,dis,disons,dites]%[AIF]=[dirai,diras,dira,dirons,direz,diront]%[AIMP]=[-,dis,-,disons,dîtes,-]%[PAP]=[dit]%[ACAOIN]=[dis,dis,dit,dîmes,dîtes,dirent]%[ARAIPR]=[dis,dis,dit,disons,dîtes,disent]%[PPP]=[dit]%[PRESACTPART]=[disant,disant,disant,-,-,-]%[NIFALAIP]=[suis dit,es dit,est dit,sommes dits,êtes dits,sont dits]%[ACAOINBIS]=[dis,dis,dit,disons,dites,dirent]%[ASI]=[dise,dises,dise,disions,disiez,disent]%[BINHIPER]=[dis,dis,dit,dîmes,dîtes,dirent]%[AIFP]=[aurai dis, auras dit,aura dit,aurons dit,aurez dit,auront dit]%[ASP]=[dise,dises,dise,disions,disiez,disent]%[ASPP]=[eusse dit,eusses dit,eut dit,eussions dits,eussiez dit,eurent dit]%[IPRPLU]=[disent]"), verbRepository, null, null, null, declensionFactory);
        Phrase phrase = new Phrase(1,Language.ROMANIAN);
        Verb verb = verbRepository.getVerb("zicand");
        verb.updateInitialValue("zicand");
        phrase.addWordAtPosition(1, verb);
        Analysis analysis = new Analysis(Language.ROMANIAN,phrase);
        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("zicand"));
        assertEquals("disant",frenchTranslator.translateToRead(analysis));
    }

    @Test
    public void verbs_with_conjugation_having_multiple_construction_for_the_same_person_translates_well_other_velurd() {
        //GIVEN
        List<String> verbDefinitions = getParams("locu,,[locuiesc]");
        List<String> conjugationDefinitions = getParams("locuiesc%locuiesc.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("locuiesc",Arrays.asList("IPR=>iesc,iesti,este|ieste,im,itsi,iesc"));
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Collections.EMPTY_LIST,Collections.EMPTY_LIST);
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);
        Translator frenchTranslator = new FrenchTranslator(Arrays.asList("locu@verb!norm%1(verb)=habiter"), Arrays.asList("habiter@NORM%[INFINITIVE]=[habiter]%[IPR]=[habite,habites,habite,habitons,habitez,habitent]"), verbRepository, null, null, null, declensionFactory);
        Phrase phrase = new Phrase(1,Language.ROMANIAN);
        Verb verb = verbRepository.getVerb("locuim");
        verb.updateInitialValue("locuim");
        phrase.addWordAtPosition(1, verb);
        Analysis analysis = new Analysis(Language.ROMANIAN,phrase);
        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("locuim"));
        assertEquals("habitons",frenchTranslator.translateToRead(analysis));
    }

    @Test
    public void participle_past_are_well_translated() {
        //GIVEN
        List<String> verbDefinitions = getParams("incarc,,[incerca],(PAP*incarc*incarcat)");
        List<String> conjugationDefinitions = getParams("incerca%incerca.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("incerca",Arrays.asList("IPR=>,i,a,am,atsi,a","AII=>am,ai,a,am,atsi,au","PAP=>[adj,adjts]"));
        RomanianDeclension adjective = new RomanianDeclension("adjective.txt", Arrays.asList("nomadjmascsga%sing%masc%","nomnounmascsg%sing%masc%ul","nomnounfemsga%sing%fem%a"),false);
        RomanianDeclension adjectivets = new RomanianDeclension("adjectivets.txt", Arrays.asList("nom%sing%masc%","nom%sing%fem%a","gen%plr%masc%silor"),false);
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Arrays.asList("adj%adjective.txt","adjts%adjectivets.txt"),Arrays.asList(adjective,adjectivets));
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);
        Translator frenchTranslator = new FrenchTranslator(Arrays.asList("incarc@verb!norm%1(verb)=charger"), Arrays.asList("charger@NORM%[INFINITIVE]=[charger]%[IPR]=[charge,charges,charge,chargeons,chargez,chargent]%[PAP]=[chargé]%[PAPR]=[chargeant]"), verbRepository, null, null, null, declensionFactory);
        Phrase phrase = new Phrase(1,Language.ROMANIAN);
        Verb verb = verbRepository.getVerb("incarcat");
        verb.updateInitialValue("incarcat");
        phrase.addWordAtPosition(1, verb);
        Analysis analysis = new Analysis(Language.ROMANIAN,phrase);
        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("incarcat"));
        assertEquals("chargé",frenchTranslator.translateToRead(analysis));
    }

    @Test
    public void subjonctive_are_well_translated() {
        //GIVEN
        List<String> verbDefinitions = getParams("pierd,e,[vinde],(AII*pie*pia@CONJ0134*pierd*pier@CONJ25*pierd*piard)");
        List<String> conjugationDefinitions = getParams("vinde%vinde.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("vinde",Arrays.asList("IPR=>d,zi,de,dem,detsi,d","PSP=>d,zi,da,dem,detsi,d","CONJ=>z,zi,da,em,etsi,a"));
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Collections.EMPTY_LIST,Collections.EMPTY_LIST);
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);
        Translator frenchTranslator = new FrenchTranslator(Arrays.asList("pierde@verb!norm%1(verb)=perdre"), Arrays.asList("perdre@NORM%[INFINITIVE]=[perdre]%[IPR]=[perds,perds,perd,perdons,perdez,perdent]%[PIF]=[serai perdu,seras perdu,sera perdu,serons perdus,serez perdus,seront perdus]%[HIFPER]=[causais de perdre,causais de perdre,causa de perdre,causâmes de perdre,causâtes de perdre,causèrent de perdre]%[PAP]=[perdu]%[ACP]=[perdrais,perdrais,perdrait,perdrions,perdriez,perdraient]%[AII]=[perdais,perdais,perdait,perdions,perdiez,perdaient]%[CONJ]=[perdre]"), verbRepository, null, null, null, declensionFactory);
        Phrase phrase = new Phrase(1,Language.ROMANIAN);
        Verb verb = verbRepository.getVerb("piarda");
        verb.updateInitialValue("piarda");
        phrase.addWordAtPosition(1, verb);
        Analysis analysis = new Analysis(Language.ROMANIAN,phrase);
        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("piarda"));
        assertEquals("perdre",frenchTranslator.translateToRead(analysis));
    }

    @Test
    public void poate_verbal_romanian_construction_is_handled() {
        //GIVEN
        List<String> verbDefinitions = getParams("pot@IRREGULAR%[INFINITIVE]=[putea]%[IPR]=[pot,potsi,poate|poata,putem,putetsi,pot]%[PAP]=[putut]","deven,i,[deveni],(PAP*devinat*devenit@IPR0125*deven*devin@AIP01*deven*devi@AIP25*deven*devin@ACPINF*deven*ardeveni)");
        List<String> conjugationDefinitions = getParams("deveni%deveni.txt");
        Map<String, List<String>> conjugationsDefinitionsList = new HashMap<>();
        conjugationsDefinitionsList.put("deveni",Arrays.asList("IPR=>,i,e,im,itsi,","AIP=>u,i,a,im,itsi,a","PAP=>it","ACPINF=>,xx"));
        RomanianDeclensionFactory declensionFactory = new RomanianDeclensionFactory(Collections.EMPTY_LIST,Collections.EMPTY_LIST);
        RomanianConjugationFactory conjugationFactory = new RomanianConjugationFactory(conjugationDefinitions, conjugationsDefinitionsList, declensionFactory);
        verbRepository = new VerbRepository2(conjugationFactory, Language.ROMANIAN, new DummyAccentuer(), verbDefinitions);
        Translator frenchTranslator = new FrenchTranslator(Arrays.asList("pot@verb!irrg%1(verb)=pouvoir","deveni@verb!norm%1(verb)=devenir"), Arrays.asList("pouvoir@NORM%[INFINITIVE]=[pouvoir]%[IPR]=[peux,peux,peut,pouvons,pouvez,peuvent]","devenir@NORM%[INFINITIVE]=[devenir]%[IPR]=[deviens,deviens,devient,devenons,devenez,deviennent]"), verbRepository, null, null, null, declensionFactory);
        Phrase phrase = new Phrase(1,Language.ROMANIAN);
        Verb verbPoate = verbRepository.getVerb("poate");
        verbPoate.updateInitialValue("poate");
        Verb verbDeveni = verbRepository.getVerb("deveni");
        verbDeveni.updateInitialValue("deveni");
        phrase.addWordAtPosition(1, verbPoate);
        phrase.addWordAtPosition(2, verbDeveni);
        Analysis analysis = new Analysis(Language.ROMANIAN,phrase);
        //WHEN

        //THEN
        assertTrue(verbRepository.hasVerb("poate"));
        assertTrue(verbRepository.hasVerb("deveni"));
        assertEquals("peut devenir",frenchTranslator.translateToRead(analysis));
    }

    private List<String> getParams(String... testData) {
        List<String> params = new ArrayList<>();
        for(String data : testData) {
            params.add(data);
        }
        return params;
    }
}

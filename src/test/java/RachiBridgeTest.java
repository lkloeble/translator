import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.Accentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import patrologia.translator.conjugation.hebrew.HebrewConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.hebrew.HebrewDeclension;
import patrologia.translator.declension.hebrew.HebrewDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.HebrewAnalyzer;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.hebrew.HebrewRuleFactory;
import patrologia.translator.utils.Analyzer;

import java.util.*;

import static junit.framework.Assert.assertTrue;

public class RachiBridgeTest extends TranslatorBridgeTest {

    protected TranslatorBridge translatorBridge;

    private String localTestPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\hebrew\\";
    private String localCommonPath = "C:\\Users\\kloeble.l\\IdeaProjects\\translator\\src\\main\\resources\\";

    String nounFileDescription = localResourcesPath + "nouns.txt";
    String verbFileDescription = localResourcesPath + "verbs.txt";
    String prepositionFileDescription = localResourcesPath + "prepositions.txt";
    String hebrewFrenchDataFile = localResourcesPath + "cohn_hebrew_to_french.txt";
    String frenchVerbsDataFile = localCommonPath + "french_verbs.txt";
    String hebrewPathFile = localTestPath + "rachi_content.txt";
    String hebrewResultFile = localTestPath + "rachi_expected_results.txt";


    @Before
    public void init() {
        HebrewRuleFactory ruleFactory = new HebrewRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.HEBREW, new HebrewCaseFactory(), ruleFactory, getPrepositions(prepositionFileDescription));
        HebrewDeclensionFactory hebrewDeclensionFactory = new HebrewDeclensionFactory(getDeclensions(), getDeclensionList());
        NounRepository nounRepository = new NounRepository(Language.HEBREW, hebrewDeclensionFactory, new Accentuer(), getNouns(nounFileDescription));
        VerbRepository2 verbRepository = new VerbRepository2(new HebrewConjugationFactory(getConjugations(), getHebrewConjugationsDefinitions(), hebrewDeclensionFactory), Language.HEBREW, new Accentuer(), getVerbs(verbFileDescription));
        Analyzer hebrewAnalyzer = new HebrewAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getHebDico(hebrewFrenchDataFile), getFrenchVerbs(), verbRepository, nounRepository, null, null, hebrewDeclensionFactory);
        translatorBridge = new TranslatorBridge(hebrewAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(hebrewPathFile);
        mapValuesForResult = loadMapFromFiles(hebrewResultFile);
    }

    private List<String> getFrenchVerbs() {
        /*
        return Arrays.asList(
                new String[]{
                        "garder@NORM%[INFINITIVE]=[garder]%[IPR]=[garde,gardes,garde,gardons,gardez,gardent]%[PIP]=[suis gardé,es gardé,est gardé,sommes gardés,êtes gardés,sont gardés]%[AIP]=[gardais,gardais,garda,gardions,gardiez,gardaient]%[SPA]=[aie gardé,aies gardé,ait gardé,ayons gardés,ayez gardés,aient gardés]%[ASI]=[garde,gardes,garde,gardions,gardiez,gardent]%[PAP]=[gardé]%[AIF]=[garderai,garderas,gardera,garderons,garderez,garderont]"
                }
        );
        */
        return getFileContentForRepository(frenchVerbsDataFile);
    }

    private List<String> getNouns(String nounFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "awzn@masc%mascot"
                //"ykb@fem%ot2"
        });
        */
        return getFileContentForRepository(nounFileDescription);
    }

    private List<String> getHebDico(String dictionaryFile) {
        /*
        return Arrays.asList(new String[]{
                "qra@verb!norm%1(verb)=crier%2(verb)=lire%3(verb)=appeler",
                "an'hnw@prep%1(prep)=nous"
        });
         */
        return getFileContentForRepository(dictionaryFile);
    }

    private List<String> getPrepositions(String prepositionFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "b56@prep()",
                "b@prep()"
        });
        */
        return getFileContentForRepository(prepositionFileDescription);
    }

    private List<String> getVerbs(String verbFiles) {
        /*
        return Arrays.asList(new String[]{
                "smy,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@IPR%substitute%sm*swm*0@ARAIPR%substitute%smy*swmy*0@BINHIFPAST%substitute(3:4:8)%my*miy)"
        });
         */
        return getFileContentForRepository(verbFiles);
    }

    private List<String> getDeclensions() {
        return Arrays.asList(new String[]{
                "im%im.txt",
                "imsimple%imsimple.txt",
                "imdir%imdir.txt",
                "ot%ot.txt",
                "ot2%ot2.txt",
                "otplr%otplr.txt",
                "im-fem%im-fem.txt",
                "invmasc%invmasc.txt",
                "invfem%invfem.txt",
                "im-ot%im-ot.txt",
                "adj%adj.txt",
                "empty%empty.txt",
                "mascot%mascot.txt",
                "imonly%imonly.txt",
                "invpur%invpur.txt",
                "endingh%endingh.txt",
                "mascendingh%mascendingh.txt"
        });
    }

    private List<Declension> getDeclensionList() {
        List<Declension> declensionList = new ArrayList<>();
        declensionList.add(new HebrewDeclension("im.txt", getImElements()));
        declensionList.add(new HebrewDeclension("imsimple.txt", getImSimpleElements()));
        declensionList.add(new HebrewDeclension("imdir.txt", getImDirectionalElements()));
        declensionList.add(new HebrewDeclension("ot.txt", getOtElements()));
        declensionList.add(new HebrewDeclension("ot2.txt", getOtSansHElements()));
        declensionList.add(new HebrewDeclension("otplr.txt", getOtPlrlements()));
        declensionList.add(new HebrewDeclension("im-fem.txt", getImfemElements()));
        declensionList.add(new HebrewDeclension("invfem.txt", getInvfemElements()));
        declensionList.add(new HebrewDeclension("invmasc.txt", getInvmascElements()));
        declensionList.add(new HebrewDeclension("im-ot.txt", getImOtElements()));
        declensionList.add(new HebrewDeclension("mascot.txt", getMascOtElements()));
        declensionList.add(new HebrewDeclension("adj.txt", getAdjElements()));
        declensionList.add(new HebrewDeclension("imonly.txt", getImOnlyElements()));
        declensionList.add(new HebrewDeclension("empty.txt", getEmptyElements()));
        declensionList.add(new HebrewDeclension("invpur.txt", getInvPur()));
        declensionList.add(new HebrewDeclension("endingh.txt", getEndingH()));
        declensionList.add(new HebrewDeclension("mascendingh.txt", getMascEndingH()));
        return declensionList;

    }

    private List<String> getOtPlrlements() {
        return Arrays.asList(new String[]{
                "decot-i%plr%fem%i",
                "decot-ihm%plr%fem%ihm"
        });
    }

    private List<String> getEmptyElements() {
        return Arrays.asList(new String[]{
                "nomempty%sing%neut%",
                "decendw%sing%neut%w",
                "decemptyh%sing%neut%h",
                "nomendk%sing%neut%k",
                "nomendi%sing%neut%i"
        });
    }

    private List<String> getInvPur() {
        return Arrays.asList(new String[]{
                "nomempty%sing%neut%"
        });
    }

    private List<String> getImSimpleElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%masc%",
                "nomplra%plr%masc%60im000",
        });
    }

    private List<String> getImElements() {
        return Arrays.asList(new String[]{
                "nomsg(nomsg)%sing%masc%",
                "cst(nomsg)%sing%masc%&",
                "decim-h%sing%masc%h",
                "decim-iw%sing%masc%iw",
                "decim-nw%plr%masc%nw",
                "decim-w%sing%masc%w331",
                "decim-ik%sing%masc%ik000",
                "decim-k%sing%masc%k000",
                "decim-nsof%sing%masc%n000",
                "nomplra%plr%masc%60im000",
                "nomplrb%plr%masc%60in000",
                "decim-m%plr%masc%m000",
                "decim-ihm%plr%masc%ihm000",
                "cst(nomplra1)%plr%masc%i",
                "cst(nomplra2)%plr%masc%i&"
        });
    }


    private List<String> getImDirectionalElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%masc%",
                "cst(nomsg)%sing%masc%&",
                "dir(nomsg)%sing%masc%h",
                "decim-iw%sing%masc%iw",
                "decim-nw%plr%masc%nw",
                "decim-w%sing%masc%w331",
                "decim-k%sing%masc%k",
                "decim-nsof%sing%masc%n000",
                "nomplra%plr%masc%60im000",
                "nomplrb%plr%masc%60in000",
                "decim-m%plr%masc%m000",
                "cst(nomplra1)%plr%masc%i",
                "cst(nomplra2)%plr%masc%i&"
        });
    }


    private List<String> getOtElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%fem%h",
                "decot-t%sing%fem%t",
                "decot-ti%sing%fem%t60i",
                "decot-tk%sing%fem%t56k00064",
                "decot-tw%sing%fem%tw",
                "decot-w%sing%fem%w331",
                "decim-w%sing%fem%w331",
                "decim-nsof%sing%fem%n000",
                "decot-tn%sing%fem%tn",
                "nomplr%plr%fem%wt"
        });
    }

    private List<String> getOtSansHElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%fem%",
                "decot-i%sing%fem%i",
                "decot-w%sing%fem%w331",
                "decim-nsof%sing%fem%n000",
                "decot-tw%sing%fem%tw",
                "decot-tk%sing%fem%t62k00064",
                "decot-k%sing%fem%k00064",
                "nomplr%plr%fem%wt"
        });
    }


    private List<String> getAdjElements() {
        return Arrays.asList(new String[]{
                "nomh%sing%fem%h",
                "nomot%plr%fem%wt",
                "nomempty%sing%masc%",
                "nomim%plr%masc%im",
                "nomfemplr%plr%fem%w331t",
                "decim-iw%sing%masc%iw",
                "cst(nomim)%plr%masc%i&"
        });
    }

    private List<String> getImOnlyElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%masc%",
                "cst(nomsg)%sing%masc%&",
                "nomplr%plr%masc%im",
                "cst(nomplr)%plr%masc%i&",
        });
    }

    private List<String> getMascOtElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%masc%",
                "cst(nomsg)%sing%masc%&",
                "nomplr%plr%masc%wt",
                "decim-i%sing%masc%i",
                "decim-ih%sing%masc%ih",
                "decim-iw%sing%masc%iw",
                "decim-w%sing%masc%w",
                "decim-tw%sing%masc%tw",
                "decim-nw%sing%masc%nw",
                "decim-inw%sing%masc%inw",
                "decim-ik%sing%masc%ik000",
                "decim-k%sing%masc%k",
                "decim-wti%plr%masc%wti",
                "decim-wtinw%plr%masc%wtinw"
        });
    }

    private List<String> getImOtElements() {
        return Arrays.asList(new String[]{
                "nommascsg%sing%masc%",
                "nomfemsg%sing%fem%64h",
                "nomfemplr%plr%fem%w331t",
                "cst(nommascsg)%sing%masc%&",
                "decim-i%sing%masc%i",
                "decim-ik%sing%masc%ik000",
                "decim-w%sing%masc%w",
                "decim-ih%sing%masc%ih",
                "decim-iw%sing%masc%iw",
                "decim-ihm%plr%masc%ihm",
                "cst(nomplra1)%plr%masc%i",
                "nomplr%plr%masc%60im000",
                "cst(nomplr)%plr%masc%i&"
        });
    }

    private List<String> getInvmascElements() {
        return Arrays.asList(new String[]{
                "noma%sing%masc%",
                "dir(noma)%sing%masc%h",
                "decim-k%sing%masc%k",
                "nom%plr%masc%"
        });
    }

    private List<String> getEndingH() {
        return Arrays.asList(
                new String[]{
                        "noma%sing%fem%h",
                        "nomb%sing%fem%t",
                        "noma%plr%fem%wt",
                        "nomb%plr%fem%iw",
                        "decot-tk%sing%fem%t56k00064",
                        "decot-tm%plr%fem%tm000"
                });
    }

    private List<String> getMascEndingH() {
        return Arrays.asList(
                new String[]{
                        "nomsg%sing%masc%h",
                        "nomplr%plr%masc%im",
                        "cst(nomplr)%plr%masc%i"
                });
    }

    private List<String> getImfemElements() {
        return Arrays.asList(new String[]{
                "noma%sing%fem%",
                "nomb%sing%fem%h",
                "nomplr%plr%fem%im",
                "decim-m%plr%fem%m000",
                "cst(nomplr)%plr%fem%i"
        });
    }

    private List<String> getInvfemElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%fem%",
                "decim-i%sing%fem%i",
                "cst(nomsg)%sing%fem%&"
        });
    }

    private List<String> getConjugations() {
        return Arrays.asList(new String[]{
                "paal%paal.txt",
                "paal2%paal2.txt",
                "hiphil%hiphil.txt",
                "piel%piel.txt",
                "mishna%mishna.txt",
                "binyan%binyan.txt"
        });
    }


    private Map<String, List<String>> getHebrewConjugationsDefinitions() {
        Map<String, List<String>> hebrewConjugationDefinitionsMap = new HashMap<>();
        hebrewConjugationDefinitionsMap.put("paal", getPaalDefinition());
        hebrewConjugationDefinitionsMap.put("paal2", getPaal2Definition());
        hebrewConjugationDefinitionsMap.put("hiphil", getHiphilDefinition());
        hebrewConjugationDefinitionsMap.put("piel", getPielDefinition());
        hebrewConjugationDefinitionsMap.put("mishna", getMishnaDefinition());
        hebrewConjugationDefinitionsMap.put("binyan", getBinyalPielDefinition());
        hebrewConjugationDefinitionsMap.put("binyanhifil", getBinyalHifilDefinition());
        hebrewConjugationDefinitionsMap.put("binyanhufal", getBinyalHufalDefinition());
        hebrewConjugationDefinitionsMap.put("binyanpual", getBinyalPualDefinition());
        hebrewConjugationDefinitionsMap.put("hitpael", getHitpaelDefinition());
        hebrewConjugationDefinitionsMap.put("nifal", getNifalDefinition());
        return hebrewConjugationDefinitionsMap;
    }

    private List<String> getNifalDefinition() {
        return Arrays.asList(new String[]{
                "NIFAIP=>*n*ti,*n*t,*n*@|*n*h,*n*nw,*n*tm|*n*tn,*n*w",
                "NIFAIF=>*a*h,*t*h|*t*i,*i*h|*t*h,*n*h,*t*w|*t*inh,*i*w|*t*inh",
                "NIFIPR=>*n*@|*n*t,*n*@|*n*t,*n*@|*n*t,*n*im|*n*wt,*n*im|*n*wt,*n*im|*n*wt",
                "AIMP=>*h*@,*h*i,*h*w,*h*h"
        });
    }

    private List<String> getHitpaelDefinition() {
        return Arrays.asList(new String[]{
                "HITPRE=>*mt*@|*mt*t,*mt*@|*mt*t,*mt*@|*mt*t,*mt*im000|*mt*wt,*mt*im000|*mt*wt,*mt*im000|*mt*wt",
                "HITAIP=>*n*iti,*n*it,*n*h|*n*th,*n*inw,*n*itm|*n*itn,*n*w"
        });
    }

    private List<String> getPielDefinition() {
        return Arrays.asList(new String[]{
                "PIEPER=>ti,t|t,|h,nw,tm|tn,w309",
                "PIEPRE=>*m*@|*m*t,*m*@|*m*t,*m*@|*m*t,*m*im|*m*wt,*m*im|*m*wt,*m*im|*m*wt",
                "PIEAIF=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*nh,*i*w|*t*nh"
        });
    }

    private List<String> getPaalDefinition() {
        /*
        return Arrays.asList(new String[]{
                "IPRPLU=>im",
                "BINHIFPAST=>*h*ti,*h*t,*h*@|*h*i,*h*nw,*h*tm|*h*tn,*h*w"
        });
         */
        return Arrays.asList(new String[]{
                "AIP=>ti,t|t,|h,nw,tm000|tn,w309",
                "AIPSHORT=>x-,x-,zz,x-,x-,x-",
                "ARAIPR=>x-,x-,,nw,x-,im000|in000",
                "ARAPAPR=>*d*h",
                "AIF=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*nh,*i*w|*t*nh",
                "CONVFUT=>x-,x-,*i*@,x-,x-,x-",
                "NIFALAIP=>*n*ti,*n*t,*n*@|*n*h,*n*nw,*n*tm000|*n*tn,*n*w",
                "BINHIFPAST=>*h*ti,*h*t,*h*@|*h*i,*h*nw,*h*tm|*h*tn,*h*w",
                "IPR=>|t,|t,|t,im|wt,im|wt,im|wt",
                "IPRPLU=>im",
                "SUBST=>i"
        });
    }

    private List<String> getPaal2Definition() {
        return Arrays.asList(new String[]{
                "AIP=>ti,t|t,|h,nw,tm|tn,w309",
                "AIF=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*nh,*i*w|*t*nh"
        });
    }

    private List<String> getHiphilDefinition() {
        /*
        return Arrays.asList(new String[]{
                "HIFPER=>*h*ti,*h*t|*h*t,*h*@|*h*h,*h*nw309,*h*tm|*h*tn,*h*w309"
        });
        */

        return Arrays.asList(new String[]{
                "HIFPER=>*h*ti,*h*t|*h*t,*h*@|*h*h,*h*nw309,*h*tm|*h*tn,*h*w309",
                "HIFFUT=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*nh,*i*w|*t*nh",
                "HIFPART=>*m*@|*m*t,*m*im|*m*wt"
        });
    }

    private List<String> getBinyalHifilDefinition() {
        /*
        return Arrays.asList(new String[]{
                "BINHIFUT=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*h,*i*w|*t*h"
        });
         */
        return Arrays.asList(new String[]{
                "BINHIPRE=>*m*@|*m*h,*m*@|*m*h,*m*@|*m*h,*m*im|*m*wt,*m*im|*m*wt,*m*im|*m*wt",
                "ARAPRE=>x-,x-,x-,x-,x-,*m*in000",
                "BINHIPER=>*h*ti,*h*t|*h*t,*h*h|*h*th,*h*nw309,*h*tm|*h*tn,*h*w309",
                "BINHIFUT=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*h,*i*w|*t*h"
        });
    }

    private List<String> getBinyalHufalDefinition() {
        return Arrays.asList(new String[]{
                "BINHUPER=>*h*ti,*h*t|*h*t,*h*a|*h*ah,*h*nw309,*h*tm|*h*tn,*h*aw309",
                "BINHUFUT=>*aw*@,*tw*@|*tw*i,*iw*@|*tw*@,*nw*@,*tw*w|*tw*nh,*iw*w|*tw*nh"
        });
    }

    private List<String> getBinyalPualDefinition() {
        return Arrays.asList(new String[]{
                "BINPUPER=>ti,t|t,|h,nw,tm|tn,w309",
                "BINPUFUT=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*nh,*i*w|*t*nh"
        });
    }

    private List<String> getBinyalPielDefinition() {
        return Arrays.asList(new String[]{
                "BINPER=>*@*ti,*@*t,*@*@|*@*h,*@*nw309,*@*m000|*@*n000,*@*w309",
                "BINPRE=>*m*@|*m*t,*m*@|*m*t,*m*@|*m*t,*m*im000|*m*wt,*m*im000|*m*wt,*m*im000|*m*wt",
                "BINPART=>*m*h|*m*it,*m*im|*m*wt",
                "BINIMPERA=>*@*@|*@*i,*@*w|*@*nh"
        });
    }

    private List<String> getMishnaDefinition() {
        return Arrays.asList(new String[]{
                "IPR=>-,-,-,-,-,in000"
        });
    }
    @Test
    public void rachi_bereshit_chapter1() {
        checkInMaps("rachitext1A", translatorBridge);
        checkInMaps("rachitext1B", translatorBridge);
        checkInMaps("rachitext1C", translatorBridge);
        checkInMaps("rachitext1D", translatorBridge);
        checkInMaps("rachitext1E", translatorBridge);
        checkInMaps("rachitext1F", translatorBridge);
        checkInMaps("rachitext1G", translatorBridge);
        checkInMaps("rachitext1H", translatorBridge);
        checkInMaps("rachitext1I", translatorBridge);
        checkInMaps("rachitext1J", translatorBridge);
        checkInMaps("rachitext1K", translatorBridge);
        checkInMaps("rachitext1L", translatorBridge);
    }

    @Test
    public void shei_lamorei_rachi1() {
        checkInMaps("sheilamoreiA", translatorBridge);
        checkInMaps("sheilamoreiB", translatorBridge);
        checkInMaps("sheilamoreiC", translatorBridge);
        checkInMaps("sheilamoreiD", translatorBridge);
        checkInMaps("sheilamoreiE", translatorBridge);
        checkInMaps("sheilamoreiF", translatorBridge);
        checkInMaps("sheilamoreiG", translatorBridge);
        checkInMaps("sheilamoreiH", translatorBridge);
        checkInMaps("sheilamoreiB1", translatorBridge);
        checkInMaps("sheilamoreiB2", translatorBridge);
        checkInMaps("sheilamoreiB3", translatorBridge);
        checkInMaps("sheilamoreiB4", translatorBridge);
        checkInMaps("sheilamoreiB5", translatorBridge);
        checkInMaps("sheilamoreiB6", translatorBridge);
        checkInMaps("sheilamoreiB7", translatorBridge);
        checkInMaps("sheilamoreiB8", translatorBridge);
        checkInMaps("sheilamoreiB9", translatorBridge);
        checkInMaps("sheilamoreiB10", translatorBridge);
        checkInMaps("sheilamoreiB11", translatorBridge);
        checkInMaps("sheilamoreiB12", translatorBridge);
        checkInMaps("sheilamoreiC1", translatorBridge);
        checkInMaps("sheilamoreiC2", translatorBridge);
        checkInMaps("sheilamoreiC3", translatorBridge);
        checkInMaps("sheilamoreiC4", translatorBridge);
        checkInMaps("sheilamoreiC5", translatorBridge);
        checkInMaps("sheilamoreiC6", translatorBridge);
        checkInMaps("sheilamoreiC7", translatorBridge);
        checkInMaps("sheilamoreiC8", translatorBridge);
        checkInMaps("sheilamoreiC9", translatorBridge);
    }

    @Test
    public void test_failedones() {
        assertTrue(true);
        checkInMaps("toto", translatorBridge);
    }
}

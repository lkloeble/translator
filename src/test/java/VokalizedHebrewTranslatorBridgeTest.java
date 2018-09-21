import org.junit.Before;
import org.junit.Test;
import org.patrologia.translator.TranslatorBridge;
import org.patrologia.translator.basicelements.*;
import org.patrologia.translator.basicelements.noun.NounRepository;
import org.patrologia.translator.basicelements.preposition.PrepositionRepository;
import org.patrologia.translator.basicelements.verb.VerbRepository;
import org.patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import org.patrologia.translator.conjugation.hebrew.HebrewConjugationFactory;
import org.patrologia.translator.declension.Declension;
import org.patrologia.translator.declension.hebrew.HebrewDeclension;
import org.patrologia.translator.declension.hebrew.HebrewDeclensionFactory;
import org.patrologia.translator.linguisticimplementations.FrenchTranslator;
import org.patrologia.translator.linguisticimplementations.HebrewAnalyzer;
import org.patrologia.translator.linguisticimplementations.Translator;
import org.patrologia.translator.rule.hebrew.HebrewRuleFactory;
import org.patrologia.translator.utils.Analizer;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lkloeble on 28/09/2017.
 */
public class VokalizedHebrewTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;
    String frenchVerbsDataFile = "C:\\translator\\src\\main\\resources\\french_verbs.txt";
    String hebrewPathFile = "C:\\translator\\src\\test\\resources\\hebrew_content.txt";
    String hebrewResultFile = "C:\\translator\\src\\test\\resources\\hebrew_expected_results.txt";


    @Before
    public void init() {
        HebrewRuleFactory ruleFactory = new HebrewRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.HEBREW, new HebrewCaseFactory(), ruleFactory, getPrepositions());
        HebrewDeclensionFactory hebrewDeclensionFactory = new HebrewDeclensionFactory(getDeclensions(), getDeclensionList());
        NounRepository nounRepository = new NounRepository(Language.HEBREW, hebrewDeclensionFactory, new Accentuer(),getNouns());
        VerbRepository verbRepository = new VerbRepository(new HebrewConjugationFactory(getConjugations(), getHebrewConjugationsDefinitions(), nounRepository), Language.HEBREW, new Accentuer(),getVerbs());
        Analizer hebrewAnalyzer = new HebrewAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getHebDico(), getFrenchVerbs(), verbRepository, nounRepository, null, null, hebrewDeclensionFactory);
        translatorBridge = new TranslatorBridge(hebrewAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(hebrewPathFile);
        mapValuesForResult = loadMapFromFiles(hebrewResultFile);
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
                "nomsg%sing%masc%",
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
                "decot-w%sing%fem%w331",
                "decim-w%sing%fem%w331",
                "decim-nsof%sing%fem%n000",
                "decot-tn%sing%fem%tn",
                "nomplr%plr%fem%ot"
        });
    }

    private List<String> getOtSansHElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%fem%",
                "decot-w%sing%fem%w331",
                "nomplr%plr%fem%ot"
        });
    }


    private List<String> getAdjElements() {
        return Arrays.asList(new String[]{
                "nomh%sing%fem%h",
                "nomot%plr%fem%ot",
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
                "decim-w%sing%masc%w331",
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
                "decim-i%sing%fem%i60",
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

    private List<String> getFrenchVerbs() {
        /*
        return Arrays.asList(
                new String[]{
                        "aller@NORM%[INFINITIVE]=[aller]%[IPR]=[vais,vas,va,allons,allez,vont]%[AIP]=[allai,allas,alla,allions,alliez,allèrent]%[PAP]=[allé]%[PAPR]=[allant]",
                }
        );
        */
        return getFileContentForRepository(frenchVerbsDataFile);
    }

    private List<String> getNouns() {

        return Arrays.asList(new String[]{
                "a@masc%invpur",
                "ab@masc%mascot",
                "abr@masc%imonly",
                "abrhm@masc%invpur",
                "a58d64m64h@fem%custom(nomsg=admh|decim-nw=admtnw)",
                "a64d64m000@masc%invmasc",
                "a62'h64d@masc%empty",
                "a63'h63t@masc%empty",
                "akilt@neut%invpur",
                "a60is298@masc%custom(nomplr=ansim|cst(nomplr)=ansi&|dechsofit=aish|nomsg=a60is298)",
                "aw331r@fem%invfem",
                "awrita@neut%invpur",
                "a'h@masc%im",
                "al65h60im000@masc%custom(nomsg=alhim|cst(nomsg)=alhi&|decim-inwplr=alhinw|decim-ik=alhik)",
                "alhi@neut%invpur",
                "aliyzr@masc%invmasc",
                "alyzr@masc%invmasc",
                "amh@fem%custom(nomsg=amh|cst(nomsg)=amt|nomplr=a67mw331t)",
                "a64mw331n000@masc%im",
                "a62r62ts@fem%custom(nomplr=artswt|nomsg=a62r62ts)",
                "arwk@adj%adj",
                "a60s29864h@fem%custom(nomplr=nsim|cst(nomplr)=nsi&|nomsg=a60s29864h|cst(nomsg)=ast|decim-k=a60s29856t56k00064|decimplr-k=n64s29862ik00064|decimplr-h=nsihm000)",
                "aswr@adj%im-ot",
                "asmwr@fem%endingh",
                "atsxrik@neut%invpur",
                "b65hw@neut%invpur",
                "bit@masc%im",
                "b62n000@masc%im-ot",
                "b30565q62r@masc%im",
                "bwqr@masc%im",
                "b60q62r@masc%im",
                "b30556r60it@fem%mascot",
                "b56r64k64@fem%ot",
                "b30564s29964r@masc%im",
                "bt@fem%ot2",
                "bnwt@fem%otplr",
                "d64b64r@masc%im",
                "dbri@masc%invmasc",
                "dp@masc%im",
                "drk@masc%im",
                "dsa@masc%im",
                "g64dw331l@adj%im-ot",
                "gwi@masc%custom(nomplr=gwim|nomsg=gwi)",
                "girsa@fem%invpur",
                "gm'@neut%invpur",
                "gmlial@masc%invmasc",
                "g64n000@masc%im",
                "hwsyih@neut%invpur",
                "h61ik64l@masc%im",
                "hll@neut%invpur",
                "hqbh@neut%invpur",
                "h63r@masc%im",
                "wtrin@adj%im-ot",
                "zhr@neut%invpur",
                "z64q61n000@adj%adj",
                "zmn@masc%im",
                "zry@masc%im",
                "'hds@adj%im-ot",
                "'h65d62s@masc%im",
                "'hwb@masc%mascot",
                "'hwbt@fem%invpur",
                "'hmh@fem%invfem",
                "'htswt@masc%invmasc",
                "'hs29862k00056@fem%im-fem",
                "'h63k63m000@adj%adj",
                "'hlb@masc%im",
                "'h58lw331m000@masc%mascot",
                "xym@masc%im",
                "xrpwn000@masc%invpur",
                "ibsh@fem%invpur",
                "ih@masc%invpur",
                "ihwh@masc%invmasc",
                "ihwswy@masc%invmasc%",
                "iw331m000@masc%custom(nomplr=imim000|cst(nomplr)=imi|cst(nomsg)=iwm&|nomsg=iwm000)",
                "iwsp@masc%invpur",
                "ilqwx@neut%invpur",
                "its'hq@masc%invpur",
                "i60r56a63t@neut%imonly",
                "i56rw309s29864l63i60m000@masc%invmasc",
                "i60s29956r64a61l@masc%invmasc",
                "i63d@masc%im",
                "i61y58q65b@masc%invmasc",
                "itsia@fem%endingh",
                "i56s298w309y64@fem%ot",
                "i56s298w309y64h@masc%invpur",
                "i64s29863r@adj%im-ot",
                "kdai@neut%invpur",
                "khn@masc%im",
                "k'h@masc%mascot",
                "k31556n63y63n000@neut%invpur",
                "kwhn@masc%im",
                "kw331k56b61@masc%im",
                "krti@neut%invmasc",
                "ktib@neut%invpur",
                "lbn@masc%invmasc",
                "l63i56l64h@fem%custom(nomplr=lilwt|cst(nomsg)=lil&|nomsg=lilh)",
                "l64i56l64h@fem%custom(nomplr=lilwt|cst(nomsg)=lil&|nomsg=lilh)",
                "l'hm@neut%invpur",
                "lsxim000@neut%invpur",
                "lsxin000@neut%invpur",
                "lswn@fem%ot2",
                "lkt@neut%invmasc",
                "mamr@neut%im",
                "m64qw331m000@masc%mascot",
                "mbdil@neut%invpur",
                "m60d56b30564r@masc%imdir",
                "mdrs@neut%im",
                "min@masc%im",
                "m64i60m000@neut%invpur",
                "mktb@masc%im",
                "m60ts56r63i60m000@masc%invmasc",
                "mtsriim000@masc%invmasc",
                "mlak@masc%im",
                "m62l62k00056@masc%im",
                "mqwh@neut%invpur",
                "mqr@masc%mascendingh",
                "mrgl@masc%im",
                "mys@masc%endingh",
                "mtsw@fem%ot",
                "ms29862h@masc%invmasc",
                "msli@neut%invpur",
                "msnh@fem%invpur",
                "msikir@neut%invmasc",
                "msth@masc%invmasc",
                "m63t56'h60il@neut%invpur",
                "nakl@masc%im",
                "n64b60ia@masc%im-ot",
                "n56h63r@masc%mascot",
                "n64y63r@masc%im",
                "nts@masc%im",
                "ybir@fem%ot",
                "ywlm@masc%im",
                "yzrih@masc%invpur",
                "yiin@masc%invpur",
                "y63in@masc%im",
                "y60ir@fem%custom(nomplr=yrim|cst(nomplr)=yri&|dir(nomsg)=yirh|nomsg=y60ir)",
                "y64m000@masc%im",
                "ymwd@masc%im",
                "ynin@masc%im",
                "y64p64r@fem%im-fem",
                "yrbit@masc%invmasc",
                "y62r62b@masc%im",
                "yrib@masc%invpur",
                "ysb@masc%im",
                "yts@masc%custom(nomsg=yts|cst(nomsg)=ytst&|nomplr=ytsim|cst(nomplr)=ytsi&)",
                "y61s29964w309@masc%invmasc",
                "yt@fem%im-fem",
                "p64n64@fem%im-fem",
                "pdgwg@masc%im",
                "pirws@masc%im",
                "p56r60i@masc%im",
                "pryh@masc%invpur",
                "prq@masc%im",
                "prs@fem%endingh",
                "pswq@masc%im",
                "tsat@fem%invpur",
                "tswtn@neut%invmasc",
                "ts63d60iq@adj%im-ot",
                "tsrik@masc%im",
                "qdmt@neut%im",
                "q64dw331s298@adj%im-ot",
                "qw331l@masc%mascot",
                "qrwa@neut%invpur",
                "qwra@neut%invpur",
                "qm@masc%im",
                "qm@neut%empty",
                "qriat@neut%invpur",
                "qwm@neut%invmasc",
                "qwra@masc%invmasc",
                "qxr@masc%invmasc",
                "qtsr@adj%adj",
                "r'@neut%invpur",
                "rbh@neut%invpur",
                "r61as60it@masc%invmasc",
                "ra65s298@fem%im",
                "raswn@fem%ot",
                "rbi@masc%invmasc",
                "rbn@masc%invmasc",
                "rbta@fem%invpur",
                "rgil@adj%im-ot",
                "rw'h63@neut%invpur",
                "r'hl@fem%invpur",
                "r63y@adj%im-ot",
                "rqiy@masc%invpur",
                "rsai@neut%invpur",
                "r64s29864y@masc%im",
                "rtswn@masc%mascot",
                "s'hr@masc%im",
                "sal@neut%invmasc",
                "s29860b56y64h@neut%invpur",
                "sbyim000@neut%invpur",
                "s60in64i@neut%invpur",
                "skb@neut%invmasc",
                "sdr@masc%im",
                "swkb@neut%invmasc",
                "skibh@neut%invmasc",
                "simn@masc%imsimple",
                "s29856mw309a61l@masc%invmasc",
                "slws@adj%im-ot",
                "slis@masc%im",
                "slisi@masc%invpur",
                "s29856l65m65h@masc%empty",
                "s29861m000@masc%mascot",
                "smai@neut%invpur",
                "s29864m6351i60m000@masc%invmasc",
                "s29864m63i60m000@masc%invmasc",
                "s29864m64i60m000@masc%invmasc",
                "smy@masc%invmasc",
                "smywni@neut%invpur",
                "sms@fem%ot",
                "snh@fem%im-fem",
                "sni@neut%invpur",
                "sy@fem%endingh",
                "spr@masc%im",
                "sw309s@masc%im-ot",
                "swp@masc%im",
                "s63y58s298w309y60@masc%im",
                "srh@fem%invpur",
                "stiim@fem%invpur",
                "t56hw65m000@neut%invpur",
                "tw@masc%imsimple",
                "twr@fem%endingh",
                "tiamr@neut%invpur",
                "tklt@adj%invmasc",
                "t65hw@neut%invpur",
                "tna@neut%im",
                "trwm@fem%ot",
                "tsan@masc%im",
                "tsw@masc%im",
                "xw331b@adj%im-ot",
                "xwma@fem%endingh"
        });
    }

    private List<String> getHebDico() {
        return Arrays.asList(new String[]{
                "a@noun!invpur%1(noun)=un",
                "ab@noun!mascot%1(noun)=père",
                "abr@noun!imonly%1(noun)=membre",
                "abrhm@noun!invpur%1(noun)=abraham",
                "a58d64m64h@noun!custom(nomsg=admh|decim-nw=admtnw)%1(noun)=terre",
                "a64d64m000@noun!invmasc%1(noun)=homme",
                "awi@prep%1(prep)=oï",
                "aw331i@prep%1(prep)=oï",
                "az@prep%1(prep)=alors",
                "a'h@noun!im%1(noun)=frère",
                "a'hr@prep%1(prep)=après",
                "a63'h63r@prep%1(prep)=après",
                "a'hri@prep%1(prep)=après",
                "a63'h58r61i@prep%1(prep)=après",
                "a'hrih@prep%1(prep)=après elle",
                "a'hrim@prep%1(prep)=autres",
                "a58'h61r60im000@prep%1(prep)=autres",
                "a62'h64d@noun!empty%1(noun)=un",
                "a63'h63t@noun!empty%1(noun)=une",
                "akilt@noun!invpur%1(noun)=manger",
                "ailk000@prep%1(prep)=désormais",
                "ain@prep%1(prep)=il n'y a pas",
                "ainw@prep%1(prep)=ce n'est pas",
                "a61in000@prep%1(prep)=il n'y a pas",
                "a60is298@noun!custom(nomplr=ansim|cst(nomplr)=ansi&|dechsofit=aish|nomsg=a60is298)%1(noun)=homme",
                "ait@prep%1(prep)=il y a",
                "akl@verb!norm%1(verb)=manger",
                "akwl@verb!norm%1(verb)=manger2",
                "aim@verb!norm%1(verb)=menacer",
                "aimti@prep%1(prep)=quand",
                "aimt@prep%1(prep)=quand",
                "al@prep%1(prep)=vers",
                "a62l@prep%1(prep)=vers",
                "ala@prep%1(prep)=mais",
                "alh@prep%1(prep)=ces",
                "alhi@noun!invpur%1(prep)=dieu",
                "a61l62h@prep%1(prep)=ces",
                "aliyzr@noun!invmasc%1(noun)=éliezer",
                "alyzr@noun!invmasc%1(noun)=élazar",
                "amh@noun!custom(nomsg=amh|cst(nomsg)=amt|nomplr=a67mw331t)%1(noun)=peuple",
                "amr@verb!norm%1(verb)=dire",
                "aw331r@noun!invfem%1(noun)=flamme,feu%2(noun)=lumière",
                "awrita@noun!invpur%1(noun)=torah",
                "al65h60im000@noun!custom(nomsg=alhim|cst(nomsg)=alhi&|decim-inwplr=alhinw|decim-ik=alhik)%1(noun)=dieu",
                "am@prep%1(prep)=si",
                "a64mw331n000@noun!im%1(noun)=artisan",
                "an'hnw@prep%1(prep)=nous",
                "ani@prep%1(prep)=je",
                "a58n60i@prep%1(prep)=je",
                "anki@prep%1(prep)=je",
                "a64n65k60i@prep%1(prep)=je",
                "a58n6363'hnw309@prep%1(prep)=nous",
                "arwk@adj!adj%1(adj)=long",
                "ark@verb!norm%1(verb)=prolonger",
                "a62r62ts@noun!custom(nomplr=artswt|nomsg=a62r62ts)%1(noun)=pays%2(noun)=terre",
                "aswr@adj!im-ot%1(noun)=interdit",
                "asmwr@noun!endingh%1(noun)=veille",
                "asr@prep%1(prep)=qui",
                "a59s29862r@prep%1(prep)=qui",
                "a60s29864h@noun!custom(nomplr=nsim|cst(nomplr)=nsi&|nomsg=a60s29864h|cst(nomsg)=ast|decim-k=a60s29856t56k00064|decimplr-k=n64s29862ik00064|decimplr-h=nsihm000)%1(noun)=femme",
                "atsxrik@noun!invpur%1(noun)=il est nécessaire",
                "at@prep%1(prep)=COD",
                "a61t@prep%1(prep)=COD",
                "ati@prep%1(prep)=moi",
                "a65t60i@prep%1(prep)=moi",
                "atnw@prep%1(prep)=nous",
                "a62ts56lw331@prep%1(prep)=près de lui",
                "a63t64h@prep%1(prep)=toi",
                "ath@prep%1(prep)=toi",
                "atk@prep%1(prep)=avec toi",
                "a65t56k00064@prep%1(prep)=avec toi",
                "atkm@prep%1(prep)=vous",
                "a62t56k62m000@prep%1(prep)=vous",
                "atm@prep%1(prep)=vous%2(prep)=eux",
                "b@prep%1(prep)=dans",
                "b30558@prep%1(prep)=dans",
                "b30560@prep%1(prep)=dans",
                "b30564@prep%1(prep)=dans",
                "b56@prep%1(prep)=dans",
                "b63@prep%1(prep)=dans",
                "ba@verb!norm%1(verb)=venir",
                "bar@verb!norm%1(verb)=expliquer",
                "bdl@verb!norm%1(verb)=separer",
                "bh@prep%1(prep)=en elle",
                "b65hw@noun!invpur%1(noun)=vide",
                "bw@prep%1(prep)=en lui",
                "b305w331@prep()%1(prep)=en lui",
                "b'hr@verb!norm%1(verb)=choisir",
                "b30561in000@prep%1(prep)=entre",
                "bin000@prep%1(prep)=entre",
                "b61in000@prep%1(prep)=entre",
                "bit@noun!im%1(noun)=maison",
                "bk@prep%1(prep)=en toi",
                "b62n000@noun!im-ot%1(noun)=fils",
                "bnw@prep%1(prep)=en nous",
                "b64nw309@prep%1(prep)=en nous",
                "bytsmk@prep%1(prep)=en personne",
                "b30565q62r@noun!im%1(noun)=matin",
                "bwqr@noun!im%1(noun)=matin",
                "b60q62r@noun!im%1(noun)=matin",
                "bra@verb!norm%1(noun)=creer",
                "brk@verb!norm%1(noun)=benir",
                "b30556r60it@noun!mascot%1(noun)=alliance",
                "b56r64k64@noun!ot%1(noun)=bénédiction",
                "b30564s29964r@noun!im%1(noun)=chair",
                "bt@noun!ot2%1(noun)=fille",
                "bnwt@noun!otplr%1(noun)=filles",
                "gw@prep%1(prep)=dedans",
                "gm000@prep%1(prep)aussi%2(prep)=même%3(prep)=encore",
                "g63m000@prep%1(prep)aussi%2(prep)=même%3(prep)=encore",
                "g64dw331l@adj!im-ot%(adj)=grand",
                "gwi@noun!custom(nomplr=gwim|nomsg=gwi)%1(noun)=gentil%2(noun)=nation",
                "girsa@noun!invpur%1(noun)=variante%2(noun)=version",
                "gm'@noun!invpur%1(noun)=gémara",
                "gmlial@noun!invmasc%1(noun)=gamaliel",
                "g64n000@noun!im%1(noun)=jardin",
                "grs@verb!norm%1(verb)=declarer",
                "d@prep%1(prep)=qui",
                "dbr@verb!norm%1(verb)=parler",
                "dbri@noun!invmasc%1(noun)=paroles de",
                "dnn@verb!norm%1(verb)=discuter",
                "dp@noun!im%1(noun)=page",
                "d64b64r@noun!im%1(noun)=chose,acte%2(noun)=parole",
                "dktib@prep%1(prep)=comme dit l'écriture",
                "dla@prep%1(prep)=qui n'est pas",
                "drk@noun!im%1(noun)=voie%2(noun)=chemin",
                "dsa@noun!im%1(noun)=végétation",
                "dsa@verb!norm%1(verb)=verdir",
                "h@prep%1(prep)=le[les,la]",
                "h?q@prep%1(prep)=ainsi soit-il",
                "h59@prep%1(prep)=le[les,la]",
                "hwsyih@noun!invpur%1(noun)=hoshaya",
                "h60n61h@prep%1(prep)=voici",
                "hnh@prep%1(prep)=voici",
                "h62m000@prep%1(prep)=eux",
                "hm@prep%1(prep)=eux",
                "h6269@prep%1(prep)=le[les,la]",
                "h63@prep%1(prep)=le[les,la]",
                "h6369@prep%1(prep)=le[les,la]",
                "hwh@prep%1(prep)=Xmystère hwhX",
                "hzh@prep%1(prep)=ce",
                "hzat@prep%1(prep)=cette",
                "h63z65at@prep%1(prep)=cette",
                "hia@prep%1(prep)=elle",
                "hiinw@prep%1(prep)=ce qui signifie",
                "h61ik00056@prep%1(prep)=comment",
                "h63r@noun!im%1(noun)=montagne",
                "h64@prep%1(prep)=le[les,la]",
                "h6469@prep%1(prep)=le[les,la]",
                "h69@prep%1(prep)=le[les,la]",
                "hri@prep%1(prep)=voici",
                "hiw@verb!irrg%1(verb)=etre",
                "h61ik64l@noun!im%1(noun)=palais",
                "hwa@prep%1(prep)=lui",
                "hw309a@prep%1(prep)=lui",
                "hll@noun!invpur%1(noun)=hillel",
                "hqbh@noun!invpur%1(noun)=SaintBéniSoitIl",
                "hlk@verb!norm%1(noun)=aller",
                "hr'hiq@verb!norm%1(verb)=renvoyer%2(verb)=separer%3(verb)=eloigner",
                "w@prep%1(prep)=et",
                "w56@prep%1(prep)=et",
                "w63@prep%1(prep)=et",
                "w309@prep%1(prep)=et",
                "wkw'@prep%1(prep)=etc",
                "wtrin@adj!im-ot%1(adj)=permis",
                "z@prep%(prep)=zayin",
                "z62h@prep%(prep)=ce",
                "zh@prep%(prep)=ce",
                "zhr@noun!invpur%1(noun)=zohar",
                "zkh@verb!norm%1(verb)=gagner",
                "zkr@verb!norm%1(verb)=sesouvenir",
                "zkr2@verb!norm%1(verb)=sesouvenir",
                "zmn@noun!im%1(noun)=temps",
                "z64q61n000@noun!adj%1(adj)=ancien",
                "zw@prep%(prep)=ce",
                "zry@noun!im%1(noun)=semence",
                "zry@verb!norm%1(verb)=semer",
                "zry2@verb!norm%1(verb)=semer",
                "'hds@adj!im-ot%1(noun)=mois%2(adj)=nouveau",
                "'h65d62s@noun!im%1(noun)=mois",
                "'hwb@noun!mascot%1(noun)=dette",
                "'hwbt@noun!invpur%1(noun)=obligation",
                "'hs29862k00056@noun!im-fem%1(noun)=arrêt,pause%2(noun)=sombre,obscur%3(noun)=obscurité",
                "'h63k63m000@adj!adj%1(noun)=sage",
                "'hlb@noun!im%1(noun)=lait%2(noun)=graisse",
                "'h58lw331m000@noun!mascot%1(noun)=rêve",
                "'hmh@noun!invfem%1(noun)=soleil",
                "'htm@verb!norm%1(verb)=sceller%2(verb)=signer%3(verb)=finir",
                "'htswt@noun!invmasc%1(noun)=milieu de la nuit",
                "xym@noun!im%1(noun)=goût",
                "xrpwn000@noun!invpur%1(noun)=tarfon",
                "ibsh@noun!invpur%1(noun)=sec",
                "i63d@noun!im%1(noun)=main",
                "idy@verb!norm%1(verb)=connaitre",
                "iw331m000@noun!custom(nomplr=imim000|cst(nomplr)=imi|cst(nomsg)=iwm&|nomsg=iwm000)%1(noun)=jour",
                "ih@noun!invmasc%1(noun)=le seigneur%1(noun)=YH(VH)",
                "ihwh@noun!invmasc%1(noun)=le seigneur%1(noun)=YHVH",
                "ihwswy@noun!invmasc%1(noun)=yehoshouah",
                "ikl@verb!norm%1(verb)=pouvoir",
                "iwsp@noun!invpur%1(noun)=joseph",
                "ilqwx@noun!invpur%1(noun)=yalkout",
                "i61y58q65b@noun!invmasc%1(noun)=jacob",
                "itsia@noun!endingh%1(noun)=sortie%2(noun)=exode",
                "i60r56a63t@noun!imonly%1(noun)=crainte",
                "ird@verb!norm%1(verb)=descendre",
                "i56rw309s29864l63i60m000@noun!invmasc%1(noun)=jérusalem",
                "is@prep%(prep)=il y a",
                "isb@verb!norm%1(noun)=demeurer",
                "i56s298w309y64@noun!ot%1(noun)=salut",
                "i56s298w309y64h@noun!invpur%1(noun)=josué",
                "i64s29863r@adj!im-ot%1(adj)=correct%2(adj)=honnête",
                "i60s29956r64a61l@noun!invmasc%1(noun)=israël",
                "itsa@verb!norm%1(verb)=sortir",
                "itsa2@verb!norm%1(verb)=sortir",
                "its'hq@noun!invpur%1(noun)=isaac",
                "k@prep%1(prep)=comme",
                "k56@prep%1(prep)=comme",
                "k31559@prep%1(prep)=comme",
                "k31560@prep%1(prep)=comme",
                "k31563@prep%1(prep)=comme",
                "k31564@prep%1(prep)=comme",
                "k3156369@prep%1(prep)=comme",
                "k3156469@prep%1(prep)=comme",
                "kan@prep%1(prep)=ici",
                "kbs@verb!norm%1(verb)=conquerir",
                "kd@prep%1(prep)=quand",
                "kdai@noun!invpur%1(noun)=il faut",
                "kdi@prep%1(prep)=quand%2(prep)=afin de%3(prep)=pour",
                "kdmprs@prep%1(prep)=comme interprété",
                "khn@noun!im%1(noun)=prêtre",
                "kwhn@noun!im%1(noun)=prêtre",
                "k31556n63y63n000@noun!invpur%1(noun)=canaan",
                "kw331k56b61@noun!im%1(noun)=étoile",
                "k'h@noun!mascot%1(noun)=puissance",
                "ki@prep%1(prep)=car%2(prep)=lorsque%3(prep)=pour que%4(prep)=que",
                "k315i@prep%1(prep)=car%2(prep)=lorsque%3(prep)=pour que%4(prep)=que",
                "k31560i@prep%1(prep)=car%2(prep)=lorsque%3(prep)=pour que%4(prep)=que",
                "kl@prep%1(prep)=tout",
                "k31564l@prep%1(prep)=tout",
                "k64l@prep%1(prep)=tout",
                "kmw@prep%1(prep)=comme",
                "kmwk@prep%1(prep)=comme toi",
                "k64mw331k00064@prep%1(prep)=comme toi",
                "kn@prep%1(prep)=oui%2(prep)=ainsi",
                "kns@verb!norm%1(verb)=entrer",
                "krt@verb!norm%1(verb)=couper",
                "ksh@verb!norm%1(verb)=couvrir",
                "krti@noun!invmasc%1(noun)=poireau",
                "ktib@noun!invpur%1(noun)=écriture",
                "ksofit@prep%1(prep)=de-toi",
                "l@prep%1(prep)=à",
                "l56@prep%1(prep)=à",
                "la@prep%1(prep)=ne",
                "law@prep%1(prep)=ne",
                "l65a@prep%1(prep)=ne",
                "lbd@prep%1(prep)=seulement",
                "lbn@noun!invmasc%1(noun)=blanc%2(noun)=laban",
                "l63i56l64h@noun!custom(nomplr=lilwt|cst(nomsg)=lil&|nomsg=lilh)%1(noun)=nuit",
                "l64i56l64h@noun!custom(nomplr=lilwt|cst(nomsg)=lil&|nomsg=lilh)%1(noun)=nuit",
                "lmd@verb!norm%1(verb)=enseigner",
                "lmh@prep%1(prep)=pourquoi",
                "lminhw@prep%1(prep)=selon son type",
                "lpikk@prep%1(prep)=par conséquent",
                "lw@prep%1(prep)=pour lui",
                "lw331@prep%1(prep)=pour lui",
                "l'hm@noun!invpur%1(noun)=pain",
                "li@prep%1(prep)=à moi",
                "l56k00064@prep%1(prep)=à toi",
                "lk@prep%1(prep)=à toi",
                "lkm@prep%1(prep)=à vous",
                "lkt@noun!invmasc%1(noun)=départ",
                "lpninw@prep%1(prep)=devant nous",
                "lpnih@prep%1(prep)=devant elle",
                "lq'h@verb!norm%1(verb)=prendre",
                "lsxim000@noun!invpur%1(noun)=brigand",
                "lsxin000@noun!invpur%1(noun)=brigand",
                "lswn@noun!ot2%1(noun)=langue",
                "m@prep%1(prep)=de",
                "m60@prep%1(prep)=de",
                "mamr@noun!im%1(noun)=expression%2(noun)=discours",
                "mad@prep%1(prep)=très",
                "m56a65d@prep%1(prep)=très",
                "m64qw331m000@noun!mascot%1(noun)=lieu",
                "mbdil@noun!invpur%1(noun)=séparation",
                "m60d56b30564r@noun!imdir%1(noun)=désert",
                "mdrs@noun!im%1(noun)=midrash",
                "mh@prep%1(prep)=quoi",
                "m63h@prep%1(prep)=quoi",
                "m64h@prep%1(prep)=quoi",
                "mi@prep%1(prep)=qui",
                "m60i@prep%1(prep)=qui",
                "m64i60m000@noun!invpur%1(noun)=eaux",
                "min@noun!im%1(noun)=hérétique%2(noun)=genre grammatical%3(noun)=acte sexuel%4(noun)=genre sexuel%5(noun)=espèce%6(noun)=type",
                "mkan000@prep%1(prep)=de là",
                "mkiwn000@prep%1(prep)=puisque",
                "mktb@noun!im%1(noun)=lettre",
                "mlak@noun!im%1(noun)=messager%2(noun)=ange",
                "m62l62k00056@noun!im%1(noun)=roi",
                "mmk@prep%1(prep)=depuis toi",
                "mmni@prep%1(prep)=depuis moi",
                "mn@prep%1(prep)=de",
                "m60n000@prep%1(prep)=de",
                "m00060n000@prep%1(prep)=de",
                "mqwh@noun!invpur%1(noun)=rassemblement des eaux",
                "mqr@noun!mascendingh%1(noun)=accident%2(noun)=occurence",
                "mrgl@noun!im%1(noun)=explorateur%2(noun)=espion",
                "mys@noun!endingh%1(noun)=acte%2(noun)=oeuvre",
                "mswm@prep%1(prep)=parce que",
                "mtsw@noun!ot%1(noun)=commandement",
                "m60ts56r63i60m000@noun!invmasc%1(noun)=égypte",
                "mtsriim000@noun!invmasc%1(noun)=égypte",
                "ms29862h@noun!invmasc%1(noun)=moïse",
                "msli@noun!invpur%1(noun)=livre des proverbes",
                "msnh@noun!invpur%1(noun)=mishna",
                "msikir@noun!invmasc%1(noun)=meshearir%2(noun)=lorsque l'on sait reconnaître",
                "msl@verb!norm%1(verb)=diriger",
                "msth@noun!invmasc%1(noun)=fête%2(noun)=banquet",
                "m63t56'h60il@noun!invpur%1(noun)=début",
                "mti@prep%1(prep)=quand",
                "mtsa@verb!norm%1(verb)=etretrouve",
                "nakl@noun!im%1(noun)=destiné à être mangé",
                "n56h63r@noun!mascot%1(noun)=rivière",
                "n64y63r@noun!im%1(noun)=jeune homme%2(noun)=garçon",
                "nw@prep%1(prep)=nous",
                "n64b60ia@noun!im-ot%1(noun)=prophète",
                "ngd@verb!norm%1(verb)=dire",
                "ngy@verb!norm%1(verb)=arriver",
                "n'hl@verb!norm%1(verb)=heriter",
                "nkns@verb!norm%1(verb)=entrer",
                "nxh@verb!norm%1(verb)=incliner",
                "nxh2@verb!norm%1(verb)=incliner",
                "nm@verb!norm%1(verb)=dormir",
                "nyr@verb!norm%1(verb)=braire",
                "ntn@verb!norm%1(verb)=donner",
                "ntn2@verb!norm%1(verb)=donner",
                "nts@noun!im%1(noun)=faucon%2(noun)=floraison",
                "pdgwg@noun!im%1(noun)=pédagogue",
                "pirws@noun!im%1(noun)=commentaire",
                "p64n64@noun!im-fem%1(noun)=face",
                "p56r60i@noun!im%1(noun)=fruit",
                "pryh@noun!invpur%1(noun)=pharaon",
                "prs@noun!endingh%1(noun)=parasha",
                "prq@noun!im%1(noun)=section",
                "psd@verb!norm%1(verb)=perdre",
                "pswq@noun!im%1(noun)=verset%2(noun)=passage",
                "pt'h@verb!norm%1(verb)=ouvrir",
                "tsat@noun!invpur%1(noun)=sortie",
                "tswh@verb!norm%1(verb)=ordonner",
                "ts63d60iq@adj!im-ot%1(adj)=juste",
                "tsny@verb!norm%1(verb)=cacher",
                "qai@prep%1(prep)=(expre intraduisible :fait référence à)",
                "qdmt@noun!im%1(noun)=introduction",
                "q64dw331s298@adj!im-ot%1(noun)=saint",
                "qwh@verb!norm%1(verb)=couler",
                "qw331l@noun!mascot%1(noun)=voix",
                "qwm@noun!invmasc%1(noun)=lever",
                "q'hn@verb!norm%1(verb)=tirer",
                "qwra@noun!invpur%1(noun)=lecteur",
                "qm@noun!empty%1(noun)=lever",
                "qm@noun!im%1(noun)=ennemi",
                "qmn000@prep%1(prep)=avant",
                "qmn@prep%1(prep)=avant",
                "qra@verb!norm%1(verb)=crier%2(verb)=lire%3(verb)=appeler",
                "qrwa@noun!invmasc%1(noun)=lecture",
                "qriat@noun!invpur%1(noun)=appel de",
                "qxr@noun!invmasc%1(noun)=encens%2(noun)=combustion",
                "qtsr@adj!adj%1(adj)=court",
                "yiin@noun!invpur%1(noun)=voir",
                "y63in@noun!im%1(noun)=oeil",
                "y60ir@noun!custom(nomplr=yrim|cst(nomplr)=yri&dir(nomsg)=yirh|nomsg=y60ir)%1(noun)=ville",
                "ybir@noun!ot%1(noun)=crime%2(noun)=péché%3(noun)=transgression",
                "yd@prep%1(prep)=jusqu'à",
                "y63d@prep%1(prep)=jusqu'à",
                "ywlm@noun!im%1(noun)=globe%2(noun)=terre%2(noun)=monde",
                "yzrih@noun!invpur%1(noun)=azarya",
                "yl@prep%1(prep)=en faveur de%2(prep)=au dessus de%3(noun)=sur",
                "y63l@prep%1(prep)=en faveur de%2(prep)=au dessus de%3(noun)=sur",
                "ybd@verb!norm%1(verb)=servir",
                "ybr@verb!norm%1(verb)=traverser",
                "ylh@verb!norm%1(verb)=grandir%2(verb)=couter%3(verb)=selever",
                "ymd@verb!norm%1(verb)=lever",
                "y64p64r@noun!im-fem%1(noun)=jeune daim%2(noun)=gris,cendré%3(noun)=poussière",
                "ym@prep%1(prep)=avec",
                "y60m@prep%1(prep)=avec",
                "ym000@prep%1(prep)=avec",
                "y60m000@prep%1(prep)=avec",
                "ymwd@noun!im%1(noun)=pilier%2(noun)=colonne%3(noun)=page",
                "ymm000@prep%1(prep)=avec eux",
                "y60m64m000@prep%1(prep)=avec eux",
                "y64m000@noun!im%1(noun)=peuple",
                "ymnw@prep%1(prep)=avec nous",
                "ynin@noun!im%1(noun)=sujet",
                "ypprep@prep%1(prep)=selon",
                "yrbit@noun!invmasc%1(noun)=prière du soir",
                "y62r62b@noun!im%1(noun)=soir",
                "yrib@noun!invpur%1(noun)=soir",
                "y61s29964w309@noun!invmasc%1(noun)=esav",
                "ysh@verb!norm%1(verb)=faire",
                "ysb@noun!im%1(noun)=herbe",
                "yt@noun!im%1(noun)=temps",
                "yts@noun!custom(nomsg=yts|cst(nomsg)=ytst&|nomplr=ytsim|cst(nomplr)=ytsi&)%1(noun)=arbre",
                "ytsmi@prep%1(prep)=moi-même",
                "tswtn@noun!invmasc%1(noun)=précepte",
                "tsrik@noun!im%1(noun)=besoin",
                "r'@noun!invpur%1(noun)=rabbi",
                "ra65s298@noun!im%1(noun)=tête",
                "r61as60it@noun!invmasc%1(noun)=commencement",
                "raswn@noun!ot%1(noun)=commencement%2(adj)=première",
                "rah@verb!norm%1(noun)=apparaitre%2(verb)=voir",
                "rbh@noun!invpur%1(noun)=grand",
                "rbi@noun!invmasc%1(noun)=rabbi",
                "rbn@noun!invmasc%1(noun)=rabban",
                "rbta@noun!invpur%1(noun)=important",
                "rgil@adj!im-ot%1(adj)=habituel",
                "rwb@prep%1(prep)=la plupart",
                "rw'h63@noun!invpur%1(noun)=esprit%2(noun)=souffle",
                "r63y@noun!im-ot%1(noun)=mauvais",
                "r'hl@noun!invpur%1(noun)=rachel",
                "r'hp@verb!norm%1(verb)=planer",
                "rqiy@noun!invpur%1(noun)=firmament",
                "rsai@noun!invpur%1(noun)=autorisé",
                "r64s29864y@noun!im%1(noun)=impie",
                "rtswn@noun!mascot%1(noun)=volonté",
                "s@prep%1(prep)=que",
                "sal@noun!invmasc%1(noun)=shéol",
                "s29860b56y64h@noun!invpur%1(noun)=sept",
                "sbyim000@noun!invpur%1(noun)=soixante-dix",
                "sdr@noun!im%1(noun)=ordre",
                "s'hr@noun!im%1(noun)=aube",
                "swkb@noun!invmasc%1(noun)=coucher",
                "skibh@noun!invmasc%1(noun)=coucher",
                "simn@noun!imsimple%1(noun)=signe",
                "s60in64i@noun!invpur%1(noun)=sinai",
                "skb@noun!invmasc%1(noun)=coucher",
                "skb@verb!norm%1(verb)=secoucher",
                "sl@prep%1(prep)=de",
                "sw309s@noun!im-ot%1(noun)=cheval",
                "skn@verb!norm%1(verb)=mettreendanger",
                "skn@prep%1(prep)=que ainsi",
                "slws@adj!im-ot%1(adj)=troisième",
                "slis@noun!im%1(noun)=tiers",
                "slisi@noun!invpur%1(noun)=troisième",
                "sl'h@verb!norm%1(verb)=envoyer",
                "s29856l65m65h@noun!invpur%1(noun)=salomon",
                "s29856mw309a61l@noun!invpur%1(noun)=samuel",
                "s29861m000@noun!mascot%1(noun)=nom",
                "smai@noun!invpur%1(noun)=shammai",
                "s29864m6351i60m000@noun!invmasc%1(noun)=ciels",
                "s29864m63i60m000@noun!invmasc%1(noun)=ciels",
                "s29864m64i60m000@noun!invmasc%1(noun)=ciels",
                "smy@noun!invmasc%1(noun)=shema",
                "smy@verb!norm%1(verb)=entendre",
                "smywni@noun!invpur%1(noun)=shimoni",
                "smr@verb!norm%1(verb)=garder",
                "sms@noun!ot%1(noun)=chandelle%2(noun)=serviteur%3(noun)=soleil",
                "snh@noun!im-fem%1(noun)=année",
                "sni@noun!invpur%1(noun)=second",
                "spr@noun!im%1(noun)=livre",
                "sy@noun!endingh%1(noun)=heure",
                "swp@noun!im%1(noun)=fin",
                "s63y58s298w309y60@noun!im%1(noun)=divertissement",
                "srh@noun!invpur%1(noun)=sarah",
                "stiim@noun!invpur%1(noun)=deux",
                "t56hw65m000@noun!invpur%1(noun)=abîme",
                "t65hw@noun!invpur%1(noun)=informe",
                "tw@noun!imsimple%1(noun)=note%2(noun)=marque%3(noun)=lettre",
                "twk000@prep%1(prep)=intérieur",
                "twr@noun!endingh%1(noun)=torah",
                "t'ht@prep%1(prep)=à la place de%2(prep)=sous",
                "t'hl@verb!norm%1(verb)=commencer",
                "t63'h63t@prep%1(prep)=à la place de%2(prep)=sous",
                "tiamr@noun!invpur%1(noun)=il a été dit",
                "tklt@adj!invmasc%1(adj)=bleu",
                "tna@noun!im%1(noun)=tannah",
                "trwm@noun!ot%1(noun)=térouma",
                "tsan@noun!im%1(noun)=troupeau",
                "tsw@noun!im%1(noun)=ordre%2(noun)=décret",
                "xw331b@noun!im-ot%1(noun)=bon",
                "xbl@verb!norm%1(verb)=immerger",
                "xhr@verb!norm%1(verb)=etrepurifie",
                "xwma@noun!endingh%1(noun)=impureté",
                "xma@verb!norm%1(verb)=profaner",
                "xxdexx@prep%1(prep)=de",
                "xxdirectionalhexx@prep%1(prep)=vers",
                "xxhexx@prep%1(prep)=à elle",
                "xxixx@prep%1(prep)=de moi",
                "xxihxx@prep%1(prep)=à elle",
                "xxiwxx@prep%1(prep)=à lui",
                "xxmxx@prep%1(prep)=à eux",
                "xxnxx@prep%1(prep)=leur",
                "xxnwxx@prep%1(prep)=à nous",
                "xxinwxx@prep%1(prep)=à nous",
                "xxtkxx@prep%1(prep)=à toi",
                "xxtnxx@prep%1(prep)=à eux",
                "xxtixx@prep%1(prep)=de moi",
                "xx?xx@prep%1(prep)=\"",
                "wavend@prep%1(prep)=de lui"
        });
    }

    private List<String> getVerbs() {
        return Arrays.asList(new String[]{
                "akl,,[paal],(AIP%leadingrootletter%a*a64@AIP%secondletterroot%k*k64@AIP%alternateaccentuation(5:9)%k64*k56@IPR%substitute%akl*awkl*0@ARAIPR%substitute%akl*awkl*0)",
                "akwl,,[paal]",
                "amr,,[paal],(AIP%leadingrootletter%a*a64@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@AIP%alternateaccentuation(5)%r*r64@ARAIPR%substitute%amr*awmr*0@IPR%substitute%amr*awmr*0@ARAIPR%substitute%amr*awmr*0)",
                "ark,harik,[hiphil]",
                "ba,,[paal2],(AIP%leadingrootletter%b*b30564@AIP%alternateaccentuation(5)%a*a64))",
                "bar,,[piel]",
                "bdl,,[hiphil],(HIFPER%leadingrootletter%h*h60@HIFPER%secondletterroot%b*b56@HIFPER%alternateaccentuation(4:5:9)%d*d60i",
                "b'hr,,[paal],(IPR%substitute%b'h*bw'h*0)",
                "bra,,[paal],(AIP%leadingrootletter%*b30564@AIP%secondletterroot%r*r64@AIP%secondletterrootexception(5:9)%r*r56@AIP%alternateaccentuation%b30564*b64@IPR%substitute%br*bwr*0",
                "brk,,[binyan]",
                "grs,,[paal],(ARAIPR%substitute%grs*gwrs*0)",
                "dbr,,[piel],(PIEPER%leadingrootletter%d*d60@PIEPER%secondletterroot%b*b63@PIEPER%alternateaccentuation(4)%b63*b30562@PIEPER%alternateaccentuation(5:9)%b63*b30556)",
                "dnn,,[paal],(AIP%substitute%nn*n*0)",
                "dsa,,[hiphil]",
                "hiw@IRREGULAR%[AIP]=[hiiti,hiit,h64i64h|h64i56t64h,hiinw,,h64iw309]%[AIF]=[ahih,,ihi|ihih,nhih,,ihiw|thiinh]%[PALFUT]=[,,ihi,,,,]%[AIMP]=[,,,hiw,]",
                "hlk,,[paal],(AIP%leadingrootletter%h*h64@AIP%secondletterroot%l*l63@AIP%sofitrootletter%k3*k000@AIP%sofitaccentuedletter%k000*k00056@IPR%substitute%hl*hwl*0)",
                "psd,hpsid,[hiphil],(HIFPER%substitute(4:5:9)%hpsd*hpsid)",
                "hr'hiq,hr'hiq,[paal]",
                "'htm,'htwm,[paal]",
                "xbl,,[paal]",
                "xhr,hixhr,[nifal]",
                "xma,,[nifal]",
                "zkh,,[paal],(AIP%substitute%kh*ki*0)",
                "zkr,,[paal],(IPR%substitute%zk*zwk*0)",
                "zkr2,,[binyanhifil],(ARAPRE%substitute%kr2*kr*0@ARAPRE%substitute%kr*kir*0)",
                "zry,,[paal],(IPR%substitute%zr*zwr*0)",
                "zry2,,[hiphil],(HIFPART%substitute%y2*iy*0)",
                "idy,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%d*d63@AIP%alternateaccentuation(5:9)%d63*d56@AIP%alternateaccentuation(5)%y*y64@IPR%substitute%id*iwd*0)",
                "ikl,wkl,[paal],(ARAIPR%substitute%ikl*ikwl*0)",
                "ird,,[paal],(IPR%substitute%ir*iwr*0@ARAIPR%substitute%ird*iwrd*0)",
                "isb,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%s*s29863@AIP%alternateaccentuation(5:9)%s29863*s29856@AIP%alternateaccentuation(5)%b*b64@IPR%substitute%is*iws*0)",
                "itsa,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%ts*ts64@AIP%alternateaccentuation(5:9)%ts64*ts56@IPR%substitute%it*iwt*0)",
                "itsa2,,[binyanhufal],(HIFPART%substitute%sa2*sa*0@BINHUPER%substitute%sa2*sa*0@BINHUPER%substitute%its*ts*0@BINHUFUT%substitute%sa2*sa*0@BINHUFUT%substitute%its*ts*0)",
                "kbs,,[paal]",
                "kns,,[nifal]",
                "krt,,[paal],(AIP%leadingrootletter%k*k31564@AIP%secondletterroot%r*r63@IPR%substitute%kr*kwr*0@ARAIPR%substitute%kr*kwr*0",
                "ksh,,[binyan],(BINPART%substitute%ksh*ks*0@BINPART%alternateaccentuation%ks*kws*0)",
                "lmd,,[nifal]",
                "lq'h,,[paal],(AIP%leadingrootletter%l*l64@AIP%secondletterroot%q*q63@AIP%alternateaccentuation(5:9)%q63*q56@AIP%alternateaccentuation(5)%'h*'h64@IPR%substitute%lq*lwq*0)",
                "msl,,[paal],(AIP%leadingrootletter%m*m64@AIP%secondletterroot%s*s29863@IPR%substitute%ms*mws*0)",
                "mtsa,,[nifal]",
                "ngd,,[binyanhifil],(BINHIPER%substitute(1:2:3:6:7:8:9)%ngd*gd*0@BINHIPER%substitute(4:5)%ngd*gid*0@BINHIPER%substitute(4)%hgidh*hgid*0@BINHIPER%substitute(5)%hgidth*hgidh*0)",
                "ngy,,[hiphil],(HIFPER%substitute(1:2:3:6:7:8)%ng*g*0@HIFPER%substitute(4:5:9)%ng*gi*0)",
                "n'hl,,[paal],(IPR%substitute%n'h*nw'h*0)",
                "nkns,,[mishna]",
                "nm,,[paal]",
                "nxh,,[paal2],(AIF%substitute%nxh*xh*0@AIF%substitute(3:6:7:8:9)%xh*x*0)",
                "nxh2,,[binyanhifil],(BINHIPER%substitute(1:2:3:6:7:8)%nxh2*xi*0@BINHIPER%substitute(4:5:9)%nxh2*x*0)",
                "ntn,tt,[paal],(AIP%leadingrootletter%n*n64@AIP%secondletterroot%t*t63@AIP%sofitrootletter%n3*n000@AIP%alternateaccentuation(5:9)%t63*t56@AIP%alternateaccentuation(5)%56n*56n64@IPR%substitute%nt*nwt*0)",
                "ntn2,,[nifal],(AIMP%substitute%ntn2*ntn*0)",
                "nyr,,[paal]",
                "ybd,,[paal],(IPR%substitute%yb*ywb*0)",
                "ybr,,[paal]",
                "ylh,,[paal],(IPR%substitute%yl*ywl*0@ARAIPR%substitute%ylh*ywlh*0)",
                "ymd,,[paal],(ARAIPR%substitute%ym*ywm*0@AIF%substitute%ymd*ymwd*0)",
                "ysh,yswt,[paal],(AIP%leadingrootletter%y*y64@AIP%secondletterroot%s*s29964@AIPSHORT%substitute%yshzz*iys*0@IPR%substitute%ys*yws*0)",
                "pt'h,,[paal],(IPR%substitute%pt*pwt*0)",
                "tswh,,[hitaip],(HITAIP%substitute%tswh*tsxw*0)",
                "qwh,,[nifal],(NIFAIF%substitute%qwh*qw*0)",
                "q'hn,,[paal]",
                "qra,qrwt,[paal],(AIP%leadingrootletter%q*q64@AIP%secondletterroot%r*r64@AIP%alternateaccentuation%q64*q6469@AIP%alternateaccentuation(5:9)%r64*r56@AIP%alternateaccentuation(5)%a*a64@AIP%substitute(1:2:3:6:7:8)%qra*qri@ARAIPR%substitute%ra*wr*0@AIF%substitute(9)%qra*qr*0@IPR%substitute%qr*qwr*0@IPR%alternateaccentuation(8:9)%qwr*qr*0)",
                "rah,,[paal],(AIP%leadingrootletter%r*r64@AIP%secondletterroot%a*a64@AIP%alternateaccentuation(9)%a64*a@AIP%deleteletter(9)%h@CONVFUT%deleteletter%h@IPR%substitute%ra*rwa*0",
                "r'hp,,[binyan],(BINPRE%leadingrootletter%m*m56@BINPRE%secondletterroot%r*r63@BINPRE%alternateaccentuation%'h*'h62@BINPRE%alternateaccentuation%p*p62",
                "skb,,[paal],(ARAIPR%substitute%sk*swk*0@ARAPAPR%substitute%sk*ski*0)",
                "skn,,[binyan],(BINPER%substitute%sk*sik*0)",
                "sl'h,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%l*l63@IPR%substitute%sl*swl*0)",
                "smy,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@IPR%substitute%sm*swm*0@ARAIPR%substitute%smy*swmy*0",
                "smr,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@IPR%substitute%sm*swm*0",
                "t'hl,ht'hil,[hiphil]",
                "tsny,,[hiphil],(HIFPART%alternateaccentuation%mts*mwts*0)"
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
        hebrewConjugationDefinitionsMap.put("hitaip", getHitpaelDefinition());
        hebrewConjugationDefinitionsMap.put("nifal", getNifalDefinition());
        return hebrewConjugationDefinitionsMap;
    }

    private List<String> getNifalDefinition() {
        return Arrays.asList(new String[]{
                "NIFAIP=>*n*ti,*n*t,*n*@|*n*h,*n*nw,*n*tm|*n*tn,*n*w",
                "NIFAIF=>*a*h,*t*h|*t*i,*i*h|*t*h,*n*h,*t*w|*t*inh,*i*w|*t*inh",
                "IPR=>*n*@|*n*t,*n*@|*n*t,*n*@|*n*t,*n*im|*n*wt,*n*im|*n*wt,*n*im|*n*wt",
                "AIMP=>*h*@,*h*i,*h*w,*h*h"
        });
    }

    private List<String> getHitpaelDefinition() {
        return Arrays.asList(new String[]{
                "HITAIP=>*n*iti,*n*it,*n*h|*n*th,*n*inw,*n*itm|*n*itn,*n*w"
        });
    }

    private List<String> getPielDefinition() {
        return Arrays.asList(new String[]{
                "PIEPER=>ti,t|t,|h,nw,tm|tn,w309",
                "PIEPRE=>*m*@|*m*t,*m*@|*m*t,*m*@|*m*t,*m*im|*m*wt,*m*im|*m*wt,*m*im|*m*wt"
        });
    }

    private List<String> getPaalDefinition() {
        return Arrays.asList(new String[]{
                "AIP=>ti,t|t,|h,nw,tm|tn,w309",
                "AIPSHORT=>x-,x-,zz,x-,x-,x-",
                "ARAIPR=>x-,x-,,x-,x-,im000|in000",
                "ARAPAPR=>*d*h",
                "AIF=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*nh,*i*w|*t*nh",
                "CONVFUT=>x-,x-,*i*@,x-,x-,x-",
                "NIFALAIP=>*n*ti,*n*t,*n*@|*n*h,*n*nw,*n*tm|*n*tn,*n*w",
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
        return Arrays.asList(new String[]{
                "HIFPER=>*h*ti,*h*t|*h*t,*h*@|*h*h,*h*nw309,*h*tm|*h*tn,*h*w309",
                "HIFFUT=>*a*@,*t*@|*t*i,*i*@|*t*@,*n*@,*t*w|*t*nh,*i*w|*t*nh",
                "HIFPART=>*m*@|*m*t,*m*im|*m*wt"
        });
    }

    private List<String> getBinyalHifilDefinition() {
        return Arrays.asList(new String[]{
                "BINHIPRE=>*m*@|*m*h,*m*@|*m*h,*m*@|*m*h,*m*im|*m*wt,*m*im|*m*wt,*m*im|*m*wt",
                "ARAPRE=>x-,x-,x-,x-,x-,*m*in000",
                "BINHIPER=>*h*ti,*h*t|*h*t,*h*h|*h*th,*h*nw309,*h*tm|*h*tn,*h*w309"
        });
    }

    private List<String> getBinyalHufalDefinition() {
        return Arrays.asList(new String[]{
                "BINHUPER=>*h*ti,*h*t|*h*t,*h*a|*h*ah,*h*nw309,*h*tm|*h*tn,*h*aw309",
                "BINHUFUT=>*aw*@,*tw*@|*tw*i,*iw*@|*tw*@,*nw*@,*tw*w|*tw*nh,*iw*w|*tw*nh"
        });
    }

    private List<String> getBinyalPielDefinition() {
        return Arrays.asList(new String[]{
                "BINPER=>*@*ti,*@*t,*@*@|*@*h,*@*nw309,*@*m000|*@*n000,*@*w309",
                "BINPRE=>*m*@|*m*t,*m*@|*m*t,*m*@|*m*t,*m*im000|*m*wt,*m*im000|*m*wt,*m*im000|*m*wt",
                "BINPART=>*m*h|*m*it,*m*im|*m*wt"
        });
    }

    private List<String> getMishnaDefinition() {
        return Arrays.asList(new String[]{
                "IPR=>-,-,-,-,-,in000"
        });
    }

    private List<String> getPrepositions() {

        return Arrays.asList(new String[]{
                "awi@prep()",
                "aw331i@prep()",
                "az@prep()",
                "a'hr@prep()",
                "a63'h63r@prep()",
                "a'hri@prep()",
                "a63'h58r61i@prep()",
                "a'hrih@prep()",
                "a'hrim@prep()",
                "a58'h61r60im000@prep()",
                "ailk000@prep()",
                "aimti@prep()",
                "aimt@prep()",
                "ain@prep()",
                "ainw@prep()",
                "ait@prep()",
                "a61in000@prep()",
                "al@prep()%[eraseFollowingMinus]",
                "a62l@prep()%[eraseFollowingMinus]",
                "ala@prep()",
                "alh@prep()",
                "a61l62h@prep()",
                "am@prep()",
                "an'hnw@prep()",
                "a58n6363'hnw309@prep()",
                "ani@prep()",
                "a58n60i@prep()",
                "anki@prep()",
                "a64n65k60i@prep()",
                "asr@prep()",
                "a59s29862r@prep()",
                "at@prep()",
                "atk@prep()",
                "a65t56k00064@prep()",
                "ati@prep()",
                "a65t60i@prep()",
                "atnw@prep()",
                "a62ts56lw331@prep()",
                "a61t@prep()",
                "a63t64h@prep()",
                "ath@prep()",
                "atkm@prep()",
                "a62t56k62m000@prep()",
                "atm@prep()",
                "b@prep()",
                "b30558@prep()",
                "b30560@prep()",
                "b30564@prep()",
                "b56@prep()",
                "b63@prep()",
                "bk@prep()",
                "bh@prep()",
                "bw@prep()",
                "b305w331@prep()",
                "b30561in000@prep()",
                "bin000@prep()",
                "b61in000@prep()",
                "bnw@prep()",
                "b64nw309@prep()",
                "bytsmk@prep()",
                "gw@prep()",
                "gm000@prep()",
                "g63m000@prep()",
                "d@prep()",
                "dktib@prep()",
                "dla@prep()",
                "h@prep()",
                "h?q@prep()",
                "h59@prep()",
                "h6269@prep()",
                "h63@prep()",
                "h6369@prep()",
                "h64@prep()",
                "h6469@prep()",
                "h69@prep()",
                "hwa@prep()",
                "hw309a@prep()",
                "hwh@prep()",
                "h61ik00056@prep()",
                "hzh@prep()",
                "hzat@prep()",
                "h63z65at@prep()",
                "hia@prep()",
                "hiinw@prep()",
                "h60n61h@prep()",
                "hnh@prep()",
                "h62m000@prep()",
                "hm@prep()",
                "hri@prep()",
                "w@prep()%[wawConversiveForFutureAndPastVerbs]",
                "w63@prep()%[wawConversiveForFutureAndPastVerbs]",
                "w56@prep()%[wawConversiveForFutureAndPastVerbs]",
                "w309@prep()%[wawConversiveForFutureAndPastVerbs]",
                "wkw'@prep()",
                "z@prep()",
                "z62h@prep()",
                "zh@prep()",
                "zw@prep()",
                "is@prep()",
                "k@prep()",
                "k56@prep()",
                "k31559@prep()",
                "k31560@prep()",
                "k31563@prep()",
                "k31564@prep()",
                "k3156369@prep()",
                "k3156469@prep()",
                "kan@prep()",
                "kd@prep()",
                "kdi@prep()",
                "kdmprs@prep()",
                "ki@prep()",
                "k31560i@prep()",
                "k315i@prep()",
                "kl@prep()",
                "k31564l@prep()",
                "k64l@prep()",
                "kmw@prep()",
                "kmwk@prep()",
                "k64mw331k00064@prep()",
                "kn@prep()",
                "ksofit@prep()",
                "l@prep()",
                "l56@prep()",
                "la@prep()",
                "l65a@prep()",
                "law@prep()",
                "lbd@prep()",
                "lw331@prep()",
                "lw@prep()",
                "li@prep()",
                "l56k00064@prep()",
                "lk@prep()",
                "lkm@prep()",
                "lmh@prep()",
                "lminhw@prep()",
                "lpikk@prep()",
                "lpninw@prep()",
                "lpnih@prep()",
                "m@prep()",
                "m60@prep()",
                "mad@prep()",
                "m56a65d@prep()",
                "mh@prep()",
                "m63h@prep()",
                "m64h@prep()",
                "mi@prep()",
                "m60i@prep()",
                "mkan000@prep()",
                "mkiwn000@prep()",
                "mmk@prep()",
                "mmni@prep()",
                "mn@prep()",
                "m60n000@prep()",
                "m00060n000@prep()",
                "mti@prep()",
                "mswm@prep()",
                "nw@prep()",
                "yd@prep()",
                "y63d@prep()",
                "yl@prep()",
                "y63l@prep()",
                "ym@prep()",
                "y60m@prep()",
                "ym000@prep()",
                "y60m000@prep()",
                "ymm000@prep()",
                "ymnw@prep()",
                "y60m64m000@prep()",
                "ypprep@prep()",
                "ytsmi@prep()",
                "qai@prep()",
                "qmn000@prep()",
                "qmn@prep()",
                "rwb@prep()",
                "s@prep()",
                "skn@prep()",
                "sl@prep()",
                "twk000@prep()",
                "t'ht@prep()",
                "t63'h63t@prep()",
                "xxdexx@prep()",
                "xxdirectionalhexx@prep()",
                "xxhexx@prep()",
                "xxixx@prep()",
                "xxihxx@prep()",
                "xxiwxx@prep()",
                "xxmxx@prep()",
                "xxnxx@prep()",
                "xxnwxx@prep()",
                "xxinwxx@prep()",
                "xxtkxx@prep()",
                "xxtnxx@prep()",
                "xxtixx@prep()",
                "xx?xx@prep()",
                "wavend@prep()"
        });
    }

    @Test
    public void test_weingreen_chapter_16() {
        checkInMaps("wein16E1", translatorBridge);
        checkInMaps("wein16A1", translatorBridge);
        checkInMaps("wein16A2", translatorBridge);
        checkInMaps("wein16B1", translatorBridge);
        checkInMaps("wein16B2", translatorBridge);
        checkInMaps("wein16C1", translatorBridge);
        checkInMaps("wein16C2", translatorBridge);
        checkInMaps("wein16D1", translatorBridge);
        checkInMaps("wein16D2", translatorBridge);
        checkInMaps("wein16E1", translatorBridge);
        checkInMaps("wein16E2", translatorBridge);
        checkInMaps("wein16F1", translatorBridge);
        checkInMaps("wein16F2", translatorBridge);
        checkInMaps("wein16G1", translatorBridge);
        checkInMaps("wein16G2", translatorBridge);
        checkInMaps("wein16H1", translatorBridge);
        checkInMaps("wein16H2", translatorBridge);
        checkInMaps("wein16I1", translatorBridge);
        checkInMaps("wein16I2", translatorBridge);
        checkInMaps("wein16J1", translatorBridge);
        checkInMaps("wein16J2", translatorBridge);
        checkInMaps("wein16K1", translatorBridge);
        checkInMaps("wein16K2", translatorBridge);
        checkInMaps("wein16L1", translatorBridge);
        checkInMaps("wein16L2", translatorBridge);
        checkInMaps("wein16M1", translatorBridge);
        checkInMaps("wein16M2", translatorBridge);
        checkInMaps("wein16N1", translatorBridge);
        checkInMaps("wein16N2", translatorBridge);
        checkInMaps("wein16O1", translatorBridge);
        checkInMaps("wein16O2", translatorBridge);
        checkInMaps("wein16P1", translatorBridge);
        checkInMaps("wein16P2", translatorBridge);
        checkInMaps("wein16Q1", translatorBridge);
        checkInMaps("wein16Q2", translatorBridge);
        checkInMaps("wein16R1", translatorBridge);
        checkInMaps("wein16R2", translatorBridge);
        checkInMaps("wein16S1", translatorBridge);
        checkInMaps("wein16S2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_17() {
        checkInMaps("wein17U1", translatorBridge);
        checkInMaps("wein17A1", translatorBridge);
        checkInMaps("wein17A2", translatorBridge);
        checkInMaps("wein17B1", translatorBridge);
        checkInMaps("wein17B2", translatorBridge);
        checkInMaps("wein17C1", translatorBridge);
        checkInMaps("wein17C2", translatorBridge);
        checkInMaps("wein17D1", translatorBridge);
        checkInMaps("wein17D2", translatorBridge);
        checkInMaps("wein17E1", translatorBridge);
        checkInMaps("wein17E2", translatorBridge);
        checkInMaps("wein17F1", translatorBridge);
        checkInMaps("wein17F2", translatorBridge);
        checkInMaps("wein17G1", translatorBridge);
        checkInMaps("wein17G2", translatorBridge);
        checkInMaps("wein17H1", translatorBridge);
        checkInMaps("wein17H2", translatorBridge);
        checkInMaps("wein17I1", translatorBridge);
        checkInMaps("wein17I2", translatorBridge);
        checkInMaps("wein17J1", translatorBridge);
        checkInMaps("wein17J2", translatorBridge);
        checkInMaps("wein17K1", translatorBridge);
        checkInMaps("wein17K2", translatorBridge);
        checkInMaps("wein17L1", translatorBridge);
        checkInMaps("wein17L2", translatorBridge);
        checkInMaps("wein17M1", translatorBridge);
        checkInMaps("wein17M2", translatorBridge);
        checkInMaps("wein17N1", translatorBridge);
        checkInMaps("wein17N2", translatorBridge);
        checkInMaps("wein17O1", translatorBridge);
        checkInMaps("wein17O2", translatorBridge);
        checkInMaps("wein17P1", translatorBridge);
        checkInMaps("wein17P2", translatorBridge);
        checkInMaps("wein17Q1", translatorBridge);
        checkInMaps("wein17Q2", translatorBridge);
        checkInMaps("wein17R1", translatorBridge);
        checkInMaps("wein17R2", translatorBridge);
        checkInMaps("wein17S1", translatorBridge);
        checkInMaps("wein17S2", translatorBridge);
        checkInMaps("wein17T1", translatorBridge);
        checkInMaps("wein17T2", translatorBridge);
        checkInMaps("wein17U1", translatorBridge);
        checkInMaps("wein17U2", translatorBridge);
        checkInMaps("wein17V1", translatorBridge);
        checkInMaps("wein17V2", translatorBridge);
        checkInMaps("wein17W1", translatorBridge);
        checkInMaps("wein17W2", translatorBridge);
        checkInMaps("wein17X1", translatorBridge);
        checkInMaps("wein17X2", translatorBridge);
        checkInMaps("wein17Y1", translatorBridge);
        checkInMaps("wein17Y2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_18() {
        checkInMaps("wein18A1", translatorBridge);
        checkInMaps("wein18A2", translatorBridge);
        checkInMaps("wein18B1", translatorBridge);
        checkInMaps("wein18B2", translatorBridge);
        checkInMaps("wein18C1", translatorBridge);
        checkInMaps("wein18C2", translatorBridge);
        checkInMaps("wein18D1", translatorBridge);
        checkInMaps("wein18D2", translatorBridge);
        checkInMaps("wein18E1", translatorBridge);
        checkInMaps("wein18E2", translatorBridge);
        checkInMaps("wein18F1", translatorBridge);
        checkInMaps("wein18F2", translatorBridge);
        checkInMaps("wein18G1", translatorBridge);
        checkInMaps("wein18G2", translatorBridge);
        checkInMaps("wein18H1", translatorBridge);
        checkInMaps("wein18H2", translatorBridge);
        checkInMaps("wein18I1", translatorBridge);
        checkInMaps("wein18I2", translatorBridge);
        checkInMaps("wein18J1", translatorBridge);
        checkInMaps("wein18J2", translatorBridge);
        checkInMaps("wein18K1", translatorBridge);
        checkInMaps("wein18K2", translatorBridge);
        checkInMaps("wein18L1", translatorBridge);
        checkInMaps("wein18L2", translatorBridge);
        checkInMaps("wein18M1", translatorBridge);
        checkInMaps("wein18M2", translatorBridge);
        checkInMaps("wein18N1", translatorBridge);
        checkInMaps("wein18N2", translatorBridge);
        checkInMaps("wein18O1", translatorBridge);
        checkInMaps("wein18O2", translatorBridge);
        checkInMaps("wein18P1", translatorBridge);
        checkInMaps("wein18P2", translatorBridge);
        checkInMaps("wein18Q1", translatorBridge);
        checkInMaps("wein18Q2", translatorBridge);
        checkInMaps("wein18R1", translatorBridge);
        checkInMaps("wein18R2", translatorBridge);
        checkInMaps("wein18S1", translatorBridge);
        checkInMaps("wein18S2", translatorBridge);
        checkInMaps("wein18T1", translatorBridge);
        checkInMaps("wein18T2", translatorBridge);
        checkInMaps("wein18U1", translatorBridge);
        checkInMaps("wein18U2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_19() {
        assertEquals(2, 2);
    }

    @Test
    public void test_weingreen_chapter_20() {
        checkInMaps("wein20A1", translatorBridge);
        checkInMaps("wein20A2", translatorBridge);
        checkInMaps("wein20B1", translatorBridge);
        checkInMaps("wein20B2", translatorBridge);
        checkInMaps("wein20C1", translatorBridge);
        checkInMaps("wein20C2", translatorBridge);
        checkInMaps("wein20D1", translatorBridge);
        checkInMaps("wein20D2", translatorBridge);
        checkInMaps("wein20E1", translatorBridge);
        checkInMaps("wein20E2", translatorBridge);
        checkInMaps("wein20F1", translatorBridge);
        checkInMaps("wein20F2", translatorBridge);
        checkInMaps("wein20G1", translatorBridge);
        checkInMaps("wein20G2", translatorBridge);
        checkInMaps("wein20H1", translatorBridge);
        checkInMaps("wein20H2", translatorBridge);
        checkInMaps("wein20I1", translatorBridge);
        checkInMaps("wein20I2", translatorBridge);
        checkInMaps("wein20J1", translatorBridge);
        checkInMaps("wein20J2", translatorBridge);
        checkInMaps("wein20K1", translatorBridge);
        checkInMaps("wein20K2", translatorBridge);
        checkInMaps("wein20L1", translatorBridge);
        checkInMaps("wein20L2", translatorBridge);
        checkInMaps("wein20M1", translatorBridge);
        checkInMaps("wein20M2", translatorBridge);
        checkInMaps("wein20N1", translatorBridge);
        checkInMaps("wein20N2", translatorBridge);
        checkInMaps("wein20O1", translatorBridge);
        checkInMaps("wein20O2", translatorBridge);
        checkInMaps("wein20P1", translatorBridge);
        checkInMaps("wein20P2", translatorBridge);
        checkInMaps("wein20Q1", translatorBridge);
        checkInMaps("wein20Q2", translatorBridge);
        checkInMaps("wein20R1", translatorBridge);
        checkInMaps("wein20R2", translatorBridge);
        checkInMaps("wein20S1", translatorBridge);
        checkInMaps("wein20S2", translatorBridge);
        checkInMaps("wein20T1", translatorBridge);
        checkInMaps("wein20T2", translatorBridge);
        checkInMaps("wein20U1", translatorBridge);
        checkInMaps("wein20U2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_21() {
        assertEquals(2, 2);
    }

    @Test
    public void test_weingreen_chapter_22() {
        checkInMaps("wein22A1", translatorBridge);
        checkInMaps("wein22A2", translatorBridge);
        checkInMaps("wein22B1", translatorBridge);
        checkInMaps("wein22B2", translatorBridge);
        checkInMaps("wein22C1", translatorBridge);
        checkInMaps("wein22C2", translatorBridge);
        checkInMaps("wein22D1", translatorBridge);
        checkInMaps("wein22D2", translatorBridge);
        checkInMaps("wein22E1", translatorBridge);
        checkInMaps("wein22E2", translatorBridge);
        checkInMaps("wein22F1", translatorBridge);
        checkInMaps("wein22F2", translatorBridge);
        checkInMaps("wein22G1", translatorBridge);
        checkInMaps("wein22G2", translatorBridge);
        checkInMaps("wein22H1", translatorBridge);
        checkInMaps("wein22H2", translatorBridge);
        checkInMaps("wein22I1", translatorBridge);
        checkInMaps("wein22I2", translatorBridge);
        checkInMaps("wein22J1", translatorBridge);
        checkInMaps("wein22J2", translatorBridge);
        checkInMaps("wein22K1", translatorBridge);
        checkInMaps("wein22K2", translatorBridge);
        checkInMaps("wein22L1", translatorBridge);
        checkInMaps("wein22L2", translatorBridge);
        checkInMaps("wein22M1", translatorBridge);
        checkInMaps("wein22M2", translatorBridge);
        checkInMaps("wein22N1", translatorBridge);
        checkInMaps("wein22N2", translatorBridge);
        checkInMaps("wein22O1", translatorBridge);
        checkInMaps("wein22O2", translatorBridge);
        checkInMaps("wein22P1", translatorBridge);
        checkInMaps("wein22P2", translatorBridge);
        checkInMaps("wein22Q1", translatorBridge);
        checkInMaps("wein22Q2", translatorBridge);
        checkInMaps("wein22R1", translatorBridge);
        checkInMaps("wein22R2", translatorBridge);
        checkInMaps("wein22S1", translatorBridge);
        checkInMaps("wein22S2", translatorBridge);
        checkInMaps("wein22T1", translatorBridge);
        checkInMaps("wein22T2", translatorBridge);
        checkInMaps("wein22U1", translatorBridge);
        checkInMaps("wein22U2", translatorBridge);
        checkInMaps("wein22V1", translatorBridge);
        checkInMaps("wein22V2", translatorBridge);
        checkInMaps("wein22W1", translatorBridge);
        checkInMaps("wein22W2", translatorBridge);
        checkInMaps("wein22X1", translatorBridge);
        checkInMaps("wein22X2", translatorBridge);
        checkInMaps("wein22Y1", translatorBridge);
        checkInMaps("wein22Y2", translatorBridge);
        checkInMaps("wein22Z1", translatorBridge);
        checkInMaps("wein22Z2", translatorBridge);
        checkInMaps("wein22A11", translatorBridge);
        checkInMaps("wein22A12", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_23() {
        assertEquals(2, 2);
    }

    @Test
    public void test_weingreen_chapter_24() {
        checkInMaps("wein24A1", translatorBridge);
        checkInMaps("wein24A2", translatorBridge);
        checkInMaps("wein24B1", translatorBridge);
        checkInMaps("wein24B2", translatorBridge);
        checkInMaps("wein24C1", translatorBridge);
        checkInMaps("wein24C2", translatorBridge);
        checkInMaps("wein24D1", translatorBridge);
        checkInMaps("wein24D2", translatorBridge);
        checkInMaps("wein24E1", translatorBridge);
        checkInMaps("wein24E2", translatorBridge);
        checkInMaps("wein24F1", translatorBridge);
        checkInMaps("wein24F2", translatorBridge);
        checkInMaps("wein24G1", translatorBridge);
        checkInMaps("wein24G2", translatorBridge);
        checkInMaps("wein24H1", translatorBridge);
        checkInMaps("wein24H2", translatorBridge);
        checkInMaps("wein24I1", translatorBridge);
        checkInMaps("wein24I2", translatorBridge);
        checkInMaps("wein24J1", translatorBridge);
        checkInMaps("wein24J2", translatorBridge);
        checkInMaps("wein24K1", translatorBridge);
        checkInMaps("wein24K2", translatorBridge);
        checkInMaps("wein24L1", translatorBridge);
        checkInMaps("wein24L2", translatorBridge);
        checkInMaps("wein24M1", translatorBridge);
        checkInMaps("wein24M2", translatorBridge);
        checkInMaps("wein24N1", translatorBridge);
        checkInMaps("wein24N2", translatorBridge);
        checkInMaps("wein24O1", translatorBridge);
        checkInMaps("wein24O2", translatorBridge);
        checkInMaps("wein24P1", translatorBridge);
        checkInMaps("wein24P2", translatorBridge);
        checkInMaps("wein24Q1", translatorBridge);
        checkInMaps("wein24Q2", translatorBridge);
        checkInMaps("wein24R1", translatorBridge);
        checkInMaps("wein24R2", translatorBridge);
        checkInMaps("wein24S1", translatorBridge);
        checkInMaps("wein24S2", translatorBridge);
        checkInMaps("wein24T1", translatorBridge);
        checkInMaps("wein24T2", translatorBridge);
        checkInMaps("wein24U1", translatorBridge);
        checkInMaps("wein24U2", translatorBridge);
        checkInMaps("wein24V1", translatorBridge);
        checkInMaps("wein24V2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_27() {
        checkInMaps("wein27A1", translatorBridge);
        checkInMaps("wein27A2", translatorBridge);
        checkInMaps("wein27B1", translatorBridge);
        checkInMaps("wein27B2", translatorBridge);
        checkInMaps("wein27C1", translatorBridge);
        checkInMaps("wein27C12", translatorBridge);
        checkInMaps("wein27C22", translatorBridge);
        checkInMaps("wein27D1", translatorBridge);
        checkInMaps("wein27D2", translatorBridge);
        checkInMaps("wein27E1", translatorBridge);
        checkInMaps("wein27E2", translatorBridge);
        checkInMaps("wein27F1", translatorBridge);
        checkInMaps("wein27F2", translatorBridge);
        checkInMaps("wein27G1", translatorBridge);
        checkInMaps("wein27G2", translatorBridge);
        checkInMaps("wein27H1", translatorBridge);
        checkInMaps("wein27H2", translatorBridge);
        checkInMaps("wein27I1", translatorBridge);
        checkInMaps("wein27I2", translatorBridge);
        checkInMaps("wein27J1", translatorBridge);
        checkInMaps("wein27J2", translatorBridge);
        checkInMaps("wein27K1", translatorBridge);
        checkInMaps("wein27K2", translatorBridge);
        checkInMaps("wein27L1", translatorBridge);
        checkInMaps("wein27L2", translatorBridge);
        checkInMaps("wein27M1", translatorBridge);
        checkInMaps("wein27M2", translatorBridge);
        checkInMaps("wein27N1", translatorBridge);
        checkInMaps("wein27N2", translatorBridge);
        checkInMaps("wein27O1", translatorBridge);
        checkInMaps("wein27O2", translatorBridge);
        checkInMaps("wein27P1", translatorBridge);
        checkInMaps("wein27P2", translatorBridge);
        checkInMaps("wein27Q1", translatorBridge);
        checkInMaps("wein27Q2", translatorBridge);
        checkInMaps("wein27R1", translatorBridge);
        checkInMaps("wein27R2", translatorBridge);
        checkInMaps("wein27S1", translatorBridge);
        checkInMaps("wein27S2", translatorBridge);
        checkInMaps("wein27T1", translatorBridge);
        checkInMaps("wein27T2", translatorBridge);
        checkInMaps("wein27U1", translatorBridge);
        checkInMaps("wein27U2", translatorBridge);
        checkInMaps("wein27V1", translatorBridge);
        checkInMaps("wein27V2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_30() {
        checkInMaps("wein30A1", translatorBridge);
        checkInMaps("wein30A2", translatorBridge);
        checkInMaps("wein30B1", translatorBridge);
        checkInMaps("wein30B2", translatorBridge);
        checkInMaps("wein30C1", translatorBridge);
        checkInMaps("wein30C2", translatorBridge);
        checkInMaps("wein30D1", translatorBridge);
        checkInMaps("wein30D2", translatorBridge);
        checkInMaps("wein30E1", translatorBridge);
        checkInMaps("wein30E2", translatorBridge);
        checkInMaps("wein30F1", translatorBridge);
        checkInMaps("wein30F2", translatorBridge);
        checkInMaps("wein30G1", translatorBridge);
        checkInMaps("wein30G2", translatorBridge);
        checkInMaps("wein30H1", translatorBridge);
        checkInMaps("wein30H2", translatorBridge);
        checkInMaps("wein30I1", translatorBridge);
        checkInMaps("wein30I2", translatorBridge);
        checkInMaps("wein30J1", translatorBridge);
        checkInMaps("wein30J2", translatorBridge);
        checkInMaps("wein30K1", translatorBridge);
        checkInMaps("wein30K2", translatorBridge);
        checkInMaps("wein30L1", translatorBridge);
        checkInMaps("wein30L2", translatorBridge);
        checkInMaps("wein30M1", translatorBridge);
        checkInMaps("wein30M2", translatorBridge);
        checkInMaps("wein30N1", translatorBridge);
        checkInMaps("wein30N2", translatorBridge);
        checkInMaps("wein30O1", translatorBridge);
        checkInMaps("wein30O2", translatorBridge);
        checkInMaps("wein30P1", translatorBridge);
        checkInMaps("wein30P2", translatorBridge);
        checkInMaps("wein30Q1", translatorBridge);
        checkInMaps("wein30Q2", translatorBridge);
        checkInMaps("wein30R1", translatorBridge);
        checkInMaps("wein30R2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_33() {
        checkInMaps("wein33A1", translatorBridge);
        checkInMaps("wein33A2", translatorBridge);
        checkInMaps("wein33B1", translatorBridge);
        checkInMaps("wein33B2", translatorBridge);
        checkInMaps("wein33C1", translatorBridge);
        checkInMaps("wein33C2", translatorBridge);
        checkInMaps("wein33D1", translatorBridge);
        checkInMaps("wein33D2", translatorBridge);
        checkInMaps("wein33E1", translatorBridge);
        checkInMaps("wein33E2", translatorBridge);
        checkInMaps("wein33F1", translatorBridge);
        checkInMaps("wein33F2", translatorBridge);
        checkInMaps("wein33G1", translatorBridge);
        checkInMaps("wein33G2", translatorBridge);
        checkInMaps("wein33H1", translatorBridge);
        checkInMaps("wein33H2", translatorBridge);
        checkInMaps("wein33I1", translatorBridge);
        checkInMaps("wein33I2", translatorBridge);
        checkInMaps("wein33J1", translatorBridge);
        checkInMaps("wein33J2", translatorBridge);
        checkInMaps("wein33K1", translatorBridge);
        checkInMaps("wein33K2", translatorBridge);
        checkInMaps("wein33L1", translatorBridge);
        checkInMaps("wein33L2", translatorBridge);
        checkInMaps("wein33M1", translatorBridge);
        checkInMaps("wein33M2", translatorBridge);
        checkInMaps("wein33N1", translatorBridge);
        checkInMaps("wein33N2", translatorBridge);
        checkInMaps("wein33O1", translatorBridge);
        checkInMaps("wein33O2", translatorBridge);
        checkInMaps("wein33P1", translatorBridge);
        checkInMaps("wein33P2", translatorBridge);
        checkInMaps("wein33Q1", translatorBridge);
        checkInMaps("wein33Q2", translatorBridge);
        checkInMaps("wein33R1", translatorBridge);
        checkInMaps("wein33R2", translatorBridge);
        checkInMaps("wein33S1", translatorBridge);
        checkInMaps("wein33S2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_35() {
        checkInMaps("wein35A1", translatorBridge);
        checkInMaps("wein35A2", translatorBridge);
        checkInMaps("wein35B1", translatorBridge);
        checkInMaps("wein35B2", translatorBridge);
        checkInMaps("wein35C1", translatorBridge);
        checkInMaps("wein35C2", translatorBridge);
        checkInMaps("wein35D1", translatorBridge);
        checkInMaps("wein35D2", translatorBridge);
        checkInMaps("wein35E1", translatorBridge);
        checkInMaps("wein35E2", translatorBridge);
        checkInMaps("wein35F1", translatorBridge);
        checkInMaps("wein35F2", translatorBridge);
        checkInMaps("wein35G1", translatorBridge);
        checkInMaps("wein35G2", translatorBridge);
        checkInMaps("wein35H1", translatorBridge);
        checkInMaps("wein35H2", translatorBridge);
        checkInMaps("wein35I1", translatorBridge);
        checkInMaps("wein35I2", translatorBridge);
        checkInMaps("wein35J1", translatorBridge);
        checkInMaps("wein35J2", translatorBridge);
        checkInMaps("wein35K1", translatorBridge);
        checkInMaps("wein35K2", translatorBridge);
        checkInMaps("wein35L1", translatorBridge);
        checkInMaps("wein35L2", translatorBridge);
        checkInMaps("wein35M1", translatorBridge);
        checkInMaps("wein35M2", translatorBridge);
        checkInMaps("wein35N1", translatorBridge);
        checkInMaps("wein35N2", translatorBridge);
        checkInMaps("wein35O1", translatorBridge);
        checkInMaps("wein35O2", translatorBridge);
        checkInMaps("wein35P1", translatorBridge);
        checkInMaps("wein35P2", translatorBridge);
        checkInMaps("wein35Q1", translatorBridge);
        checkInMaps("wein35Q2", translatorBridge);
        checkInMaps("wein35R1", translatorBridge);
        checkInMaps("wein35R2", translatorBridge);
        checkInMaps("wein35S1", translatorBridge);
        checkInMaps("wein35S2", translatorBridge);
        checkInMaps("wein35T1", translatorBridge);
        checkInMaps("wein35T2", translatorBridge);
        checkInMaps("wein35U1", translatorBridge);
        checkInMaps("wein35U2", translatorBridge);
        checkInMaps("wein35V1", translatorBridge);
        checkInMaps("wein35V2", translatorBridge);
        checkInMaps("wein35W1", translatorBridge);
        checkInMaps("wein35W2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_38() {
        checkInMaps("wein38A1", translatorBridge);
        checkInMaps("wein38A2", translatorBridge);
        checkInMaps("wein38B1", translatorBridge);
        checkInMaps("wein38B2", translatorBridge);
        checkInMaps("wein38C1", translatorBridge);
        checkInMaps("wein38C2", translatorBridge);
        checkInMaps("wein38D1", translatorBridge);
        checkInMaps("wein38D2", translatorBridge);
        checkInMaps("wein38E1", translatorBridge);
        checkInMaps("wein38E2", translatorBridge);
        checkInMaps("wein38F1", translatorBridge);
        checkInMaps("wein38F2", translatorBridge);
        checkInMaps("wein38G1", translatorBridge);
        checkInMaps("wein38G2", translatorBridge);
        checkInMaps("wein38H1", translatorBridge);
        checkInMaps("wein38H2", translatorBridge);
        checkInMaps("wein38I1", translatorBridge);
        checkInMaps("wein38I2", translatorBridge);
        checkInMaps("wein38J1", translatorBridge);
        checkInMaps("wein38J2", translatorBridge);
        checkInMaps("wein38K1", translatorBridge);
        checkInMaps("wein38K2", translatorBridge);
        checkInMaps("wein38L1", translatorBridge);
        checkInMaps("wein38L2", translatorBridge);
        checkInMaps("wein38M1", translatorBridge);
        checkInMaps("wein38M2", translatorBridge);
        checkInMaps("wein38N1", translatorBridge);
        checkInMaps("wein38N2", translatorBridge);
        checkInMaps("wein38O1", translatorBridge);
        checkInMaps("wein38O2", translatorBridge);
        checkInMaps("wein38P1", translatorBridge);
        checkInMaps("wein38P2", translatorBridge);
        checkInMaps("wein38Q1", translatorBridge);
        checkInMaps("wein38Q2", translatorBridge);
        checkInMaps("wein38R1", translatorBridge);
        checkInMaps("wein38R2", translatorBridge);
        checkInMaps("wein38S1", translatorBridge);
        checkInMaps("wein38S2", translatorBridge);
        checkInMaps("wein38T1", translatorBridge);
        checkInMaps("wein38T2", translatorBridge);
        checkInMaps("wein38U1", translatorBridge);
        checkInMaps("wein38U2", translatorBridge);
    }

    @Test
    public void test_weingreen_chapter_41() {
        checkInMaps("wein41A", translatorBridge);
        checkInMaps("wein41B", translatorBridge);
        checkInMaps("wein41C", translatorBridge);
        checkInMaps("wein41D", translatorBridge);
        checkInMaps("wein41E", translatorBridge);
        checkInMaps("wein41F", translatorBridge);
        checkInMaps("wein41G", translatorBridge);
        checkInMaps("wein41H", translatorBridge);
        checkInMaps("wein41I", translatorBridge);
        checkInMaps("wein41J", translatorBridge);
        checkInMaps("wein41K", translatorBridge);
        checkInMaps("wein41L", translatorBridge);
        checkInMaps("wein41M", translatorBridge);
        checkInMaps("wein41N", translatorBridge);
        checkInMaps("wein41O", translatorBridge);
        checkInMaps("wein41P", translatorBridge);
        checkInMaps("wein41Q", translatorBridge);
        checkInMaps("wein41R", translatorBridge);
        checkInMaps("wein41S", translatorBridge);
        checkInMaps("wein41T", translatorBridge);
        checkInMaps("wein41U", translatorBridge);
        checkInMaps("wein41V", translatorBridge);
    }

    @Test
    public void test_bereshit_chapter1() {
        checkInMaps("bereshit1A1", translatorBridge);
        checkInMaps("bereshit1A2", translatorBridge);
        checkInMaps("bereshit1B1", translatorBridge);
        checkInMaps("bereshit1B2", translatorBridge);
        checkInMaps("bereshit1C", translatorBridge);
        checkInMaps("bereshit1D", translatorBridge);
        checkInMaps("bereshit1E", translatorBridge);
        checkInMaps("bereshit1F", translatorBridge);
        checkInMaps("bereshit1G", translatorBridge);
        checkInMaps("bereshit1H", translatorBridge);
        checkInMaps("bereshit1I", translatorBridge);
        checkInMaps("bereshit1J", translatorBridge);
        checkInMaps("bereshit1K", translatorBridge);
        checkInMaps("bereshit1L", translatorBridge);
        checkInMaps("bereshit1M", translatorBridge);
        checkInMaps("bereshit1N", translatorBridge);
        checkInMaps("bereshit1O", translatorBridge);
        checkInMaps("bereshit1P", translatorBridge);
        checkInMaps("bereshit1Q", translatorBridge);
        checkInMaps("bereshit1R", translatorBridge);
        checkInMaps("bereshit1S", translatorBridge);
        checkInMaps("bereshit1T", translatorBridge);
        checkInMaps("bereshit1U", translatorBridge);
        checkInMaps("bereshit1V", translatorBridge);
        checkInMaps("bereshit1W", translatorBridge);
        checkInMaps("bereshit1X", translatorBridge);
        checkInMaps("bereshit1Y", translatorBridge);
        checkInMaps("bereshit1Z", translatorBridge);
        checkInMaps("bereshit1AA", translatorBridge);
        checkInMaps("bereshit1BB", translatorBridge);
        checkInMaps("bereshit1CC", translatorBridge);
        checkInMaps("bereshit1DD", translatorBridge);
        checkInMaps("bereshit1EE", translatorBridge);
    }

    @Test
    public void test_mishnah_berakhot_chapter1() {
        checkInMaps("mishnah1A", translatorBridge);
        checkInMaps("mishnah1B", translatorBridge);
        checkInMaps("mishnah1C", translatorBridge);
        checkInMaps("mishnah1D", translatorBridge);
        checkInMaps("mishnah1E", translatorBridge);
        checkInMaps("mishnah1F", translatorBridge);
        checkInMaps("mishnah1G", translatorBridge);
        checkInMaps("mishnah1H", translatorBridge);
        checkInMaps("mishnah1I", translatorBridge);
        checkInMaps("mishnah1J", translatorBridge);
        checkInMaps("mishnah1K", translatorBridge);
        checkInMaps("mishnah1L", translatorBridge);
        checkInMaps("mishnah1M", translatorBridge);
        checkInMaps("mishnah1N", translatorBridge);
        checkInMaps("mishnah1O", translatorBridge);
        checkInMaps("mishnah1P", translatorBridge);
        checkInMaps("mishnah1Q", translatorBridge);
        checkInMaps("mishnah1R", translatorBridge);
        checkInMaps("mishnah1S", translatorBridge);
        checkInMaps("mishnah1T", translatorBridge);
        checkInMaps("mishnah1U", translatorBridge);
        checkInMaps("mishnah1V", translatorBridge);
        checkInMaps("mishnah1W", translatorBridge);
    }

    @Test
    public void test_rachi_bereshit_chapter1() {
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
    public void test_shei_lamorei_rachi1() {
        checkInMaps("sheilamoreiA", translatorBridge);
        checkInMaps("sheilamoreiB", translatorBridge);
        checkInMaps("sheilamoreiC", translatorBridge);
        checkInMaps("sheilamoreiD", translatorBridge);
        checkInMaps("sheilamoreiE", translatorBridge);
        checkInMaps("sheilamoreiF", translatorBridge);
        checkInMaps("sheilamoreiG", translatorBridge);
        checkInMaps("sheilamoreiH", translatorBridge);
    }

    @Test
    public void englishBavli() {
        checkInMaps("engbavli1Arightmain1", translatorBridge);
        checkInMaps("engbavli1Arightmain2", translatorBridge);
        checkInMaps("engbavli1Arightmain3", translatorBridge);
        checkInMaps("engbavli1Arightmain4A", translatorBridge);
        checkInMaps("engbavli1Arightmain4B", translatorBridge);
        checkInMaps("engbavli1Arightmain4C", translatorBridge);
        checkInMaps("engbavli1Arightmain4D", translatorBridge);
        checkInMaps("engbavli1Arightmain4E", translatorBridge);
        checkInMaps("engbavli1Arightmain5", translatorBridge);
        checkInMaps("engbavli1Arightmain6", translatorBridge);
        checkInMaps("engbavli1Arightmain7", translatorBridge);
        checkInMaps("engbavli1Aleftmain1", translatorBridge);
        checkInMaps("engbavli1Aleftmain2", translatorBridge);
    }


    @Test
    public void englishYeroushalmi() {
        checkInMaps("engyerou1Arightleft1", translatorBridge);
        checkInMaps("engyerou1Arightleft2", translatorBridge);
        checkInMaps("engyerou1Arightleft3", translatorBridge);
        checkInMaps("engyerou1Arightleft4A", translatorBridge);
        checkInMaps("engyerou1Arightleft4B", translatorBridge);
        checkInMaps("engyerou1Arightleft5", translatorBridge);
        checkInMaps("engyerou1Arightleft6", translatorBridge);
        checkInMaps("engyerou1Arightleft7", translatorBridge);
    }

    @Test
    public void hebrewBavli() {
        checkInMaps("hebbavli1ApagecommentA", translatorBridge);
        checkInMaps("hebbavli1ApagecommentB", translatorBridge);
        checkInMaps("hebbavli1ApagecommentC", translatorBridge);
        checkInMaps("hebbavli1ApagecommentD", translatorBridge);
        checkInMaps("hebbavli1ApagecommentE", translatorBridge);
        checkInMaps("hebbavli1ApagecommentF", translatorBridge);
        checkInMaps("hebbavli1ApagecommentG", translatorBridge);
        checkInMaps("hebbavli1ApagecommentH", translatorBridge);
        checkInMaps("hebbavli1ApagecommentI", translatorBridge);
        checkInMaps("hebbavli1ApagecommentJ", translatorBridge);
        checkInMaps("hebbavli1ApagecommentK", translatorBridge);
        checkInMaps("hebbavli1ApagecommentL", translatorBridge);
        checkInMaps("hebbavli1ApagecommentM", translatorBridge);
        checkInMaps("hebbavli1ApagecommentN", translatorBridge);
        checkInMaps("hebbavli1ApagecommentO", translatorBridge);
        checkInMaps("hebbavli1ApagecommentP", translatorBridge);
    }


    @Test
    public void midrabaeng() {
        checkInMaps("midrashrabaeng1A", translatorBridge);
        checkInMaps("midrashrabaeng1B", translatorBridge);
        checkInMaps("midrashrabaeng1C", translatorBridge);
        checkInMaps("midrashrabaeng1D", translatorBridge);
        checkInMaps("midrashrabaeng1E", translatorBridge);
        checkInMaps("midrashrabaeng1F", translatorBridge);
        checkInMaps("midrashrabaeng1G", translatorBridge);
        checkInMaps("midrashrabaeng1H", translatorBridge);
        checkInMaps("midrashrabaeng1I", translatorBridge);
        checkInMaps("midrashrabaeng1J", translatorBridge);
        checkInMaps("midrashrabaeng1K", translatorBridge);
    }

    @Test
    public void midrabheb() {
        checkInMaps("midrashrabahebcom1A", translatorBridge);
        checkInMaps("midrashrabahebcom1B", translatorBridge);
        checkInMaps("midrashrabahebcom1C", translatorBridge);
        checkInMaps("midrashrabahebcom1D", translatorBridge);
        checkInMaps("midrashrabahebcom1E", translatorBridge);
        checkInMaps("midrashrabahebcom1F", translatorBridge);
        checkInMaps("midrashrabahebcom1G", translatorBridge);
        checkInMaps("midrashrabahebcom1H", translatorBridge);
        checkInMaps("midrashrabahebcom1I", translatorBridge);
        checkInMaps("midrashrabahebcom1J", translatorBridge);
        checkInMaps("midrashrabahebcom1K", translatorBridge);
        checkInMaps("midrashrabahebcom1L", translatorBridge);
    }

    @Test
    public void test_failed_ones() {
        assertTrue(true);
        checkInMaps("mishnah1S", translatorBridge);
        checkInMaps("toto", translatorBridge);
        checkInMaps("totoacc", translatorBridge);
    }

}

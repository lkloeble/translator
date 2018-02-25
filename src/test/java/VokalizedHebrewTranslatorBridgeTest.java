import org.junit.Before;
import org.junit.Test;
import org.patrologia.translator.TranslatorBridge;
import org.patrologia.translator.basicelements.*;
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
        NounRepository nounRepository = new NounRepository(Language.HEBREW, hebrewDeclensionFactory, getNouns());
        VerbRepository verbRepository = new VerbRepository(new HebrewConjugationFactory(getConjugations(), getHebrewConjugationsDefinitions()), Language.HEBREW, new Accentuer(),getVerbs());
        Analizer hebrewAnalyzer = new HebrewAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getHebDico(), getFrenchVerbs(), verbRepository, nounRepository, null, null, hebrewDeclensionFactory);
        translatorBridge = new TranslatorBridge(hebrewAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(hebrewPathFile);
        mapValuesForResult = loadMapFromFiles(hebrewResultFile);
    }

    private List<String> getDeclensions() {
        return Arrays.asList(new String[]{
                "im%im.txt",
                "ot%ot.txt",
                "im-fem%im-fem.txt",
                "invmasc%invmasc.txt",
                "invfem%invfem.txt",
                "im-ot%im-ot.txt",
                "adj%adj.txt",
                "empty%empty.txt",
                "mascot%mascot.txt",
                "imonly%imonly.txt",
                "endingh%endingh.txt"
        });
    }

    private List<Declension> getDeclensionList() {
        List<Declension> declensionList = new ArrayList<>();
        declensionList.add(new HebrewDeclension("im.txt", getImElements()));
        declensionList.add(new HebrewDeclension("ot.txt", getOtElements()));
        declensionList.add(new HebrewDeclension("im-fem.txt", getImfemElements()));
        declensionList.add(new HebrewDeclension("invfem.txt", getInvfemElements()));
        declensionList.add(new HebrewDeclension("invmasc.txt", getInvmascElements()));
        declensionList.add(new HebrewDeclension("im-ot.txt", getImOtElements()));
        declensionList.add(new HebrewDeclension("mascot.txt", getMascOtElements()));
        declensionList.add(new HebrewDeclension("adj.txt", getAdjElements()));
        declensionList.add(new HebrewDeclension("imonly.txt", getImOnlyElements()));
        declensionList.add(new HebrewDeclension("empty.txt", getEmptyElements()));
        declensionList.add(new HebrewDeclension("endingh.txt", getEndingH()));
        return declensionList;
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

    private List<String> getImElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%masc%",
                "cst(nomsg)%sing%masc%&",
                "decim-h%sing%masc%h",
                "decim-iw%sing%masc%iw",
                "decim-w%sing%masc%w331",
                "nomplr%plr%masc%60im000",
                "cst(nomplr1)%plr%masc%i",
                "cst(nomplr2)%plr%masc%i&"
        });
    }

    private List<String> getOtElements() {
        return Arrays.asList(new String[]{
                "nomsg%sing%fem%h",
                "decot-t%sing%fem%t",
                "decot-w%sing%fem%w331",
                "decot-tn%sing%fem%tn",
                "nomplr%plr%fem%ot"
        });
    }

    private List<String> getAdjElements() {
        return Arrays.asList(new String[]{
                "nomh%sing%fem%h",
                "nomot%plr%fem%ot",
                "nomempty%sing%masc%",
                "nomim%plr%masc%im",
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
                "decim-nw%sing%masc%nw",
                "decim-k%sing%masc%k"
        });
    }

    private List<String> getImOtElements() {
        return Arrays.asList(new String[]{
                "nommascsg%sing%masc%",
                "nomfemsg%sing%fem%64h",
                "nomfemplr%plr%fem%w331t",
                "nommascsg%sing%masc%",
                "cst(nommascsg)%sing%masc%&",
                "decim-w%sing%masc%w",
                "nomplr%plr%masc%60im000",
                "cst(nomplr)%plr%masc%i&"
        });
    }

    private List<String> getInvmascElements() {
        return Arrays.asList(new String[]{"noma%sing%masc%", "dir(noma)%sing%masc%h", "nom%plr%masc%"});
    }

    private List<String> getEndingH() {
        return Arrays.asList(
                new String[]{
                        "noma%sing%fem%h",
                        "nomb%sing%fem%t",
                        "noma%plr%fem%wt"
                });
    }

    private List<String> getImfemElements() {
        return Arrays.asList(new String[]{"nomsg%sing%fem%", "nomplr%plr%fem%im"});
    }

    private List<String> getInvfemElements() {
        return Arrays.asList(new String[]{"nomsg%sing%fem%", "cst(nomsg)%sing%fem%&"});
    }

    private List<String> getConjugations() {
        return Arrays.asList(new String[]{"paal%paal.txt"});
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
                "abr@masc%imonly",
                "a58d64m64h@fem%custom(nomsg=admh|decim-nw=admtnw)",
                "a64d64m000@masc%invmasc",
                "a62'h64d@masc%empty",
                "a63'h63t@masc%empty",
                "a60is298@masc%custom(nomplr=ansim|cst(nomplr)=ansi&|dechsofit=aish|nomsg=a60is298)",
                "aw331r@fem%invfem",
                "al65h60im000@masc%custom(nomsg=alhim|cst(nomsg)=alhi&|decim-inwplr=alhinw)",
                "aliyzr@masc%invmasc",
                "a62r62ts@fem%custom(nomplr=artswt|nomsg=a62r62ts)",
                "a60s29864h@fem%custom(nomplr=nsim|cst(nomplr)=nsi&|nomsg=a60s29864h)",
                "asmwrt@fem%invfem",
                "bit@masc%im",
                "b62n000@masc%im",
                "b30565q62r@masc%im",
                "b60q62r@masc%im",
                "b30556r60it@fem%ot",
                "d64b64r@masc%im",
                "dbri@masc%invmasc",
                "dp@masc%im",
                "drk@masc%empty",
                "g64dw331l@adj%im-ot",
                "gmlial@masc%invmasc",
                "g64n000@masc%im",
                "h61ik64l@masc%im",
                "h63r@masc%im",
                "wtrin@adj%im-ot",
                "z64q61n000@adj%adj",
                "'hmh@fem%invfem",
                "'htswt@masc%invmasc",
                "ihwh@masc%invmasc",
                "ihwswy@masc%invmasc%",
                "iw331m000@masc%custom(nomplr=imim000|cst(nomplr)=imi|cst(nomsg)=iwm&|nomsg=iwm000)",
                "i56rw309s29864l63i60m000@masc%invmasc",
                "i60s29956r64a61l@masc%invmasc",
                "i63d@masc%im",
                "i61y58q65b@masc%invmasc",
                "'hs29862k00056@fem%im-fem",
                "'h63k63m000@adj%im-ot",
                "'hlb@masc%im",
                "khn@masc%im",
                "kwhn@masc%im",
                "kw331k56b61@masc%im",
                "krti@neut%invmasc",
                "lbn@masc%invmasc",
                "l63i56l64h@fem%invfem",
                "l64i56l64h@fem%invfem",
                "m60ts56r63i60m000@masc%invmasc",
                "m62l62k00056@masc%im",
                "mys@masc%endingh",
                "ms29862h@masc%invmasc",
                "msikir@neut%invmasc",
                "msth@masc%invmasc",
                "nakl@masc%im",
                "n56h63r@masc%mascot",
                "n64b60ia@masc%im-ot",
                "nts@masc%im",
                "ybir@fem%ot",
                "y63in@masc%im",
                "y60ir@fem%custom(nomplr=yrim|cst(nomplr)=yri&|dir(nomsg)=yirh|nomsg=y60ir)",
                "y64m000@masc%im",
                "ymwd@masc%im",
                "y64p64r@fem%im-fem",
                "yrbit@masc%invmasc",
                "y62r62b@masc%im",
                "yts@masc%custom(nomsg=yts|cst(nomsg)=ytst&|nomplr=ytsim|cst(nomplr)=ytsi&)",
                "y61s29964w309@masc%invmasc",
                "p56r60i@masc%im",
                "tswtn@neut%invmasc",
                "q64dw331s298@adj%im-ot",
                "qw331l@masc%mascot",
                "qxr@masc%invmasc",
                "r61as60it@masc%invmasc",
                "ra65s298@fem%im",
                "raswn@fem%ot",
                "rbi@masc%invmasc",
                "rbn@masc%invmasc",
                "r63y@adj%im-ot",
                "s'hr@masc%im",
                "s29856mw309a61l@masc%invmasc",
                "slws@adj%im-ot",
                "s29856l65m65h@masc%empty",
                "s29861m000@masc%mascot",
                "s29864m6351i60m000@masc%invmasc",
                "s29864m63i60m000@masc%invmasc",
                "s29864m64i60m000@masc%invmasc",
                "smy@masc%invmasc",
                "sy@fem%endingh",
                "sw309s@masc%im-ot",
                "swp@masc%im",
                "tklt@adj%invmasc",
                "trwm@fem%ot",
                "xw331b@noun%im-ot"
        });
    }

    private List<String> getHebDico() {
        return Arrays.asList(new String[]{
                "abr@noun!imonly%1(noun)=membre",
                "a58d64m64h@noun!custom(nomsg=admh|decim-nw=admtnw)%1(noun)=terre",
                "a64d64m000@noun!invmasc%1(noun)=homme",
                "a62'h64d@noun!empty%1(noun)=un",
                "a63'h63t@noun!empty%1(noun)=une",
                "a60is298@noun!custom(nomplr=ansim|cst(nomplr)=ansi&|dechsofit=aish|nomsg=a60is298)%1(noun)=homme",
                "akl@verb!norm%1(verb)=manger",
                "akwl@verb!norm%1(verb)=manger2",
                "aimti@prep%1(prep)=quand",
                "al@prep%1(prep)=vers",
                "a62l@prep%1(prep)=vers",
                "ala@prep%1(prep)=mais",
                "aliyzr@noun!invmasc%1(noun)=éliezer",
                "ani@prep%1(prep)=je",
                "a58n60i@prep%1(prep)=je",
                "amr@verb!norm%1(verb)=dire",
                "aw331r@noun!invfem%1(noun)=flamme,feu%2(noun)=lumière",
                "al65h60im000@noun!custom(nomsg=alhim|cst(nomsg)=alhi&|decim-inwplr=alhinw)%1(noun)=dieu",
                "am@prep%1(prep)=si",
                "a62r62ts@noun!custom(nomplr=artswt|nomsg=a62r62ts)%1(noun)=pays%2(noun)=terre",
                "asmwrt@noun!invfem%1(noun)=veille",
                "asr@prep%1(prep)=qui",
                "a59s29862r@prep%1(prep)=qui",
                "a60s29864h@noun!custom(nomplr=nsim|cst(nomplr)=nsi&|nomsg=a60s29864h)%1(noun)=femme",
                "at@prep%1(prep)=COD",
                "a61t@prep%1(prep)=COD",
                "atm@prep%1(prep)=vous%2(prep)=eux",
                "b@prep%1(prep)=dans",
                "b30558@prep%1(prep)=dans",
                "b30564@prep%1(prep)=dans",
                "b56@prep%1(prep)=dans",
                "b63@prep%1(prep)=dans",
                "ba@verb!norm%1(verb)=venir",
                "bdl@verb!norm%1(verb)=separer",
                "bin000@prep%1(prep)=entre",
                "b61in000@prep%1(prep)=entre",
                "bit@noun!im%1(noun)=maison",
                "b62n000@noun!im%1(noun)=fils",
                "b30565q62r@noun!im%1(noun)=matin",
                "b60q62r@noun!im%1(noun)=matin",
                "bra@verb!norm%1(noun)=creer",
                "b30556r60it@noun!ot%1(noun)=alliance",
                "dbr@verb!norm%1(verb)=parler",
                "dbri@noun!invmasc%1(noun)=paroles de",
                "dp@noun!im%1(noun)=page",
                "gm000@prep%1(prep)aussi%2(prep)=même%3(prep)=encore",
                "g63m000@prep%1(prep)aussi%2(prep)=même%3(prep)=encore",
                "g64dw331l@adj!im-ot%(adj)=grand",
                "gmlial@noun!invmasc%1(noun)=gamaliel",
                "g64n000@noun!im%1(noun)=jardin",
                "d64b64r@noun!im%1(noun)=chose,acte%2(noun)=parole",
                "drk@noun!empty%1(noun)=voie%2(noun)=chemin",
                "h@prep%1(prep)=le[les,la]",
                "h59@prep%1(prep)=le[les,la]",
                "h60n61h@prep%1(prep)=voici",
                "hnh@prep%1(prep)=voici",
                "h62m000@prep%1(prep)=eux",
                "hm@prep%1(prep)=eux",
                "h6269@prep%1(prep)=le[les,la]",
                "h63@prep%1(prep)=le[les,la]",
                "h6369@prep%1(prep)=le[les,la]",
                "h63r@noun!im%1(noun)=montagne",
                "h64@prep%1(prep)=le[les,la]",
                "h6469@prep%1(prep)=le[les,la]",
                "h69@prep%1(prep)=le[les,la]",
                "hiw@verb!irrg%1(verb)=etre",
                "h61ik64l@noun!im%1(noun)=palais",
                "hwa@prep%1(prep)=lui",
                "hw309a@prep%1(prep)=lui",
                "hlk@verb!norm%1(noun)=aller",
                "hr'hiq@verb!norm%1(verb)=renvoyer%2(verb)=separer%3(verb)=eloigner",
                "w@prep%1(prep)=et",
                "w56@prep%1(prep)=et",
                "w63@prep%1(prep)=et",
                "w309@prep%1(prep)=et",
                "wtrin@adj!im-ot%1(adj)=permis",
                "z64q61n000@noun!adj%1(adj)=ancien",
                "zw@prep%(prep)=ce",
                "'hs29862k00056@noun!im-fem%1(noun)=arrêt,pause%2(noun)=sombre,obscur%3(noun)=obscurité",
                "'h63k63m000@noun!im-ot%1(noun)=sage",
                "'hlb@noun!im%1(noun)=lait%2(noun)=graisse",
                "'hmh@noun!invfem%1(noun)=soleil",
                "'htswt@noun!invmasc%1(noun)=milieu de la nuit",
                "i63d@noun!im%1(noun)=main",
                "idy@verb!norm%1(verb)=connaitre",
                "iw331m000@noun!custom(nomplr=imim000|cst(nomplr)=imi|cst(nomsg)=iwm&|nomsg=iwm000)%1(noun)=jour",
                "ihwh@noun!invmasc%1(noun)=le seigneur%1(noun)=YHVH",
                "ihwswy@noun!invmasc%1(noun)=yehoshouah",
                "i61y58q65b@noun!invmasc%1(noun)=jacob",
                "i56rw309s29864l63i60m000@noun!invmasc%1(noun)=jérusalem",
                "isb@verb!norm%1(noun)=demeurer",
                "i60s29956r64a61l@noun!invmasc%1(noun)=israël",
                "itsa@verb!norm%1(verb)=sortir",
                "k@prep%1(prep)=comme",
                "k56@prep%1(prep)=comme",
                "k31559@prep%1(prep)=comme",
                "k31560@prep%1(prep)=comme",
                "k31563@prep%1(prep)=comme",
                "k31564@prep%1(prep)=comme",
                "k3156369@prep%1(prep)=comme",
                "k3156469@prep%1(prep)=comme",
                "kdi@prep%1(prep)=quand%2(prep)=afin de%3(prep)=pour",
                "khn@noun!im%1(noun)=prêtre",
                "kwhn@noun!im%1(noun)=prêtre",
                "kw331k56b61@noun!im%1(noun)=étoile",
                "ki@prep%1(prep)=car%2(prep)=lorsque%3(prep)=pour que%4(prep)=que",
                "k315i@prep%1(prep)=car%2(prep)=lorsque%3(prep)=pour que%4(prep)=que",
                "k31560i@prep%1(prep)=car%2(prep)=lorsque%3(prep)=pour que%4(prep)=que",
                "kl@prep%1(prep)=tout",
                "k31564l@prep%1(prep)=tout",
                "k64l@prep%1(prep)=tout",
                "kn@prep%1(prep)=oui%2(prep)=ainsi",
                "krt@verb!norm%1(verb)=couper",
                "krti@noun!invmasc%1(noun)=poireau",
                "l@prep%1(prep)=à",
                "l56@prep%1(prep)=à",
                "lbd@prep%1(prep)=seulement",
                "lbn@noun!invmasc%1(noun)=blanc%2(noun)=laban",
                "l63i56l64h@noun!invfem%1(noun)=nuit",
                "l64i56l64h@noun!invfem%1(noun)=nuit",
                "la@prep%1(prep)=ne",
                "l65a@prep%1(prep)=ne",
                "lmh@prep%1(prep)=pourquoi",
                "lw@prep%1(prep)=pour lui",
                "lw331@prep%1(prep)=pour lui",
                "lq'h@verb!norm%1(verb)=prendre",
                "m@prep%1(prep)=de",
                "m60@prep%1(prep)=de",
                "mad@prep%1(prep)=très",
                "m56a65d@prep%1(prep)=très",
                "mh@prep%1(prep)=quoi",
                "m63h@prep%1(prep)=quoi",
                "m64h@prep%1(prep)=quoi",
                "mi@prep%1(prep)=qui",
                "m60i@prep%1(prep)=qui",
                "m62l62k00056@noun!im%1(noun)=roi",
                "mn@prep%1(prep)=de",
                "m60n000@prep%1(prep)=de",
                "m00060n000@prep%1(prep)=de",
                "mys@noun!endingh%1(noun)=acte%2(noun)=oeuvre",
                "m60ts56r63i60m000@noun!invmasc%1(noun)=égypte",
                "ms29862h@noun!invmasc%1(noun)=moïse",
                "msikir@noun!invmasc%1(noun)=meshearir%2(noun)=lorsque l'on sait reconnaître",
                "msth@noun!invmasc%1(noun)=fête%2(noun)=banquet",
                "nakl@noun!im%1(noun)=destiné à être mangé",
                "n56h63r@noun!mascot%1(noun)=rivière",
                "n64b60ia@noun!im-ot%1(noun)=prophète",
                "nkns@verb!norm%1(verb)=entrer",
                "ntn@verb!norm%1(verb)=donner",
                "nts@noun!im%1(noun)=faucon%2(noun)=floraison",
                "p56r60i@noun!im%1(noun)=fruit",
                "q64dw331s298@adj!im-ot%1(noun)=saint",
                "qw331l@noun!mascot%1(noun)=voix",
                "qra@verb!norm%1(verb)=crier%2(verb)=lire%3(verb)=appeler",
                "qxr@noun!invmasc%1(noun)=encens%2(noun)=combustion",
                "y63in@noun!im%1(noun)=oeil",
                "y60ir@noun!custom(nomplr=yrim|cst(nomplr)=yri&dir(nomsg)=yirh|nomsg=y60ir)%1(noun)=ville",
                "ybir@noun!ot%1(noun)=crime%2(noun)=péché%3(noun)=transgression",
                "yd@prep%1(prep)=jusqu'à",
                "y63d@prep%1(prep)=jusqu'à",
                "yl@prep%1(prep)=en faveur de%2(prep)=au dessus de%3(noun)=sur",
                "y63l@prep%1(prep)=en faveur de%2(prep)=au dessus de%3(noun)=sur",
                "ylh@verb!norm%1(verb)=grandir%2(verb)=couter%3(verb)=selever",
                "y64p64r@noun!im-fem%1(noun)=jeune daim%2(noun)=gris,cendré%3(noun)=poussière",
                "ym@prep%1(prep)=avec",
                "y60m@prep%1(prep)=avec",
                "ym000@prep%1(prep)=avec",
                "y60m000@prep%1(prep)=avec",
                "ymwd@noun!im%1(noun)=pilier%2(noun)=colonne%3(noun)=page",
                "ymm000@prep%1(prep)=avec eux",
                "y60m64m000@prep%1(prep)=avec eux",
                "y64m000@noun!im%1(noun)=peuple",
                "yrbit@noun!invmasc%1(noun)=prière du soir",
                "y62r62b@noun!im%1(noun)=soir",
                "y61s29964w309@noun!invmasc%1(noun)=esav",
                "yts@noun!custom(nomsg=yts|cst(nomsg)=ytst&|nomplr=ytsim|cst(nomplr)=ytsi&)%1(noun)=arbre",
                "tswtn@noun!invmasc%1(noun)=précepte",
                "ra65s298@noun!im%1(noun)=tête",
                "r61as60it@noun!invmasc%1(noun)=commencement",
                "raswn@noun!ot%1(noun)=commencement%2(adj)=première",
                "rah@verb!norm%1(noun)=apparaitre%2(verb)=voir",
                "rbi@noun!invmasc%1(noun)=rabbi",
                "rbn@noun!invmasc%1(noun)=rabban",
                "r63y@noun!im-ot%1(noun)=mauvais",
                "s@prep%1(prep)=que",
                "s'hr@noun!im%1(noun)=aube",
                "slws@adj!im-ot%1(adj)=troisième",
                "s29856l65m65h@noun!empty%1(noun)=salomon",
                "s29856mw309a61l@noun!invmasc%1(noun)=samuel",
                "s29861m000@noun!mascot%1(noun)=nom",
                "s29864m6351i60m000@noun!invmasc%1(noun)=ciels",
                "s29864m63i60m000@noun!invmasc%1(noun)=ciels",
                "s29864m64i60m000@noun!invmasc%1(noun)=ciels",
                "smy@noun!invmasc%1(noun)=shema",
                "sw309s@noun!im-ot%1(noun)=cheval",
                "smy@verb!norm%1(verb)=entendre",
                "smr@verb!norm%1(verb)=garder",
                "sy@noun!endingh%1(noun)=heure",
                "swp@noun!im%1(noun)=fin",
                "t'ht@prep%1(prep)=à la place de%2(prep)=sous",
                "t63'h63t@prep%1(prep)=à la place de%2(prep)=sous",
                "tklt@adj!invmasc%1(adj)=bleu",
                "trwm@noun!ot%1(noun)=térouma",
                "xw331b@noun!im-ot%1(noun)=bon",
                "xxdexx@prep%1(prep)=de",
                "xxtnxx@prep%1(prep)=à eux",
                "wavend@prep%1(prep)=de lui"
        });
    }

    private List<String> getVerbs() {
        return Arrays.asList(new String[]{
                "akl,,[paal],(AIP%leadingrootletter%a*a64@AIP%secondletterroot%k*k64@AIP%alternateaccentuation(5:9)%k64*k56)",
                "akwl,,[paal]",
                "amr,,[paal],(AIP%leadingrootletter%a*a64@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@AIP%alternateaccentuation(5)%r*r64@ARAIPR%substitute%amr*awmr*0)",
                "ba,,[paal2],(AIP%leadingrootletter%b*b30564@AIP%alternateaccentuation(5)%a*a64))",
                "bdl,,[hiphil],(HIFPER%leadingrootletter%h*h60@HIFPER%secondletterroot%b*b56@HIFPER%alternateaccentuation(4:5:9)%d*d60i",
                "bra,,[paal],(AIP%leadingrootletter%*b30564@AIP%secondletterroot%r*r64@AIP%secondletterrootexception(5:9)%r*r56@AIP%alternateaccentuation%b30564*b64",
                "dbr,,[piel],(PIEPER%leadingrootletter%d*d60@PIEPER%secondletterroot%b*b63@PIEPER%alternateaccentuation(4)%b63*b30562@PIEPER%alternateaccentuation(5:9)%b63*b30556)",
                "hiw@IRREGULAR%[AIP]=[,,h64i64h,,,h64iw309]%[AIF]=[,,ihi,,,,]%[PALFUT]=[,,ihi,,,,]%[AIMP]=[,,,hiw,]",
                "hlk,,[paal],(AIP%leadingrootletter%h*h64@AIP%secondletterroot%l*l63@AIP%sofitrootletter%k3*k000@AIP%sofitaccentuedletter%k000*k00056)",
                "hr'hiq,hr'hiq,[paal]",
                "idy,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%d*d63@AIP%alternateaccentuation(5:9)%d63*d56@AIP%alternateaccentuation(5)%y*y64)",
                "isb,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%s*s29863@AIP%alternateaccentuation(5:9)%s29863*s29856@AIP%alternateaccentuation(5)%b*b64)",
                "itsa,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%ts*ts64@AIP%alternateaccentuation(5:9)%ts64*ts56)",
                "krt,,[paal],(AIP%leadingrootletter%k*k31564@AIP%secondletterroot%r*r63",
                "lq'h,,[paal],(AIP%leadingrootletter%l*l64@AIP%secondletterroot%q*q63@AIP%alternateaccentuation(5:9)%q63*q56@AIP%alternateaccentuation(5)%'h*'h64)",
                "nkns,,[mishna]",
                "ntn,,[paal],(AIP%leadingrootletter%n*n64@AIP%secondletterroot%t*t63@AIP%sofitrootletter%n3*n000@AIP%alternateaccentuation(5:9)%t63*t56@AIP%alternateaccentuation(5)%56n*56n64)",
                "ylh,,[paal]",
                "qra,qrwt,[paal],(AIP%leadingrootletter%q*q64@AIP%secondletterroot%r*r64@AIP%alternateaccentuation%q64*q6469@AIP%alternateaccentuation(5:9)%r64*r56@AIP%alternateaccentuation(5)%a*a64@AIP%substitute(1:2:3:6:7:8)%qra*qri@ARAIPR%substitute%ra*wr*0)",
                "rah,,[paal],(AIP%leadingrootletter%r*r64@AIP%secondletterroot%a*a64@AIP%alternateaccentuation(9)%a64*a@AIP%deleteletter(9)%h",
                "smy,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56",
                "smr,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56"
        });
    }

    private Map<String, List<String>> getHebrewConjugationsDefinitions() {
        Map<String, List<String>> hebrewConjugationDefinitionsMap = new HashMap<>();
        hebrewConjugationDefinitionsMap.put("paal", getPaalDefinition());
        hebrewConjugationDefinitionsMap.put("paal2", getPaal2Definition());
        hebrewConjugationDefinitionsMap.put("hiphil", getHiphilDefinition());
        hebrewConjugationDefinitionsMap.put("piel", getPielDefinition());
        hebrewConjugationDefinitionsMap.put("mishna", getMishnaDefinition());
        return hebrewConjugationDefinitionsMap;
    }

    private List<String> getPielDefinition() {
        return Arrays.asList(new String[]{
                "PIEPER=>ti,t|t,|h,nw,tm|tn,w309"
        });
    }

    private List<String> getPaalDefinition() {
        return Arrays.asList(new String[]{
                "AIP=>ti,t|t,|h,nw,tm|tn,w309",
                "ARAIPR=>x-,x-,-,x-,x-,im000|in000",
                "AIF=>x-,x-,*i*@,x-,x-,x-"
        });
    }

    private List<String> getPaal2Definition() {
        return Arrays.asList(new String[]{
                "AIP=>ti,t|t,|h,nw,tm|tn,w309",
                "AIF=>foobar"
        });
    }

    private List<String> getHiphilDefinition() {
        return Arrays.asList(new String[]{
                "HIFPER=>*h*ti,*h*t|*h*t,*h*@|*h*h,*h*nw309,*h*tm|*h*tn,*h*w309"
        });
    }

    private List<String> getMishnaDefinition() {
        return Arrays.asList(new String[]{
                "IPR=>-,-,-,-,-,in000"
        });
    }

    private List<String> getPrepositions() {

        return Arrays.asList(new String[]{
                "aimti@prep()",
                "al@prep()%[eraseFollowingMinus]",
                "a62l@prep()%[eraseFollowingMinus]",
                "ala@prep()",
                "am@prep()",
                "ani@prep()",
                "a58n60i@prep()",
                "asr@prep()",
                "a59s29862r@prep()",
                "at@prep()",
                "a61t@prep()",
                "atm@prep()",
                "b@prep()",
                "b30558@prep()",
                "b30564@prep()",
                "b56@prep()",
                "b63@prep()",
                "bin000@prep()",
                "b61in000@prep()",
                "gm000@prep()",
                "g63m000@prep()",
                "h@prep()",
                "h59@prep()",
                "h60n61h@prep()",
                "hnh@prep()",
                "h62m000@prep()",
                "hm@prep()",
                "h6269@prep()",
                "h63@prep()",
                "h6369@prep()",
                "h64@prep()",
                "h6469@prep()",
                "h69@prep()",
                "hwa@prep()",
                "hw309a@prep()",
                "w@prep()%[wawConversiveForFutureAndPastVerbs]",
                "w63@prep()%[wawConversiveForFutureAndPastVerbs]",
                "w56@prep()%[wawConversiveForFutureAndPastVerbs]",
                "w309@prep()%[wawConversiveForFutureAndPastVerbs]",
                "zw@prep()",
                "k@prep()",
                "k56@prep()",
                "k31559@prep()",
                "k31560@prep()",
                "k31563@prep()",
                "k31564@prep()",
                "k3156369@prep()",
                "k3156469@prep()",
                "kdi@prep()",
                "ki@prep()",
                "k31560i@prep()",
                "k315i@prep()",
                "kl@prep()",
                "k31564l@prep()",
                "k64l@prep()",
                "kn@prep()",
                "l@prep()",
                "l56@prep()",
                "la@prep()",
                "l65a@prep()",
                "lbd@prep()",
                "lw331@prep()",
                "lw@prep()",
                "lmh@prep()",
                "m@prep()",
                "m60@prep()",
                "mad@prep()",
                "m56a65d@prep()",
                "mh@prep()",
                "m63h@prep()",
                "m64h@prep()",
                "mi@prep()",
                "m60i@prep()",
                "mn@prep()",
                "m60n000@prep()",
                "m00060n000@prep()",
                "yd@prep()",
                "y63d@prep()",
                "yl@prep()",
                "y63l@prep()",
                "ym@prep()",
                "y60m@prep()",
                "ym000@prep()",
                "y60m000@prep()",
                "ymm000@prep()",
                "y60m64m000@prep()",
                "s@prep()",
                "t'ht@prep()",
                "t63'h63t@prep()",
                "xxdexx@prep()",
                "xxtnxx@prep()",
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
        checkInMaps("wein18I1", translatorBridge);
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
    public void test_bereshit_chapter1() {
        checkInMaps("bereshit1A1", translatorBridge);
        checkInMaps("bereshit1A2", translatorBridge);
        checkInMaps("bereshit1B", translatorBridge);
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
        checkInMaps("rachitext1M", translatorBridge);
        checkInMaps("rachitext1N", translatorBridge);
        checkInMaps("rachitext1O", translatorBridge);
    }

    @Test
    public void test_failed_ones() {
        assertTrue(true);
        //checkInMaps("mishnah1I", translatorBridge);
        checkInMaps("toto", translatorBridge);
        checkInMaps("totoacc", translatorBridge);
    }

}

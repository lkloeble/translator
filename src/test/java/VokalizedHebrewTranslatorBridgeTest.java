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

/**
 * Created by lkloeble on 28/09/2017.
 */
public class VokalizedHebrewTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;
    String hebrewPathFile = "E:\\translator\\src\\test\\resources\\hebrew_content.txt";
    String hebrewResultFile = "E:\\translator\\src\\test\\resources\\hebrew_expected_results.txt";


    @Before
    public void init() {
        HebrewRuleFactory ruleFactory = new HebrewRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.HEBREW, new HebrewCaseFactory(), ruleFactory, getPrepositions());
        HebrewDeclensionFactory hebrewDeclensionFactory = new HebrewDeclensionFactory(getDeclensions(), getDeclensionList());
        NounRepository nounRepository = new NounRepository(Language.HEBREW, hebrewDeclensionFactory, getNouns());
        VerbRepository verbRepository = new VerbRepository(new HebrewConjugationFactory(getConjugations(),getHebrewConjugationsDefinitions()), Language.HEBREW, getVerbs());
        Analizer hebrewAnalyzer = new HebrewAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getHebDico(), getFrenchVerbs(), verbRepository, nounRepository, null, null,hebrewDeclensionFactory);
        translatorBridge = new TranslatorBridge(hebrewAnalyzer, frenchTranslator);
        mapValuesForTest = loadMapFromFiles(hebrewPathFile);
        mapValuesForResult = loadMapFromFiles(hebrewResultFile);
    }

    private List<String> getImElements() {
        return Arrays.asList(new String[]{"nomsg%sing%masc%"});
    }

    private List<Declension> getDeclensionList() {
        List<Declension> declensionList = new ArrayList<>();
        declensionList.add(new HebrewDeclension("im.txt",getImElements()));
        declensionList.add(new HebrewDeclension("im-fem.txt",getImfemElements()));
        declensionList.add(new HebrewDeclension("invfem.txt",getInvfemElements()));
        declensionList.add(new HebrewDeclension("invmasc.txt",getInvmascElements()));
        declensionList.add(new HebrewDeclension("im-ot.txt",getImOtElements()));
        declensionList.add(new HebrewDeclension("mascot.txt",getMascOtElements()));
        return declensionList;
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
                "nomfemsg%sing%fem%h",
                "nomfemplr%plr%fem%wt",
                "nommascsg%sing%masc%",
                "cst(nommascsg)%sing%masc%&",
                "decim-w%sing%masc%w",
                "nomplr%plr%masc%im",
                "cst(nomplr)%plr%masc%i&"
        });
    }

    private List<String> getInvmascElements() {
        return Arrays.asList(new String[]{"noma%sing%masc%","dir(noma)%sing%masc%h","nom%plr%masc%"});
    }

    private List<String> getImfemElements() {
        return Arrays.asList(new String[]{"nomsg%sing%fem%","nomplr%plr%fem%im"});
    }

    private List<String> getInvfemElements() {
        return Arrays.asList(new String[]{"nomsg%sing%fem%","cst(nomsg)%sing%fem%&"});
    }

    private List<String> getConjugations() {
        return Arrays.asList(new String[]{"paal%paal.txt"});
    }

    private List<String> getFrenchVerbs() {
        return Arrays.asList(
                new String[]{
                        "aller@NORM%[INFINITIVE]=[aller]%[IPR]=[vais,vas,va,allons,allez,vont]%[AIP]=[allai,allas,alla,allions,alliez,allèrent]%[PAP]=[allé]%[PAPR]=[allant]",
                        "appeler@NORM%[INFINITIVE]=[appeler]%[IPR]=[appele,appelles,appele,appelons,appelez,appelent]%[AIP]=[appelai,appelas,appela,appelâmes,appelâtes,appelèrent]%[PAP]=[appelé]%[PII]=[ai été appelé,as été appelé,a été appelé,avons été appelés,avez été appelé,ont été appelés]%[ACP]=[appelerais,appelerais,appelerait,appelerions,appeleriez,appeleraient]%[AIMP]=[appele,appelons,appelez]%[PIP]=[suis appelé,es appelé,est appelé,sommes appelés,êtes appelés,sont appelés]%[PEACIN]=[ai appelé,as appelé,a appelé,avonns appelés,avez appelés,ont appelés]%[PRPARPASS]=[être appelé,qui appelle à,qui appelle à,qui appelle à]%[ARAIPR]=[appelle,appelles,appelle,appellons,appellez,appellent]%[ACAOIN]=[appelai,appelas,appela,appelâmes,appelâtes,appelèrent]%[AIF]=[appelerai,appeleras,appelera,appelerons,appelerez,appeleront]",
                        "creer@NORM%[IPR]=[crée,crées,crée,créons,créez,créent]%[AIP]=[créai,créas,créa,créames,créates,créèrent]%[PAP]=[crée]%[INFINITIVE]=[créer]",
                        "dire@NORM%[INFINITIVE]=[dire]%[IPR]=[dis,dis,dit,disons,dîtes,disent]%[AIP]=[dis,dis,dit,dîmes,dîtes,dirent]%[PIP]=[suis dit,es dit,est dit,sommes dits, êtes dits, sont dit]%[AIP]=[x,dis,x,x,dites,x]%[AII]=[disais,disais,disait,disions,disiez,disaient]%[PAPR]=[disant]%[AIPP]=[ai dit,as dit,a dit,avons dit,avez dit,ont dit]%[ACAOIM]=[dis,dis,disons,dites]%[AIF]=[dirai,diras,dira,dirons,direz,diront]%[AIMP]=[-,dis,-,disons,dîtes,-]%[PAP]=[dit]%[ACAOIN]=[dis,dis,dit,dîmes,dîtes,dirent]%[ARAIPR]=[dis,dis,dit,disons,dîtes,disent]%[PAINPRMIPA]=[dire,dit,dite,dit]",
                        "donner@NORM%[INFINITIVE]=[donner]%[IPR]=[donne,donnes,donne,donnons,donnez,donnent]%[AIP]=[donnai,donnas,donna,donnâmes,donnâtes,donnèrent]%[PIP]=[suis donné,es donné,est donné,sommes donnés,êtes donnés,sont donnés]%[AIF]=[donnerai,donneras,donnera,donnerons,donnerez,donneront]%[PIF]=[serai donné,seras donné,sera donné,serons donnés,serez donnés,seront donnés]%[PAP]=[donné]%[AIMP]=[donne,donnez]%[PAPR]=[donnant]%[PALINF]=[donner]%[AIPP]=[ai donné,as donné,a donné,avons donné,avez donné,ont donné]",
                        "etre@IRREGULAR%[INFINITIVE]=[être]%[IPR]=[suis,es,est,sommes,êtes,sont]%[AII]=[étais,étais,était,étions,étiez,étaient]%[AIF]=[serai,seras,sera,serons,serez,seront]%[ASP]=[sois,sois,soit,soyons,soyez,soient]%[ASI]=[étais,étais,était,étions,étiez,étaient]%[AIP]=[fus,fus,fut,fûmes,fûtes,fûrent]%[AIMP]=[sois,soit,soyons,soyez,soient]%[AIPP]=[ai été,as été,a été,avons  été,avez été,ont été]%[PAPR]=[étant]%[ACP]=[serais,serais,serait,serions,seriez,seraient]%[PAP]=[été]%[IAP]=[avoir été]%[ACOPPR]=[serais,serais,serait,serions,seriez,seraient]%[AIFP]=[aurai été,auras été,aura été,aurons été,aurez été,auront été]",
                        "venir@NORM%[INFINITIVE]=[venir]%[IPR]=[viens,viens,vient,venons,venez,viennent]%[AIP]=[vins,vins,vint,vînmes,vîntes,vinrent]%[AII]=[venais,venais,venait,venions,veniez,venaient]%[AIF]=[viendrai,viendrais,viendra,viendrons,viendrez,viendront]%[AIMP]=[viens,viens,venez]%[AIPP]=[ai venu,as venu,a venu,avonsvenu,avez venu,ont venus]%[PAPR]=[venant]%[PAP]=[venu]",
                        "voir@NORM%[INFINITIVE]=[voir]%[IPR]=[vois,vois,voit,voyons,voyez,voient]%[AIP]=[vis,vis,vit,vîmes,vîtes,virent]%[PSP]=[sois vu,sois vu,soit vu,soyons vus,soyez vus,soient vus]%[AII]=[voyais,voyais,voyait,voyions,voyiez,voyaient]%[AIF]=[verrai,verras,verra,verrons,verrez,verront]%[PIF]=[serai vu, seras vu,sera vu,serons vus,serez vus,seront vus]%[PII]=[suis vu,es vu,est vu,sommes vus,êtes vus,sont vus]%[AIPP]=[ai vu,as vu,a vu,avons vu,avez vu,ont vu]%[PIF]=[serai vu,seras vu,sera vu,serons vus,serez vus,seront vus]%[PIP]=[suis vu,es vu,est vu,sommes vus,êtes vus,sont vus]%[AIMP]=[vois,vois,vois,voyons,voyez,voyez]%[PAP]=[vu]%[PAPR]=[voyant]%[ACAOIN]=[vis,vis,vit,vîmes,vîtes,virent]%[PAAOIM]=[que sois vu,que sois vu,que soit vu,que soyons vus,que soyez vus,que soient vus]%[AORPASIND]=[fus vu,fus vu,fut vu,fûmes vus,fûtes vus,furent vus]"
                }
        );
    }

    private List<String> getNouns() {

        return Arrays.asList(new String[]{
                "a58d64m64h@fem%custom(nomsg=admh|decim-nw=admtnw)",
                "a64d64m000@masc%invmasc",
                "a60is298@masc%custom(nomplr=ansim|cst(nomplr)=ansi&|dechsofit=aish)",
                "aw331r@fem%invfem",
                "al65h60im000@masc%custom(nomsg=alhim|cst(nomsg)=alhi&|decim-inwplr=alhinw)",
                "a6251r62ts@fem%custom(nomplr=artswt)",
                "a60s29864h@fem%custom(nomplr=nsim|cst(nomplr)=nsi&)",
                "g64dw331l@adj%im",
                "d64b64r@masc%im",
                "h61ik64l@masc%im",
                "ihwh@masc%invmasc",
                "iw331m000@masc%custom(nomplr=imim|cst(nomplr)=imi|cst(nomsg)=iwm&|nomsg=iwm)",
                "'hs29862k00056@fem%im-fem",
                "'h63k63m000@adj%im-ot",
                "l63i56l64h@fem%invfem",
                "l64i56l64h@fem%invfem",
                "m62l62k00056@masc%im",
                "ms29862h@masc%invmasc",
                "y60ir@fem%custom(nomplr=yrim|cst(nomplr)=yri&|dir(nomsg)=yirh|nomsg=yir)",
                "y64m000@masc%im",
                "y64p64r@fem%im-fem",
                "qw331l@masc%mascot",
                "ra65s298@fem%im",
                "r63y@adj%im-ot",
                "s29856mw309a61l@masc%invmasc",
                "s29864m6351i60m000@masc%invmasc",
                "xw331b@noun%im-ot"
        });
    }

    private List<String> getHebDico() {
        return Arrays.asList(new String[]{
                "a58d64m64h@noun!custom(nomsg=admh|decim-nw=admtnw)%1(noun)=terre",
                "a64d64m000@noun!invmasc%1(noun)=homme",
                "a60is298@noun!custom(nomplr=ansim|cst(nomplr)=ansi&|dechsofit=aish)%1(noun)=homme",
                "al@prep%1(prep)=vers",
                "a62l@prep%1(prep)=vers",
                "amr@verb!norm%1(verb)=dire",
                "aw331r@noun!invfem%1(noun)=flamme,feu%2(noun)=lumière",
                "al65h60im000@noun!custom(nomsg=alhim|cst(nomsg)=alhi&|decim-inwplr=alhinw)%1(noun)=dieu",
                "a6251r62ts@noun!custom(nomplr=artswt)%1(noun)=pays%2(noun)=terre",
                "asr@prep%1(prep)=qui",
                "a59s29862r@prep%1(prep)=qui",
                "a60s29864h@noun!custom(nomplr=nsim|cst(nomplr)=nsi&)%1(noun)=femme",
                "b@prep%1(prep)=dans",
                "b30558@prep%1(prep)=dans",
                "ba@verb!norm%1(verb)=venir",
                "bra@verb!norm%1(noun)=creer",
                "g64dw331l@adj!im%(adj)=grand",
                "d64b64r@noun!im%1(noun)=chose,acte%2(noun)=parole",
                "h@prep%1(prep)=le[les,la]",
                "h59@prep%1(prep)=le[les,la]",
                "h6269@prep%1(prep)=le[les,la]",
                "h63@prep%1(prep)=le[les,la]",
                "h6369@prep%1(prep)=le[les,la]",
                "h64@prep%1(prep)=le[les,la]",
                "h6469@prep%1(prep)=le[les,la]",
                "h69@prep%1(prep)=le[les,la]",
                "hiw@verb!irrg%1(verb)=etre",
                "h61ik64l@noun!im%1(noun)=palais",
                "hwa@prep%1(prep)=lui",
                "hw309a@prep%1(prep)=lui",
                "hlk@verb!norm%1(noun)=aller",
                "w@prep%1(prep)=et",
                "w56@prep%1(prep)=et",
                "'hs29862k00056@noun!im-fem%1(noun)=arrêt,pause%2(noun)=sombre,obscur%3(noun)=obscurité",
                "'h63k63m000@noun!im-ot%1(noun)=sage",
                "iw331m000@noun!custom(nomplr=imim|cst(nomplr)=imi|cst(nomsg)=iwm&nomsg=iwm)%1(noun)=jour",
                "ihwh@noun!invmasc%1(noun)=le seigneur%1(noun)=YHVH",
                "k@prep%1(prep)=comme",
                "k56@prep%1(prep)=comme",
                "k31559@prep%1(prep)=comme",
                "k31563@prep%1(prep)=comme",
                "k31564@prep%1(prep)=comme",
                "k3156369@prep%1(prep)=comme",
                "k3156469@prep%1(prep)=comme",
                "ki@prep%1(prep)=car%2(prep)=lorsque%3(prep)=pour que%4(prep)=que",
                "k31560i@prep%1(prep)=car%2(prep)=lorsque%3(prep)=pour que%4(prep)=que",
                "l@prep%1(prep)=à",
                "l56@prep%1(prep)=à",
                "l63i56l64h@noun!invfem%1(noun)=nuit",
                "l64i56l64h@noun!invfem%1(noun)=nuit",
                "la@prep%1(prep)=ne",
                "l65a@prep%1(prep)=ne",
                "m@prep%1(prep)=de",
                "m60@prep%1(prep)=de",
                "m62l62k00056@noun!im%1(noun)=roi",
                "mn@prep%1(prep)=de",
                "m60n000@prep%1(prep)=de",
                "m00060n000@prep%1(prep)=de",
                "ms29862h@noun!invmasc%1(noun)=moïse",
                "ntn@verb!norm%1(verb)=donner",
                "qw331l@noun!mascot%1(noun)=voix",
                "qra@verb!norm%1(verb)=crier%2(verb)=lire%3(verb)=appeler",
                "y60ir@noun!custom(nomplr=yrim|cst(nomplr)=yri&dir(nomsg)=yirh|nomsg=yir))%1(noun)=ville",
                "yl@prep%1(prep)=en faveur de%2(prep)=au dessus de%3(noun)=sur",
                "y63l@prep%1(prep)=en faveur de%2(prep)=au dessus de%3(noun)=sur",
                "y64p64r@noun!im-fem%1(noun)=jeune daim%2(noun)=gris,cendré%3(noun)=poussière",
                "y64m000@noun!im%1(noun)=peuple",
                "ra65s298@noun!im%1(noun)=tête",
                "rah@verb!norm%1(noun)=apparaitre%2(verb)=voir",
                "r63y@noun!im-ot%1(noun)=mauvais",
                "s29856mw309a61l@noun!invmasc%1(noun)=samuel",
                "s29864m6351i60m000@noun!invmasc%1(noun)=ciels",
                "xw331b@noun!im-ot%1(noun)=bon"
        });
    }

    private List<String> getVerbs() {
        return Arrays.asList(new String[]{
                "amr,,[paal],(AIF%leadingrootletter%a*a64@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@AIP%alternateaccentuation(5:9)%r*r64)",
                "ba,,[paal2],(AIP%leadingrootletter%b*b30564@AIP%alternateaccentuation(5)%a*a64))",
                "bra,,[paal],(AIP%leadingrootletter%*b30564@AIP%secondletterroot%r*r64@AIP%secondletterrootexception(5:9)%r*r56",
                "hiw@IRREGULAR%[AIP]=[,,h64i64h,,,hiw]%[AIF]=[,,ihi,,,,]%[PALFUT]=[,,ihi,,,,]%[AIMP]=[,,,hiw,]",
                "hlk,,[paal],(AIP%leadingrootletter%h*h64@AIP%secondletterroot%l*l63@AIP%sofitrootletter%k3*k000@AIP%sofitaccentuedletter%k000*k00056)",
                "ntn,,[paal],(AIP%leadingrootletter%n*n64@AIP%secondletterroot%t*t63@AIP%sofitrootletter%n3*n000)",
                "qra,,[paal],(AIP%leadingrootletter%q*q64@AIP%secondletterroot%r*r64@AIP%alternateaccentuation%q64*q6469@AIP%alternateaccentuation(5:9)%r64*r56@AIP%alternateaccentuation(5)%a*a64)",
                "rah,,[paal],(AIP%leadingrootletter%r*r64@AIP%secondletterroot%a*a64)"
        });
    }

    private Map<String, List<String>> getHebrewConjugationsDefinitions() {
        Map<String, List<String>> hebrewConjugationDefinitionsMap = new HashMap<>();
        hebrewConjugationDefinitionsMap.put("paal", getPaalDefinition());
        hebrewConjugationDefinitionsMap.put("paal2", getPaal2Definition());
        return hebrewConjugationDefinitionsMap;
    }

    private List<String> getPaalDefinition() {
        return Arrays.asList(new String[]{
                "AIP=>ti,ta|t,|h,nw,tm|tn,w",
                "AIF=>foobar"
        });
    }

    private List<String> getPaal2Definition() {
        return Arrays.asList(new String[]{
                "AIP=>ti,t|t,|h,nw,tm|tn,w",
                "AIF=>foobar"
        });
    }

    private List<String> getDeclensions() {
        return Arrays.asList(new String[]{
                "im%im.txt",
                "im-fem%im-fem.txt",
                "invmasc%invmasc.txt",
                "invfem%invfem.txt",
                "im-ot%im-ot.txt",
                "mascot%mascot.txt"
        });
    }

    private List<String> getPrepositions()
    {

        return Arrays.asList(new String[]{
                "al@prep()%[eraseFollowingMinus]",
                "a62l@prep()%[eraseFollowingMinus]",
                "asr@prep()",
                "a59s29862r@prep()",
                "b@prep()",
                "b30558@prep()",
                "h@prep()",
                "h59@prep()",
                "h6269@prep()",
                "h63@prep()",
                "h6369@prep()",
                "h64@prep()",
                "h6469@prep()",
                "h69@prep()",
                "hwa@prep()",
                "hw309a@prep()",
                "w@prep()%[wawConversiveForFutureAndPastVerbs]",
                "w56@prep()%[wawConversiveForFutureAndPastVerbs]",
                "k@prep()",
                "k56@prep()",
                "k31559@prep()",
                "k31563@prep()",
                "k31564@prep()",
                "k3156369@prep()",
                "k3156469@prep()",
                "ki@prep()",
                "k31560i@prep()",
                "l@prep()",
                "l56@prep()",
                "la@prep()",
                "l65a@prep()",
                "m@prep()",
                "m60@prep()",
                "mn@prep()",
                "m60n000@prep()",
                "m00060n000@prep()",
                "yl@prep()",
                "y63l@prep()"
        });
    }

    @Test
    public void test_weingreen_chapter_16() {
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
        checkInMaps("wein17H1", translatorBridge);
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
        checkInMaps("wein18T1", translatorBridge);
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

}

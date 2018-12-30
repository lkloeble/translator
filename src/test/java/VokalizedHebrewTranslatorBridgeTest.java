import org.junit.Before;
import org.junit.Test;
import patrologia.translator.TranslatorBridge;
import patrologia.translator.basicelements.*;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository;
import patrologia.translator.casenumbergenre.hebrew.HebrewCaseFactory;
import patrologia.translator.conjugation.hebrew.HebrewConjugationFactory;
import patrologia.translator.declension.Declension;
import patrologia.translator.declension.hebrew.HebrewDeclension;
import patrologia.translator.declension.hebrew.HebrewDeclensionFactory;
import patrologia.translator.linguisticimplementations.FrenchTranslator;
import patrologia.translator.linguisticimplementations.HebrewAnalyzer;
import patrologia.translator.linguisticimplementations.Translator;
import patrologia.translator.rule.hebrew.HebrewRuleFactory;
import patrologia.translator.utils.Analizer;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by lkloeble on 28/09/2017.
 */
public class VokalizedHebrewTranslatorBridgeTest extends TranslatorBridgeTest {

    private TranslatorBridge translatorBridge;

    private String localTestPath="C:\\Users\\laurent.kloeble\\IdeaProjects\\translator\\src\\test\\resources\\";
    private String localResourcesPath="C:\\Users\\laurent.kloeble\\IdeaProjects\\translator\\src\\main\\resources\\hebrew\\";
    private String localCommonPath="C:\\Users\\laurent.kloeble\\IdeaProjects\\translator\\src\\main\\resources\\";

    String nounFileDescription = localResourcesPath + "nouns.txt";
    String prepositionFileDescription = localResourcesPath + "prepositions.txt";
    String hebrewFrenchDataFile = localResourcesPath + "cohn_hebrew_to_french.txt";
    String frenchVerbsDataFile = localCommonPath + "french_verbs.txt";
    String hebrewPathFile = localTestPath + "hebrew_content.txt";
    String hebrewResultFile = localTestPath + "hebrew_expected_results.txt";


    @Before
    public void init() {
        HebrewRuleFactory ruleFactory = new HebrewRuleFactory();
        PrepositionRepository prepositionRepository = new PrepositionRepository(Language.HEBREW, new HebrewCaseFactory(), ruleFactory, getPrepositions(prepositionFileDescription));
        HebrewDeclensionFactory hebrewDeclensionFactory = new HebrewDeclensionFactory(getDeclensions(), getDeclensionList());
        NounRepository nounRepository = new NounRepository(Language.HEBREW, hebrewDeclensionFactory, new Accentuer(),getNouns(nounFileDescription));
        VerbRepository verbRepository = new VerbRepository(new HebrewConjugationFactory(getConjugations(), getHebrewConjugationsDefinitions(), nounRepository), Language.HEBREW, new Accentuer(),getVerbs());
        Analizer hebrewAnalyzer = new HebrewAnalyzer(prepositionRepository, nounRepository, verbRepository);
        Translator frenchTranslator = new FrenchTranslator(getHebDico(hebrewFrenchDataFile), getFrenchVerbs(), verbRepository, nounRepository, null, null, hebrewDeclensionFactory);
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

    private List<String> getNouns(String nounFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "gdl@adj%im-ot"
        });
        */
        return getFileContentForRepository(nounFileDescription);
    }

    private List<String> getHebDico(String dictionaryFile) {
        /*
        return Arrays.asList(new String[]{
                "wavend@prep%1(prep)=de lui"
        });
        */
        return getFileContentForRepository(dictionaryFile);
    }

    private List<String> getVerbs() {
        return Arrays.asList(new String[]{
                "abl,abwl,[paal]",
                "awr,hair,[binyanhifil],(BINHIPER%substitute(1:2:3:6:7:8)%awr*art*0@BINHIPER%substitute(4:5:9)%awr*air*0@BINHIPRE%substitute%awr*air*0)",
                "akl,,[paal],(AIP%leadingrootletter%a*a64@AIP%secondletterroot%k*k64@AIP%alternateaccentuation(5:9)%k64*k56@IPR%substitute%akl*awkl*0@ARAIPR%substitute%akl*awkl*0)",
                "akwl,,[paal]",
                "amn,,[binyanhufal],(BINHUPER%substitute(4)%hamna*hamn*0)",
                "amr,,[paal],(AIP%leadingrootletter%a*a64@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@AIP%alternateaccentuation(5)%r*r64@ARAIPR%substitute%amr*awmr*0@IPR%substitute%amr*awmr*0@ARAIPR%substitute%amr*awmr*0)",
                "ark,harik,[hiphil]",
                "ba,,[paal2],(AIP%leadingrootletter%b*b30564@AIP%alternateaccentuation(5)%a*a64))",
                "bar,,[piel]",
                "bdl,,[hiphil],(HIFPER%leadingrootletter%h*h60@HIFPER%secondletterroot%b*b56@HIFPER%alternateaccentuation(4:5:9)%d*d60i",
                "bwa,hbia,[hiphil]",
                "b'hr,,[paal],(IPR%substitute%b'h*bw'h*0)",
                "bra,,[paal],(AIP%leadingrootletter%*b30564@AIP%secondletterroot%r*r64@AIP%secondletterrootexception(5:9)%r*r56@AIP%alternateaccentuation%b30564*b64@IPR%substitute%br*bwr*0",
                "brk,,[binyan]",
                "grs,,[paal],(ARAIPR%substitute%grs*gwrs*0)",
                "dbr,,[piel],(PIEPER%leadingrootletter%d*d60@PIEPER%secondletterroot%b*b63@PIEPER%alternateaccentuation(4)%b63*b30562@PIEPER%alternateaccentuation(5:9)%b63*b30556)",
                "dnn,,[paal],(AIP%substitute%nn*n*0)",
                "drs,drws,[paal]",
                "dsa,,[hiphil]",
                "hiw@IRREGULAR%[AIP]=[hiiti,hiit,h64i64h|h64i56t64h,hiinw,,h64iw309]%[AIF]=[ahih,,ihi|ihih,nhih,,ihiw|thiinh]%[PALFUT]=[,,ihi,,,,]%[AIMP]=[,,,hiw,]",
                "hlk,,[paal],(AIP%leadingrootletter%h*h64@AIP%secondletterroot%l*l63@AIP%sofitrootletter%k3*k000@AIP%sofitaccentuedletter%k000*k00056@IPR%substitute%hl*hwl*0)",
                "psd,hpsid,[hiphil],(HIFPER%substitute(4:5:9)%hpsd*hpsid)",
                "hr'hiq,hr'hiq,[paal]",
                "'htm,'htwm,[paal]",
                "'hsk,'hswk,[paal]",
                "xbl,,[paal]",
                "xhr,hixhr,[nifal]",
                "xhr2,xhwr,[paal],(AIP%substitute%hr2*hr*0@)",
                "xma,,[nifal]",
                "zkh,,[paal],(AIP%substitute%kh*ki*0)",
                "zkr,zkwr,[paal],(IPR%substitute%zk*zwk*0@AIF%alternateaccentuation%zkr*zkwr*)",
                "zkr2,hzkir,[binyanhifil],(ARAPRE%substitute%kr2*kr*0@ARAPRE%substitute%kr*kir*0)",
                "zry,,[paal],(IPR%substitute%zr*zwr*0)",
                "zry2,,[hiphil],(HIFPART%substitute%y2*iy*0)",
                "idy,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%d*d63@AIP%alternateaccentuation(5:9)%d63*d56@AIP%alternateaccentuation(5)%y*y64@IPR%substitute%id*iwd*0)",
                "ikl,wkl,[paal],(ARAIPR%substitute%ikl*ikwl*0)",
                "inq,,[binyanhufal],(BINHUPER%substitute(4)%hinqa*hinq*0)",
                "ird,,[paal],(IPR%substitute%ir*iwr*0@ARAIPR%substitute%ird*iwrd*0)",
                "isb,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%s*s29863@AIP%alternateaccentuation(5:9)%s29863*s29856@AIP%alternateaccentuation(5)%b*b64@IPR%substitute%is*iws*0)",
                "itsa,,[paal],(AIP%leadingrootletter%i*i64@AIP%secondletterroot%ts*ts64@AIP%alternateaccentuation(5:9)%ts64*ts56@IPR%substitute%it*iwt*0)",
                "itsa2,,[binyanhufal],(HIFPART%substitute%sa2*sa*0@BINHUPER%substitute%sa2*sa*0@BINHUPER%substitute%its*ts*0@BINHUFUT%substitute%sa2*sa*0@BINHUFUT%substitute%its*ts*0)",
                "kbs,,[paal]",
                "kns,,[nifal]",
                "kpr,,[paal]",
                "krt,,[paal],(AIP%leadingrootletter%k*k31564@AIP%secondletterroot%r*r63@IPR%substitute%kr*kwr*0@ARAIPR%substitute%kr*kwr*0",
                "ksh,,[binyan],(BINPART%substitute%ksh*ks*0@BINPART%alternateaccentuation%ks*kws*0)",
                "ksh2,,[binyanpual],(BINHUPER%substitute%ksh2*kwsh*0)",
                "lmd,,[nifal]",
                "lq'h,,[paal],(AIP%leadingrootletter%l*l64@AIP%secondletterroot%q*q63@AIP%alternateaccentuation(5:9)%q63*q56@AIP%alternateaccentuation(5)%'h*'h64@IPR%substitute%lq*lwq*0)",
                "msl,,[paal],(AIP%leadingrootletter%m*m64@AIP%secondletterroot%s*s29863@IPR%substitute%ms*mws*0)",
                "mtsa,,[nifal]",
                "ngd,,[binyanhifil],(BINHIPER%substitute(1:2:3:6:7:8:9)%ngd*gd*0@BINHIPER%substitute(4:5)%ngd*gid*0@BINHIPER%substitute(4)%hgidh*hgid*0@BINHIPER%substitute(5)%hgidth*hgidh*0)",
                "ngy,,[hiphil],(HIFPER%substitute(1:2:3:6:7:8)%ng*g*0@HIFPER%substitute(4:5:9)%ng*gi*0)",
                "n'hl,,[paal],(IPR%substitute%n'h*nw'h*0)",
                "nia,,[binyanhifil]",
                "nkns,,[mishna]",
                "nxl,,[paal]",
                "nxh,,[paal2],(AIF%substitute%nxh*xh*0@AIF%substitute(3:6:7:8:9)%xh*x*0)",
                "nxh2,,[binyanhifil],(BINHIPER%substitute(1:2:3:6:7:8)%nxh2*xi*0@BINHIPER%substitute(4:5:9)%nxh2*x*0)",
                "nm,,[paal]",
                "ntn,tt,[paal],(AIP%leadingrootletter%n*n64@AIP%secondletterroot%t*t63@AIP%sofitrootletter%n3*n000@AIP%alternateaccentuation(5:9)%t63*t56@AIP%alternateaccentuation(5)%56n*56n64@IPR%substitute%nt*nwt*0@AIF%substitute%ntn*tn*0)",
                "ntn2,,[nifal],(AIMP%substitute%ntn2*ntn*0)",
                "nyr,,[paal]",
                "nsa,,[paal],(AIF%substitute%ns*s*0)",
                "ybd,,[paal],(IPR%substitute%yb*ywb*0)",
                "ybr,,[paal]",
                "ykb,,[binyan]",
                "ylh,,[paal],(IPR%substitute%yl*ywl*0@ARAIPR%substitute%ylh*ywlh*0)",
                "ymd,,[paal],(ARAIPR%substitute%ym*ywm*0@AIF%substitute%ymd*ymwd*0)",
                "ysh,yswt,[paal],(AIP%leadingrootletter%y*y64@AIP%secondletterroot%s*s29964@AIPSHORT%substitute%yshzz*iys*0@IPR%substitute%ys*yws*0)",
                "prs,,[piel]",
                "pt'h,,[paal],(IPR%substitute%pt*pwt*0)",
                "tswh,,[hitaip],(HITAIP%substitute%tswh*tsxw*0)",
                "qdm,,[binyanhifil],(BINHIPER%substitute(4:5:9)%qdm*qdim*0@BINHIPER%substitute(4)%dimh*dim*2)",
                "qwh,,[nifal],(NIFAIF%substitute%qwh*qw*0)",
                "q'hn,,[paal]",
                "qra,qrwt,[paal],(AIP%leadingrootletter%q*q64@AIP%secondletterroot%r*r64@AIP%alternateaccentuation%q64*q6469@AIP%alternateaccentuation(5:9)%r64*r56@AIP%alternateaccentuation(5)%a*a64@AIP%substitute(1:2:3:6:7:8)%qra*qri@ARAIPR%substitute%ra*wr*0@AIF%substitute(9)%qra*qr*0@IPR%substitute%qr*qwr*0@IPR%alternateaccentuation(8:9)%qwr*qr*0)",
                "rah,,[paal],(AIP%leadingrootletter%r*r64@AIP%secondletterroot%a*a64@AIP%alternateaccentuation(9)%a64*a@AIP%deleteletter(9)%h@CONVFUT%deleteletter%h@IPR%substitute%ra*rwa*0",
                "rdp,,[paal]",
                "r'hp,,[binyan],(BINPRE%leadingrootletter%m*m56@BINPRE%secondletterroot%r*r63@BINPRE%alternateaccentuation%'h*'h62@BINPRE%alternateaccentuation%p*p62",
                "sap,,[paal]",
                "skb,,[paal],(ARAIPR%substitute%sk*swk*0@ARAPAPR%substitute%sk*ski*0)",
                "skn,,[binyan],(BINPER%substitute%sk*sik*0)",
                "sl'h,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%l*l63@IPR%substitute%sl*swl*0)",
                "smy,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@IPR%substitute%sm*swm*0@ARAIPR%substitute%smy*swmy*0",
                "smr,,[paal],(AIP%leadingrootletter%s*s29864@AIP%secondletterroot%m*m63@AIP%alternateaccentuation(5:9)%m63*m56@IPR%substitute%sm*swm*0",
                "spx,,[paal]",
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
        hebrewConjugationDefinitionsMap.put("binyanpual", getBinyalPualDefinition());
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
                "BINPUPER=>ti,t|t,|h,nw,tm|tn,w309"
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

    private List<String> getPrepositions(String prepositionFileDescription) {
        /*
        return Arrays.asList(new String[]{
                "wavend@prep()"
        });
        */
        return getFileContentForRepository(prepositionFileDescription);
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
    }

    @Test
    public void englishBavliRashi() {
        checkInMaps("engbavli1Arashi001", translatorBridge);
        checkInMaps("engbavli1Arashi002", translatorBridge);
        checkInMaps("engbavli1Arashi003", translatorBridge);
        checkInMaps("engbavli1Arashi004", translatorBridge);
        checkInMaps("engbavli1Arashi005", translatorBridge);
        checkInMaps("engbavli1Arashi006", translatorBridge);
        checkInMaps("engbavli1Arashi007", translatorBridge);
        checkInMaps("engbavli1Arashi008", translatorBridge);
        checkInMaps("engbavli1Arashi009", translatorBridge);
        checkInMaps("engbavli1Arashi010", translatorBridge);
        checkInMaps("engbavli1Arashi011", translatorBridge);
        checkInMaps("engbavli1Arashi012", translatorBridge);
        checkInMaps("engbavli1Arashi013", translatorBridge);
        checkInMaps("engbavli1Arashi014", translatorBridge);
        checkInMaps("engbavli1Arashi015", translatorBridge);
        checkInMaps("engbavli1Arashi016", translatorBridge);
        checkInMaps("engbavli1Arashi017", translatorBridge);
        checkInMaps("engbavli1Arashi018", translatorBridge);
        checkInMaps("engbavli1Arashi019", translatorBridge);
        checkInMaps("engbavli1Arashi020", translatorBridge);
    }


    @Test
    public void englishYeroushalmi() {
        checkInMaps("engyerou1Apneimoshe1", translatorBridge);
        checkInMaps("engyerou1Apneimoshe2", translatorBridge);
        checkInMaps("engyerou1Apneimoshe3", translatorBridge);
        checkInMaps("engyerou1Apneimoshe4A", translatorBridge);
        checkInMaps("engyerou1Apneimoshe4B", translatorBridge);
        checkInMaps("engyerou1Apneimoshe5", translatorBridge);
        checkInMaps("engyerou1Apneimoshe6", translatorBridge);
        checkInMaps("engyerou1Apneimoshe7", translatorBridge);
        checkInMaps("engyerou1Apneimoshe8", translatorBridge);
        checkInMaps("engyerou1Apneimoshe9", translatorBridge);
        checkInMaps("engyerou1Apneimoshe10", translatorBridge);
        checkInMaps("engyerou1Apneimoshe11", translatorBridge);
        checkInMaps("engyerou1Apneimoshe12", translatorBridge);
        checkInMaps("engyerou1Apneimoshe13", translatorBridge);
        checkInMaps("engyerou1Apneimoshe14", translatorBridge);
        checkInMaps("engyerou1Apneimoshe15", translatorBridge);
        checkInMaps("engyerou1Apneimoshe16", translatorBridge);
        checkInMaps("engyerou1Apneimoshe17", translatorBridge);
        checkInMaps("engyerou1Apneimoshe18", translatorBridge);
        checkInMaps("engyerou1Apneimoshe19", translatorBridge);
        checkInMaps("engyerou1Apneimoshe20", translatorBridge);
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
        checkInMaps("hebbavli1ApagecommentQ", translatorBridge);
        checkInMaps("hebbavli1ApagecommentR", translatorBridge);
        checkInMaps("hebbavli1ApagecommentS", translatorBridge);
        checkInMaps("hebbavli1ApagecommentT", translatorBridge);
        checkInMaps("hebbavli1ApagecommentU", translatorBridge);
        checkInMaps("hebbavli1ApagecommentV", translatorBridge);
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
        checkInMaps("midrashrabaeng1L", translatorBridge);
        checkInMaps("midrashrabaeng1M", translatorBridge);
        checkInMaps("midrashrabaeng1N", translatorBridge);
        checkInMaps("midrashrabaeng1O", translatorBridge);
        checkInMaps("midrashrabaeng1P", translatorBridge);
        checkInMaps("midrashrabaeng1Q", translatorBridge);
        checkInMaps("midrashrabaeng1R", translatorBridge);
        checkInMaps("midrashrabaeng1S", translatorBridge);
        checkInMaps("midrashrabaeng1T", translatorBridge);
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
        checkInMaps("midrashrabahebcom1M", translatorBridge);
        checkInMaps("midrashrabahebcom1N", translatorBridge);
        checkInMaps("midrashrabahebcom1O", translatorBridge);
        checkInMaps("midrashrabahebcom1P", translatorBridge);
    }

    @Test
    public void test_failed_ones() {
        assertTrue(true);
        checkInMaps("bereshit1Q", translatorBridge);
        //checkInMaps("toto", translatorBridge);
        //checkInMaps("totoacc", translatorBridge);
    }

}

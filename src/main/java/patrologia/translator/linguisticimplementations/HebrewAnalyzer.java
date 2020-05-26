package patrologia.translator.linguisticimplementations;

import patrologia.translator.basicelements.Analysis;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.Phrase;
import patrologia.translator.basicelements.modificationlog.DefaultModificationLog;
import patrologia.translator.basicelements.noun.NounRepository;
import patrologia.translator.basicelements.preposition.PrepositionRepository;
import patrologia.translator.basicelements.verb.VerbRepository2;
import patrologia.translator.casenumbergenre.AvoidCaseOperator;
import patrologia.translator.casenumbergenre.CaseOperatorContainer;
import patrologia.translator.casenumbergenre.hebrew.HebrewDirectionalCase;
import patrologia.translator.rule.hebrew.HebrewRuleFactory;
import patrologia.translator.utils.Analyzer;
import patrologia.translator.utils.PhraseAnalizer;
import patrologia.translator.utils.WordAnalyzer;
import patrologia.translator.utils.WordSplitter;

public class HebrewAnalyzer implements Analyzer {

    private WordSplitter wordSplitter = new WordSplitter();
    private WordAnalyzer wordAnalyzer = null;
    private PhraseAnalizer phraseAnalizer = new PhraseAnalizer();

    public HebrewAnalyzer(PrepositionRepository prepositionRepository, NounRepository nounRepository, VerbRepository2 verbRepository) {
        CaseOperatorContainer caseOperatorContainer = new CaseOperatorContainer(nounRepository,prepositionRepository);
        caseOperatorContainer.addCaseOperator(new AvoidCaseOperator(new HebrewDirectionalCase("direction")));
        wordAnalyzer = new WordAnalyzer(prepositionRepository, nounRepository, verbRepository, new HebrewPhraseChanger(nounRepository, verbRepository, prepositionRepository, new HebrewRuleFactory()), new DefaultModificationLog(), new CustomRule(), caseOperatorContainer, Language.HEBREW);
    }

    public Analysis analyze(String sentence) {
        String characterAlphaOnly = replaceHebrewWithAlpha(sentence);
        String expressionsHandled = replaceExpressions(characterAlphaOnly);
        Phrase phrase = wordSplitter.splitSentence(expressionsHandled, Language.HEBREW, new HebrewWordSplitterPattern());
        phrase = wordAnalyzer.affectAllPossibleInformations(phrase);
        return phraseAnalizer.affectAllPossibleInformationsBetweenWords(Language.HEBREW, phrase);
    }

    private String replaceExpressions(String sentence) {
        String replace1 = sentence.replace("hqb \" h", "hqbh");
        replace1 = replace1.replace("yp?'","ypprep");
        replace1 = replace1.replace("qm?l","qmlexpr");
        replace1 = replace1.replace("'hz?l","'hzlexpr");
        replace1 = replace1.replace("'hid?a","'hidaexpr");
        replace1 = replace1.replace("r63s29860?i","rashiexpr");
        replace1 = replace1.replace("zts?l","ztslexpr");
        replace1 = replace1.replace("pr?a","praexpr");
        replace1 = replace1.replace("r?a","raexpr");
        replace1 = replace1.replace("z?l","zlexpr");
        return replace1.replace("hqb?h", "hqbhexpr");
    }


    private String replaceHebrewWithAlpha(String sentence) {
        if(sentence == null) return "";
        StringBuilder sb = new StringBuilder();
        char[] chars = sentence.toCharArray();
        for (char c : chars) {
            int i = (int) c;
            switch (i) {
                case 34://"
                    sb.append("?");
                    break;
                case 39://(
                    sb.append("'");
                    break;
                case 40://(
                    sb.append(" ( ");
                    break;
                case 41://(
                    sb.append(" ) ");
                    break;
                case 44://,
                    sb.append(" , ");
                    break;
                case 45://-
                    sb.append("& ");
                    break;
                case 46://.
                    sb.append(" . ");
                    break;
                case 49://1
                    sb.append("1");
                    break;
                case 58://.
                    sb.append(" : ");
                    break;
                case 59://;
                    sb.append(" ; ");
                    break;
                case 63://?
                    sb.append("?");
                    break;
                case 64://@
                    sb.append("@");
                    break;
                case 86://V
                    sb.append("V");
                    break;
                case 1425://signe sous lettre
                    break;
                case 1428://signe sous lettre
                    break;
                case 1429://signe sous lettre
                    break;
                case 1430://signe sous lettre
                    break;
                case 1431://signe sous lettre
                    break;
                case 1433://signe sous lettre
                    break;
                case 1434://signe sous lettre
                    break;
                case 1435://signe sous lettre
                    break;
                case 1436://signe sous lettre
                    break;
                case 1438://signe sous lettre
                    break;
                case 1440://signe sous lettre
                    break;
                case 1443://signe (bizarre) sous lettre
                    break;
                case 1444://signe sous lettre
                    break;
                case 1445://signe sous lettre
                    break;
                case 1447://signe sous lettre
                    break;
                case 1448://signe sous lettre
                    break;
                case 1451://signe < sur lettre
                    //sb.append("51");
                    break;
                case 1456://signe : sous lettre
                    sb.append("56");
                    break;
                case 1457://signe a sous lettre
                    break;
                case 1458://signe a sous lettre
                    sb.append("58");
                    break;
                case 1459://signe a sous lettre
                    sb.append("59");
                    break;
                case 1460://signe i sous lettre
                    sb.append("60");
                    break;
                case 1461://signe .. sous lettre
                    sb.append("61");
                    break;
                case 1462://signe e sous lettre sous forme de trois points en triangle
                    sb.append("62");
                    break;
                case 1463://signe a sous lettre
                    sb.append("63");
                    break;
                case 1464://signe a sous lettre en forme de T
                    sb.append("64");
                    break;
                case 1465://signe o sur lettre
                    sb.append("65");
                    break;
                case 1467://signe ou sous lettre 1467
                    sb.append("67");
                    break;
                case 1468://signe sous lettre
                    break;
                case 1469://signe | sous lettre
                    //sb.append("69");
                    break;
                case 1470://sorte de minus supérieur
                    sb.append(" ");
                    break;
                case 1473://signe sous lettre
                    break;
                case 1474://signe sous lettre
                    break;
                case 1488://aleph
                    sb.append("a");
                    break;
                case 1489://beth
                    sb.append("b");
                    break;
                case 1490://guimel
                    sb.append("g");
                    break;
                case 1491://daleth
                    sb.append("d");
                    break;
                case 1492://hé
                    sb.append("h");
                    break;
                case 1493://waw
                    sb.append("w");
                    break;
                case 1494://zayin
                    sb.append("z");
                    break;
                case 1495://'heth
                    sb.append("'h");
                    break;
                case 1496://teth
                    sb.append("x");//x pour différencier avec tav qui reste t
                    break;
                case 1497://iod
                    sb.append("i");
                    break;
                case 1498://kaf sofit
                    sb.append("k000");
                    break;
                case 1499://kaf
                    sb.append("k");
                    break;
                case 1500://lamed
                    sb.append("l");
                    break;
                case 1501://mem sofit
                    sb.append("m000");
                    break;
                case 1502://mem
                    sb.append("m");
                    break;
                case 1503://noun4 sofit
                    sb.append("n000");
                    break;
                case 1504://noun
                    sb.append("n");
                    break;
                case 1505://samer
                    sb.append("s");
                    break;
                case 1506://ayin
                    sb.append("y");
                    break;
                case 1507://pé sofit
                    sb.append("p");
                    break;
                case 1508://pé
                    sb.append("p");
                    break;
                case 1509://tsadé sofit
                    sb.append("ts");
                    break;
                case 1510://tsadé
                    sb.append("ts");
                    break;
                case 1511://quouf
                    sb.append("q");
                    break;
                case 1512://resh
                    sb.append("r");
                    break;
                case 1513://shin
                    sb.append("s");
                    break;
                case 1514://tav
                    sb.append("t");
                    break;
                case 64331://waw with o
                    sb.append("w331");
                    break;
                case 64298://shin with right point
                    sb.append("s298");
                    break;
                case 64299://shin with left point
                    sb.append("s299");
                    break;
                case 64305://b with central point
                    sb.append("b305");
                    break;
                case 64309://wav with central point for ou
                    sb.append("w309");
                    break;
                case 64315://kaf with central point
                    sb.append("k315");
                    break;
                default:
                    if (i != 32) {
                        System.out.println("caractère hébreu accentué non pris en charge.");
                    }
                    sb.append(" ");
                    break;
            }
        }
        return sb.toString().trim();
    }


}


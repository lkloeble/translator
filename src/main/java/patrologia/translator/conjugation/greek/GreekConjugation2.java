package patrologia.translator.conjugation.greek;

import patrologia.translator.conjugation.Conjugation2;
import patrologia.translator.declension.DeclensionFactory;

import java.util.List;

/*
 * ACAOIM => Active Aoriste Imperative
 * MIDPASSIMP => MIDdle PASSive IMPerative
 * ACAOIN => Active Aoriste Indicatif
 * ACAOINBIS => Active Aoriste Indicatif
 * ACOPPR => ACtive OPtative PResent
 * PAAOIM => Passive AOrist IMpÃ©rative
 * MIAOIN => MIddle AOrist INdicative
 * PAINPRAC => PArticiple INfinitive PResent ACtive
 * PPP => Present Passive Participle
 * PAINPRMI => PArticiple INfinitive PResent PAssive
 * PEPASPAR => PErfect PASsive PARticiple
 * PERACTPAR => PERfect ACTive PARticiple
 * AORPASOPT =>AORist PASsive OPTative
 * AORPASIND =>AORist PASsive INDicative
 * AORACTINF => AORist ACTive INFinitive
 * AORPASSIMP => AORist PASSive IMPerfect
 * PERMIDPASINF => PERfect MIDdle/PASsive INFinitive
 * PARAORINFACT => PARticipe AORist INFinitif ACTif
 * PARAORINFPAS => PARticipe AORist INFinitif PASsif
 * AORPASDEP => AORiste PASsive DEPonent
 * AORPASSUB => AORist PASsive SUBjonctive
 * MFI => Middle Future Indicatif
 * MIDDPASSIND => MIDDle PASSive INDicatif
 * AORPASSPART => AORist PARTiciple PASSive
 * FUTPARTACT => FUTure PARticiple INFinitive
 * ACAOOP => ACtive AOrist OPtative
 * AORMIDDIND => AORist MIDDle INDicative
 * IMPMIDPASSIND => IMPerfect MIddlePASSive INDicative
 * PRESACTPART => PREsent ACTive PARTiciple
 * AORACTPART => AORist ACTive PARTiciple
 * PRESPASPART => PREsent PASsive PARTiciple
 * PRMIDPASSPART => PREsent MID PASsive PARTiciple
 * PRMIDPASSDEPPART => PResent MIDdle/PASSive DEPonent PARTiciple
 * PERFMIDPASSINDI => PERFect MIDdle PASSive INDIcative
 * MIDPASSSUBJPRE => MIDdle PASsive SUBjonctive PREsent
 * MIDPASSPLUPERFIND => MIDdle PASsive PLUPERfect INDicative
 * PRESPASSINF => PRESent PASSive INFinitive
 * PERFMIDPASSSUBJ => PERFect MIDdle PASSive SUBJonctive
 * PERFACTIND => PERFect ACTive INDicative
 * AORACTSUBJ => AORiste ACTive SUBJonctive
 * PERFMIDPART => PERFect MIDdle PARTiciple
 * AORINFMID => verb aor inf mid ionic
 * PARTMIDAOR => PARTicipe MIDdle AORist
 * AORICONJ => AORIst CONJugation
 * AORMIDIMP => AORist MIDdle IMPerative
 */
public class GreekConjugation2 extends Conjugation2 {

    public GreekConjugation2(String conjugationName, List<String> conjugationDescription, DeclensionFactory declensionFactory) {
        this.conjugationName=conjugationName;
        this.declensionFactory = declensionFactory;
        processConjugationDescription(conjugationDescription);
        processRelationToNoun(conjugationDescription);
    }

}

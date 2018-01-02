package org.patrologia.translator.conjugation.greek;

import org.patrologia.translator.conjugation.Conjugation;
import org.patrologia.translator.conjugation.ConjugationLoader;
import org.patrologia.translator.conjugation.VerbDefinition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 * IPR => Active InNicative Present
 * ACAOIM => Active Aoriste Imperative
 * ACAOIN => Active Aoriste Indicatif
 * ACOPPR => ACtive OPtative PResent
 * PAAOIM => Passive AOrist IMpérative
 * MIAOIN => MIddle AOrist INdicative
 * PAINPRAC => PArticiple INfinitive PResent ACtive
 * PAINPRMIPA => PArticiple INfinitive PResent MIddlePAssive
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
 * AORPASSPART => AORist PARTiciple PASSive
 * FUTPARTACT => FUTure PARticiple INFinitive
 * ACAOOP => ACtive AOrist OPtative
 * AORMIDDIND => AORist MIDDle INDicative
 * IMPMIDPASSIND => IMPerfect MIddlePASSive INDicative
 * PRESACTPART => PREsent ACTive PARTiciple
 * PRESPASPART => PREsent PASsiveive PARTiciple
 * PRMIDPASSDEPPART => PResent MIDdle/PASSive DEPonent PARTiciple
 */
public class GreekConjugation extends Conjugation {

    protected static List<String> times = Arrays.asList(new String[]{"IPR","AII","ACAOIM","ACAOIN","PEACIN","PRPARPASS","PII","ACOPPR","PAAOIM","MIAOIN","PAINPRAC","PEPASPAR","AORPASOPT","AORPASIND","AORACTINF","PASANT","AORPASSPART","PARAORINFACT","AORPASDEP","PARAORINFPAS","AORPASSPART","ACAOOP","AORMIDDIND","FUTPARTACT","IMPMIDPASSIND","PRESACTPART","PRMIDPASSDEPPART","PRESPASPART","PERACTPAR"});

    private String conjugationFilePath;
    private ConjugationLoader conjugationLoader = new ConjugationLoader();

    public GreekConjugation(List<String> conjugationElements, VerbDefinition verbDefinition) {
        this.verbDefinition = verbDefinition;
        initializeMap(conjugationElements);
    }

    protected GreekConjugation() {}

    private void initializeMap(List<String> conjugationElements) {
        allEndings = conjugationLoader.loadConjugation(conjugationElements);
    }

    @Override
    public List<String> getCongujationByTimePattern(String timePattern) {
        if(timePattern != null && !times.contains(timePattern)) {
            return Collections.EMPTY_LIST;
        }
        return allEndings.get(timePattern) != null ? allEndings.get(timePattern) : Collections.EMPTY_LIST;
    }
}

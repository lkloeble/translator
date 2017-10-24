package org.patrologia.translator;

import org.patrologia.translator.basicelements.Analysis;
import org.patrologia.translator.basicelements.DefaultFinalModifier;
import org.patrologia.translator.basicelements.NullTranslation;
import org.patrologia.translator.basicelements.Translation;

/**
 * Created by Laurent KLOEBLE on 01/09/2015.
 */
public class NullTranslatorRepository implements TranslatorRepository {

    public Translation computeReaderTranslation(Analysis analysis) {
        return new NullTranslation(new DefaultFinalModifier());
    }

    public Translation computeStudyTranslation(Analysis analysis) {
        return new NullTranslation(new DefaultFinalModifier());
    }
}

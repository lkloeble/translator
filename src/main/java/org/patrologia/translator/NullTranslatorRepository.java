package patrologia.translator;

import patrologia.translator.basicelements.Analysis;
import patrologia.translator.basicelements.modifier.DefaultFinalModifier;
import patrologia.translator.basicelements.NullTranslation;
import patrologia.translator.basicelements.Translation;

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

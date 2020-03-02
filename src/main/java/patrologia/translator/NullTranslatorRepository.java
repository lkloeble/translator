package patrologia.translator;

import patrologia.translator.basicelements.Analysis;
import patrologia.translator.basicelements.NullTranslation;
import patrologia.translator.basicelements.Translation;
import patrologia.translator.basicelements.modifier.DefaultFinalModifier;

public class NullTranslatorRepository  implements TranslatorRepository {

    public Translation computeReaderTranslation(Analysis analysis) {
        return new NullTranslation(new DefaultFinalModifier());
    }

    public Translation computeStudyTranslation(Analysis analysis) {
        return new NullTranslation(new DefaultFinalModifier());
    }
}

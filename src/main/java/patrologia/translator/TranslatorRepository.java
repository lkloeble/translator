package patrologia.translator;

import patrologia.translator.basicelements.Analysis;
import patrologia.translator.basicelements.Translation;

public interface TranslatorRepository {

    Translation computeReaderTranslation(Analysis analysis);
}

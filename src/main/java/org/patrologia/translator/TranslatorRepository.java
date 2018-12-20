package patrologia.translator;

import patrologia.translator.basicelements.Analysis;
import patrologia.translator.basicelements.Translation;

/**
 * Created by Laurent KLOEBLE on 01/09/2015.
 */
public interface TranslatorRepository {

    Translation computeReaderTranslation(Analysis analysis);
}

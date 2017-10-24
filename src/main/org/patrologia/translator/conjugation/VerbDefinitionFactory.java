package org.patrologia.translator.conjugation;

import org.patrologia.translator.basicelements.Language;
import org.patrologia.translator.conjugation.english.EnglishVerbDefinition;
import org.patrologia.translator.conjugation.german.GermanVerbDefinition;
import org.patrologia.translator.conjugation.greek.GreekVerbDefinition;
import org.patrologia.translator.conjugation.hebrew.HebrewVerbDefinition;
import org.patrologia.translator.conjugation.latin.LatinIrregularVerbDefinition;
import org.patrologia.translator.conjugation.latin.LatinVerbDefinition;
import org.patrologia.translator.conjugation.romanian.RomanianVerbDefinition;

/**
 * Created by Laurent KLOEBLE on 14/10/2015.
 */
public class VerbDefinitionFactory {

    public VerbDefinition getVerbDefinition(Language language, String definition) {
        if(language == null) {
            return new NullVerbDefinition();
        }
        switch(language) {
            case LATIN:
                return new LatinVerbDefinition(definition);
            case GREEK:
                return new GreekVerbDefinition(definition);
            case HEBREW:
                return new HebrewVerbDefinition(definition);
            case ROMANIAN:
                return new RomanianVerbDefinition(definition);
            case GERMAN:
                return new GermanVerbDefinition(definition);
            case ENGLISH:
                return new EnglishVerbDefinition(definition);
        }
        return new NullVerbDefinition();
    }

    public IrregularVerbDefinition getIrregularVerbDefinition(Language language, String definition) {
        if(language == null) {
            return new NullIrregularVerbDefinition();
        }
        switch(language) {
            case LATIN:
            case ROMANIAN:
            case GERMAN:
            case ENGLISH:
            case GREEK:
            case HEBREW:
                return new LatinIrregularVerbDefinition(definition);
        }
        return new NullIrregularVerbDefinition();

    }
}

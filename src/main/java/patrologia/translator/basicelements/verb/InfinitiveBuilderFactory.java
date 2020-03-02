package patrologia.translator.basicelements.verb;

import patrologia.translator.basicelements.Language;

public class InfinitiveBuilderFactory {

    public static InfinitiveBuilder getInfinitiveBuilder(Language language) {
        if(language.equals(Language.ROMANIAN)) {
            return new RomanianInfinitiveBuilder();
        }
        return new DefaultInfinitiveBuilder();
    }

}

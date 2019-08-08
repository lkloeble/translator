package patrologia.translator.basicelements.verb;

import patrologia.translator.basicelements.Accentuer;
import patrologia.translator.basicelements.Language;
import patrologia.translator.basicelements.TranslationInformationReplacement;
import patrologia.translator.conjugation.*;

import java.util.List;

public class VerbRepository2 {

    private ConjugationFactory conjugationFactory;
    private Language language;
    private Accentuer accentuer;
    private VerbDefinitionFactory verbDefinitionFactory = new VerbDefinitionFactory();

    private ConjugationMap conjugationMap;

    public VerbRepository2(ConjugationFactory conjugationFactory, Language language, Accentuer accentuer, List<String> definitionList) {
        this.conjugationFactory = conjugationFactory;
        this.language = language;
        this.accentuer = accentuer;
        this.conjugationMap = new ConjugationMap(accentuer, new DefaultInfinitiveBuilder());
        definitionList.stream().forEach(definition -> addVerb(definition));
    }

    public boolean hasVerb(String initialValue) {
        return conjugationMap.containsKey(initialValue);
    }

    private void addVerb(String definition) {
        VerbDefinition verbDefinition = verbDefinitionFactory.getVerbDefinition(language, definition);
        Conjugation2 conjugation = conjugationFactory.getConjugationByPattern(verbDefinition);
        conjugation.getTimes().stream().forEach(time -> addAllConjugationAndRoot(time, conjugation, verbDefinition.getBaseConjugationRoot(),verbDefinition));
    }

    private void addAllConjugationAndRoot(String time, Conjugation2 conjugation, String baseConjugationRoot, VerbDefinition verbDefinition) {
        String valuesAllInOne = conjugation.getRootWithEveryEndingsByTime(baseConjugationRoot, time);
        String[] values = valuesAllInOne.split(",");
        TranslationInformationReplacement2 translationInformationReplacement = verbDefinition.getTranslationInformationReplacement2();
        int indice = 0;
        for (String value : values) {
            conjugationMap.put(translationInformationReplacement.replace(time,value,ConjugationPosition.getValueByPosition(indice++)), baseConjugationRoot);
        }
    }
}

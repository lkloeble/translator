package patrologia.translator.basicelements.noun;

import patrologia.translator.basicelements.Accentuer;
import patrologia.translator.basicelements.PairConstructionGender;
import patrologia.translator.casenumbergenre.Gender;

import java.util.*;

public class NounMap {

    private HashMap<PairConstructionGender, Noun> internalMap = new HashMap<PairConstructionGender, Noun>();
    private Map<String, Set<String>> declensionsForConstructions = new HashMap<>();
    private Accentuer accentuer = new Accentuer();

    public Noun get(String construction, String declension, Gender gender) {
        return internalMap.get(new PairConstructionGender(construction, declension,gender));
    }

    public boolean containsKey(String construction, String declension, Gender gender) {
        return internalMap.containsKey(new PairConstructionGender(construction, declension, gender));
    }

    public Collection<Noun> values() {
        return internalMap.values();
    }

    public void put(String construction, Noun noun, String declension, Gender gender) {
        if(!declensionsForConstructions.containsKey(construction)) {
            HashSet<String> declensions = new HashSet<>();
            declensions.add(declension);
            declensionsForConstructions.put(construction, declensions);
        } else {
            Set<String> declensions = declensionsForConstructions.get(construction);
            declensions.add(declension);
            declensionsForConstructions.put(construction, declensions);
        }
        internalMap.put(new PairConstructionGender(construction, declension, gender), noun);
    }

    public Collection<Noun> get(String construction, boolean lastCall) {
        Collection<Noun> nouns = new ArrayList<>();
        Set<String> declensions = declensionsForConstructions.get(construction);
        if(declensions == null && lastCall) return get(accentuer.unaccentuedWithSofit(construction), false);
        if(declensions == null && !construction.contains("&")) return get(construction + "&",true);
        addNounByGenderAndContruction(construction, declensions, new Gender(Gender.MASCULINE), nouns);
        addNounByGenderAndContruction(construction, declensions, new Gender(Gender.FEMININE), nouns);
        addNounByGenderAndContruction(construction, declensions, new Gender(Gender.NEUTRAL), nouns);
        addNounByGenderAndContruction(construction, declensions, new Gender(Gender.ADJECTIVE), nouns);
        return nouns.size() == 0  && !construction.equals(unaccentuedWithSofit(construction)) ? get(unaccentuedWithSofit(construction),true) : nouns;
    }

    private String unaccentuedWithSofit(String value) {
        return accentuer.unaccentuedWithSofit(value);
    }

    private void addNounByGenderAndContruction(String construction, Set<String> declensions, Gender gender, Collection<Noun> nouns) {
        if(declensions == null) return;
        for(String declension : declensions) {
            PairConstructionGender key = new PairConstructionGender(construction, declension, gender);
            if (internalMap.containsKey(key)) {
                nouns.add(internalMap.get(key));
            }
        }
    }

}

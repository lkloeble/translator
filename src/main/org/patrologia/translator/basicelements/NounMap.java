package org.patrologia.translator.basicelements;

import org.patrologia.translator.casenumbergenre.Gender;

import java.util.*;

/**
 * Created by lkloeble on 20/06/2016.
 */
public class NounMap {

    private HashMap<PairConstructionGender, Noun> internalMap = new HashMap<PairConstructionGender, Noun>();
    private Map<String, Set<String>> declensionsForConstructions = new HashMap<>();
    private Repository repository = new Repository();

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

    public Collection<Noun> get(String construction) {
        Collection<Noun> nouns = new ArrayList<>();
        Set<String> declensions = declensionsForConstructions.get(construction);
        addNounByGenderAndContruction(construction, declensions, new Gender(Gender.MASCULINE), nouns);
        addNounByGenderAndContruction(construction, declensions, new Gender(Gender.FEMININE), nouns);
        addNounByGenderAndContruction(construction, declensions, new Gender(Gender.NEUTRAL), nouns);
        addNounByGenderAndContruction(construction, declensions, new Gender(Gender.ADJECTIVE), nouns);
        return nouns.size() == 0  && !construction.equals(unaccentuedWithSofit(construction)) ? get(unaccentuedWithSofit(construction)) : nouns;
    }

    private String unaccentuedWithSofit(String value) {
        return repository.unaccentuedWithSofit(value);
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

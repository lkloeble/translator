package org.patrologia.translator.casenumbergenre;

import java.util.List;

/**
 * Created by Laurent KLOEBLE on 23/08/2015.
 */
public class Gender implements Comparable {

    public static final int MASCULINE = 0;
    public static final int FEMININE = 100;
    public static final int NEUTRAL = 200;
    public static final int ADJECTIVE = 300;
    public static final int UNKNOWN = 400;

    protected int value;

    public Gender(int value) {
        this.value = value;
    }

    public String name() {
        switch (value) {
            case MASCULINE: return "MASCULINE";
            case FEMININE: return "FEMININE";
            case NEUTRAL: return "NEUTRAL";
            case ADJECTIVE: return "ADJECTIVE";
            default:return "UNKNOWN";
        }
    }

    public static Gender getGenderByCode(String form) {
        if(form == null) return new Gender(UNKNOWN);
        if("fem".equals(form.toLowerCase())) {
            return new Gender(FEMININE);
        } else if("masc".equals(form.toLowerCase())) {
            return new Gender(MASCULINE);
        } else if("neut".equals(form.toLowerCase())) {
            return new Gender(NEUTRAL);
        } else if("adj".equals(form.toLowerCase())) {
            return new Gender(ADJECTIVE);
        }
        return new Gender(UNKNOWN);
    }

    public static Gender getSingleGender(List<CaseNumberGenre> possibleCaseNumbers, Gender gender) {
        if(gender != null) return gender;
        int masc = 0, fem = 0, neut = 0;
        for(CaseNumberGenre cng : possibleCaseNumbers) {
            if(cng.getGender().equals(MASCULINE)) {
                masc++;
            } else if(cng.getGender().equals(FEMININE)) {
                fem++;
            } else if(cng.getGender().equals(NEUTRAL)) {
                neut++;
            }
        }
        if(masc > 0 && fem == 0 && neut == 0) {
            return new Gender(MASCULINE);
        } else if(masc == 0 && fem > 0 && neut == 0) {
            return new Gender(FEMININE);
        } else if(masc == 0 & fem == 0 && neut > 0) {
            return new Gender(NEUTRAL);
        }
        return null;
    }

    @Override
    public String toString() {
        return name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Gender gender = (Gender) o;
        return value == gender.value;

    }

    @Override
    public int hashCode() {
        return value;
    }

    public boolean isFeminine() {
        return value == FEMININE;
    }

    @Override
    public int compareTo(Object o) {
        Gender other = (Gender)o;
        if(this.value > other.value) return 1;
        else if(this.value < other.value) return -1;
        return 0;
    }
}

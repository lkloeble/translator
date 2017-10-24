package org.patrologia.translator.basicelements;

import org.patrologia.translator.casenumbergenre.Gender;

/**
 * Created by lkloeble on 20/06/2016.
 */
public class PairConstructionGender {

    private String construction;
    private String declension;
    private Gender gender;

    public PairConstructionGender(String construction, String declension, Gender gender) {
        this.construction = construction;
        this.gender = gender;
        this.declension = declension;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PairConstructionGender that = (PairConstructionGender) o;

        if (!construction.equals(that.construction)) return false;
        if (!declension.equals(that.declension)) return false;
        return gender.equals(that.gender);

    }

    @Override
    public int hashCode() {
        int result = construction.hashCode();
        result = 31 * result + declension.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PairConstructionGender{" +
                "construction='" + construction + '\'' +
                ", declension='" + declension + '\'' +
                ", gender=" + gender +
                '}';
    }
}

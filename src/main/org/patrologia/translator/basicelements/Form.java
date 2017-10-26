package org.patrologia.translator.basicelements;

/**
 * Created by lkloeble on 29/12/2015.
 */
public class Form {

    private String value;
    private String originValue;
    private WordType type;
    private String declension;
    private int preferedTranslation;
    private Repository repository = new Repository();

    public Form(String value, String originValue, WordType type, String declension, int preferedTranslation) {
        this.value = value;
        this.originValue = originValue;
        this.type = type;
        this.declension = declension;
        this.preferedTranslation = preferedTranslation;
    }

    public String getValue() {
        return value;
    }

    public String getOriginValue() {
        return originValue;
    }

    public int getPreferedTranslation() {
        return preferedTranslation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Form form = (Form) o;

        if (preferedTranslation != form.preferedTranslation) return false;
        if (!value.equals(form.value)) return false;
        if (!originValue.equals(form.originValue)) return false;
        if (type != form.type) return false;
        return !(declension != null ? !declension.equals(form.declension) : form.declension != null);

    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + originValue.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (declension != null ? declension.hashCode() : 0);
        result = 31 * result + preferedTranslation;
        return result;
    }

    @Override
    public String toString() {
        return "Form{" +
                "value='" + value + '\'' +
                ", originValue='" + originValue + '\'' +
                ", type=" + type +
                ", prefTrans=" + preferedTranslation +
                '}';
    }

    public Form updateToDefaultPreferedTranslation() {
        return new Form(value, originValue,type, declension, 1);
    }

    public Form updateForMinusEndingForm() {
        return new Form(value + "-", originValue, type, declension, 1);
    }

    public Form updateToAvoidNounAndVerbConfusion() {
        return new Form(value,value,type,declension,1);
    }

    public Form updateToUnaccentued() {
        return new Form(repository.unaccentuedWithSofit(value),value,type,declension,1);
    }
}

package patrologia.translator.basicelements;

import patrologia.translator.utils.StringUtils;

public class DummyAccentuer  extends Accentuer {

    public String unaccentued(String value) {
        return StringUtils.unaccentuate(value).replace("[","").replace("]","");
    }

    @Override
    public String toString() {
        return "DummyAccentuer{}";
    }
}

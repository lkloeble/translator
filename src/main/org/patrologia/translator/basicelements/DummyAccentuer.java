package org.patrologia.translator.basicelements;

import org.patrologia.translator.utils.StringUtils;

public class DummyAccentuer extends Accentuer {

    public String unaccentued(String value) {
        return StringUtils.unaccentuate(value);
    }

    @Override
    public String toString() {
        return "DummyAccentuer{}";
    }
}

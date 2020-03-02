package patrologia.translator.casenumbergenre;

import java.util.Comparator;

public class CaseComparator  implements Comparator<Case> {

    @Override
    public int compare(Case c1, Case c2) {
        return c2.toString().compareTo(c1.toString());
    }


}

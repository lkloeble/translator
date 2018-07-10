package org.patrologia.translator.basicelements;

import org.patrologia.translator.basicelements.verb.InfinitiveBuilder;
import org.patrologia.translator.casenumbergenre.CaseNumberGenre;

import java.util.*;

/**
 * Created by lkloeble on 30/12/2015.
 */
public class FormRepository {

    private Map<Form, Form> formCorrespondances = new HashMap<Form, Form>();
    private Set<String>  allPossibleWordsValue = new HashSet<>();
    private Accentuer accentuer = new Accentuer();
    private InfinitiveBuilder infinitiveBuilder;

    public FormRepository(InfinitiveBuilder infinitiveBuilder) {
        this.infinitiveBuilder = infinitiveBuilder;
    }

    public boolean containsFormValue(String initialValue) {
        /*
        List<String> strs = new ArrayList<>(allPossibleWordsValue);
        Collections.sort(strs);
        int i=0;
        for(String s : strs) {
            if(s.startsWith("asmw")) System.out.println(i + " " + s);
            i++;
        }
        */
        boolean firstResult = allPossibleWordsValue.contains(initialValue) || allPossibleWordsValue.contains(accentuer.unaccentued(initialValue));
        if(!firstResult) {
            return allPossibleWordsValue.contains(infinitiveBuilder.getInfinitiveFromInitialValue(initialValue)) || allPossibleWordsValue.contains(accentuer.unaccentued(infinitiveBuilder.getInfinitiveFromInitialValue(initialValue)));
        }
        return firstResult;
    }

    public String getValueByForm(Form form) {
        if(formCorrespondances.get(form) != null) {
           return formCorrespondances.get(form).getValue();
        }
        Form formUpdated = formCorrespondances.get(form.updateToUnaccentued());
        if(formUpdated != null) {
            return formUpdated.getValue();
        }
        formUpdated = formCorrespondances.get(form.updateToUnaccentuedOriginValue());
        if(formUpdated != null) {
            return formUpdated.getValue();
        }
        formUpdated = formCorrespondances.get(form.updateToDefaultPreferedTranslation());
        if(formUpdated != null) {
            return formUpdated.getValue();
        }
        formUpdated = formCorrespondances.get(form.updateForMinusEndingForm());
        if(formUpdated != null){
            return formUpdated.getValue();
        }
        formUpdated  = formCorrespondances.get(form.updateToAvoidNounAndVerbConfusion());
        if(formUpdated != null){
            return formUpdated.getValue();
        }
        formUpdated = formCorrespondances.get(form.updateToAlternateInfinitiveForm());
        if(formUpdated != null){
            return formUpdated.getValue();
        }
        return "UNKNOWVALUEBYFORM";
    }

    public void addForm(Form key, Form value, Map<CaseNumberGenre, String> exceptions, CaseNumberGenre caseNumberGenre) {
        if(exceptions != null && exceptions.containsKey(caseNumberGenre)) {
            formCorrespondances.put(new Form(exceptions.get(caseNumberGenre), key.getOriginValue(), WordType.NOUN, null,key.getPreferedTranslation(), infinitiveBuilder), value);
            allPossibleWordsValue.add(exceptions.get(caseNumberGenre));
            return;
        }
        formCorrespondances.put(key, value);
        allPossibleWordsValue.add(key.getValue());
    }

    public void addForm(Form key, Form value) {
        formCorrespondances.put(key, value);
        allPossibleWordsValue.add(key.getValue());
    }

}

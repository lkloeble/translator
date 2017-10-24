package org.patrologia.translator.basicelements;

import org.patrologia.translator.casenumbergenre.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Laurent KLOEBLE on 22/08/2015.
 */
public class Preposition extends Word {

    protected Case _case;
    private boolean caseAffectEveryRelatedWord;
    List<String> descriptions = new ArrayList<String>();
    private boolean doubleNumberTranslation;
    private boolean doubleGenreTranslation;
    private boolean article;
    private boolean newRule;

    public Preposition(Language language, String initialValue, Case _case) {
        super(WordType.PREPOSITION, initialValue, language);
        this._case = _case;
        this.caseAffectEveryRelatedWord = true;
    }

    public Preposition(Language language, String initialValue, Case _case, boolean caseAffectEveryRelatedWord) {
        super(WordType.PREPOSITION, initialValue, language);
        this._case = _case;
        this.caseAffectEveryRelatedWord = caseAffectEveryRelatedWord;
    }

    public Preposition(Preposition toClone) {
        super(toClone);
        this._case = toClone._case;
        this.caseAffectEveryRelatedWord = toClone.caseAffectEveryRelatedWord;
        this.descriptions = toClone.descriptions;
        this.doubleNumberTranslation = toClone.doubleNumberTranslation;
        this.doubleGenreTranslation = toClone.doubleGenreTranslation;
        this.article = toClone.article;
        this.preferedTypeSymbol = 'P';
        this.newRule = toClone.newRule;
    }

    public void addDescription(String description) {
        descriptions.add(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Preposition)) return false;
        if (!super.equals(o)) return false;

        Preposition that = (Preposition) o;

        return !(_case != null ? !_case.equals(that._case) : that._case != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (_case != null ? _case.hashCode() : 0);
        return result;
    }

    public void setDistinctiveTranslationByNumber() {
        doubleNumberTranslation = true;
    }

    public void setDistinctiveTranslationByGenre() {
        doubleGenreTranslation = true;
    }

    public boolean isDoubleNumberTranslation() {
        return doubleNumberTranslation;
    }

    public boolean isDoubleGenreTranslation() {
        return doubleGenreTranslation;
    }

    public void avoidDoubleNumberTranslation() {
        this.doubleNumberTranslation = false;
    }

    public void defineAsArticle() {
        this.article = true;
    }

    public boolean isArticle() {
        return article;
    }

    public boolean hasCase(Case aCase) {
        return _case.getTrigramForCase().equals(aCase.getTrigramForCase());
    }

    public void emptyRules() {
        this.rules = new HashSet<>();
        this.newRule = true;
    }

    public boolean hasNewRules() {
        return newRule;
    }

}

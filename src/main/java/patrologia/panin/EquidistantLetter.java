package patrologia.panin;

public class EquidistantLetter {

    private ELSRepository elsRepository;
    private String textToStudy;

    public EquidistantLetter(ELSRepository elsRepository, String textToStudy) {
        this.elsRepository = elsRepository;
        this.textToStudy = textToStudy;
    }

    public boolean hasSequence() {
        if(textToStudy == null) return false;
        if(textToStudy.length() == 0) return false;
        if(elsRepository.vocabularyMatching(textToStudy)) return false;
        return true;
    }
}

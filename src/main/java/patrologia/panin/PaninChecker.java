package patrologia.panin;

import java.util.*;

public class PaninChecker {

    private String text;
    public PaninVocabularyStore vocabularyStore;
    public List<String> greekVowels = Arrays.asList("α","ε","η","ι","ω","ο","υ");
    public List<String> greekConsons = Arrays.asList("β","γ","δ","ζ","θ","κ","λ","μ","ν","π","ξ","ρ","σ","ς","τ","φ","χ","ψ");
    public Map<String, Integer> letterValues = new HashMap<String,Integer>();

    public PaninChecker(String text, PaninVocabularyStore vocabularyStore) {
        populateLetterValues();
        this.text = eraseNumbers(text.toLowerCase());
        this.vocabularyStore = vocabularyStore;
    }

    public PaninChecker(PaninTextPart textPart) {
        populateLetterValues();
        switch (textPart) {
            case FULL_FINAL_MARK:
                this.text = cleanText(getMarkFinal());
                break;
            case MARK_FINAL_BEFORE_JESUS_SPEECH:
                this.text = cleanText(getMarkAfterBeforeJesusSpeech());
                break;
            case MARK_FINAL_JESUS_SPEECH:
                this.text = cleanText(getJesusSpeech());
                break;
            case MARK_FINAL_AFTER_JESUS_SPEECH:
                this.text = cleanText(getMarkAfterBeforeJesusSpeech());
                break;
        }
        this.vocabularyStore = getGreekPaninVocabularyStore();
    }

    public String getText() {
        return text;
    }

    private String cleanText(String text) {
        return erasePonctuation(eraseNumbers(text.toLowerCase()));
    }

    private String erasePonctuation(String text) {
        return text.replace(".", "")
                .replace(",", "")
                .replace("\n","");
    }

    private String eraseNumbers(String text) {
        return text.replace("0","").replace("1","")
                .replace("2","")
                .replace("3","")
                .replace("4","")
                .replace("5","")
                .replace("6","")
                .replace("7","")
                .replace("8","")
                .replace("9","");
    }

    public int wordCount() {
        return text.length() > 0 ? text.split(" ").length : 0;
    }

    public int letterCount() {
        return text.replace(" ","").length();
    }

    public int formsCount() {
        return getUniqueFormsFromText().size();
    }

    public int formValuesCount() {
        Set<String> uniqueFormsFromText = getUniqueFormsFromText();
        int total = 0;
        for(String word : uniqueFormsFromText) {
            total += getWordValue(word);
        }
        return total;
    }
    public int vowelCount(PaninVocabularyStore vocabularyStore) {
        return eraseLetters(greekConsons, vocabularyStore).replace(" ","").length();
    }

    public int consonantCount(PaninVocabularyStore vocabularyStore) {
        return eraseLetters(greekVowels, vocabularyStore).replace(" ","").length();
    }

    public int vocabularyCount() {
        return vocabularyStore != null ? vocabularyStore.checkNumberOfWordsInVocabulary(text) : 0;
    }

    public String eraseLetters(List<String> toErase, PaninVocabularyStore vocabularyStore) {
        StringBuilder keepLetters = new StringBuilder();
        String localText = vocabularyStore.getAllInOneSentence(text);
        char[] allLetters = localText.toCharArray();
        for(char c : allLetters) {
            String letter = Character.toString(c);
            if(toErase.contains(letter)) continue;
            keepLetters.append(letter);
        }
        return keepLetters.toString();
    }

    private String getMarkFinal() {
        return "9 Αναστας δε πρωι πρωτη σαββατου εφανη πρωτον Μαρια τη Μαγδαληνη, παρ ης εκβεβληκει επτα δαιμονια.\n" +
                "10 εκεινη πορευθεισα απηγγειλεν τοις μετ αυτου γενομενοις πενθουσι και κλαιουσιν.\n" +
                "11 κα κεινοι ακουσαντες οτι ζη και εθεαθη υπ αυτης ηπιστησαν.\n" +
                "12 Μετα δε ταυτα δυσιν εξ αυτων περιπατουσιν εφανερωθη εν ετερα μορφη, πορευομενοις εις αγρον\n" +
                "13 κα κεινοι απελθοντες απηγγειλαν τοις λοιποις ουδε εκεινοις επιστευσαν.\n" +
                "14 Υστερον δε ανακειμενοις αυτοις τοις ενδεκα εφανερωθη, και ωνειδισεν την απιστιαν αυτων και σκληροκαρδιαν οτι τοις θεασαμενοις αυτον εγηγερμενον εκ νεκρων ουκ επιστευσαν.\n" +
                "15 και ειπεν αυτοις Πορευθεντες εις τον κοσμον απαντα κηρυξατε το ευαγγελιον παση τη κτισει.\n" +
                "16 ο πιστευσας και βαπτισθεις σωθησεται, ο δε απιστησας κατακριθησεται.\n" +
                "17 σημεια δε τοις πιστευσασιν ακολουθησει ταυτα εν τω ονοματι μου δαιμονια εκβαλουσιν, γλωσσαις λαλησουσιν,\n" +
                "18 και εν ταις χερσιν οφεις αρουσιν καν θανασιμον τι πιωσιν ου μη αυτους βλαψη επι αρρωστους χειρας επιθησουσιν και καλως εξουσιν.\n" +
                "19 Ο μεν ουν Κυριος Ιησους μετα το λαλησαι αυτοις, ανελημφθη εις τον ουρανον και εκαθισεν εκ δεξιων του Θεου.\n" +
                "20 εκεινοι δε εξελθοντες εκηρυξαν πανταχου, του Κυριου συνεργουντος, και τον λογον βεβαιουντος δια των επακολουθουντων σημειων.";
    }

    private String getMarkFinalBeforeJesusSpeech() {
        return "9 Αναστας δε πρωι πρωτη σαββατου εφανη πρωτον Μαρια τη Μαγδαληνη, παρ ης εκβεβληκει επτα δαιμονια.\n" +
                "10 εκεινη πορευθεισα απηγγειλεν τοις μετ αυτου γενομενοις πενθουσι και κλαιουσιν.\n" +
                "11 κα κεινοι ακουσαντες οτι ζη και εθεαθη υπ αυτης ηπιστησαν.\n" +
                "12 Μετα δε ταυτα δυσιν εξ αυτων περιπατουσιν εφανερωθη εν ετερα μορφη, πορευομενοις εις αγρον\n" +
                "13 κα κεινοι απελθοντες απηγγειλαν τοις λοιποις ουδε εκεινοις επιστευσαν.\n" +
                "14 Υστερον δε ανακειμενοις αυτοις τοις ενδεκα εφανερωθη, και ωνειδισεν την απιστιαν αυτων και σκληροκαρδιαν οτι τοις θεασαμενοις αυτον εγηγερμενον εκ νεκρων ουκ επιστευσαν.\n" +
                "15 και ειπεν αυτοις";
    }

    private String getJesusSpeech() {
        return " Πορευθεντες εις τον κοσμον απαντα κηρυξατε το ευαγγελιον παση τη κτισει.\n" +
                "16 ο πιστευσας και βαπτισθεις σωθησεται, ο δε απιστησας κατακριθησεται.\n" +
                "17 σημεια δε τοις πιστευσασιν ακολουθησει ταυτα εν τω ονοματι μου δαιμονια εκβαλουσιν, γλωσσαις λαλησουσιν,\n" +
                "18 και εν ταις χερσιν οφεις αρουσιν καν θανασιμον τι πιωσιν ου μη αυτους βλαψη επι αρρωστους χειρας επιθησουσιν και καλως εξουσιν.";
    }

    private String getMarkAfterBeforeJesusSpeech() {
        return "19 Ο μεν ουν Κυριος Ιησους μετα το λαλησαι αυτοις, ανελημφθη εις τον ουρανον και εκαθισεν εκ δεξιων του Θεου.\n" +
                "20 εκεινοι δε εξελθοντες εκηρυξαν πανταχου, του Κυριου συνεργουντος, και τον λογον βεβαιουντος δια των επακολουθουντων σημειων.";
    }

    private PaninVocabularyStore getGreekPaninVocabularyStore() {
        PaninVocabularyStore store = new PaninVocabularyStore(getGreekPaninDictionary());
        store.addFullSentenceOfVocabulary(erasePonctuation("Αναστας@450 δε@1161 πρωι@4404 πρωτη@4413 σαββατου@4521 εφανη@5316 πρωτον@4413 Μαρια@3137 τη@3588 Μαγδαληνη@3094, παρ@3844 ης@3739 εκβεβληκει@1544 επτα@2033 δαιμονια@1140."));
        store.addFullSentenceOfVocabulary(erasePonctuation("εκεινη@1565 πορευθεισα@4198 απηγγειλεν@518 τοις@3588 μετ@3326 αυτου@846 γενομενοις@1096 πενθουσι@3996 και@2532 κλαιουσιν@2799."));
        store.addFullSentenceOfVocabulary(erasePonctuation("κα@2532 κεινοι@1565 ακουσαντες@191 οτι@3754 ζη@2198 και@2532 εθεαθη@2300 υπ@5259 αυτης@846 ηπιστησαν@569."));
        store.addFullSentenceOfVocabulary(erasePonctuation("Μετα@3326 δε@1161 ταυτα@3778 δυσιν@1417 εξ@1537 αυτων@846 περιπατουσιν@4043 εφανερωθη@5319 εν@1722 ετερα@2087 μορφη@3444, πορευομενοις@4198 εις@1519 αγρον@68"));
        store.addFullSentenceOfVocabulary(erasePonctuation("κα@2532 κεινοι@1565 απελθοντες@565 απηγγειλαν@518 τοις@3588 λοιποις@3062 ουδε@3761 εκεινοις@1565 επιστευσαν@4100."));
        store.addFullSentenceOfVocabulary(erasePonctuation("Υστερον@5305 δε@1161 ανακειμενοις@345 αυτοις@846 τοις@3588 ενδεκα@1733 εφανερωθη@5319, και@2532 ωνειδισεν@3679 την@3588 απιστιαν@570 αυτων@846 και@2532 σκληροκαρδιαν@4641 οτι@3754 τοις@3588 θεασαμενοις@2300 αυτον@846 εγηγερμενον@1453 εκ@1537 νεκρων@3498 ουκ@3756 επιστευσαν@4100."));
        store.addFullSentenceOfVocabulary(erasePonctuation("και@3532 ειπεν@2036 αυτοις@846 Πορευθεντες@4198 εις@1519 τον@3588 κοσμον@1889 απαντα@537 κηρυξατε@2784 το@3588 ευαγγελιον@2098 παση@3956 τη@3588 κτισει@2937."));
        store.addFullSentenceOfVocabulary(erasePonctuation("ο@3588 πιστευσας@4100 και@2532 βαπτισθεις@907 σωθησεται@4982, ο@3588 δε@1161 απιστησας@569 κατακριθησεται@2632."));
        store.addFullSentenceOfVocabulary(erasePonctuation("σημεια@4592 δε@1161 τοις@3588 πιστευσασιν@4100 ακολουθησει@190 ταυτα@3778 εν@1722 τω@3588 ονοματι@3686 μου@1473 δαιμονια@1140 εκβαλουσιν@1544, γλωσσαις@1100 λαλησουσιν@2980,"));
        store.addFullSentenceOfVocabulary(erasePonctuation("και@2532 εν@1722 ταις@3588 χερσιν@5495 οφεις@3789 αρουσιν@142 καν@2579 θανασιμον@2286 τι@5100 πιωσιν@4095 ου@3756 μη@3361 αυτους@846 βλαψη@984 επι@1909 αρρωστους@732 χειρας@5495 επιθησουσιν@2007 και@2532 καλως@2573 εξουσιν@2192."));
        store.addFullSentenceOfVocabulary(erasePonctuation("Ο@3588 μεν@3303 ουν@3767 Κυριος@2962 Ιησους@2424 μετα@3326 το@3588 λαλησαι@2980 αυτοις@846, ανελημφθη@353 εις@1519 τον@3588 ουρανον@3772 και@2532 εκαθισεν@2523 εκ@1537 δεξιων@1188 του@3588 Θεου@2316"));
        store.addFullSentenceOfVocabulary(erasePonctuation("εκεινοι@1565 δε@1161 εξελθοντες@1831 εκηρυξαν@2784 πανταχου@3837, του@3588 Κυριου@2962 συνεργουντος@4903, και@2532 τον@3588 λογον@3056 βεβαιουντος@950 δια@1223 των@3588 επακολουθουντων@1872 σημειων@4592."));
        return store;
    }

    private PaninGreekRepository getGreekPaninDictionary() {
        PaninGreekRepository repository = new PaninGreekRepository();
        repository.addStrongReference("αγρος@68");
        repository.addStrongReference("αιρω@142");
        repository.addStrongReference("ακολουθεω@190");
        repository.addStrongReference("ακουω@191");
        repository.addStrongReference("ανακειμαι@345");
        repository.addStrongReference("αναλαμβανω@353");
        repository.addStrongReference("ανιστημι@450");
        repository.addStrongReference("απαγγελλω@518");
        repository.addStrongReference("απας@537");
        repository.addStrongReference("απελθω@565");
        repository.addStrongReference("απιστεω@569");
        repository.addStrongReference("απιστια@570");
        repository.addStrongReference("αρρωστος@732");
        repository.addStrongReference("αυτος@846");
        repository.addStrongReference("βαπιζω@907");
        repository.addStrongReference("βεβαιοω@950");
        repository.addStrongReference("βλαπτω@984");
        repository.addStrongReference("γινομαι@1096");
        repository.addStrongReference("γλωσσα@1100");
        repository.addStrongReference("δαιμονιον@1140");
        repository.addStrongReference("δε@1161");
        repository.addStrongReference("δεξιος@1188");
        repository.addStrongReference("δια@1223");
        repository.addStrongReference("δυο@1417");
        repository.addStrongReference("εγειρω@1453");
        repository.addStrongReference("ειπω@2036"); //classified in 2036 because of form επω
        repository.addStrongReference("εις@1519");
        repository.addStrongReference("εκ@1537");
        repository.addStrongReference("εκβαλλω@1544");
        repository.addStrongReference("εκεινος@1565");
        repository.addStrongReference("εν@1722");
        repository.addStrongReference("ενδεκα@1733");
        repository.addStrongReference("εξελθω@1831");
        repository.addStrongReference("επι@1909");
        repository.addStrongReference("επακολουθεω@1872");
        repository.addStrongReference("επιτιθημι@2007");
        repository.addStrongReference("επτα@2033");
        repository.addStrongReference("ετερος@2087");
        repository.addStrongReference("ευαγγελιον@2098");
        repository.addStrongReference("εχω@2192");
        repository.addStrongReference("ζαω@2198");
        repository.addStrongReference("θανασιμος@2286");
        repository.addStrongReference("θεαομαι@2300");
        repository.addStrongReference("θεος@2316");
        repository.addStrongReference("ιησους@2424");
        repository.addStrongReference("καθιζω@2523");
        repository.addStrongReference("και@2532");
        repository.addStrongReference("καλος@2573");
        repository.addStrongReference("καν@2579");
        repository.addStrongReference("κατακρινω@2632");
        repository.addStrongReference("κηρυσσω@2784");
        repository.addStrongReference("κλαιω@2799");
        repository.addStrongReference("κοσμος@1889");
        repository.addStrongReference("κτισις@2937");
        repository.addStrongReference("κυριος@2962");
        repository.addStrongReference("λαλεω@2980");
        repository.addStrongReference("λογος@3056");
        repository.addStrongReference("λοιπος@3062");
        repository.addStrongReference("μαγδαληνη@3094");
        repository.addStrongReference("μαρια@3137");
        repository.addStrongReference("μεν@3303");
        repository.addStrongReference("μετα@3326");
        repository.addStrongReference("μη@3361");
        repository.addStrongReference("μορφη@3444");
        repository.addStrongReference("μου@1473");
        repository.addStrongReference("νεκρος@3498");
        repository.addStrongReference("ο@3588");
        repository.addStrongReference("ονειδιζω@3679");
        repository.addStrongReference("ονομα@3686");
        repository.addStrongReference("ος@3739");
        repository.addStrongReference("οτι@3754");
        repository.addStrongReference("ου@3756");
        repository.addStrongReference("ουδε@3761");
        repository.addStrongReference("ουν@3767");
        repository.addStrongReference("ουρανος@3772");
        repository.addStrongReference("ουτος@3778");
        repository.addStrongReference("οφις@3789");
        repository.addStrongReference("πανταχου@3837");
        repository.addStrongReference("παρα@3844");
        repository.addStrongReference("πας@3956");
        repository.addStrongReference("πενθεω@3996");
        repository.addStrongReference("περιπατεω@4043");
        repository.addStrongReference("πινω@4095");
        repository.addStrongReference("πιστευω@4100");
        repository.addStrongReference("πορευομαι@4198");
        repository.addStrongReference("πρωι@4404");
        repository.addStrongReference("προτερος@4413");
        repository.addStrongReference("σαββατον@4521");
        repository.addStrongReference("σημειον@4592");
        repository.addStrongReference("σκληροκαρδια@4641");
        repository.addStrongReference("συνεργεω@4903");
        repository.addStrongReference("σωζω@4982");
        repository.addStrongReference("τις@5100");
        repository.addStrongReference("υπο@5259");
        repository.addStrongReference("υστερος@5305");
        repository.addStrongReference("φαινω@5316");
        repository.addStrongReference("φαινεροω@5319");
        repository.addStrongReference("χειρ@5495");

        return repository;
    }

    public String getHeptadicFormat(PaninSearch paninSearch) {
        int result = 0;
        switch(paninSearch) {
            case LETTERS:
                result = letterCount();
                break;
            case WORDS:
                result = wordCount();
                break;
            case FORMS:
                result = formsCount();
                break;
            case FORM_VALUES:
                result = formValuesCount();
                break;
            case VOCABULARY:
                result = vocabularyCount();
                break;
            case FORMS_ONLY_ONCE:
                result = form_occurence_counter(1);
                break;
            case FORMS_TWICE_MIN:
                result = form_occurence_min_counter(2);
                break;
            case VOCABULARY_LETTERS:
                result = vocabularyLetterCount();
                break;
            case VOCABULARY_LETTERS_VOWELS:
                result = vowelCount(vocabularyStore);
                break;
            case VOCABULARY_LETTERS_CONSONANTS:
                result = consonantCount(vocabularyStore);
                break;
            default:
                break;
        }
        return paninSearch.getViewLabel() + " " + printHeptadic(result);
    }

    private int vocabularyLetterCount() {
        return vocabularyStore.letterCountForText(text);
    }

    private Map<String,Integer> computeWordsAndOccurence() {
        Map<String, Integer> wordsWithOccurence = new HashMap<>();
        StringTokenizer stringTokenizer = new StringTokenizer(text);
        while(stringTokenizer.hasMoreTokens()) {
            String word = stringTokenizer.nextToken();
            if(!wordsWithOccurence.containsKey(word)) {
                wordsWithOccurence.put(word,1);
            } else {
                int occurenceValue = wordsWithOccurence.get(word);
                wordsWithOccurence.put(word,++occurenceValue);
            }
        }
        return  wordsWithOccurence;
    }

    private int form_occurence_counter(int number_of_occurence) {
        Map<String,Integer> wordsWithOccurences = computeWordsAndOccurence();
        Set<String> allForms = wordsWithOccurences.keySet();
        int total = 0;
        for(String form : allForms) {
            int occurenceFound = wordsWithOccurences.get(form);
            if(number_of_occurence == occurenceFound) ++ total;
        }
        return total;
    }

    private int form_occurence_min_counter(int number_of_occurence) {
        Map<String,Integer> wordsWithOccurences = computeWordsAndOccurence();
        Set<String> allForms = wordsWithOccurences.keySet();
        int total = 0;
        for(String form : allForms) {
            int occurenceFound = wordsWithOccurences.get(form);
            if(number_of_occurence <= occurenceFound) ++ total;
        }
        return total;
    }

    private String printHeptadic(int result) {
        if(doubleHeptadic(result)) {
            return "7*7*" + result/49;
        }
        return isHeptadic(result) ? "7*" + result/7 : "NOHEPTADIC : " + result;
    }

    private boolean doubleHeptadic(int result) {
        return result%49 == 0;
    }

    private boolean isHeptadic(int result) {
        return result%7 == 0;
    }

    private Set<String> getUniqueFormsFromText() {
        Set<String> words = new HashSet<>();
        StringTokenizer stringTokenizer = new StringTokenizer(text);
        while(stringTokenizer.hasMoreTokens()) {
            words.add(stringTokenizer.nextToken());
        }
        return words;
    }

    private int getWordValue(String word) {
        char[] letters = word.toCharArray();
        int total = 0;
        for(char c : letters) {
            String s = Character.toString(c);
            total += letterValues.get(s);
        }
        return total;
    }

    private void populateLetterValues() {
        letterValues.put("α",1);
        letterValues.put("β",2);
        letterValues.put("γ",3);
        letterValues.put("δ",4);
        letterValues.put("ε",5);
        letterValues.put("ζ",7);
        letterValues.put("η",8);
        letterValues.put("θ",9);
        letterValues.put("ι",10);
        letterValues.put("κ",20);
        letterValues.put("λ",30);
        letterValues.put("μ",40);
        letterValues.put("ν",50);
        letterValues.put("ξ",60);
        letterValues.put("ο",70);
        letterValues.put("π",80);
        letterValues.put("ρ",100);
        letterValues.put("σ",200);
        letterValues.put("ς",200);
        letterValues.put("τ",300);
        letterValues.put("υ",400);
        letterValues.put("φ",500);
        letterValues.put("χ",600);
        letterValues.put("ψ",700);
        letterValues.put("ω",800);
    }

}

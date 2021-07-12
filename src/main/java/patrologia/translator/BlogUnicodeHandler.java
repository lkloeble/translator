package patrologia.translator;

public class BlogUnicodeHandler {

    public static void main(String[] args) {
        BlogUnicodeHandler blogUnicodeHandler = new BlogUnicodeHandler();
        blogUnicodeHandler.convertToUnicode();
    }

    public void convertToUnicode() {

        String sentenceToConvert = "הקורא את שמע ולא השמיע לאוזנו, יצא; רבי יוסי אומר, לא יצא.  קרא ולא דיקדק באותותיה, רבי יוסי אומר, יצא; רבי יהודה אומר, לא יצא.  הקורא למפרע, לא יצא.  קרא וטעה, יחזור למקום שטעה.";




        if (sentenceToConvert == null) return;

        StringBuilder sb = new StringBuilder();
        char[] chars = sentenceToConvert.toLowerCase().toCharArray();

        for (char c : chars) {
            int i = (int) c;
                sb.append("&#").append(i).append(";");
        }
        System.out.println(sb.toString());
    }
}

import org.junit.Test;
import static org.junit.Assert.assertTrue;

/**
 * Created by lkloeble on 28/09/2017.
 */
public class VokalizedHebrewTranslatorBridgeTest extends HebrewTranslatorBridgeTest {


    @Test
    public void mishnah_berakhot_chapter1() {
        checkInMaps("mishnah1A", translatorBridge);
        checkInMaps("mishnah1B", translatorBridge);
        checkInMaps("mishnah1C", translatorBridge);
        checkInMaps("mishnah1D", translatorBridge);
        checkInMaps("mishnah1E", translatorBridge);
        checkInMaps("mishnah1F", translatorBridge);
        checkInMaps("mishnah1G", translatorBridge);
        checkInMaps("mishnah1H", translatorBridge);
        checkInMaps("mishnah1I", translatorBridge);
        checkInMaps("mishnah1J", translatorBridge);
        checkInMaps("mishnah1K", translatorBridge);
        checkInMaps("mishnah1L", translatorBridge);
        checkInMaps("mishnah1M", translatorBridge);
        //checkInMaps("mishnah1N", translatorBridge);
        checkInMaps("mishnah1O", translatorBridge);
        checkInMaps("mishnah1P", translatorBridge);
        checkInMaps("mishnah1Q", translatorBridge);
        checkInMaps("mishnah1R", translatorBridge);
        checkInMaps("mishnah1S", translatorBridge);
        checkInMaps("mishnah1T", translatorBridge);
        checkInMaps("mishnah1U", translatorBridge);
        checkInMaps("mishnah1V", translatorBridge);
    }

    @Test
    public void mishnah_berakhot_chapter2() {
        checkInMaps("mishnaberakhot21A1", translatorBridge);
        checkInMaps("mishnaberakhot21A2", translatorBridge);
        checkInMaps("mishnaberakhot21A3", translatorBridge);
        checkInMaps("mishnaberakhot21B1", translatorBridge);
        checkInMaps("mishnaberakhot21B2", translatorBridge);
        checkInMaps("mishnaberakhot21B3", translatorBridge);
        checkInMaps("mishnaberakhot21B4", translatorBridge);
        checkInMaps("mishnaberakhot21B5", translatorBridge);
        checkInMaps("mishnaberakhot21B6", translatorBridge);
        checkInMaps("mishnaberakhot21B7", translatorBridge);
        checkInMaps("mishnaberakhot21B8", translatorBridge);
        checkInMaps("mishnaberakhot21B9", translatorBridge);
        checkInMaps("mishnaberakhot21B10", translatorBridge);
        checkInMaps("mishnaberakhot21C1", translatorBridge);
        checkInMaps("mishnaberakhot21C2", translatorBridge);
        checkInMaps("mishnaberakhot21C3", translatorBridge);
        checkInMaps("mishnaberakhot21C4", translatorBridge);
        checkInMaps("mishnaberakhot21D1", translatorBridge);
        checkInMaps("mishnaberakhot21D2", translatorBridge);
        checkInMaps("mishnaberakhot21E1", translatorBridge);
        checkInMaps("mishnaberakhot21E2", translatorBridge);
        checkInMaps("mishnaberakhot21E3", translatorBridge);
        checkInMaps("mishnaberakhot21E4", translatorBridge);
        checkInMaps("mishnaberakhot21F1", translatorBridge);
        checkInMaps("mishnaberakhot21F2", translatorBridge);
        checkInMaps("mishnaberakhot21F3", translatorBridge);
        checkInMaps("mishnaberakhot21G1", translatorBridge);
        checkInMaps("mishnaberakhot21G2", translatorBridge);
        checkInMaps("mishnaberakhot21G3", translatorBridge);
        checkInMaps("mishnaberakhot21H1", translatorBridge);
        checkInMaps("mishnaberakhot21H2", translatorBridge);
    }

        @Test
    public void rachi_bereshit_chapter1() {
        checkInMaps("rachitext1A", translatorBridge);
        checkInMaps("rachitext1B", translatorBridge);
        checkInMaps("rachitext1C", translatorBridge);
        checkInMaps("rachitext1D", translatorBridge);
        checkInMaps("rachitext1E", translatorBridge);
        checkInMaps("rachitext1F", translatorBridge);
        checkInMaps("rachitext1G", translatorBridge);
        checkInMaps("rachitext1H", translatorBridge);
        checkInMaps("rachitext1I", translatorBridge);
        checkInMaps("rachitext1J", translatorBridge);
        checkInMaps("rachitext1K", translatorBridge);
        checkInMaps("rachitext1L", translatorBridge);
    }

    @Test
    public void shei_lamorei_rachi1() {
        checkInMaps("sheilamoreiA", translatorBridge);
        checkInMaps("sheilamoreiB", translatorBridge);
        checkInMaps("sheilamoreiC", translatorBridge);
        checkInMaps("sheilamoreiD", translatorBridge);
        checkInMaps("sheilamoreiE", translatorBridge);
        checkInMaps("sheilamoreiF", translatorBridge);
        checkInMaps("sheilamoreiG", translatorBridge);
        checkInMaps("sheilamoreiH", translatorBridge);
        checkInMaps("sheilamoreiB1", translatorBridge);
        checkInMaps("sheilamoreiB2", translatorBridge);
        checkInMaps("sheilamoreiB3", translatorBridge);
        checkInMaps("sheilamoreiB4", translatorBridge);
        checkInMaps("sheilamoreiB5", translatorBridge);
        checkInMaps("sheilamoreiB6", translatorBridge);
        checkInMaps("sheilamoreiB7", translatorBridge);
        checkInMaps("sheilamoreiB8", translatorBridge);
        checkInMaps("sheilamoreiB9", translatorBridge);
        checkInMaps("sheilamoreiB10", translatorBridge);
        checkInMaps("sheilamoreiB11", translatorBridge);
        checkInMaps("sheilamoreiB12", translatorBridge);
        checkInMaps("sheilamoreiC1", translatorBridge);
        checkInMaps("sheilamoreiC2", translatorBridge);
        checkInMaps("sheilamoreiC3", translatorBridge);
        checkInMaps("sheilamoreiC4", translatorBridge);
        checkInMaps("sheilamoreiC5", translatorBridge);
        checkInMaps("sheilamoreiC6", translatorBridge);
        checkInMaps("sheilamoreiC7", translatorBridge);
        checkInMaps("sheilamoreiC8", translatorBridge);
        checkInMaps("sheilamoreiC9", translatorBridge);
    }

    @Test
    public void englishBavliRashi() {
        checkInMaps("engbavli1Arashi001", translatorBridge);
        checkInMaps("engbavli1Arashi002", translatorBridge);
        checkInMaps("engbavli1Arashi003", translatorBridge);
        checkInMaps("engbavli1Arashi004", translatorBridge);
        checkInMaps("engbavli1Arashi005", translatorBridge);
        checkInMaps("engbavli1Arashi006", translatorBridge);
        checkInMaps("engbavli1Arashi007", translatorBridge);
        checkInMaps("engbavli1Arashi008", translatorBridge);
        checkInMaps("engbavli1Arashi009", translatorBridge);
        checkInMaps("engbavli1Arashi010", translatorBridge);
        checkInMaps("engbavli1Arashi011", translatorBridge);
        checkInMaps("engbavli1Arashi012", translatorBridge);
        checkInMaps("engbavli1Arashi013", translatorBridge);
        checkInMaps("engbavli1Arashi014", translatorBridge);
        checkInMaps("engbavli1Arashi015", translatorBridge);
        checkInMaps("engbavli1Arashi016", translatorBridge);
        checkInMaps("engbavli1Arashi017", translatorBridge);
        checkInMaps("engbavli1Arashi018", translatorBridge);
        checkInMaps("engbavli1Arashi019", translatorBridge);
        checkInMaps("engbavli1Arashi020", translatorBridge);
    }


    @Test
    public void englishYeroushalmi() {
        checkInMaps("engyerou1Apneimoshe1", translatorBridge);
        checkInMaps("engyerou1Apneimoshe2", translatorBridge);
        checkInMaps("engyerou1Apneimoshe3", translatorBridge);
        checkInMaps("engyerou1Apneimoshe4A", translatorBridge);
        checkInMaps("engyerou1Apneimoshe4B", translatorBridge);
        checkInMaps("engyerou1Apneimoshe5", translatorBridge);
        checkInMaps("engyerou1Apneimoshe6", translatorBridge);
        checkInMaps("engyerou1Apneimoshe7", translatorBridge);
        checkInMaps("engyerou1Apneimoshe8", translatorBridge);
        checkInMaps("engyerou1Apneimoshe9", translatorBridge);
        checkInMaps("engyerou1Apneimoshe10", translatorBridge);
        checkInMaps("engyerou1Apneimoshe11", translatorBridge);
        checkInMaps("engyerou1Apneimoshe12", translatorBridge);
        checkInMaps("engyerou1Apneimoshe13", translatorBridge);
        checkInMaps("engyerou1Apneimoshe14", translatorBridge);
        checkInMaps("engyerou1Apneimoshe15", translatorBridge);
        checkInMaps("engyerou1Apneimoshe16", translatorBridge);
        checkInMaps("engyerou1Apneimoshe17", translatorBridge);
        checkInMaps("engyerou1Apneimoshe18", translatorBridge);
        checkInMaps("engyerou1Apneimoshe19", translatorBridge);
        checkInMaps("engyerou1Apneimoshe20", translatorBridge);
    }

    @Test
    public void hebrewBavli() {
        checkInMaps("hebbavli1ApagecommentA", translatorBridge);
        checkInMaps("hebbavli1ApagecommentB", translatorBridge);
        checkInMaps("hebbavli1ApagecommentC", translatorBridge);
        checkInMaps("hebbavli1ApagecommentD", translatorBridge);
        checkInMaps("hebbavli1ApagecommentE", translatorBridge);
        checkInMaps("hebbavli1ApagecommentF", translatorBridge);
        checkInMaps("hebbavli1ApagecommentG", translatorBridge);
        checkInMaps("hebbavli1ApagecommentH", translatorBridge);
        checkInMaps("hebbavli1ApagecommentI", translatorBridge);
        checkInMaps("hebbavli1ApagecommentJ", translatorBridge);
        checkInMaps("hebbavli1ApagecommentK", translatorBridge);
        checkInMaps("hebbavli1ApagecommentL", translatorBridge);
        checkInMaps("hebbavli1ApagecommentM", translatorBridge);
        checkInMaps("hebbavli1ApagecommentN", translatorBridge);
        checkInMaps("hebbavli1ApagecommentO", translatorBridge);
        checkInMaps("hebbavli1ApagecommentP", translatorBridge);
        checkInMaps("hebbavli1ApagecommentQ", translatorBridge);
        checkInMaps("hebbavli1ApagecommentR", translatorBridge);
        checkInMaps("hebbavli1ApagecommentS", translatorBridge);
        checkInMaps("hebbavli1ApagecommentT", translatorBridge);
        checkInMaps("hebbavli1ApagecommentU", translatorBridge);
        checkInMaps("hebbavli1ApagecommentV", translatorBridge);
        checkInMaps("hebbavli1ApagecommentW", translatorBridge);
        checkInMaps("hebbavli1ApagecommentX", translatorBridge);
        checkInMaps("hebbavli1ApagecommentY", translatorBridge);
        checkInMaps("hebbavli1ApagecommentZ", translatorBridge);
        checkInMaps("hebbavli1ApagecommentA1", translatorBridge);
        checkInMaps("hebbavli1ApagecommentA2", translatorBridge);
        checkInMaps("hebbavli1ApagecommentA3", translatorBridge);
        checkInMaps("hebbavli1ApagecommentA4", translatorBridge);
    }


    @Test
    public void midrabaeng() {
        checkInMaps("midrashrabaeng1A", translatorBridge);
        checkInMaps("midrashrabaeng1B", translatorBridge);
        checkInMaps("midrashrabaeng1C", translatorBridge);
        checkInMaps("midrashrabaeng1D", translatorBridge);
        checkInMaps("midrashrabaeng1E", translatorBridge);
        checkInMaps("midrashrabaeng1F", translatorBridge);
        checkInMaps("midrashrabaeng1G", translatorBridge);
        checkInMaps("midrashrabaeng1H", translatorBridge);
        checkInMaps("midrashrabaeng1I", translatorBridge);
        checkInMaps("midrashrabaeng1J", translatorBridge);
        checkInMaps("midrashrabaeng1K", translatorBridge);
        checkInMaps("midrashrabaeng1L", translatorBridge);
        checkInMaps("midrashrabaeng1M", translatorBridge);
        checkInMaps("midrashrabaeng1N", translatorBridge);
        checkInMaps("midrashrabaeng1O", translatorBridge);
        checkInMaps("midrashrabaeng1P", translatorBridge);
        checkInMaps("midrashrabaeng1Q", translatorBridge);
        checkInMaps("midrashrabaeng1R", translatorBridge);
        checkInMaps("midrashrabaeng1S", translatorBridge);
        checkInMaps("midrashrabaeng1T", translatorBridge);
    }

    @Test
    public void midrabheb() {
        checkInMaps("midrashrabahebcom1A", translatorBridge);
        checkInMaps("midrashrabahebcom1B", translatorBridge);
        checkInMaps("midrashrabahebcom1C", translatorBridge);
        checkInMaps("midrashrabahebcom1D", translatorBridge);
        checkInMaps("midrashrabahebcom1E", translatorBridge);
        checkInMaps("midrashrabahebcom1F", translatorBridge);
        checkInMaps("midrashrabahebcom1G", translatorBridge);
        checkInMaps("midrashrabahebcom1H", translatorBridge);
        checkInMaps("midrashrabahebcom1I", translatorBridge);
        checkInMaps("midrashrabahebcom1J", translatorBridge);
        checkInMaps("midrashrabahebcom1K", translatorBridge);
        checkInMaps("midrashrabahebcom1L", translatorBridge);
        checkInMaps("midrashrabahebcom1M", translatorBridge);
        checkInMaps("midrashrabahebcom1N", translatorBridge);
        checkInMaps("midrashrabahebcom1O", translatorBridge);
        checkInMaps("midrashrabahebcom1P", translatorBridge);
    }

    @Test
    public void test_failed_ones() {
        assertTrue(true);
        checkInMaps("hebbavli1ApagecommentN", translatorBridge);
        //checkInMaps("totoacc", translatorBridge);
        //checkInMaps("toto", translatorBridge);
    }

}

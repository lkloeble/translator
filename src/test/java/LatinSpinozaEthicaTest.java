import org.junit.Test;

public class LatinSpinozaEthicaTest extends LatinTranslatorBridgeTest {

    @Test
    public void ethica_livre1() {
        checkInMaps("spinozaethi1A", translatorBridge);
        checkInMaps("spinozaethi1B", translatorBridge);
        checkInMaps("spinozaethi1C", translatorBridge);
        checkInMaps("spinozaethi1D", translatorBridge);
        checkInMaps("spinozaethi1E", translatorBridge);
        checkInMaps("spinozaethi1F", translatorBridge);
        checkInMaps("spinozaethi1G", translatorBridge);
        checkInMaps("spinozaethi1H", translatorBridge);
    }


}

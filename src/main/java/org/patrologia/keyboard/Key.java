package patrologia.keyboard;

/**
 * Created by Laurent KLOEBLE on 16/04/2016.
 */
public class Key {

    private String text;
    private String equivalentKey;
    private KeyConstraint keyConstraint;

    public Key(String equivalentKey, String text) {
        this.equivalentKey = equivalentKey;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getEquivalentKey() {
        return equivalentKey;
    }

    public Key setKeyConstraint(KeyConstraint keyConstraint) {
        this.keyConstraint = keyConstraint;
        return this;
    }

    public KeyConstraint getKeyConstraint() {
        return keyConstraint;
    }

    public String toString() {
        return "key : " + text + " <=> " + equivalentKey;
    }
}

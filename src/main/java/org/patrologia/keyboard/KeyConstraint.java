package patrologia.keyboard;

/**
 * Created by Laurent KLOEBLE on 16/04/2016.
 */
public class KeyConstraint {

    public int row, column;
    public double span = 1d;

    public KeyConstraint(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public KeyConstraint(int row, int column, double span) {
        this.row = row;
        this.column = column;
        this.span = span;
    }
}

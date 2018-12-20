package patrologia.keyboard; /**
 * Created by Laurent KLOEBLE on 16/04/2016.
 *
 * based on http://stackoverflow.com/questions/24622279/laying-out-a-keyboard-in-swing
 *
 */
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class HebrewKeyboard {

    public static void main(String[] args) {
        new HebrewKeyboard();
    }

    private static Map<String, String> keyMap  = new HashMap<>();

    private static final Key[][] hebrewKeys = new Key[][]{
            {
                    createKey("","y", 0, 0),
                    createKey("&", ".", 0, 1),
                    createKey("é", ",", 0, 2),
                    createKey("\"", "\"", 0, 3),
                    createKey("'", "'", 0, 4),
                    createKey("(", "(", 0, 5),
                    createKey("-", "-", 0, 6),
                    createKey("7", "7", 0, 7),
                    createKey("8", "8", 0, 8),
                    createKey("9", "9", 0, 9),
                    createKey("0", "0", 0, 10),
                    createKey(")", ")", 0, 11),
                    createKey("=", "=", 0, 12),
                    createKey("", "Backspace", 0, 13, 2d)},
            {
                    createKey("", "Tab", 1, 0, 1.5d),
                    createKey("a", "א", 1, 2),
                    createKey("z", "ז", 1, 3),
                    createKey("e", "ה", 1, 4),
                    createKey("r", "ר", 1, 5),
                    createKey("t", "ת", 1, 6),
                    createKey("y", "ע", 1, 7),
                    createKey("u", "צ", 1, 8),
                    createKey("i", "י", 1, 9),
                    createKey("o", "ס", 1, 10),
                    createKey("p", "פ", 1, 11),
                    createKey("[", "[", 1, 12),
                    createKey("\\", "\\", 1, 13)
            },
            {
                    createKey("", "Caps", 2, 0, 1.5d),
                    createKey("q", "ק", 2, 2),
                    createKey("s", "ס", 2, 3),
                    createKey("d", "ד", 2, 4),
                    createKey("f", "ץ", 2, 5),
                    createKey("g", "ג", 2, 6),
                    createKey("h", "ח", 2, 7),
                    createKey("j", "ט", 2, 8),
                    createKey("k", "כ", 2, 9),
                    createKey("l", "ל", 2, 10),
                    createKey("m", "מ", 2, 11),
                    createKey("'", "'", 2, 12),
                    createKey("", "Enter", 2, 13, 2d)
            },
            {
                    createKey("", "Shift", 3, 0, 2d),
                    createKey("w", "ו", 3, 2),
                    createKey("x", "ף", 3, 3),
                    createKey("c", "ש", 3, 4),
                    createKey("v", "ת", 3, 5),
                    createKey("b", "ב", 3, 6),
                    createKey("n", "נ", 3, 7),
                    createKey(",", "ך", 3, 8),
                    createKey(";", "ם", 3, 9),
                    createKey(":", "ן", 3, 10),
                    createKey("!", ":", 3, 11),
                    createKey("", "fill", 3, 12, 0.5d),
                    createKey("", "\u2191", 3, 13),
            },
            {
                    createKey("", "fill", 4, 0, 4d),
                    createKey("", " ", 4, 1, 6d),
                    createKey("", "fill", 4, 2, 1.5d),
                    createKey("", "\u2190", 4, 3),
                    createKey("", "\u2193", 4, 4),
                    createKey("", "\u2192", 4, 5),
            },
    };

    private static Key[][] keys  = hebrewKeys;

    public HebrewKeyboard() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Keyboard");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                JMenuBar menuBar = new JMenuBar();
                frame.setJMenuBar(menuBar);
                JMenu menuLangues = new JMenu("Langues");
                JMenuItem menuItemHebreu = new JMenuItem("hebreu");
                menuBar.add(menuLangues);
                menuItemHebreu.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("on a choisi l'hebreu");
                        keys = hebrewKeys;
                        frame.removeAll();
                        frame.add(new TestPane());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                });
                menuLangues.add(menuItemHebreu);
                JMenuItem menuItemGrec = new JMenuItem("grec");
                menuItemGrec.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("on a choisi le grec");
                        //keys = greekKeys;
                        frame.add(new TestPane());
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                });
                menuLangues.add(menuItemGrec);
            }
        });
    }

    public class TestPane extends JPanel implements ItemListener {

        public TestPane() {
            setLayout(new KeyBoardLayout());
            for (int row = 0; row < keys.length; row++) {
                for (int col = 0; col < keys[row].length; col++) {
                    Key key = keys[row][col];
                    add(createButton(key.getText()), key.getKeyConstraint());
                }
            }
        }

        @Override
        public void itemStateChanged(ItemEvent e) {

        }

        protected JComponent createButton(String text) {
            JComponent comp = null;
            if (text == null || text.equalsIgnoreCase("fill")) {
                comp = new JLabel();
            } else {
                comp = new JButton(text);
                ((JButton)comp).addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        Character character = new Character(e.getKeyChar());
                        if(character.charValue() == ' ') {
                            System.out.print(" ");
                            return;
                        }
                        String sValue = character.toString();
                        System.out.print(keyMap.get(sValue));
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });
            }
            return comp;
        }

    }

    public static Key createKey(String equivalentKey, String text, int x, int y, double span) {
        keyMap.put(equivalentKey, text);
        return new Key(equivalentKey, text).setKeyConstraint(new KeyConstraint(x, y, span));
    }

    public static Key createKey(String equivalentKey, String text, int x, int y) {
        keyMap.put(equivalentKey, text);
        return new Key(equivalentKey, text).setKeyConstraint(new KeyConstraint(x, y));
    }
}
package org.patrologia.keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lkloeble on 26/05/2017.
 */
public class GreekKeyboard {
    public static void main(String[] args) {
        new GreekKeyboard();
    }

    private static Map<String, String> keyMap  = new HashMap<>();

    private static final Key[][] greekKeys = new Key[][]{
            {
                    createKey("","y", 0, 0),
                    createKey("1", "1", 0, 1),
                    createKey("2", "2", 0, 2),
                    createKey("3", "3", 0, 3),
                    createKey("4", "4", 0, 4),
                    createKey("5", "5", 0, 5),
                    createKey("6", "6", 0, 6),
                    createKey("7", "7", 0, 7),
                    createKey("8", "8", 0, 8),
                    createKey("9", "9", 0, 9),
                    createKey("0", "0", 0, 10),
                    createKey("-", "-", 0, 11),
                    createKey("=", "=", 0, 12),
                    createKey("", "Backspace", 0, 13, 2d)},
            {
                    createKey("", "Tab", 1, 0, 1.5d),
                    createKey("a", "α", 1, 2),
                    createKey("z", "ζ", 1, 3),
                    createKey("e", "ε", 1, 4),
                    createKey("r", "ρ", 1, 5),
                    createKey("t", "τ", 1, 6),
                    createKey("y", "θ", 1, 7),
                    createKey("u", "υ", 1, 8),
                    createKey("i", "ι", 1, 9),
                    createKey("o", "ο", 1, 10),
                    createKey("p", "π", 1, 11),
                    createKey("[", "[", 1, 12),
                    createKey("\\", "\\", 1, 13)
            },
            {
                    createKey("", "Caps", 2, 0, 1.5d),
                    createKey("q", "ς", 2, 2),
                    createKey("s", "σ", 2, 3),
                    createKey("d", "δ", 2, 4),
                    createKey("f", "φ", 2, 5),
                    createKey("g", "γ", 2, 6),
                    createKey("h", "η", 2, 7),
                    createKey("j", "ς", 2, 8),
                    createKey("k", "κ", 2, 9),
                    createKey("l", "λ", 2, 10),
                    createKey("m", "μ", 2, 11),
                    createKey("ù", "ξ", 2, 12),
                    createKey("", "Enter", 2, 13, 2d)
            },
            {
                    createKey("", "Shift", 3, 0, 2d),
                    createKey("w", "ω", 3, 2),
                    createKey("x", "χ", 3, 3),
                    createKey("c", "ψ", 3, 4),
                    createKey("v", "ψ", 3, 5),
                    createKey("b", "β", 3, 6),
                    createKey("n", "ν", 3, 7),
                    createKey(",", ",", 3, 8),
                    createKey(";", ";", 3, 9),
                    createKey(".", ".", 3, 10),
                    createKey("", "/", 3, 11),
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

    private static Key[][] keys  = greekKeys;

    public GreekKeyboard() {
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
                        keys = greekKeys;
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

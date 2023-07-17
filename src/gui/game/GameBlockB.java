package gui.game;

import logic.Player;

import javax.swing.*;
import java.awt.*;
/**
 * Fasst die verschiedenen Zeilen aus dem zweiten Block
 * zu einem gesamtKonstrukt zusammen.
 */
public class GameBlockB extends JPanel {

    public GameBlockB(Player player) {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(0, 25, 0, 25);
        c.fill = GridBagConstraints.HORIZONTAL;

        add(new GameBlockRow("DREIERPASCH", player), c);
        c.gridy = 1;
        add(new GameBlockRow("VIERERPASCH", player), c);
        c.gridy = 2;
        add(new GameBlockRow("FULL-HOUSE", player), c);
        c.gridy = 3;
        add(new GameBlockRow("KLEINE STRASSE", player), c);
        c.gridy = 4;
        add(new GameBlockRow("GROSSE STRASSE", player), c);
        c.gridy = 5;
        add(new GameBlockRow("KNIFFEL", player), c);
        c.gridy = 6;
        add(new GameBlockRow("CHANCE", player), c);
        c.gridy = 7;
        add(new GameBlockRow("<html><span>Gesamt<br>Unterer Teil</span></html>", player), c);
        c.gridy = 8;
        add(new GameBlockRow("<html><span>Gesamt<br>Oberer Teil</span></html>", player), c);
        c.gridy = 9;
        add(new GameBlockRow("Endsumme", player), c);
    }
}

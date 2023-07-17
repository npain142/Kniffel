package gui.game;

import connector.Connector;
import logic.Block;
import logic.Player;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Fasst die verschiedenen Zeilen aus dem ersten Block
 * zu einem gesamtKonstrukt zusammen.
 */
public class GameBlockA extends JPanel  {

    public GameBlockA(Player player) {
        setBackground(Color.WHITE);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(0, 25, 0, 25);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridy = 0;
        add(new GameBlockRow("1", player), c);
        c.gridy = 1;
        add(new GameBlockRow("2", player), c);
        c.gridy = 2;
        add(new GameBlockRow("3", player), c);
        c.gridy = 3;
        add(new GameBlockRow("4", player), c);
        c.gridy = 4;
        add(new GameBlockRow("5", player), c);
        c.gridy = 5;
        add(new GameBlockRow("6", player), c);
        c.gridy = 6;
        add(new GameBlockRow("Gesamt", player), c);
        c.gridy = 7;
        add(new GameBlockRow("Bonus", player), c);
        c.gridy = 8;
        add(new GameBlockRow("<html><span>Gesamt<br>Oberer Teil</span></html>", player), c);
    }
}

package gui.game;

import gui.Window;
import connector.Connector;

import javax.swing.*;
import java.awt.*;

/**
 * Die allgemeine grafische Darstellung
 * des Spielblocks. Die Klasse fasst verschiedene
 * komponenten zu einer gesamtKonstruktion zusammen.
 */

public class GameBlockContainer extends JPanel {
    public static GameBlockA gbA;
    public static GameBlockB gbB;
    Connector connector;
    JTabbedPane tabPane;
    public GameBlockContainer() {
        connector = Connector.instance();
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize((new Dimension(250, ( int) Window.getWindowDimensions().getHeight())));
        tabPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
        tabPane.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tabPane.setBackground(connector.getCurrentPlayer().getTheme());
        gbA = new GameBlockA(connector.getCurrentPlayer());
        gbB = new GameBlockB(connector.getCurrentPlayer());
        tabPane.addTab("Block A", gbA);
        tabPane.addTab("Block B", gbB);
        add(tabPane, BorderLayout.CENTER);
        add(new MenuBar(), BorderLayout.NORTH);
    }
}

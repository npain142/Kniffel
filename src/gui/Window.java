package gui;

import connector.Connector;
import gui.game.EndFrame;
import gui.game.GameContainer;
import gui.menu.StartMenu;
import logic.Player;
import logic.Sound;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * In dem Programm gibt es genau ein Objekt der Klasse Window.
 * Es ist das Hauptanwendungsfenster, welche fast alle Komponenten
 * beinhaltet. Mit der Erstellung eines Window-Objekts wird
 * das Programm gestartet.
 */
public class Window extends JFrame implements PropertyChangeListener {
    private static final Dimension windowDimension = new Dimension(1000, 750);
    private static final HashMap<String, Integer> insets = new HashMap<>();
    private static final Font primaryFont = new Font("System Fett", Font.BOLD, 13);
    private static final Color primaryColor = new Color(204, 153, 255);
    Connector connector;

    public Window() {
        connector = Connector.instance();
        connector.addPropertyChangeListener(this);
        this.setSize(windowDimension);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        setInsets();
        Sound.playBack();
        connector.startApplication();
    }

    public void changeScene(JPanel newPanel) {
        setContentPane(newPanel);
        revalidate();
        setVisible(true);
    }

    public static Dimension getWindowDimensions() {
        return windowDimension;
    }

    public static int getInset(String str) {
        return insets.get(str);
    }

    public static Font getPrimaryFont(int size) {
        return new Font(primaryFont.getFontName(), primaryFont.getStyle(), size);
    }

    public static Color getPrimaryColor() {
        return primaryColor;
    }

    /**
     * Jedes JFrame hat bei Erstellung vordefienierte insets.
     * Beim Zeichnen sollten diese Insets bereucksichtigt werden.
     */
    public void setInsets() {
        insets.put("BOTTOM", this.getInsets().bottom);
        insets.put("TOP", this.getInsets().top);
        insets.put("LEFT", this.getInsets().left);
        insets.put("RIGHT", this.getInsets().right);
        insets.put("HORIZONTAL", (this.getInsets().right) + (this.getInsets().left));
        insets.put("VERTICAL", (this.getInsets().top) + (this.getInsets().bottom));
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("NEW_GAME")) {
            changeScene( new GameContainer());
        } else if (evt.getPropertyName().equals("MENU")) {
            changeScene(new StartMenu());
        } else if (evt.getPropertyName().equals("END_GAME")) {
            changeScene(new EndFrame((ArrayList<Player>) evt.getNewValue()));
        }
    }
}

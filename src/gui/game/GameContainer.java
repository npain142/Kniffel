package gui.game;

import javax.swing.*;
import java.awt.*;

/**
 * GammeContainer fungiert als Container für alle
 * grafischen Komponenten die zum Spiel gehoeren.
 */
public class GameContainer extends JPanel {
    public static GameBlockContainer gameBlockContainer;
    public static MainFrame mainFrame;
    public GameContainer() {
        setLayout(new BorderLayout());
        mainFrame = new MainFrame();
        gameBlockContainer = new GameBlockContainer();
        add(gameBlockContainer, BorderLayout.WEST);
        add(mainFrame, BorderLayout.CENTER);
    }
}

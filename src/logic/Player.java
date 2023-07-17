package logic;

import gui.menu.PlayerVisual;
import java.awt.*;

/**
 * Die Klasse Player repraesentiert einen Spieler und speichert alle noetigen
 * Daten für den Spieler. Ein Spieler Objekt wird erzeugt, wenn man im Startmenue
 * einen neuen Spieler hinzufuegt. Ein Spielerobjekt existiert nur in der
 * allPlayers-Liste in der Connector-klasse
 */

public class Player {
    String name;
    PlayerVisual visual;
    Play currentPlay;
    Color theme;
    Throw currentThrow;
    private final Block playBlock = new Block();

    public Player(String name, PlayerVisual visual) {
        setName(name);
        setVisual(visual);
        setTheme(visual.getBackground());
        visual.setPlayer(this);
    }

    public void writePoints(String type, int points) {
        if (currentPlay.isReadyToWritePoints()) {
            getPlayBlock().writePoint(type, points);
            currentPlay.setReadyToWritePoints(false);
            currentPlay.setReadyToPass(true);
        }
    }

    /**
     * Wird in Connector in der Methode turnTable aufgerufen
     */
    public void startRound() {
        setCurrentPlay(new Play());
    }
    public void throwDice() {
        currentThrow = currentPlay.rollDice();
    }
    public void setVisual(PlayerVisual visual) {
        this.visual = visual;
    }



    public PlayerVisual getVisual() {
        return visual;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCurrentPlay(Play currentPlay) {
        this.currentPlay = currentPlay;
    }

    public Play getCurrentPlay() {
        return currentPlay;
    }

    public Block getPlayBlock() {
        return playBlock;
    }

    public void setTheme(Color theme) {
        this.theme = theme;
    }
    public Color getTheme() {
        return theme;
    }

    public Color getSecondaryTheme() {
        int difference = 100;
        int rn, gn, bn;
        int r, g, b;
        if ((r = getTheme().getRed()) + difference <= 255) {
            rn = r + difference;
        } else {
            rn = 255;
        }
        if ((g = getTheme().getGreen()) + difference <= 255) {
            gn = g + difference;
        } else {
            gn = 255;
        }
        if ((b = getTheme().getBlue()) + difference <= 255) {
            bn = b + difference;
        } else {
            bn = 255;
        }
        return new Color(rn, gn, bn);
    }

}

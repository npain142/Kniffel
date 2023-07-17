/**
 * Das connector-package enthaelt die Klasse zum Austausch zwischen
 * gui- und logik-schicht und zur Spielkontrolle.
 */
package connector;
import logic.Player;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Die Connector-Klasse ist das Herzstueck des Programms.
 * Über diese Klasse findet der Austausch zwischen der
 * Logik- und der GUI-schicht statt. Sie fungiert als
 * Java-Bean nach dem Singleton-Muster.
 */
public class Connector implements Serializable {
    private static Connector unique;
    private int playerIndex = 0;
    private final PropertyChangeSupport changeSupport;
    private final ArrayList<Player> allPlayers;
    private static Player currentPlayer;
    private Connector() {
        changeSupport = new PropertyChangeSupport(this);
        allPlayers = new ArrayList<>();
    }

    /**
     * startApplication wird aufgerufen, wenn das StartMenue neu
     * geladen werden soll.
     */
    public void startApplication() {
        allPlayers.clear();
        playerIndex = 0;
        changeSupport.firePropertyChange("MENU", null, null);
    }

    /**
     * StartGame startet ein Spiel an sich
     */
    public void startGame() {
        GameController gameController = new GameController(); //Muss nicht genutzt werden, muss nur erzeugt werden
        setCurrentPlayer(allPlayers.get(0));
        changeSupport.firePropertyChange("NEW_GAME", null, null);
        turnTable();
    }

    public void endGame() {
        for (Player player : allPlayers) {
            player.getPlayBlock().setSummeA();
            player.getPlayBlock().setSummeB();
            player.getPlayBlock().setSummeGesamt();
        }
        changeSupport.firePropertyChange("END_GAME", null, allPlayers);
    }

    public void writePlayerPoints(int points, String type) {
        getCurrentPlayer().writePoints(type, points);
        changeSupport.firePropertyChange("WRITE_POINTS", points, currentPlayer);
    }


    public void turnTable() {
            if (playerIndex >= getAllPlayers().size()) {
                playerIndex = 0;
                changeSupport.firePropertyChange("NEW_ROUND", null, null);
            }
            if (getAllPlayers().size() > 0) {
                setCurrentPlayer(allPlayers.get(playerIndex++));
                getCurrentPlayer().startRound();
                changeSupport.firePropertyChange("TABLE_TURN", null, getCurrentPlayer());
            }
    }

    public void addPlayer(Player player) {
        if (getAllPlayers().size() < 6) {
            allPlayers.add(player);
            changeSupport.firePropertyChange("NEW_PLAYER",allPlayers, player);
        } else {
            JOptionPane.showMessageDialog(null, "Maximale Anzahl an Spielern erreicht");
        }
    }

    public void removePlayer(Player player) {
        getAllPlayers().remove(player);
        changeSupport.firePropertyChange("PLAYER_REMOVED", player, null);
    }


    public void setCurrentPlayer(Player currentPlayer) {
        Connector.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public ArrayList<Player> getAllPlayers() {
        return allPlayers;
    }
    public static Connector instance() {
        if (unique == null) {
            unique = new Connector();
        }

        return unique;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
}

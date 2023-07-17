package connector;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Wird zu Beginn eines jeden Spiels neu erzeugt.
 * Kontrolliert, ob das Spiel vorbei ist.
 */
public class GameController implements PropertyChangeListener {
    Connector connector;
    private int rounds = 13;
    public GameController() {
        connector = Connector.instance();
        connector.addPropertyChangeListener(this);
    }
    public void endGame() {
        connector.endGame();
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("NEW_ROUND")) {
            rounds--;
            if (rounds == 0) {
                endGame();
            }
        }
    }
}

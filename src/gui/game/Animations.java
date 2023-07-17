/**
 * im gui.game-package sind alle Klassen zur grafischen Darstellung
 * des Spiels an sich enthalten.
 */
package gui.game;

import connector.Connector;
import gui.Window;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Die Animations-Klasse dient zum Animieren einzelner Komponenten
 */
public class Animations {
    private double mills;
    Connector connector;
    private static boolean isAnimatingThrow, isModifying;
    private Timer animationTimer;
    private final AffineTransform at = new AffineTransform();
    AffineTransform rotationTransform = new AffineTransform();
    private final MainFrame mainFrame;
    public Animations(MainFrame mainFrame) {
        connector = Connector.instance();
        this.mainFrame = mainFrame;
        transformDefault();
    }

    /**
     * Diese Methode wird am Anfang aufgerufen, um die Wuerfel
     * in ihre Startposition zu bringen.
     */
    public void transformDefault() {
        at.translate((mainFrame.getPreferredSize().getWidth() / 2) - (Dice.getSideLength()  / 2) - Window.getInset("HORIZONTAL"),
                mainFrame.getPreferredSize().getHeight() * 2/10 - (Dice.getSideLength() / 2) - Window.getInset("VERTICAL"));
        MainFrame.getDiceList().get(0).transform(at);
        for (Area[] a : MainFrame.getDice()[0].getAllEyesArea()) {
            for (Area eye : a) {
                eye.transform(at);
            }
        }

        clearTransformation(at);

        at.translate((mainFrame.getPreferredSize().getWidth()) * 2/6 - (Dice.getSideLength()  / 2) - Window.getInset("HORIZONTAL"),
                mainFrame.getPreferredSize().getHeight() * 4/10 - (Dice.getSideLength() / 2) - Window.getInset("VERTICAL"));
        MainFrame.getDiceList().get(1).transform(at);
        for (Area[] a : MainFrame.getDice()[1].getAllEyesArea()) {
            for (Area eye : a) {
                eye.transform(at);
            }
        }

        clearTransformation(at);

        at.translate((mainFrame.getPreferredSize().getWidth()) * 4/10 - (Dice.getSideLength()  / 2) - Window.getInset("HORIZONTAL"),
                mainFrame.getPreferredSize().getHeight() * 6/10 - (Dice.getSideLength() / 2) - Window.getInset("VERTICAL"));
        MainFrame.getDiceList().get(2).transform(at);
        for (Area[] a : MainFrame.getDice()[2].getAllEyesArea()) {
            for (Area eye : a) {
                eye.transform(at);
            }
        }

        clearTransformation(at);

        at.translate((mainFrame.getPreferredSize().getWidth()) * 4/6 - (Dice.getSideLength()  / 2) - Window.getInset("HORIZONTAL"),
                mainFrame.getPreferredSize().getHeight() * 4/10 - (Dice.getSideLength() / 2) - Window.getInset("VERTICAL"));
        MainFrame.getDiceList().get(3).transform(at);
        for (Area[] a : MainFrame.getDice()[3].getAllEyesArea()) {
            for (Area eye : a) {
                eye.transform(at);
            }
        }

        clearTransformation(at);

        at.translate((mainFrame.getPreferredSize().getWidth()) * 6/10 - (Dice.getSideLength()  / 2) - Window.getInset("HORIZONTAL"),
                mainFrame.getPreferredSize().getHeight() * 6/10 - (Dice.getSideLength() / 2) - Window.getInset("VERTICAL"));
        MainFrame.getDiceList().get(4).transform(at);
        for (Area[] a : MainFrame.getDice()[4].getAllEyesArea()) {
            for (Area eye : a) {
                eye.transform(at);
            }
        }

        clearTransformation(at);


    }

    public void clearTransformation(AffineTransform tr) {
        tr.setToIdentity();
    }

    /**
     * Animiert das Drehen der Wuerfel für eine bestimmte
     * Zeit.
     */
    public void animateThrow() {

        rotationTransform.setToRotation(10, 10, (mainFrame.getPreferredSize().getWidth() / 2) - Window.getInset("HORIZONTAL"),
                mainFrame.getPreferredSize().getHeight() * 4/10  - Window.getInset("VERTICAL"));
        isAnimatingThrow = true;
        mills = 100;
            animationTimer = new Timer();
        TimerTask animationTask = new TimerTask() {
            @Override
            public void run() {
                if (mills > 0) {
                    for (Dice dice : MainFrame.getDice()) {
                        dice.transform(rotationTransform);
                        for (Area[] a : dice.getAllEyesArea()) {
                            for (Area eye : a) {
                                eye.transform(rotationTransform);
                            }
                        }
                    }
                    mills--;
                    mainFrame.repaint();
                } else {
                    animationTimer.cancel();
                    clearTransformation(rotationTransform);
                    if (connector.getCurrentPlayer().getCurrentPlay().getThrowsLeft() > 0) {
                        connector.getCurrentPlayer().getCurrentPlay().setReadyToRoll(true);
                    } else {
                        connector.getCurrentPlayer().getCurrentPlay().setReadyToWritePoints(true);
                        List<Dice> toRemove = new ArrayList<>();
                        isModifying = true;
                        while (mainFrame.drawingDice) {
                            if (!mainFrame.drawingDice) {
                                break;
                            }
                        }
                        for (Dice d : MainFrame.getDiceList()) {
                            toRemove.add(d);
                            mainFrame.choosePanel.addChoosedDice(d);
                        }
                        MainFrame.getDiceList().removeAll(toRemove);
                        isModifying = false;
                    }
                    isAnimatingThrow = false;
                }
            }
        };
        animationTimer.schedule(animationTask, 0, 10);
    }

    public static boolean isIsModifying() {
        return isModifying;
    }


    /**
     *
     * @return ob gerade der Wurf animiert wird
     */
    public static boolean isIsAnimatingThrow() {
        return isAnimatingThrow;
    }

}

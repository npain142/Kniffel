package gui.game;

import connector.Connector;
import logic.exceptions.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Diese Klasse ist die Grafische Darstellung eines Wuerfels.
 * Die groeßte Herausforederung war wohl das Darstellen der
 * Augen des Wuerfels.
 */
public class Dice extends Path2D.Double {
    Connector connector;
    private ArrayList<Area> visualEyes = new ArrayList<>();
    private final ArrayList<Area[]> allEyesArea = new ArrayList<>();
    private final Eye[] eyeArray;
    private final static double sideLength = 100;
    private final static double eyeRadius = 20;
    private int numOfEyes;
    Random random = new Random();

    public Dice(int numOfEyes) {
        connector = Connector.instance();
        setNumOfEyes(numOfEyes);
        Outline outline = new Outline(sideLength);

        eyeArray = new Eye[6];
        for (int i = 0; i < 6; i++) {
            eyeArray[i] = new Eye(eyeRadius);
        }
        setEyeCoords();

        visualEyes.addAll(Arrays.asList(allEyesArea.get(numOfEyes-1)));

        append(outline, false);
    }

    public void setEyeCoords() {
        Area[] a;
        eyeArray[0].setCoords((sideLength / 2) - (eyeRadius / 2), (sideLength / 2) - (eyeRadius / 2));
        a = new Area[1];
        for (int i = 0; i < a.length; i++) {
            a[i] = new Area(eyeArray[i]);
        }
        allEyesArea.add(a);

        eyeArray[0].setCoords((sideLength / 4) - (eyeRadius / 2), (sideLength / 4) - (eyeRadius / 2));
        eyeArray[1].setCoords((sideLength * 3/4) - (eyeRadius / 2), (sideLength * 3/4) - (eyeRadius / 2));
        a = new Area[2];
        for (int i = 0; i < a.length; i++) {
            a[i] = new Area(eyeArray[i]);
        }
        allEyesArea.add(a);

        eyeArray[0].setCoords((sideLength / 5) - (eyeRadius / 2), (sideLength / 5) - (eyeRadius / 2));
        eyeArray[1].setCoords((sideLength * 4/5) - (eyeRadius / 2), (sideLength * 4/5) - (eyeRadius / 2));
        eyeArray[2].setCoords((sideLength * 2.5/5) - (eyeRadius / 2), (sideLength * 2.5/5) - (eyeRadius / 2));
        a = new Area[3];
        for (int i = 0; i < a.length; i++) {
            a[i] = new Area(eyeArray[i]);
        }
        allEyesArea.add(a);

        eyeArray[0].setCoords((sideLength / 4) - (eyeRadius / 2), (sideLength / 4) - (eyeRadius / 2));
        eyeArray[1].setCoords((sideLength * 3/4) - (eyeRadius / 2), (sideLength * 3/4) - (eyeRadius / 2));
        eyeArray[2].setCoords((sideLength / 4) - (eyeRadius / 2), (sideLength * 3/4) - (eyeRadius / 2));
        eyeArray[3].setCoords((sideLength * 3/4) - (eyeRadius / 2), (sideLength / 4) - (eyeRadius / 2));
        a = new Area[4];
        for (int i = 0; i < a.length; i++) {
            a[i] = new Area(eyeArray[i]);
        }
        allEyesArea.add(a);

        eyeArray[0].setCoords((sideLength / 5) - (eyeRadius / 2), (sideLength / 5) - (eyeRadius / 2));
        eyeArray[1].setCoords((sideLength * 4/5) - (eyeRadius / 2), (sideLength * 4/5) - (eyeRadius / 2));
        eyeArray[2].setCoords((sideLength * 2.5/5) - (eyeRadius / 2), (sideLength * 2.5/5) - (eyeRadius / 2));
        eyeArray[3].setCoords((sideLength / 5) - (eyeRadius / 2), (sideLength * 4/5) - (eyeRadius / 2));
        eyeArray[4].setCoords((sideLength * 4/5) - (eyeRadius / 2), (sideLength / 5) - (eyeRadius / 2));
        a = new Area[5];
        for (int i = 0; i < a.length; i++) {
            a[i] = new Area(eyeArray[i]);
        }
        allEyesArea.add(a);

        eyeArray[0].setCoords((sideLength * 1.5/6) - (eyeRadius / 2), (sideLength / 6) - (eyeRadius / 2));
        eyeArray[1].setCoords((sideLength * 1.5/6) - (eyeRadius / 2), (sideLength *  3/6) - (eyeRadius / 2));
        eyeArray[2].setCoords((sideLength * 1.5/6) - (eyeRadius / 2), (sideLength *  5/6) - (eyeRadius / 2));
        eyeArray[3].setCoords((sideLength *  4.5/6) - (eyeRadius / 2), (sideLength / 6) - (eyeRadius / 2));
        eyeArray[4].setCoords((sideLength *  4.5/6) - (eyeRadius / 2), (sideLength *  3/6) - (eyeRadius / 2));
        eyeArray[5].setCoords((sideLength *  4.5/6) - (eyeRadius / 2), (sideLength *  5/6) - (eyeRadius / 2));
        a = new Area[6];
        for (int i = 0; i < a.length; i++) {
            a[i] = new Area(eyeArray[i]);
        }
        allEyesArea.add(a);
    }

    public void updateVisualEyes() {
        visualEyes = new ArrayList<>(Arrays.asList(allEyesArea.get(numOfEyes - 1)));
    }


    public ArrayList<Area> getVisualEyes() {
        return visualEyes;
    }

    public void setNumOfEyes(int numOfEyes) {
        if (checkNumOfEyes(numOfEyes)) {
            this.numOfEyes = numOfEyes;
        } else {
            throw new NumberOfEyesException();
        }
    }
    public void throwDiceForAnimation() {
        numOfEyes = random.nextInt(1, 7);
        updateVisualEyes();
    }


    public static double getSideLength() {
        return sideLength;
    }

    public boolean checkNumOfEyes(int c) {
        return c >= 1 && c <= 6;
    }

    /**
     * Grafische Darstellung eines Auge eines Wuerfels
     */
    class Eye extends Ellipse2D.Double {

        public Eye(double radius) {
            setFrame(0, 0, radius, radius);
        }


        public void setCoords(double x, double y) {
            setFrame(x, y, getWidth(), getHeight());
        }
    }

    /**
     * Umrandung eines Wuerfels
     */
    class Outline extends Path2D.Double {

        public Outline(double sideLen) {
            moveTo(0, 0);
            lineTo(sideLen, 0);
            lineTo(sideLen, sideLen);
            lineTo(0, sideLen);
            lineTo(0, 0);
            closePath();
        }
    }
    public ArrayList<Area[]> getAllEyesArea() {
        return allEyesArea;
    }
    public int getNumOfEyes() {
        return numOfEyes;
    }

}

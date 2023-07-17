package logic;

/**
 * Die Klasse Dice repraesentriert einen einfachen Würfel für die Logik-schicht
 */
public class Dice implements Comparable {
    private int eyes;

    public Dice(int eyes) {
        setEyes(eyes);
    }
    public void setEyes(int eyes) {
        this.eyes = eyes;
    }

    public int getEyes() {
        return eyes;
    }

    @Override
    public int compareTo(Object o) {
        Dice cmp = (Dice) o;
        return Integer.compare(getEyes(), cmp.getEyes());
    }
}

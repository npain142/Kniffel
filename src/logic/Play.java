package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Play repraesientert einen Spielzug.
 * Jedesmal wenn ein Spieler eine Runde beginnt wird ein neues
 * Play-Objekt erstellt.
 */
public class Play {

    private int throwsLeft;
    private Throw currentThrow;
    private final Throw[] totalThrows = new Throw[3];
    private final ArrayList<Dice> choosedDice = new ArrayList<>();
    private boolean readyToPass = false, readyToRoll = true, readyToWritePoints = false, noThrowsLeft = false;
    public Play() {
        setThrowsLeft(3);
    }
    private void endPlay() {
        setThrowsLeft(0);
        Collections.reverse(Arrays.asList(totalThrows));
    }

    public void chooseDice(int d) {
        choosedDice.add(new Dice(d));
    }
    public void removeChoosedDice(int p) {
        Dice toRemove = null;
        for (Dice d : choosedDice) {
            if (d.getEyes() == p) {
                toRemove = d;
                break;
            }
        }
        choosedDice.remove(toRemove);
    }

    /**
     * Die Methode gibt die Punkte zurück, die die aktuell ausgewaehlten
     * Wuerfel (choosedDice) liefern würden.
     * @param variation ist die Bezeichnung des Feldes des Spielblocks
     * @return Gibt die Punkte zur uebergebenen Feldbezeichnung zurück
     */
    public int previewPoints(String variation) {
        Dice[] newDice = new Dice[5];
        for (int i = 0; i < newDice.length; i++) {
            newDice[i] = choosedDice.get(i);
        }
        switch (variation) {
            case "1" : {return Rules.countNums(newDice, 1);}

            case "2" : {return Rules.countNums(newDice, 2);}
            case "3" : {return Rules.countNums(newDice, 3);}
            case "4" : {return Rules.countNums(newDice, 4);}
            case "5" : {return Rules.countNums(newDice, 5);}
            case "6" : {return Rules.countNums(newDice, 6);}
            case "DREIERPASCH" : {return Rules.getPasch(newDice, 3);}
            case "VIERERPASCH": {return Rules.getPasch(newDice, 4);}
            case "FULL-HOUSE":  {
                if (Rules.isFullHouse(newDice)) {
                    return 25;
                } else {
                    return 0;
                }
            }
            case "KLEINE STRASSE" : {
                if (Rules.isKleineStrasse(newDice)) {
                    return 30;
                } else {
                    return 0;
                }
            }

            case "GROSSE STRASSE" : {
                if (Rules.isGrosseStrasse(newDice)) {
                    return 40;
                } else {
                    return 0;
                }
            }
            case "KNIFFEL" : {
                if (Rules.isKniffel(newDice)) {
                    return 50;
                } else {
                    return 0;
                }
            }
            case "CHANCE" : {
                return Rules.getChance(newDice);
            }
            default:
                return 0;
        }
    }

    public Throw rollDice() {
        if (getThrowsLeft() > 1) {
            setReadyToRoll(false);
            Throw newThrow = new Throw();
            setCurrentThrow(newThrow);
            getTotalThrows()[getThrowsLeft() - 1] = newThrow;
            setThrowsLeft(getThrowsLeft()-1);
            return newThrow;
        } else {
            endPlay();
            setNoThrowsLeft(true);
            setReadyToRoll(false);
            return currentThrow;
        }
    }
    public void setNoThrowsLeft(boolean noThrowsLeft) {
        this.noThrowsLeft = noThrowsLeft;
    }

    public boolean isNoThrowsLeft() {
        return noThrowsLeft;
    }

    public int getThrowsLeft() {
        return throwsLeft;
    }

    public Throw[] getTotalThrows() {
        return totalThrows;
    }

    public void setCurrentThrow(Throw currentThrow) {
        this.currentThrow = currentThrow;
    }

    public void setThrowsLeft(int throwsLeft) {
        this.throwsLeft = throwsLeft;
    }

    public void setReadyToRoll(boolean readyToRoll) {
        this.readyToRoll = readyToRoll;
    }

    public boolean isReadyToPass() {
        return readyToPass;
    }

    public void setReadyToPass(boolean readyToPass) {
        this.readyToPass = readyToPass;
    }

    public boolean isReadyToWritePoints() {
        return readyToWritePoints;
    }

    public void setReadyToWritePoints(boolean readyToWritePoints) {
        this.readyToWritePoints = readyToWritePoints;
    }

    public boolean isReadyToRoll() {
        return readyToRoll;
    }
}


package logic;

import logic.exceptions.UnknownExpressionException;
import java.util.HashMap;

/**
 * Diese Klasse repraesentiert den Spielblock.
 * Jeder Spieler "bekommt" einen neuen Spielblock zu beginn eines Spiels
 */
public class Block {
    private final HashMap<DiceVariation, Boolean> blockA = new HashMap<>();
    private final HashMap<DiceVariation, Boolean> blockB = new HashMap<>();
    private final HashMap<DiceVariation, Boolean> blockTotal = new HashMap<>();
    private int summeA, summeB, summeGesamt, bonus = 0;
    public Block() {
        blockA.put(new DiceVariation("1", 0), false);
        blockA.put(new DiceVariation("2", 0), false);
        blockA.put(new DiceVariation("3", 0), false);
        blockA.put(new DiceVariation("4", 0), false);
        blockA.put(new DiceVariation("5", 0), false);
        blockA.put(new DiceVariation("6", 0), false);

        blockB.put(new DiceVariation("DREIERPASCH", 0), false);
        blockB.put(new DiceVariation("VIERERPASCH", 0), false);
        blockB.put(new DiceVariation("FULL-HOUSE", 0), false);
        blockB.put(new DiceVariation("KLEINE STRASSE", 0), false);
        blockB.put(new DiceVariation("GROSSE STRASSE", 0), false);
        blockB.put(new DiceVariation("KNIFFEL", 0), false);
        blockB.put(new DiceVariation("CHANCE", 0), false);

        blockTotal.putAll(blockA);
        blockTotal.putAll(blockB);
    }

    public void writePoint(String type, int points) {
        type = type.toUpperCase();
        for (DiceVariation v : getBlockTotal().keySet()) {
            if (v.toString().equals(type) && !getBlockTotal().get(v)) {
                v.setPoints(points);
                if (blockA.containsKey(v)) {
                    getBlockA().replace(v, true);
                } else {
                    getBlockB().replace(v, true);
                }
                getBlockTotal().replace(v, true);
                return;
            }

        }
        throw new UnknownExpressionException();
    }

    public int getPointsOf(String type) {
        for (DiceVariation dv : getBlockTotal().keySet()) {
            if (dv.equals(type)) {                              //Equals wird in DiceVariation überschrieben
                return dv.getPoints();
            }
        }
        throw new UnknownExpressionException();
    }

    public void setSummeA() {
        this.summeA = calcSummeA();
    }

    public void setSummeB() {
        this.summeB = calcSummeB();
    }

    public void setSummeGesamt() {
        this.summeGesamt = calcsummeGes();
    }

    public int getSummeABonus() {
        return this.summeA + this.bonus;
    }
    public int getSummeA()  {
        return this.summeA;
    }
    public int getSummeB() {
        return this.summeB;
    }
    public int getSummeGes() {
        return this.summeGesamt;
    }
    private int calcSummeA() {
        int sum = getBlockA().keySet().stream().mapToInt(DiceVariation::getPoints).sum();
        if (sum >= 63) {
            setBonus();
        }
        return sum;
    }

    private int calcSummeB() {
        return getBlockB().keySet().stream().mapToInt(DiceVariation::getPoints).sum();
    }
    private int calcsummeGes() {
        return getSummeABonus() + getSummeB();
    }

    public HashMap<DiceVariation, Boolean> getBlockA() {
        return blockA;
    }

    public HashMap<DiceVariation, Boolean> getBlockB() {
        return blockB;
    }

    public HashMap<DiceVariation, Boolean> getBlockTotal() {
        return blockTotal;
    }

    public void setBonus() {
        this.bonus = 35;
    }

    public int getBonus() {
        return this.bonus;
    }

    public static class DiceVariation {

        private String name;
        private int points;

        /**
         * DiceVariation ist ein nicht-primitiver Datentyp, welcher ein Feld aus dem
         * Spielblock und dessen Punkte zusammenfasst.
         *
         * @param name  die Bezeichnung des Spielblockfeldes
         * @param points die Punkte die unter dem Feld gespeichert sind
         */
        public DiceVariation(String name, int points) {
            setName(name);
            setPoints(points);
        }

        public String getName() {
            return name;
        }

        public int getPoints() {
            return points;
        }
        public void setName(String name) {
            this.name = name;
        }

        public void setPoints(int points) {
            this.points = points;
        }

        /**
         * Die equals Methode liefert Wahr zurueck, der von Dicevariation
         * gleich dem Parameter obj ist, als ein String.
         * @param obj eigentlich ein String
         *
         */
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof String str) {
                return str.equals(this.getName());
            }
            return false;
        }

        @Override
        public String toString() {
            return this.getName();
        }
    }

}

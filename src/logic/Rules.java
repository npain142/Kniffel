package logic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Die Rules-Klasse repraesentiert die "Regeln" von Kniffel.
 * Sie hat für jede gueltige Wuerfel-zusammenstellung eine Methode,
 * um sie zu pruefen. Der Zugriff erfolgt über die previewPoints-Methode
 * in Play.
 */

public class Rules {

    public static int countNums(final Dice[] currentDice, final int num) {
        int tmp = 0;
        for (Dice dice : currentDice) {
            if (dice.getEyes() == num) {
                tmp += num;
            }
        }
        return tmp;
    }

    public static int getPasch(final Dice[] currentDice, int pasch) {
        int tmp = 0;
        if (isPasch(currentDice, pasch)) {
            for (Dice dice : currentDice) {
                tmp += dice.getEyes();
            }
            return tmp;
        }
        return 0;
    }

    private static boolean isPasch(final Dice[] t, int p) {
        for (int i = 1; i <=6; i++) {
            int tmp = 0;
            for (int j = 0; j < 5; j++) {
                if (t[j].getEyes() == i) {
                    tmp++;
                }
                if (tmp >= p) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isFullHouse(final Dice[] currentDice) {
        int[] tmpDice = new int[currentDice.length];
        for (int i = 0; i < currentDice.length; i++) {
            tmpDice[i] = currentDice[i].getEyes();
        }
        Arrays.sort(tmpDice);
        if (tmpDice[0] == tmpDice[4]) {
            return false;
        }
        return tmpDice[0] == tmpDice[1] && tmpDice[2] == tmpDice[3] && tmpDice[2] == tmpDice[4] ||
                tmpDice[0] == tmpDice[1] && tmpDice[0] == tmpDice[2] && tmpDice[3] == tmpDice[4];
    }

    public static boolean isKleineStrasse(final Dice[] currentDice) {
        ArrayList<Integer> l = new ArrayList<>();
        for (Dice value : currentDice) {
            l.add(value.getEyes());
        }
        StringBuilder sb = new StringBuilder();
        l.stream().distinct().sorted().forEach(sb::append);
        return (
                    ((sb.length() >= 4) && (sb.substring(0, 4).equals("1234") || sb.substring(0, 4).equals("2345") || sb.substring(0, 4).equals("3456"))) ||
                    ((sb.length() == 5) && (sb.substring(1, 5).equals("1234") || sb.substring(1, 5).equals("2345") || sb.substring(1, 5).equals("3456")))
                );
    }

    public static boolean isGrosseStrasse(final Dice[] currentDice) {
        ArrayList<Integer> l = new ArrayList<>();
        for (Dice value : currentDice) {
            l.add(value.getEyes());
        }
        StringBuilder sb = new StringBuilder();
        l.stream().sorted().forEach(sb::append);
        return sb.substring(0, 5).equals("12345") || sb.substring(0, 5).equals("23456");
    }

    public static boolean isKniffel(final Dice[] currentDice) {
        int first = currentDice[0].getEyes();
        for (Dice dice : currentDice) {
            if (dice.getEyes() != first) {
                return false;
            }
        }
        return true;
    }

    public static int getChance(Dice[] currentDice) {
        int tmp = 0;
        for (Dice dice : currentDice) {
            tmp += dice.getEyes();
        }
        return tmp;
    }


}

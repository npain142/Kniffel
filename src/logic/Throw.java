package logic;

import java.util.Random;

/**
 * Ein Throw repraesentiert einen Wurf von fuenf Würfeln.
 * Beim Kniffel waere es der Wuerfelbecher.
 */
public class Throw {
    int[] allEyes = new int[5];
    public Throw() {
        Random rd = new Random();

        for (int i = 0; i < 5; i++) {
            allEyes[i]= rd.nextInt(1, 7);
        }
    }
    public int[] getAllEyes() {
        return allEyes;
    }
}

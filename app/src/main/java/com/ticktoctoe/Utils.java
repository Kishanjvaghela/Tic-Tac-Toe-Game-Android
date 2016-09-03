package com.ticktoctoe;

import java.util.Random;

/**
 * Created by kisha_000 on 9/3/2016.
 */
public class Utils {
    public static int getRandom(int minimum, int maximum) {
        Random rn = new Random();
        int range = maximum - minimum + 1;
        return rn.nextInt(range) + minimum;
    }
}

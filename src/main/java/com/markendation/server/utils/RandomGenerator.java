package com.markendation.server.utils;

import java.util.Random;

public class RandomGenerator {
    public int genInt(int l, int r) {
        Random random = new Random();

        return random.nextInt(r - l + 1) + l;
    }
}

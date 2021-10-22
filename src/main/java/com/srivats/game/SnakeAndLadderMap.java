package com.srivats.game;

import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

public class SnakeAndLadderMap {

    private static final Map<Integer, Integer> LADDER = Map.of(
            1, 38,
            4, 14,
            8, 30,
            21, 42,
            28, 76,
            50, 67,
            71, 92,
            80, 99


    );

    private static final Map<Integer,Integer> SNAKES = Map.of(
            32, 10,
            36, 6,
            48, 26,
            62, 18,
            88, 24,
            95, 56,
            97, 78
    );

    private static final Map<Integer, Integer> MAP = new HashMap<>();

    static {
        MAP.putAll(SNAKES);
        MAP.putAll(LADDER);
    }


    public static int getPosition(int position){
        return MAP.getOrDefault(position, position);
    }
}

package com.mateo9x.carbot.game;

import java.util.Arrays;

public enum GameType {
    FunFact("/funfact"),
    Quiz("/quiz"),
    CarMeter("/carmeter"),
    None(null);

    public final String value;

    GameType(String value) {
        this.value = value;
    }

    public static GameType getByValue(String value) {
        return Arrays.stream(GameType.values())
                .filter(gameType -> value.equals(gameType.value))
                .findFirst().orElse(GameType.None);
    }
}

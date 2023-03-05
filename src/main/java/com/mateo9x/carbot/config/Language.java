package com.mateo9x.carbot.config;

import java.util.Arrays;

public enum Language {
    EN("en"),
    PL("pl");

    public final String value;

    Language(String value) {
        this.value = value;
    }

    public static Language getByValue(String value) {
        return Arrays.stream(Language.values())
                .filter(languageType -> value.equals(languageType.value))
                .findFirst().orElse(null);
    }
}

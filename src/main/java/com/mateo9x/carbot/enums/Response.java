package com.mateo9x.carbot.enums;

import java.util.Arrays;

public enum Response {
    FunFact("/ciekawostka"),
    Quiz("/quiz"),
    None(null);

    public final String value;

    Response(String value) {
        this.value = value;
    }

    public static Response getResponseByValue(String value) {
        return Arrays.stream(Response.values())
                .filter(response -> value.equals(response.value))
                .findFirst().orElse(Response.None);
    }
}

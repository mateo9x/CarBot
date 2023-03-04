package com.mateo9x.carbot.validator;

import java.util.Arrays;
import java.util.List;

public class ForbiddenWordDictionary {

    public static List<String> forbiddenWordList;

    static {
        forbiddenWordList = Arrays.asList("kurwa", "chuj", "huj", "japierdole", "pierdole", "szmata",
                "murzyn", "żyd", "pedał", "jebać", "jebany", "jebana", "jeb", "jebło", "ciul");
    }
}

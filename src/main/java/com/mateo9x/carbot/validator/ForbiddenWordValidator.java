package com.mateo9x.carbot.validator;

import com.mateo9x.carbot.config.Language;
import com.mateo9x.carbot.message.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ForbiddenWordValidator {

    private static final String BAD_LANGUAGE_LIST_PROPERTY = "bad.language.list";

    public static boolean doesTextContainsForbiddenWord(Language language, String text) {
        List<String> forbiddenWordList = getForbiddenWordList(language);
        return forbiddenWordList.contains(text.toLowerCase().trim());
    }

    private static List<String> getForbiddenWordList(Language language) {
       return Arrays.stream(MessageSource.getMessage(language, BAD_LANGUAGE_LIST_PROPERTY)
               .split(","))
               .collect(Collectors.toList());
    }
}

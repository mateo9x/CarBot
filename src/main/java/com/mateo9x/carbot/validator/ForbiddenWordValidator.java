package com.mateo9x.carbot.validator;

import com.mateo9x.carbot.CarBot;
import com.mateo9x.carbot.config.Language;
import com.mateo9x.carbot.message.MessageSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ForbiddenWordValidator {

    private static final String BAD_LANGUAGE_LIST_PROPERTY = "bad.language.list";

    public static boolean doesTextContainsForbiddenWord(CarBot carBot, String text) {
        List<String> forbiddenWordList = getForbiddenWordList(carBot);
        return forbiddenWordList.contains(text.toLowerCase().trim());
    }

    private static List<String> getForbiddenWordList(CarBot carBot) {
       return Arrays.stream(carBot.getMessageSource().getMessage(BAD_LANGUAGE_LIST_PROPERTY)
               .split(","))
               .collect(Collectors.toList());
    }
}

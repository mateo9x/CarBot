package com.mateo9x.carbot.message;


import com.mateo9x.carbot.config.Language;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageSource {

    private static final String MESSAGE_PROPERTIES_PATH= "i18n/message";

    public static String getMessage(Language language, String messageKey) {
        Locale locale = Locale.forLanguageTag(language.value);
        try {
            return (String) ResourceBundle.getBundle(MESSAGE_PROPERTIES_PATH, locale).getObject(messageKey);
        } catch (Exception e) {
            return (String) ResourceBundle.getBundle(MESSAGE_PROPERTIES_PATH, Locale.US).getObject(messageKey);
        }
    }
}

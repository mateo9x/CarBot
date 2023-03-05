package com.mateo9x.carbot.message;


import com.mateo9x.carbot.config.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;

@AllArgsConstructor
@Getter
public class MessageSource {

    private static final String MESSAGE_PROPERTIES_PATH= "i18n/message";
    private final Language language;

    public String getMessage(String messageKey) {
        Locale locale = Locale.forLanguageTag(language.value);
        try {
            return (String) ResourceBundle.getBundle(MESSAGE_PROPERTIES_PATH, locale).getObject(messageKey);
        } catch (Exception e) {
            return (String) ResourceBundle.getBundle(MESSAGE_PROPERTIES_PATH, Locale.US).getObject(messageKey);
        }
    }
}
